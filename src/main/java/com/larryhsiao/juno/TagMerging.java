package com.larryhsiao.juno;

import com.larryhsiao.clotho.Action;
import com.larryhsiao.clotho.Source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.Collection;

/**
 * Action to merge given tags.
 */
public class TagMerging implements Action {
    private final Source<Connection> db;
    private final Tag[] tags;

    public TagMerging(Source<Connection> db, Tag... tags) {
        this.db = db;
        this.tags = tags;
    }

    @Override
    public void fire() {
        try {
            if (tags.length < 2) {
                throw new IllegalArgumentException(
                    "Tags should more then 2, given: " + tags.length
                );
            }
            final Connection conn = db.value();
            final String tagIds = tagIds();
            final String fileIds = fileIds();
            try (PreparedStatement stmt = conn.prepareStatement(
                // language=H2
                "UPDATE FILE_TAG SET TAG_ID=? " +
                    "WHERE TAG_ID IN (" + tagIds + ") " +
                    "AND FILE_ID NOT IN (" + fileIds + ");"
            )) {
                stmt.setLong(1, tags[0].id());
                stmt.execute();
            }
           new TagDeletionById(
               db,
               Arrays.stream(tags).skip(1).mapToLong(Tag::id).toArray()
           ).fire();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String fileIds() {
        Collection<AFile> files = new QueriedAFiles(
            new FilesByTagId(db, tags[0].id())
        ).value().values();
        final StringBuilder builder = new StringBuilder();
        int i = 0;
        for (AFile file : files) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(file.id());
            i++;
        }
        return builder.toString();
    }

    private String tagIds() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 1; i < tags.length; i++) {
            if (i > 1) {
                builder.append(", ");
            }
            builder.append(tags[i].id());
        }
        return builder.toString();
    }
}
