package es.eucm.eadventure.engine.core.data;

public class SaveTimer{
	
	 private int state;
	 
	 private long timeUpdate;
	 
	 private long lastUpdate;
	 
	 private boolean isAssessmentRule;
	 
	 public void setState(int state){
		 this.state = state;
	 }
	 
	 public void setTimeUpdate(long timeUpdate){
		 this.timeUpdate = timeUpdate;
	 }
	 
	 public void setLastUpdate(long lastUpdate){
		 this.lastUpdate = lastUpdate;
	 }
	 
	 public int getState(){
		 return state;
	 }
	 
	 public long getLastUpdate(){
		 return lastUpdate;
	 }
	
	 public long getTimeUpdate(){
		 return timeUpdate;
	 }

	public boolean isAssessmentRule() {
		return isAssessmentRule;
	}

	public void setAssessmentRule(boolean isAssessmentRule) {
		this.isAssessmentRule = isAssessmentRule;
	}
	
}
