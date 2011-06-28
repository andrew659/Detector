package edu.hkust.cse.detector;

import java.util.ArrayList;

/**
 * @author andrew	Jun 16, 2011
 */
public class StateMachine {
	private ArrayList<State> stateList;
	private ArrayList<PropositionalContextVar> pcvList;
	private ArrayList<Rule> ruleList;
	/*
	 * each state in the state list corresponds to a state matrix in the same position of the state matrix list
	 */
	private ArrayList<StateMatrix> stateMatrixList;
	
	public boolean loadFromXML(String filePath){
		
		return true;
	}
	public ArrayList<State> getStateList(){
		return this.stateList;
	}
	public void setStateList(ArrayList<State> sl){
		this.stateList=sl;
	}
	public ArrayList<PropositionalContextVar> getPCVList(){
		return this.pcvList;
	}
	public void setPCVList(ArrayList<PropositionalContextVar> pcvl){
		this.pcvList=pcvl;
	}
	public ArrayList<Rule> getRuleList(){
		return this.ruleList;
	}
	public void setRuleList(ArrayList<Rule> rl){
		this.ruleList=rl;
	}
	public ArrayList<StateMatrix> getStateMatrixList(){
		return this.stateMatrixList;
	}
	public void setStateMatrixList(ArrayList<StateMatrix> sml){
		this.stateMatrixList=sml;
	}
}
