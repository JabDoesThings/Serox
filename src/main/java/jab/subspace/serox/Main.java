/*
 * Serox Copyright (c) Jab, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version
 * 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library.
 */
package jab.subspace.serox;

import java.io.File;

/** @author Jab */
public class Main {

  public static void main(String args[]) {
    File lvlFile;
    File saveFolder = new File("");
    int x1, y1, x2, y2;
    // Make sure the arguments are correct.
    System.out.println("Serox V2.00. Created by Jab.");
    if (args.length < 5 || args.length > 6) {
      System.out.println("Usage: [x1] [y1] [x2] [y2] [lvl] [save dir*] (* fields are optional)");
      System.out.println("NOTE: coordinates can only be 0-1023.");
      return;
    }
    try {
      x1 = Integer.parseInt(args[0]);
    } catch (Exception e) {
      throw new IllegalStateException("x1 coordinate cannot be " + args[0]);
    }
    try {
      y1 = Integer.parseInt(args[1]);
    } catch (Exception e) {
      throw new IllegalStateException("y1 coordinate cannot be " + args[1]);
    }
    try {
      x2 = Integer.parseInt(args[2]);
    } catch (Exception e) {
      throw new IllegalStateException("x2 coordinate cannot be " + args[2]);
    }
    try {
      y2 = Integer.parseInt(args[3]);
    } catch (Exception e) {
      throw new IllegalStateException("y2 coordinate cannot be " + args[3]);
    }
    lvlFile = new File(args[4]);
    if (!lvlFile.exists()) {
      throw new IllegalStateException("LVL file not found: " + args[4]);
    }
    try {
      if (args.length == 6) {
        if (!args[5].endsWith("\\") && !args[5].endsWith("/")) {
          saveFolder =
              new File(
                  args[5]
                      + "/"
                      + lvlFile.getName().substring(0, lvlFile.getName().length() - 4)
                      + "\\");
          if (!saveFolder.isDirectory()) {
            throw new IllegalStateException(
                "Invalid save folder directory: " + saveFolder.getAbsolutePath() + " .");
          }
        } else {
          saveFolder =
              new File(
                  args[5] + lvlFile.getName().substring(0, lvlFile.getName().length() - 4) + "\\");
        }
        if (!saveFolder.exists()) {
          saveFolder.mkdirs();
        }
      } else {
        saveFolder = lvlFile.getParentFile();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    Serox.silent = false;
    // Read the lvl file(s) and contents.
    Serox serox = new Serox(lvlFile);
    // MapRenderer and save the images.
    serox.render(saveFolder, x1, y1, x2, y2);
  }
}
