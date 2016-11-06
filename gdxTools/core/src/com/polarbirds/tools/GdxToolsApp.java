package com.polarbirds.tools;

import com.badlogic.gdx.ApplicationAdapter;
import com.polarbirds.tools.tmxconverter.ConverterApp;

/**
 * Created by Harald on 24.5.15.
 */
public class GdxToolsApp extends ApplicationAdapter {

  @Override
  public void create() {
    ConverterApp.run();
  }
}