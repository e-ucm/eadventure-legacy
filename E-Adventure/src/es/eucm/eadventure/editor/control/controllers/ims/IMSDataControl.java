package es.eucm.eadventure.editor.control.controllers.ims;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.ims.IMSClassification;
import es.eucm.eadventure.editor.data.ims.IMSEducational;
import es.eucm.eadventure.editor.data.ims.IMSGeneral;
import es.eucm.eadventure.editor.data.ims.IMSLifeCycle;
import es.eucm.eadventure.editor.data.ims.IMSMetaMetaData;
import es.eucm.eadventure.editor.data.ims.IMSRights;
import es.eucm.eadventure.editor.data.ims.IMSTechnical;
import es.eucm.eadventure.editor.data.lom.LangString;

public class IMSDataControl {

	public static final String DEFAULT_LANGUAGE="en";
	
	private IMSGeneralDataControl general;
	
	private IMSLifeCycleDataControl lifeCycle;
	
	private IMSTechnicalDataControl technical;
	
	private IMSEducationalDataControl educational;
	
	private IMSMetaMetaDataControl metametadata;
	
	private IMSRightsDataControl rights;
	
	private IMSClassificationDataControl classification;
	
	public IMSDataControl () {
		IMSGeneral generalData = new IMSGeneral();
		generalData.setTitle( new LangString("Default") );
		generalData.setCalaog(new String("Default"));
		generalData.addEntry(new LangString("Default"));
		generalData.setDescription( new LangString("Default") );
		generalData.setKeyword( new LangString("Default") );
		generalData.setLanguage( DEFAULT_LANGUAGE );
		general = new IMSGeneralDataControl(generalData);
		
		IMSLifeCycle lifeCycle = new IMSLifeCycle();
		lifeCycle.setVersion( new LangString("Default") );
		lifeCycle.addStatus(0);
		this.lifeCycle = new IMSLifeCycleDataControl(lifeCycle);
		
		IMSTechnical tech = new IMSTechnical();
		tech.setFormat(new String(""));
		tech.setLocation(new String(""), true);
		tech.setMinimumVersion( Controller.getInstance().getEditorMinVersion() );
		tech.setMaximumVersion( Controller.getInstance().getEditorVersion() );
		this.technical = new IMSTechnicalDataControl(tech);
		
		IMSEducational educ = new IMSEducational();
		educ.setTypicalLearningTime( new String("") );
		educ.setLanguage( DEFAULT_LANGUAGE );
		educ.setDescription( new LangString("Default") );
		educ.setTypicalAgeRange( new LangString("Default") );
		educ.setContext( 0 );
		educ.setIntendedEndUserRole( 0 );
		educ.setLearningResourceType( 0 );
		
		educational = new IMSEducationalDataControl(educ);
		
		IMSMetaMetaData metametad = new IMSMetaMetaData();
		metametad.setMetadatascheme(new String(""));
		metametadata = new IMSMetaMetaDataControl(metametad);
		
		IMSRights right = new IMSRights();
		right.setCopyrightandotherrestrictions(0);
		right.setCost(0);
		rights = new IMSRightsDataControl(right);
		
		IMSClassification classif = new IMSClassification();
		classif.setDescription(new LangString("Default"));
		classif.setKeyword(new LangString("Default"));
		classif.setPurpose(0);
		classification = new IMSClassificationDataControl(classif);
		
		
	}

	public void updateLanguage(){
		String language = general.getData( ).getLanguage( );
		LangString.updateLanguage( language );
	}
	
	/**
	 * @return the general
	 */
	public IMSGeneralDataControl getGeneral( ) {
		return general;
	}

	/**
	 * @param general the general to set
	 */
	public void setGeneral( IMSGeneralDataControl general ) {
		this.general = general;
	}

	/**
	 * @return the lifeCycle
	 */
	public IMSLifeCycleDataControl getLifeCycle( ) {
		return lifeCycle;
	}

	/**
	 * @param lifeCycle the lifeCycle to set
	 */
	public void setLifeCycle( IMSLifeCycleDataControl lifeCycle ) {
		this.lifeCycle = lifeCycle;
	}

	/**
	 * @return the technical
	 */
	public IMSTechnicalDataControl getTechnical( ) {
		return technical;
	}

	/**
	 * @param technical the technical to set
	 */
	public void setTechnical( IMSTechnicalDataControl technical ) {
		this.technical = technical;
	}

	/**
	 * @return the educational
	 */
	public IMSEducationalDataControl getEducational( ) {
		return educational;
	}

	/**
	 * @param educational the educational to set
	 */
	public void setEducational( IMSEducationalDataControl educational ) {
		this.educational = educational;
	}

	/**
	 * 
	 * @return the meta meta data
	 */
	public IMSMetaMetaDataControl getMetametadata() {
		return metametadata;
	}

	/**
	 * 
	 * @param metametadata to be set
	 */
	public void setMetametadata(IMSMetaMetaDataControl metametadata) {
		this.metametadata = metametadata;
	}

	/**
	 * 
	 * @return
	 */
	public IMSRightsDataControl getRights() {
		return rights;
	}

	/**
	 * 
	 * @param rights
	 */
	public void setRights(IMSRightsDataControl rights) {
		this.rights = rights;
	}

	/**
	 * 
	 * @return
	 */
	public IMSClassificationDataControl getClassification() {
		return classification;
	}

	/**
	 * 
	 * @param classification
	 */
	public void setClassification(IMSClassificationDataControl classification) {
		this.classification = classification;
	}
}
