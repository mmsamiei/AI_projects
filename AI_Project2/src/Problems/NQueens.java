package Problems;

import java.util.Vector;

import algorithmes.HillClimbing;
import algorithmes.SimulatedAnnealing;

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

		/*if (state.turn == n)
			return actions;*/

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

		NQueenState result = new NQueenState(n, temp, (state.turn + 1)%8);
		return result;
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
		/*HillClimbing<NQueenState, NQueenAction> hillClimbing = new HillClimbing<>(
				nQueens);*/
		//hillClimbing.first_choice();
		//hillClimbing.simple();
		//hillClimbing.Stochastic();
		//hillClimbing.random_restart();
		
		SimulatedAnnealing<NQueenState, NQueenAction> simulatedAnnealing;
		simulatedAnnealing = new SimulatedAnnealing<>(nQueens);
		simulatedAnnealing.search(2);
		

	}

	@Override
	public void print(NQueenState state) {
		for (int i = 0; i < n; i++)
			System.out.print(state.board[i] + "\t");
		System.out.println();
	}



	@Override
	public double objective_function(NQueenState state) {
		int collision = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (Math.abs(state.board[i] - state.board[j]) == Math
						.abs(i - j)) {
					collision++;
				}
			}
		}
		return (-collision);
	}

	@Override
	public NQueenState randomState() {
		int[] board = new int[n];
		boolean[] flag = new boolean[n];
		for (int i = 0; i < n; i++) {
			flag[i] = false;// means that n'th row hasn't value
		}
		for (int i = 0; i < n; i++) {
			while (true) {
				int val = (int) (Math.random() * n);
				if (flag[val] == false) {
					flag[val] = true;
					board[val] = i;
					break;
				}
			}
		}
		return new NQueenState(n, board, 0);
	}

	@Override
	public NQueenState crossOver(NQueenState x, NQueenState y) {
		return null;
	}

	@Override
	public NQueenState mutation(NQueenState x) {
		return null;
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