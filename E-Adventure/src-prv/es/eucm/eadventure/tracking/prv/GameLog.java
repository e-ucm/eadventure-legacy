/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the
 * e-UCM research group.
 * 
 * Copyright 2005-2012 e-UCM research group.
 * 
 * e-UCM is a research group of the Department of Software Engineering and
 * Artificial Intelligence at the Complutense University of Madrid (School of
 * Computer Science).
 * 
 * C Profesor Jose Garcia Santesmases sn, 28040 Madrid (Madrid), Spain.
 * 
 * For more info please visit: <http://e-adventure.e-ucm.es> or
 * <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 * You can access a list of all the contributors to eAdventure at:
 * http://e-adventure.e-ucm.es/contributors
 * 
 * ****************************************************************************
 * eAdventure is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * eAdventure is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Adventure. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.tracking.prv;

import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.tracking.pub._GameLog;
import es.eucm.eadventure.tracking.pub._HighLevelEvents;

public class GameLog implements _GameLog {

    private List<GameLogEntry> allEntries;

    private long startTimeStamp;

    private long lastLowLevelUpdate;

    private boolean logging;

    private boolean effectVerbosity;

    private long threshold;
    
    private List<GameLogEntry> specialEvents;

    public List<GameLogEntry> getEntries( ) {

        return allEntries;
    }

    public GameLog( boolean logging, boolean effectVerbosity, long startTimeStamp, long threshold, String gameId, String code, String specialEvents ) {

        allEntries = new ArrayList<GameLogEntry>( );
        this.logging = logging;
        this.effectVerbosity = effectVerbosity;
        this.startTimeStamp = startTimeStamp;
        this.threshold = threshold;
        this.specialEvents = buildSpecialEntries(specialEvents);
        addStartTimeEntry( gameId, code );
    }

    private void addStartTimeEntry( String gameId, String code ) {
        if( !logging )
            return;
        
        Integer offset=getOffsetX();
        GameLogEntry newEntry = new GameLogEntry( startTimeStamp, "start", offset, new String[] { "version="+TrackingConfigExtended.VERSION, "gameid="+gameId, "code="+code,"timestampms=" + startTimeStamp, "timestamp=" + DateFormat.getDateTimeInstance( ).format( new Date( startTimeStamp ) ) } );
        addEntry( newEntry );
    }

    private Integer getOffsetX(){
        Integer offset=null;
        if (Game.getInstance( )!=null && Game.getInstance( ).getFunctionalScene( )!=null){
            offset=new Integer(Game.getInstance( ).getFunctionalScene( ).getOffsetX( ));
        }
        return offset;
    }
    
    private String idToStr( int id ) {

        String str = "";
        if( id == MouseEvent.MOUSE_CLICKED )
            str = "c";
        else if( id == MouseEvent.MOUSE_DRAGGED )
            str = "d";
        else if( id == MouseEvent.MOUSE_ENTERED )
            str = "en";
        else if( id == MouseEvent.MOUSE_EXITED )
            str = "ex";
        else if( id == MouseEvent.MOUSE_MOVED )
            str = "m";
        else if( id == MouseEvent.MOUSE_RELEASED || id == KeyEvent.KEY_RELEASED )
            str = "r";
        else if( id == MouseEvent.MOUSE_PRESSED || id == KeyEvent.KEY_PRESSED )
            str = "p";
        else if( id == MouseEvent.MOUSE_WHEEL )
            str = "w";
        else if( id == KeyEvent.KEY_TYPED )
            str = "t";
        else if( id == FocusEvent.FOCUS_GAINED )
            str = "g";
        else if( id == FocusEvent.FOCUS_LOST )
            str = "l";
        return str;
    }

    
    public void lowLevelEvent( MouseEvent e ) {

        if( !logging )
            return;
        long currentTime = System.currentTimeMillis( );

        if( currentTime - lastLowLevelUpdate >= threshold || e.getID( ) == MouseEvent.MOUSE_CLICKED || e.getID( ) == MouseEvent.MOUSE_PRESSED || e.getID( ) == MouseEvent.MOUSE_RELEASED ) {
            Integer offset=getOffsetX();
            GameLogEntry newEntry = new GameLogEntry( startTimeStamp, "l", offset, new String[] { "t=m", "i=" + idToStr( e.getID( ) ), "x=" + e.getX( ), "y=" + e.getY( ), "b=" + e.getButton( ), "c=" + e.getClickCount( ), "m=" + e.getModifiersEx( ) } );
            
            addEntry( newEntry );
            lastLowLevelUpdate = currentTime;
        }
    }

    public void lowLevelEvent( KeyEvent k ) {

        if( !logging )
            return;
        Integer offset=getOffsetX();
        String keyCode = "";
        if ( k.getID( ) == KeyEvent.KEY_TYPED ){
            keyCode = "k=" + k.getKeyChar( );
        }
        else {
            keyCode = "c=" + k.getKeyCode( );
        }
        
        GameLogEntry newEntry = new GameLogEntry( startTimeStamp, "l", offset, new String[] { "t=k", "i=" + idToStr( k.getID( ) ), keyCode, "l=" + k.getKeyLocation( ), "m=" + k.getModifiersEx( ) } );
        addEntry( newEntry );
    }

