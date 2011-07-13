package edu.hkust.cse.detector;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author andrew	Jun 28, 2011
 * this is the main class which implements Sama's algorithms based on state matrix. 
 * Something like constraints are hard coded.
 */
public class Main {

	/**
	 * @param args
	 */
	//declare 12 propositional context variables
	private static PropositionalContextVar<Boolean> Agps;
	private static PropositionalContextVar<String> Bgps;
	private static PropositionalContextVar<String> Cgps;
	private static PropositionalContextVar<Integer> Dgps;
	private static PropositionalContextVar<Integer> Egps;
	private static PropositionalContextVar<String> Abt;
	private static PropositionalContextVar<String> Bbt;
	private static PropositionalContextVar<String> Cbt;
	private static PropositionalContextVar<String> Dbt;
	private static PropositionalContextVar<Integer> Ebt;
	private static PropositionalContextVar<String> At;
	private static PropositionalContextVar<String> Bt;
	private static int pcvCount=0;
	
	//declare 9 states
	private static State general;
	private static State outdoor;
	private static State office;
	private static State jogging;
	private static State driving;
	private static State drivingFast;
	private static State home;
	private static State meeting;
	private static State sync;
	private static int stateCount=0;
	private static State[] stateList;
	
	//declare 19 rules
	private static Rule ActivateOutdoor;
	private static Rule DeactivateOutdoor;
	private static Rule ActivateJogging;
	private static Rule DeactivateJogging;
	private static Rule ActivateDriving1;
	private static Rule ActivateDriving2;
	private static Rule ActivateDriving3;
	private static Rule ActivateDriving4;
	private static Rule DeactivateDriving;
	private static Rule ActivateDrivingFast;
	private static Rule DeactivateDrivingFast;
	private static Rule ActivateHome;
	private static Rule DeactivateHome;
	private static Rule ActivateOffice;
	private static Rule DeactivateOffice;
	private static Rule ActivateMeeting;
	private static Rule DeactivateMeeting;
	private static Rule ActivateSync;
	private static Rule DeactivateSync;
	private static int ruleCount=0;
	private static Rule[] ruleList;
	
	private static int raceCount=0;
	private static int cycleCount=0;
	private static int fpCount=0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//construct 12 propositional context variables
		//Agps: GPS.isValid()
		Agps=new PropositionalContextVar<Boolean>((short)0, "Agps", ContextType.GPS_VALID,ContextOperator.EQUAL, true);
		pcvCount++;
		//Bgps: GPS.location()=HOME
		Bgps=new PropositionalContextVar<String>((short)1, "Bgps", ContextType.GPS_LOCATION, ContextOperator.EQUAL,"HOME" );
		pcvCount++;
		//Cgps: GPS.location()=OFFICE
		Cgps=new PropositionalContextVar<String>((short)2, "Cgps", ContextType.GPS_LOCATION, ContextOperator.EQUAL, "OFFICE");
		pcvCount++;
		//Dgps: GPS.speed()>5
		Dgps=new PropositionalContextVar<Integer>((short)3, "Dgps", ContextType.GPS_SPEED, ContextOperator.GREATER, 5);
		pcvCount++;
		//Egps: GPS.speed()>70
		Egps=new PropositionalContextVar<Integer>((short)4, "Egps",ContextType.GPS_SPEED , ContextOperator.GREATER, 70);
		pcvCount++;
		//Abt: BT=CAR_HANDSFREE
		Abt=new PropositionalContextVar<String>((short)5, "Abt", ContextType.BT, ContextOperator.EQUAL, "CAR_HANDSFREE");
		pcvCount++;
		//Bbt: BT=HOME_PC
		Bbt=new PropositionalContextVar<String>((short)6, "Bbt", ContextType.BT, ContextOperator.EQUAL, "HOME_PC");
		pcvCount++;
		//Cbt: BT=OFFICE_PC
		Cbt=new PropositionalContextVar<String>((short)7, "Cbt", ContextType.BT, ContextOperator.EQUAL, "OFFICE_PC");
		pcvCount++;
		//Dbt: BT=OFFICE_PC_*
		Dbt=new PropositionalContextVar<String>((short)8, "Dbt", ContextType.BT, ContextOperator.EQUAL, "OFFICE_PC_*");
		pcvCount++;
		//Ebt: BT.count()>=3
		Ebt=new PropositionalContextVar<Integer>((short)9, "Ebt", ContextType.BT_COUNT, ContextOperator.GREATER_EQUAL, 3);
		pcvCount++;
		//At: Time>=MEETING_START
		At=new PropositionalContextVar<String>((short)10, "At", ContextType.TIME, ContextOperator.GREATER_EQUAL, "MEETING_START");
		pcvCount++;
		//Bt: Time>=MEETING_END
		Bt=new PropositionalContextVar<String>((short)11, "Bt", ContextType.TIME, ContextOperator.GREATER_EQUAL, "MEETING_END");
		pcvCount++;
		
		
		//construct 9 states
		ArrayList<State> tempStateList=new ArrayList<State>();
		general=new State((short)0, "General");
		tempStateList.add(general);
		stateCount++;
		outdoor=new State((short)1, "Outdoor");
		tempStateList.add(outdoor);
		stateCount++;
		office=new State((short)2, "Office");
		tempStateList.add(office);
		stateCount++;
		jogging=new State((short)3, "Jogging");
		tempStateList.add(jogging);
		stateCount++;
		driving=new State((short)4, "Driving");
		tempStateList.add(driving);
		stateCount++;
		drivingFast=new State((short)5, "DrivingFast");
		tempStateList.add(drivingFast);
		stateCount++;
		home=new State((short)6, "Home");
		tempStateList.add(home);
		stateCount++;
		meeting=new State((short)7, "Meeting");
		tempStateList.add(meeting);
		stateCount++;
		sync=new State((short)8, "Sync");
		tempStateList.add(sync);
		stateCount++;
		
