package com.smokebox.statsEditor.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Stat {
	
	private StringProperty statName;
	private FloatProperty value;
	
	public Stat() {
		this(null,0);
	}
	
	public Stat(String name, float value) {
		statName = new SimpleStringProperty(name);
		this.value = new SimpleFloatProperty(value);
	}
	
	public String getStatName() {
		return statName.get();
	}
	
	public void setStatName(String s) {
		this.statName.set(s);
	}
	
	public StringProperty getStatNameProperty() {
		return statName;
	}
	
	public FloatProperty getValueProperty() {
		return value;
	}
	
	public float getValue() {
		return value.get();
	}
	
	public void setValue(float f) {
		value.set(f);
	}
}
