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
package es.eucm.eadventure.editor.data.meta.lomes;

import java.util.ArrayList;

import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMESEducational {

    //5.1 Vocabulary: active, explosive, mixed
    private Vocabulary interactivityType;

    //5.2 Vocabulary: exercise, simulation, questionnaire, diagram, figure, graph, index, slide, table, 
    //narrative text, exam, experiment, problem statement, self assessment, lecture
    private ArrayList<Vocabulary> learningResourceType;

    //5.3 Vocabulary: active, explosive, mixed
    private Vocabulary interactivityLevel;

    //5.4
    private Vocabulary semanticDensity;

    //5.5
    private ArrayList<Vocabulary> intendedEndUserRole;

    //5.6
    private ArrayList<Vocabulary> context;

    //5.7
    private ArrayList<LangString> typicalAgeRange;

    //5.8
    private Vocabulary difficulty;

    //5.9 DURATION
    private String typicalLearningTime;

    //5.10
    private ArrayList<LangString> description;

    //5.11 : NOT SUPPORTED BY EDITOR
    private ArrayList<String> language;

    //5.12
    private ArrayList<Vocabulary> cognitiveProcess;

    public LOMESEducational( ) {

        this.typicalLearningTime = null;

        this.language = new ArrayList<String>( );

        this.context = new ArrayList<Vocabulary>( );
        this.intendedEndUserRole = new ArrayList<Vocabulary>( );
        this.learningResourceType = new ArrayList<Vocabulary>( );
        this.cognitiveProcess = new ArrayList<Vocabulary>( );

        this.description = new ArrayList<LangString>( );
        this.typicalAgeRange = new ArrayList<LangString>( );

        this.difficulty = new Vocabulary( Vocabulary.ED_DIFFICULTY_5_8, Vocabulary.LOM_ES_SOURCE, 0 );
        this.interactivityLevel = new Vocabulary( Vocabulary.ED_INTERACTIVITY_LEVEL_5_3, Vocabulary.LOM_ES_SOURCE, 0 );
        this.interactivityType = new Vocabulary( Vocabulary.ED_INTERACTIVITY_TYPE_5_1, Vocabulary.LOM_ES_SOURCE, 0 );
        this.semanticDensity = new Vocabulary( Vocabulary.ED_SEMANTIC_DENSITY_5_4, Vocabulary.LOM_ES_SOURCE, 0 );

    }

    /** *********************************ADD METHODS ************************* */

    public void addLanguage( String lang ) {

        language.add( lang );
    }

    public void addContext( int index ) {

        this.context.add( new Vocabulary( Vocabulary.LOMES_ED_CONTEXT_5_6, Vocabulary.LOM_ES_SOURCE, index ) );
    }

    public void addIntendedEndUserRole( int index ) {

        this.intendedEndUserRole.add( new Vocabulary( Vocabulary.LOMES_ED_INTENDED_END_USER_ROLE_5_5, Vocabulary.LOM_ES_SOURCE, index ) );
    }

    public void addLearningResourceType( int index ) {

        this.learningResourceType.add( new Vocabulary( Vocabulary.LOMES_ED_LEARNING_RESOURCE_TYPE_5_2, Vocabulary.LOM_ES_SOURCE, index ) );
    }

    public void addDescription( LangString description ) {

        this.description.add( description );
    }

    public void addTypicalAgeRange( LangString typicalAgeRange ) {

        this.typicalAgeRange.add( typicalAgeRange );
    }

    public void addCognitiveProcess( int index ) {

        this.cognitiveProcess.add( new Vocabulary( Vocabulary.LOMES_ED_COGNITIVE_PROCESS_5_12, Vocabulary.LOM_ES_SOURCE, index ) );
    }

    /** ********************************* SETTERS ************************* */
    public void setTypicalLearningTime( String typicalLearningTime ) {

        this.typicalLearningTime = typicalLearningTime;
    }

    public void setLanguage( String language ) {

        this.language = new ArrayList<String>( );
        this.language.add( language );
    }

    public void setContext( int index ) {

        this.context = new ArrayList<Vocabulary>( );
        this.context.add( new Vocabulary( Vocabulary.LOMES_ED_CONTEXT_5_6, Vocabulary.LOM_ES_SOURCE, index ) );
    }

    public void setIntendedEndUserRole( int index ) {

        this.intendedEndUserRole = new ArrayList<Vocabulary>( );
        this.intendedEndUserRole.add( new Vocabulary( Vocabulary.LOMES_ED_INTENDED_END_USER_ROLE_5_5, Vocabulary.LOM_ES_SOURCE, index ) );
    }

    public void setSemanticDensity( int index ) {

        this.semanticDensity = new Vocabulary( Vocabulary.ED_SEMANTIC_DENSITY_5_4, Vocabulary.LOM_ES_SOURCE, index );
    }

    public void setDifficulty( int index ) {

        this.difficulty = new Vocabulary( Vocabulary.ED_DIFFICULTY_5_8, Vocabulary.LOM_ES_SOURCE, index );
    }

    public void setInteractivityLevel( int index ) {

        this.interactivityLevel = new Vocabulary( Vocabulary.ED_INTERACTIVITY_LEVEL_5_3, Vocabulary.LOM_ES_SOURCE, index );
    }

    public void setInteractivityType( int index ) {

        this.interactivityType = new Vocabulary( Vocabulary.ED_INTERACTIVITY_TYPE_5_1, Vocabulary.LOM_ES_SOURCE, index );
    }

    public void setCognitiveProcess( int index ) {

        this.cognitiveProcess = new ArrayList<Vocabulary>( );
        this.cognitiveProcess.add( new Vocabulary( Vocabulary.LOMES_ED_COGNITIVE_PROCESS_5_12, Vocabulary.LOM_ES_SOURCE, index ) );
    }

    public void setLearningResourceType( int index ) {

        this.learningResourceType = new ArrayList<Vocabulary>( );
        this.learningResourceType.add( new Vocabulary( Vocabulary.LOMES_ED_LEARNING_RESOURCE_TYPE_5_2, Vocabulary.LOM_ES_SOURCE, index ) );
    }

    public void setDescription( LangString description ) {

        this.description = new ArrayList<LangString>( );
        this.description.add( description );
    }

    public void setTypicalAgeRange( LangString typicalAgeRange ) {

        this.typicalAgeRange = new ArrayList<LangString>( );
        this.typicalAgeRange.add( typicalAgeRange );
    }

    /** ********************************* GETTERS ************************* */
    public String getTypicalLearningTime( ) {

        return typicalLearningTime;
    }

    public String getLanguage( ) {

        return language.get( 0 );
    }

    public ArrayList<Vocabulary> getContext( ) {

        return context;
    }

    public ArrayList<Vocabulary> getIntendedEndUserRole( ) {

        return intendedEndUserRole;
    }

    public ArrayList<Vocabulary> getLearningResourceType( ) {

        return learningResourceType;
    }

    public ArrayList<Vocabulary> getCognitiveProcess( ) {

        return this.cognitiveProcess;
    }

    public ArrayList<LangString> getDescription( ) {

        return description;
    }

    public ArrayList<LangString> getTypicalAgeRange( ) {

        return typicalAgeRange;
    }

    public Vocabulary getDifficulty( ) {

        return difficulty;
    }

    public Vocabulary getInteractivityLevel( ) {

        return interactivityLevel;
    }

    public Vocabulary getInteractivityType( ) {

        return interactivityType;
    }

    public Vocabulary getSemanticDensity( ) {

        return semanticDensity;
    }

}
