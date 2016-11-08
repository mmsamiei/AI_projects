package Problems;

import java.util.Vector;

import algorithmes.AStar;
import algorithmes.BFS;
import algorithmes.DFS;
import algorithmes.UniformedCostSearch;

public class RobotRouting extends
		Problem<RobotRoutingState, RobotRoutingAction> {
	int m, n;

	class Wall {
		int x1, y1, x2, y2;

		public Wall(int x1, int y1, int x2, int y2) {
			super();
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}

	}

	Vector<Wall> walls = new Vector<Wall>();

	public RobotRouting(int m, int n, int[][] wall_array) {
		super();
		this.m = m;
		this.n = n;
		for (int i = 0; i < wall_array.length; i++) {
			Wall wall = new Wall(wall_array[i][0], wall_array[i][1],
					wall_array[i][2], wall_array[i][3]);
			walls.addElement(wall);
		}
	}

	@Override
	public RobotRoutingState initiallState() {
		return new RobotRoutingState(1, 1);
	}

	@Override
	public Vector<RobotRoutingAction> actions(RobotRoutingState state) {
		int x = state.x, y = state.y;
		Vector<RobotRoutingAction> actions = new Vector<RobotRoutingAction>();
		boolean L, R, U, D;
		L = R = U = D = true;
		for (Wall w : walls) {
			if (w.x1 == x && w.y1 == y && w.x2 == w.x1 - 1 && w.y2 == w.y1)
				L = false;
			if (w.x1 == x && w.y1 == y && w.x2 == w.x1 + 1 && w.y2 == w.y1)
				R = false;
			if (w.x1 == x && w.y1 == y && w.x2 == w.x1 && w.y2 == w.y1 + 1)
				D = false;
			if (w.x1 == x && w.y1 == y && w.x2 == w.x1 && w.y2 == w.y1 - 1)
				U = false;
		}
		if (x == 1)
			L = false;
		if (x == m)
			R = false;
		if (y == 1)
			U = false;
		if (y == n)
			D = false;

		if (L)
			actions.addElement(new RobotRoutingAction('L'));
		if (R)
			actions.addElement(new RobotRoutingAction('R'));
		if (U)
			actions.addElement(new RobotRoutingAction('U'));
		if (D)
			actions.addElement(new RobotRoutingAction('D'));
		return actions;
	}

	@Override
	public RobotRoutingState result(RobotRoutingState state,
			RobotRoutingAction action) {

		int tx = state.x;
		int ty = state.y;
		switch (action.dir) {
		case 'L':
			tx--;
			break;
		case 'R':
			tx++;
			break;
		case 'U':
			ty--;
			break;
		case 'D':
			ty++;
			break;
		}
		return new RobotRoutingState(tx, ty);
	}

	@Override
	public double cost(RobotRoutingState father, RobotRoutingState child,
			RobotRoutingAction action) {
		return 1;
	}

	@Override
	public boolean goal(RobotRoutingState state) {
		if (state.x == m && state.y == n)
			return true;
		return false;
	}

	@Override
	public void print(RobotRoutingState state) {
		System.out.println(state.x + " , " + state.y);
	}

	public static void main(String[] args) {
		int[][] a = new int[3][4];
		a[0][0] = 2;
		a[0][1] = 1;
		a[0][2] = 3;
		a[0][3] = 1;
		a[1][0] = 2;
		a[1][1] = 2;
		a[1][2] = 3;
		a[1][3] = 2;
		a[2][0] = 2;
		a[2][1] = 3;
		a[2][2] = 3;
		a[2][3] = 3;
		RobotRouting robotRouting = new RobotRouting(3, 3, a);
		// UniformedCostSearch<RobotRoutingState, RobotRoutingAction> u = new
		// UniformedCostSearch<RobotRoutingState, RobotRoutingAction>(
		// robotRouting);
		// u.graphSearch();
		/*DFS<RobotRoutingState, RobotRoutingAction> dfs = new DFS<RobotRoutingState, RobotRoutingAction>(
				robotRouting);
		dfs.graphSearch();*/
		AStar<RobotRoutingState, RobotRoutingAction> aStar = new AStar<RobotRoutingState,RobotRoutingAction>(robotRouting);
		aStar.treeSearch();
	}

	@Override
	public double heuristic(RobotRoutingState state) {
		int tx = m - state.x;
		int ty = n - state.y;
		return Math.sqrt(tx*tx + ty*ty);
	}
}

class RobotRoutingState {
	int x, y;

	@Override
	public boolean equals(Object obj) {
		RobotRoutingState other = (RobotRoutingState) obj;
		if (other.y == this.y && other.x == this.x)
			return true;
		return false;
	}

	public RobotRoutingState(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class RobotRoutingAction {
	char dir;

	public RobotRoutingAction(char move) {
		this.dir = move;
	}
}