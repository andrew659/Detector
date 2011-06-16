package edu.hkust.cse.detector;
/**
 * @author andrew	Jun 16, 2011
 */
public class State {
	private short no;
	private String name;
	public State(short stateNo,String stateName){
		this.no=stateNo;
		this.name=stateName;
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
}
