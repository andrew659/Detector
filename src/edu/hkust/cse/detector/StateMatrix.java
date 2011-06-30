package edu.hkust.cse.detector;

import java.util.ArrayList;

/**
 * @author andrew	Jun 17, 2011
 */
public class StateMatrix {
	private ArrayList<boolean[]> matrix;
	private ArrayList<ArrayList<Rule>> ruleListList;
	public StateMatrix(){
		this.matrix=new ArrayList<boolean[]>();
		this.ruleListList=new ArrayList<ArrayList<Rule>>();
	}
	public ArrayList<boolean[]> getMatrix(){
		return this.matrix;
	}
	public ArrayList<ArrayList<Rule>> getRuleListList(){
		return this.ruleListList;
	}

}
