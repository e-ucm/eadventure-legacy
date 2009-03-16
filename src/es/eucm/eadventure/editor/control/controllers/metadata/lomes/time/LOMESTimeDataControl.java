package es.eucm.eadventure.editor.control.controllers.metadata.lomes.time;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;

public class LOMESTimeDataControl extends LOMESDurationDataControl {


	public static final int MILISECONDS = 6;
	
	public static final int TIMEZONE = 7;
	
	protected int milisec;
	
	protected String timeZone;
	
	public LOMESTimeDataControl (){
		super();
		milisec =0;
		timeZone = new String();
	}
	
	public String toString(){
		String value = new String();
		if (years>0){
			value+=years+"-";
		}
		if (months>0&&months<13){
			value+=months+"-";
		}
		if (days>0&&days<32){
			value+=days+"-";
		}
		if (hours>0 || minutes>0 || seconds>0){
			value+="T";
		}
		if (hours>0&&hours<24){
			value+=hours+":";
		}
		if (minutes>0&&minutes<60){
			value+=minutes+":";
		}
		if (seconds>0 &&seconds <60){
			value+=seconds+":";
		}
		if (milisec>0 && milisec<10){
			value+=milisec;
		}
		
		if (timeZone!=""){
			value+=timeZone;
		}
		
		return value;


	}
	
	public void parseDuration( String duration ){
		
		
		
			if (duration.contains( "-" )){
				int posY = duration.indexOf( "-" );
				years = Integer.parseInt( duration.substring( 0, posY ) );
				duration = duration.substring( posY+1, duration.length( ) );
			}
			if (duration.contains( "-" ) ){
				int posM = duration.indexOf( "-" );
				months = Integer.parseInt( duration.substring( 0, posM ) );
				duration = duration.substring( posM+1, duration.length( ) );
			}
			if (duration.contains( "T" )){
				int posD = duration.indexOf( "D" );
				days = Integer.parseInt( duration.substring( 0, posD ) );
				duration = duration.substring( posD+1, duration.length( ) );
			}
			else  {
				days = Integer.parseInt( duration);
			}
			if (duration.contains( ":" )){
				int posH = duration.indexOf( ":" );
				hours = Integer.parseInt( duration.substring( 0, posH ) );
				duration = duration.substring( posH+1, duration.length( ) );
			}
			if (duration.contains( ":" )){
				int posM = duration.indexOf( ":" );
				minutes = Integer.parseInt( duration.substring( 0, posM ) );
				duration = duration.substring( posM+1, duration.length( ) );
			}
			int posS  =-1;
			if (duration.contains( "Z" )){
				posS = duration.indexOf( "Z" );	
			}
			if (duration.contains( "+" )){
				posS = duration.indexOf( "+" );
			}
			if (duration.contains( "-" )){
				posS = duration.indexOf( "-" );
			}

			if (posS!=-1){
				seconds = Integer.parseInt( duration.substring( 0, posS ) );
				duration = duration.substring( posS+1, duration.length( ) );
				timeZone = duration;
			} else {
				seconds = Integer.parseInt( duration);
			}
		


		
	}
	
	protected String paramToString(int param){
		String paramString =super.paramToString(param);
		if (paramString==null){
		switch (param){
			case MILISECONDS: paramString = TextConstants.getText( "LOM.Duration.Years" );break;
			case TIMEZONE: paramString = TextConstants.getText( "LOM.Duration.Months" );break;
		}
		}
		return paramString;
		
	}
	
	protected boolean setParameter(int param, String value){
		
	
		boolean set = super.setParameter(param,value);
		try{
		if (!set){
		int intValue=-1;
		
			if (value==null || value.equals( "" ) || Integer.parseInt( value )>0){
				if (value==null || value.equals( "" )){
					intValue=0; 	
				}else{
					intValue = Integer.parseInt( value );
				}
				switch (param){
					case MILISECONDS: milisec = intValue;break;
					case TIMEZONE: timeZone = value;break;
				}

				set = true;
			}
		}
		} catch (Exception e){}
		
		// Display error message
		if (!set){
			Controller.getInstance().showErrorDialog( TextConstants.getText("LOM.Duration.InvalidValue.Title"), TextConstants.getText( "LOM.Duration.InvalidValue.Message", paramToString(param) ) );
		}
		
		return set;
		
	}
	
	public boolean setMiliSeconds(String s){
		return setParameter(MILISECONDS, s);
	}

	public boolean setTimeZone(String s){
		return setParameter(TIMEZONE, s);
	}

	/**
	 * @return the milisec
	 */
	public int getMilisec() {
		return milisec;
	}

	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}
	
	
}