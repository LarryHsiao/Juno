package com.larryhsiao.juno;

import com.larryhsiao.clotho.Source;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Source to fetch all tag entry in database.
 */
public class AllTags implements Source<ResultSet> {
    private final Source<Connection> db;

    /**
     * @param db The connection of tagging database.
     */
    public AllTags(Source<Connection> db) {
        this.db = db;
    }

    @Override
    public ResultSet value() {
        try {
            Statement stmt = db.value().createStatement();
            return stmt.executeQuery(
                // language=H2
                "SELECT * FROM tags;"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
