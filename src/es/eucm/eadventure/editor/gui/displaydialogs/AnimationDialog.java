/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.gui.displaydialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.common.loader.Loader;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.EditorImageLoader;
import es.eucm.eadventure.editor.gui.otherpanels.AnimationPanel;

/**
 * This class plays an animation showing it inside a dialog.
 * 
 * @author Bruno Torijano Bueno
 */
public class AnimationDialog extends JDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new animation dialog with the given path.
     * 
     * @param animationPath
     *            Path of the animation
     */
    public AnimationDialog( String animationPath ) {

        // Call to the JDialog constructor
        super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "AnimationDialog.Title", AssetsController.getFilename( animationPath ) ), Dialog.ModalityType.TOOLKIT_MODAL );

        if( animationPath.endsWith( ".eaa" ) ) {
            add( new AnimationPanel( true, Loader.loadAnimation( AssetsController.getInputStreamCreator( ), animationPath, new EditorImageLoader() ) ) );
        }
        else {
            // Create and add the animation panel (a PNG suffix is attached to the path)
            add( new AnimationPanel( true, animationPath + "_01.png" ) );
        }
        // Set the dialog properties
        setMinimumSize( new Dimension( 400, 300 ) );
        setSize( 500, 380 );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );

        // Show the dialog
        setVisible( true );
    }

    /**
     * Creates a new animation dialog with the given path.
     * 
     * @param animationPath
     *            Path of the animation
     */
    public AnimationDialog( Animation animation ) {

        // Call to the JDialog constructor
        super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "AnimationDialog.Title", animation.getId( ) ), Dialog.ModalityType.APPLICATION_MODAL );

        // Create and add the animation panel (a PNG suffix is attached to the path)
        add( new AnimationPanel( true, animation ) );

        // Set the dialog properties
        setMinimumSize( new Dimension( 400, 300 ) );
        setSize( 500, 380 );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );

        // Show the dialog
        setVisible( true );
    }

}
