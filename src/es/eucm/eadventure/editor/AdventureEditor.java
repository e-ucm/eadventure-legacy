/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor;

//Prueba

import javax.media.Codec;
import javax.media.Format;
import javax.media.PlugInManager;
import javax.media.format.VideoFormat;

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

        
        try {
            Codec video = (Codec) Class.forName( "net.sourceforge.jffmpeg.VideoDecoder" ).newInstance( );
            PlugInManager.addPlugIn( "net.sourceforge.jffmpeg.VideoDecoder", video.getSupportedInputFormats( ), new Format[] { new VideoFormat( VideoFormat.MPEG ) }, PlugInManager.CODEC );
        }
        catch( Exception e ) {
        }

        try {
            Controller controller = Controller.getInstance( );
            if( args.length > 0 )
                controller.init( args[0] );
            else
                controller.init( null );

        }
        catch( Exception e ) {
            ReportDialog.GenerateErrorReport( e, true, "Error in main AdventureEditor" );
        }
    }
}
