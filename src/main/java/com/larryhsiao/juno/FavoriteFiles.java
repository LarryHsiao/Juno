package com.larryhsiao.juno;

import com.silverhetch.clotho.Source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Source to fetch all file entry in database.
 */
public class FavoriteFiles implements Source<ResultSet> {
    private final Source<Connection> db;

    /**
     * @param db The connection of tagging database.
     */
    public FavoriteFiles(Source<Connection> db) {
        this.db = db;
    }

    @Override
    public ResultSet value() {
        try {
            PreparedStatement stmt = db.value().prepareStatement(
                // language=H2
                "SELECT F.* FROM FILE_TAG " +
                    "LEFT JOIN FILES F on FILE_TAG.FILE_ID = F.ID " +
                    "WHERE TAG_ID=?;"
            );
            stmt.setLong(
                1,
                new TagByName(db, "favorite").value().id()
            );
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
