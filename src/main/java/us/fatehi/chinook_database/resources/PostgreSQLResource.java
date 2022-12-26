/*
========================================================================
Chinook Database
http://www.schemacrawler.com
Copyright (c) 2020-2023, Sualeh Fatehi <sualeh@hotmail.com>.
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
package us.fatehi.chinook_database.resources;

import static java.nio.charset.StandardCharsets.UTF_8;
import static us.fatehi.chinook_database.DatabaseType.postgresql;

import java.util.function.Supplier;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;

public class PostgreSQLResource implements Supplier<EncodedResource> {

  @Override
  public EncodedResource get() {
    return new EncodedResource(new ClassPathResource(postgresql.getClassPathResourcePath()), UTF_8);
  }
}
