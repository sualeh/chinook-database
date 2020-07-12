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
import static us.fatehi.chinook_database.DatabaseType.oracle;
import static us.fatehi.test.utility.TestUtils.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@EnabledIfSystemProperty(named = "heavydb", matches = "^((?!(false|no)).)*$")
@Testcontainers(disabledWithoutDocker = true)
public class TestOracle
{

  @Container
  private final JdbcDatabaseContainer dbContainer =
    new OracleContainer("wnameless/oracle-xe-11g-r2");

  @Test
  public void oracle()
    throws SQLException
  {
    System.setProperty("oracle.jdbc.timezoneAsRegion", Boolean.FALSE.toString());
    test(dbContainer, oracle, "Album", 347);
  }

}
