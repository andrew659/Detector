package edu.hkust.cse.detector;

import java.util.LinkedList;

/**
 * @author andrew	Jun 16, 2011
 */
public class Rule {
	private short no;
	private String name;
	private short currentStateNo;
	private LinkedList<BooleanFunc> fullPredicate;
	private boolean form;
	private short newStateNo;
	private byte priority;
	private Action action;
	/*
	 * default constructor, initialize fullPredicate
	 */
	public Rule(){
		this.fullPredicate=new LinkedList<BooleanFunc>();
	}
	public short getNo(){
		return this.no;
	}
	public void setNo(short stateNo){
		this.no=stateNo;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String stateName){
		this.name=stateName;
	}
	public short getCurrentStateNo(){
		return this.currentStateNo;
	}
	public void setCurrentStateNo(short csn){
		this.currentStateNo=csn;
	}
	public LinkedList<BooleanFunc> getFullPredicate(){
		return this.fullPredicate;
	}
	public short getNewStateNo(){
		return this.newStateNo;
	}
	public void setNewStateNo(short nsn){
		this.newStateNo=nsn;
	}
	public boolean getFrom(){
		return this.form;
	}
	public void setForm(boolean nf){
		this.form=nf;
	}
	public byte getPriority(){
		return this.priority;
	}
	public void setPriority(byte np){
		this.priority=np;
	}
	/*
	 * leave us one question, after getting full predicate and its form, how to evaluate it?
	 */
	
}
