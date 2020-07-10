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

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@EnabledIfSystemProperty(named = "heavydb", matches = "^((?!(false|no)).)*$")
@Testcontainers(disabledWithoutDocker = true)
public class TestSqlServer
{

  @Container
  private final JdbcDatabaseContainer dbContainer =
    new MSSQLServerContainer<>();

  @BeforeEach
  public void _createChinookDatabase()
    throws SQLException
  {
    final Connection connection = dbContainer.createConnection("");
    final EncodedResource chinookSql =
      new EncodedResource(new ClassPathResource(
        "chinook_database/Chinook_SqlServer.sql"), UTF_8);
    executeSqlScript(connection,
                     chinookSql,
                     false,
                     true,
                     "#",
                     "GO",
                     "/*",
                     "*/");
  }

  @Test
  public void sqlServer()
    throws SQLException
  {
    final Connection connection = dbContainer.createConnection("");
    assertThat(connection, is(not(nullValue())));
    assertThat(connection.isClosed(), is(false));

    final JdbcTemplate jdbcTemplate =
      new JdbcTemplate(new SingleConnectionDataSource(connection, true));
    final Integer count = jdbcTemplate.queryForObject(
      "SELECT COUNT(*) FROM Chinook.dbo.Album",
      Integer.class);
    assertThat(count, is(not(nullValue())));
    assertThat(count, is(347));
  }

}
