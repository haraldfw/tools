package com.polarbirds.tools.tmxconverter;

import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;

/**
 * Created by Harald on 23.5.15.
 */
public class Converter {

  MapWithName[] layers;
  TextureWithInfo[] texturesWithInfo;
  String outputDir;

  public Converter(MapWithName[] layers, TextureWithInfo[] texturesWithInfo,
                   String outputDir) {
    this.layers = layers;
    this.texturesWithInfo = texturesWithInfo;
    this.outputDir = outputDir;
  }

  void dispose() {
    for (MapWithName mapWithName : layers) {
      mapWithName.map.dispose();
    }
    for (TextureWithInfo textureWithInfo : texturesWithInfo) {
      textureWithInfo.texture.dispose();
    }
  }

  void convert() {
    for (MapWithName mapWithName : layers) {
      PrintWriter writer = null;
      try {
        writer =
            new PrintWriter(outputDir + "/" + mapWithName.filename + ".sec");
        TiledMapTileLayer map = (TiledMapTileLayer) mapWithName.map.getLayers().get(0);

        int width = map.getWidth();
        int height = map.getHeight();

        writer.println(width);
        writer.println(height);

        for (int y = height - 1; y >= 0; y--) {
          StringBuffer buffer = new StringBuffer(width);
          for (int x = 0; x < width; x++) {
            TextureData data = map.getCell(x, y).getTile().getTextureRegion().getTexture()
                .getTextureData();
            data.prepare();
            String s = getTile(data.consumePixmap().getPixels());
            buffer.append(s);
          }
          writer.println(buffer);
        }

        MapProperties properties = mapWithName.map.getProperties();
        writer.println((String) properties.get("LEFT"));
        writer.println((String) properties.get("RIGHT"));
        writer.println((String) properties.get("TOP"));
        writer.println((String) properties.get("BOTTOM"));

        printLocations((String) properties.get("SPAWN"), writer);
        printLocations((String) properties.get("CHEST"), writer);
        printLocations((String) properties.get("ENEMY"), writer);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } finally {
        if (writer != null) {
          System.out.println("Converted file '" + mapWithName.filename + "'");
          writer.close();
        }
      }
    }

    System.out.println("Conversion of " + layers.length + " files complete!");
  }

  private void printLocations(String s, PrintWriter writer) {
    if(s != null) {
      writer.println(s);
    } else {
      writer.println();
    }
  }

  private String getTile(ByteBuffer tileBytes) {
    for (TextureWithInfo textureWithInfo : texturesWithInfo) {
      if (tileBytes.compareTo(textureWithInfo.byteBuffer) == 0) {
        return textureWithInfo.tile;
      }
    }
    return "i";
  }
}
