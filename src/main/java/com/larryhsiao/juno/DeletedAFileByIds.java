package com.larryhsiao.juno;

import com.larryhsiao.clotho.Action;
import com.larryhsiao.clotho.Source;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Action to delete a file by id
 */
public class DeletedAFileByIds implements Action {
    private final Source<Connection> dbConn;
    private final Source<List<Long>> ids;

    public DeletedAFileByIds(
        Source<Connection> dbConn, Source<List<Long>> ids) {
        this.dbConn = dbConn;
        this.ids = ids;
    }

    @Override
    public void fire() {
        final Connection conn = dbConn.value();
        final String idString = idString(ids.value());
        filesTable(conn, idString);
        fileTagTable(conn, idString);
    }

    private void fileTagTable(Connection conn, String idString) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(
                // language=H2
                "DELETE FROM FILE_TAG WHERE FILE_ID IN (" + idString + ");"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void filesTable(Connection conn, String idString) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(
                // language=H2
                "DELETE FROM files WHERE id IN (" + idString + ");"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String idString(List<Long> ids) {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(ids.get(i));
        }
        return result.toString();
    }
}
