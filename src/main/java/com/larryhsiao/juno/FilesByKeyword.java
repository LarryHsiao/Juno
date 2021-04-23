package com.larryhsiao.juno;

import com.larryhsiao.clotho.Source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Source to fetch all file entry in database have given keyword.
 */
public class FilesByKeyword implements Source<ResultSet> {
    private final Source<Connection> db;
    private final String keyword;
    private final boolean excluded;

    /**
     * @param db       The connection of tagging database.
     * @param keyword  The keyword the file should related.
     * @param excluded Exclude the given keyword.
     */
    public FilesByKeyword(
        Source<Connection> db,
        String keyword,
        boolean excluded) {
        this.db = db;
        this.keyword = keyword;
        this.excluded = excluded;
    }

    public FilesByKeyword(Source<Connection> db, String keyword) {
        this(db, keyword, false);
    }

    @Override
    public ResultSet value() {
        try {
            PreparedStatement stmt = statement();
            stmt.setString(1, "%" + keyword + "%");
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement statement() throws SQLException {
        if (excluded) {
            return db.value().prepareStatement(  // language=H2
                "SELECT * FROM files " +
                    "where id not in (" +
                    "SELECT files.id FROM files " +
                    "LEFT OUTER JOIN file_tag ft on files.id = ft.file_id " +
                    "LEFT OUTER JOIN tags t on ft.tag_id = t.id " +
                    "WHERE files.name like (?1) " +
                    "or t.name like (?1)" +
                    "GROUP BY files.id" +
                    ");");
        } else {
            return db.value().prepareStatement(  // language=H2
                "SELECT files.* FROM files " +
                    "LEFT OUTER JOIN file_tag ft on files.id = ft.file_id " +
                    "LEFT OUTER JOIN tags t on ft.tag_id = t.id " +
                    "WHERE files.name like (?1) " +
                    "OR t.name like (?1)" +
                    "GROUP BY files.id;");
        }
    }
}
