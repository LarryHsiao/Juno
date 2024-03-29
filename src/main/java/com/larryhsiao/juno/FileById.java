package com.larryhsiao.juno;

import com.larryhsiao.clotho.Source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Source to fetch all file entry in database.
 */
public class FileById implements Source<ResultSet> {
    private final Source<Connection> db;
    private final long id;

    /**
     * @param db The connection of tagging database.
     * @param id Id of the file.
     */
    public FileById(Source<Connection> db, long id) {
        this.db = db;
        this.id = id;
    }

    @Override
    public ResultSet value() {
        try {
            PreparedStatement stmt = db.value().prepareStatement(
                // language=H2
                "SELECT * FROM files WHERE id=?;"
            );
            stmt.setLong(1, id);
            ResultSet res = stmt.executeQuery();
            res.next();
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
