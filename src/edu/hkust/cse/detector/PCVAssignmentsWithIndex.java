package edu.hkust.cse.detector;

import java.util.ArrayList;

/**
 * @author andrew	Jul 16, 2011
 */
public class PCVAssignmentsWithIndex {
	private ArrayList<boolean[]> pcvValueList;
	private ArrayList<Short> pcvIndexList;
	public PCVAssignmentsWithIndex(){
		this.pcvIndexList=new ArrayList<Short>();
		this.pcvValueList=new ArrayList<boolean[]>();
	}
	public ArrayList<boolean[]> getPCVValueList(){
		return this.pcvValueList;
	}
	public ArrayList<Short> getPCVIndexList(){
		return this.pcvIndexList;
	}
	public void setPCVValueList(ArrayList<boolean[]> l){
		this.pcvValueList=l;
	}
	public void setPCVIndexList(ArrayList<Short> l){
		this.pcvIndexList=l;
	}
}
