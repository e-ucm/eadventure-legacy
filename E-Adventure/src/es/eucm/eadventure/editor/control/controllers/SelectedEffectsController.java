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
package es.eucm.eadventure.editor.control.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.config.ProjectConfigData;

public class SelectedEffectsController {

    private static final int NUMBER_OF_EFFECTS = 23;

    /**
     * Constants for each type of effects.
     */
    private static final String ACTIVATE = "ACTIVATE";

    private static final String DEACTIVATE = "DEACTIVATE";

    private static final String INCREMENT = "INCREMENT";

    private static final String DECREMENT = "DECREMENT";

    private static final String SETVALUE = "SETVALUE";

    private static final String MACRO = "MACRO";

    private static final String CONSUME = "CONSUME";

    private static final String GENERATE = "GENERATE";

    private static final String CANCEL = "CANCEL";

    private static final String SPPLAYER = "SPPLAYER";

    private static final String SPNPC = "SPNPC";

    private static final String BOOK = "BOOK";

    private static final String SOUND = "SOUND";

    private static final String ANIMATION = "ANIMATION";

    private static final String MVPLAYER = "MVPLAYER";

    private static final String MVNPC = "MVNPC";

    private static final String CONVERSATION = "CONVERSATION";

    private static final String CUTSCENE = "CUTSCENE";

    private static final String SCENE = "SCENE";

    private static final String LASTSCENE = "LASTSCENE";

    private static final String RANDOM = "RANDOM";

    private static final String SHOWTEXT = "SHOWTEXT";

    private static final String WAITTIME = "WAITTIME";
    
    private static final String HIGHLIGHT = "HIGHLIGHT";
    
    private static final String MOVE_OBJECT = "MOVEOBJECT";

    /**
     * Store all effects selection. Connects the type of effect with the number
     * of times that has been used
     */
    private HashMap<String, ListElements> selectedEffects;

    public SelectedEffectsController( ) {

        init( );
    }

    public ListElements[] getMostVisiteEffects( int size ) {

        ArrayList<ListElements> list = new ArrayList<ListElements>( selectedEffects.values( ) );
        Collections.sort( list );
        ListElements[] values = ( size >= 1 && size <= NUMBER_OF_EFFECTS ) ? new ListElements[ size ] : new ListElements[ NUMBER_OF_EFFECTS ];
        for( int i = 0; i < values.length; i++ ) {
            values[i] = list.get( NUMBER_OF_EFFECTS - i - 1 );
        }
        return values;
    }

    public static String[] getAllEffectTypes( ) {

        return new String[] { ACTIVATE, DEACTIVATE, INCREMENT, DECREMENT, SETVALUE, MACRO, CONSUME, GENERATE, CANCEL, SPPLAYER, SPNPC, BOOK, SOUND, ANIMATION, MVPLAYER, MVNPC, CONVERSATION, CUTSCENE, SCENE, LASTSCENE, RANDOM, SHOWTEXT, WAITTIME };
    }

    /**
     * Restore the past effect selection
     */
    private void init( ) {

        final String[] effectNames = { ACTIVATE, DEACTIVATE, INCREMENT, DECREMENT, SETVALUE, MACRO, CONSUME, GENERATE, CANCEL, SPPLAYER, SPNPC, BOOK, SOUND, ANIMATION, MVPLAYER, MVNPC, CONVERSATION, CUTSCENE, SCENE, LASTSCENE, RANDOM, SHOWTEXT, WAITTIME };
        selectedEffects = new HashMap<String, ListElements>( );
        for( int i = 0; i < effectNames.length; i++ ) {
            String result = ProjectConfigData.getProperty( effectNames[i] );
            if( result != null )
                selectedEffects.put( effectNames[i], new ListElements( effectNames[i], Integer.parseInt( result ) ) );
            else
                selectedEffects.put( effectNames[i], new ListElements( effectNames[i], new Integer( 0 ) ) );
        }

    }

    public void addSelectedEffect( String name ) {

        int value = selectedEffects.get( name ).getValue( );
        selectedEffects.put( name, new ListElements( name, value + 1 ) );
    }

