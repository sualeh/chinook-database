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
package us.fatehi.chinook_database;


import static java.util.Objects.requireNonNull;
import static org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript;
import static us.fatehi.chinook_database.DatabaseType.sqlite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;

import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import us.fatehi.chinook_database.resources.DB2Resource;
import us.fatehi.chinook_database.resources.MySQLResource;
import us.fatehi.chinook_database.resources.OracleResource;
import us.fatehi.chinook_database.resources.PostgreSQLResource;
import us.fatehi.chinook_database.resources.SQLServerResource;
import us.fatehi.chinook_database.resources.SQLiteResource;

public class ChinookDatabaseUtils
{

  public static void createChinookDatabase(final DatabaseType databaseType,
                                           final Connection connection)
  {
    requireNonNull(databaseType, "No database type provided");
    requireNonNull(connection, "No connection provided");

    final EncodedResource chinookSql = getResource(databaseType).get();
    executeSqlScript(connection,
                     chinookSql,
                     true,
                     true,
                     "--",
                     databaseType.getScriptSeparator(),
                     "/*",
                     "*/");
  }

  public static Supplier<EncodedResource> getResource(final DatabaseType databaseType)
  {
    requireNonNull(databaseType, "No database type provided");

    switch (databaseType)
    {
      case db2:
        return new DB2Resource();
      case mysql:
        return new MySQLResource();
      case oracle:
        return new OracleResource();
      case postgresql:
        return new PostgreSQLResource();
      case sqlite:
        return new SQLiteResource();
      case sqlserver:
        return new SQLServerResource();
      default:
        throw new IllegalArgumentException();
    }
  }

  private ChinookDatabaseUtils()
  {
    // Prevent instantiation
  }

}
