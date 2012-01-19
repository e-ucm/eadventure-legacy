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

import javax.swing.JDialog;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.gui.TC;
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
        super( Controller.getInstance( ).peekWindow( ), TC.get( "AnimationDialog.Title", AssetsController.getFilename( animationPath ) ), Dialog.ModalityType.TOOLKIT_MODAL );

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
        super( Controller.getInstance( ).peekWindow( ), TC.get( "AnimationDialog.Title", animation.getId( ) ), Dialog.ModalityType.APPLICATION_MODAL );

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
