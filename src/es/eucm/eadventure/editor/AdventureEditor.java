package es.eucm.eadventure.editor;

import javax.media.Codec;
import javax.media.Format;
import javax.media.PlugInManager;
import javax.media.format.VideoFormat;

import de.schlichtherle.io.ArchiveDetector;
import de.schlichtherle.io.DefaultArchiveDetector;
import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.control.Controller;

public class AdventureEditor {

	/**
	 * @param args
	 */
	/*
	 * @updated by Javier Torrente. New functionalities added - Support for .ead files. Therefore <e-Adventure> files
	 * are no longer .zip but .ead
	 */

	public static void main( String[] args ) {
		de.schlichtherle.io.File.setDefaultArchiveDetector( new DefaultArchiveDetector( ArchiveDetector.NULL, // delegate
		new String[] {
		"jar", "de.schlichtherle.io.archive.zip.JarDriver",
		"ead", "de.schlichtherle.io.archive.zip.Zip32Driver", 
		"zip", "de.schlichtherle.io.archive.zip.Zip32Driver"} ) );

		try {
			Codec video = (Codec) Class.forName(
					"net.sourceforge.jffmpeg.VideoDecoder").newInstance();
			PlugInManager.addPlugIn("net.sourceforge.jffmpeg.VideoDecoder",
					video.getSupportedInputFormats(),
					new Format[] { new VideoFormat(VideoFormat.MPEG) },
					PlugInManager.CODEC);
		} catch (Exception e) {
		}

		try{
			Controller controller = Controller.getInstance( );
			if (args.length>0)
				controller.init( args[0] );
			else
				controller.init( null );
				
		}catch( Exception e){
			ReportDialog.GenerateErrorReport(e, true, "Error in main AdventureEditor");
		}
	}
}
