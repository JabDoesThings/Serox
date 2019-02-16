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

import jab.subspace.serox.map.Lvl;
import jab.subspace.serox.map.Tileset;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * @author Jab
 */
public class MapRendererNormal extends MapRenderer {

  @NotNull
  @Override
  public BufferedImage render(Lvl lvl, int x1, int y1, int x2, int y2) {
    int[] distance = getDistance(x1, y1, x2, y2);
    int width = distance[0] + 16;
    int height = distance[1] + 16;
    BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
    Graphics g = finalImage.getGraphics();
    g.setColor(Color.black);
    g.fillRect(0, 0, width, height);
    for (int y = y1; y <= y2; y++) {
      for (int x = x1; x <= x2; x++) {
        int tile = lvl.getTile(x, y);
        if (!Tileset.isException(tile)) {
          BufferedImage tileImage = lvl.getTileset().getImage(tile);
          if (tileImage != null) {
            for (int tileY = 0; tileY < 16; tileY++) {
              for (int tileX = 0; tileX < 16; tileX++) {
                try {
                  finalImage.setRGB(
                      (((x - x1) * 16) + tileX),
                      (((y - y1) * 16) + tileY),
                      tileImage.getRGB(tileX, tileY));
                } catch (ArrayIndexOutOfBoundsException e) {
                  printDebugInfo(x, y, tileX, tileY);
                  e.printStackTrace(System.err);
                  System.exit(-2);
                }
              }
            }
          }
        }
      }
    }
    return finalImage;
  }

  @NotNull
  @Override
  public String getName() {
    return "Normal";
  }
}
