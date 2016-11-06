package com.polarbirds.tools.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.polarbirds.tools.GdxToolsApp;
import com.polarbirds.tools.tmxconverter.ConverterApp;

public class ConverterLauncher {

  public static void main(String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.width = 1;
    config.height = 1;
    config.resizable = false;
    new LwjglApplication(new GdxToolsApp(), config);
  }
}
