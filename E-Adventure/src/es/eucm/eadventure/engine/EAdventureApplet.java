/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.engine;
import javax.media.Codec;
import javax.media.Format;
import javax.media.PlugInManager;
import javax.media.format.VideoFormat;

import de.schlichtherle.io.ArchiveDetector;
import de.schlichtherle.io.DefaultArchiveDetector;
import de.schlichtherle.io.File;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.core.gui.GUIApplet;
import es.eucm.eadventure.comm.manager.commManager.CommManagerLD;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * This is the main class, when run in a browser as an applet. Creates a new game and runs it.
 */
/**
 * @updated by Javier Torrente. New functionalities added
 * - Support for .ead files. Therefore <e-Adventure> files are no longer .zip but .ead
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
    public void init( ) {
        TextConstants.loadStrings( EAdventureApplet.class.getResourceAsStream( "/lanengine/en_EN.xml" ) );
        File.setDefaultArchiveDetector(new DefaultArchiveDetector(
                ArchiveDetector.NULL, // delegate
                new String[] {
                    //"ead", "de.schlichtherle.io.archive.zip.JarDriver",
                    //"ead", "de.schlichtherle.io.archive.zip.Zip32Driver",
                    "ead", "es.eucm.eadventure.common.auxiliar.EADDriver",
                }));
        
        try {
            Codec video = (Codec) Class.forName(
                    "net.sourceforge.jffmpeg.VideoDecoder").newInstance();
            PlugInManager.addPlugIn("net.sourceforge.jffmpeg.VideoDecoder",
                    video.getSupportedInputFormats(),
                    new Format[] { new VideoFormat(VideoFormat.MPEG) },
                    PlugInManager.CODEC);
        } catch (Exception e) {
        }

        //FIXME: Harcoded
        final String adventureName = "integration";
        this.readParameters();

        ResourceHandler.setRestrictedMode( true );
        ResourceHandler.getInstance( ).setZipFile( adventureName + ".zip" );
        
        if (!windowed) {
	        GUI.setGUIType(GUI.GUI_APPLET);
	        GUIApplet.setApplet(this);
        }
        
        Game.create( );
        eAdventure = Game.getInstance( );
        eAdventure.setAdventureName(adventureName );

        eAdventure.setComm(EAdventureApplet.this);

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
    public void start( ) {
        this.connect(null);
        gameThread = new Thread(eAdventure);
        gameThread.start();
    }
    
    /*
     *  (non-Javadoc)
     * @see java.applet.Applet#stop()
     */
    public void stop() {
        System.out.println("Closing...");
        eAdventure.setGameOver();
        try {
            System.out.println("Trying to join...");
            gameThread.join();
            System.out.println("...join successful.");
            Game.delete();
            ResourceHandler.delete();
        } catch( InterruptedException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.destroy();
    }


}
