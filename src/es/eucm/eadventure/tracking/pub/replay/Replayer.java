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

package es.eucm.eadventure.tracking.pub.replay;

import java.awt.AWTException;
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

import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.tracking.pub.replay.TraceDefaultHandler.Actions;

public class Replayer extends Thread {

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
        if( fileChooser.showOpenDialog( null ) == JFileChooser.APPROVE_OPTION ) {
            File f = fileChooser.getSelectedFile( );
            handler = parseXML( f );
            this.start( );
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
            long elapsed = 0;
            long oldTime = System.currentTimeMillis( );
            long currentTimestamp = System.currentTimeMillis( );
            long diff = 0;

            Point loc = GUI.getInstance( ).getFrame( ).getLocationOnScreen( );

            int i = 0;
            while( i < handler.getActions( ).size( ) ) {
                r.delay( handler.getDelays( ).get( i ) );
                currentTimestamp = System.currentTimeMillis( );
                diff = currentTimestamp - oldTime;
                elapsed += diff;
                oldTime = currentTimestamp;
                //                while ( handler.getMs( ).get( i ) < elapsed ) {
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
                }
                i++;
            }

            //            }
        }
        catch( AWTException e ) {
            e.printStackTrace( );
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
        if( nextOpt < handler.getOpts( ).size( ) - 1) {
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
}
