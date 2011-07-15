package edu.hkust.cse.simulator;

import java.util.ArrayList;

import edu.hkust.cse.clusteringEngine.AgglomerativeClustering;

/**
 * @author andrew	Jul 15, 2011
 * this is an experiment of PhoneAdatper
 * (1) randomly generate a set of possible events
 * (2) feed the possible events into inference engine,infer variable dependence and physical constraints from possible events
 * (3) feed the possible events into clustering engine, cluster the data set
 * (4) import a set of events, which will cause potential adaptation faults according to the model checker's investigation
 * (5) rank the result using only clustering engine
 * (6) rank the result using only inference engine
 * (7) how to combine (5) and (6)
 */
public class PhoneAdapterExperiment {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<boolean[]> dataSet=PhoneAdapterSimulator.simulate(1000);
		long start=System.currentTimeMillis();
		AgglomerativeClustering ac=new AgglomerativeClustering();
		ac.importDataset(dataSet);
		//ac.disableDynamicProgramming();
		ac.enableDynamicProgramming();
		//ac.setClusterCompactnessUpperBound(10);
		try{
			ac.build();
		}
		catch(Exception e){
			e.printStackTrace();
			//System.err.println(e.getMessage());
		}
		long end=System.currentTimeMillis();
		System.out.println("it takes "+(end-start)+"ms");
		
		ac.printResult();
	}

}
