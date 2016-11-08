package algorithmes;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

import javax.swing.plaf.nimbus.State;

import algorithmes.UniformedCostSearch.Node;
import Problems.Problem;

public class DFS<S, A> {

	class Node<S> {
		Node<S> parent;
		S state;
		double cost;
		int depth;

		public Node(Node<S> parent, S state, double costFromParent) {
			this.parent = parent;
			this.state = state;
			this.cost = parent.cost + costFromParent;
			this.depth = parent.depth + 1;
		}

		public Node(S state) {
			this.state = state;
			this.cost = 0;
			this.depth = 0;
		}
	}

	Problem<S, A> problem;
	int limit;
	boolean limited_depth;

	public DFS(Problem<S, A> problem, int limit) {

		this.problem = problem;
		limited_depth = true;
		this.limit = limit;
	}

	public DFS(Problem<S, A> problem) {

		this.problem = problem;
		limited_depth = false;
		this.limit = Integer.MAX_VALUE;
	}

	public void treeSearch() {
		int observedNodes = 0;
		int expandedNodes = 0;
		int maxQueueSize = 0;
		S first = problem.initiallState();
		observedNodes++;
		Stack<Node<S>> queue = new Stack<Node<S>>();
		queue.add(new Node<S>(first));
		while (true) {
			if (queue.isEmpty()) {
				System.out.println("error");
				System.out.println("I can't find Soluton");
				return;
			} else {
				Node<S> current;
				current = queue.pop();
				if (!limited_depth || current.depth < limit) {
					expandedNodes++;
					for (A a : problem.actions(current.state)) {
						Node<S> newNode = new Node<S>(current, problem.result(
								current.state, a), problem.cost(current.state,
								problem.result(current.state, a), a));
						observedNodes++;
						queue.add(newNode);
						if (problem.goal(newNode.state) == true) {
							System.out.println("I have found Solution");
							System.out.println("I have seen " + observedNodes
									+ " nodes");
							System.out.println("I have expanded "
									+ expandedNodes + " nodes");
							System.out.println("the path is:\n");
							printPath(newNode);
							System.out.println("\nthis cost of path is : "
									+ newNode.cost);
							System.out.println("the Max Memory usage is "
									+ maxQueueSize + " nodes\n");
							return;
						}
					}
				}
			}
			if (maxQueueSize < queue.size())
				maxQueueSize = queue.size();
		}
	}

	public void graphSearch() {
		int observedNodes = 0;
		int expandedNodes = 0;
		int maxQueueSize = 0;
		S first = problem.initiallState();
		observedNodes++;
		Stack<Node<S>> queue = new Stack<Node<S>>();
		Vector<S> explored = new Vector<S>();
		queue.add(new Node<S>(first));
		while (true) {
			if (queue.isEmpty()) {
				System.out.println("error");
				System.out.println("I can't find Soluton");
				return;
			} else {
				Node<S> current;
				current = queue.pop();
				explored.addElement(current.state);
				if (!limited_depth || current.depth < limit) {
					expandedNodes++;
					for (A a : problem.actions(current.state)) {
						Node<S> child = new Node<S>(current, problem.result(
								current.state, a), problem.cost(current.state,
								problem.result(current.state, a), a));
						if (!explored.contains(child.state)
								&& !isInFrontier(queue, child.state)) {
							queue.add(child);
							observedNodes++;
							if (problem.goal(child.state) == true) {
								System.out.println("I have found Solution");
								System.out.println("I have seen "
										+ observedNodes + " nodes");
								System.out.println("I have expanded "
										+ expandedNodes + " nodes");
								System.out.println("the path is:\n");
								printPath(child);
								System.out.println("\nthis cost of path is : "
										+ child.cost);
								System.out.println("the Max Memory usage is "
										+ maxQueueSize + " nodes\n");
								return;
							}
						}
					}
				}
			}
			if (maxQueueSize < queue.size())
				maxQueueSize = queue.size();
		}
	}

	public void printPath(Node<S> node) {
		if (node.parent == null) {
			problem.print(node.state);
		} else {
			printPath(node.parent);
			problem.print(node.state);
		}
	}

	public boolean isInFrontier(Stack<Node<S>> queue, S s) {
		LinkedList<Node<S>> linkedList = new LinkedList<Node<S>>(queue);
		for (Node<S> node : linkedList) {
			if (node.state.equals(s)) {
				return true;
			}
		}
		return false;
	}

}
