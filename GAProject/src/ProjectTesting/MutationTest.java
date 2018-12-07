package ProjectTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import edu.neu.info6205.project1.Population;
import edu.neu.info6205.project1.Robi;

class MutationTest {

	@Test
	void geneTest() {
		Robi r1 = new Robi();
		int[] gene = r1.getStrategys();
		assertEquals(243, gene.length);
	}

	@Test
	void populationTest() {
		Population p1 = new Population(100);
		// generate a population with only 100 robi
		int size = p1.getSize();
		assertEquals(100, size);
		p1.newGen();
		// make sure that the whole population of each generation are the same
		assertEquals(100, p1.getSize());
	}

	@Test
	void selectionTest() {
		Robi r1 = new Robi();
		Population p1 = new Population(100);
		p1.oneGeneration();
		p1.kill();
		// defalut setting will kill 1/4 of the robies
		assertEquals(p1.robiesnum(), 75);

	}

	@Test
	void MutationTest() {
		Population p = new Population();
		Robi r = new Robi();
		int[] strategy = r.getStrategys();
		int[] newStrategy = p.Mutation(Arrays.copyOf(r.getStrategys(), 243));
		int count = 0;
		for (int i = 0; i < newStrategy.length; i++) {
			if (strategy[i] != newStrategy[i])
				count++;
		}
		boolean res = false;
		if (count <= 10)
			res = true;
		assertEquals(res, true);
	}

}
