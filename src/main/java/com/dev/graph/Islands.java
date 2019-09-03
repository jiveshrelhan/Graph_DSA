package com.dev.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class Islands {

	static int connected_components = 0;

	static void DFS(ArrayList<ArrayList<Integer>> list, int N, int M, int i, int j) {
		// boundary check
		if (i < 0 || j < 0 || i >= N || j >= M) {
			return;
		}

		int node = list.get(i).get(j);

		if (node == 0) {
			return;
		}

		if (node == 1) {

			ArrayList<Integer> dependents = list.get(i);
			dependents.remove(j);
			dependents.add(j, 0);
			list.remove(i);
			list.add(i, dependents);
		}

		DFS(list, N, M, i - 1, j - 1);
		DFS(list, N, M, i - 1, j);
		DFS(list, N, M, i - 1, j + 1);
		DFS(list, N, M, i, j + 1);
		DFS(list, N, M, i + 1, j + 1);
		DFS(list, N, M, i + 1, j);
		DFS(list, N, M, i + 1, j - 1);
		DFS(list, N, M, i, j - 1);

	}

	static int findIslands(ArrayList<ArrayList<Integer>> list, int N, int M) {
		connected_components=0;
		for (int i = 0; i < list.size(); i++) {
			ArrayList<Integer> dependents = list.get(i);

			for (int j = 0; j < dependents.size(); j++) {
				int node = dependents.get(j);
				if (node == 1) {
					connected_components++;
					DFS(list, N, M, i, j);
				}
			}
		}
		return connected_components;
	}

	@Test
	public void test1() {
		Graph graph = new Graph(3);
		graph.addEdge(0, 1);
		graph.addEdge(0, 1);
		graph.addEdge(0, 0);
		graph.addEdge(1, 0);
		graph.addEdge(1, 0);
		graph.addEdge(1, 1);
		graph.addEdge(2, 1);
		graph.addEdge(2, 0);
		graph.addEdge(2, 1);

		int islands = findIslands(graph.graph, graph.NO_OF_VERTICES, graph.NO_OF_VERTICES);
		assertEquals(2, islands);
	}
	
	@Test
	public void test2() {
		Graph graph = new Graph(10,3,"0 0 1 1 1 0 1 0 1 1 1 1 0 1 0 0 1 0 1 0 1 0 0 1 0 0 0 1 1 1");

		int islands = findIslands(graph.graph, graph.NO_OF_VERTICES, 3);
		assertEquals(2, islands);
	}
}
