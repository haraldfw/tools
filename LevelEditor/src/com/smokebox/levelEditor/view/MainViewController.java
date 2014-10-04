package com.smokebox.levelEditor.view;

import com.smokebox.levelEditor.MainApp;
import com.smokebox.levelEditor.model.*;
import com.smokebox.levelEditor.model.Layer.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.controlsfx.dialog.Dialogs;

import java.util.ArrayList;

public class MainViewController {

    @FXML
    TableView<Layer> layerTable;
    @FXML
    TableColumn<Layer, String> layersColumn;

    @FXML
    TableView<Image> tilesTable;
    @FXML
    TableColumn<Image, String> tilesColumn;

    @FXML
    StackPane canvasPane;
    ResizableCanvas canvas;

    @FXML
    HBox toolBox;

    private float renderScale;
    private float shiftX;
    private float shiftY;

    private IntegerProperty mapWidth;
    private IntegerProperty mapHeight;
    private IntegerProperty tileSize;
    public ObservableList<Image> tileSet;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * Is called before the initialize() method.
     */
    public MainViewController() {
        
    }

    /**
     * Initializes the controller class. This method is 
     * automatically called when the fxml-file is loaded.
     */
    @FXML
    private void initialize() {
        layersColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        //tilesColumn.setCellFactory(

        mapWidth = new SimpleIntegerProperty(10);
        mapHeight = new SimpleIntegerProperty(10);
        tileSize = new SimpleIntegerProperty(50);

        canvas = new ResizableCanvas(mapWidth, mapHeight, this);
        canvasPane.getChildren().add(0, canvas);
        canvas.widthProperty().bind(canvasPane.widthProperty());
        canvas.heightProperty().bind(canvasPane.heightProperty());
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                canvas.edit(event);
                canvas.draw();
            }
        });
        layerTable.setItems(canvas.layers);
        tilesTable.setItems(tileSet);

        tileSet = FXCollections.observableArrayList();
        tileSet.add(new Image("file:D:/GoogleDrive/Dev/Valkyrie/Environments/testTile1.png"));

        NumberField mw = new NumberField(mapWidth);
        mw.setPrefWidth(80);
        NumberField mh = new NumberField(mapHeight);
        mh.setPrefWidth(80);
        NumberField ts = new NumberField(tileSize);
        ts.setPrefWidth(80);
        toolBox.getChildren().add(1, mw);
        toolBox.getChildren().add(3, mh);
        toolBox.getChildren().add(5, ts);

        layersColumn.prefWidthProperty().bind(layerTable.widthProperty());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public int getActiveTile() {
        return tilesTable.getSelectionModel().getSelectedIndex();
    }

    @FXML
    public void addIntLayer() {
        canvas.addLayer(new IntLayer("New IntLayer", this));
        System.out.println("IntLayer added");
    }

    @FXML
    public void addBooleanLayer() {
        canvas.addLayer(new BooleanLayer("New BooleanLayer", this));
    }

    public Layer getActiveLayer() {
        Layer l = layerTable.getSelectionModel().getSelectedItem();
        return l;
    }

    public int getWidth() {
        return mapWidth.get();
    }

    public int getHeight() {
        return mapHeight.get();
    }

    public int getTileSize() {
        return tileSize.get();
    }
}
