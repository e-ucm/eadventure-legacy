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
package es.eucm.eadventure.engine;

import java.util.HashMap;

import javax.media.Codec;
import javax.media.Format;
import javax.media.PlugInManager;
import javax.media.format.VideoFormat;

import de.schlichtherle.io.ArchiveDetector;
import de.schlichtherle.io.DefaultArchiveDetector;
import de.schlichtherle.io.File;
import es.eucm.eadventure.comm.manager.commManager.CommManagerScorm;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.core.gui.GUIApplet;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

public class EAdventureAppletScorm extends CommManagerScorm {

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
        File.setDefaultArchiveDetector( new DefaultArchiveDetector( ArchiveDetector.NULL, // delegate
        new String[] {
        //"ead", "de.schlichtherle.io.archive.zip.JarDriver",
        //"ead", "de.schlichtherle.io.archive.zip.Zip32Driver",
        "ead", "es.eucm.eadventure.common.auxiliar.EADDriver", } ) );

        try {
            Codec video = (Codec) Class.forName( "net.sourceforge.jffmpeg.VideoDecoder" ).newInstance( );
            PlugInManager.addPlugIn( "net.sourceforge.jffmpeg.VideoDecoder", video.getSupportedInputFormats( ), new Format[] { new VideoFormat( VideoFormat.MPEG ) }, PlugInManager.CODEC );
        }
        catch( Exception e ) {
        }

        //FIXME: Harcoded
        String adventureName = "integration";
        this.readParameters( );

        if( !windowed ) {
            GUI.setGUIType( GUI.GUI_APPLET );
            GUIApplet.setApplet( this );
        }

        ResourceHandler.setRestrictedMode( true );
        ResourceHandler.getInstance( ).setZipFile( adventureName + ".zip" );
        Game.create( );
        eAdventure = Game.getInstance( );
        eAdventure.setAdventureName( adventureName );

        /*try {
        	this.sendMessage("esto es", "un mensaje");
        	//this.connect(new HashMap<String, String>());
        } catch (CommException e) {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        }*/
        eAdventure.setComm( this );
        //System.out.println("Init finished succesfully");
        //System.out.println( " CODE BASE="+this.getCodeBase( ).toString( ));

    }

    /*
     *  (non-Javadoc)
     * @see java.applet.Applet#start()
     */
    @Override
    public void start( ) {

        this.connect( new HashMap<String, String>( ) );
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
        this.disconnect( new HashMap<String, String>( ) );
        try {
            System.out.println( "Trying to join..." );
            Game.delete( );
            ResourceHandler.delete( );
            gameThread.join( );
            System.out.println( "...join successful." );

        }
        catch( InterruptedException e ) {
            // TODO Auto-generated catch block
            //e.printStackTrace();

        }
        this.destroy( );
    }

    @Override
    public void dataFromLMS( String key, String value ) {

        super.dataFromLMS( key, value );
    }

}
