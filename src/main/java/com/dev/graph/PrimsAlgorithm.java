package com.dev.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import com.dev.graph.WeightedGraph.Edge;

public class PrimsAlgorithm {

	private void addEdgesToPQ(LinkedList<Edge>[] WG, int vertex, boolean[] visited, PriorityQueue<Edge> pq) {
		LinkedList<Edge> edges = WG[vertex];
		for (int i = 0; i < edges.size(); i++) {
			Edge edge = edges.get(i);
			int target = edge.dest;
			if (!visited[target]) {
				pq.add(edge);
			}
		}
	}

	public List<Integer> hamiltonianPath(LinkedList<Edge>[] WG, int size, int src) {

		boolean[] visited = new boolean[size];
		// Total Path Weight
		int path_weight = 0;
		List<Integer> hamiltonianPath = new ArrayList<>();

		PriorityQueue<Edge> pq = new PriorityQueue<>(new Comparator<Edge>() {

			@Override
			public int compare(Edge o1, Edge o2) {
				if (o1.weight > o2.weight)
					return 1;
				else if (o1.weight < o2.weight)
					return -1;
				return 0;
			}
		});

		hamiltonianPath.add(src);
		addEdgesToPQ(WG, src, visited, pq);
		visited[src] = true;
		while (!pq.isEmpty()) {
			Edge edge = pq.poll();
			int V = edge.dest;

			if (!visited[V]) {
				path_weight += edge.weight;
				visited[V] = true;
				hamiltonianPath.add(V);
				addEdgesToPQ(WG, V, visited, pq);
			}
		}
		return hamiltonianPath;

	}

	public static void main(String[] args) {
		WeightedGraph WG = new WeightedGraph(5);
		WG.addEdge(0, 1, 10);
		WG.addEdge(0, 2, 5);
		WG.addEdge(1, 2, 7);
		WG.addEdge(1, 3, 14);
		WG.addEdge(1, 4, 10);
		WG.addEdge(2, 4, 27);
		WG.addEdge(3, 4, 21);
		WG.addEdge(3, 2, 12);
		WG.addEdge(4, 0, 25);
		WG.addEdge(0, 3, 23);

		PrimsAlgorithm pa = new PrimsAlgorithm();
		pa.hamiltonianPath(WG.WG, 5, 0);
	}

}
