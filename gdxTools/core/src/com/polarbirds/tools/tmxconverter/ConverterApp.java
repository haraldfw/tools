package com.polarbirds.tools.tmxconverter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class ConverterApp {

  public static void run() {
    DirectoryChooser chooser = new DirectoryChooser();
    Converter converter =
        new Converter(chooser.getLayers(), chooser.getTextures(), chooser.getOutputDirectory());
    converter.convert();
    converter.dispose();
    Gdx.app.exit();
  }
}
