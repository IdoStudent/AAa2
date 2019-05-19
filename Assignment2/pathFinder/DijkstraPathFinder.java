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
					for(int o=0;o<map.originCells.size();o++) {
						if(map.originCells.contains(map.cells[r][c])) {	// if origin
							map.cells[r][c].setValue(0);
							S.add(new Node(map.cells[r][c],null));
							origin = true;
						}
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
		
		while(count != length) { // while haven't explored all coordinates
			
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
	    				
	    				if(coordinates.get(i).getCoordinate().getValue() > (currentValue + 1)) {	//if new value is smaller
	    					coordinates.get(i).getCoordinate().setValue(currentValue + 1);	//update value
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
		Node tempNode = S.get(count);
		for(int i=0;i<map.destCells.size();i++) {								// search from destination
			for(int k=0;k<S.size();k++) {
				if (S.get(k).getCoordinate().equals(map.destCells.get(i))){
					tempNode = S.get(k);
				}
			}
		}
		int pathLength = tempNode.getCoordinate().getValue();
		for(int i=0;i<pathLength+1;i++) {
			path.add(tempNode.getCoordinate());						// add to path
			tempNode = tempNode.getPrevious();						// get the previous node
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
		Node tempNode = coordinates.get(0);
    	int lowest = coordinates.get(0).getCoordinate().getValue();
    	int toDelete = 0;
    	for(int i=0;i<coordinates.size();i++) {
    		if(coordinates.get(i).getCoordinate().getValue() < lowest) {
    			System.out.println("lower " + coordinates.get(i) + " value: " + coordinates.get(i).getCoordinate().getValue());
    			lowest = coordinates.get(i).getCoordinate().getValue();
    			tempNode = coordinates.get(i);
    			toDelete = i;
    		}
    	}
    	System.out.println("add " + coordinates.get(toDelete).getCoordinate() + " value: " + coordinates.get(toDelete).getCoordinate().getValue());
    	coordinates.remove(toDelete);	//remove from coordinates
    	S.add(tempNode);				//add to S
	}
}
