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
package jab.subspace.serox.render;

import jab.subspace.serox.Serox;
import jab.subspace.serox.map.Lvl;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/** @author Jab */
@SuppressWarnings("WeakerAccess")
public abstract class MapRenderer {

  /**
   * Renders the lvl to a saved image file.
   *
   * @param lvl The lvl to render.
   * @param saveFolder The folder to save the image.
   * @param x1 The top-left x coordinate.
   * @param y1 The top-left y coordinate.
   * @param x2 The bottom-right x coordinate.
   * @param y2 The bottom-right y coordinate.
   */
  public void render(Lvl lvl, File saveFolder, int x1, int y1, int x2, int y2) {
    String fileName = getFileName(lvl.getFile());
    File file = new File(saveFolder, fileName);
    if (!Serox.silent) {
      System.out.print("Rendering " + getName() + "... ");
    }
    BufferedImage image = render(lvl, x1, y1, x2, y2);
    saveImage(file, image);
  }

  public void saveImage(File file, BufferedImage image) {
    try {
      ImageIO.write(image, "png", file);
      if (!Serox.silent) {
        System.out.println("saved.");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param lvlFile The original lvl file.
   * @return Returns the name of the file.
   */
  public String getFileName(File lvlFile) {
    return lvlFile.getName().substring(0, lvlFile.getName().length() - 4)
        + "_"
        + getName()
        + ".png";
  }

  /**
   * Renders the lvl to a image.
   *
   * @param lvl The lvl to render.
   * @param x1 The top-left x coordinate.
   * @param y1 The top-left y coordinate.
   * @param x2 The bottom-right x coordinate.
   * @param y2 The bottom-right y coordinate.
   * @return Returns the rendered image.
   */
  @NotNull
  public abstract BufferedImage render(Lvl lvl, int x1, int y1, int x2, int y2);

  /** @return Returns the name of the renderer. */
  @NotNull
  public abstract String getName();

  /**
   * @param x1 The top-left x coordinate.
   * @param y1 The top-left y coordinate.
   * @param x2 The bottom-right x coordinate.
   * @param y2 The bottom-right y coordinate.
   * @return Returns the pixel dimensions of the image.
   */
  @NotNull
  public static int[] getDistance(int x1, int y1, int x2, int y2) {
    return new int[] {(x2 * 16) - (x1 * 16), (y2 * 16) - (y1 * 16)};
  }

  static void printDebugInfo(int x, int y, int tileX, int tileY) {
    System.err.println("Debug Draw Information:");
    System.err.println("\tIn the image tile coordinate: " + '{' + x + ',' + y + '}');
    System.err.println("\tTile coordinate: " + '{' + tileX + ',' + tileY + '}');
    System.err.println("\tabs pixel: " + '{' + (x + tileX) + ',' + (y + tileY) + '}');
    System.err.println();
  }
}
