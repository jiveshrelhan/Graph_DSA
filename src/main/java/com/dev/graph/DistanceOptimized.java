package com.dev.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

public class DistanceOptimized {
	static class SpecialNode {
		int value;
		int i;
		int j;

		SpecialNode(int i, int j, int value) {
			this.i = i;
			this.j = j;
			this.value = value;
		}

		@Override
		public String toString() {
			return "i :-> " + this.i + " j :-> " + this.j + " value :-> " + this.value;
		}
	}

	static void nearest(ArrayList<ArrayList<Integer>> list, int rows, int cols) {

		// 1st Step initialize the resultant Array and fill initial distance as MAXIMUM;
		// Initialize visited Array to exit from the function.
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < rows; i++) {
			result.add(new ArrayList<Integer>(Collections.nCopies(cols, null)));
		}

		boolean[][] parentVisited = new boolean[rows][cols];
		// 2nd Step look for 1's in the list if its is not visited; then start the
		// procedure "Attack".

		for (int i = 0; i < rows; i++) {
			ArrayList<Integer> row = list.get(i);
			boolean[][] visited = new boolean[rows][cols];

			for (int j = 0; j < cols; j++) {
				int node = row.get(j);
				if (node == 1 && !parentVisited[i][j]) {
					parentVisited[i][j] = true;
					attack(list, i, j, result, visited);
				}
			}
		}

		/************
		 * After completing the logic Just call the print function provided in the
		 * locked part
		 **********/
	}

	static boolean isValid(int i, int j, int rows, int cols) {

		if (i < 0 || i >= rows || j < 0 || j >= cols) {
			return false;
		}
		return true;
	}

	static int getNode(int i, int j, ArrayList<ArrayList<Integer>> list) {
		return list.get(i).get(j);
	}

	static int getMinimumDistance(SpecialNode node, int currentDistance, ArrayList<ArrayList<Integer>> result,
			boolean[][] visited) {

		int res = currentDistance;

		if (isValid(node.i - 1, node.j, visited.length, visited[0].length)
				&& result.get(node.i - 1).get(node.j) != null) {
			res = Math.min(res, result.get(node.i - 1).get(node.j) + 1);
		}

		if (isValid(node.i + 1, node.j, visited.length, visited[0].length)
				&& result.get(node.i + 1).get(node.j) != null) {
			res = Math.min(res, result.get(node.i + 1).get(node.j) + 1);
		}

		if (isValid(node.i, node.j - 1, visited.length, visited[0].length)
				&& result.get(node.i).get(node.j - 1) != null) {
			res = Math.min(res, result.get(node.i).get(node.j - 1) + 1);
		}

		if (isValid(node.i, node.j + 1, visited.length, visited[0].length)
				&& result.get(node.i).get(node.j + 1) != null) {
			res = Math.min(res, result.get(node.i).get(node.j + 1) + 1);
		}

		return res;
	}

	static void expandAttack(ArrayList<ArrayList<Integer>> list, int i, int j, boolean[][] visited,
			Queue<SpecialNode> neighbours) {
		// up
		if (isValid(i - 1, j, visited.length, visited[0].length) && getNode(i - 1, j, list) == 0
				&& !visited[i - 1][j]) {
			visited[i - 1][j] = true;
			neighbours.offer(new SpecialNode(i - 1, j, 0));
		}

		// down
		if (isValid(i + 1, j, visited.length, visited[0].length) && getNode(i + 1, j, list) == 0
				&& !visited[i + 1][j]) {
			visited[i + 1][j] = true;
			neighbours.offer(new SpecialNode(i + 1, j, 0));
		}

		// left
		if (isValid(i, j - 1, visited.length, visited[0].length) && getNode(i, j - 1, list) == 0
				&& !visited[i][j - 1]) {
			visited[i][j - 1] = true;
			neighbours.offer(new SpecialNode(i, j - 1, 0));
		}

		// right
		if (isValid(i, j + 1, visited.length, visited[0].length) && getNode(i, j + 1, list) == 0
				&& !visited[i][j + 1]) {
			visited[i][j + 1] = true;
			neighbours.offer(new SpecialNode(i, j + 1, 0));
		}
	}

	static int getCurrentDistance(int i1, int i2, int j1, int j2) {
		return (Math.abs(i1 - i2) + Math.abs(j1 - j2));
	}

	/*
	 * Once called ! Attack in 4 Directions UP,DOWN,LEFT,RIGHT. Check if the
	 * respective node is '0' and compute the distance. if the distance is already
	 * processed add 1 to it and compute minimum among these. This should be in BFS
	 * fashion Also Boundary level check should be there.
	 */
	static void attack(ArrayList<ArrayList<Integer>> list, int i, int j, ArrayList<ArrayList<Integer>> result,
			boolean[][] visited) {

		Queue<SpecialNode> neighbours = new LinkedList<>();
		expandAttack(list, i, j, visited, neighbours);
		result.get(i).remove(j);
		result.get(i).add(j, 0);
		visited[i][j] = true;

		while (!neighbours.isEmpty()) {
			SpecialNode node = neighbours.poll();
			int current_distance = getCurrentDistance(i, node.i, j, node.j);

			int minimum_dist = getMinimumDistance(node, current_distance, result, visited);

			ArrayList<Integer> modified = result.get(node.i);

			modified.remove(node.j);
			modified.add(node.j, minimum_dist);

			expandAttack(list, node.i, node.j, visited, neighbours);

		}

	}
	/*
	 * @Test public void test1() {
	 * 
	 * Graph graph = new Graph(3, 3, "0 0 0 0 0 0 1 0 0");
	 * Distance.nearest(graph.graph, 3, 3); }
	 * 
	 * @Test public void test2() {
	 * 
	 * Graph graph = new Graph(1, 2, "1 1"); Distance.nearest(graph.graph, 1, 2); }
	 */

	@Test
	public void test3() {

		Graph graph = new Graph(3, 3, "0 0 1 0 1 0 0 0 0");
		DistanceOptimized.nearest(graph.graph, 3, 3);
	}
}