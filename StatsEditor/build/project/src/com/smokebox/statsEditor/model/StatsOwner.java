package com.smokebox.statsEditor.model;

import javax.xml.bind.annotation.XmlElement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StatsOwner {

	private StringProperty charName;
	private ObservableList<Stat> stats;
	
	public StatsOwner() {
		this(null);
	}
	
	public StatsOwner(String name) {
		charName = new SimpleStringProperty(name);
		stats = FXCollections.observableArrayList();
		stats.add(new Stat("new stat", 0));
	}
	
	public void newStat(String name, float value) {
		stats.add(new Stat(name, value));
	}
	
	public void deleteStat(Stat toRemove) {
		stats.remove(toRemove);
	}
	
	public void setName(String name) {
		charName.set(name);
	}
	
	public String getName() {
		return charName.get();
	}
	
	public StringProperty getNameProperty() {
		return charName;
	}
	
	@XmlElement(name = "stat")
	public ObservableList<Stat> getStats() {
		return stats;
	}
	
	public Stat getStatByName(String name) {
		for(Stat s : stats) {
			if(s.getStatName() == name) return s;
		}
		return null;
	}
	
	public Stat getStatByIndex(int index) {
		return stats.get(index);
	}
}
