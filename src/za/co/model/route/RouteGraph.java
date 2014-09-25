package za.co.model.route;

import za.co.model.entities.Graph;
import za.co.model.entities.Tile;


public class RouteGraph extends Graph {
	
	public RouteGraph(){
		super();
	}
	
	public static java.util.Set<RouteNode> getNeighbours(RouteNode node){
		java.util.Set<RouteNode> neighbours = new java.util.HashSet<>();
		for (Tile tile : node.getNode().getNeighbours()){
			neighbours.add(new RouteNode(tile));
		}
		return neighbours;
		
	}
	
	public RouteNode getStartNode(){		
		return new RouteNode(getStartTile());		
	}
	
	public RouteNode getTargetNode(){		
		return new RouteNode(getEndTile());		
	}

}
