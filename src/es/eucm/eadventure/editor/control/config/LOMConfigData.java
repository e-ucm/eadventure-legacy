/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.control.config;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMEducationalDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMGeneralDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMLifeCycleDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMTechnicalDataControl;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMConfigData implements ProjectConfigDataConsumer {

    public void updateData( ) {

    }

    private static Controller controller = Controller.getInstance( );

    public static void storeData( String group, String field, String value ) {

        ProjectConfigData.setProperty( getKey( group, field ), value );
    }

    private static String getKey( String groupName, String fieldName ) {

        int chapter = controller.getSelectedChapter( );
        return "Chapter" + chapter + "." + groupName + "." + fieldName;
    }

    public static boolean isStored( String group, String field ) {

        return ProjectConfigData.existsKey( getKey( group, field ) );

    }

    /**
     * Make the correct changes in LOMData
     */
    public static void loadData( ) {

        // general
        if( LOMConfigData.isStored( LOMGeneralDataControl.GROUP, "title" ) ) {
            controller.getLOMDataControl( ).getGeneral( ).getData( ).setTitle( new LangString( getProperty( LOMGeneralDataControl.GROUP, "title" ) ) );
        }
        if( LOMConfigData.isStored( LOMGeneralDataControl.GROUP, "language" ) ) {
            controller.getLOMDataControl( ).getGeneral( ).getData( ).setLanguage( getProperty( LOMGeneralDataControl.GROUP, "language" ) );
            controller.updateLOMLanguage( );
        }
        if( LOMConfigData.isStored( LOMGeneralDataControl.GROUP, "description" ) ) {
            controller.getLOMDataControl( ).getGeneral( ).getData( ).setDescription( new LangString( getProperty( LOMGeneralDataControl.GROUP, "description" ) ) );
        }
        if( LOMConfigData.isStored( LOMGeneralDataControl.GROUP, "keyword" ) ) {
            controller.getLOMDataControl( ).getGeneral( ).getData( ).setKeyword( new LangString( getProperty( LOMGeneralDataControl.GROUP, "keyword" ) ) );
        }

        // life cycle
        if( LOMConfigData.isStored( LOMLifeCycleDataControl.GROUP, "version" ) ) {
            controller.getLOMDataControl( ).getLifeCycle( ).getData( ).setVersion( new LangString( getProperty( LOMLifeCycleDataControl.GROUP, "version" ) ) );
        }

        // technical
        if( LOMConfigData.isStored( LOMTechnicalDataControl.GROUP, "minimumVersion" ) ) {
            controller.getLOMDataControl( ).getTechnical( ).getData( ).setMinimumVersion( getProperty( LOMTechnicalDataControl.GROUP, "minimumVersion" ) );
        }
        if( LOMConfigData.isStored( LOMTechnicalDataControl.GROUP, "maximumVersion" ) ) {
            controller.getLOMDataControl( ).getTechnical( ).getData( ).setMinimumVersion( getProperty( LOMTechnicalDataControl.GROUP, "maximumVersion" ) );
        }

        // educational
        if( isStored( LOMEducationalDataControl.GROUP, "intendedEndUserRole" ) ) {
            controller.getLOMDataControl( ).getEducational( ).getData( ).setIntendedEndUserRole( Integer.parseInt( getProperty( LOMEducationalDataControl.GROUP, "intendedEndUserRole" ) ) );
        }
        if( isStored( LOMEducationalDataControl.GROUP, "semanticDensity" ) ) {
            controller.getLOMDataControl( ).getEducational( ).getData( ).setSemanticDensity( Integer.parseInt( getProperty( LOMEducationalDataControl.GROUP, "semanticDensity" ) ) );
        }
        if( isStored( LOMEducationalDataControl.GROUP, "learningResourcesType" ) ) {
            controller.getLOMDataControl( ).getEducational( ).getData( ).setLearningResourceType( Integer.parseInt( getProperty( LOMEducationalDataControl.GROUP, "learningResourcesType" ) ) );

        }
        if( isStored( LOMEducationalDataControl.GROUP, "context" ) ) {
            controller.getLOMDataControl( ).getEducational( ).getData( ).setContext( Integer.parseInt( getProperty( LOMEducationalDataControl.GROUP, "context" ) ) );
        }
        if( isStored( LOMEducationalDataControl.GROUP, "difficulty" ) ) {
            controller.getLOMDataControl( ).getEducational( ).getData( ).setDifficulty( Integer.parseInt( getProperty( LOMEducationalDataControl.GROUP, "difficulty" ) ) );
        }
        if( isStored( LOMEducationalDataControl.GROUP, "interactivityLevel" ) ) {
            controller.getLOMDataControl( ).getEducational( ).getData( ).setInteractivityLevel( Integer.parseInt( getProperty( LOMEducationalDataControl.GROUP, "interactivityLevel" ) ) );
        }
        if( isStored( LOMEducationalDataControl.GROUP, "interactivityType" ) ) {
            controller.getLOMDataControl( ).getEducational( ).getData( ).setInteractivityType( Integer.parseInt( getProperty( LOMEducationalDataControl.GROUP, "interactivityType" ) ) );
        }

        if( isStored( LOMEducationalDataControl.GROUP, "description" ) ) {
            controller.getLOMDataControl( ).getEducational( ).getData( ).setDescription( new LangString( getProperty( LOMEducationalDataControl.GROUP, "description" ) ) );
        }
        if( isStored( LOMEducationalDataControl.GROUP, "typicalAgeRange" ) ) {
            controller.getLOMDataControl( ).getEducational( ).getData( ).setTypicalAgeRange( new LangString( getProperty( LOMEducationalDataControl.GROUP, "typicalAgeRange" ) ) );
        }
        if( isStored( LOMEducationalDataControl.GROUP, "typicalLearningTime" ) ) {
            controller.getLOMDataControl( ).getEducational( ).getData( ).setTypicalLearningTime( getProperty( LOMEducationalDataControl.GROUP, "typicalLearningTime" ) );

        }
    }

    public static String getProperty( String group, String field ) {

        return ProjectConfigData.getProperty( getKey( group, field ) );
    }

}
