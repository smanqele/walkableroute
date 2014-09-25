package za.co.model.entities;


/**
 * The graph node which also carries a tile position / x-y coordinates
 * Uniquely identifies a tile by its type and position 
 * @author Sihle
 *
 */
public class Tile {
	
	final TileType type;
	final Position position;
	boolean routePath = false;
	java.util.Set<Tile> neighbours = new java.util.HashSet<Tile>();
	
	
	public Tile (String tile , Position position){
		type = TileType.getType(tile);
		this.position = position;
	}

	public TileType getType() {
		return type;
	}

	public Position getPosition() {
		return position;
	}

	/**
	 * Is the tile a part of the route we are seeking? 
	 * @return
	 */
	public boolean isRoutePath() {
		return routePath;
	}


	public void setRoutePath(boolean routePath) {
		this.routePath = routePath;
	}
	
	/**
	 * Is this actually a walkable terrain?
	 * @return
	 */
	public boolean canUse(){
		return type.canUse();
	}
	
	public Integer getMoveCost(){
		return type.getMoveCost();
	}
	
	public boolean isStartType(){
		return TileType.isStartType(type);
	}
	
	public boolean isEndType(){
		return TileType.isEndType(type);
	}

	public java.util.Collection<Tile> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(java.util.Set<Tile> neighbours) {
		if (neighbours != null && neighbours.size() > 0){
			this.neighbours = neighbours;
		}
			
	}
	
	public String toString(){
		return position.toString() + "_"+ type.toString() ;
	}
	

}
