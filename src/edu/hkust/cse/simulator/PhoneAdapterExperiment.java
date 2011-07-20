package edu.hkust.cse.simulator;

import java.util.ArrayList;

import edu.hkust.cse.clusteringEngine.AgglomerativeClustering;
import edu.hkust.cse.detector.ContextOperator;
import edu.hkust.cse.detector.ContextType;
import edu.hkust.cse.detector.Main;
import edu.hkust.cse.detector.PCVAssignmentsWithIndex;
import edu.hkust.cse.detector.PropositionalContextVar;
import edu.hkust.cse.inferenceEngine.ConfidenceTable;
import edu.hkust.cse.inferenceEngine.Constraint;
import edu.hkust.cse.inferenceEngine.ConstraintType;

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
	
//	private static PropositionalContextVar<Boolean> Agps=new PropositionalContextVar<Boolean>((short)0, "Agps", ContextType.GPS_VALID,ContextOperator.EQUAL, true);
//	private static PropositionalContextVar<String> Bgps=new PropositionalContextVar<String>((short)1, "Bgps", ContextType.GPS_LOCATION, ContextOperator.EQUAL,"HOME" );
//	private static PropositionalContextVar<String> Cgps=new PropositionalContextVar<String>((short)2, "Cgps", ContextType.GPS_LOCATION, ContextOperator.EQUAL, "OFFICE");
//	private static PropositionalContextVar<Integer> Dgps=new PropositionalContextVar<Integer>((short)3, "Dgps", ContextType.GPS_SPEED, ContextOperator.GREATER, 5);
//	private static PropositionalContextVar<Integer> Egps=new PropositionalContextVar<Integer>((short)4, "Egps", ContextType.GPS_SPEED, ContextOperator.GREATER, 70);
//	private static PropositionalContextVar<String> Abt=new PropositionalContextVar<String>((short)5, "Abt", ContextType.BT, ContextOperator.EQUAL, "CAR_HANDSFREE");
//	private static PropositionalContextVar<String> Bbt=new PropositionalContextVar<String>((short)6, "Bbt", ContextType.BT, ContextOperator.EQUAL, "HOME_PC");
//	
//	private static PropositionalContextVar<String> Cbt=new PropositionalContextVar<String>((short)7, "Cbt", ContextType.BT, ContextOperator.EQUAL, "OFFICE_PC");
//	
//	private static PropositionalContextVar<String> Dbt=new PropositionalContextVar<String>((short)8, "Dbt", ContextType.BT, ContextOperator.EQUAL, "OFFICE_PC_*");
//	
//	private static PropositionalContextVar<Integer> Ebt=new PropositionalContextVar<Integer>((short)9, "Ebt", ContextType.BT_COUNT, ContextOperator.GREATER_EQUAL, 3);
//	
//	private static PropositionalContextVar<String> At=new PropositionalContextVar<String>((short)10, "At", ContextType.TIME, ContextOperator.GREATER_EQUAL, "MEETING_START");
//	
//	private static PropositionalContextVar<String> Bt=new PropositionalContextVar<String>((short)11, "Bt", ContextType.TIME, ContextOperator.GREATER_EQUAL, "MEETING_END");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<boolean[]> dataSet=PhoneAdapterSimulator.simulate(500);
		boolean performClustering=false;
		boolean performInference=true;
		if(performClustering){
			//long start=System.currentTimeMillis();
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
			//long end=System.currentTimeMillis();
			//System.out.println("it takes "+(end-start)+"ms");
			//ac.printResult();
			
			//clustering can be used for ranking
			//(1) generate a list of undeterminism warnings
			ArrayList<PCVAssignmentsWithIndex> warnings=Main.detection(true, false, false, false, false);
			//extract all warnings to the list, might not be aligned
			ArrayList<boolean[]> warningList=new ArrayList<boolean[]>();
			ArrayList<Integer> frequency=new ArrayList<Integer>();
			ArrayList<boolean[]> temp;
			ArrayList<Short> indexTemp;
			//int[] frequency=new int[warnings.size()];
			//System.out.println(warnings.size());
			try{
				for(int i=0;i<warnings.size();i++){
					temp=warnings.get(i).getPCVValueList();
					indexTemp=warnings.get(i).getPCVIndexList();
					for(int j=0;j<temp.size();j++){
						//variable dependency engine is hard coded here
						//System.out.println(temp.get(j).length);
						//if(Main.eventSatisfyingVariableDependency(temp.get(j))){ //for unstability faults
							int result=ac.classifyPartialInstance(temp.get(j), indexTemp);
							if(result==-1){
								warningList.add(temp.get(j));
								frequency.add(Integer.MAX_VALUE);
							}
							else if(result==-2){
								warningList.add(temp.get(j));
								frequency.add(1);
							}
							else{
								warningList.add(temp.get(j));
								frequency.add(ac.getResult().get(result).size()+1);
							}
						//}
					}
				}
				//System.out.println(warningList.size());
//				for(int i=0;i<warningList.size();i++){
//					printBooleanList(warningList.get(i));
//					System.out.println("\t"+frequency.get(i));
//				}
				
				//perform random ranking to check the false positive rate in top 10% and 20%
				//of course, we can use unsorted result, but it's dependent on implementation
//				Random rand=new Random();
//				for(int i=0;i<warningList.size();i++){
//					int toSwap=rand.nextInt(warningList.size());
//					Integer tempI=frequency.get(i);
//					frequency.set(i, frequency.get(toSwap));
//					frequency.set(toSwap, tempI);
//					boolean[] tempL=warningList.get(i);
//					warningList.set(i, warningList.get(toSwap));
//					warningList.set(toSwap, tempL);
//				}
				int numberOfWarningsToCheck=warningList.size()/5;
				int fpCount=0;
				for(int i=0;i<numberOfWarningsToCheck;i++){
					//if(!Main.eventSatisfyingPhysicalConstraints(warningList.get(i))){ //for unstability faults
					if(!partialEventPossible(warningList.get(i))){
						fpCount++;
					}
					//}
				}
				System.out.println("without ranking:");
				System.out.println("true positives: "+(numberOfWarningsToCheck-fpCount)+"/"+numberOfWarningsToCheck);
				
				//(2) perform ranking, bubble sort
				for(int i=0;i<warningList.size()-1;i++){
					for(int j=0;j<warningList.size()-1-i;j++)
					{
						if(frequency.get(j)<frequency.get(j+1)){
							//System.out.println("sorting");
							Integer tempI=frequency.get(j);
							frequency.set(j, frequency.get(j+1));
							frequency.set(j+1, tempI);
							boolean[] tempL=warningList.get(j);
							warningList.set(j, warningList.get(j+1));
							warningList.set(j+1, tempL);
						}
					}
				}
				System.out.println("after ranking");
//				for(int i=0;i<warningList.size();i++){
//					printBooleanList(warningList.get(i));
//					System.out.println("\t"+frequency.get(i));
//				}
				
				//System.out.println(warningList.size());
				
				//false positive rate in top 20%
				//numberOfWarningsToCheck=warningList.size()/10;
				fpCount=0;
				for(int i=0;i<numberOfWarningsToCheck;i++){
					//if(!Main.eventSatisfyingPhysicalConstraints(warningList.get(i))){ //for unstability faults
					if(!partialEventPossible(warningList.get(i))){
						fpCount++;
					}
					//}
				}
				System.out.println("true positives: "+(numberOfWarningsToCheck-fpCount)+"/"+numberOfWarningsToCheck);
				
				
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		if(performInference){
			PropositionalContextVar<Boolean> Agps=new PropositionalContextVar<Boolean>((short)0, "Agps", ContextType.GPS_VALID,ContextOperator.EQUAL, true);
			PropositionalContextVar<String> Bgps=new PropositionalContextVar<String>((short)1, "Bgps", ContextType.GPS_LOCATION, ContextOperator.EQUAL,"HOME" );
			PropositionalContextVar<String> Cgps=new PropositionalContextVar<String>((short)2, "Cgps", ContextType.GPS_LOCATION, ContextOperator.EQUAL, "OFFICE");
			PropositionalContextVar<Integer> Dgps=new PropositionalContextVar<Integer>((short)3, "Dgps", ContextType.GPS_SPEED, ContextOperator.GREATER, 5);
			PropositionalContextVar<Integer> Egps=new PropositionalContextVar<Integer>((short)4, "Egps", ContextType.GPS_SPEED, ContextOperator.GREATER, 70);
			PropositionalContextVar<String> Abt=new PropositionalContextVar<String>((short)5, "Abt", ContextType.BT, ContextOperator.EQUAL, "CAR_HANDSFREE");
			PropositionalContextVar<String> Bbt=new PropositionalContextVar<String>((short)6, "Bbt", ContextType.BT, ContextOperator.EQUAL, "HOME_PC");
			
			PropositionalContextVar<String> Cbt=new PropositionalContextVar<String>((short)7, "Cbt", ContextType.BT, ContextOperator.EQUAL, "OFFICE_PC");
			
			PropositionalContextVar<String> Dbt=new PropositionalContextVar<String>((short)8, "Dbt", ContextType.BT, ContextOperator.EQUAL, "OFFICE_PC_*");
			
			PropositionalContextVar<Integer> Ebt=new PropositionalContextVar<Integer>((short)9, "Ebt", ContextType.BT_COUNT, ContextOperator.GREATER_EQUAL, 3);
			
			PropositionalContextVar<String> At=new PropositionalContextVar<String>((short)10, "At", ContextType.TIME, ContextOperator.GREATER_EQUAL, "MEETING_START");
			
			PropositionalContextVar<String> Bt=new PropositionalContextVar<String>((short)11, "Bt", ContextType.TIME, ContextOperator.GREATER_EQUAL, "MEETING_END");

			ArrayList<PropositionalContextVar> list=new ArrayList<PropositionalContextVar>();
			list.add(Agps);
			list.add(Bgps);
			list.add(Cgps);
			list.add(Dgps);
			list.add(Egps);
			list.add(Abt);
			list.add(Bbt);
			list.add(Cbt);
			list.add(Dbt);
			list.add(Ebt);
			list.add(At);
			list.add(Bt);
			ConfidenceTable table=new ConfidenceTable();
			table.setPCVList(list);
			table.initializeTable();
			try{
				//hard setting variable dependency
				Constraint tempC=new Constraint(Bgps,Agps,ConstraintType.IMPLICATION);
				table.fixConstraint(tempC);
				tempC=new Constraint(Cgps, Agps, ConstraintType.IMPLICATION);
				table.fixConstraint(tempC);
				tempC=new Constraint(Dgps, Agps, ConstraintType.IMPLICATION);
				table.fixConstraint(tempC);
				tempC=new Constraint(Egps, Agps, ConstraintType.IMPLICATION);
				table.fixConstraint(tempC);
				tempC=new Constraint(Bgps, Cgps, ConstraintType.EXCLUSIVENESS);
				table.fixConstraint(tempC);
				tempC=new Constraint(Egps, Dgps, ConstraintType.IMPLICATION);
				table.fixConstraint(tempC);
				tempC=new Constraint(Bt, At, ConstraintType.IMPLICATION);
				table.fixConstraint(tempC);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			for(int i=0;i<dataSet.size();i++){
				table.updateTable(dataSet.get(i));
			}
			table.predict(10);
		}
	}
	protected static void printBooleanList(boolean[] blist){
		for(int i=0;i<blist.length;i++){
			if(blist[i]){
				System.out.print("1 ");
			}
			else{
				System.out.print("0 ");
			}
		}
	}
	
	//hard coding, just for evaluating 
	protected static boolean partialEventPossible(boolean[] pe){
//		//!Agps -> (!Bgps ^ !Cgps ^ !Dgps ^ !Egps)
//		if(index.contains((short)0)){
//			if(index.contains((short)1)){
//				if(!pe[index.indexOf((short)0)] && pe[index.indexOf((short)1)]){
//					return false;
//				}
//			}
//			if(index.contains((short)2)){
//				if(!pe[index.indexOf((short)0)] && pe[index.indexOf((short)2)]){
//					return false;
//				}
//			}
//			if(index.contains((short)3)){
//				if(!pe[index.indexOf((short)0)] && pe[index.indexOf((short)3)]){
//					return false;
//				}
//			}
//			if(index.contains((short)4)){
//				if(!pe[index.indexOf((short)0)] && pe[index.indexOf((short)4)]){
//					return false;
//				}
//			}
//		}
//		if(index.contains((short)1) && index.contains((short)2)){
//			if(pe[index.indexOf((short)1)] && pe[index.indexOf((short)2)]){
//				return false;
//			}
//		}
//		
//		if(index.contains((short)4) && index.contains((short)3)){
//			if(pe[index.indexOf((short)4)] && !pe[index.indexOf((short)3)]){
//				return false;
//			}
//		}
//		
//		if(index.contains((short)11) && index.contains((short)10)){
//			if(pe[index.indexOf((short)11)] && !pe[index.indexOf((short)10)]){
//				return false;
//			}
//		}
//		
//		if(index.contains((short)6)){
//			if(index.contains((short)5)){
//				if(pe[index.indexOf((short)6)] && pe[index.indexOf((short)5)]){
//					return false;
//				}
//			}
//			if(index.contains((short)8)){
//				if(pe[index.indexOf((short)6)] && pe[index.indexOf((short)8)]){
//					return false;
//				}
//			}
//		}
//		
//		if(index.contains((short)1)){
//			if(index.contains((short)7)){
//				if(pe[index.indexOf((short)1)] && pe[index.indexOf((short)7)]){
//					return false;
//				}
//			}
//			if(index.contains((short)8)){
//				if(pe[index.indexOf((short)1)] && pe[index.indexOf((short)8)]){
//					return false;
//				}
//			}
//		}
//		
//		if(index.contains((short)2) && index.contains((short)6)){
//			if(pe[index.indexOf((short)2)] && pe[index.indexOf((short)6)]){
//				return false;
//			}
//		}
//		return true;
		//a list of true positives
		ArrayList<boolean[]> list=new ArrayList<boolean[]>();
		boolean[] e1={true,false,false,false,false,false,true};
		boolean[] e2={true,false,false,false,false,true,false};
		boolean[] e3={true,false,false,false,false,true,true};
		boolean[] e4={true,false,false,false,true,false,false};
		list.add(e1);
		list.add(e2);
		list.add(e3);
		list.add(e4);
		for(int i=0;i<4;i++){
			if(Main.blistEqual(pe, list.get(i))){
				System.out.println("true");
				return true;
			}
		}
		return false;
	}
}
