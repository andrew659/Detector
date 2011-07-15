package edu.hkust.cse.clusteringEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author andrew	Jul 15, 2011
 */
public class AgglomerativeClustering {
	private ArrayList<boolean[]> dataSet;
	//each instance corresponds to an event
	private int instanceSize;
	private int dataSetSize;
	private double compactnessUpperBound;
	private int numberOfClusters;
	/*
	 * result will be manipulated frequently with insertion or deletion operations
	 * that is the reason for using linked list
	 */
	private LinkedList<ArrayList<Integer>> result;
	private boolean dataImported;
	private boolean clusteringDone;
	private double[] weight;
	private boolean useDynamicProgramming;
	private double[][] map;;
	
	public AgglomerativeClustering(){
		this.instanceSize=0;
		this.dataSetSize=0;
		this.compactnessUpperBound=0.0;
		this.numberOfClusters=0;
		this.result=new LinkedList<ArrayList<Integer>>();
		this.dataImported=false;
		this.clusteringDone=false;
		this.useDynamicProgramming=true;
	}
	//the main algorithm of agglomerative clustering
	public void build() throws Exception{
		if(!this.dataImported){
			throw new Exception("cannot perform clustering without any data set imported!");
		}
		if(!this.clusteringDone){
			//perform culstering, 
			if(this.useDynamicProgramming){
				/*
				 * use concept of dynamic programming
				 * hashmaps to store the distance between each pair of events
				 * (e1,(e2,dist)) nested hashma
				 */
				//compute and store the distance of each pair of events
				//long start=System.currentTimeMillis();
				this.map=new double[this.dataSet.size()][this.dataSet.size()];
				double tempD;
				for(int i=0;i<dataSetSize;i++){
					for(int j=0;j<dataSetSize;j++){
						if(i!=j){
							map[i][j]=dist(this.dataSet.get(i),this.dataSet.get(j));
						}
					}
				}
				//long end=System.currentTimeMillis();
				//System.out.println("takes "+(end-start)+"ms");
			}
			//the following step is the same for using dynamic programming case and not using ...
			//step one augmenting all events into groups
			ArrayList<Integer> tempL;
			for(int i=0;i<this.dataSetSize;i++){
				tempL=new ArrayList<Integer>();
				tempL.add(i);
				result.add(tempL);	
			}
			/*
			 * iterations
			 * in each iteration, the most two similar group are merged, which reduces the number of clusters by one
			 * termination condition: when the distance between the most two similar group is larger than the compactnessUpperBound
			 */
			if(this.compactnessUpperBound<=0){
				throw new Exception("cluster compactness upper bound is not correctly set!");
			}
			else{
				while(true){
					int index1=0,index2=0;
					double min=Double.MAX_VALUE;
					double previousMin=0.0;
					/*
					 * after loop, the most similar two groups will be located 
					 */
					for(int i=0;i<this.result.size();i++){
						for(int j=0;j<this.result.size();j++){
							if(i!=j){
								double temp=groupDist(this.result.get(i),this.result.get(j));
								//a trick to speed up
								if(temp==previousMin || temp==previousMin+1){
									min=temp;
									index1=i;
									index2=j;
									break;
								}
								if(temp<min){
									min=temp;
									index1=i;
									index2=j;
								}
							}
						}
					}
					if(min<=this.compactnessUpperBound){
						//System.out.println("merge!");
						//merge group(index1) and group(index2)
//						ArrayList<boolean[]> toAdd=this.result.get(index1);
//						ArrayList<boolean[]> add=this.result.get(index2);
//						for(int i=0;i<add.size();i++){
//							toAdd.add(add.get(i));
//						}
						this.result.get(index1).addAll(this.result.get(index2));
						this.result.remove(index2);
						this.numberOfClusters--;
						previousMin=min;
					}
					else{
						//System.out.println(min);
						break;
					}
				}
			}
			if(numberOfClusters!=result.size()){
				numberOfClusters=result.size();
			}
			this.clusteringDone=true;
		}
	}
	
	
	/*
	 * three cases
	 * instance is a member of some cluster, then return 0, which means the event has happened before
	 * instance forms a new group, then return -1, which means the event has never happened before and it has no similar events
	 * instance falls into some group, then return the index of the that group
	 */
	public int classifyInstance(boolean[] blist) throws Exception{
		if(this.clusteringDone){
			double min=Double.MAX_VALUE;
			double temp;
			int index=0;
			for(int i=0;i<result.size();i++){
				temp=insGroupdist(blist, result.get(i));
				if(temp<min){
					min=temp;
					index=i;
				}
			}
			if(min==0.0){
				return 0;
			}
			else if(min>this.compactnessUpperBound){
				return -1;
			}
			else{
				return index;
			}
		}
		else{
			throw new Exception("cannot classify instance before clustering is done!");
		}
	}
	
