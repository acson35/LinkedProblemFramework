package main.util.model.instance;

public final class location {
	public final int locationIdx; 
	public final int x;
	public final int y;
	    
	 
    public location(int locationIdx, int x, int y){
    	this.locationIdx = locationIdx;
    	this.x = x;
    	this.y = y;
    }
    
    public int getLocationId() {
    	return this.locationIdx;
    }
    
    public int getX() {
    	return this.x;
    }
    
    public int getY() {
    	return this.y;
    }
    
    
  
	public String toString(){
    	StringBuilder sb = new StringBuilder();
    	sb.append("locationIdx:" + locationIdx);
    	sb.append("x-coordinate:" + x);
    	sb.append("y-coordinate:" + y);
    	
    	return sb.toString();
	}

}