		stateList=new State[stateCount];
		for(int i=0;i<stateCount;i++){
			stateList[i]=tempStateList.get(i);
		}
		tempStateList=null;
		
		
		//construct 16 rules
		//rule 1: ActivateOutdoor general-->outdoor Agps ^ !Bgps ^ !Cgps 5
		ArrayList<Rule> tempList=new ArrayList<Rule>();
		BooleanFunc temp=new BooleanFunc();
		temp.getCVNoList().add(Agps.getNo());
		temp.getCVNoList().add(Bgps.getNo());
		temp.getCVNoList().add(Cgps.getNo());
		temp.setConn((byte)1);
		temp.setSingle(false);
		temp.getForm().add(true);
		temp.getForm().add(false);
		temp.getForm().add(false);
		ActivateOutdoor=new Rule();
		tempList.add(ActivateOutdoor);
		ruleCount++;
		ActivateOutdoor.setNo((short)0);
		ActivateOutdoor.setName("ActivateOutdoor");
		ActivateOutdoor.setCurrentStateNo(general.getNo());
		ActivateOutdoor.setNewStateNo(outdoor.getNo());
		ActivateOutdoor.setIsSingle(true);
		ActivateOutdoor.setConn((byte)-1);
		ActivateOutdoor.setForm(true);
		ActivateOutdoor.setPriority((byte)5);
		ActivateOutdoor.getFullPredicate().add(temp);
		
		//rule 2: DeactivateOutdoor ourdoor-->general !(Agps ^ !Bgps ^ !Cgps) 5
		DeactivateOutdoor=new Rule();
		tempList.add(DeactivateOutdoor);
		ruleCount++;
		DeactivateOutdoor.setNo((short)1);
		DeactivateOutdoor.setName("DeactivateOutdoor");
		DeactivateOutdoor.setCurrentStateNo(outdoor.getNo());
		DeactivateOutdoor.setNewStateNo(general.getNo());
		DeactivateOutdoor.setIsSingle(true);
		DeactivateOutdoor.setConn((byte)-1);
		DeactivateOutdoor.setForm(false);
		DeactivateOutdoor.setPriority((byte)5);
		DeactivateOutdoor.getFullPredicate().add(temp);
		
		//rule 3: ActivateJogging outdoor-->jogging Agps ^ Dgps 5
		temp=new BooleanFunc();
		temp.getCVNoList().add(Agps.getNo());
		temp.getCVNoList().add(Dgps.getNo());
		temp.setConn((byte)1);
		temp.setSingle(false);
		temp.getForm().add(true);
		temp.getForm().add(true);
		ActivateJogging=new Rule();
		tempList.add(ActivateJogging);
		ruleCount++;
		ActivateJogging.setNo((short)2);
		ActivateJogging.setName("ActivateJogging");
		ActivateJogging.setCurrentStateNo(outdoor.getNo());
		ActivateJogging.setNewStateNo(jogging.getNo());
		ActivateJogging.setIsSingle(true);
		ActivateJogging.setConn((byte)-1);
		ActivateJogging.setForm(true);
		ActivateJogging.setPriority((byte)5);
		ActivateJogging.getFullPredicate().add(temp);
		
