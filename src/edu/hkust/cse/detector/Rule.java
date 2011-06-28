package edu.hkust.cse.detector;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author andrew	Jun 16, 2011
 */
public class Rule {
	private short no;
	private String name;
	private short currentStateNo;
	private ArrayList<BooleanFunc> fullPredicate;
	//supporting DNF, conn=0 indicating disjunction, conn=-1 when isSingle=true
	private Byte conn;
	private boolean isSingle;
	private boolean form;
	private short newStateNo;
	private byte priority;
	private Action action;
	/*
	 * default constructor, initialize fullPredicate
	 */
	public Rule(){
		this.fullPredicate=new ArrayList<BooleanFunc>();
	}
	public short getNo(){
		return this.no;
	}
	public void setNo(short ruleNo){
		this.no=ruleNo;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String ruleName){
		this.name=ruleName;
	}
	public short getCurrentStateNo(){
		return this.currentStateNo;
	}
	public void setCurrentStateNo(short csn){
		this.currentStateNo=csn;
	}
	public ArrayList<BooleanFunc> getFullPredicate(){
		return this.fullPredicate;
	}
	public void setConn(byte connector){
		this.conn=connector;
	}
	public boolean getIsSingle(){
		return this.isSingle;
	}
	public void setIsSingle(boolean single){
		this.isSingle=single;
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
	public boolean satisfied(ArrayList<Boolean> blist){
		if(isSingle){
			if(form){
				return fullPredicate.get(0).satisfied(blist);
			}
			else{
				return !fullPredicate.get(0).satisfied(blist);
			}
		}
		else{ // conn must be 0
			boolean flag=false;
			for(int i=0;i<fullPredicate.size();i++){
				if(fullPredicate.get(i).satisfied(blist)){
					flag=true;
					break;
				}
			}
			if(form){
				return flag;
			}
			else{
				return !flag;
			}
		}
	}
}
