package edu.hkust.cse.simulator;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author andrew	Jul 11, 2011
 * simulate the environment of a typical user
 */
public class PhoneAdapterSimulator {
	private final static int Agps=0;
	private final static int Bgps=1;
	private final static int Cgps=2;
	private final static int Dgps=3;
	private final static int Egps=4;
	
	private final static int Abt=5;
	private final static int Bbt=6;
	private final static int Cbt=7;
	private final static int Dbt=8;
	private final static int Ebt=9;
	
	private final static int At=10;
	private final static int Bt=11;
	
	
	public static ArrayList<boolean[]> simulate(int numberOfEvents){
		ArrayList<boolean[]> eventList=new ArrayList<boolean[]>();
		for(int i=0;i<numberOfEvents;i++){
			eventList.add(randomGen(12));
		}
		return eventList;
	}
	
	public static boolean[] randomGen(int num){
		boolean[] list=new boolean[num];
		Random rand=new Random();
		while(true){
			for(int i=0;i<num;i++){
				list[i]=rand.nextBoolean();
			}
			if(globalConstriantsSatisfied(list) && isPossible(list)){
				break;
			}
		}
//		for(int i=0;i<num;i++){
//			System.out.print(list[i]);
//		}
		return list;
		
	}
	public static boolean globalConstriantsSatisfied(boolean[] smRow){
		//!Agps -> (!Bgps ^ !Cgps ^ !Dgps ^ !Egps)
		if(!smRow[Agps]){
			if(smRow[Bgps]){
				return false;
			}
			if(smRow[Cgps]){
				return false;
			}
			if(smRow[Dgps]){
				return false;
			}
			if(smRow[Egps]){
				return false;
			}
		}
		
		//(Bgps=>!Cgps) ^ (Cgps=>!Bgps) locations are mutually exclusive
		if(smRow[Bgps]==true && smRow[Cgps]==true){
			return false;
		}
		
		//Egps=>Dgps
		if(smRow[Egps] && !smRow[Dgps]){
			return false;
		}
		
		//Bt=>At
		if(smRow[Bt] && !smRow[At]){
			return false;
		}
		return true;
	}
	
	public static boolean isPossible(boolean[] blist){
		if(blist[Bbt] && blist[Cbt]){
			return false;
		}
		
		if(blist[Bbt] && blist[Dbt]){
			return false;
		}
		
		if(blist[Bgps] && blist[Cbt]){
			return false;
		}
		
		if(blist[Bgps] && blist[Dbt]){
			return false;
		}
		
		if(blist[Cgps] && blist[Bbt]){
			return false;
		}
		
		
		return true;
	}
	
	
}
