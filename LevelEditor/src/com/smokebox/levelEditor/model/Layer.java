package com.smokebox.levelEditor.model;

import com.smokebox.levelEditor.view.MainViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.GraphicsContext;
import org.controlsfx.dialog.Dialogs;

/**
 * Created by Harald Wilhelmsen on 8/27/2014.
 */
public interface Layer {

    public void draw(GraphicsContext gc);

    public void edit(int x, int y);

    public StringProperty getNameProperty();
}