package Problems;

import algorithmes.*;

import java.util.Vector;

public class NQueens extends Problem<NQueenState, NQueenAction> {
	int n;

	public NQueens(int n) {
		this.n = n;
	}

	@Override
	public NQueenState initiallState() {
		int[] temp = new int[n];
		for (int i = 0; i < n; i++)
			temp[i] = i;
		NQueenState nQueenState = new NQueenState(n, temp, 0);
		return nQueenState;
	}

	@Override
	public Vector<NQueenAction> actions(NQueenState state) {

		Vector<NQueenAction> actions = new Vector<>(0);

		if (state.turn == n)
			return actions;

		for (int i = 0; i < n; i++) {
			actions.addElement(new NQueenAction(i));
		}
		return actions;
	}

	@Override
	public NQueenState result(NQueenState state, NQueenAction action) {
		int[] temp = new int[n];
		for (int i = 0; i < n; i++)
			temp[i] = state.board[i];

		int tempInt = temp[state.turn];
		temp[state.turn] = temp[action.actionNum];
		temp[action.actionNum] = tempInt;

		NQueenState result = new NQueenState(n, temp, state.turn + 1);
		return result;
	}

	@Override
	public double cost(NQueenState father, NQueenState child,
			NQueenAction action) {
		return 1;
	}

	@Override
	public boolean goal(NQueenState state) {
		for (int i = 0; i < n; i++)
			if (state.board[i] == -1)
				return false;
		for (int i = 0; i < n; i++)
			for (int j = i + 1; j < n; j++) {
				if (Math.abs(i - j) == Math
						.abs(state.board[i] - state.board[j]))
					return false;
			}
		return true;
	}

	public static void main(String[] args) {
		NQueens nQueens = new NQueens(8);
		
		  UniformedCostSearch<NQueenState, NQueenAction> ufg = new
		  UniformedCostSearch<>( nQueens); ufg.graphSearch();
		 
		
		  /*DFS<NQueenState, NQueenAction> dfs= new DFS<NQueenState,
		  NQueenAction>(nQueens,9); dfs.treeSearch();*/
		 
		/*BFS<NQueenState, NQueenAction> bfs = new BFS<NQueenState, NQueenAction>(
				nQueens);
		bfs.graphSearch();*/
		/*(AStar<NQueenState, NQueenAction> aStar = new AStar<NQueenState,NQueenAction>(nQueens);
		aStar.treeSearch();*/
	}

	@Override
	public void print(NQueenState state) {
		for (int i = 0; i < n; i++)
			System.out.print(state.board[i] + "\t");
		System.out.println();
	}

	@Override
	public double heuristic(NQueenState state) {
		int maxQueenInDia = 0;
		//	finding the max number of Queens in a SouthWest to NorthEast Diameter
		for (int s = 0; s <= 2 * (state.n) - 2; s++) {
			int temp = 0;
			for (int i = 0; i < n; i++) {
				if (i + state.board[i] == s) {
					temp++;
				}
			}
			if(maxQueenInDia < temp)
				maxQueenInDia =temp;
		}
//		finding the max number of Queens in a SouthWest to NorthEast Diameter
			for (int s = 0; s <= 2 * (state.n) - 2; s++) {
				int temp = 0;
				for (int i = 0; i < n; i++) {
					if (state.board[i] - i + 3 == s) {
						temp++;
					}
				}
				if(maxQueenInDia < temp)
					maxQueenInDia =temp;
			}
		
		
		return (maxQueenInDia)/2;
	}
}

class NQueenState {
	int n;
	int turn;
	int[] board;

	public NQueenState(int n, int[] input_board, int turn) {
		board = new int[n];
		for (int i = 0; i < n; i++)
			board[i] = input_board[i];
		this.turn = turn;
	}

	@Override
	public boolean equals(Object arg0) {
		NQueenState other = (NQueenState) arg0;
		for (int i = 0; i < other.board.length; i++) {
			if (board[i] != other.board[i]) {
				return false;
			}
		}
		return true;
	}
}

class NQueenAction {
	int actionNum;

	public NQueenAction(int actionNum) {
		this.actionNum = actionNum;
	}
}