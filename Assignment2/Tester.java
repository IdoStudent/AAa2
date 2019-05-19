import map.PathMap;
import pathFinder.DijkstraPathFinder;
import map.Coordinate;
import java.util.*;

public class Tester {

	public static void main(String[] args) {
		
		int rowNum = 3;
		int colNum = 3;
		List<Coordinate> originCells = new ArrayList<Coordinate>();
		List<Coordinate> destCells = new ArrayList<Coordinate>();
		Set<Coordinate> impassableCells = new HashSet<Coordinate>();
		Map<Coordinate,Integer> terrainCells = null;
		List<Coordinate> waypointCells = null;
		
		impassableCells.add(new Coordinate(2,2));
		impassableCells.add(new Coordinate(3,2));
		impassableCells.add(new Coordinate(4,2));
		impassableCells.add(new Coordinate(1,2));
		impassableCells.add(new Coordinate(3,3));
		impassableCells.add(new Coordinate(2,3));
		impassableCells.add(new Coordinate(1,3));
		impassableCells.add(new Coordinate(2,4));
		
		
		PathMap map = new PathMap();
		//map.initMap(rowNum, colNum, originCells, destCells, impassableCells, terrainCells, waypointCells);
		map.sizeC = 7;
		map.sizeR = 7;
		
		map.cells = new Coordinate[map.sizeR][map.sizeC];

        // construct the coordinates in the grid and also update inforamtion about impassable
        // and terrain costs.
        for (int i = 0; i < map.sizeR; i++) {
            for (int j = 0; j < map.sizeC; j++) {
                Coordinate coord = new Coordinate(i, j);
                // add impassable cells
                if (impassableCells.contains(coord)) {
                    coord.setImpassable(true);
                    //System.out.println("impassable");
                }
                /*
                // add terrain information
                // should not be both
                if (terrainCells.containsKey(coord)) {
                    int cost = terrainCells.get(coord).intValue();
                    coord.setTerrainCost(cost);
                }*/

                map.cells[i][j] = coord;
            }
        }
        
        destCells.add(new Coordinate(4,5));
        originCells.add(new Coordinate(2,0));

        map.destCells = destCells;
        map.originCells = originCells;
		
		DijkstraPathFinder test = new DijkstraPathFinder(map);
		test.findPath();

	}

}
