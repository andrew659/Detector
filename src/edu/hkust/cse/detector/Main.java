package edu.hkust.cse.detector;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * @author andrew	Jun 28, 2011
 * this is the main class which implements Sama's algorithms based on state matrix. 
 * Something like constraints are hard coded.
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//construct 12 propositional context variables
		//Agps: GPS.isValid()
		PropositionalContextVar<Boolean> Agps=new PropositionalContextVar<Boolean>((short)0, "Agps", ContextType.GPS_VALID,ContextOperator.EQUAL, true);
		//Bgps: GPS.location()=HOME
		PropositionalContextVar<String> Bgps=new PropositionalContextVar<String>((short)1, "Bgps", ContextType.GPS_LOCATION, ContextOperator.EQUAL,"HOME" );
		//Cgps: GPS.location()=OFFICE
		PropositionalContextVar<String> Cgps=new PropositionalContextVar<String>((short)2, "Cgps", ContextType.GPS_LOCATION, ContextOperator.EQUAL, "OFFICE");
		//Dgps: GPS.speed()>5
		PropositionalContextVar<Integer> Dgps=new PropositionalContextVar<Integer>((short)3, "Dgps", ContextType.GPS_SPEED, ContextOperator.GREATER, 5);
		//Egps: GPS.speed()>70
		PropositionalContextVar<Integer> Egps=new PropositionalContextVar<Integer>((short)4, "Egps",ContextType.GPS_SPEED , ContextOperator.GREATER, 70);
		//Abt: BT=CAR_HANDSFREE
		PropositionalContextVar<String> Abt=new PropositionalContextVar<String>((short)5, "Abt", ContextType.BT, ContextOperator.EQUAL, "CAR_HANDSFREE");
		//Bbt: BT=HOME_PC
		PropositionalContextVar<String> Bbt=new PropositionalContextVar<String>((short)6, "Bbt", ContextType.BT, ContextOperator.EQUAL, "HOME_PC");
		//Cbt: BT=OFFICE_PC
		PropositionalContextVar<String> Cbt=new PropositionalContextVar<String>((short)7, "Cbt", ContextType.BT, ContextOperator.EQUAL, "OFFICE_PC");
		//Dbt: BT=OFFICE_PC_*
		PropositionalContextVar<String> Dbt=new PropositionalContextVar<String>((short)8, "Dbt", ContextType.BT, ContextOperator.EQUAL, "OFFICE_PC_*");
		//Ebt: BT.count()>=3
		PropositionalContextVar<Integer> Ebt=new PropositionalContextVar<Integer>((short)9, "Ebt", ContextType.BT_COUNT, ContextOperator.GREATER_EQUAL, 3);
		//At: Time>=MEETING_START
		PropositionalContextVar<String> At=new PropositionalContextVar<String>((short)10, "At", ContextType.TIME, ContextOperator.GREATER_EQUAL, "MEETING_START");
		//Bt: Time>=MEETING_END
		PropositionalContextVar<String> Bt=new PropositionalContextVar<String>((short)11, "Bt", ContextType.TIME, ContextOperator.GREATER_EQUAL, "MEETING_END");
		
		//construct 9 states
		State general=new State((short)0, "General");
		State outdoor=new State((short)1, "Outdoor");
		State office=new State((short)2, "Office");
		State jogging=new State((short)3, "Jogging");
		State driving=new State((short)4, "Driving");
		State drivingFast=new State((short)5, "DrivingFast");
		State home=new State((short)6, "Home");
		State meeting=new State((short)7, "Meeting");
		State sync=new State((short)8, "Sync");
		
		//construct 16 rules
		//rule 1: ActivateOutdoor general-->outdoor Agps ^ !Bgps ^ !Cgps 5
		BooleanFunc temp=new BooleanFunc();
		temp.getCVNoList().add(Agps.getNo());
		temp.getCVNoList().add(Bgps.getNo());
		temp.getCVNoList().add(Cgps.getNo());
		temp.setConn((byte)1);
		temp.setSingle(false);
		temp.getForm().add(true);
		temp.getForm().add(false);
		temp.getForm().add(false);
		Rule ActivateOutdoor=new Rule();
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
		Rule DeactivateOutdoor=new Rule();
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
		Rule ActivateJogging=new Rule();
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
		Rule DeactivateJogging=new Rule();
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
		Rule ActivateDriving1=new Rule();
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
		Rule ActivateDriving2=new Rule();
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
		Rule ActivateDriving3=new Rule();
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
		Rule ActivateDriving4=new Rule();
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
		Rule DeactivateDriving=new Rule();
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
		Rule ActivateDrivingFast=new Rule();
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
		Rule DeactivateDrivingFast=new Rule();
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
		Rule ActivateHome=new Rule();
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
		Rule DeactivateHome=new Rule();
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
		Rule ActivateOffice=new Rule();
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
		Rule DeactivateOffice=new Rule();
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
		Rule ActivateMeeting=new Rule();
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
		Rule DeactivateMeeting=new Rule();
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
		Rule ActivateSync=new Rule();
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
		Rule DeactivateSync=new Rule();
		ActivateSync.setNo((short)18);
		ActivateSync.setName("DeactivateSync");
		ActivateSync.setCurrentStateNo(sync.getNo());
		ActivateSync.setNewStateNo(general.getNo());
		ActivateSync.setIsSingle(true);
		ActivateSync.setConn((byte)-1);
		ActivateSync.setForm(false);
		ActivateSync.setPriority((byte)9);
		ActivateSync.getFullPredicate().add(temp);
	}

}
