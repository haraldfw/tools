package com.polarbirds.tools.tmxconverter;

import com.badlogic.gdx.graphics.Texture;

import java.nio.ByteBuffer;

/**
 * Created by Harald on 24.5.15.
 */
final class TextureWithInfo {

  final Texture texture;
  final ByteBuffer byteBuffer;
  final String tile;

  public TextureWithInfo(Texture texture, String tile) {
    this.texture = texture;
    this.tile = tile;
    texture.getTextureData().prepare();
    byteBuffer = texture.getTextureData().consumePixmap().getPixels();
  }
}
