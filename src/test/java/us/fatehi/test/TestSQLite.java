/*
========================================================================
Chinook Database
http://www.schemacrawler.com
Copyright (c) 2020-21, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

This software is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

This software and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

========================================================================
*/
package us.fatehi.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static us.fatehi.chinook_database.SQLiteGenerator.createSQLiteChinookDatabase;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.testcontainers.junit.jupiter.Testcontainers;

@EnabledIfSystemProperty(named = "heavydb", matches = "^((?!(false|no)).)*$")
@Testcontainers(disabledWithoutDocker = true)
public class TestSQLite {

  @Test
  public void sqlite() throws SQLException, IOException {
    final Path chinookDatabasePath = createSQLiteChinookDatabase();

    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setUrl("jdbc:sqlite:" + chinookDatabasePath);

    final JdbcTemplate jdbcTemplate =
        new JdbcTemplate(new SingleConnectionDataSource(dataSource.getConnection(), true));
    final Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Album", Integer.class);
    assertThat(count, is(not(nullValue())));
    assertThat(count, is(347));
  }
}
