package edu.neu.info6205.project1;

public class Driver {

	public static void main(String[] args) {

		Population p = new Population();
		for (int i = 0; i < 200; i++) {
			p.oneGeneration();
			p.newGen();
		}
		// p.FileOut();

	}
}
