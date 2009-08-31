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
package es.eucm.eadventure.editor.gui.displaydialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.otherpanels.AudioPanel;
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
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "VideoDialog.Title", AssetsController.getFilename( videoPath ) ), Dialog.ModalityType.TOOLKIT_MODAL );

		// Add a video panel
		 videoPanel = new VideoPanel( videoPath );
		if (!videoPanel.isError())
			add( videoPanel );
		else{
			JPanel errorPanel = new JPanel();
			JLabel error = new JLabel(TextConstants.getText("Error.BadAudioFormat.Title"));
			errorPanel.add(error);
			add(errorPanel);
		}
			
		// Set the dialog properties
		setResizable( false );
		pack( );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		this.addWindowListener( new WindowAdapter(){

			public void windowClosed( WindowEvent e ) {
				videoPanel.removeVideo( );
				
			}

			public void windowClosing( WindowEvent e ) {
				videoPanel.removeVideo( );
				
			}
			
		});
		
		// Show the dialog
		setVisible( true );
	}
}
