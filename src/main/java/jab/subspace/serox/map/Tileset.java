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

import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;
import java.io.File;

/** @author Jab */
@SuppressWarnings({"WeakerAccess", "unused"})
public class Tileset {

  public static final short TILE_ID_DOOR1 = 162;
  public static final short TILE_ID_DOOR2 = 163;
  public static final short TILE_ID_DOOR3 = 164;
  public static final short TILE_ID_DOOR4 = 165;
  public static final short TILE_ID_DOOR5 = 166;
  public static final short TILE_ID_DOOR6 = 167;
  public static final short TILE_ID_DOOR7 = 168;
  public static final short TILE_ID_DOOR8 = 169;
  public static final short TILE_ID_FLAG = 170;
  public static final short TILE_ID_GOAL = 172;

  private BufferedImage tileset;
  private BufferedImage[] tiles;

  /**
   * Main constructor.
   *
   * @param lvl The LVL file to read.
   */
  Tileset(@NotNull File lvl) {
    // Read the bitmap tileset.
    this.tileset = LvlReader.readTileset(lvl);
    // Split it up.
    parseTiles();
  }

  /** Parses the tileset bitmap image to individual tiles. */
  private void parseTiles() {
    tiles = new BufferedImage[191];
    int count = 1;
    for (int y = 0; y < 10; y++) {
      for (int x = 0; x < 19; x++) {
        BufferedImage bi = tileset.getSubimage(x * 16, y * 16, 16, 16);
        tiles[count++] = bi;
      }
    }
  }

  /**
   * @param tile The ID of the tile.
   * @return Returns the image associated with the tile ID given.
   */
  public BufferedImage getImage(int tile) {
    return this.tiles[tile];
  }

  /**
   * @param tile The tile ID to test.
   * @return Returns true if the tile is special.
   */
  @SuppressWarnings("BooleanMethodIsAlwaysInverted")
  public static boolean isException(int tile) {
    return isDoor(tile) || isSpecial(tile) || tile == TILE_ID_FLAG || tile == TILE_ID_GOAL;
  }

  /**
   * @param tile The tile ID to test.
   * @return Returns true if the tile is a door tile.
   */
  public static boolean isDoor(int tile) {
    return tile >= 162 && tile <= 169;
  }

  /**
   * @param tile The tile ID to test.
   * @return Returns true if the tile is a special tile that is beyond the range of the SubSpace
   *     tileset range.
   */
  public static boolean isSpecial(int tile) {
    return tile > 190;
  }
}