		//rule 4: DeactivateJogging jogging-->outdoor !(Agps ^ Dgps) 5
		DeactivateJogging=new Rule();
		tempList.add(DeactivateJogging);
		ruleCount++;
		DeactivateJogging.setNo((short)3);
		DeactivateJogging.setName("DeactivateJogging");
		DeactivateJogging.setCurrentStateNo(jogging.getNo());
		DeactivateJogging.setNewStateNo(outdoor.getNo());
		DeactivateJogging.setIsSingle(true);
		DeactivateJogging.setConn((byte)-1);
		DeactivateJogging.setForm(false);
		DeactivateJogging.setPriority((byte)5);
		DeactivateJogging.getFullPredicate().add(temp);
		
		//rule 5: ActivateDriving general-->driving Abt 1
		temp=new BooleanFunc();
		temp.getCVNoList().add(Abt.getNo());
		temp.setConn((byte)-1);
		temp.setSingle(true);
		temp.getForm().add(true);
		ActivateDriving1=new Rule();
		tempList.add(ActivateDriving1);
		ruleCount++;
		ActivateDriving1.setNo((short)4);
		ActivateDriving1.setName("ActivateDriving");
		ActivateDriving1.setCurrentStateNo(general.getNo());
		ActivateDriving1.setNewStateNo(driving.getNo());
		ActivateDriving1.setIsSingle(true);
		ActivateDriving1.setConn((byte)-1);
		ActivateDriving1.setForm(true);
		ActivateDriving1.setPriority((byte)1);
		ActivateDriving1.getFullPredicate().add(temp);
		
		//rule 6: ActivateDriving home-->driving Abt 1
		ActivateDriving2=new Rule();
		tempList.add(ActivateDriving2);
		ruleCount++;
		ActivateDriving2.setNo((short)5);
		ActivateDriving2.setName("ActivateDriving");
		ActivateDriving2.setCurrentStateNo(home.getNo());
		ActivateDriving2.setNewStateNo(driving.getNo());
		ActivateDriving2.setIsSingle(true);
		ActivateDriving2.setConn((byte)-1);
		ActivateDriving2.setForm(true);
		ActivateDriving2.setPriority((byte)1);
		ActivateDriving2.getFullPredicate().add(temp);
		
		//rule 7: ActivateDriving office-->driving Abt 1
		ActivateDriving3=new Rule();
		tempList.add(ActivateDriving3);
		ruleCount++;
		ActivateDriving3.setNo((short)6);
		ActivateDriving3.setName("ActivateDriving");
		ActivateDriving3.setCurrentStateNo(office.getNo());
		ActivateDriving3.setNewStateNo(driving.getNo());
		ActivateDriving3.setIsSingle(true);
		ActivateDriving3.setConn((byte)-1);
		ActivateDriving3.setForm(true);
		ActivateDriving3.setPriority((byte)1);
		ActivateDriving3.getFullPredicate().add(temp);
		
		//rule 8: ActivateDriving outdoor-->driving Abt 1
		ActivateDriving4=new Rule();
		tempList.add(ActivateDriving4);
		ruleCount++;
		ActivateDriving4.setNo((short)7);
		ActivateDriving4.setName("ActivateDriving");
		ActivateDriving4.setCurrentStateNo(outdoor.getNo());
		ActivateDriving4.setNewStateNo(driving.getNo());
		ActivateDriving4.setIsSingle(true);
		ActivateDriving4.setConn((byte)-1);
		ActivateDriving4.setForm(true);
		ActivateDriving4.setPriority((byte)1);
		ActivateDriving4.getFullPredicate().add(temp);
		
		//rule 9: DeactivateDriving driving-->general !Abt 1
		DeactivateDriving=new Rule();
		tempList.add(DeactivateDriving);
		ruleCount++;
		DeactivateDriving.setNo((short)8);
		DeactivateDriving.setName("DeactivateDriving");
		DeactivateDriving.setCurrentStateNo(driving.getNo());
		DeactivateDriving.setNewStateNo(general.getNo());
		DeactivateDriving.setIsSingle(true);
		DeactivateDriving.setConn((byte)-1);
		DeactivateDriving.setForm(false);
		DeactivateDriving.setPriority((byte)1);
		DeactivateDriving.getFullPredicate().add(temp);
		
