package es.eucm.eadventure.editor.data.lomdata;


import es.eucm.eadventure.editor.control.controllers.lom.LOMTextDataControl;

public class LOMTechnical {

	//4.4.1.3
	private String minimumVersion;
	
	//4.4.1.4
	private String maximumVersion;
	
	//4..1
	
	public LOMTechnical (){
		minimumVersion = null;
		maximumVersion = null;
	}

	/**
	 * @return the minimumVersion
	 */
	public String getMinimumVersion( ) {
		return minimumVersion;
	}

	/**
	 * @param minimumVersion the minimumVersion to set
	 */
	public void setMinimumVersion( String minimumVersion ) {
		this.minimumVersion = minimumVersion;
	}

	/**
	 * @return the maximumVersion
	 */
	public String getMaximumVersion( ) {
		return maximumVersion;
	}

	/**
	 * @param maximumVersion the maximumVersion to set
	 */
	public void setMaximumVersion( String maximumVersion ) {
		this.maximumVersion = maximumVersion;
	}
	
	
	
}
