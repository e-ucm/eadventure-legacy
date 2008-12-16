package es.eucm.eadventure.common.data.adaptation;

/**
 * This class is used for adaptation sent by LMS, using SCORM data model
 *
 */
public class AdaptationObjetive extends AdaptedState{

	private String objetiveId;
	
	public AdaptationObjetive (){
		super();
		objetiveId = new String("");
	}
	
	public AdaptationObjetive (String id){
		super();
		objetiveId = new String(id);
	}

	public String getObjetiveId() {
		return objetiveId;
	}

	public void setObjetiveId(String objetiveId) {
		this.objetiveId = objetiveId;
	}
	
	
}
