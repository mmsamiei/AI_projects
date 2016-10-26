import java.util.Vector;

public class NQueens extends Problem<NQueenState, NQueenAction> {
	int n;

	public NQueens(int n) {
		this.n = n;
	}

	@Override
	NQueenState initiallState() {
		int[] temp = new int[n];
		for (int i = 0; i < n; i++)
			temp[i] = -1;
		NQueenState nQueenState = new NQueenState(n, temp);
		return nQueenState;
	}

	@Override
	Vector<NQueenAction> actions(NQueenState state) {

		Vector<NQueenAction> actions = new Vector<>(0);
		int put_time = 0;
		for (int i = 0; i < n; i++) {
			if (state.board[i] != -1)
				put_time++;
		}
		if (put_time == n)
			return actions;

		boolean[] can = new boolean[n];
		for (int i = 0; i < n; i++)
			can[i] = true;

		for (int i = 0; i < put_time; i++) {
			can[state.board[i]] = false;
		}

		for (int i = 0; i < n; i++) {
			if (can[i] == true)
				actions.addElement(new NQueenAction(i));
		}
		return actions;
	}

	@Override
	NQueenState result(NQueenState state, NQueenAction action) {
		int[] temp = new int[n];
		for (int i = 0; i < n; i++)
			temp[i] = -1;

		for (int i = 0; i < n; i++) {
			if (state.board[i] == -1) {
				temp[i] = action.actionNum;
				break;
			} else {
				temp[i] = state.board[i];
			}
		}
		NQueenState result = new NQueenState(n, temp);
		return result;
	}

	@Override
	double cost(NQueenState father, NQueenState child, NQueenAction action) {
		return 1;
	}

	@Override
	boolean goal(NQueenState state) {
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
		BFS<NQueenState, NQueenAction> bfs = new BFS<>(nQueens);
	}

	@Override
	void print(NQueenState state) {
		for (int i = 0; i < n; i++)
			System.out.print(state.board[i] + "\t");
		System.out.println();
	}
}

class NQueenState {
	int n;
	int[] board;

	public NQueenState(int n, int[] input_board) {
		board = new int[n];
		for (int i = 0; i < n; i++)
			board[i] = input_board[i];
	}
}

class NQueenAction {
	int actionNum;

	public NQueenAction(int actionNum) {
		this.actionNum = actionNum;
	}
}