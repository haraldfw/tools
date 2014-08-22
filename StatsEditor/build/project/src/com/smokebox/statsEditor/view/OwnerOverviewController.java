package com.smokebox.statsEditor.view;

import org.controlsfx.dialog.Dialogs;

import com.smokebox.statsEditor.MainApp;
import com.smokebox.statsEditor.model.StatsOwner;
import com.smokebox.statsEditor.model.Stat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class OwnerOverviewController {
	@FXML
    private TableView<StatsOwner> ownerTable;
    @FXML
    private TableColumn<StatsOwner, String> ownerColumn;
    
    @FXML
    TableView<Stat> statTable;
    @FXML
    TableColumn<Stat, String> statNameColumn;
    @FXML
    TableColumn<Stat, Float> statValueColumn;

    @FXML
    private TextField nameField;
    

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * Is called before the initialize() method.
     */
    public OwnerOverviewController() {
    }

    /**
     * Initializes the controller class. This method is 
     * automatically called when the fxml-file isloaded.
     */
    @FXML
    private void initialize() {
        // Initialize the stats-table with the two columns.
        ownerColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        
     // Clear stats.
        showOwnerDetails(null);

        // Listen for selection changes and show the stats when changed.
        ownerTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showOwnerDetails(newValue));

    }
    
    private void showOwnerDetails(StatsOwner owner) {
    	if(owner != null) {
    		statTable.setEditable(true);

            statNameColumn.setCellValueFactory(new PropertyValueFactory<Stat, String>("statName"));
            statNameColumn.setCellFactory(
  	            new Callback<TableColumn<Stat, String>, TableCell<Stat, String>>() {
	                public TableCell<Stat, String> call(TableColumn<Stat, String> p) {
	                    return new EditingCellString();
	                }
            });
            statNameColumn.setOnEditCommit(
            		new EventHandler<TableColumn.CellEditEvent<Stat, String>>() {
    					@Override
    					public void handle(CellEditEvent<Stat, String> t) {
    						((Stat) t.getTableView().getItems().get(
    								t.getTablePosition().getRow()
    								)).setStatName(t.getNewValue());
    					}
    				}
            	);
            statValueColumn.setCellValueFactory(new PropertyValueFactory<Stat, Float>("value"));
            statValueColumn.setCellFactory(
  	              new Callback<TableColumn<Stat, Float>, TableCell<Stat, Float>>() {
                public TableCell<Stat, Float> call(TableColumn<Stat, Float> p) {
                    return new EditingCellFloat();
                }
            });
            statValueColumn.setOnEditCommit(
            		new EventHandler<TableColumn.CellEditEvent<Stat, Float>>() {
    					@Override
    					public void handle(CellEditEvent<Stat, Float> t) {
    						((Stat) t.getTableView().getItems().get(
    								t.getTablePosition().getRow()
    								)).setValue(t.getNewValue());
    					}
    				}
            	);
            
            
            nameField.setText(owner.getName());
            nameField.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent a) {
							owner.setName(nameField.getText());
						}
    				}
            	);
            
            statTable.setItems(owner.getStats());
        } else {
        	nameField.setText("");
        	statTable.setItems(null);
        }
    }
    
    @FXML
    private void handleDeleteOwner() {
        int selectedIndex = ownerTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            ownerTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Dialogs.create()
                .title("No Selection")
                .masthead("No Owner Selected")
                .message("Please select an owner in the table.")
                .showWarning();
        }
    }
    
    @FXML
    private void handleNewOwner() {
        StatsOwner tempChar = new StatsOwner("New Owner");
        mainApp.getOwnerData().add(tempChar);
    }
    
    @FXML 
    private void handleNewStat() {
    	ownerTable.getSelectionModel().getSelectedItem().newStat("New stat", 0);
    }
    
    @FXML
    private void handleDeleteStat() {
    	ownerTable.getSelectionModel().getSelectedItem().deleteStat(statTable.getSelectionModel().getSelectedItem());
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        ownerTable.setItems(mainApp.getOwnerData());
    }
    
    class EditingCellFloat extends TableCell<Stat, Float> {
    	 
        private TextField textField;
       
        public EditingCellFloat() {}
       
        @Override
        public void startEdit() {
            super.startEdit();
           
            if (textField == null) {
                createTextField();
            }
           
            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.selectAll();
        }
       
        @Override
        public void cancelEdit() {
            super.cancelEdit();
           
            setText(String.valueOf(getItem()));
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
   
        @Override
        public void updateItem(Float item, boolean empty) {
            super.updateItem(item, empty);
           
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }
   
        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                    	boolean s = true;
                    	float f = 0;
                    	try{
                    		f = Float.parseFloat(textField.getText());
                    	} catch(Exception e) {
                    		System.out.println("Invalid float");
                    		s = false;
                    	}
                    	if(s) commitEdit(f);
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }
       
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
    class EditingCellString extends TableCell<Stat, String> {
   	 
        private TextField textField;
       
        public EditingCellString() {}
       
        @Override
        public void startEdit() {
            super.startEdit();
           
            if (textField == null) {
                createTextField();
            }
           
            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.selectAll();
        }
       
        @Override
        public void cancelEdit() {
            super.cancelEdit();
           
            setText(String.valueOf(getItem()));
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
   
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
           
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }
   
        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
               
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit((textField.getText()));
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }
       
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
}
