package edu.neu.info6205.project1;

import java.util.Random;

public class Robi {

	// The gene is the strategys int[]
	private int[] strategys = new int[243];
	// 3^5 = 243
	private int grade;
	private int moveTimes;
	// Each Robi will have its own stage
	private Stage stage;
	private double avgGrade;

	Random rm;
	// some constant fileds
	private static final int getJar = 10;
	private static final int getNothing = -1;
	private static final int knockWall = -5;
	// the position of robi
	private int x;
	private int y;

	// Pass the stage to Robi
	public Robi(Stage stage) {
		this();
		this.stage = stage;
	}

	public Robi() {
		rm = new Random();
		for (int i = 0; i < strategys.length; i++) {
			strategys[i] = rm.nextInt(7);
			// There are 7 type of movements
			// 0--> move left
			// 1--> move right
			// 2--> move down
			// 3--> move up
			// 4--> random move (L,R,U,D);
			// 5--> pick up garbage
			// 6--> do nothing
		}
		this.moveTimes = 200;
		this.x = 0;
		this.y = 0;
	}

	// Why so many constructor?
	public Robi(int[] strategys) {
		this();
		if (strategys.length == 243) {
			this.strategys = strategys;
		}
	}

	private void move(int i) {
		switch (i) {
		case 0:
			move(x - 1, y);
			break;
		case 1:
			move(x + 1, y);
			break;
		case 2:
			move(x, y - 1);
			break;
		case 3:
			move(x, y + 1);
			break;
		case 4:
			move(rm.nextInt(4));
			break;
		case 5:
			pickJar(x, y);
			break;
		case 6:
			break;
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void start() {
		grade = 0;
		int aroundState;
		int strategy;
		for (int i = 0; i < moveTimes; i++) {
			// have been converted
			aroundState = Integer.parseInt(Integer.toString(stage.getAroundState(x, y)), 3);

			strategy = strategys[aroundState];
			move(strategy);
			// System.out.println(i + "times move!!!");
			// System.out.println("arround : " + stage.getAroundState(x, y) + "--->"
			// +aroundState);
			// System.out.println("startegy : " + strategy);
			// System.out.println("location:" + x + "," + y);
		}
	}

	public void start(int times) {
		int sum = 0;
		for (int i = 0; i < times; i++) {
			setStage(new Stage());
			start();
			sum += this.grade;
		}

		this.avgGrade = (double) sum / times;
	}

	public double getAvgGrade() {
		return avgGrade;
	}

	public int[] getStrategys() {
		return strategys;
	}

	public void setStrategys(int[] strategys) {
		this.strategys = strategys;
	}

	private void move(int x1, int y1) {
		if (stage.isWall(x1, y1)) {
			// if the movement chosen hit the wall then stay here and count the grade
			grade = grade + knockWall;
		} else {
			// set Robi to the point after movement
			x = x1;
			y = y1;
		}
	}

	private void pickJar(int x1, int y1) {
		if (stage.getState(x1, y1) == 1) {
			// There is a garbage
			stage.updateStage(x1, y1, 0);
			// pick up it and upgrade the grade
			grade += getJar;
		} else {
			grade += getNothing;
		}
	}

	public int getGrade() {
		return grade;
	}

	public Stage getStage() {
		return this.stage;
	}

}
