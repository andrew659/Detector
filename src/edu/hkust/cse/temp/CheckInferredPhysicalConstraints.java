package edu.hkust.cse.temp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author andrew	Jul 20, 2011
 */
public class CheckInferredPhysicalConstraints {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f=new File("/Users/andrew/java_workspace/AdaptationFaultDetector/src/edu/hkust/cse/temp/inferredConstraints");
		try{
			BufferedReader br=new BufferedReader(new FileReader(f));
			String temp;
			int count=0;
			int tp=0;
			while((temp=br.readLine())!=null){
				if(temp.length()>0){
					count++;
					if(temp.startsWith("Exclusive")){
						if(temp.contains("Bbt and Cbt")){
							tp++;
						}
						if(temp.contains("Cbt and Bbt")){
							tp++;
						}
						if(temp.contains("Bbt and Dbt")){
							tp++;
						}
						if(temp.contains("Dbt and Bbt")){
							tp++;
						}
						if(temp.contains("Bgps and Cbt")){
							tp++;
						}
						if(temp.contains("Cbt and Bgps")){
							tp++;
						}
						if(temp.contains("Bgps and Dbt")){
							tp++;
						}
						if(temp.contains("Dbt and Bgps")){
							tp++;
						}
						if(temp.contains("Cgps and Bbt")){
							tp++;
						}
						if(temp.contains("Bbt and Cgps")){
							tp++;
						}
					}
				}
			}
			System.out.println(tp+"/"+count);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
