package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.util.*;

public class DijkstraPathFinder implements PathFinder{
	
	List<Node> S;
	List<Node> coordinates;
	int length = 0;
	PathMap map;
	
	public DijkstraPathFinder(PathMap map) {
		
		this.map = map;
		
		S = new ArrayList<Node>();
		coordinates = new ArrayList<Node>();
		
		for(int r=0;r<map.sizeR;r++) {	// ADD cells to unexplored
			for(int c=0;c<map.sizeC;c++) {
				boolean origin = false;
				map.cells[r][c].setValue(1000);
				for(int o=0;o<map.originCells.size();o++) {
					if(map.originCells.contains(map.cells[r][c])) {	// if origin
						map.cells[r][c].setValue(0);
						S.add(new Node(map.cells[r][c],null));
						origin = true;
					}
				}
				if(!origin) {
					coordinates.add(new Node(map.cells[r][c],null));
				}	
			}
		}
    }

	@Override
	public List<Coordinate> findPath() {
		
		while(length != map.sizeC * map.sizeR - 1) { // while isn't destination
			
			int col = S.get(length).getCoordinate().getColumn();
	    	int row = S.get(length).getCoordinate().getRow();
	    	int currentValue = S.get(length).getCoordinate().getValue();
			
			for(int i=0;i<coordinates.size();i++) {						//find adjacent
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
	    				
	    				if(coordinates.get(i).getCoordinate().getValue() > (currentValue + 1)) {
	    					coordinates.get(i).getCoordinate().setValue(currentValue + 1);	//update value
	    					coordinates.get(i).setPrevious(S.get(length));
	    					System.out.println("update: " + coordinates.get(i).getCoordinate() + " previous: " + S.get(length).getCoordinate());
	    				}
	    		}
	    	}
			find();
			length++;
		}
		
		//create a list with path
		List<Coordinate> path = new ArrayList<Coordinate>();
		Node tempNode = S.get(length);
		//Coordinate tempCoordinate = S.get(length).getCoordinate();
		for(int i=0;i<map.destCells.size();i++) {
			for(int k=0;k<S.size();k++) {
				if (S.get(k).getCoordinate().equals(map.destCells.get(i))){
					tempNode = S.get(k);
				}
			}
		}
		int pathLength = tempNode.getCoordinate().getValue();
		for(int i=0;i<pathLength+1;i++) {
			path.add(tempNode.getCoordinate());
			tempNode = tempNode.getPrevious();
			//System.out.println("previous: " + tempNode.getPrevious().getCoordinate());
		}
		
		for(int i=0;i<path.size();i++) {
			System.out.println("path: " + path.get(i) + " value: " + path.get(i).getValue());
		}
		return path;
	}
	
	public int coordinatesExplored() {
        // placeholder
        return 0;
    } // end of cellsExplored()
	
	public void find() {
		System.out.println("find");
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
    	coordinates.remove(toDelete);
    	S.add(tempNode);
	}
}
