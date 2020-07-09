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


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.logging.Level;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers(disabledWithoutDocker = true)
public class TestSqlServer
{

  @Container
  private JdbcDatabaseContainer dbContainer = new MSSQLServerContainer<>();

  @Test
  public void sqlServer()
    throws SQLException
  {
    final DataSource dataSource = createDataSource(dbContainer);
    assertThat(dataSource.getConnection(), is(not(nullValue())));
  }

  private static DataSource createDataSource(final JdbcDatabaseContainer dbContainer)
  {
    final BasicDataSource ds = new BasicDataSource();
    ds.setUrl(dbContainer.getJdbcUrl());
    ds.setUsername(dbContainer.getUsername());
    ds.setPassword(dbContainer.getPassword());
    // ds.setDefaultAutoCommit(false);

    return ds;
  }

}
