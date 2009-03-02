package es.eucm.eadventure.editor.control.tools.assessment;

import es.eucm.eadventure.common.data.assessment.AssessmentProfile;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeReportSettingsTool extends Tool{
	
	public static final int SHOW_REPORT = 1;
	public static final int SEND = 2;
	public static final int EMAIL = 3;
	public static final int SMTP_SERVER = 4;
	public static final int SMTP_SSL = 5;
	public static final int SMTP_PORT = 6;
	public static final int SMTP_USER = 7;
	public static final int SMTP_PWD = 8;
	
	private AssessmentProfile profile;
	
	private int mode;
	
	private String oldValue;
	
	private String newValue;
	
	public ChangeReportSettingsTool ( AssessmentProfile profile, boolean newValue, int mode ){
		this.profile = profile;
		this.newValue = Boolean.toString(newValue);
		this.mode = mode;
	}
	
	public ChangeReportSettingsTool ( AssessmentProfile profile, String newValue, int mode ){
		this.profile = profile;
		this.newValue = newValue;
		this.mode = mode;
	}

	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		boolean changed = true;
		ChangeReportSettingsTool o = null;
		if (other instanceof ChangeReportSettingsTool){
			 o =(ChangeReportSettingsTool)other;
			 if (o.profile!=profile)
				 return false;
		} else {
			return false;
		}
		if (mode!=o.mode)
			return false;
		
		switch(mode){
			case SMTP_SERVER:
			case SMTP_PWD:
			case SMTP_USER:
			case SMTP_PORT:
			case EMAIL: 
				newValue = o.newValue;
				break;
			default: changed = false;break;
		}
		return changed;
	}

	@Override
	public boolean doTool() {
		boolean changed = true;
		switch(mode){
			case SHOW_REPORT: 
				oldValue = Boolean.toString(profile.isShowReportAtEnd());
				if (oldValue==null && newValue ==null ||oldValue.equals(newValue)){
					changed = false; break;
				}
				profile.setShowReportAtEnd(Boolean.parseBoolean(newValue));
				break;
			case SEND: 
				oldValue = Boolean.toString(profile.isSendByEmail());
				if (oldValue==null && newValue ==null ||oldValue.equals(newValue)){
					changed = false; break;
				}
				
				profile.setSendByEmail(Boolean.parseBoolean(newValue));
				break;
			case EMAIL: 
				oldValue = profile.getEmail();
				if (oldValue==null && newValue ==null ||oldValue.equals(newValue)){
					changed = false; break;
				}

				profile.setEmail(newValue);
				break;
			case SMTP_SERVER: 
				oldValue = profile.getSmtpServer();
				if (oldValue==null && newValue ==null ||oldValue.equals(newValue)){
					changed = false; break;
				}

				profile.setSmtpServer(newValue);
				break;
			case SMTP_SSL: 
				oldValue = Boolean.toString(profile.isSmtpSSL());
				if (oldValue==null && newValue ==null ||oldValue.equals(newValue)){
					changed = false; break;
				}

				profile.setSmtpSSL(Boolean.parseBoolean(newValue));
				break;
			case SMTP_PORT:
				oldValue = profile.getSmtpPort();
				if (oldValue==null && newValue ==null ||oldValue.equals(newValue)){
					changed = false; break;
				}

				profile.setSmtpPort(newValue);
				break;
			case SMTP_USER: 
				oldValue = profile.getSmtpUser();
				if (oldValue==null && newValue ==null ||oldValue.equals(newValue)){
					changed = false; break;
				}

				profile.setSmtpUser(newValue);
				break;
			case SMTP_PWD:
				oldValue = profile.getSmtpPwd();
				if (oldValue==null && newValue ==null ||oldValue.equals(newValue)){
					changed = false; break;
				}

				profile.setSmtpPwd(newValue); 
				break;
			default: changed = false;break;
		}
		return changed;
	}

	@Override
	public boolean redoTool() {
		boolean changed = true;
		switch(mode){
			case SHOW_REPORT: 
				profile.setShowReportAtEnd(Boolean.parseBoolean(newValue));
				break;
			case SEND: 
				profile.setSendByEmail(Boolean.parseBoolean(newValue));
				break;
			case EMAIL: 
				profile.setEmail(newValue);
				break;
			case SMTP_SERVER: 
				profile.setSmtpServer(newValue);
				break;
			case SMTP_SSL: 
				profile.setSmtpSSL(Boolean.parseBoolean(newValue));
				break;
			case SMTP_PORT:
				profile.setSmtpPort(newValue);
				break;
			case SMTP_USER: 
				profile.setSmtpUser(newValue);
				break;
			case SMTP_PWD:
				profile.setSmtpPwd(newValue); 
				break;
			default: changed = false;break;
		}
		
		if (changed)
			Controller.getInstance().updatePanel();
		
		return changed;
	}

	@Override
	public boolean undoTool() {
		boolean changed = true;
		switch(mode){
			case SHOW_REPORT: 
				profile.setShowReportAtEnd(Boolean.parseBoolean(oldValue));
				break;
			case SEND: 
				profile.setSendByEmail(Boolean.parseBoolean(oldValue));
				break;
			case EMAIL: 
				profile.setEmail(oldValue);
				break;
			case SMTP_SERVER: 
				profile.setSmtpServer(oldValue);
				break;
			case SMTP_SSL: 
				profile.setSmtpSSL(Boolean.parseBoolean(oldValue));
				break;
			case SMTP_PORT:
				profile.setSmtpPort(oldValue);
				break;
			case SMTP_USER: 
				profile.setSmtpUser(oldValue);
				break;
			case SMTP_PWD:
				profile.setSmtpPwd(oldValue); 
				break;
			default: changed = false;break;

		}
		
		if (changed)
			Controller.getInstance().updatePanel();
		
		return changed;
	}
}
