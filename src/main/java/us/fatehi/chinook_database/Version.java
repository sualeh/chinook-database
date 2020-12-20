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

/**
 * Version information for this product. Has methods to obtain information about the product, as
 * well as a main method, so it can be called from the command-line.
 *
 * @author Sualeh Fatehi
 */
public final class Version {

  private static final String ABOUT = "https://github.com/schemacrawler/chinook-database";
  private static final String PRODUCTNAME = "Chinook Database for Java";
  private static final String VERSION = "2.0.1";

  /**
   * Information about this product.
   *
   * @return Information about this product.
   */
  public static String about() {
    return ABOUT;
  }

  /**
   * Product name.
   *
   * @return Product name.
   */
  public static String getProductName() {
    return PRODUCTNAME;
  }

  /**
   * Product version number.
   *
   * @return Product version number.
   */
  public static String getVersion() {
    return VERSION;
  }

  /**
   * Main routine. Prints information about this product.
   *
   * @param args Arguments to the main routine - they are ignored.
   */
  public static void main(final String[] args) {
    System.out.printf("%s%n%s%n%s%n", getProductName(), getVersion(), about());
  }

  private Version() {
    // Prevent external instantiation
  }
}
