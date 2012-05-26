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
package es.eucm.eadventure.engine;

import java.awt.BorderLayout;

import javax.media.Codec;
import javax.media.PlugInManager;

import es.eucm.eadventure.comm.manager.commManager.CommManagerLD;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.core.gui.GUIApplet;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * This is the main class, when run in a browser as an applet. Creates a new
 * game and runs it.
 */
/**
 * @updated by Javier Torrente. New functionalities added - Support for .ead
 *          files. Therefore <e-Adventure> files are no longer .zip but .ead
 */

public class EAdventureApplet extends CommManagerLD {

    /**
     * Required by AsynchronousCommunicationAppletLD
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instance of the Game
     */
    private Game eAdventure;

    /**
     * Thread in which the game will be running
     */
    private Thread gameThread;

    /*
     *  (non-Javadoc)
     * @see java.applet.Applet#init()
     */
    @Override
    public void init( ) {
        TC.loadStrings( EAdventureApplet.class.getResourceAsStream( "/i18n/engine/en_EN.xml" ) );

        try {
            Codec video = (Codec) Class.forName( "net.sourceforge.jffmpeg.VideoDecoder" ).newInstance( );
            PlugInManager.addPlugIn( "net.sourceforge.jffmpeg.VideoDecoder", video.getSupportedInputFormats( ), video.getSupportedOutputFormats( null ), PlugInManager.CODEC );
            PlugInManager.commit( );
        }
        catch( Exception e ) {
        }

        //FIXME: Harcoded
        final String adventureName = "integration";
        this.readParameters( );

        ResourceHandler.setRestrictedMode( true );
        ResourceHandler.getInstance( ).setZipFile( adventureName + ".zip" );

        
        if( !windowed ) {
            setLayout(new BorderLayout());
            GUI.setGUIType( GUI.GUI_APPLET );
            GUIApplet.setApplet( this );
        }

        Game.create( );
        eAdventure = Game.getInstance( );
        eAdventure.setAdventureName( adventureName );

        eAdventure.setComm( EAdventureApplet.this );

        // Para probar en eclipse (comentar el la parte anterior)
        /*
               ResourceHandler.setRestrictedMode( false );
               ResourceHandler.getInstance( ).setZipFile( "C:/Users/e-ucm/Documents/hematocrito_2margins.ead" );
               
               if (!windowed) {
               GUI.setGUIType(GUI.GUI_APPLET);
               GUIApplet.setApplet(this);
               }
               
               Game.create( );
               eAdventure = Game.getInstance( );
               eAdventure.setAdventureName("hematocrito_2margins");
               eAdventure.setAdventurePath("C:/Users/e-ucm/Documents");
               eAdventure.setComm(EAdventureApplet.this);
        */

        //System.out.println("Init finished succesfully");
        //System.out.println( " CODE BASE="+this.getCodeBase( ).toString( ));
    }

    /*
     *  (non-Javadoc)
     * @see java.applet.Applet#start()
     */
    @Override
    public void start( ) {

        this.connect( null );
        gameThread = new Thread( eAdventure );
        gameThread.start( );
    }

    /*
     *  (non-Javadoc)
     * @see java.applet.Applet#stop()
     */
    @Override
    public void stop( ) {

        System.out.println( "Closing..." );
        eAdventure.setGameOver( );
        try {
            System.out.println( "Trying to join..." );
            gameThread.join( );
            System.out.println( "...join successful." );
            Game.delete( );
            ResourceHandler.delete( );
        }
        catch( InterruptedException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace( );
        }
        this.destroy( );
    }

}
