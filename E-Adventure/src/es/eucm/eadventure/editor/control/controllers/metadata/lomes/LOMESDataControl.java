/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.controllers.metadata.lomes;

import es.eucm.eadventure.editor.data.meta.auxiliar.LOMContribute;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMRequirement;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMTaxon;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMTaxonPath;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESClassification;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESEducational;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESGeneral;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESLifeCycle;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESMetaMetaData;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESRights;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESTechnical;
import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMESDataControl {

    public static final String DEFAULT_LANGUAGE = "en";

    private LOMESGeneralDataControl general;

    private LOMESLifeCycleDataControl lifeCycle;

    private LOMESTechnicalDataControl technical;

    private LOMESEducationalDataControl educational;

    private LOMESMetaMetaDataControl metametadata;

    private LOMESRightsDataControl rights;

    private LOMESClassificationDataControl classification;

    public LOMESDataControl( ) {

        LOMESGeneral generalData = new LOMESGeneral( );
        generalData.setTitle( new LangString( "Default" ) );
        //generalData.addIdentifier(LOMIdentifier.CATALOG_DEFAULT,LOMIdentifier.ENTRY_DEFAULT);
        generalData.setDescription( new LangString( "Default" ) );
        generalData.setKeyword( new LangString( "Default" ) );
        generalData.setLanguage( DEFAULT_LANGUAGE );
        generalData.setAggregationLevel( 0 );
        general = new LOMESGeneralDataControl( generalData );

        LOMESLifeCycle lifeCycle = new LOMESLifeCycle( );
        lifeCycle.setVersion( new LangString( LOMESLifeCycle.VERSION_DEFAULT ) );
        lifeCycle.addStatus( 1 );
        lifeCycle.setContribute( new LOMContribute( Vocabulary.LC_CONTRIBUTION_TYPE_2_3_1 ) );
        this.lifeCycle = new LOMESLifeCycleDataControl( lifeCycle );

        LOMESTechnical tech = new LOMESTechnical( );
        tech.setRequirement( new LOMRequirement( ) );
        this.technical = new LOMESTechnicalDataControl( tech );

        LOMESEducational educ = new LOMESEducational( );
        educ.setTypicalLearningTime( new String( "" ) );
        educ.setLanguage( DEFAULT_LANGUAGE );
        educ.setDescription( new LangString( "Default" ) );
        educ.setTypicalAgeRange( new LangString( "Default" ) );
        educ.setContext( 0 );
        educ.setIntendedEndUserRole( 0 );
        educ.setLearningResourceType( 0 );
        educ.setCognitiveProcess( 0 );
        educ.setTypicalLearningTime( "PT1H30M" );
        educational = new LOMESEducationalDataControl( educ );

        LOMESMetaMetaData metametad = new LOMESMetaMetaData( );
        //metametad.addIdentifier(LOMIdentifier.CATALOG_DEFAULT,LOMIdentifier.ENTRY_DEFAULT+"-meta");
        metametad.setContribute( new LOMContribute( Vocabulary.MD_CONTRIBUTION_TYPE_2_3_1 ) );
        metametad.setDescription( "Empty" );
        metametad.setLanguage( "en" );
        metametad.setMetadatascheme( new String( "LOM-ES V1.0" ) );
        metametadata = new LOMESMetaMetaDataControl( metametad );

        LOMESRights right = new LOMESRights( );
        right.setCopyrightandotherrestrictions( 0 );
        right.setCost( 1 );
        //right.setDescription(new LangString("Empty"));
        right.setAccessType( 0 );
        right.setAccessDescription( new LangString( "None" ) );

        rights = new LOMESRightsDataControl( right );

        LOMESClassification classif = new LOMESClassification( );
        classif.setDescription( new LangString( "Default" ) );
        classif.setKeyword( new LangString( "Default" ) );
        classif.setPurpose( 0 );
        classif.setTaxonPath( new LOMTaxonPath( new LangString( "Default" ), new LOMTaxon( new String( "Default" ), new LangString( "Default" ) ) ) );
        classification = new LOMESClassificationDataControl( classif );

    }

    public void updateLanguage( ) {

        String language = general.getData( ).getLanguage( );
        LangString.updateLanguage( language );
    }

    /**
     * @return the general
     */
    public LOMESGeneralDataControl getGeneral( ) {

        return general;
    }

    /**
     * @param general
     *            the general to set
     */
    public void setGeneral( LOMESGeneralDataControl general ) {

        this.general = general;
    }

    /**
     * @return the lifeCycle
     */
    public LOMESLifeCycleDataControl getLifeCycle( ) {

        return lifeCycle;
    }

    /**
     * @param lifeCycle
     *            the lifeCycle to set
     */
    public void setLifeCycle( LOMESLifeCycleDataControl lifeCycle ) {

        this.lifeCycle = lifeCycle;
    }

    /**
     * @return the technical
     */
    public LOMESTechnicalDataControl getTechnical( ) {

        return technical;
    }

    /**
     * @param technical
     *            the technical to set
     */
    public void setTechnical( LOMESTechnicalDataControl technical ) {

        this.technical = technical;
    }

    /**
     * @return the educational
     */
    public LOMESEducationalDataControl getEducational( ) {

        return educational;
    }

    /**
     * @param educational
     *            the educational to set
     */
    public void setEducational( LOMESEducationalDataControl educational ) {

        this.educational = educational;
    }

    /**
     * 
     * @return the meta meta data
     */
    public LOMESMetaMetaDataControl getMetametadata( ) {

        return metametadata;
    }

    /**
     * 
     * @param metametadata
     *            to be set
     */
    public void setMetametadata( LOMESMetaMetaDataControl metametadata ) {

        this.metametadata = metametadata;
    }

    /**
     * 
     * @return
     */
    public LOMESRightsDataControl getRights( ) {

        return rights;
    }

    /**
     * 
     * @param rights
     */
    public void setRights( LOMESRightsDataControl rights ) {

        this.rights = rights;
    }

    /**
     * 
     * @return
     */
    public LOMESClassificationDataControl getClassification( ) {

        return classification;
    }

    /**
     * 
     * @param classification
     */
    public void setClassification( LOMESClassificationDataControl classification ) {

        this.classification = classification;
    }
}
