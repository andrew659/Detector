package edu.hkust.cse.inferenceEngine;

import edu.hkust.cse.detector.PropositionalContextVar;

/**
 * @author andrew	Jul 20, 2011
 */
public class Constraint{
	private PropositionalContextVar varL;
	private PropositionalContextVar varR;
	private short type;
	public Constraint(){
		
	}
	public Constraint(PropositionalContextVar l,PropositionalContextVar r,short t){
		this.varL=l;
		this.varR=r;
		this.type=t;
	}
	public PropositionalContextVar getVarL(){
		return this.varL;
	}
	public PropositionalContextVar getVarR(){
		return this.varR;
	}
	public short getType(){
		return this.type;
	}
	public void setVarL(PropositionalContextVar var){
		this.varL=var;
	}
	public void setVarR(PropositionalContextVar var){
		this.varR=var;
	}
	public void setType(short t){
		this.type=t;
	}	
}
