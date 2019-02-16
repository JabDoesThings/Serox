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

import jab.subspace.serox.map.Lvl;
import jab.subspace.serox.render.*;
import jab.subspace.serox.render.MapRendererAlphaNoBlack;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/** @author Jab */
@SuppressWarnings("WeakerAccess")
public class Serox {

  public static boolean silent = true;

  private MapRendererNormal renderNormal;
  private MapRendererAlpha renderAlpha;
  private MapRendererAlphaNoBlack renderAlphaNoBlack;
  private MapRendererSpecialMask renderSpecialMask;
  private MapRendererAlphaSpecialMask renderAlphaSpecialMask;
  private MapRendererMask renderMask;

  public Lvl lvl;

  /** Renderer constructor. */
  private Serox() {
    renderMask = new MapRendererMask();
    renderSpecialMask = new MapRendererSpecialMask();
    renderAlphaNoBlack = new MapRendererAlphaNoBlack();
    renderAlpha = new MapRendererAlpha();
    renderNormal = new MapRendererNormal();
    renderAlphaSpecialMask = new MapRendererAlphaSpecialMask();
  }

  /**
   * Main constructor.
   *
   * @param lvl The LVL file to read.
   */
  public Serox(@NotNull File lvl) {
    this();
    this.lvl = new Lvl(lvl);
  }

  /**
   * MapRenderer the lvl with the applicable renders.
   *
   * @param saveFolder The folder to save the image.
   * @param x1 The top-left x coordinate.
   * @param y1 The top-left y coordinate.
   * @param x2 The bottom-right x coordinate.
   * @param y2 The bottom-right y coordinate.
   */
  public void render(@NotNull File saveFolder, int x1, int y1, int x2, int y2) {
    System.out.println(
        "Rendering "
            + lvl.getFile()
            + "... (x1: "
            + x1
            + " y1: "
            + y1
            + " x2: "
            + x2
            + " y2: "
            + y2
            + " width: "
            + (x2 - x1)
            + ", height: "
            + (y2 - x1)
            + ")");
    renderNormal.render(lvl, saveFolder, x1, y1, x2, y2);
    renderAlpha.render(lvl, saveFolder, x1, y1, x2, y2);
    renderAlphaNoBlack.render(lvl, saveFolder, x1, y1, x2, y2);
    if (lvl.hasMask()) {
      renderSpecialMask.render(lvl, saveFolder, x1, y1, x2, y2);
      renderAlphaSpecialMask.render(lvl, saveFolder, x1, y1, x2, y2);
    }
    renderMask.render(lvl, saveFolder, x1, y1, x2, y2);
  }
}
