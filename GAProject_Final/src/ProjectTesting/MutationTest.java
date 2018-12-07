package ProjectTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import edu.neu.info6205.project1.Population;
import edu.neu.info6205.project1.Robi;

class MutationTest {

	@Test
	void geneTest() {
		Robi r1=new Robi();
		int[] gene=r1.getStrategys();
		assertEquals(243,gene.length);
	}
	
	@Test
	void populationTest() {
		Population p1=new Population(100);
		//generate a population with only 100 robi
		int size=p1.getSize();
		assertEquals(100,size);
		p1.newGen();
		//make sure that the whole population of each generation are the same 
		assertEquals(100,p1.getSize());
	}
	

}
