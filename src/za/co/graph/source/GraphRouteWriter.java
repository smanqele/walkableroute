package za.co.graph.source;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;


import za.co.model.entities.Graph;
import za.co.model.entities.Position;
import za.co.model.entities.Tile;


public class GraphRouteWriter {
	
	final Integer initialNum = -10000;
	
	public void write(java.util.Set<Tile> set, Graph graph) throws IOException{
		String filepath = GraphLoader.getFileResultName();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath)));
		java.util.List<Position> keys = new java.util.ArrayList<>(graph.getGraph().keySet());
		sort(keys);
		Integer currentLine = initialNum;
		for (Position pos : keys){
			if (currentLine != pos.getyPos() && currentLine != initialNum){
				writer.newLine();
			}
			currentLine = pos.getyPos();
			Tile tile = graph.getTile(pos);
			if (set.contains(tile)){
				writer.write("#");
			} else {
				writer.write(tile.getType().getTileString());
			}			
		}
		writer.close();
	}
	
	
	public void sort(java.util.List<Position> keys){
		Collections.sort(keys);
	}

}
