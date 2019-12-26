package com.larryhsiao.juno;

import com.silverhetch.clotho.Source;
import com.silverhetch.clotho.source.ConstSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Source to build tag map from db query result.
 */
public class QueriedTags implements Source<Map<String, Tag>> {
    private final Source<ResultSet> resSource;

    public QueriedTags(Source<ResultSet> resSource) {
        this.resSource = resSource;
    }

    @Override
    public Map<String, Tag> value() {
        try (ResultSet res = resSource.value()) {
            Map<String, Tag> result = new HashMap<>();
            while (res.next()) {
                final String name = res.getString("name");
                result.put(
                    name,
                    new QueriedTag(new ConstSource<>(res), false).value()
                );
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
