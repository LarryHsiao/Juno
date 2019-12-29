package com.larryhsiao.juno;

import com.silverhetch.clotho.Source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Source to fetch all file entry in database have tag attached.
 */
public class FilesByTagId implements Source<ResultSet> {
    private final Source<Connection> db;
    private final long[] tagId;

    /**
     * @param db    The connection of tagging database.
     * @param tagId The tag we wonder to search with.
     */
    public FilesByTagId(Source<Connection> db, long... tagId) {
        this.db = db;
        this.tagId = tagId;
    }

    @Override
    public ResultSet value() {
        try {
            PreparedStatement stmt =
                db.value().prepareStatement(  // language=H2
                    "SELECT f.* FROM file_tag " +
                        "LEFT JOIN files f on file_tag.file_id = f.id " +
                        "WHERE file_tag.tag_id IN (" + tagIdStr() + ");");
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String tagIdStr() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tagId.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(tagId[i]);
        }

        return builder.toString();
    }
}
