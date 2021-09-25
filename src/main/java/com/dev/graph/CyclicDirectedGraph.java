package com.dev.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class CyclicDirectedGraph {

	/*
	 * static boolean isCyclic(ArrayList<ArrayList<Integer>> list, int V) {
	 * boolean[] visited = new boolean[V];
	 * 
	 * 
	 * for (int i = 0; i < list.size(); i++) { ArrayList<Integer> dependents =
	 * list.get(i); for (int j = 0; j < dependents.size(); j++) { int node =
	 * dependents.get(j); if (!visited[node]) { Arrays.fill(visited, false); if
	 * (DFS(list, node, visited)) { return true; } } } }
	 * 
	 * return false; }
	 * 
	 * private static boolean DFS(ArrayList<ArrayList<Integer>> list, int src,
	 * boolean[] visited) { visited[src] = true; ArrayList<Integer> dependents =
	 * list.get(src); for (int i = 0; i < dependents.size(); i++) { int dependent =
	 * dependents.get(i); if (visited[dependent]) { return true; } else { return
	 * DFS(list, dependent, visited); } }
	 * 
	 * return false; }
	 */

	static boolean isCyclic(ArrayList<ArrayList<Integer>> list, int V) {
		boolean[] global_visited = new boolean[V];

		for (int i = 0; i < V; i++) {
			if (!global_visited[i]) {
				boolean[] local_visited = new boolean[V];
				if (DFS(list, i, global_visited, local_visited)) {
					return true;
				}
			}
		}

		return false;
	}

	private static boolean DFS(ArrayList<ArrayList<Integer>> list, int src, boolean[] global_visited,
			boolean[] local_visited) {

		global_visited[src] = true;
		local_visited[src] = true;
		boolean res = false;
		ArrayList<Integer> dependents = list.get(src);

		for (int i = 0; i < dependents.size(); i++) {
			int dependent = dependents.get(i);
			if (local_visited[dependent])
				return true;
			else {
				res = DFS(list, dependent, global_visited, local_visited);
			}
		}

		return res;
	}

	@Test
	public void test1() {
		Graph graph = new Graph(2);
		graph.addEdges("0 1 0 0");
		boolean result = isCyclic(graph.graph, 2);
		assertEquals(true, result);
	}

	@Test
	public void test2() {
		Graph graph = new Graph(7);
		graph.addEdges("0 1 1 2 6 2 2 3 3 1 ");
		boolean result = isCyclic(graph.graph, 7);
		assertEquals(true, result);
	}

	@Test
	public void test3() {
		Graph graph = new Graph(4);
		graph.addEdges("0 1 2 3 3 2");
		boolean result = isCyclic(graph.graph, 4);
		assertEquals(true, result);
	}
}