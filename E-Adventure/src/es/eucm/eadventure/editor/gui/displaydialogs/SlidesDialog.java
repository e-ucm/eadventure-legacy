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

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.common.gui.TextConstants;
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
        setTitle( TextConstants.getText( "SlidesDialog.Title", AssetsController.getFilename( slidesPath ) ) );
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
