package java_sqlite_3005;

public class Action {

	private String action;
	private int    duration; 
	private int    actionID;
	
	public Action(int id, String a, int d){
		actionID=id;
		action  =a;
		duration=d;
	}
	
	public String getAction()   	 {return action;  }
	public int    getDuration() 	 {return duration;}
	public int    getActionID()		 {return actionID;}
	
	public void setAction(String action)  {this.action = action;}
	public void setDuration(int duration) {this.duration = duration;}
	public void setActionID(int actionID) {this.actionID = actionID;}

	public String toString(){
		return "-"+action+" for "+duration;
	}
}