		//rule 10: ActivateDrivingFast driving-->drivingfast Agps ^ Egps 0
		temp=new BooleanFunc();
		temp.getCVNoList().add(Agps.getNo());
		temp.getCVNoList().add(Egps.getNo());
		temp.setConn((byte)1);
		temp.setSingle(false);
		temp.getForm().add(true);
		temp.getForm().add(true);
		ActivateDrivingFast=new Rule();
		tempList.add(ActivateDrivingFast);
		ruleCount++;
		ActivateDrivingFast.setNo((short)9);
		ActivateDrivingFast.setName("ActivateDrivingFast");
		ActivateDrivingFast.setCurrentStateNo(driving.getNo());
		ActivateDrivingFast.setNewStateNo(drivingFast.getNo());
		ActivateDrivingFast.setIsSingle(true);
		ActivateDrivingFast.setConn((byte)-1);
		ActivateDrivingFast.setForm(true);
		ActivateDrivingFast.setPriority((byte)0);
		ActivateDrivingFast.getFullPredicate().add(temp);
		
		//rule 11: DeactivateDrivingFast drivingfast-->driving !(Agps ^ Egps) 0
		DeactivateDrivingFast=new Rule();
		tempList.add(DeactivateDrivingFast);
		ruleCount++;
		DeactivateDrivingFast.setNo((short)10);
		DeactivateDrivingFast.setName("DeactivateDrivingFast");
		DeactivateDrivingFast.setCurrentStateNo(drivingFast.getNo());
		DeactivateDrivingFast.setNewStateNo(driving.getNo());
		DeactivateDrivingFast.setIsSingle(true);
		DeactivateDrivingFast.setConn((byte)-1);
		DeactivateDrivingFast.setForm(false);
		DeactivateDrivingFast.setPriority((byte)0);
		DeactivateDrivingFast.getFullPredicate().add(temp);
		
		//rule 12: ActivateHome general-->home Bbt v (Agps ^ Bgps) 5
		temp=new BooleanFunc();
		BooleanFunc temp1=new BooleanFunc();
		temp.getCVNoList().add(Bbt.getNo());
		temp.setConn((byte)-1);
		temp.setSingle(true);
		temp.getForm().add(true);
		temp1.getCVNoList().add(Agps.getNo());
		temp1.getCVNoList().add(Bgps.getNo());
		temp1.setConn((byte)1);
		temp1.setSingle(false);
		temp1.getForm().add(true);
		temp1.getForm().add(true);
		ActivateHome=new Rule();
		tempList.add(ActivateHome);
		ruleCount++;
		ActivateHome.setNo((short)11);
		ActivateHome.setName("ActivateHome");
		ActivateHome.setCurrentStateNo(general.getNo());
		ActivateHome.setNewStateNo(home.getNo());
		ActivateHome.setIsSingle(false);
		ActivateHome.setConn((byte)0);
		ActivateHome.setForm(true);
		ActivateHome.setPriority((byte)5);
		ActivateHome.getFullPredicate().add(temp);
		ActivateHome.getFullPredicate().add(temp1);
		
		//rule 13: DeactivateHome home-->general !(Bbt v (Agps ^ Bgps)) 5
		DeactivateHome=new Rule();
		tempList.add(DeactivateHome);
		ruleCount++;
		DeactivateHome.setNo((short)12);
		DeactivateHome.setName("DeactivateHome");
		DeactivateHome.setCurrentStateNo(home.getNo());
		DeactivateHome.setNewStateNo(general.getNo());
		DeactivateHome.setIsSingle(false);
		DeactivateHome.setConn((byte)0);
		DeactivateHome.setForm(false);
		DeactivateHome.setPriority((byte)5);
		DeactivateHome.getFullPredicate().add(temp);
		DeactivateHome.getFullPredicate().add(temp1);
		
		//rule 14: ActivateOffice general-->office Cbt v Dbt v (Agps ^ Cgps) 5
		temp=new BooleanFunc();
		temp1=new BooleanFunc();
		BooleanFunc temp2=new BooleanFunc();
		temp.getCVNoList().add(Cbt.getNo());
		temp.setConn((byte)-1);
		temp.setSingle(true);
		temp.getForm().add(true);
		temp1.getCVNoList().add(Dbt.getNo());
		temp1.setConn((byte)-1);
		temp1.setSingle(true);
		temp1.getForm().add(true);
		temp2.getCVNoList().add(Agps.getNo());
		temp2.getCVNoList().add(Cgps.getNo());
		temp2.setConn((byte)1);
		temp2.setSingle(false);
		temp2.getForm().add(true);
		temp2.getForm().add(true);
		ActivateOffice=new Rule();
		tempList.add(ActivateOffice);
		ruleCount++;
		ActivateOffice.setNo((short)13);
		ActivateOffice.setName("ActivateOffice");
		ActivateOffice.setCurrentStateNo(general.getNo());
		ActivateOffice.setNewStateNo(office.getNo());
		ActivateOffice.setIsSingle(false);
		ActivateOffice.setConn((byte)0);
		ActivateOffice.setForm(true);
		ActivateOffice.setPriority((byte)5);
		ActivateOffice.getFullPredicate().add(temp);
		ActivateOffice.getFullPredicate().add(temp1);
		ActivateOffice.getFullPredicate().add(temp2);
		
