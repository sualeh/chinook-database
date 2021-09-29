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

import static us.fatehi.chinook_database.DatabaseType.mysql;
import static us.fatehi.test.utility.TestUtils.test;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@EnabledIfSystemProperty(named = "heavydb", matches = "^((?!(false|no)).)*$")
@Testcontainers(disabledWithoutDocker = true)
public class TestMySQL {

  @Container
  private final JdbcDatabaseContainer<?> dbContainer =
      new MySQLContainer<>(DockerImageName.parse("mysql").withTag("8.0.26"));

  @Test
  public void mySQL() throws SQLException {
    test(dbContainer, mysql, "`Album`", 347);
  }
}
