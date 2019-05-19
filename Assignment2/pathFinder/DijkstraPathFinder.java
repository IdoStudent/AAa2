package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.util.*;

public class DijkstraPathFinder implements PathFinder{
	
	List<Node> S;			//set of explored coordinates
	List<Node> coordinates;	//set of all passable coordinates
	int count = 0;			//counts elements in S
	PathMap map;
	int length = 0;			//amount of initial coordinates
	
	public DijkstraPathFinder(PathMap map) {
		
		this.map = map;
		S = new ArrayList<Node>();
		coordinates = new ArrayList<Node>();
		
		for(int r=0;r<map.sizeR;r++) {	// ADD cells to coordinates list
			for(int c=0;c<map.sizeC;c++) {
				if(map.cells[r][c].getImpassable() == false) {
					boolean origin = false;
					map.cells[r][c].setValue(1000);						//set to infinity
						if(map.originCells.contains(map.cells[r][c])) {	// if origin
							map.cells[r][c].setValue(0);
							S.add(new Node(map.cells[r][c],null));
							origin = true;
						}
					if(!origin) {	// if not origin
						coordinates.add(new Node(map.cells[r][c],null));
					}
				}	
			}
		}
		length = coordinates.size();
    }

	@Override
	public List<Coordinate> findPath() {
		
		while(coordinates.isEmpty() == false) { // while haven't explored all coordinates
			
			int col = S.get(count).getCoordinate().getColumn();				//explored next from S
	    	int row = S.get(count).getCoordinate().getRow();
	    	int currentValue = S.get(count).getCoordinate().getValue();
			
			for(int i=0;i<coordinates.size();i++) {						//find adjacent cells
	    		if((coordinates.get(i).getCoordinate().getColumn() == col &&
					coordinates.get(i).getCoordinate().getRow() == row+1 &&
	    		   !coordinates.get(i).getCoordinate().getImpassable())||
	    		   (coordinates.get(i).getCoordinate().getColumn() == col+1 &&
				   coordinates.get(i).getCoordinate().getRow() == row &&
		           !coordinates.get(i).getCoordinate().getImpassable())||
	    		   (coordinates.get(i).getCoordinate().getColumn() == col &&
				   coordinates.get(i).getCoordinate().getRow() == row-1 &&
		           !coordinates.get(i).getCoordinate().getImpassable()) ||
	    		   (coordinates.get(i).getCoordinate().getColumn() == col-1 &&
				   coordinates.get(i).getCoordinate().getRow() == row &&
		           !coordinates.get(i).getCoordinate().getImpassable())) {
	    				
	    				System.out.println("S: " + S.get(count).getCoordinate());
	    				System.out.print("check " + coordinates.get(i).getCoordinate());
	    				System.out.println(" new value: " + (currentValue + coordinates.get(i).getCoordinate().getTerrainCost()));
	    				if(coordinates.get(i).getCoordinate().getValue() > (currentValue + coordinates.get(i).getCoordinate().getTerrainCost())) {	//if new value is smaller
	    					coordinates.get(i).getCoordinate().setValue(currentValue + coordinates.get(i).getCoordinate().getTerrainCost());	//update value
	    					coordinates.get(i).setPrevious(S.get(count));					//update previous node
	    					System.out.println("update: " + coordinates.get(i).getCoordinate() + " previous: " + S.get(count).getCoordinate());
	    				}
	    		}
	    	}
			find();	// add smallest value coordinate to S
			count++;
		}
		
		
		//create a list with path
		List<Coordinate> path = new ArrayList<Coordinate>();
		Node tempNode = S.get(S.size() -1);
		int desValue = 1000;
		for(int i=0;i<map.destCells.size();i++) {								// search for lowest value destination
			for(int k=0;k<S.size();k++) {
				if (S.get(k).getCoordinate().equals(map.destCells.get(i)) && S.get(k).getCoordinate().getValue() < desValue){
					tempNode = S.get(k);
					desValue = S.get(k).getCoordinate().getValue();
				}
			}
		}
		System.out.println(tempNode.getCoordinate());
		boolean des = false;
		while(!des) {
			path.add(tempNode.getCoordinate());						// add to path
			if(map.originCells.contains(tempNode.getCoordinate())) {
				des = true;
			}
			tempNode = tempNode.getPrevious();						// get the previous node
			//System.out.println("add");
		}
		
		for(int i=0;i<path.size();i++) {	// print path
			System.out.println("path: " + path.get(i) + " value: " + path.get(i).getValue());
		}
		return path;
	}
	
	public int coordinatesExplored() {
        // placeholder
        return 0;
    } // end of cellsExplored()
	
	public void find() {
		//find smallest value
		if(coordinates.isEmpty() == false) {
			Node tempNode = coordinates.get(0);
	    	int lowest = coordinates.get(0).getCoordinate().getValue();
	    	int toDelete = 0;
	    	for(int i=0;i<coordinates.size();i++) {
	    		if(coordinates.get(i).getCoordinate().getValue() < lowest) {
	    			//System.out.println("lower " + coordinates.get(i) + " value: " + coordinates.get(i).getCoordinate().getValue());
	    			lowest = coordinates.get(i).getCoordinate().getValue();
	    			tempNode = coordinates.get(i);
	    			toDelete = i;
	    		}
	    	}
	    	System.out.println("add " + coordinates.get(toDelete).getCoordinate() + " value: " + coordinates.get(toDelete).getCoordinate().getValue());
	    	coordinates.remove(toDelete);	//remove from coordinates
	    	S.add(tempNode);				//add to S
//	    	for(int i=0;i<S.size();i++) {
//	    		System.out.println(S.get(i).getCoordinate());
//	    	}
		}
	}
}
