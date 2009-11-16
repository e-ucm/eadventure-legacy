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
package es.eucm.eadventure.editor.gui.otherpanels.imagepanels;

import java.awt.Graphics;
import java.awt.Image;

import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.gui.auxiliar.ImageTransformer;

/**
 * Panel for the preview image of the book, including
 * arrows
 * @author Ángel Serrano
 *
 */
public class BookPreviewImagePanel extends ImagePanel {
    /**
     * Required
     */
    private static final long serialVersionUID = 4194460546059811772L;

    private static final int MARGIN = 40;
    
    /**
     * Vars for images for arrows.
     */
    private Image arrowLeftNormalImage,
                  arrowRightNormalImage;
    
    /**
     * Vars for relative positions of the arrows
     */
    private int xLeft, xRight, yLeft, yRight;
    
    /**
     * Constructor.
     * @param imagePath Path of background image
     * @param arrowLeftNormalPath Path of image for the left normal arrow
     * @param arrowRightNormalPath Path of image for the right normal arrow
     */
    public BookPreviewImagePanel ( String imagePath, String arrowLeftNormalPath, String arrowRightNormalPath ){
        super( imagePath );
        
        loadArrowImages( arrowLeftNormalPath, arrowRightNormalPath );
    }
    
    /**
     * Load arrow images.
     * @param arrowLeftNormalPath Path of image for the left normal arrow
     * @param arrowRightNormalPath Path of image for the right normal arrow
     */
    public void loadArrowImages( String arrowLeftNormalPath, String arrowRightNormalPath ){
        
      // If we have only left arrow, we use the mirror image for the right arrow
      if ( arrowLeftNormalPath != null && arrowRightNormalPath == null ){
      
        arrowLeftNormalImage = AssetsController.getImage( arrowLeftNormalPath );
        arrowRightNormalImage = ImageTransformer.getInstance().getScaledImage( arrowLeftNormalImage, -1.0f, 1.0f );
        
      }
      // If we have only right arrow, we use the mirror image for the left arrow
      else if ( arrowLeftNormalPath == null && arrowRightNormalPath != null ){
          arrowRightNormalImage = AssetsController.getImage( arrowRightNormalPath );
          arrowLeftNormalImage =  ImageTransformer.getInstance( ).getScaledImage( arrowRightNormalImage, -1.0f, 1.0f );
      }
      // If we have both arrows, we load them
      else if ( arrowLeftNormalPath != null && arrowRightNormalPath != null ){
          arrowLeftNormalImage = AssetsController.getImage( arrowLeftNormalPath );
          arrowRightNormalImage = AssetsController.getImage( arrowRightNormalPath );
      }
      // If we have none
      else {
          arrowLeftNormalImage = null;
          arrowRightNormalImage = null;
      }

    }
    
    /**
     * 
     * @return If image arrows are loaded.
     */
    public boolean isArrowsLoaded(){
        return ( arrowLeftNormalImage != null ) && ( arrowRightNormalImage != null );
    }
    
    @Override
    public void paint( Graphics g ){
        super.paint( g );
        
        if (super.isImageLoaded( ) && isArrowsLoaded( )){                  
            int widthRight = getAbsoluteWidth( arrowRightNormalImage.getWidth( null ) );
            int widthLeft = getAbsoluteWidth( arrowLeftNormalImage.getWidth( null ) );
            int heightRight = getAbsoluteHeight( arrowRightNormalImage.getHeight( null ) );
            int heightLeft = getAbsoluteWidth( arrowLeftNormalImage.getHeight( null ) );
            
            int margin = getAbsoluteWidth( MARGIN );
            
            xLeft = x + margin;
            xRight = x + width - widthRight - margin; 
            
            yLeft =  y + height - heightLeft - margin;
            yRight = y + height - heightRight - margin;
            
            g.drawImage( arrowLeftNormalImage, xLeft, yLeft, widthLeft, heightLeft, null ); 
            g.drawImage( arrowRightNormalImage, xRight, yRight, widthRight, heightRight, null );
        }
    }

}
