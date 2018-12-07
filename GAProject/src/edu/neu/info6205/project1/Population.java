package edu.neu.info6205.project1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author yin.haoyu
 *
 *         Update: directly kill some robot after each generation
 */
public class Population {

	private int size;
	private double crossProbability;
	private double mutationProbability;
	private int maxVariCount;
	private Robi[] robis;
	private Random rm;
	private int gen;
	// generation
	private int dead;
	// store every Robis score
	private List<Double> average;
	private List<Double> max;

	public int getSize() {
		return this.size;
	}

	public int robiesnum() {
		return this.robis.length;
	}

	// Constructor
	public Population() {
		this.size = 200;
		this.crossProbability = 0.82;
		this.mutationProbability = 0.078;
		this.maxVariCount = 10;
		robis = new Robi[size];
		for (int i = 0; i < robis.length; i++) {
			// The new Robi will have random Gene
			robis[i] = new Robi();
		}
		rm = new Random();
		this.gen = 1;// record the recent generation
		this.dead = size / 4;
		this.average = new ArrayList<>();
		this.max = new ArrayList<>();
	}

	public Population(int groupsize) {
		this.size = groupsize;
		// this.crossProbability = 0.82;
		// this.mutationProbability = MP;
		// this.maxVariCount = 10;
		robis = new Robi[size];
		for (int i = 0; i < robis.length; i++) {
			// The new Robi will have random Gene
			robis[i] = new Robi();
		}
		rm = new Random();
		this.gen = 1;// record the recent generation
		this.dead = size / 4;
		this.average = new ArrayList<>();
		this.max = new ArrayList<>();
	}

	// public Population(int groupsize,double mp) {
	// this.size = groupsize;
	//// this.crossProbability = 0.82;
	// this.mutationProbability = mp;
	//// this.maxVariCount = 10;
	// robis = new Robi[size];
	// for (int i = 0; i < robis.length; i++) {
	// // The new Robi will have random Gene
	// robis[i] = new Robi();
	// }
	// rm = new Random();
	// this.gen = 1;// record the recent generation
	// this.dead = size / 4;
	// this.average = new ArrayList<>();
	// this.max = new ArrayList<>();
	// }

	// simulate one generation
	public void oneGeneration() {
		double sum = 0;
		for (int j = 0; j < size; j++) {
			robis[j].start(1000);
			sum += robis[j].getAvgGrade();
		}
		System.out.println((gen++) + " generation!");
		System.out.println("average : " + sum / size);
		average.add(sum / size);

		Arrays.sort(robis, (r1, r2) -> Double.compare(r1.getAvgGrade(), r2.getAvgGrade()));
		System.out.println("max :" + robis[robis.length - 1].getAvgGrade());
		max.add(robis[robis.length - 1].getAvgGrade());
	}

	// kill some
	public void kill() {
		// update the robis object
		robis = Arrays.copyOfRange(robis, dead, size);
	}

	// generate probabilities
	private double[] generateProbabilities() {
		double total = 0;
		double[] weight = new double[robis.length];
		double plus = 0;
		// make sure all weight is positive
		if (robis[0].getAvgGrade() <= 0) {
			plus = -robis[0].getAvgGrade() + 1;
		}
		for (int i = 0; i < robis.length; i++) {
			weight[i] = robis[i].getAvgGrade() + plus;
			total += weight[i];
		}
		for (int i = 0; i < robis.length; i++) {
			weight[i] = weight[i] / total;
		}
		return weight;
	}

	// gene mutation
	public int[] Mutation(int[] gene) {
		if (Math.random() < mutationProbability) {
			for (int i = 0; i < rm.nextInt(maxVariCount); i++) {
				// set the random index of the gene to another random int.
				gene[rm.nextInt(gene.length)] = rm.nextInt(7);
			}
			return gene;
		} else {
			return gene;
		}
	}

	// select the index of parents according to probabilities
	private int selectParent(double[] probs) {
		double sum = 0.0;
		// calculate the sum of all probability
		for (int i = 0; i < probs.length; i++) {
			sum += probs[i];
		}

		double r = Math.random() * sum;
		sum = 0.0;
		for (int i = 0; i < probs.length; i++) {
			sum += probs[i];
			if (sum > r)
				return i;
		}
		return probs.length - 1;
	}

	// generate two children gene from two parents
	private int[][] geneCross(Robi a, Robi b) {
		int[] aStrategy = a.getStrategys();
		int[] bStrategy = b.getStrategys();
		int[] aNew = new int[243];
		int[] bNew = new int[243];
		int split = rm.nextInt(243);
		// will split at a random index
		if (Math.random() < crossProbability) {
			int k;
			for (k = 0; k < split; k++) {
				aNew[k] = aStrategy[k];
				bNew[k] = bStrategy[k];
			}
			for (; k < 243; k++) {
				bNew[k] = aStrategy[k];
				aNew[k] = bStrategy[k];
			}
		} else {
			aNew = aStrategy;
			bNew = bStrategy;
		}
		int[][] children = { aNew, bNew };
		return children;
	}

	// generate two children according to the probability
	private Robi[] reproduce(double[] weight) {
		Robi parentA = robis[selectParent(weight)];
		Robi parentB = robis[selectParent(weight)];
		int[][] children = geneCross(parentA, parentB);

		Robi childrenA = new Robi(Mutation(children[0]));
		Robi childrenB = new Robi(Mutation(children[1]));
		Robi[] res = { childrenA, childrenB };
		return res;
	}

	// generate new generation
	public void newGen() {
		kill();
		double weight[] = generateProbabilities();
		Robi[] newRobis = new Robi[size];
		//
		for (int i = 0; i < size / 2; i++) {
			Robi[] children = reproduce(weight);
			newRobis[2 * i] = children[0];
			newRobis[2 * i + 1] = children[1];
		}
		this.robis = newRobis;

	}

	// output to csv
	public void FileOut() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("result.csv")));) {
			for (int i = 0; i < average.size(); i++) {
				bw.write(i + "," + average.get(i) + "," + max.get(i));
				bw.newLine();
			}
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
