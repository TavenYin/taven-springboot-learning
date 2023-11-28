package com.github.taven.springboot3ebean.config;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.config.MatchingNamingConvention;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EbeanConfiguration {

    @Bean
    public Database database(CurrentUser currentUser) {
        DatabaseConfig config = new DatabaseConfig();
        // config.setName("db"); db is the default name
        config.setCurrentUserProvider(currentUser);
        config.loadFromProperties();
        // 默认会将 hello_world 映射到 entity 的 helloWorld
        // 使用 MatchingNamingConvention 数据库和 entity 命名一致
        config.setNamingConvention(new MatchingNamingConvention());
        return DatabaseFactory.create(config);
    }

}
