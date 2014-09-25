package za.co.graph.source;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;

import za.co.model.entities.Position;
import za.co.model.entities.Tile;

public class GraphLoader {
	
	static final String path = "large_map.txt";

	public Map<Position, Tile> load() {
		try {
			Map<Position, Tile> graph = this.readFileAsString(getFileName());
			buildAdjacentTiles(graph);

			return (graph != null ? graph : new java.util.HashMap<Position, Tile>() );
		} catch (java.io.IOException e) {
			throw new RuntimeException("Cannot read file " + getFileName());
		}
	}

	private void buildAdjacentTiles(Map<Position, Tile> graph) {
		if (graph == null || graph.size() < 1){
			return;
		}
		java.util.Collection<Tile> values = graph.values();	
		for (Tile tile : values){
			if (!tile.canUse()){
				continue;
			}
			java.util.Set<Tile> set = new java.util.HashSet<>();
			
			Tile xTile = traverseXPos(tile, graph);
			if (xTile != null && xTile.canUse()){
				set.add(xTile);
			} 
			
			Tile yTile = traverseYPos(tile, graph);
			Tile yxTile = null;
			if (yTile != null ){
				if (yTile.canUse()){
					set.add(yTile);
				}
				yxTile = traverseXPos(yTile, graph);
			}			
			if (yxTile != null && yxTile.canUse()){
				set.add(yxTile);
			}

			tile.setNeighbours(set);
		}
		
	}
	
	private Tile traverseXPos (Tile tile, Map<Position, Tile> graph){
		Position xpos = tile.getPosition().traverseXPos();
		return graph.get(xpos);
	}
	
	private Tile traverseYPos(Tile tile, Map<Position, Tile> graph){
		Position ypos = tile.getPosition().traverseYPos();
		return graph.get(ypos);
	}

	Map<Position, Tile> readFileAsString(String filePath) throws java.io.IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
		Map<Position, Tile> graph = new java.util.HashMap<Position, Tile>();

		String line = null;
		int lineCounter = 0;
		while ((line = reader.readLine()) != null) {
			processLine(lineCounter, line, graph);
			lineCounter -= 1;
		}

		reader.close();

		return graph;
	}
	
	/*
	 * lineCounter is the y-coordinate
	 */
	void processLine(int lineCounter, String line, Map<Position, Tile > graph){
		char[] characters = line.toCharArray();
		int columnCounter = 0;
		for (char character : characters){
			Position position = new Position(columnCounter, lineCounter);
			Tile tile = new Tile(String.valueOf(character), position);
			graph.put(position, tile);
			columnCounter += 1;
		}
	}
	
	public static String getFileName(){
		return "resources/" + path;
	}
	
	public static String getFileResultName(){
		String[] tokens = new StringBuilder(path).toString().split("_");
		StringBuilder builder = new StringBuilder(tokens[0]);
		builder.append("_result_").append(tokens[1]);
		return "resources/" + builder.toString();
		
	}
	
	public static void main(String[] args){
		//new GraphLoader().load();
		System.out.println(getFileResultName());
	}
	
	

}
