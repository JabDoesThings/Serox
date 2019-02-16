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

import java.io.File;

/** @author Jab */
@SuppressWarnings("unused")
public class Lvl {

  private short[][] tiles;
  private Tileset tileset;
  private File file;
  private Lvl mask;

  public Lvl(@NotNull File file) {
    this.file = file;
    tileset = loadTileset(file);
    tiles = LvlReader.readMap(file);
    String maskFilePath =
        file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4) + "_mask.lvl";
    File mask = new File(maskFilePath);
    if (mask.exists()) {
      try {
        this.mask = new Lvl(mask);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @NotNull
  public Tileset loadTileset(File file) {
    return new Tileset(file);
  }

  public Lvl getMask() {
    return this.mask;
  }

  public void setMask(Lvl mask) {
    this.mask = mask;
  }

  public boolean hasMask() {
    return this.mask != null;
  }

  public Tileset getTileset() {
    return this.tileset;
  }

  public short getTile(int x, int y) {
    return this.tiles[x][y];
  }

  public File getFile() {
    return this.file;
  }
}
