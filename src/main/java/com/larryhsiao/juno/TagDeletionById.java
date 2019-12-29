package com.larryhsiao.juno;

import com.silverhetch.clotho.Action;
import com.silverhetch.clotho.Source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Action to delete tag by ids
 */
public class TagDeletionById implements Action {
    private final Source<Connection> conn;
    private final long[] ids;

    public TagDeletionById(Source<Connection> conn, long... ids) {
        this.conn = conn;
        this.ids = ids;
    }

    @Override
    public void fire() {
        try {
            try (PreparedStatement stmt = conn.value().prepareStatement(
                // language=H2
                "DELETE FROM TAGS WHERE ID IN (" + tagIds() + ")"
            )) {
                stmt.execute();
            }
            try (PreparedStatement stmt = conn.value().prepareStatement(
                // language=H2
                "DELETE FROM FILE_TAG WHERE TAG_ID IN (" + tagIds() + ")"
            )) {
                stmt.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String tagIds() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ids.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(ids[i]);
        }
        return builder.toString();
    }
}
