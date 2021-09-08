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

import static us.fatehi.chinook_database.ChinookDatabaseUtils.createChinookDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.Callable;

import picocli.CommandLine;

@CommandLine.Command(
    description = "Creates the Chinook database schema on a server",
    name = "Chinook Database Creator",
    mixinStandardHelpOptions = true)
public class ChinookDatabaseCreator implements Callable<Integer> {

  public static int call(String... args) {
    final int exitCode = new CommandLine(new ChinookDatabaseCreator()).execute(args);
    return exitCode;
  }

  public static void main(String... args) {
    System.exit(call(args));
  }

  @CommandLine.Option(
      names = {"--url"},
      required = true,
      description = "JDBC connection URL to the database",
      paramLabel = "<url>")
  private String connectionUrl;

  @CommandLine.Option(
      names = {"--user"},
      description = "Database user name",
      paramLabel = "<user>")
  private String user;

  @CommandLine.Option(
      names = {"--password"},
      description = "Database password",
      paramLabel = "<password>")
  private String passwordProvided;

  private ChinookDatabaseCreator() {}

  @Override
  public Integer call() {
    try (final Connection connection =
        DriverManager.getConnection(connectionUrl, user, passwordProvided); ) {
      final DatabaseType server = DatabaseType.fromConnectionUrl(connectionUrl);
      createChinookDatabase(server, connection);
    } catch (final Exception e) {
      e.printStackTrace();
      return 1;
    }
    return 0;
  }
}
