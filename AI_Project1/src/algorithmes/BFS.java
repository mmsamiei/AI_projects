package algorithmes;

import Problems.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

import javax.swing.plaf.nimbus.State;

import algorithmes.DFS.Node;
import Problems.Problem;

public class BFS<S, A> {

	class Node<S> {
		Node<S> parent;
		S state;
		double cost;

		public Node(Node<S> parent, S state, double costFromParent) {
			this.parent = parent;
			this.state = state;
			this.cost = parent.cost + costFromParent;
		}

		public Node(S state) {
			this.state = state;
			this.cost = 0;
		}
	}

	Problem<S, A> problem;

	public BFS(Problem<S, A> problem) {

		this.problem = problem;
	}

	public void treeSearch() {
		int observedNodes = 0;
		int expandedNodes = 0;
		int maxQueueSize = 0;
		S first = problem.initiallState();
		observedNodes++;
		Queue<Node<S>> queue = new LinkedList<Node<S>>();
		queue.add(new Node<S>(first));
		while (true) {
			if (queue.isEmpty()) {
				System.out.println("error");
				System.out.println("I can't find Soluton");
				return;
			} else {
				Node<S> current;
				current = queue.poll();
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
						System.out.println("I have expanded " + expandedNodes
								+ " nodes");
						System.out.println("the path is:\n");
						printPath(newNode);
						System.out.println("\nthis cost of path is : "
								+ newNode.cost);
						System.out.println("the Max Memory usage is "
								+ maxQueueSize + " nodes\n");
						System.out
								.println("\t***********************************************");
						return;
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
		Queue<Node<S>> queue = new LinkedList<Node<S>>();
		queue.add(new Node<S>(first));
		Vector<Node<S>> explored = new Vector<Node<S>>();
		while (true) {
			if (queue.isEmpty()) {
				System.out.println("error");
				System.out.println("I can't find Soluton");
				return;
			} else {
				Node<S> current;
				current = queue.poll();
				expandedNodes++;
				for (A a : problem.actions(current.state)) {
					Node<S> newNode = new Node<S>(current, problem.result(
							current.state, a), problem.cost(current.state,
							problem.result(current.state, a), a));
					if (!isInVector(explored, newNode.state)) {
						observedNodes++;
						queue.add(newNode);
					}
					if (problem.goal(newNode.state) == true) {
						System.out.println("I have found Solution");
						System.out.println("I have seen " + observedNodes
								+ " nodes");
						System.out.println("I have expanded " + expandedNodes
								+ " nodes");
						System.out.println("the path is:\n");
						printPath(newNode);
						System.out.println("\nthis cost of path is : "
								+ newNode.cost);
						System.out.println("the Max Memory usage is "
								+ maxQueueSize + " nodes\n");
						System.out
								.println("\t***********************************************");
						return;
					}
				}
			}
			if (maxQueueSize < queue.size())
				maxQueueSize = queue.size();
		}
	}

	public boolean isInVector(Vector<Node<S>> vector, S s) {
		for (Node<S> node : vector) {
			if (node.state.equals(s)) {
				return true;
			}
		}
		return false;
	}

	public void printPath(Node<S> node) {
		if (node.parent == null) {
			problem.print(node.state);
		} else {
			printPath(node.parent);
			problem.print(node.state);
		}
	}

}
