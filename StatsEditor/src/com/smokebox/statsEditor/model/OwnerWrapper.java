package com.smokebox.statsEditor.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "StatsOwners")
public class OwnerWrapper {

	private List<StatsOwner> stats;
	
	@XmlElement(name = "owner")
	public List<StatsOwner> getStats() {
		return stats;
	}

	public void setStats(List<StatsOwner> stats) {
		this.stats = stats;
	}
}
