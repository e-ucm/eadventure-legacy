/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 * research group.
 * 
 * Copyright 2005-2012 <e-UCM> research group.
 * 
 * <e-UCM> is a research group of the Department of Software Engineering and
 * Artificial Intelligence at the Complutense University of Madrid (School of
 * Computer Science).
 * 
 * C Profesor Jose Garcia Santesmases sn, 28040 Madrid (Madrid), Spain.
 * 
 * For more info please visit: <http://e-adventure.e-ucm.es> or
 * <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * This file is part of <e-Adventure>, version 1.4.
 * 
 * You can access a list of all the contributors to <e-Adventure> at:
 * http://e-adventure.e-ucm.es/contributors
 * 
 * ****************************************************************************
 * <e-Adventure> is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with <e-Adventure>. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.tracking.prv.replay;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.OptionConversationNode;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.gamestate.GameState;
import es.eucm.eadventure.engine.core.control.gamestate.GameStatePlaying;
import es.eucm.eadventure.engine.core.control.gamestate.GameStateSlidescene;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.tracking.prv.replay.TraceDefaultHandler.Actions;
import es.eucm.eadventure.tracking.pub.replay._Replayer;

public class Replayer extends Thread implements _Replayer{

    private JFileChooser fileChooser = new JFileChooser( );

    private static TraceDefaultHandler handler;

    public static boolean replaying = false;

    public static int nextLine = 0;

    public static int nextOpt = 0;

    public static int currentScene = -1;

    public static boolean errorOpt = false;

    public Replayer( ) {

    }

    public void loadTraces( ) {

        replaying = false;
        if (replaying){
            if( fileChooser.showOpenDialog( null ) == JFileChooser.APPROVE_OPTION ) {
                File f = fileChooser.getSelectedFile( );
                handler = parseXML( f );
                this.start( );
            }
        }
    }

    private static TraceDefaultHandler parseXML( File xmlFile ) {

        SAXParserFactory factory = SAXParserFactory.newInstance( );
        TraceDefaultHandler handler = new TraceDefaultHandler( );
        try {
            SAXParser saxParser = factory.newSAXParser( );
            saxParser.parse( xmlFile, handler );
        }
        catch( ParserConfigurationException e ) {
            e.printStackTrace( );
        }
        catch( SAXException e ) {
            e.printStackTrace( );
        }
        catch( IOException e ) {
            e.printStackTrace( );
        }
        return handler;
    }

    @Override
    public void run( ) {

        try {
            Robot r = new Robot( );
            errorOpt = false;
            replaying = true;
            nextLine = 0;
            nextOpt = 0;
            currentScene = -1;
            boolean checkScene = false;

            Point loc = GUI.getInstance( ).getFrame( ).getLocationOnScreen( );

            int i = 0;
            while( i < handler.getActions( ).size( ) ) {
                r.delay( handler.getDelays( ).get( i ) );
                Actions a = handler.getActions( ).get( i );
                Point p = handler.getPoints( ).get( i );
                p.x += loc.x;
                p.y += loc.y;
                Integer button = handler.getButtons( ).get( i );
                int b = InputEvent.BUTTON1_DOWN_MASK;
                switch( button ) {
                    case 2:
                        b = InputEvent.BUTTON2_DOWN_MASK;
                        break;
                    case 3:
                        b = InputEvent.BUTTON3_DOWN_MASK;
                        break;
                }

                if( checkScene ) {
                    checkScene = false;
                    String gameScene = Game.getInstance( ).getFunctionalScene( ).getScene( ).getId( );
                    String logsScene = getCurrentScene( );
                    if( !gameScene.equals( logsScene ) && !logsScene.equals( "unknown" ) ) {
                        changeGameScene( logsScene, p.x - loc.x, p.y - loc.y );
                    }
                }

                if( a == Actions.MOVE ) {
                    r.mouseMove( p.x, p.y );
                }
                else if( a == Actions.PRESS ) {
                    r.mouseMove( p.x, p.y );
                    r.mousePress( b );
                }
                else if( a == Actions.RELEASE ) {
                    r.mouseMove( p.x, p.y );
                    r.mouseRelease( b );
                }
                else if( a == Actions.SCENE ) {
                    currentScene++;
                    checkScene = true;
                }
                i++;
            }
        }
        catch( AWTException e ) {
            e.printStackTrace( );
        }
    }

    private void changeGameScene( String logsScene, int x, int y ) {

        Scene scene = Game.getInstance( ).getFunctionalScene( ).getScene( );
        Exit rightExit = null;
        for( Exit e : scene.getExits( ) ) {
            if( e.isPointInside( x, y ) && e.getNextSceneId( ).equals( logsScene ) ) {
                rightExit = e;
                break;
            }
            else if( e.getNextSceneId( ).equals( logsScene ) ) {
                rightExit = e;
            }
        }
        if( rightExit != null ) {
            Game.getInstance( ).setNextScene( rightExit );
            Game.getInstance( ).setState( Game.STATE_NEXT_SCENE );
        }

    }

    public static String getNextLine( ) {

        String line = handler.getLines( ).get( nextLine );
        if( nextLine < handler.getLines( ).size( ) - 1 ) {
            nextLine++;
        }
        else {
            errorOpt = true;
        }
        return line;
    }

    public static int getNextOpt( ) {

        int opt = handler.getOpts( ).get( nextOpt );
        if( nextOpt < handler.getOpts( ).size( ) - 1 ) {
            nextOpt++;
        }
        else {
            errorOpt = true;
        }
        return opt;
    }

    public static String getCurrentScene( ) {

        return currentScene >= 0 && currentScene < handler.getScenes( ).size( ) ? handler.getScenes( ).get( currentScene ) : "unknown";
    }
    
    /**
     * This method is 
     * 
     * @param option
     * @param line
     */
    public static void doTheTrick( int option, String line, OptionConversationNode node ) {

        boolean found = false;
        int index = 0;
        while( index < node.getLineCount( ) && !found ) {
            ConversationLine lineNode = node.getLine( index );
            if( lineNode.getText( ).equals( line ) ) {
                found = true;
            }
            else {
                index++;
            }
        }
        ConversationLine lineNode = node.removeLine( index );
        ConversationNode nodeRemoved = node.removeChild( index );

        node.addLine( option, lineNode );
        node.addChild( option, nodeRemoved );
    }

    public boolean arrangeOptionsNode( OptionConversationNode node ) {
     // If the replayer is working, we use the preloaded responses
        if( Replayer.replaying ) {
            String s = Replayer.getNextLine( );
            int opt = Replayer.getNextOpt( );
            doTheTrick(opt,s,node);
            return true;
        }
        return false;
    }

    public void renderState( Graphics2D g, GameState state ) {
        if (state!=null && state instanceof GameStatePlaying){
            if( Replayer.replaying ) {
                g.setColor( Color.WHITE );
                g.drawString( "Current scene: " + Replayer.getCurrentScene( ), 10, 14 );
                if ( Replayer.errorOpt ){
                    g.drawString( "Error with options generation", 10, 34 );
                }
            }
        }
        else if (state!=null && state instanceof GameStateSlidescene){
            if( Replayer.replaying ) {
                GUI.getInstance( ).getGraphics( ).setColor( Color.WHITE );
                GUI.getInstance( ).getGraphics( ).drawString( "Current scene: " + Replayer.getCurrentScene( ), 10, 14 );
            }
        }

        
    }
}
