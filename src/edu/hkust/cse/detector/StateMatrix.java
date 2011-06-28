package edu.hkust.cse.detector;

import java.util.ArrayList;

/**
 * @author andrew	Jun 17, 2011
 */
public class StateMatrix {
	private ArrayList<Short> pcvNoList;
	private ArrayList<ArrayList<Boolean>> matrix;
	public StateMatrix(){
		this.pcvNoList=new ArrayList<Short>();
		this.matrix=new ArrayList<ArrayList<Boolean>>();
	}
	public ArrayList<Short> getPCVNoList(){
		return this.pcvNoList;
	}
	public ArrayList<ArrayList<Boolean>> getMatrix(){
		return this.matrix;
	}
	public int getStateMatrixSize(){
		return this.matrix.size();
	}

}