		//rule 15: DeactivateOffice office-->general !(Cbt v Dbt v (Agps ^ Cgps)) 5
		DeactivateOffice=new Rule();
		tempList.add(DeactivateOffice);
		ruleCount++;
		DeactivateOffice.setNo((short)14);
		DeactivateOffice.setName("DeactivateOffice");
		DeactivateOffice.setCurrentStateNo(office.getNo());
		DeactivateOffice.setNewStateNo(general.getNo());
		DeactivateOffice.setIsSingle(false);
		DeactivateOffice.setConn((byte)0);
		DeactivateOffice.setForm(false);
		DeactivateOffice.setPriority((byte)5);
		DeactivateOffice.getFullPredicate().add(temp);
		DeactivateOffice.getFullPredicate().add(temp1);
		DeactivateOffice.getFullPredicate().add(temp2);
		
		//rule 16: ActivateMeeting office-->meeting At ^ Ebt 4
		temp=new BooleanFunc();
		temp.getCVNoList().add(At.getNo());
		temp.getCVNoList().add(Ebt.getNo());
		temp.setConn((byte)1);
		temp.setSingle(false);
		temp.getForm().add(true);
		temp.getForm().add(true);
		ActivateMeeting=new Rule();
		tempList.add(ActivateMeeting);
		ruleCount++;
		ActivateMeeting.setNo((short)15);
		ActivateMeeting.setName("ActivateMeeting");
		ActivateMeeting.setCurrentStateNo(office.getNo());
		ActivateMeeting.setNewStateNo(meeting.getNo());
		ActivateMeeting.setIsSingle(true);
		ActivateMeeting.setConn((byte)-1);
		ActivateMeeting.setForm(true);
		ActivateMeeting.setPriority((byte)4);
		ActivateMeeting.getFullPredicate().add(temp);
		
		//rule 17: DeactivateMeeting meeting-->office Bt 4
		temp=new BooleanFunc();
		temp.getCVNoList().add(Bt.getNo());
		temp.setConn((byte)-1);
		temp.setSingle(true);
		temp.getForm().add(true);
		DeactivateMeeting=new Rule();
		tempList.add(DeactivateMeeting);
		ruleCount++;
		DeactivateMeeting.setNo((short)16);
		DeactivateMeeting.setName("DeactivateMeeting");
		DeactivateMeeting.setCurrentStateNo(meeting.getNo());
		DeactivateMeeting.setNewStateNo(office.getNo());
		DeactivateMeeting.setIsSingle(true);
		DeactivateMeeting.setConn((byte)-1);
		DeactivateMeeting.setForm(true);
		DeactivateMeeting.setPriority((byte)4);
		DeactivateMeeting.getFullPredicate().add(temp);
		
		//rule 18: ActivateSync general-->sync Bbt v Cbt 9
		temp=new BooleanFunc();
		temp.getCVNoList().add(Bbt.getNo());
		temp.getCVNoList().add(Cbt.getNo());
		temp.setConn((byte)0);
		temp.setSingle(false);
		temp.getForm().add(true);
		temp.getForm().add(true);
		ActivateSync=new Rule();
		tempList.add(ActivateSync);
		ruleCount++;
		ActivateSync.setNo((short)17);
		ActivateSync.setName("ActivateSync");
		ActivateSync.setCurrentStateNo(general.getNo());
		ActivateSync.setNewStateNo(sync.getNo());
		ActivateSync.setIsSingle(true);
		ActivateSync.setConn((byte)-1);
		ActivateSync.setForm(true);
		ActivateSync.setPriority((byte)9);
		ActivateSync.getFullPredicate().add(temp);
		
