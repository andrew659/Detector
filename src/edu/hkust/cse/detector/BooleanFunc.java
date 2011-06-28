package edu.hkust.cse.detector;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author andrew	Jun 16, 2011
 * Boolean function here means a set of boolean variables connected only by disjunction or conjunction
 * A v (B ^ C) is not a legal boolean function in our definition, it should be split into two sub functions
 */
public class BooleanFunc implements LogicOperator {
	private ArrayList<Short> cvNo;
	/*
	 * a boolean variable has two forms: original and its negation, example: A and !A
	 * true for original, false for its negation 
	 */
	private ArrayList<Boolean> form;
	/*
	 * connector, 1 for conjunction and 0 for disjunction 
	 */
	private byte conn;
	private boolean isSingle;
	/*
	 * default constructor, we assume there is only one propositional context variable and no connector
	 */
	public BooleanFunc(){
		this.cvNo=new ArrayList<Short>();
		this.form=new ArrayList<Boolean>();
		this.conn=-1;
		this.isSingle=true;
	}
	public ArrayList<Short> getCVNoList(){
		return this.cvNo;
	}
	public ArrayList<Boolean> getForm(){
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
	public boolean satisfied(ArrayList<Boolean> blist){
		boolean flag=false;
		if(conn==0){
			flag=false;
		}
		if(conn==1){
			flag=true;
		}
		if(isSingle){
			if(this.form.get(0)){
				return blist.get((int)cvNo.get(0));
			}
			else{
				return !blist.get((int)cvNo.get(0));
			}
		}
		else{
			for(int i=0;i<this.cvNo.size();i++){
				if(conn==0){
					if(form.get(i)==blist.get((int)cvNo.get(i))){
						flag=true;
						break;
					}
				}
				//else conn==1 conjunction
				else{
					if(form.get(i)!=blist.get((int)cvNo.get(i))){
						flag=false;
						break;
					}
				}
			}
		}
		return flag;
	}
}
