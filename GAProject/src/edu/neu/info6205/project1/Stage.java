package edu.neu.info6205.project1;

import java.util.Random;

public class Stage {
	// This is the class of the map and the Gabage will occure on the map.
	// using the 2D array to present the stage.
	private int[][] stage;
	private int size;

	// constructor
	// need to make sure every stage will have the same amount of targets.
	public Stage(int size) {
		this.size = size;
		this.stage = new int[size][size];
		// EXP: we will generat a 10*10 map
		// and add 50 garbage into the map randomly.
		// Half of the space
		this.initialStage();
	}

	public void initialStage() {
		// initial a map and put the garbage randomly
		int halfspace = (this.size * this.size) / 2;
		int count = 0;
		Random random = new Random();
		// make sure that there are half of the nodes have garbage
		while (count < halfspace) {
			// return 0~9
			int col = random.nextInt(10);
			int row = random.nextInt(10);
			if (stage[row][col] != 1) {
				stage[row][col] = 1;
				count++;
			}
		}

	}

	// @Override the default constructor
	public Stage() {
		this(10);
	}

	@Override
	public String toString() {
		// use this function to print the map
		StringBuilder sb = new StringBuilder();
		for (int[] is : stage) {
			for (int i : is) {
				sb.append(i + "|");
			}
			sb.append("\n--------------------\n");
		}
		return sb.toString();
	}

	// getter and setter?
	public int[][] getStage() {
		// get the whole map
		return stage;
	}

	public void setStage(int[][] stage) {
		// Given a 2D Array, set as the map
		// use to pick up the garbage / put the garbage
		this.stage = stage;
	}

	public void updateStage(int x, int y, int status) {
		if (status == 0 || status == 1) {
			this.stage[x][y] = status;
		}
	}

	// get the value of the specific node
	public int getState(int row, int col) {
		if (row >= size || col >= size || row < 0 || col < 0) {
			// The wall(Out of Bound)
			return 2;
		} else {
			// return 0: Nothing there;
			// return 1: Trash in the node;
			return stage[row][col];
		}
	}

	public int getAroundState(int x, int y) {
		// mid
		int mid = getState(x, y);
		// up
		int up = getState(x, y + 1);
		// down
		int down = getState(x, y - 1);
		// right
		int right = getState(x + 1, y);
		// left
		int left = getState(x - 1, y);
		// Convert the status of the node in to a 5-digit Num and each digit represent
		// the status of the node
		return mid + up * 10 + down * 100 + right * 1000 + left * 10000;
	}

	public boolean isWall(int x, int y) {
		// Giving a point on the stagea and check if wall?
		// x: 0~9 y:0~9 size =10
		if (x >= size || y >= size || x < 0 || y < 0)
			return true;
		return false;
	}

	// public boolean checkJar(int x, int y) {
	// // I think this function is used to pick up the Garbage.
	// if (stage[x][y] == 1) {
	// stage[x][y] = 0;
	// return true;
	// } else {
	// return false;
	// }
	// }

}
