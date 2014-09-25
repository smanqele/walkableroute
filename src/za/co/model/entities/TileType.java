package za.co.model.entities;

public enum TileType {
	FLATLAND(".", new Integer(1)),
	START("@", new Integer(1)),
	END("X", new Integer(1)),
	FOREST("*", new Integer(2)),
	MOUNTAIN("^", new Integer(3)),
	WATER("~", Integer.MAX_VALUE, false);
	
	final String tile;
	final Integer moveCost;
	boolean canUse = true; 
	
	private TileType(String tile, Integer moveCost) {
		this.tile = tile;
		this.moveCost = moveCost;
	}
	
	private TileType(String tile, Integer moveCost, boolean canUse) {
		this(tile, moveCost);
		this.canUse = canUse;
	}
	
	public String getTileString(){
		return tile;
	}
	
	public Integer getMoveCost(){
		return moveCost;
	}
	
	public boolean canUse(){
		return canUse;
	}
	
	public String toString(){
		return this.getTileString() + "-" + this.name() ;
		
	}
	
	public static boolean isStartType(TileType type){
		return type.getTileString().equals("@");
	}
	
	public static boolean isEndType(TileType type){
		return type.getTileString().equals("X");
	}
	
	public static TileType getType(String tile){
		for (TileType type : values()){
			if (type.tile.equals(tile)){
				return type;
			}
		}
		return null;
	}

}


