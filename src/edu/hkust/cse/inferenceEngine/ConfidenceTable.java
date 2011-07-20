package edu.hkust.cse.inferenceEngine;

import java.util.ArrayList;
import java.util.Random;

import edu.hkust.cse.detector.ContextOperator;
import edu.hkust.cse.detector.ContextType;
import edu.hkust.cse.detector.PropositionalContextVar;
import edu.hkust.cse.temp.Combination;

/**
 * @author andrew	Jul 13, 2011
 */
public class ConfidenceTable {
	private ArrayList<PropositionalContextVar> pcvList;
	private ConfTableEle[][] table;
	private int count;
	public void initializeTable(){
		this.count=0;
		this.table=new ConfTableEle[pcvList.size()][pcvList.size()];
		for(int i=0;i<pcvList.size();i++){
			for(int j=0;j<pcvList.size();j++){
				table[i][j]=new ConfTableEle();
				if(i==j){
					table[i][j].setConfEqui(1);
					table[i][j].setConfEquiFixed(true);
					table[i][j].setConfExclusiveFixed(true);
					table[i][j].setConfImplicationFixed(true);
				}
			}
		}		
	}
	public void setPCVList(ArrayList<PropositionalContextVar> l){
		this.pcvList=l;
	}
	//pcvValueList must have the same size with each row of table
	public void updateTable(boolean[] pcvValueList){
		for(int i=0;i<table.length;i++){
			for(int j=0;j<i;j++){
				if(pcvValueList[i]==false && pcvValueList[j]==false){
					//update table[i][j]
					double temp=table[i][j].getConfExclusive();
					if(!table[i][j].getConfExclusiveFixed()){
						table[i][j].setConfExclusive((temp*count+(1.0/3))/(count+1));
					}
					if(!table[i][j].getConfImplicationFixed()){
						temp=table[i][j].getConfImplication();
						table[i][j].setConfImplication((temp*count+(1.0/3))/(count+1));
					}
					if(!table[i][j].getConfEquiFixed()){
						temp=table[i][j].getConfEqui();
						table[i][j].setConfEqui((temp*count+(1.0/3))/(count+1));
					}
					
					//update table[j][i]
					if(!table[j][i].getConfExclusiveFixed()){
						temp=table[j][i].getConfExclusive();
						table[j][i].setConfExclusive((temp*count+(1.0/3))/(count+1));
					}
					if(!table[j][i].getConfImplicationFixed()){
						temp=table[j][i].getConfImplication();
						table[j][i].setConfImplication((temp*count+(1.0/3))/(count+1));
					}
					if(!table[j][i].getConfEquiFixed()){
						temp=table[j][i].getConfEqui();
						table[j][i].setConfEqui((temp*count+(1.0/3))/(count+1));
					}
				}
				else if(pcvValueList[i]==false && pcvValueList[j]==true){
					//update table[i][j]
					double temp=table[i][j].getConfExclusive();
					if(!table[i][j].getConfExclusiveFixed()){
						table[i][j].setConfExclusive((temp*count+(1.0/2))/(count+1));
					}
					if(!table[i][j].getConfImplicationFixed()){
						temp=table[i][j].getConfImplication();
						table[i][j].setConfImplication((temp*count+(1.0/2))/(count+1));
					}
					if(!table[i][j].getConfEquiFixed()){
						table[i][j].setConfEqui(0.0);
						table[i][j].setConfEquiFixed(true);
					}
					
					//update table[j][i]
					if(!table[j][i].getConfExclusiveFixed()){
						temp=table[j][i].getConfExclusive();
						table[j][i].setConfExclusive((temp*count+1.0)/(count+1));
					}
					if(!table[j][i].getConfImplicationFixed()){
						table[j][i].setConfImplication(0);
						table[j][i].setConfImplicationFixed(true);
					}
					if(!table[j][i].getConfEquiFixed()){
						table[j][i].setConfEqui(0);
						table[j][i].setConfEquiFixed(true);
					}
				}
				else if(pcvValueList[i]==true && pcvValueList[j]==false){
					//update table[i][j]
					double temp=table[i][j].getConfExclusive();
					if(!table[i][j].getConfExclusiveFixed()){
						table[i][j].setConfExclusive((temp*count+1.0)/(count+1));
					}
					if(!table[i][j].getConfImplicationFixed()){
						table[i][j].setConfImplication(0);
						table[i][j].setConfImplicationFixed(true);
					}
					if(!table[i][j].getConfEquiFixed()){
						table[i][j].setConfEqui(0);
						table[i][j].setConfEquiFixed(true);
					}
					
					//update table[j][i]
					temp=table[j][i].getConfExclusive();
					if(!table[j][i].getConfExclusiveFixed()){
						table[j][i].setConfExclusive((temp*count+(1.0/2))/(count+1));
					}
					if(!table[j][i].getConfImplicationFixed()){
						temp=table[j][i].getConfImplication();
						table[j][i].setConfImplication((temp*count+(1.0/2))/(count+1));
					}
					if(!table[j][i].getConfEquiFixed()){
						table[j][i].setConfEqui(0.0);
						table[j][i].setConfEquiFixed(true);
					}
					
				}
				else{
					//update table[i][j]
					double temp=table[i][j].getConfExclusive();
					if(!table[i][j].getConfExclusiveFixed()){
						table[i][j].setConfExclusive(0);
						table[i][j].setConfExclusiveFixed(true);
					}
					if(!table[i][j].getConfImplicationFixed()){
						temp=table[i][j].getConfImplication();
						table[i][j].setConfImplication((temp*count+(1.0/2))/(count+1));
					}
					if(!table[i][j].getConfEquiFixed()){
						temp=table[i][j].getConfEqui();
						table[i][j].setConfEqui((temp*count+(1.0/2))/(count+1));
					}
					
					//update table[j][i]
					if(!table[j][i].getConfExclusiveFixed()){
						table[j][i].setConfExclusive(0);
						table[j][i].setConfExclusiveFixed(true);
					}
					if(!table[j][i].getConfImplicationFixed()){
						temp=table[j][i].getConfImplication();
						table[j][i].setConfImplication((temp*count+(1.0/2))/(count+1));
					}
					if(!table[j][i].getConfEquiFixed()){
						temp=table[j][i].getConfEqui();
						table[j][i].setConfEqui((temp*count+(1.0/2))/(count+1));
					}
				}
				
			}
		}
		count++;
	}
	//fix one binary relation in the constraint network based on a known constraint
	public void fixConstraint(Constraint c) throws Exception{
		if(!this.pcvList.contains(c.getVarL()) || !this.pcvList.contains(c.getVarR())){
			throw new Exception();
		}
		else{
			switch(c.getType()){
				case ConstraintType.EXCLUSIVENESS: {
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confExclusive=1.0;
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confExclusiveFixed=true;
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confImplication=0.0;
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confImplicationFixed=true;
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confEqui=0.0;
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confEquiFixed=true;
					
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confExclusive=1.0;
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confExclusiveFixed=true;
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confImplication=0.0;
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confImplicationFixed=true;
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confEqui=0.0;
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confEquiFixed=true;
					break;
				}
				case ConstraintType.IMPLICATION: {
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confImplication=1.0;
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confImplicationFixed=true;
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confExclusive=0.0;
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confExclusiveFixed=true;
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confEqui=0.0;
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confEquiFixed=true;
					
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confImplication=0.0;
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confImplicationFixed=true;
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confExclusive=0.0;
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confExclusiveFixed=true;
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confEqui=0.0;
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confEquiFixed=true;
					
					break;
				}
				case ConstraintType.EQUIVALENCE: {
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confEqui=1.0;
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confEquiFixed=true;
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confImplication=0.0;
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confImplicationFixed=true;
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confExclusive=0.0;
					this.table[c.getVarL().getNo()][c.getVarR().getNo()].confExclusiveFixed=true;
					
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confEqui=1.0;
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confEquiFixed=true;
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confImplication=0.0;
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confImplicationFixed=true;
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confExclusive=0.0;
					this.table[c.getVarR().getNo()][c.getVarL().getNo()].confExclusiveFixed=true;
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	public void printTable(){
		for(int i=0;i<table.length;i++){
			for(int j=0;j<table.length;j++){
				System.out.println("----"+pcvList.get(i).getName()+" and "+pcvList.get(j).getName()+"----");
				table[i][j].printTableEle();
				System.out.println();
			}
		}
	}
	
	//predict n constraints
	public void predict(int n){
		ArrayList<Node> exclusivenessList=new ArrayList<Node>();
		ArrayList<Node> implicationList=new ArrayList<Node>();
		ArrayList<Node> equivalenceList=new ArrayList<Node>();
		for(int i=0;i<table.length;i++){
			for(int j=0;j<table.length;j++){
				if(i!=j){
					Node temp;
					if(!table[i][j].getConfExclusiveFixed()){
						temp=new Node();
						temp.setConf(table[i][j].getConfExclusive());
						temp.setI(i);
						temp.setJ(j);
						exclusivenessList.add(temp);
					}
					if(!table[i][j].getConfImplicationFixed()){
						temp=new Node();
						temp.setConf(table[i][j].getConfImplication());
						temp.setI(i);
						temp.setJ(j);
						implicationList.add(temp);
					}
					if(!table[i][j].getConfEquiFixed()){
						temp=new Node();
						temp.setConf(table[i][j].getConfEqui());
						temp.setI(i);
						temp.setJ(j);
						equivalenceList.add(temp);
					}					
				}
			}
		}
//		sortNodeList(exclusivenessList);
//		sortNodeList(implicationList);
//		sortNodeList(equivalenceList);
//		System.out.println("----exclusiveness----");
//		for(int i=0;i<exclusivenessList.size();i++){
//			Node temp=exclusivenessList.get(i);
//			System.out.println(this.pcvList.get(temp.getI()).getName()+" and "+this.pcvList.get(temp.getJ()).getName()+" confidence="+temp.getConf());
//		}
//		System.out.println("----implication----");
//		for(int i=0;i<implicationList.size();i++){
//			Node temp=implicationList.get(i);
//			System.out.println(this.pcvList.get(temp.getI()).getName()+" and "+this.pcvList.get(temp.getJ()).getName()+" confidence="+temp.getConf());
//		}
//		System.out.println("----equivalence----");
//		for(int i=0;i<equivalenceList.size();i++){
//			Node temp=equivalenceList.get(i);
//			System.out.println(this.pcvList.get(temp.getI()).getName()+" and "+this.pcvList.get(temp.getJ()).getName()+" confidence="+temp.getConf());
//		}
		
		//merge and then ranking
		ArrayList<Node> list=new ArrayList<Node>();
		ArrayList<Short> typeList=new ArrayList<Short>();
		for(int i=0;i<exclusivenessList.size();i++){
			list.add(exclusivenessList.get(i));
			typeList.add(ConstraintType.EXCLUSIVENESS);
		}
		for(int i=0;i<implicationList.size();i++){
			list.add(implicationList.get(i));
			typeList.add(ConstraintType.IMPLICATION);
		}
		for(int i=0;i<equivalenceList.size();i++){
			list.add(equivalenceList.get(i));
			typeList.add(ConstraintType.EQUIVALENCE);
		}
		sortNodeList(list, typeList);
		int count=0;
		for(int i=0;i<list.size();i++){
			if(count==n){
				break;
			}
			switch(typeList.get(i)){
				case ConstraintType.EXCLUSIVENESS:{
					System.out.print("Exclusive: ");
					break;
				}
				case ConstraintType.IMPLICATION:{
					System.out.print("Implication: ");
					break;
				}
				case ConstraintType.EQUIVALENCE:{
					System.out.print("Equivalence: ");
					break;
				}
				default:{
					break;
				}
			}
			System.out.println(this.pcvList.get(list.get(i).getI()).getName()+" and "+this.pcvList.get(list.get(i).getJ()).getName()+" confidence="+list.get(i).getConf());
			count++;
		}
	}
	class Node{
		private double conf;
		private int i;
		private int j;
		public double getConf(){
			return this.conf;
		}
		public void setConf(double newConf){
			this.conf=newConf;
		}
		public int getI(){
			return this.i;
		}
		public void setI(int newI){
			this.i=newI;
		}
		public int getJ(){
			return this.j;
		}
		public void setJ(int newJ){
			this.j=newJ;
		}
	}
	class ConfTableEle{
		private double confExclusive;
		private boolean confExclusiveFixed;
		private double confImplication;
		private boolean confImplicationFixed;
		private double confEqui;
		private boolean confEquiFixed;
		public ConfTableEle(){
			this.confExclusive=0.0;
			this.confImplication=0.0;
			this.confEqui=0.0;
			this.confExclusiveFixed=false;
			this.confImplicationFixed=false;
			this.confEquiFixed=false;
		}
		public double getConfExclusive(){
			return this.confExclusive;
		}
		public void setConfExclusive(double conf){
			this.confExclusive=conf;
		}
		public boolean getConfExclusiveFixed(){
			return this.confExclusiveFixed;
		}
		public void setConfExclusiveFixed(boolean f){
			this.confExclusiveFixed=f;
		}
		
		public double getConfImplication(){
			return this.confImplication;
		}
		public void setConfImplication(double conf){
			this.confImplication=conf;
		}
		public boolean getConfImplicationFixed(){
			return this.confImplicationFixed;
		}
		public void setConfImplicationFixed(boolean f){
			this.confImplicationFixed=f;
		}
		
		public double getConfEqui(){
			return this.confEqui;
		}
		public void setConfEqui(double conf){
			this.confEqui=conf;
		}
		public boolean getConfEquiFixed(){
			return this.confEquiFixed;
		}
		public void setConfEquiFixed(boolean f){
			this.confEquiFixed=f;
		}
		public void printTableEle(){
			System.out.println("confidence of exclusiveness="+this.confExclusive+", fixed="+this.confExclusiveFixed);
			System.out.println("confidence of implication="+this.confImplication+", fixed="+this.confImplicationFixed);
			System.out.println("confidence of equivalence="+this.confEqui+", fixed="+this.confEquiFixed);
		}
		
	}
	
	//testing function, no use later
	public static void test(String[] args){
		PropositionalContextVar<Boolean> Agps=new PropositionalContextVar<Boolean>((short)0, "Agps", ContextType.GPS_VALID,ContextOperator.EQUAL, true);
		PropositionalContextVar<String> Bgps=new PropositionalContextVar<String>((short)1, "Bgps", ContextType.GPS_LOCATION, ContextOperator.EQUAL,"HOME" );
		PropositionalContextVar<String> Cgps=new PropositionalContextVar<String>((short)2, "Cgps", ContextType.GPS_LOCATION, ContextOperator.EQUAL, "OFFICE");
		PropositionalContextVar<Integer> Dgps=new PropositionalContextVar<Integer>((short)3, "Dgps", ContextType.GPS_SPEED, ContextOperator.GREATER, 5);
		PropositionalContextVar<Integer> Egps=new PropositionalContextVar<Integer>((short)4, "Egps", ContextType.GPS_SPEED, ContextOperator.GREATER, 70);
		ArrayList<PropositionalContextVar> list=new ArrayList<PropositionalContextVar>();
		list.add(Agps);
		list.add(Bgps);
		list.add(Cgps);
		list.add(Dgps);
		list.add(Egps);
		ConfidenceTable table=new ConfidenceTable();
		table.setPCVList(list);
		table.initializeTable();
		boolean[] blist=new boolean[5];
		Random rand=new Random();
		for(int i=0;i<10;i++){
			for(int j=0;j<5;j++){
				blist[j]=rand.nextBoolean();
			}
			if(constraintsSat(blist)){
				for(int k=0;k<5;k++){
					System.out.print(blist[k]+"\t");
				}
				System.out.println();
				table.updateTable(blist);
			}
		}
//		table.printTable();
		//get all combination
//		ArrayList<boolean[]> valueList=new ArrayList<boolean[]>();
//		combination(valueList,blist,0);
//		for(int i=0;i<valueList.size();i++){
//			if(constraintsSat(valueList.get(i))){
//				table.updateTable(valueList.get(i));
//			}
//		}
		table.predict(10);
		//table.printTable();
		//table.testsort();
		
		
	}
	
	//hard coding, for test constraint network(confidence table) only, no use later
	public static boolean constraintsSat(boolean[] list){
		if(list[0]==false){
			if(list[1]==true || list[2]==true || list[3]==true || list[4]==true){
				return false;
			}
		}
		else{
			if(list[1]==true && list[2]==true){
				return false;
			}
			if(list[4]==true && list[3]==false){
				return false;
			}
		}
		return true;
	}
	
	public static void combination(ArrayList<boolean[]> list,boolean[] blist, int i){
		if(i==blist.length-1){
			blist[i]=false;
			list.add(blist.clone());
			blist[i]=true;
			list.add(blist.clone());
		}
		else{
			blist[i]=false;
			combination(list,blist,i+1);
			blist[i]=true;
			combination(list,blist,i+1);
		}
	}
	
	public static void sortNodeList(ArrayList<Node> nlist,ArrayList<Short> tlist){
		int size=nlist.size();
		for(int i=0;i<size-1;i++){
			for(int j=0;j<size-1-i;j++){
				if(nlist.get(j).getConf()<nlist.get(j+1).getConf()){
					Node temp;
					temp=nlist.get(j);
					nlist.set(j, nlist.get(j+1));
					nlist.set(j+1, temp);
					short tempS;
					tempS=tlist.get(j);
					tlist.set(j, tlist.get(j+1));
					tlist.set(j+1, tempS);
				}
			}
		}
	}
}
