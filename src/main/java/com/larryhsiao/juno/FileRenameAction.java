package com.larryhsiao.juno;

import com.larryhsiao.clotho.Action;
import com.larryhsiao.clotho.Source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Action to rename a file in database.
 */
public class FileRenameAction implements Action {
    private final Source<Connection> db;
    private final AFile file;
    private final String newName;

    public FileRenameAction(Source<Connection> db, AFile file, String newName) {
        this.db = db;
        this.file = file;
        this.newName = newName;
    }

    @Override
    public void fire() {
        try (PreparedStatement stmt = db.value().prepareStatement(
            // language=H2
            "UPDATE files " +
                "SET name=?1 " +
                "WHERE id=?2"
        )) {
            stmt.setString(1, newName);
            stmt.setLong(2, file.id());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
