package com.larryhsiao.juno;

import com.silverhetch.clotho.Action;
import com.silverhetch.clotho.Source;
import com.silverhetch.clotho.file.FileDelete;
import com.silverhetch.clotho.source.ConstSource;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Action to clean up deleted files in database
 */
public class CleanUpFiles implements Action {
    private final Source<Connection> conn;
    private final Source<List<String>> fsFiles;

    public CleanUpFiles(
        Source<Connection> conn, Source<List<String>> files) {
        this.conn = conn;
        this.fsFiles = files;
    }

    @Override
    public void fire() {
        final HashSet<String> exist = new HashSet<>(fsFiles.value());
        final Map<String, AFile> dbFiles =
            new QueriedAFiles(new AllFiles(conn)).value();
        final List<Long> deleted = new ArrayList<>();
        dbFiles.forEach((name, dbFile) -> {
            if (!exist.contains(dbFile.name())) {
                deleted.add(dbFile.id());
            }
        });
        new DeletedAFileByIds(
            conn,
            new ConstSource<>(deleted)
        ).fire();
    }
}
