package com.larryhsiao.juno;

import com.larryhsiao.clotho.Source;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Source to fetch all file entry in database have given input.
 * <p>
 * There are two type of input keyword, command and keyword.
 * Command is a string that meets specific format like '#!tags' which means querying files that no tags attached.
 * Keyword is a string that related resource have.
 */
public class FilesByInput implements Source<ResultSet> {
    private final Source<Connection> db;
    private final String input;

    /**
     * @param db    The connection of tagging database.
     * @param input The command or keyword of file.
     */
    public FilesByInput(Source<Connection> db, String input) {
        this.db = db;
        this.input = input;
    }

    @Override
    public ResultSet value() {
        if ("#!tag".equals(input)) {
            return new FilesNotTagged(db).value();
        }
        if ("#favorite".equals(input)) {
            return new FavoriteFiles(db).value();
        }
        if (input.startsWith("#") && input.length() > 1) {
            return new FilesByTagId(
                db,
                new TagByName(db, input.substring(1), false).value().id()
            ).value();
        }
        if (input.startsWith("!")) {
            return new FilesByKeyword(db, input.substring(1), true).value();
        }
        return new FilesByKeyword(db, input).value();
    }
}
