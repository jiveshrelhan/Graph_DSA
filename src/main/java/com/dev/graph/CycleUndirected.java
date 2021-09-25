package com.dev.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.Test;

public class CycleUndirected {
	
	static boolean isCyclic(ArrayList<ArrayList<Integer>> list, int V) {
		boolean[] visited = new boolean[list.size()];

		for (int i = 0; i < list.size(); i++) {
		
				if (!visited[i]) {
					if (BFS(list, i, visited)) {
						return true;
					}
				}
			}
		

		return false;
	}

	private static boolean BFS(ArrayList<ArrayList<Integer>> list, int source, boolean[] visited) {

		Queue<Integer> queue = new LinkedList<Integer>();

		queue.offer(source);
		int child_count = 0;
		while (!queue.isEmpty()) {
			child_count = 0;
			int node = queue.remove();
			visited[node] = true;
			ArrayList<Integer> children = list.get(node);

			for (int i = 0; i < children.size(); i++) {
				int dependent = children.get(i);
				//self loop
				if(dependent==node) {
					return true;
				}
				if (visited[dependent]) {
					child_count++;
				} else {
					queue.offer(dependent);
				}
			}

			if (child_count >= 2) {
				return true;
			}

		}
		return false;
	}
	
	@Test
	public void test1() {
		Graph graph = new Graph(1);
		graph.addEdges("0 0");
		boolean result = isCyclic(graph.graph, 1);
		assertEquals(true, result);
	}
	
	@Test
	public void test2() {
		Graph graph = new Graph(7);
		graph.addEdges("0 1 1 0 1 2 2 3 3 4 4 5 4 6 4 0");
		boolean result = isCyclic(graph.graph, 7);
		assertEquals(true, result);
	}
}
