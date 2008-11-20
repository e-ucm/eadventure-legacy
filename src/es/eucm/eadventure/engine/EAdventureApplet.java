package es.eucm.eadventure.engine;
import javax.media.Codec;
import javax.media.Format;
import javax.media.PlugInManager;
import javax.media.format.VideoFormat;

import de.schlichtherle.io.ArchiveDetector;
import de.schlichtherle.io.DefaultArchiveDetector;
import de.schlichtherle.io.File;
import es.eucm.eadventure.engine.comm.AsynchronousCommunicationAppletLD;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * This is the main class, when run in a browser as an applet. Creates a new game and runs it.
 */
/**
 * @updated by Javier Torrente. New functionalities added
 * - Support for .ead files. Therefore <e-Adventure> files are no longer .zip but .ead
 */

public class EAdventureApplet extends AsynchronousCommunicationAppletLD {

	private static final String CONFIG_FILE = "config_engine.xml";
	
	private static final String LANGUAGE_DIR = "lanengine";

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
        TextConstants.loadStrings( EAdventureApplet.class.getResourceAsStream( "/en_EN.xml" ) );
        File.setDefaultArchiveDetector(new DefaultArchiveDetector(
                ArchiveDetector.NULL, // delegate
                new String[] {
                    //"ead", "de.schlichtherle.io.archive.zip.JarDriver",
                    "ead", "de.schlichtherle.io.archive.zip.Zip32Driver",
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
        String adventureName = "integration";
        this.readParameters();

        ResourceHandler.setRestrictedMode( true );
        ResourceHandler.getInstance( ).setZipFile( adventureName + ".zip" );
        Game.create( );
        eAdventure = Game.getInstance( );
        eAdventure.setAdventureName( adventureName );
        eAdventure.setComm(this);
        //System.out.println("Init finished succesfully");
        //System.out.println( " CODE BASE="+this.getCodeBase( ).toString( ));
        
    }

    /*
     *  (non-Javadoc)
     * @see java.applet.Applet#start()
     */
    public void start( ) {
        this.startCommunication("");
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
