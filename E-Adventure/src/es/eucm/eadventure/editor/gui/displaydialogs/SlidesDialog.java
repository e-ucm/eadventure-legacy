/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.displaydialogs;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.AssetsController;

/**
 * This class plays a set of slides, the slide can change with a mouse click in
 * the dialog.
 * 
 * @author Bruno Torijano Bueno
 */
public class SlidesDialog extends GraphicDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * List of slides.
     */
    private Image[] slides;

    /**
     * Index of the current slide.
     */
    private int currentSlideIndex;

    /**
     * Creates a new animation dialog with the given path.
     * 
     * @param slidesPath
     *            Path of the animation, without suffix ("_01.jpg")
     */
    public SlidesDialog( String slidesPath ) {

        // Load the slides
        slides = AssetsController.getAnimation( slidesPath + "_01.jpg" );

        // Add a mouse listener to the glass pane to increase the selected index
        getGlassPane( ).setVisible( true );
        getGlassPane( ).addMouseListener( new MouseAdapter( ) {

            @Override
            public void mouseClicked( MouseEvent e ) {

                currentSlideIndex = ( currentSlideIndex + 1 ) % slides.length;
                repaint( );
            }
        } );

        // Set the title and show the dialog
        setTitle( TC.get( "SlidesDialog.Title", AssetsController.getFilename( slidesPath ) ) );
        setVisible( true );
    }

    @Override
    protected Image getCurrentImage( ) {

        return slides[currentSlideIndex];
    }

    @Override
    protected double getCurrentImageRatio( ) {

        // In the slides, the ratio is always 4:3
        return 1.333d;
    }

    @Override
    protected void deleteImages( ) {

        // Flush all slides from the set
        for( Image slide : slides )
            slide.flush( );
    }
}
