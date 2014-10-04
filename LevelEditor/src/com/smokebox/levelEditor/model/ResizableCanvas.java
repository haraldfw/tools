package com.smokebox.levelEditor.model;

import com.smokebox.levelEditor.view.MainViewController;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.controlsfx.dialog.Dialogs;

/**
 * Tip 1: A canvas resizing itself to the size of
 *        the parent pane.
 */
public class ResizableCanvas extends Canvas {

    public ObservableList<Layer> layers;

    private IntegerProperty tilesWidth;
    private IntegerProperty tilesHeight;

    MainViewController controller;

    public ResizableCanvas(IntegerProperty w, IntegerProperty h, MainViewController controller) {
        // Redraw canvas when size changes.
        tilesWidth = w;
        tilesHeight = h;
        layers = FXCollections.observableArrayList();
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
        this.controller = controller;
    }

    public void draw() {
        double width = getWidth();
        double height = getHeight();

        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);

        int tileSize = controller.getTileSize();
        gc.setFill(Paint.valueOf("#990000"));
        gc.setGlobalAlpha(0.5f);
        for(int i = 0; i < tilesWidth.get(); i++)
            for(int j = 0; j < tilesHeight.get(); j++)
                gc.fillRect(i*tileSize, j*tileSize, tileSize, tileSize);
        for(Layer l : layers) l.draw(gc);
        gc.setStroke(Color.RED);
        gc.strokeLine(0, 0, width, height);
        gc.strokeLine(0, height, width, 0);
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }

    public void addLayer(Layer layer) {
        layers.add(0, layer);
        this.draw();
    }

    public void edit(MouseEvent event) {
        int tileSize = controller.getTileSize();
        try {
            controller.getActiveLayer().edit((int)(event.getX()/tileSize), (int)(event.getY()/tileSize));
            this.draw();
        } catch(Exception e) {
            Dialogs.create().title("Error").masthead("Could not complete edit-action. No active layer. \nPlease select a layer").showException(e);
        }
    }
}