    public void lowLevelEvent( MouseWheelEvent e ) {

        if( !logging )
            return;
        long currentTime = System.currentTimeMillis( );
        if( currentTime - lastLowLevelUpdate >= threshold ) {
            Integer offset=getOffsetX();
            GameLogEntry newEntry = new GameLogEntry( startTimeStamp, "l", offset, new String[] { "t=w", "i=" + idToStr( e.getID( ) ), "x=" + e.getX( ), "y=" + e.getY( ), "s=" + e.getScrollAmount( ), "r=" + e.getWheelRotation( ), "m=" + e.getModifiersEx( ) } );
            addEntry( newEntry );
            lastLowLevelUpdate = currentTime;
        }

    }

    public void lowLevelEvent( FocusEvent f ) {

        if( !logging )
            return;
        Integer offset=getOffsetX();
        GameLogEntry newEntry = new GameLogEntry( startTimeStamp, "l", offset, new String[] { "t=f", "i=" + idToStr( f.getID( ) ) } );
        addEntry( newEntry );
    }

    public void highLevelEvent( String action ) {

        if( !logging )
            return;
        Integer offset=getOffsetX();
        GameLogEntry newEntry = new GameLogEntry( startTimeStamp, "h", offset, new String[] { "a=" + action } );
        addEntry( newEntry );
    }

    public void highLevelEvent( String action, String object ) {

        if( !logging )
            return;
        Integer offset=getOffsetX();
        GameLogEntry newEntry = new GameLogEntry( startTimeStamp, "h", offset, new String[] { "a=" + action, "o=" + object } );
        addEntry( newEntry );
    }

    public void highLevelEvent( String action, String object, String target ) {

        if( !logging )
            return;
        Integer offset=getOffsetX();
        GameLogEntry newEntry = new GameLogEntry( startTimeStamp, "h", offset, new String[] { "a=" + action, "o=" + object, "t=" + target } );
        addEntry( newEntry );
    }

    public void highLevelEvent( String action, String object, String target, String line ) {

        if( !logging )
            return;
        Integer offset=getOffsetX();
        GameLogEntry newEntry = new GameLogEntry( startTimeStamp, "h", offset, new String[] { "a=" + action, "o=" + object, "t=" + target, "l=" + line } );
        addEntry( newEntry );
    }

    private void addEntry( GameLogEntry entry ) {
            checkSpecialEvent( entry );
        
        synchronized( allEntries ) {
            allEntries.add( entry );
        }
        
    }

    public void effectEvent( String effectCode, String... arguments ) {

        if( !logging || !effectVerbosity )
            return;
        String[] args = null;
        if( arguments == null || arguments.length == 0 ) {
            args = new String[ 1 ];
        }
        else {
            args = new String[ arguments.length + 1 ];
        }
        args[0] = "e=" + effectCode;
        if( args.length > 1 ) {
            for( int i = 1; i < args.length; i++ ) {
                args[i] = arguments[i - 1];
            }
        }
        Integer offset=getOffsetX();
        GameLogEntry newEntry = new GameLogEntry( startTimeStamp, "h", offset, args );
        addEntry( newEntry );
    }

    
    private void checkSpecialEvent( GameLogEntry entry ){
        for (GameLogEntry special: this.specialEvents){
            if (special.getElementName( ).equals( entry.getElementName( ) ) && 
                    special.getAttributeCount( ) == entry.getAttributeCount( )){
                boolean equals = true;
                for (int i=0; i<special.getAttributeCount( ); i++){
                    if (special.getAttributeName( i ).equals( "off" ) || special.getAttributeName( i ).equals( "ms" )){
                        continue;
                    }
                    equals &= special.getAttributeValue( i ).equals( entry.getAttributeValue(i) );
                }
                if (equals){
                    if (Game.getInstance( )!=null && Game.getInstance( ).getAssessmentEngine( )!=null){
                        highLevelEvent( _HighLevelEvents.ASSESSMENT_REPORT, 
                                Game.getInstance( ).getAssessmentEngine( ).getHTMLReportString( ));
                    }
                }
            }
        }
    }
    
    private List<GameLogEntry> buildSpecialEntries(String specialIDs){
        List<GameLogEntry> specialEntries = new ArrayList<GameLogEntry>();
        if (specialIDs!=null){
            String[] idArray = specialIDs.split( " " );
            for (String id:idArray){
                Integer offset=new Integer(0);
                GameLogEntry newEntry = new GameLogEntry( startTimeStamp, "h", offset, new String[] { "a=" + _HighLevelEvents.NEW_SCENE, "o=" + id } );
                specialEntries.add( newEntry );
            }
        }
        return specialEntries;
    }
}
