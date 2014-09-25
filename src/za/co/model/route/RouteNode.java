package za.co.model.route;

import za.co.model.entities.Position;
import za.co.model.entities.Tile;

public class RouteNode {
	
	private Tile node;
	//used to construct the path after the search is done
	private RouteNode cameFrom;
	// Distance from source to a neighboring node
	private Integer currentCost;
	// Heuristic distance from the current node to the target node
	private Integer targetCost;
	
	public RouteNode(RouteNode source, Integer currentCost, Integer targetCost) {
		this.cameFrom = source;
		this.currentCost = currentCost;
		this.targetCost = targetCost;
		this.node = source.getNode();
		
	}
	
	public RouteNode(Tile node) {
		this.node = node;
	}
	
	public Tile getNode() {
		return node;
	}
	public void setNode(Tile node) {
		this.node = node;
	}
	public RouteNode getCameFrom() {
		return cameFrom;
	}
	public void setCameFrom(RouteNode cameFrom) {
		this.cameFrom = cameFrom;
	}

	
	public Integer getCurrentCost() {
		return currentCost;
	}

	public void setCurrentCost(Integer currentCost) {
		this.currentCost = currentCost;
	}

	public Integer getTargetCost() {
		return targetCost;
	}

	public void setTargetCost(Integer targetCost) {
		this.targetCost = targetCost;
	}

	public Integer getFullCost(){
		return getCurrentCost() + getTargetCost();
	}
	
	public Position getPosition(){
		return node.getPosition();
	}
	
	public String toString(){
		return node.toString();
	}
	
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.node.getPosition().getxPos() + 
        		this.node.getPosition().getyPos();  
        return result;
    }
	
	public boolean equals(Object node){
		if (this == node){
			return true;
		}
		if (! (node instanceof RouteNode)){
			return false;
		}
		RouteNode rNode = (RouteNode)node;
		return (this.getPosition().equals(rNode.getPosition()));
	}
	
	
}
