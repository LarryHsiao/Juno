package com.larryhsiao.juno;

import com.silverhetch.clotho.Source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Updated {@link Tag}.
 */
public class RenamedTag implements Source<Tag> {
    private final Source<Connection> db;
    private final Tag tag;
    private final String newName;

    public RenamedTag(Source<Connection> db, Tag tag, String newName) {
        this.db = db;
        this.tag = tag;
        this.newName = newName;
    }

    @Override
    public Tag value() {
        try (PreparedStatement stmt = db.value().prepareStatement(
            // language=H2
            "UPDATE TAGS SET NAME=? WHERE ID=?;"
        )) {
            stmt.setString(1, newName);
            stmt.setLong(2, tag.id());
            return new ConstTag(
                tag.id(),
                newName
            );
        } catch (SQLException e) {
            throw new RuntimeException("Rename Tag failed: " + tag.id());
        }
    }
}
