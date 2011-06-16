package edu.hkust.cse.detector;

import java.util.LinkedList;

/**
 * @author andrew	Jun 16, 2011
 * Boolean function here means a set of boolean variables connected only by disjunction or conjunction
 * A v (B ^ C) is not a legal boolean function in our definition, it should be split into two sub functions
 */
public class BooleanFunc implements LogicOperator {
	private LinkedList<PropositionalContextVar> cvs;
	/*
	 * a boolean variable has two forms: original and its negation, example: A and !A
	 * true for original, false for its negation 
	 */
	private LinkedList<Boolean> form;
	/*
	 * connector, 1 for conjunction and 0 for disjunction 
	 */
	private byte conn;
	private boolean isSingle;
	/*
	 * default constructor, we assume there is only one propositional context variable and no connector
	 */
	public BooleanFunc()
	{
		this.cvs=new LinkedList<PropositionalContextVar>();
		this.form=new LinkedList<Boolean>();
		this.conn=-1;
		this.isSingle=true;
	}
	public LinkedList<PropositionalContextVar> getCVList(){
		return this.cvs;
	}
	public LinkedList<Boolean> getForm(){
		return this.form;
	}
	public short getConn()
	{
		return this.conn;
	}
	public void setConn(byte connector){
		this.conn=connector;
	}
	public boolean getSingle(){
		return this.isSingle;
	}
	public void setSingle(boolean single){
		this.isSingle=single;
	}
}
