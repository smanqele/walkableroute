package za.co.model.entities;

/**
 * Represents a Tile position in a 2-D graph 
 * @author Sihle
 *
 */
public class Position implements Comparable<Position>{
	
	public Position(Integer xPos, Integer yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	final private Integer xPos ;
	final private Integer yPos ;
	
	public Integer getxPos() {
		return xPos;
	}
	public Integer getyPos() {
		return yPos;
	}
	
	public Position traverseYPos(){
		return new Position(getxPos(), getyPos()-1);
	}
	
	public Position traverseXPos(){
		return new Position(getxPos() + 1, getyPos());
	}
	
	public int compareTo(Position obj){
		if (this.equals(obj)){
			return 0;
		}
		int res = abs(this.yPos).compareTo(abs(obj.yPos) );
		if (res != 0)
            return res;
		
		return  abs(this.xPos).compareTo(abs(obj.xPos) );
		
		
	}
	
	static Integer abs(Integer integer){
		return (integer > 0 ? integer : integer.intValue() * -1);
	}
	

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.xPos + abs(this.yPos);  
        return result;
    }
    
    public boolean equals(Object obj){
    	if (this == obj){
    		return true;
    	}
    	if ( !(obj instanceof Position)){
    		return false;
    	}
    	Position pos = (Position)obj;
    	return (this.xPos == pos.xPos && this.yPos == pos.yPos); 		
    }
	
	public String toString(){
		return "x" + xPos + "y"+yPos;
	}
	
	

}
