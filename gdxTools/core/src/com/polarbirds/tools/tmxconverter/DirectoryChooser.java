package com.polarbirds.tools.tmxconverter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

/**
 * Created by Harald on 24.5.15.
 */
class DirectoryChooser extends JFileChooser {

  DirectoryChooser() {
    setCurrentDirectory(new File("."));
    setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
  }

  private void openDialog(String title) {
    setDialogTitle(title);
    if (showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
      System.out.println("User cancelled. Exiting...");
      Gdx.app.exit();
      System.exit(0);
    }
  }

  String getOutputDirectory() {
    openDialog("Select an output directory");
    return getPath();
  }

  MapWithName[] getLayers() {
    openDialog("Select directory with tmx-files");

    File[] sectionFiles = getDirectoryContents();

    if (sectionFiles == null || sectionFiles.length == 0) {
      System.out.println("No sections found in selected directory. Exiting...");
      Gdx.app.exit();
    }

    ArrayList<MapWithName> layers = new ArrayList<>();

    for (File file : sectionFiles) {
      TmxMapLoader loader = new TmxMapLoader(new ExternalFileResolver());
      FileHandle fileHandle = new FileHandle(file.getAbsolutePath());
      TiledMap map = loader.load(fileHandle.toString());
      layers.add(new MapWithName(map, fileHandle.nameWithoutExtension()));
    }

    return layers.toArray(new MapWithName[layers.size()]);
  }

  TextureWithInfo[] getTextures() {
    openDialog("Select directory with textures");

    File[] textureFiles = getDirectoryContents();

    if (textureFiles == null || textureFiles.length == 0) {
      System.out.println("No textures found in selected location. Exiting...");
      Gdx.app.exit();
    }

    ArrayList<TextureWithInfo> textures = new ArrayList<>();
    for (File file : textureFiles) {
      Texture texture = new Texture(new FileHandle(file));
      textures.add(new TextureWithInfo(texture, file.getName().substring(0, 1)));
    }

    return textures.toArray(new TextureWithInfo[textures.size()]);
  }

  private File getFile() {
    return getSelectedFile();
  }

  private String getPath() {
    return getFile().getAbsolutePath();
  }

  private File[] getDirectoryContents() {
    return getFile().listFiles();
  }
}
