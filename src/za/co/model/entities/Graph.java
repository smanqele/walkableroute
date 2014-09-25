package za.co.model.entities;

import java.util.Map;

import za.co.graph.source.GraphLoader;

/**
 * A walkable graph with the Tile representation
 * @author Sihle
 *
 */
public class Graph {
	
	final Map<Position, Tile> graph ;
	
	public Graph(){
		graph = new GraphLoader().load();
	}
	
	public Map<Position, Tile> getGraph(){
		return java.util.Collections.unmodifiableMap(new java.util.HashMap<Position, Tile>(graph));
	}
	
	public Tile getTile(Position position){
		return graph.get(position);
	}
	
	public Tile getStartTile(){
		for (Tile tile : graph.values()){
			if (tile.isStartType()){
				return tile;
			}
		}
		return null;
	}
	
	public Tile getEndTile(){
		for (Tile tile : graph.values()){
			if (tile.isEndType()){
				return tile;
			}
		}
		return null;
	}
	
	
	static Integer calcManhattanDistance(Tile a, Tile b){
		return abs(a.getPosition().getxPos() - b.getPosition().getxPos()) + 
				abs(a.getPosition().getyPos()- b.getPosition().getyPos());
	}
	
	public static Integer calcTotalMoveCost(Tile a, Tile b){
		return calcManhattanDistance(a, b) + b.getMoveCost();
	}
	
	private static Integer abs(Integer value){
		return (value < 0 ? value * -1 : value);
	}

}
