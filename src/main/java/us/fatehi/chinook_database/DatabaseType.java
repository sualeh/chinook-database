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


public enum DatabaseType
{
  oracle("chinook-database/Chinook_Oracle.sql"),
  db2("chinook-database/Chinook_Db2.sql"),
  sqlserver("chinook-database/Chinook_SqlServer.sql", "GO"),
  sqlite("chinook-database/Chinook_Sqlite.sql"),
  mysql("chinook-database/Chinook_MySql.sql"),
  postgresql("chinook-database/Chinook_PostgreSql.sql");

  private final String classPathResourcePath;
  private final String scriptSeparator;

  DatabaseType(final String classPathResourcePath, final String scriptSeparator)
  {
    this.classPathResourcePath = classPathResourcePath;
    this.scriptSeparator = scriptSeparator;
  }

  DatabaseType(final String classPathResourcePath)
  {
    this(classPathResourcePath, ";");
  }

  public String getClassPathResourcePath()
  {
    return classPathResourcePath;
  }

  public String getScriptSeparator()
  {
    return scriptSeparator;
  }

}
