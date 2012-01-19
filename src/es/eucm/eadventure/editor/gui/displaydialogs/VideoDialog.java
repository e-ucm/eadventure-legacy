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
package es.eucm.eadventure.editor.gui.displaydialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.otherpanels.VideoPanel;

/**
 * This dialog plays a video file.
 * 
 * @author Javier Torrente
 */
public class VideoDialog extends JDialog {

    private VideoPanel videoPanel;

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param videoPath
     *            Path to the file to be played
     */
    public VideoDialog( String videoPath ) {

        super( Controller.getInstance( ).peekWindow( ), TC.get( "VideoDialog.Title", AssetsController.getFilename( videoPath ) ), Dialog.ModalityType.TOOLKIT_MODAL );

        // Add a video panel
        videoPanel = new VideoPanel( videoPath );
        if( !videoPanel.isError( ) )
            add( videoPanel );
        else {
            JPanel errorPanel = new JPanel( );
            JLabel error = new JLabel( TC.get( "Error.BadAudioFormat.Title" ) );
            errorPanel.add( error );
            add( errorPanel );
        }

        // Set the dialog properties
        setResizable( false );
        pack( );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        this.addWindowListener( new WindowAdapter( ) {

            @Override
            public void windowClosed( WindowEvent e ) {

                videoPanel.removeVideo( );

            }

            @Override
            public void windowClosing( WindowEvent e ) {

                videoPanel.removeVideo( );

            }

        } );

        // Show the dialog
        setVisible( true );
    }
}
