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
package us.fatehi.chinook_database;

import static java.util.Objects.requireNonNull;
import static us.fatehi.chinook_database.DatabaseType.sqlite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class SQLiteGenerator {

  public static Path createSQLiteChinookDatabase() throws IOException, SQLException {
    return createSQLiteChinookDatabase(Files.createTempFile("chinook-database", ".db"));
  }

  public static Path createSQLiteChinookDatabase(final Path chinookDatabasePath)
      throws SQLException {
    requireNonNull(chinookDatabasePath, "No database path provided");

    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setUrl("jdbc:sqlite:" + chinookDatabasePath);

    ChinookDatabaseUtils.createChinookDatabase(sqlite, dataSource.getConnection());

    return chinookDatabasePath;
  }

  public static void main(String[] args) throws Exception {
    final Path sqLiteChinookDatabasePath;
    if (args == null || args.length == 0) {
      sqLiteChinookDatabasePath = createSQLiteChinookDatabase();
    } else {
      final Path path = Paths.get(args[0]);
      sqLiteChinookDatabasePath = createSQLiteChinookDatabase(path);
    }

    Version.main(args);
    System.out.println(sqLiteChinookDatabasePath);
  }
}