		//rule 19: DeactivateSync sync-->general !(Bbt v Cbt) 9
		DeactivateSync=new Rule();
		tempList.add(DeactivateSync);
		ruleCount++;
		DeactivateSync.setNo((short)18);
		DeactivateSync.setName("DeactivateSync");
		DeactivateSync.setCurrentStateNo(sync.getNo());
		DeactivateSync.setNewStateNo(general.getNo());
		DeactivateSync.setIsSingle(true);
		DeactivateSync.setConn((byte)-1);
		DeactivateSync.setForm(false);
		DeactivateSync.setPriority((byte)9);
		DeactivateSync.getFullPredicate().add(temp);
		
		temp=null;
		temp1=null;
		temp2=null;
//		System.out.println(pcvCount);
//		System.out.println(stateCount);
//		System.out.println(ruleCount);
		ruleList=new Rule[ruleCount];
		for(int i=0;i<ruleCount;i++){
			ruleList[i]=tempList.get(i);
		}
		
		StateMatrix[] smList=new StateMatrix[stateCount];
		for(int i=0;i<stateCount;i++){
			smList[i]=constructStateMatrix(stateList[i]);
		}
		
		boolean checkUndeterminism=true;
		boolean checkDeadPredicate=false;
		boolean checkAdaptationRace=false;
		boolean checkUnreachability=false;
		
		if(checkUndeterminism){
			for(int i=0;i<stateList.length;i++){
				undeterminismDetection(stateList[i], smList[i]);
			}
		}
		
		if(checkDeadPredicate){
			for(int i=0;i<stateList.length;i++){
				deadPredicateDetection(stateList[i], smList[i]);
			}
		}
		
		if(checkAdaptationRace){
			for(int i=0;i<stateList.length;i++){
				adaptationRaceDetection(stateList[i],smList[i]);
			}
		}
		
		if(checkUnreachability){
			unreachableStateDetection(general, smList);
		}
		
