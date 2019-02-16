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
package jab.subspace.serox.map;

import jab.subspace.serox.util.BMPLoader;
import jab.subspace.serox.util.ByteArray;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/** @author Jab */
abstract class LvlReader {

  /**
   * Reads the tiles from a LVL file.
   *
   * @param lvl The LVL file to read.
   * @return Returns the tile lvl array with the tile IDs as shorts.
   */
  public static short[][] readMap(File lvl) {
    short[][] tiles = new short[1024][1024];
    try {
      DataInputStream dis = new DataInputStream(new FileInputStream(lvl));
      readIn(dis, 2);
      byte[] uint32 = readIn(dis, 4);
      int readInSize = ByteBuffer.wrap(uint32).order(ByteOrder.LITTLE_ENDIAN).getInt();
      readIn(dis, readInSize - 2);
      while (dis.available() >= 4) {
        byte[] b = readIn(dis, 4);
        ByteArray array = new ByteArray(b);
        int i = array.readLittleEndianInt(0);
        short tile = (short) (i >> 24 & 0x00ff);
        int y = (i >> 12) & 0x03FF;
        int x = i & 0x03FF;
        tiles[x][y] = tile;
      }
    } catch (IOException e) {
      System.out.println("Lvl File Could not be read.");
      e.printStackTrace();
    }
    return tiles;
  }

  public static BufferedImage readTileset(File lvl) {
    BufferedImage tileset = null;
    DataInputStream dis;
    try {
      dis = new DataInputStream(new FileInputStream(lvl));
      Image image = BMPLoader.instance.getBMPImage(dis);
      BMPLoader.instance.setInputStream(dis);
      tileset = new BufferedImage(304, 160, BufferedImage.TYPE_INT_RGB);
      Graphics2D bufImageGraphics = tileset.createGraphics();
      bufImageGraphics.drawImage(image, 0, 0, null);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tileset;
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  public static byte[] readIn(DataInputStream dis, int n) {
    byte[] b = new byte[n];
    try {
      dis.read(b);
      return b;
    } catch (IOException e) {
      e.printStackTrace(System.err);
      return new byte[0];
    }
  }
}
