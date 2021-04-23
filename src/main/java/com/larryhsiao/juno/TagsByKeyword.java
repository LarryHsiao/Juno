package com.larryhsiao.juno;

import com.larryhsiao.clotho.Source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Source to build tag that belongs to given keywor.
 */
public class TagsByKeyword implements Source<ResultSet> {
    private final Source<Connection> db;
    private final String keyword;

    public TagsByKeyword(Source<Connection> db, String keyword) {
        this.db = db;
        this.keyword = keyword;
    }

    @Override
    public ResultSet value() {
        try {
            final PreparedStatement stmt = db.value().prepareStatement(
                // language=H2
                "SELECT * FROM tags " +
                    "WHERE name LIKE ?1;"
            );
            stmt.setString(1, "%" + keyword + "%");
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
