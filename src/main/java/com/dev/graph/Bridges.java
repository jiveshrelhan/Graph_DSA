package com.dev.graph;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class Bridges {

	int id;
	int[] ids, low;
	boolean[] visited;
	int[] AP;
	int[] parent;

	public int[] findAPs(List<? extends List<Integer>> graph, int V) {
		id = 0;
		ids = new int[V];
		low = new int[V];
		visited = new boolean[V];
		AP = new int[V];
		parent = new int[V];
		Arrays.fill(parent, -1);
		for (int i = 0; i < V; i++) {
			if (!visited[i]) {
				DFS(graph, i, parent, AP);
			}
		}

		return AP;
	}

	private void DFS(List<? extends List<Integer>> graph, int src, int[] parent, int[] AP) {
		visited[src] = true;
		ids[src] = low[src] = ++id;
		int No_Of_Child = 0;
		List<Integer> dependents = graph.get(src);
		boolean isArticulationPoint = false;
		for (int i = 0; i < dependents.size(); i++) {

			int child = dependents.get(i);
			if (child == parent[src])
				continue;

			if (!visited[child]) {
				No_Of_Child++;
				parent[child] = src;
				DFS(graph, child, parent, AP);

				if (ids[child] <= low[src]) {
					isArticulationPoint = true;
				} else {
					low[src] = Math.min(low[src], low[child]);
				}
			}

			if (parent[src] == -1 && No_Of_Child > 1) {
				AP[src] = 1;
			} else if (parent[src] != -1 && isArticulationPoint) {
				AP[src] = 1;
			}
		}
	}

	@Test
	public void test() {
		Graph graph = new Graph(6);
		graph.addUnDirectedEdge(0, 1);
		graph.addUnDirectedEdge(0, 5);
		graph.addUnDirectedEdge(1, 2);
		graph.addUnDirectedEdge(2, 3);
		graph.addUnDirectedEdge(3, 4);
		graph.addUnDirectedEdge(4, 5);
		Bridges bridges = new Bridges();
		bridges.findAPs(graph.graph, 6);
	}

}
