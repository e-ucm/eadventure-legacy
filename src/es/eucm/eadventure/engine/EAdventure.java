package es.eucm.eadventure.engine;

import javax.media.Codec;
import javax.media.Format;
import javax.media.PlugInManager;
import javax.media.format.VideoFormat;

import de.schlichtherle.io.ArchiveDetector;
import de.schlichtherle.io.DefaultArchiveDetector;
import de.schlichtherle.io.File;
import es.eucm.eadventure.engine.core.control.config.ConfigData;
import es.eucm.eadventure.common.auxiliar.ReleaseFolders;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.engine.gamelauncher.GameLauncher;

import javax.swing.*;

/**
 * This is the main class, when run standalone. Creates a new game and runs it.
 * 
 */
/**
 * @updated by Javier Torrente. New functionalities added
 * - Support for .ead files. Therefore <e-Adventure> files are no longer .zip but .ead
 *  @updated by Enrique López. Functionalities added (10/2008)
 * - Multilanguage support. Two new classes added
 */
public class EAdventure {
    
	public static String VERSION = "1.0b";
	
	public static int languageFile=ReleaseFolders.LANGUAGE_UNKNOWN;
	
	/**
	 * Sets the current language of the editor. Accepted values are {@value #LANGUAGE_ENGLISH} & {@value #LANGUAGE_ENGLISH}.
	 * This method automatically updates the about, language strings, and loading image parameters. 
	 * 
	 * The method will reload the main window if reloadData is true
	 * @param language
	 */
	public static void setLanguage ( int language ){
		if (language == ReleaseFolders.LANGUAGE_SPANISH && languageFile!=ReleaseFolders.LANGUAGE_SPANISH ||
				language == ReleaseFolders.LANGUAGE_ENGLISH && languageFile!=ReleaseFolders.LANGUAGE_ENGLISH){
			ConfigData.setLanguangeFile( 
					ReleaseFolders.getLanguageFilePath(language), 
					ReleaseFolders.getAboutFilePath(language));
			languageFile = language;
			TextConstants.loadStrings( ReleaseFolders.getLanguageFilePath4Engine(languageFile) );
		}
	}
	
	/**
     * Launchs a new e-Adventure game
     * @param args Arguments
     */
    public static void main( String[] args ) {

        // Load the configuration
        ConfigData.loadFromXML( ReleaseFolders.configFileEngineRelativePath() );
        
        if (args.length >= 2){
        	setLanguage(ReleaseFolders.getLanguageFromPath(args[1]));
        } else
        	setLanguage(ReleaseFolders.getLanguageFromPath(ConfigData.getLanguangeFile( )));
        

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

        // Set the Look&Feel
        try {
            javax.swing.UIManager.setLookAndFeel( javax.swing.UIManager.getSystemLookAndFeelClassName( ) );
        } catch( Exception e ) {
            e.printStackTrace( );
        }
       
        GameLauncher gameLauncher = new GameLauncher( );
        
        File file = new File("");
        if (args.length >= 1){
        	file = new File(args[0]);
        	if (!args[0].equals("") && !file.exists()){
        		JOptionPane.showMessageDialog(null,
                        TextConstants.getText("ErrorMessage.Title"),
                        TextConstants.getText("ErrorMessage.Content"),
                        JOptionPane.ERROR_MESSAGE);
        		file = new File("");
        	}
        } 

        
        gameLauncher.init(file);
        /*if (args.length == 0) 
            gameLauncher.init( new File( "" ), "" );
        else {
        	if ( !args[0].equals("")) {
        		
        		File file = new File(args[0]);
        	 
        		if (file.exists( )){
        			gameLauncher.init( new File(args[0]),args[1] );
          
        		} else {
                
        			JOptionPane.showMessageDialog(null,
                        TextConstants.getText("ErrorMessage.Title"),
                        TextConstants.getText("ErrorMessage.Content"),
                        JOptionPane.ERROR_MESSAGE);
                
                gameLauncher.init(new File(""), "");
            }
        	}
        	else 
        		gameLauncher.init( new File( "" ), args[1] );
        	}*/
        new Thread( gameLauncher ).start( );

    }
}
