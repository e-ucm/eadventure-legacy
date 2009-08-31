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

import java.awt.Image;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.AssetsController;

/**
 * This class displays an image.
 * 
 * @author Bruno Torijano Bueno
 */
public class ImageDialog extends GraphicDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Image to dosplay.
	 */
	private Image image;

	/**
	 * Creates a new image dialog with the given path.
	 * 
	 * @param imagePath
	 *            Path and extension of the image
	 */
	public ImageDialog( String imagePath ) {
		// Load the image
		image = AssetsController.getImage( imagePath );

		// Set the dialog and show it
		setTitle( TextConstants.getText( "ImageDialog.Title", AssetsController.getFilename( imagePath ) ) );
		setVisible( true );
	}

	@Override
	protected Image getCurrentImage( ) {
		return image;
	}

	@Override
	protected void deleteImages( ) {
		if (image!=null)
		image.flush( );
	}
}
