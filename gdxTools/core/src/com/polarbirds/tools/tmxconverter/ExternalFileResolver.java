package com.polarbirds.tools.tmxconverter;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Harald on 24.5.15.
 */
public class ExternalFileResolver implements FileHandleResolver {

  @Override
  public FileHandle resolve(String fileName) {
    return new FileHandle(fileName);
  }
}
