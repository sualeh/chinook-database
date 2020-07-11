/*
========================================================================
Chinook Database
http://www.schemacrawler.com
Copyright (c) 2020, Sualeh Fatehi <sualeh@hotmail.com>.
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


import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers(disabledWithoutDocker = true)
public class TestSQLite
{

  private Path sqliteDatabase;

  @BeforeEach
  public void _createChinookDatabase()
    throws SQLException, IOException
  {
    sqliteDatabase = Files.createTempFile("chinook", ".db");
    final Connection connection = dataSource().getConnection();
    final EncodedResource chinookSql =
      new EncodedResource(new ClassPathResource(
        "chinook_database/Chinook_Sqlite.sql"), UTF_8);
    executeSqlScript(connection,
                     chinookSql,
                     false,
                     true,
                     "--",
                     ";",
                     "/*",
                     "*/");
  }

  @Test
  public void sqlite()
    throws SQLException
  {
    final Connection connection = dataSource().getConnection();
    assertThat(connection, is(not(nullValue())));
    assertThat(connection.isClosed(), is(false));

    final JdbcTemplate jdbcTemplate =
      new JdbcTemplate(new SingleConnectionDataSource(connection, true));
    final Integer count =
      jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Album", Integer.class);
    assertThat(count, is(not(nullValue())));
    assertThat(count, is(347));
  }

  private DataSource dataSource()
  {
    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setUrl("jdbc:sqlite:" + sqliteDatabase);
    return dataSource;
  }

}
