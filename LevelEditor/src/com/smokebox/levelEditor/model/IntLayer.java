package com.smokebox.levelEditor.model;

import com.smokebox.levelEditor.view.MainViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by Harald Wilhelmsen on 9/4/2014.
 */
public class IntLayer implements Layer {

    int[][] graphics;

    private MainViewController controller;

    StringProperty name;

    public IntLayer(String name, MainViewController controller) {
        this.name = new SimpleStringProperty(name);
        this.controller = controller;
        graphics = new int[controller.getWidth()][controller.getHeight()];
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setGlobalAlpha(1);
        int tileSize = controller.getTileSize();
        for (int i = 0; i < graphics.length; i++) {
            for (int j = 0; j < graphics[0].length; j++) {
                Image img = controller.tileSet.get(graphics[i][j]);
                if(img != null) gc.drawImage(img, i * tileSize, j * tileSize, tileSize, tileSize);
            }
        }
    }

    @Override
    public void edit(int x, int y) {
        graphics[x][y] = controller.getActiveTile();
    }

    @Override
    public StringProperty getNameProperty() {
        return name;
    }
}