    public static String convertNames( String effectName ) {

        if( effectName.equals( TC.get( "Effect.Activate" ) ) ) {
            return ACTIVATE;
        }
        else if( effectName.equals( TC.get( "Effect.Deactivate" ) ) ) {
            return DEACTIVATE;
        }
        else if( effectName.equals( TC.get( "Effect.SetValue" ) ) ) {
            return SETVALUE;
        }
        else if( effectName.equals( TC.get( "Effect.IncrementVar" ) ) ) {
            return INCREMENT;
        }
        else if( effectName.equals( TC.get( "Effect.DecrementVar" ) ) ) {
            return DECREMENT;
        }
        else if( effectName.equals( TC.get( "Effect.MacroReference" ) ) ) {
            return MACRO;
        }
        else if( effectName.equals( TC.get( "Effect.ConsumeObject" ) ) ) {
            return CONSUME;
        }
        else if( effectName.equals( TC.get( "Effect.GenerateObject" ) ) ) {
            return GENERATE;
        }
        else if( effectName.equals( TC.get( "Effect.CancelAction" ) ) ) {
            return CANCEL;
        }
        else if( effectName.equals( TC.get( "Effect.SpeakPlayer" ) ) ) {
            return SPPLAYER;
        }
        else if( effectName.equals( TC.get( "Effect.SpeakCharacter" ) ) ) {
            return SPNPC;
        }
        else if( effectName.equals( TC.get( "Effect.TriggerBook" ) ) ) {
            return BOOK;
        }
        else if( effectName.equals( TC.get( "Effect.PlaySound" ) ) ) {
            return SOUND;
        }
        else if( effectName.equals( TC.get( "Effect.TriggerConversation" ) ) ) {
            return CONVERSATION;
        }
        else if( effectName.equals( TC.get( "Effect.TriggerCutscene" ) ) ) {
            return CUTSCENE;
        }
        else if( effectName.equals( TC.get( "Effect.TriggerScene" ) ) ) {
            return SCENE;
        }
        else if( effectName.equals( TC.get( "Effect.PlayAnimation" ) ) ) {
            return ANIMATION;
        }
        else if( effectName.equals( TC.get( "Effect.MovePlayer" ) ) ) {
            return MVPLAYER;
        }
        else if( effectName.equals( TC.get( "Effect.MoveCharacter" ) ) ) {
            return MVNPC;
        }
        else if( effectName.equals( TC.get( "Effect.TriggerLastScene" ) ) ) {
            return LASTSCENE;
        }
        else if( effectName.equals( TC.get( "Effect.RandomEffect" ) ) ) {
            return RANDOM;
        }
        else if( effectName.equals( TC.get( "Effect.ShowText" ) ) ) {
            return SHOWTEXT;
        }
        else if( effectName.equals( TC.get( "Effect.WaitTime" ) ) )
            return WAITTIME;
        else if( effectName.equals( TC.get( "Effect.HighlightItem" ) ))
            return HIGHLIGHT;
        else if( effectName.equals( TC.get( "Effect.MoveObject" ) ))
            return MOVE_OBJECT;
        
        return null;
    }

    public static String reconvertNames( String effectName ) {

        if( effectName.equals( ACTIVATE ) ) {
            return TC.get( "Effect.Activate" );
        }
        else if( effectName.equals( DEACTIVATE ) ) {
            return TC.get( "Effect.Deactivate" );
        }
        else if( effectName.equals( SETVALUE ) ) {
            return TC.get( "Effect.SetValue" );
        }
        else if( effectName.equals( INCREMENT ) ) {
            return TC.get( "Effect.IncrementVar" );
        }
        else if( effectName.equals( DECREMENT ) ) {
            return TC.get( "Effect.DecrementVar" );
        }
        else if( effectName.equals( MACRO ) ) {
            return TC.get( "Effect.MacroReference" );
        }
        else if( effectName.equals( CONSUME ) ) {
            return TC.get( "Effect.ConsumeObject" );
        }
        else if( effectName.equals( GENERATE ) ) {
            return TC.get( "Effect.GenerateObject" );
        }
        else if( effectName.equals( CANCEL ) ) {
            return TC.get( "Effect.CancelAction" );
        }
        else if( effectName.equals( SPPLAYER ) ) {
            return TC.get( "Effect.SpeakPlayer" );
        }
        else if( effectName.equals( SPNPC ) ) {
            return TC.get( "Effect.SpeakCharacter" );
        }
        else if( effectName.equals( BOOK ) ) {
            return TC.get( "Effect.TriggerBook" );
        }
        else if( effectName.equals( SOUND ) ) {
            return TC.get( "Effect.PlaySound" );
        }
        else if( effectName.equals( CONVERSATION ) ) {
            return TC.get( "Effect.TriggerConversation" );
        }
        else if( effectName.equals( CUTSCENE ) ) {
            return TC.get( "Effect.TriggerCutscene" );
        }
        else if( effectName.equals( SCENE ) ) {
            return TC.get( "Effect.TriggerScene" );
        }
        else if( effectName.equals( ANIMATION ) ) {
            return TC.get( "Effect.PlayAnimation" );
        }
        else if( effectName.equals( MVPLAYER ) ) {
            return TC.get( "Effect.MovePlayer" );
        }
        else if( effectName.equals( MVNPC ) ) {
            return TC.get( "Effect.MoveCharacter" );
        }
        else if( effectName.equals( LASTSCENE ) ) {
            return TC.get( "Effect.TriggerLastScene" );
        }
        else if( effectName.equals( RANDOM ) ) {
            return TC.get( "Effect.RandomEffect" );
        }
        else if( effectName.equals( SHOWTEXT ) ) {
            return TC.get( "Effect.ShowText" );
        }
        else if( effectName.equals( WAITTIME ) )
            return TC.get( "Effect.WaitTime" );
        else
            return null;
    }

    public class ListElements implements Comparable {

        private String name;

        private Integer value;

        public ListElements( String name, Integer value ) {

            this.name = name;
            this.value = value;
        }

        public String getName( ) {

            return name;
        }

        public Integer getValue( ) {

            return value;
        }

        public int compareTo( Object o ) {

            ListElements listElement = (ListElements) o;
            return this.value.compareTo( listElement.value );
        }

    }
}
