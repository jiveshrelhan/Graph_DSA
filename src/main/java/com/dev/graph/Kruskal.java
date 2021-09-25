package com.dev.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


/**
 * Small optmization. We can also check if in current MST we have explored all the vertices o graph then 
 * there is no need of working on rest of the Edges. Becase rest all of them will create cycle.
 * 
 * In worst case normal algo takes- > E*(E+V) : Edges * DFS Call for Cycle detection and E can be VSquare. 
 * then overall complexity can be V Cube .. V2 * V
 * 
 * We reduce it to V * (V + V ) = V2 (V Square)
 * 
 * @author JiveshR1
 *
 */
public class Kruskal {

	static class Edge {
		int src;
		int dest;
		int w;

		Edge(int src, int dest, int w) {
			this.src = src;
			this.dest = dest;
			this.w = w;
		}
	}

	static boolean[] global_visited;

	private static List<Edge> getSort(List<Edge> edges) {
		Collections.sort(edges, new Comparator<Edge>() {

			@Override
			public int compare(Edge o1, Edge o2) {
					if (o1.w < o2.w)
						return -1;
					else if (o1.w > o2.w)
						return 1;
					else
						return 0;
			}
		});
		return edges;
	}

	private static List<Edge> extractEdges(ArrayList<ArrayList<Integer>> graph, int V) {
		List<Edge> res = new ArrayList<>();

		for (int i = 0; i < V; i++) {
			ArrayList<Integer> children = graph.get(i);
			for (int j = 0; j < children.size(); j++) {
				int child = children.get(j);
				if (child != Integer.MAX_VALUE)
					res.add(new Edge(i, j, child));
			}
		}
		return res;
	}

	private static void addEdge(ArrayList<ArrayList<Integer>> graph, int src, int dest) {
		graph.get(src).add(dest);
		graph.get(dest).add(src);
	}

	private static boolean isCycleOptimized(ArrayList<ArrayList<Integer>> graph, int src, int dest) {

		return (global_visited[src] && global_visited[dest]) ? true : false;
	}

	private static boolean isCycle(ArrayList<ArrayList<Integer>> graph, int src, int dest) {
		boolean[] visited = new boolean[graph.size()];

		return DFSHelper(graph, src, dest, -1, visited);
	}

	private static boolean DFSHelper(ArrayList<ArrayList<Integer>> graph, int src, int dest, int parent,
			boolean[] visited) {
		visited[src] = true;
		if (src == dest)
			return true;

		ArrayList<Integer> children = graph.get(src);

		for (int i = 0; i < children.size(); i++) {
			int child = children.get(i);
			if (!visited[child]) {
				if (DFSHelper(graph, child, dest, src, visited)) {
					return true;
				}
			} else if (child != parent) {
				return true;
			}
		}

		return false;
	}

	private static int MST(ArrayList<ArrayList<Integer>> graph, int V) {

		global_visited = new boolean[V];

		List<Edge> edges = extractEdges(graph, V);

		List<Edge> sortedEdges = getSort(edges);

		Iterator<Edge> itr = sortedEdges.iterator();

		ArrayList<ArrayList<Integer>> mst = new ArrayList<>();

		for (int i = 0; i < V; i++) {
			ArrayList<Integer> list = new ArrayList<>();
			mst.add(i, list);
		}

		int minCost = 0;

		while (itr.hasNext()) {
			Edge edge = itr.next();
			if (!isCycleOptimized(mst, edge.src, edge.dest)) {
				addEdge(mst, edge.src, edge.dest);
				global_visited[edge.src] = true;
				global_visited[edge.dest] = true;
				minCost += edge.w;
			}
		}

		return minCost;
	}

	public static void main(String[] args) {
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		ArrayList<Integer> zero = new ArrayList<>();
		zero.add(Integer.MAX_VALUE);
		zero.add(5);
		zero.add(Integer.MAX_VALUE);
		zero.add(8);

		ArrayList<Integer> one = new ArrayList<>();
		one.add(5);
		one.add(Integer.MAX_VALUE);
		one.add(Integer.MAX_VALUE);
		one.add(7);

		ArrayList<Integer> two = new ArrayList<>();
		two.add(Integer.MAX_VALUE);
		two.add(Integer.MAX_VALUE);
		two.add(Integer.MAX_VALUE);
		two.add(6);
		
		ArrayList<Integer> three = new ArrayList<>();
		three.add(8);
		three.add(7);
		three.add(6);
		three.add(Integer.MAX_VALUE);

		graph.add(zero);
		graph.add(one);
		graph.add(two);
		graph.add(three);

		System.out.println(MST(graph, 4));

	}

}
