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
package us.fatehi.chinook_database.resources;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.createTempFile;
import static java.util.stream.Collectors.joining;
import static us.fatehi.chinook_database.DatabaseType.oracle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.EncodedResource;

public class OracleResource implements Supplier<EncodedResource> {

  @Override
  public EncodedResource get() {
    try {
      final ClassPathResource oracleResource =
          new ClassPathResource(oracle.getClassPathResourcePath());
      final BufferedReader reader =
          new BufferedReader(new InputStreamReader(oracleResource.getInputStream(), UTF_8));
      final String sqlScript =
          reader.lines().filter(line -> !line.matches("conn chinook.*")).collect(joining("\n"));
      final Path sqlFile =
          Files.write(createTempFile("chinook", ".sql"), sqlScript.getBytes(UTF_8));

      return new EncodedResource(new PathResource(sqlFile), UTF_8);
    } catch (final Exception e) {
      throw new UnsupportedOperationException("Cannot load Oracle resource", e);
    }
  }
}
