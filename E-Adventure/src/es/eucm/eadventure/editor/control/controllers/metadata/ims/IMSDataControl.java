/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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

    public static final String DEFAULT_LANGUAGE = "en";

    private IMSGeneralDataControl general;

    private IMSLifeCycleDataControl lifeCycle;

    private IMSTechnicalDataControl technical;

    private IMSEducationalDataControl educational;

    private IMSMetaMetaDataControl metametadata;

    private IMSRightsDataControl rights;

    private IMSClassificationDataControl classification;

    public IMSDataControl( ) {

        IMSGeneral generalData = new IMSGeneral( );
        generalData.setTitle( new LangString( "Default" ) );
        generalData.setCalaog( new String( "Default" ) );
        generalData.addEntry( new LangString( "Default" ) );
        generalData.setDescription( new LangString( "Default" ) );
        generalData.setKeyword( new LangString( "Default" ) );
        generalData.setLanguage( DEFAULT_LANGUAGE );
        general = new IMSGeneralDataControl( generalData );

        IMSLifeCycle lifeCycle = new IMSLifeCycle( );
        lifeCycle.setVersion( new LangString( "Default" ) );
        lifeCycle.addStatus( 0 );
        this.lifeCycle = new IMSLifeCycleDataControl( lifeCycle );

        IMSTechnical tech = new IMSTechnical( );
        tech.setFormat( new String( "" ) );
        tech.setLocation( new String( "" ), true );
        tech.setMinimumVersion( Controller.getInstance( ).getEditorMinVersion( ) );
        tech.setMaximumVersion( Controller.getInstance( ).getEditorVersion( ) );
        this.technical = new IMSTechnicalDataControl( tech );

        IMSEducational educ = new IMSEducational( );
        educ.setTypicalLearningTime( new String( "" ) );
        educ.setLanguage( DEFAULT_LANGUAGE );
        educ.setDescription( new LangString( "Default" ) );
        educ.setTypicalAgeRange( new LangString( "Default" ) );
        educ.setContext( 0 );
        educ.setIntendedEndUserRole( 0 );
        educ.setLearningResourceType( 0 );

        educational = new IMSEducationalDataControl( educ );

        IMSMetaMetaData metametad = new IMSMetaMetaData( );
        metametad.setMetadatascheme( new String( "" ) );
        metametadata = new IMSMetaMetaDataControl( metametad );

        IMSRights right = new IMSRights( );
        right.setCopyrightandotherrestrictions( 0 );
        right.setCost( 0 );
        rights = new IMSRightsDataControl( right );

        IMSClassification classif = new IMSClassification( );
        classif.setDescription( new LangString( "Default" ) );
        classif.setKeyword( new LangString( "Default" ) );
        classif.setPurpose( 0 );
        classification = new IMSClassificationDataControl( classif );

    }

    public void updateLanguage( ) {

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
     * @param general
     *            the general to set
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
     * @param lifeCycle
     *            the lifeCycle to set
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
     * @param technical
     *            the technical to set
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
     * @param educational
     *            the educational to set
     */
    public void setEducational( IMSEducationalDataControl educational ) {

        this.educational = educational;
    }

    /**
     * 
     * @return the meta meta data
     */
    public IMSMetaMetaDataControl getMetametadata( ) {

        return metametadata;
    }

    /**
     * 
     * @param metametadata
     *            to be set
     */
    public void setMetametadata( IMSMetaMetaDataControl metametadata ) {

        this.metametadata = metametadata;
    }

    /**
     * 
     * @return
     */
    public IMSRightsDataControl getRights( ) {

        return rights;
    }

    /**
     * 
     * @param rights
     */
    public void setRights( IMSRightsDataControl rights ) {

        this.rights = rights;
    }

    /**
     * 
     * @return
     */
    public IMSClassificationDataControl getClassification( ) {

        return classification;
    }

    /**
     * 
     * @param classification
     */
    public void setClassification( IMSClassificationDataControl classification ) {

        this.classification = classification;
    }
}
