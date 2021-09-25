package com.dev.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class KosarajuAlgorithm {
	boolean[] visited;
	Queue<Integer> q;

	public Set<Set<Integer>> getSCCs(Graph g, int size) {
		List<? extends List<Integer>> graph = g.graph;
		visited = new boolean[size];
		q = new LinkedList<>();

		for (int i = 0; i < size; i++) {
			if (!visited[i]) {
				DFS(graph, i);
			}
		}
		//Creating a new graph. Reversing the existing graph is little challenging if the current contains cycyle.
		Graph transpose_g = new Graph(size);
		reveseGraph(g, transpose_g, size);

		Set<Set<Integer>> res = new HashSet<>();
		while (!q.isEmpty()) {
			Set<Integer> set = new HashSet<>();
			int node = q.poll();
			if (visited[node]) {
				Set<Integer> scc = DFSWithSCC(transpose_g.graph, node, set);
				res.add(scc);
			}
		}
		return res;
	}

	private void DFS(List<? extends List<Integer>> graph, int src) {

		visited[src] = true;
		q.add(src);

		List<Integer> dependents = graph.get(src);

		for (int i = 0; i < dependents.size(); i++) {
			int V = dependents.get(i);
			if (!visited[V]) {
				DFS(graph, V);
			}
		}

	}

	private Set<Integer> DFSWithSCC(List<? extends List<Integer>> graph, int src, Set<Integer> component) {

		visited[src] = false;
		component.add(src);
		List<Integer> dependents = graph.get(src);

		for (int i = 0; i < dependents.size(); i++) {
			int V = dependents.get(i);
			if (visited[V]) {
				DFSWithSCC(graph, V, component);
			}
		}
		return component;
	}

	private void reveseGraph(Graph g, Graph transpose_g, int size) {

		boolean[] processed = new boolean[size];
		for (int i = 0; i < size; i++) {
			if (!processed[i]) {
				processed[i] = true;
				reverseGraphHelper(g, transpose_g, i, processed);
			}
		}
	}

	private void reverseGraphHelper(Graph g, Graph transpose_g, int src, boolean[] processed) {
		List<? extends List<Integer>> graph = g.graph;
		List<Integer> dependents = graph.get(src);
		for (int i = 0; i < dependents.size(); i++) {
			int target = dependents.get(i);
			transpose_g.addEdge(target, src);
		}
	}

	public static void main(String[] args) {
		Graph g = new Graph(5);
		g.addEdge(0, 1);
		g.addEdge(1, 2);
		g.addEdge(2, 0);
		g.addEdge(1, 3);
		g.addEdge(3, 4);

		KosarajuAlgorithm obj = new KosarajuAlgorithm();
		Set<Set<Integer>> sc = obj.getSCCs(g, 5);
		System.out.println(sc.size());
	}

}
