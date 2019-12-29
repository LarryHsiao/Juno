package com.larryhsiao.juno;

import com.silverhetch.clotho.Action;
import com.silverhetch.clotho.Source;

import java.sql.Connection;

/**
 * Action to remove favorite mark.
 */
public class UnMarkFavorite implements Action {
    private final Source<Connection> db;
    private final long fileId;

    public UnMarkFavorite(Source<Connection> db, long fileId) {
        this.db = db;
        this.fileId = fileId;
    }

    @Override
    public void fire() {
        new DetachAction(
            db, fileId,
            new TagByName(db, "favorite").value().id()).fire();
    }
}
