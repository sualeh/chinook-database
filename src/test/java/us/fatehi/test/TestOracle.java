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
import static java.nio.file.Files.createTempFile;
import static java.util.stream.Collectors.joining;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

// @EnabledIfSystemProperty(named = "heavydb", matches = "^((?!(false|no)).)*$")
@Testcontainers(disabledWithoutDocker = true)
public class TestOracle
{

  @Container
  private final JdbcDatabaseContainer dbContainer =
    new OracleContainer("wnameless/oracle-xe-11g-r2");

  @BeforeEach
  public void _createChinookDatabase()
    throws SQLException, IOException
  {
    final Connection connection = dbContainer.createConnection("");

    final BufferedReader reader =
      new BufferedReader(new InputStreamReader(new ClassPathResource(
        "chinook_database/Chinook_Oracle.sql").getInputStream(), UTF_8));
    final String sqlScript = reader
      .lines()
      .filter(line -> !line.matches("conn chinook.*"))
      .collect(joining("\n"));
    final Path sqlFile =
      Files.write(createTempFile("chinook", ".sql"), sqlScript.getBytes(UTF_8));

    final EncodedResource chinookSql =
      new EncodedResource(new PathResource(sqlFile), UTF_8);

    executeSqlScript(connection,
                     chinookSql,
                     true,
                     true,
                     "--",
                     ";",
                     "/*",
                     "*/");
  }

  @Test
  public void oracle()
    throws SQLException
  {
    final Connection connection = dbContainer.createConnection("");
    assertThat(connection, is(not(nullValue())));
    assertThat(connection.isClosed(), is(false));

    final JdbcTemplate jdbcTemplate =
      new JdbcTemplate(new SingleConnectionDataSource(connection, true));
    final Integer count = jdbcTemplate.queryForObject(
      "SELECT COUNT(*) FROM Album",
      Integer.class);
    assertThat(count, is(not(nullValue())));
    assertThat(count, is(347));
  }

}
