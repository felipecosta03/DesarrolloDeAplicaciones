package com.example.desarrollodeaplicaciones.configs;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
public class DataSourceConfig {

  @Autowired private Environment env;

  @Bean
  @Profile("prod")
  public DataSource getDataSource() {
    return DataSourceBuilder.create()
        .password(env.getProperty("DATABASE_PASSWORD"))
        .driverClassName("org.postgresql.Driver")
        .url(
            String.format(
                "jdbc:postgresql://%s/%s",
                env.getProperty("DATABASE_URL"), env.getProperty("DATABASE_NAME")))
        .driverClassName("org.postgresql.Driver")
        .username(env.getProperty("DATABASE_USER"))
        .build();
  }

  @Bean
  @Profile("!prod")
  public DataSource getDataSourceLocal() {
    return DataSourceBuilder.create()
        .password(env.getProperty("DATABASE_PASSWORD"))
        .url(
            String.format(
                "jdbc:mysql://localhost:3306/%s?createDatabaseIfNotExist=true",
                env.getProperty("DATABASE_NAME")))
        .username(env.getProperty("DATABASE_USER"))
        .driverClassName("com.mysql.cj.jdbc.Driver")
        .build();
  }
}
