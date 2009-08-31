/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.control.controllers.metadata.ims;


import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.ims.IMSClassification;
import es.eucm.eadventure.editor.data.meta.ims.IMSEducational;
import es.eucm.eadventure.editor.data.meta.ims.IMSGeneral;
import es.eucm.eadventure.editor.data.meta.ims.IMSLifeCycle;
import es.eucm.eadventure.editor.data.meta.ims.IMSMetaMetaData;
import es.eucm.eadventure.editor.data.meta.ims.IMSRights;
import es.eucm.eadventure.editor.data.meta.ims.IMSTechnical;
import es.eucm.eadventure.editor.data.meta.LangString;

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
