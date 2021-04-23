package com.larryhsiao.juno;

import com.larryhsiao.clotho.Action;
import com.larryhsiao.clotho.Source;

import java.sql.Connection;

/**
 * Action to mark given file as favorite.
 */
public class MarkFavorite implements Action {
    private final Source<Connection> db;
    private final long id;

    public MarkFavorite(Source<Connection> db, long id) {
        this.db = db;
        this.id = id;
    }

    @Override
    public void fire() {
        new AttachAction(
            db,
            id,
            new TagByName(db, "favorite").value().id()
        ).fire();
    }
}
