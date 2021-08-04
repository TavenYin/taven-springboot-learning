package com.github.taven.webfluxfirstexp.dao;

import com.github.taven.webfluxfirstexp.model.UserDO;
import io.r2dbc.spi.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserDao {

    private DatabaseClient databaseClient;

    @Autowired
    public void setDatabaseClient(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    public Mono<UserDO> getModel(Long id) {
        Mono<UserDO> userDOMono = Mono.from(databaseClient.getConnectionFactory().create())
                .flatMapMany(connection -> {
                    Statement statement = connection.createStatement("select * from test_user where id = ?");
                    statement.bind(0, id);
                    return statement.execute();
                }).flatMap(result -> result
                        .map(
                                (row, rowMetadata) -> {
                                    UserDO userDO = new UserDO();
                                    userDO.setId(row.get("id", Long.class));
                                    userDO.setName(row.get("name", String.class));
                                    return userDO;
                                }
                        )
                ).last();

        // TODO
        // 1. 多次查询如何组合处理逻辑？
        // 2. 学习使用 DatabaseClient
        return userDOMono;
    }

}
