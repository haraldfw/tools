package com.smokebox.lib.utils.pathfinding;

import java.util.ArrayList;
import java.util.Collections;

import com.smokebox.lib.utils.MathUtils;

/**
 * @author Harald Floor Wilhelmsen
 *
 */
public class StarNode {

	public float x;
	public float y;
	
	public float costSoFar = -1;
	public float estimatedTotalCost = -1;
	
	private StarNode parent;
	
	private ArrayList<Connection> connections;
	
	public StarNode(float x, float y) {
		this.x = x;
		this.y = y;
		connections = new ArrayList<>();
	}
	
	public ArrayList<Connection> getConnections() {
		return connections;
	}
	
	/**
	 * Add a connection from this node to the one given
	 * Weight will be the geographical distance between nodes
	 * @param end	The node to add connection to
	 */
	public void addConnection(StarNode end) {
		connections.add(new Connection(this, end, MathUtils.vectorLength(end.x - x, end.y - x)));
	}
	
	public void removeConnection(Connection c) {
		if(connections.contains(c)) connections.remove(c);
	}
	
	public StarNode getParent() {
		return parent;
	}

	public void setParent(StarNode parent) {
		this.parent = parent;
	}

	public void clearHeuristics() {
		costSoFar = -1;
		estimatedTotalCost = -1;
	}
	
	public void finalizeConnections() {
//		Collections.sort(connections, new Comparator<Connection>() {
//			@Override
//			public static int compare(Connection c, Connection c2) {
//		        return (int) Math.round(c.weight - c2.weight); // use your logic
//		    }
//		});
		Collections.sort(connections, (Connection c1, Connection c2) -> { return (int) (c1.weight - c2.weight); });
	}
}
