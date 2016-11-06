package com.polarbirds.tools.tmxconverter;

import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Created by Harald on 24.5.15.
 */
class MapWithName {

  TiledMap map;
  String filename;

  MapWithName(TiledMap map, String filename) {
    this.map = map;
    this.filename = filename;
  }
}
