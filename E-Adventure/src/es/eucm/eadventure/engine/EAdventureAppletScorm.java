package es.eucm.eadventure.engine;

import java.util.HashMap;

import javax.media.Codec;
import javax.media.Format;
import javax.media.PlugInManager;
import javax.media.format.VideoFormat;

import de.schlichtherle.io.ArchiveDetector;
import de.schlichtherle.io.DefaultArchiveDetector;
import de.schlichtherle.io.File;
import es.eucm.eadventure.comm.manager.commManager.CommManagerScormV12;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.core.gui.GUIApplet;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

public class EAdventureAppletScorm extends CommManagerScormV12{


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

        if (!windowed) {
	        GUI.setGUIType(GUI.GUI_APPLET);
	        GUIApplet.setApplet(this);
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
        eAdventure.setComm(this);
        //System.out.println("Init finished succesfully");
        //System.out.println( " CODE BASE="+this.getCodeBase( ).toString( ));
        
    }

    /*
     *  (non-Javadoc)
     * @see java.applet.Applet#start()
     */
    public void start( ) {

	this.connect(new HashMap<String, String>());
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
	this.disconnect(new HashMap<String,String>());
	try {
            System.out.println("Trying to join...");
            Game.delete();
            ResourceHandler.delete();
            gameThread.join();
            System.out.println("...join successful.");
            
        } catch( InterruptedException e ) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            
        }
        this.destroy();
    }
    
    public void dataFromLMS(String key, String value){
    	super.dataFromLMS(key, value);
    	System.out.println("Recivimos el dato "+ key + " " + value);
    }
	
}
