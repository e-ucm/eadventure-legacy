package es.eucm.eadventure.engine;

import javax.media.Codec;
import javax.media.Format;
import javax.media.PlugInManager;
import javax.media.format.VideoFormat;

import de.schlichtherle.io.ArchiveDetector;
import de.schlichtherle.io.DefaultArchiveDetector;
import de.schlichtherle.io.File;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * This is the main class, when run standalone. Creates a new game and runs it.
 * 
 */
/**
 * @updated by Javier Torrente. New functionalities added
 * - Support for .ead files. Therefore <e-Adventure> files are no longer .zip but .ead
 */
public class EAdventureStandalone {
	
    private static long startTime;
    
    public static int getElapsedTime(){
        return (int)(((double)(System.currentTimeMillis( )-startTime))/1000.0d);
    }
    
    public static void printElapsedTime( String message ){
        //System.out.println( "["+getElapsedTime()+"] "+message );
    }
    /**
     * Launchs a new e-Adventure game
     * @param args Arguments
     */
    public static void main( String[] args ) {
    	TextConstants.loadStrings( EAdventureApplet.class.getResourceAsStream( "/lanengine/en_EN.xml" ) );
        startTime = System.currentTimeMillis( ); 
        printElapsedTime ("Starting");
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
        printElapsedTime ("Zip library stuff completed");
        
        // Set the Look&Feel
        try {
            javax.swing.UIManager.setLookAndFeel( javax.swing.UIManager.getSystemLookAndFeelClassName( ) );
        } catch( Exception e ) {
            e.printStackTrace( );
        }
        printElapsedTime ("Look And Feel completed");
        String adventureName = "integration";
        
        ResourceHandler.setRestrictedMode( true, false );
        printElapsedTime ("Restricted mode completed");
        ResourceHandler.getInstance( ).setZipFile( adventureName + ".zip" );
        printElapsedTime ("Zip file set");
        Game.create( );
        printElapsedTime ("Game created");
        Game.getInstance( ).setAdventureName( adventureName );
        printElapsedTime ("Adventure name set. Starting execution of the game");
        Game.getInstance( ).run( );
        Game.delete( );
        ResourceHandler.getInstance( ).closeZipFile( );
        ResourceHandler.delete( );
        System.exit( 0 );

    }
}
