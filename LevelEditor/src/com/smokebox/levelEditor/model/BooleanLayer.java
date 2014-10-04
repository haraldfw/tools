package com.smokebox.levelEditor.model;

import com.smokebox.levelEditor.view.MainViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by Harald Wilhelmsen on 9/4/2014.
 */
public class BooleanLayer implements Layer {

    StringProperty name;

    boolean[][] graphics;
    private MainViewController canvas;

    public BooleanLayer(String name, MainViewController canvas) {
        this.name = new SimpleStringProperty(name);
        graphics = new boolean[canvas.getWidth()][canvas.getHeight()];
        this.canvas = canvas;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setGlobalAlpha(0.5);
        int tileSize = canvas.getTileSize();
        for (int i = 0; i < graphics.length; i++) {
            for (int j = 0; j < graphics[0].length; j++) {
                if(graphics[i][j]) gc.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
            }
        }
    }

    @Override
    public void edit(int x, int y) {
        graphics[x][y] = canvas.getActiveTile() > 0;
    }

    @Override
    public StringProperty getNameProperty() {
        return name;
    }
}
