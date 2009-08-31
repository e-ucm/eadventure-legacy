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
package es.eucm.eadventure.editor.control.controllers.metadata.lom;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.lom.LOMEducational;
import es.eucm.eadventure.editor.data.meta.lom.LOMGeneral;
import es.eucm.eadventure.editor.data.meta.lom.LOMLifeCycle;
import es.eucm.eadventure.editor.data.meta.lom.LOMTechnical;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMDataControl {

    public static final String DEFAULT_LANGUAGE = "en";

    private LOMGeneralDataControl general;

    private LOMLifeCycleDataControl lifeCycle;

    private LOMTechnicalDataControl technical;

    private LOMEducationalDataControl educational;

    public LOMDataControl( ) {

        LOMGeneral generalData = new LOMGeneral( );
        generalData.setTitle( new LangString( "" ) );
        generalData.setDescription( new LangString( "" ) );
        generalData.setKeyword( new LangString( "" ) );
        generalData.setLanguage( DEFAULT_LANGUAGE );
        general = new LOMGeneralDataControl( generalData );

        LOMLifeCycle lifeCycle = new LOMLifeCycle( );
        lifeCycle.setVersion( new LangString( "" ) );
        this.lifeCycle = new LOMLifeCycleDataControl( lifeCycle );

        LOMTechnical tech = new LOMTechnical( );
        tech.setMinimumVersion( Controller.getInstance( ).getEditorMinVersion( ) );
        tech.setMaximumVersion( Controller.getInstance( ).getEditorVersion( ) );
        this.technical = new LOMTechnicalDataControl( tech );

        LOMEducational educ = new LOMEducational( );
        educ.setTypicalLearningTime( "" );
        educ.setLanguage( DEFAULT_LANGUAGE );
        educ.setDescription( new LangString( "" ) );
        educ.setTypicalAgeRange( new LangString( "" ) );
        educ.setContext( 0 );
        educ.setIntendedEndUserRole( 0 );
        educ.setLearningResourceType( 0 );

        educational = new LOMEducationalDataControl( educ );

    }

    public void updateLanguage( ) {

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
     * @param general
     *            the general to set
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
     * @param lifeCycle
     *            the lifeCycle to set
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
     * @param technical
     *            the technical to set
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
     * @param educational
     *            the educational to set
     */
    public void setEducational( LOMEducationalDataControl educational ) {

        this.educational = educational;
    }
}
