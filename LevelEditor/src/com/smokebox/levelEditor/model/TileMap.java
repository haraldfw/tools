package com.smokebox.levelEditor.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Created by Harald Wilhelmsen on 8/26/2014.
 */
public class TileMap {

    private ArrayList<Layer> layers;
    private boolean[][] collision;

    private ArrayList<Image> tileset;
    private float tileSize = 32;

    private float zoom = 1;
    private float tx = 0;
    private float ty = 0;

    public TileMap(int width, int height) {
        layers = new ArrayList<>();
        collision = new boolean[width][height];
    }

    public void draw(GraphicsContext gc) {
        for(Layer l : layers) l.draw(gc);
        for (int i = 0; i < collision.length; i++) {
            for (int j = 0; j < collision[0].length; j++) {
                if(collision[i][j]) gc.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
            }
        }
    }

    public void transform(float x, float y) {
        tx += x;
        ty += y;
    }

    public void transformZoomAdd(float val) {
        zoom += val;
        if(zoom <= 0) zoom = 0.01f;
    }
}