	public void importDataset(ArrayList<boolean[]> set){
		if(set.size()>0){
			this.dataSet=set;
			this.dataImported=true;
			this.clusteringDone=false;
			this.numberOfClusters=set.size();
			this.dataSetSize=set.size();
			this.instanceSize=set.get(0).length;
			//default compactness upper bound
			this.compactnessUpperBound=Math.sqrt((double)instanceSize);
			this.weight=new double[this.instanceSize];
			//default weight
			for(int i=0;i<instanceSize;i++){
				weight[i]=1.0;
			}
		}
	}
	
	public void setClusterCompactnessUpperBound(double ub){
		this.compactnessUpperBound=ub;
	}
	
	public void disableDynamicProgramming(){
		this.useDynamicProgramming=false;
	}
	public void enableDynamicProgramming(){
		this.useDynamicProgramming=true;
	}
	
	public void setWeightVector(double[] w){
		this.weight=w;
	}
	
	public LinkedList<ArrayList<Integer>> getResult(){
		return this.result;
	}
	
	protected double dist(boolean[] a,boolean[] b){
		double distance=0.0;
		for(int i=0;i<a.length;i++){
			if((a[i]&&!b[i]) || (!a[i]&&b[i])){
				distance+=weight[i]*1.0;
			}
		}
		//System.out.println(distance);
		return distance;
	}
	
	/*
	 * in agglomerative clustering, there are several ways to compute group distance
	 * single link, complete link, average link, and centroids
	 * here, we adopt complete link
	 */
	protected double groupDist(ArrayList<Integer> ga,ArrayList<Integer> gb){
		double dist=0.0;
		for(int i=0;i<ga.size();i++){
			for(int j=0;j<gb.size();j++){
				double temp;
				if(!this.useDynamicProgramming){
					temp=dist(dataSet.get(ga.get(i)),dataSet.get(gb.get(j)));
					
				}
				else{
					temp=map[ga.get(i)][gb.get(j)];
				}
				if(temp>dist){
					dist=temp;
				}
			}
		}
		//System.out.println(dist);
		return dist;
	}
	
	/*compute the distance between and instance and a group
	 * this distance might be larger than cluster compactness upper bound, then the instance forms a new cluster
	 */
	protected double insGroupdist(boolean[] ins, ArrayList<Integer> g){
		double dist=0.0;
		//do not try to fetch computed distance from hash map, because here ins might be a new object
		for(int i=0;i<g.size();i++){
			double temp=dist(ins,dataSet.get(g.get(i)));
			if(temp>dist){
				dist=temp;
			}
		}
		//note that dist might be larger than the compactness upper bound
		return dist;
	}
	
	public void printResult(){
		if(this.clusteringDone){
			System.out.println("size of data set: "+this.dataSet.size());
			System.out.println("dimensionality of each instance(event): "+this.instanceSize);
			System.out.println("cluster compactness upper bound: "+this.compactnessUpperBound);
			System.out.println("number of clusters: "+this.numberOfClusters);
			System.out.println();
			for(int i=0;i<this.result.size();i++){
				System.out.println("----cluster "+i+"----");
				for(int j=0;j<result.get(i).size();j++){
					printBooleanList(dataSet.get(result.get(i).get(j)));
				}
			}
		}
		else{
			System.out.println("clustering is not performed!");
		}
	}
	
	protected void printBooleanList(boolean[] blist){
		for(int i=0;i<blist.length;i++){
			if(blist[i]){
				System.out.print("1 ");
			}
			else{
				System.out.print("0 ");
			}
		}
		System.out.println();
	}
	
	public double testDist(boolean[] a, boolean[] b){
		return this.dist(a, b);
	}
	
	public static void main(String[] args){
		AgglomerativeClustering ac=new AgglomerativeClustering();
		double[] weight={1.0,1.0,1.0};
		ac.setWeightVector(weight);
		boolean[] a={true,false,true};
		boolean[] b={false,true,false};
		System.out.println(ac.testDist(a, b));
	}
	
	
}
