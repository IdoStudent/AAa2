package pathFinder;

import map.Coordinate;

public class Node {
	
	Coordinate coordinate;
	Node previous;
	
	public Node(Coordinate coordinate, Node previous) {
		this.coordinate = coordinate;
		this.previous = previous;
		if(previous == null) {
			previous = this;
		}
	}
	
	public Coordinate getCoordinate() {
		return coordinate;
	}
	
	public Node getPrevious() {
		return previous;
	}
	
	public void setPrevious(Node previous) {
		this.previous = previous;
	}
}
