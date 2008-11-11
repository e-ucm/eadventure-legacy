package es.eucm.eadventure.editor.control.controllers.lom;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.lomdata.LOMEducational;
import es.eucm.eadventure.editor.data.lomdata.LOMGeneral;
import es.eucm.eadventure.editor.data.lomdata.LOMLifeCycle;
import es.eucm.eadventure.editor.data.lomdata.LOMTechnical;
import es.eucm.eadventure.editor.data.lomdata.LangString;

public class LOMDataControl {

	public static final String DEFAULT_LANGUAGE="en";
	
	private LOMGeneralDataControl general;
	
	private LOMLifeCycleDataControl lifeCycle;
	
	private LOMTechnicalDataControl technical;
	
	private LOMEducationalDataControl educational;
	
	public LOMDataControl () {
		LOMGeneral generalData = new LOMGeneral();
		generalData.setTitle( new LangString("") );
		generalData.setDescription( new LangString("") );
		generalData.setKeyword( new LangString("") );
		generalData.setLanguage( DEFAULT_LANGUAGE );
		general = new LOMGeneralDataControl(generalData);
		
		LOMLifeCycle lifeCycle = new LOMLifeCycle();
		lifeCycle.setVersion( new LangString("") );
		this.lifeCycle = new LOMLifeCycleDataControl(lifeCycle);
		
		LOMTechnical tech = new LOMTechnical();
		tech.setMinimumVersion( Controller.getInstance().getEditorMinVersion() );
		tech.setMaximumVersion( Controller.getInstance().getEditorVersion() );
		this.technical = new LOMTechnicalDataControl(tech);
		
		LOMEducational educ = new LOMEducational();
		educ.setTypicalLearningTime( "" );
		educ.setLanguage( DEFAULT_LANGUAGE );
		educ.setDescription( new LangString("") );
		educ.setTypicalAgeRange( new LangString("") );
		educ.setContext( 0 );
		educ.setIntendedEndUserRole( 0 );
		educ.setLearningResourceType( 0 );
		
		educational = new LOMEducationalDataControl(educ);
		
	}

	public void updateLanguage(){
		String language = general.getData( ).getLanguage( );
		LangString.updateLanguage( language );
	}
	
	/**
	 * @return the general
	 */
	public LOMGeneralDataControl getGeneral( ) {
		return general;
	}

	/**
	 * @param general the general to set
	 */
	public void setGeneral( LOMGeneralDataControl general ) {
		this.general = general;
	}

	/**
	 * @return the lifeCycle
	 */
	public LOMLifeCycleDataControl getLifeCycle( ) {
		return lifeCycle;
	}

	/**
	 * @param lifeCycle the lifeCycle to set
	 */
	public void setLifeCycle( LOMLifeCycleDataControl lifeCycle ) {
		this.lifeCycle = lifeCycle;
	}

	/**
	 * @return the technical
	 */
	public LOMTechnicalDataControl getTechnical( ) {
		return technical;
	}

	/**
	 * @param technical the technical to set
	 */
	public void setTechnical( LOMTechnicalDataControl technical ) {
		this.technical = technical;
	}

	/**
	 * @return the educational
	 */
	public LOMEducationalDataControl getEducational( ) {
		return educational;
	}

	/**
	 * @param educational the educational to set
	 */
	public void setEducational( LOMEducationalDataControl educational ) {
		this.educational = educational;
	}
}