		System.out.println("cycleCount="+cycleCount);
		System.out.println("raceCount="+raceCount);
		System.out.println("fpCount="+fpCount);
	}
	
	public static boolean globalConstriantsSatisfied(boolean[] smRow){
		//!Agps -> (!Bgps ^ !Cgps ^ !Dgps ^ !Egps)
		if(!smRow[Agps.getNo()]){
			if(smRow[Bgps.getNo()]){
				return false;
			}
			if(smRow[Cgps.getNo()]){
				return false;
			}
			if(smRow[Dgps.getNo()]){
				return false;
			}
			if(smRow[Egps.getNo()]){
				return false;
			}
		}
		
		//(Bgps=>!Cgps) ^ (Cgps=>!Bgps) locations are mutually exclusive
		if(smRow[Bgps.getNo()]==true && smRow[Cgps.getNo()]==true){
			return false;
		}
		
		//Egps=>Dgps
		if(smRow[Egps.getNo()] && !smRow[Dgps.getNo()]){
			return false;
		}
		
		//Bt=>At
		if(smRow[Bt.getNo()] && !smRow[At.getNo()]){
			return false;
		}
		return true;
	}
	
	public static StateMatrix constructStateMatrix(State s){
		StateMatrix sm=new StateMatrix();
		ArrayList<Rule> relatedRules=getActiveRules(s);
		boolean[] pcvValueList=new boolean[pcvCount];
		explore(pcvValueList,0,sm,relatedRules);
		return sm;
		
	}
	
	public static ArrayList<Rule> getActiveRules(State s){
		ArrayList<Rule> activeRules=new ArrayList<Rule>();
		for(int i=0;i<ruleCount;i++){
			if(ruleList[i].getCurrentStateNo()==s.getNo()){
				activeRules.add(ruleList[i]);
			}
		}
		return activeRules;
	}
	
	public static void explore(boolean[] blist,int i,StateMatrix sm,ArrayList<Rule> relatedRuleList){
		if(i==blist.length-1){
			//first case
			blist[i]=false;
			boolean flag=false;
			byte min=Byte.MAX_VALUE;
			ArrayList<Rule> ruleList=new ArrayList<Rule>();
			if(globalConstriantsSatisfied(blist)){
				for(int j=0;j<relatedRuleList.size();j++){
					if(relatedRuleList.get(j).satisfied(blist)){
						flag=true;
						ruleList.add(relatedRuleList.get(j));
						if(relatedRuleList.get(j).getPriority()<min){
							min=relatedRuleList.get(j).getPriority();
						}
					}
				}
			}
			if(flag){
				//first remove rules with low priority in rule list
				if(ruleList.size()>1){
					ArrayList<Rule> toDelete=new ArrayList<Rule>();
					for(int j=0;j<ruleList.size();j++){
						byte temp=ruleList.get(j).getPriority();
						if(temp>min){
							toDelete.add(ruleList.get(j));
						}
					}
					for(int j=0;j<toDelete.size();j++){
						ruleList.removeAll(toDelete);
					}
				}
				sm.getMatrix().add(blist.clone());
				//out(blist);
				sm.getRuleListList().add(ruleList);
				//printRule(ruleList);
				//System.out.println();
			}
			
			//second case
			blist[i]=true;
			flag=false;
			min=Byte.MAX_VALUE;
			ruleList=new ArrayList<Rule>();
			if(globalConstriantsSatisfied(blist)){
				for(int j=0;j<relatedRuleList.size();j++){
					if(relatedRuleList.get(j).satisfied(blist)){
						flag=true;
						ruleList.add(relatedRuleList.get(j));
						if(relatedRuleList.get(j).getPriority()<min){
							min=relatedRuleList.get(j).getPriority();
						}
					}
				}
			}
			if(flag){
				//first remove rules with low priority in rule list
				if(ruleList.size()>1){
					ArrayList<Rule> toDelete=new ArrayList<Rule>();
					for(int j=0;j<ruleList.size();j++){
						byte temp=ruleList.get(j).getPriority();
						if(temp>min){
							toDelete.add(ruleList.get(j));
						}
					}
					for(int j=0;j<toDelete.size();j++){
						//System.out.println("remove");
						ruleList.removeAll(toDelete);
					}
				}
				sm.getMatrix().add(blist.clone());
				//out(blist);
				sm.getRuleListList().add(ruleList);
				//printRule(ruleList);
				//System.out.println();
			}
		}
		else{
			blist[i]=false;
			explore(blist,i+1,sm,relatedRuleList);
			blist[i]=true;
			explore(blist,i+1,sm,relatedRuleList);
		}
		
	}
	public static void out(boolean[] blist){
		for(int i=0;i<blist.length;i++){
			if(blist[i]){
				System.out.print(1+" ");
			}
			else{
				System.out.print(0+" ");
			}
		}
		//System.out.println();
	}
	public static void printRule(ArrayList<Rule> rlist){
		for(int i=0;i<rlist.size();i++){
			System.out.print(rlist.get(i).getName()+"\t");
		}
	}
	
	public static void undeterminismDetection(State s,StateMatrix sm){
		for(int i=0;i<sm.getMatrix().size();i++){
			if(sm.getRuleListList().get(i).size()>1){
				out(sm.getMatrix().get(i));
				printRule(sm.getRuleListList().get(i));
				System.out.println();
			}
		}
	}
	
	public static void deadPredicateDetection(State s,StateMatrix sm){
		//get active rules
		ArrayList<Rule> untriggeredRules=getActiveRules(s);
		int size=untriggeredRules.size();
		for(int i=0;i<sm.getMatrix().size();i++){
			if(untriggeredRules.size()==0){
				break;
			}
			untriggeredRules.removeAll(sm.getRuleListList().get(i));
		}
		if(untriggeredRules.size()!=0){
			System.out.println("in state "+s.getName()+" the following rules are dead");
			printRule(untriggeredRules);
			System.out.println();
		}
		if(untriggeredRules.size()==size){
			System.out.println("the state "+s.getName()+" is dead");
		}
	}
	
	/*
	 * this algorithm is too slow, but the result is correct
	 */
	public static void unreachableStateDetection(State initialState,StateMatrix[] smList){
		ArrayList<State> next=new ArrayList<State>();
		next.add(initialState);
		ArrayList<State> toExplore=new ArrayList<State>();
		ArrayList<State> unreached=new ArrayList<State>();
		for(int i=0;i<stateList.length;i++){
			unreached.add(stateList[i]);
		}
		while(next.size()!=0){
			for(int i=0;i<next.size();i++){
				unreached.remove(next.get(i));
				//System.out.println(unreached.size());
				StateMatrix sm=smList[next.get(i).getNo()];
				for(int j=0;j<sm.getMatrix().size();j++){
					ArrayList<Rule> r=sm.getRuleListList().get(j);
					//printRule(r);
					for(int k=0;k<r.size();k++){
						if(unreached.contains(stateList[r.get(k).getNewStateNo()])){
							//System.out.println("add...");
							toExplore.add(stateList[r.get(k).getNewStateNo()]);
						}
					}
				}
			}
			next=toExplore;
			toExplore=new ArrayList<State>();
		}
		for(int i=0;i<unreached.size();i++){
			System.out.println(unreached.get(i).getName()+" is unreachable from initial state");
		}
	}
	
	/*
	 * randomly choose one rule if there is undeterminism
	 */
	public static void adaptationRaceDetection(State s,StateMatrix sm){
		//System.out.println(sm.getMatrix().size());
		//
		for(int i=0;i<sm.getMatrix().size();i++){
			//System.out.println(i);
			boolean[] blist=sm.getMatrix().get(i);
			ArrayList<Short> rlist=new ArrayList<Short>(); //explored rules
			ArrayList<Short> slist=new ArrayList<Short>(); //explored states
			boolean isCycle=false;
			ArrayList<Rule> satisfiedRules=sm.getRuleListList().get(i);
			Random rand=new Random();
			int random=0;
			if(satisfiedRules.size()>1){
				random=rand.nextInt(satisfiedRules.size());
			}
			short rNo=satisfiedRules.get(random).getNo();
			State destState=stateList[ruleList[rNo].getNewStateNo()];
//			boolean flag=slist.contains((Short)destState.getNo());
			while(destState!=null){
				//System.out.println(destState.getNo());
				//System.out.print(".");
				if(slist.contains((Short)destState.getNo()) || destState.getNo()==s.getNo()){
					isCycle=true;
					rlist.add(rNo);
					slist.add(destState.getNo());
					break;
				}
				rlist.add(rNo);
				slist.add(destState.getNo());
				ArrayList<Rule> activeRules=getActiveRules(destState);
				//remove those rules which can not be satisfied by blist
				ArrayList<Rule> toDelete=new ArrayList<Rule>();
				for(int j=0;j<activeRules.size();j++){
					byte temp=activeRules.get(j).getPriority();
					if(!activeRules.get(j).satisfied(blist)){
						toDelete.add(activeRules.get(j));
					}
				}
				for(int j=0;j<toDelete.size();j++){
					//System.out.println("remove");
					activeRules.removeAll(toDelete);
				}
				
				byte min=Byte.MAX_VALUE;
				for(int j=0;j<activeRules.size();j++){
					if(activeRules.get(j).getPriority()<min){
						min=activeRules.get(j).getPriority();
					}
				}
				if(activeRules.size()>1){
					toDelete=new ArrayList<Rule>();
					for(int j=0;j<activeRules.size();j++){
						byte temp=activeRules.get(j).getPriority();
						if(temp>min){
							toDelete.add(activeRules.get(j));
						}
					}
					for(int j=0;j<toDelete.size();j++){
						//System.out.println("remove");
						activeRules.removeAll(toDelete);
					}
				}
				if(activeRules.size()>1){
					random=rand.nextInt(activeRules.size());
				}
				else{
					random=0;
				}
				if(activeRules.size()>0){
					rNo=activeRules.get(random).getNo();
					destState=stateList[ruleList[rNo].getNewStateNo()];
//					flag=slist.contains((Short)destState.getNo());
				}
				else{
					destState=null;
//					flag=true;
				}
			}
			if(slist.size()>2){
				if(isCycle){
					
					if(isPossible(blist)){
						cycleCount++;
						System.out.print("cycle:");
						out(blist);
						System.out.print("\t"+s.getNo());
						for(int k=0;k<slist.size();k++){
							System.out.print("--<"+rlist.get(k)+">--");
							System.out.print(slist.get(k));
						}
						System.out.println();
					}
					else{
						fpCount++;
					}
					
				}
				else{
					
					if(isPossible(blist)){
						raceCount++;
						System.out.print("race:");
						out(blist);
						System.out.print("\t"+s.getNo());
						for(int k=0;k<slist.size();k++){
							System.out.print("--<"+rlist.get(k)+">--");
							System.out.print(slist.get(k));
						}
						System.out.println();
					}
					else{
						fpCount++;
					}
					
					
				}
			}
		}
		
	}
	
	//more constraints to remove false positives
	public static boolean isPossible(boolean[] blist){
		if(blist[Bbt.getNo()] && blist[Cbt.getNo()]){
			return false;
		}
		
		if(blist[Bbt.getNo()] && blist[Dbt.getNo()]){
			return false;
		}
		
		if(blist[Bgps.getNo()] && blist[Cbt.getNo()]){
			return false;
		}
		
		if(blist[Bgps.getNo()] && blist[Dbt.getNo()]){
			return false;
		}
		
		if(blist[Cgps.getNo()] && blist[Bbt.getNo()]){
			return false;
		}
		
		
		return true;
	}

}
