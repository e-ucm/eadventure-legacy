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
package es.eucm.eadventure.editor.gui.otherpanels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.MissingResourceException;

import javax.media.ControllerErrorEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.PrefetchCompleteEvent;
import javax.media.RealizeCompleteEvent;
import javax.media.StopEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;

public class VideoPanel extends JPanel implements ControllerListener {

    /**
     * Required
     */
    private static final long serialVersionUID = 3265909297116717581L;

    private boolean realized = false;

    private boolean stoped = false;

    private boolean error = false;

    private Player player;

    private boolean prefetched;

    public VideoPanel( String videoPath ) {

        this( );
        loadVideo( videoPath );
    }

    public VideoPanel( ) {

        realized = stoped = prefetched = false;
        this.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;

        this.add( new JLabel( "Video not available" ), c );

    }

    public synchronized void blockingRealize( ) {

        //TODO excepcion cuando no soporta el formato
        player.realize( );
        while( !realized ) {
            try {
                wait( );
                if( error ) {
                    Controller.getInstance( ).showErrorDialog( TC.get( "Error.VideoGeneralError" ), TC.get( "Error.BadAudioFormat.Message" ) );
                    //error=false;
                    break;
                }
            }
            catch( InterruptedException e ) {
                System.out.println( "Interrupted while waiting on realize...exiting." );
                System.exit( 1 );
            }
        }
    }

    public synchronized void blockingStop( ) {

        player.stop( );
        while( !stoped ) {
            try {
                wait( );

            }
            catch( InterruptedException e ) {
                System.out.println( "Interrupted while waiting on realize...exiting." );
                System.exit( 1 );
            }
        }

    }

    public synchronized void blockingPrefetch( ) {

        player.prefetch( );
        while( !prefetched ) {
            try {
                wait( );

            }
            catch( InterruptedException e ) {
                System.out.println( "Interrupted while waiting on realize...exiting." );
                System.exit( 1 );
            }
        }
    }

    public synchronized void controllerUpdate( ControllerEvent event ) {

        if( event instanceof RealizeCompleteEvent ) {
            realized = true;
            notify( );
        }
        else if( event instanceof EndOfMediaEvent ) {
            //eomReached = true;
        }
        else if( event instanceof StopEvent ) {
            stoped = true;
            notify( );
        }
        else if( event instanceof PrefetchCompleteEvent ) {
            prefetched = true;
            notify( );
        }
        else if( event instanceof ControllerErrorEvent ) {
            error = true;

            notify( );

        }
        //(else if (event instanceof )
    }

    public void loadVideo( String videoPath ) {

        try {
            realized = stoped = prefetched = false;
            if( player != null )
                blockingStop( );
            this.removeAll( );

            player = Manager.createPlayer( AssetsController.getVideo( videoPath ) );
            
            if( player != null ) {
                player.addControllerListener( this );

                this.blockingRealize( );
                if( !error ) {
                    this.blockingPrefetch( );

                    this.setLayout( new BorderLayout( ) );
                    Component visual = player.getVisualComponent( );
                    if( visual != null )
                        this.add( visual, BorderLayout.CENTER );
                    else
                        Controller.getInstance( ).showErrorDialog( TC.get( "Error.NoVideoFormat" ), TC.get( "Error.NoVideoFormat.Message" ) );
                    this.add( player.getControlPanelComponent( ), BorderLayout.SOUTH );
                    this.updateUI( );
                    //this.blockingPrefetch( );

                    //this.add( 
                    //player.getControlPanelComponent( ));
                }
            }
            else {
                Controller.getInstance( ).showErrorDialog( TC.get( "Error.VideoGeneralError" ), TC.get( "Error.BadAudioFormat.Message" ) );
            }

        }
        catch( NoPlayerException e ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.NoVideoFormat" ), TC.get( "Error.NoVideoFormat.Message" ) );
        }
        catch( MalformedURLException e ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.VideoGeneralError" ), TC.get( "Error.VideoGeneralError.Message" ) );
        }
        catch( IOException e ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.VideoGeneralError" ), TC.get( "Error.VideoGeneralError.Message" ) );
        }
        catch( MissingResourceException e ) {
            Controller.getInstance( ).showErrorDialog( TC.get( "Error.VideoGeneralError" ), TC.get( "Error.BadAudioFormat.Message" ) );
        }

    }

    public void removeVideo( ) {

        if( player != null && !error ) {
            blockingStop( );
            this.removeAll( );
            this.setLayout( new GridBagLayout( ) );
            GridBagConstraints c = new GridBagConstraints( );
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;

            this.add( new JLabel( "Video not available" ), c );
            updateUI( );
            player = null;
        }

    }

    public void stopVideo( ) {

        if( player != null )
            blockingStop( );
    }

    public boolean isError( ) {

        return error;
    }

}
