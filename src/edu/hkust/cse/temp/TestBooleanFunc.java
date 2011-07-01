package edu.hkust.cse.temp;

import edu.hkust.cse.detector.BooleanFunc;

/**
 * @author andrew	Jul 1, 2011
 */
public class TestBooleanFunc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BooleanFunc temp=new BooleanFunc();
		temp.getCVNoList().add((short)0);
		temp.getCVNoList().add((short)1);
		temp.getCVNoList().add((short)2);
		temp.setConn((byte)1);
		temp.setSingle(false);
		temp.getForm().add(true);
		temp.getForm().add(false);
		temp.getForm().add(false);
		boolean[] blist={true,false,false};
		System.out.println(temp.satisfied(blist));

	}

}
