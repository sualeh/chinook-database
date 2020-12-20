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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class TestResources {

  @Test
  public void checkResources() throws IOException {

    Arrays.stream(
            new String[] {
              "Chinook_Db2.sql",
              "Chinook_MySql.sql",
              "Chinook_MySql_AutoIncrementPKs.sql",
              "Chinook_Oracle.sql",
              "Chinook_PostgreSql.sql",
              "Chinook_Sqlite.sql",
              "Chinook_Sqlite_AutoIncrementPKs.sql",
              "Chinook_SqlServer.sql",
              "Chinook_SqlServer_AutoIncrementPKs.sql",
            })
        .forEach(
            classPathResource -> {
              final Resource chinookSql =
                  new ClassPathResource("chinook-database/" + classPathResource);
              System.out.println(chinookSql);
              assertThat(classPathResource, chinookSql, is(not(nullValue())));
              assertThat(classPathResource, chinookSql.exists(), is(true));
              assertThat(classPathResource, chinookSql.isReadable(), is(true));
              try {
                assertThat(classPathResource, chinookSql.contentLength(), is(greaterThan(0L)));
              } catch (final IOException e) {
                fail(e);
              }
            });
  }
}
