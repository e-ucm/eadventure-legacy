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
package es.eucm.eadventure.editor.control.writer.domwriters;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.Timer;
import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.conditions.GlobalState;
import es.eucm.eadventure.common.data.chapter.conversation.Conversation;
import es.eucm.eadventure.common.data.chapter.effects.Macro;
import es.eucm.eadventure.common.data.chapter.elements.Atrezzo;
import es.eucm.eadventure.common.data.chapter.elements.Item;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.scenes.Cutscene;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;

public class ChapterDOMWriter {

    /**
     * Private constructor.
     */
    private ChapterDOMWriter( ) {

    }

    /**
     * Returns the DOM element for the chapter
     * 
     * @param chapter
     *            Chapter data to be written
     * @return DOM element with the chapter data
     */
    public static Node buildDOM( Chapter chapter, String zipFile, Document doc ) {

        Element chapterNode = null;

        //try {
        // Create the necessary elements to create the DOM
        /*DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
        DocumentBuilder db = dbf.newDocumentBuilder( );
        Document doc = db.newDocument( );
        */
        // Create the root node
        chapterNode = doc.createElement( "eAdventure" );

        // Add the adaptation and assessment active profiles
        if( !chapter.getAdaptationName( ).equals( "" ) )
            chapterNode.setAttribute( "adaptProfile", chapter.getAdaptationName( ) );

        // Create and append the assessment configuration
        if( !chapter.getAssessmentName( ).equals( "" ) ) {
            chapterNode.setAttribute( "assessProfile", chapter.getAssessmentName( ) );
        }

        // Append the scene elements
        for( Scene scene : chapter.getScenes( ) ) {
            boolean initialScene = chapter.getTargetId( ).equals( scene.getId( ) );
            Node sceneNode = SceneDOMWriter.buildDOM( scene, initialScene );
            doc.adoptNode( sceneNode );
            chapterNode.appendChild( sceneNode );
        }

        // Append the cutscene elements
        for( Cutscene cutscene : chapter.getCutscenes( ) ) {
            boolean initialScene = chapter.getTargetId( ).equals( cutscene.getId( ) );
            Node cutsceneNode = CutsceneDOMWriter.buildDOM( cutscene, initialScene );
            doc.adoptNode( cutsceneNode );
            chapterNode.appendChild( cutsceneNode );
        }

        // Append the book elements
        for( Book book : chapter.getBooks( ) ) {
            Node bookNode = BookDOMWriter.buildDOM( book );
            doc.adoptNode( bookNode );
            chapterNode.appendChild( bookNode );
        }

        // Append the item elements
        for( Item item : chapter.getItems( ) ) {
            Node itemNode = ItemDOMWriter.buildDOM( item );
            doc.adoptNode( itemNode );
            chapterNode.appendChild( itemNode );
        }

        // Append the player element
        Node playerNode = PlayerDOMWriter.buildDOM( chapter.getPlayer( ) );
        doc.adoptNode( playerNode );
        chapterNode.appendChild( playerNode );

        // Append the character element
        for( NPC character : chapter.getCharacters( ) ) {
            Node characterNode = CharacterDOMWriter.buildDOM( character );
            doc.adoptNode( characterNode );
            chapterNode.appendChild( characterNode );
        }

        // Append the conversation element
        for( Conversation conversation : chapter.getConversations( ) ) {
            Node conversationNode = ConversationDOMWriter.buildDOM( conversation );
            doc.adoptNode( conversationNode );
            chapterNode.appendChild( conversationNode );
        }

        // Append the timers
        for( Timer timer : chapter.getTimers( ) ) {
            Node timerNode = TimerDOMWriter.buildDOM( timer );
            doc.adoptNode( timerNode );
            chapterNode.appendChild( timerNode );
        }

        // Append global states
        for( GlobalState globalState : chapter.getGlobalStates( ) ) {
            Element globalStateElement = ConditionsDOMWriter.buildDOM( globalState );
            doc.adoptNode( globalStateElement );
            chapterNode.appendChild( globalStateElement );
        }

        // Append macros
        for( Macro macro : chapter.getMacros( ) ) {
            Element macroElement = EffectsDOMWriter.buildDOM( macro );
            doc.adoptNode( macroElement );
            chapterNode.appendChild( macroElement );
        }

        // Append the atrezzo item elements
        for( Atrezzo atrezzo : chapter.getAtrezzo( ) ) {
            Node atrezzoNode = AtrezzoDOMWriter.buildDOM( atrezzo );
            doc.adoptNode( atrezzoNode );
            chapterNode.appendChild( atrezzoNode );
        }

        /*} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
        }*/

        return chapterNode;
    }
}
