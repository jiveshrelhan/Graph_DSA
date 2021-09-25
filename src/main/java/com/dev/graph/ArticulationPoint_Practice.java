package com.dev.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ArticulationPoint_Practice {

	private int[] APs, min, parent, ids;

	private boolean[] visited;

	private int time;

	public int[] findAP(List<? extends List<Integer>> graph, int size) {

		APs = new int[size];
		min = new int[size];
		parent = new int[size];
		ids = new int[size];
		visited = new boolean[size];
		time = 0;

		Arrays.fill(parent, -1);

		for (int i = 0; i < size; i++) {
			if (!visited[i]) {
				DFS(i, graph);
			}
		}

		return APs;
	}

	private void DFS(int current, List<? extends List<Integer>> graph) {

		visited[current] = true;
		ids[current] = min[current] = ++time;
		int children = 0;

		List<Integer> dependents = graph.get(current);

		for (int i = 0; i < dependents.size(); i++) {
			int child = dependents.get(i);
			if (!visited[child]) {
				children++;
				parent[child] = current;
				DFS(child, graph);

				min[current] = Math.min(min[current], min[child]);

				if (parent[current] == -1 && children > 1) {
					APs[current] = 1;
				} else if (parent[current] != -1 && min[child] >= ids[current]) {
					APs[current] = 1;
				}

			}// child != parent[current] is to avoid the edge if V is direct child of U  
			else if (child != parent[current]) {
				min[current] = Math.min(min[current], ids[child]);
			}
		}

	}

	@Test
	public void test1() {
		Graph graph = new Graph(5);
		graph.addUnDirectedEdge(0, 4);
		graph.addUnDirectedEdge(0, 1);
		graph.addUnDirectedEdge(1, 2);
		graph.addUnDirectedEdge(2, 3);


		ArticulationPoint_Practice point = new ArticulationPoint_Practice();
		int[] res = point.findAP(graph.graph, graph.NO_OF_VERTICES);
		int sum = Arrays.stream(res).reduce(0, (a, b) -> a + b);
		assertEquals(3, sum);
	}

}
