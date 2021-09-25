package com.dev.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class DetectCycle {
	static boolean isCyclic(ArrayList<ArrayList<Integer>> list, int V) {
		boolean[] visited = new boolean[V];

		for (int i = 0; i < list.size(); i++) {

			ArrayList<Integer> dependents = list.get(i);

			for (int j = 0; j < dependents.size(); j++) {
				int node = dependents.get(j);
				if (!visited[node]) {
					if (DFS(-1, node, list, V, visited)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	static boolean isCyclic2(ArrayList<ArrayList<Integer>> list, int V) {
		boolean[] visited = new boolean[V];

		for (int i = 0; i < list.size(); i++) {

			ArrayList<Integer> dependents = list.get(i);

			for (int j = 0; j < dependents.size(); j++) {
				int node = dependents.get(j);
				if (!visited[node]) {
					if (BFS(node, list, visited)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	
	
	static boolean BFS(int curr, ArrayList<ArrayList<Integer>> list, boolean[] visited) {
		
        int[] result = new int[list.size()];

        Collections.reverse(Arrays.stream(result).boxed().collect(Collectors.toList()));
		
		Queue<Integer> queue = new LinkedList<>();
		int parent = -1;
		queue.offer(curr);
		
		Stack<Integer> stack = new Stack<>();
				
		while(!queue.isEmpty()) {
			
			int pop = queue.remove();
			visited[pop] = true;
			ArrayList<Integer> dependents = list.get(pop);
			
			for(int i=0;i<dependents.size();i++) {
				int child = dependents.get(i);
				if(visited[child]) {
					if(child!=parent) {
						return true;
					}
				}
				else {
					queue.offer(child);
				}
			}
		

			
		}
		
		return false;
		
	}

	static boolean DFS(int parent, int curr, ArrayList<ArrayList<Integer>> list, int V, boolean[] visited) {

		visited[curr] = true;

		ArrayList<Integer> dependents = list.get(curr);

		for (int i = 0; i < dependents.size(); i++) {
			int newCurrent = dependents.get(i);
			if (!visited[newCurrent]) {
				if (DFS(curr, newCurrent, list, V, visited))
					return true;
			} else if (newCurrent != parent) {
				return true;
			}
		}

		return false;
	}

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<>();
		list.add(1);
		ArrayList<Integer> list1 = new ArrayList<>();
		list1.add(0);
		list1.add(2);
		ArrayList<Integer> list2 = new ArrayList<>();
		list2.add(3);
		list2.add(1);
		ArrayList<Integer> list3 = new ArrayList<>();
		list3.add(2);
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		graph.add(0, list);
		graph.add(list1);
		graph.add(list2);
		graph.add(list3);
		System.out.println(isCyclic2(graph, 4));
	}
}
