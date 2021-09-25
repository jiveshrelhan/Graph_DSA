package com.dev.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;


/**
 * This is second and neat solution of the problem.
 * The Original soln was also correct. But this is more aggressive.
 * In org. soln. we find all the 1's which are not visited
 *  For each 1
 *      We perform BFS across its surrounding in all 4 directions.
 *      If any of its neighbor is not visited and has value 0.
 *          then we take minimum of (Current_Distance [Calculated from formulae],already calculated neighbors result of all 4 directions);
 *          create a pair of this coordinate and add to queue; General expansion of BFS (level 2,level3..level n)
 * 
 *  // At this movement we have filled the shortest distances of the 0s available in the resultant matrix from the 1 we selected before.
 *  // Above distance might not be optimized, because there may be other 1s in the matrix through which distance of certain 0's can be shorter.
 *  
 * 
 * Algo continues:
 *      Reset the visited[][] but maintain the 1's visited in another [][].
 *      Repeat the process, if the distance is shorter update them.
 * 
 * Time complexity: K*V*E => K is no of 1s. V & E are vertex and edges.
 * 
 **/
 
 
 /**
  * * This optimized algorithm.
  * 
  *  There were two bottle necks of above algorithm:
  *  1) We have to again traverse the whole graph/or a component for each 1.
  *  2) We have to check all the neighbors result from the matrix and compute the minimum.
  * 
  * For 2nd Point. If we closely observed, if there is more than one 1's in graph. Then the distance is 
  * definitely going to be shorter for their neighbors and their further levels in BFS. So we can stop checking the minimum value from its neighbor
  * and assign directly from the current_distance. 
  * This is valid for the 1st BFS(1) call too. As initial distance we have calculated in Infinity/502 and new distance is definitely going to be shorter or same. 
  * 
  * From the 2nd Point: We can also think of processing all the 1s first. Compute their neighbors with best values and then expand to other level.
  * In other words. We will have result calculated for immediate neighbor's(Level-1) for all the 1 we have in the original matrix. 
  * Once we have this we can safely expand to other levels and replace with newer value. We don't need compare the result.(THIS IS IMPORTANT)
  * If we visualize, we have already calculate all the surrounding of 1's. If there is a case surroundings of one or more 1's clash like 2 one's are very near.
  * Then it doesn't matter because distance is going to be 1 either from any 1. 
  * Because we are computing only immediate neighbors.
  * No if we expand to other levels. It means distance is going to be 2. 
  * You reach it from anywhere from level-1 of 1 the answer is going to remain same. So its safe to replace value directly.
  * 
  * 
  * Only clever change in coding will be. The objective is to process the all 1's neighbor first and then expand. In naive BFS solution initially 
  * we are level 0 only as we add source vertex to queue and as we progress we add their children to the queue which is a level deeper.
  * So before doing this. we can proactively find all the 1's in matrix and add in queue. It will be like we have multiple sources. Then proceed with normal algo.
  * 
  * 
  */ 


public class Distance {

	
	
	static class Pair {
		int i;
		int j;

		Pair(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}

	static void nearest(ArrayList<ArrayList<Integer>> list, int rows, int cols) {
		Queue<Pair> queue = new LinkedList<>();

		ArrayList<ArrayList<Integer>> result = new ArrayList<>();

		boolean[][] visited = new boolean[rows][cols];

		for (int i = 0; i < rows; i++) {
			ArrayList<Integer> row = new ArrayList<>(Collections.nCopies(cols, 502));
			result.add(row);

		}

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (list.get(r).get(c) == 1) {
					Pair one = new Pair(r, c);
					updateValue(result, r, c, 0);
					visited[r][c] = true;
					queue.add(one);
				}
			}
		}

		process(list, queue, rows, cols, visited, result);

	}

	static void process(ArrayList<ArrayList<Integer>> list, Queue<Pair> queue, int rows, int cols, boolean[][] visited,
			ArrayList<ArrayList<Integer>> result) {
		// D, L , R , U
		int[] X = { 1, 0, 0, -1 };
		int[] Y = { 0, -1, 1, 0 };

		while (!queue.isEmpty()) {

			Pair current = queue.poll();

			for (int k = 0; k < 4; k++) {
				int x = current.i + X[k];
				int y = current.j + Y[k];

				if (isValid(x, y, rows, cols) && !visited[x][y] && getData(list, x, y) == 0) {
					int min_value = getData(result, current.i, current.j) + 1;
					updateValue(result, x, y, min_value);
					visited[x][y] = true;
					queue.add(new Pair(x, y));
				}
			}

		}
	}

	static void updateValue(ArrayList<ArrayList<Integer>> result, int i, int j, int newValue) {
		ArrayList<Integer> row = result.get(i);
		row.remove(j);
		row.add(j, newValue);
	}

	static int getData(ArrayList<ArrayList<Integer>> list, int i, int j) {
		return list.get(i).get(j);
	}

	static boolean isValid(int i, int j, int rows, int cols) {
		return (i < 0 || i >= rows || j < 0 || j >= cols) ? false : true;
	}

	@Test
	public void test3() {

		Graph graph = new Graph(3, 3, "0 0 1 0 1 0 0 0 0");
		Distance.nearest(graph.graph, 3, 3);
	}
}