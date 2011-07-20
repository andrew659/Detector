package edu.hkust.cse.temp;
/**
 * @author andrew	Jul 16, 2011
 */
public class TestEquals {
	public static void main(String[] args){
		boolean[] a={true,false};
		boolean[] b=new boolean[2];
		b[0]=true;
		b[1]=false;
		System.out.println(blistEqual(a, b));
	}
	
	public static boolean blistEqual(boolean[] a,boolean[] b){
		if(a.length!=b.length){
			return false;
		}
		else{
			for(int i=0;i<a.length;i++){
				if(a[i]!=b[i]){
					return false;
				}
			}
			return true;
		}
	}
}
