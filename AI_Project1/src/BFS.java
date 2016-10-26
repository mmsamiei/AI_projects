import sun.misc.Queue;




public class BFS<S, A> {
	Problem<S, A> problem;
	
	public BFS(Problem<S, A> problem) {
		
		this.problem = problem;
		search();
	}

	public void search() {
		S first = problem.initiallState();
		Queue<Node<S>> queue = new Queue<Node<S>>();
		queue.enqueue(new Node<S>(first));
		while(true){
			if(queue.isEmpty()){
				System.out.println("eroor");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				Node<S> current;
				try {
					current = queue.dequeue();
					problem.print(current.state);
					for(A a:problem.actions(current.state)){
						Node<S> newNode = new Node<S>(problem.result(current.state, a));
						problem.print(newNode.state);
						queue.enqueue(newNode);
						if(problem.goal(newNode.state)==true){
							System.out.println("222");
							Thread.sleep(1000);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	 
}
class Node<S>{
	Node<S> parent;
	S state;
	double cost;
	public Node(Node<S> parent, S state, double costFromParent) {
		this.parent = parent;
		this.state = state;
		this.cost = parent.cost+costFromParent;	
	}
	public Node(S state){
		this.state=state;
		this.cost=0;
	}
}
