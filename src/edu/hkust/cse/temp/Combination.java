package edu.hkust.cse.temp;

import java.util.ArrayList;

/**
 * @author andrew	Jun 30, 2011
 */
public class Combination {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean[] list=new boolean[4];
		explore(list,0);
	}
	
	public static void explore(boolean[] blist,int i){
		if(i==blist.length-1){
			blist[i]=false;
			out(blist);
			blist[i]=true;
			out(blist);
		}
		else
		{
			blist[i]=false;
			explore(blist,i+1);
			blist[i]=true;
			explore(blist,i+1);
		}
	}
	
	public static void out(boolean[] blist){
		for(int i=0;i<blist.length;i++){
			System.out.print(blist[i]+"\t");
		}
		System.out.println();
	}

}
