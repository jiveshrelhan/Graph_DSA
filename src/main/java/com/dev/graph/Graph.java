package com.dev.graph;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Graph {

	ArrayList<ArrayList<Integer>> graph;

	int NO_OF_VERTICES;;

	Graph(int size) {
		this.NO_OF_VERTICES = size;
		graph = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			ArrayList<Integer> dependents = new ArrayList<>();
			graph.add(dependents);
		}
	}

	Graph(int N, int M, String input) {
		this.NO_OF_VERTICES = N;
		this.graph = new ArrayList<>(N);

		for (int i = 0; i < N; i++) {
			ArrayList<Integer> dependents = new ArrayList<>();
			graph.add(dependents);
		}

		int count = 0;
		while (count <= input.length()) {
			int i = count;
			int rem = count / (2 * M);
			int j = count + (2 * M);
			while (i < j) {

				ArrayList<Integer> dependents = graph.get(rem);

				char value = input.charAt(i);
				if (value == '1') {
					dependents.add(1);
				} else {
					dependents.add(0);
				}
				count += 2;
				i += 2;
			}
		}
	}

	public void printGraph() {
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < this.graph.size(); i++) {
			List<Integer> parent = this.graph.get(i);
			res.append(i);
			for (int j = 0; j < parent.size(); j++) {
				res.append("->" + parent.get(j));
			}

			System.out.print(res.toString());
			System.out.println();
			res.setLength(0);
		}
	}

	public void dfsRecursive(int src, List<? extends List<Integer>> graph, boolean visited[]) {
		visited[src] = true;
		System.out.print(src + " ");
		for (int adj = 0; adj < graph.get(src).size(); adj++) {
			int adjNode = graph.get(src).get(adj);
			if (!visited[adjNode]) {
				dfsRecursive(adjNode, graph, visited);
			}
		}
	}

	public void dfs(int src, ArrayList<? extends List<Integer>> list, boolean vis[]) {

		/*
		 * This temp boolean variable tracks if there is any adj edge from curr source
		 * which not visited.Therefore we need currNode in future we will not pop. In
		 * case all the adj edges are explored we can pop it from stack. Recursive
		 * approach also added.
		 */

		Deque<Integer> stack = new LinkedList<>();
		stack.push(src);
		vis[src] = true;
		boolean temp = false;
		StringBuilder res = new StringBuilder();
		res.append(src + " ");
		while (!stack.isEmpty()) {
			temp = false;
			int popNode = stack.peek();

			for (int adj = 0; adj < list.get(popNode).size(); adj++) {
				int adjNode = list.get(popNode).get(adj);
				if (!vis[adjNode]) {
					vis[adjNode] = true;
					res.append(adjNode + " ");
					stack.push(adjNode);
					temp = true;
					break;
				}

			}
			if (!temp) {
				stack.pop();
			}
		}
		System.out.print(res.toString().trim());
	}

	public void addUnDirectedEdge(int src, int target) {
		this.graph.get(src).add(target);
		this.graph.get(target).add(src);
	}

	public void removeDirectedEdge(int src, int target) {
		this.graph.get(src).remove(((Integer) target));
	}

	public void addEdge(int src, int target) {
		this.graph.get(src).add(target);
	}

	public void addEdges(String edges) {
		String[] arr = edges.split(" ");
		for (int i = 0; i < arr.length - 1;) {
			this.addEdge(Integer.valueOf(arr[i]), Integer.valueOf(arr[i + 1]));
			i += 2;
		}
	}

	public static void main(String[] arg) {
		Graph graph = new Graph(5);
		graph.addEdges("0 1 0 2 0 3 2 4");
		graph.printGraph();
		graph.dfs(0, graph.graph, new boolean[graph.NO_OF_VERTICES]);
		graph.dfsRecursive(0, graph.graph, new boolean[graph.NO_OF_VERTICES]);
	}

}
