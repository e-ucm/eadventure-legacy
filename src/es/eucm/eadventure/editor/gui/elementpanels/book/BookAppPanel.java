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
package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResourcesPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public class BookAppPanel extends JPanel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Controller of the book.
     */
    private BookDataControl bookDataControl;

    private LooksPanel bookLooks;

    /**
     * Constructor.
     * 
     * @param bookDataControl
     *            Book controller
     */
    public BookAppPanel( BookDataControl bookDataControl ) {

        // Set the controller
        this.bookDataControl = bookDataControl;

        // Create the list of resources
        String[] resourcesArray = new String[ bookDataControl.getResourcesCount( ) ];
        for( int i = 0; i < bookDataControl.getResourcesCount( ); i++ )
            resourcesArray[i] = TC.get( "ResourcesList.ResourcesBlockNumber" ) + ( i + 1 );

        // Set the layout
        setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 5, 5, 5, 5 );

        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 3;

        this.bookLooks = new BookLooksPanel( this.bookDataControl );
        this.add( bookLooks, c );
    }

    private class BookLooksPanel extends LooksPanel {

        /**
         * 
         */
        private static final long serialVersionUID = 1L; 

        /**
         * Preview image panel.
         */
        private BookImagePanel imagePanel;

        public BookLooksPanel( DataControlWithResources control ) {

            super( control );

        }       

        @Override
        protected void createPreview( ) {

            String bookImagePath = bookDataControl.getPreviewImage( );
            String arrowLeftNormalImagePath = bookDataControl.getArrowImagePath( BookDataControl.ARROW_LEFT, BookDataControl.ARROW_NORMAL );
            String arrowRightNormalImagePath = bookDataControl.getArrowImagePath( BookDataControl.ARROW_RIGHT, BookDataControl.ARROW_NORMAL );
    
            JPanel previewPanel = new JPanel( );
            previewPanel.setLayout( new BorderLayout( ) );
            
            imagePanel = new BookImagePanel( bookImagePath, arrowLeftNormalImagePath, arrowRightNormalImagePath  );
            
            previewPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Book.Preview" ) ) );
            previewPanel.add( imagePanel, BorderLayout.CENTER );
            lookPanel.add( previewPanel, cLook );
            // TODO Parche, arreglar
            lookPanel.setPreferredSize( new Dimension( 0, 90 ) );
        }

        @Override
        public void updatePreview( ) {

            imagePanel.loadImage( bookDataControl.getPreviewImage( ) );
            imagePanel.loadArrowImages( bookDataControl.getArrowImagePath( BookDataControl.ARROW_LEFT, BookDataControl.ARROW_NORMAL ), bookDataControl.getArrowImagePath( BookDataControl.ARROW_RIGHT, BookDataControl.ARROW_NORMAL ) );
            imagePanel.repaint( );
            if( getParent( ) != null && getParent( ).getParent( ) != null )
                getParent( ).getParent( ).repaint( );
        }

        @Override
        public void updateResources( ) {

            super.updateResources( );
            reorganizeResourcesPanel( resourcesPanel );
            if( getParent( ) != null && getParent( ).getParent( ) != null )
                getParent( ).getParent( ).repaint( );
        }
        
        private void reorganizeResourcesPanel(ResourcesPanel r){
            
            Component components[] = r.getComponents( );
            r.removeAll( );
            GridBagConstraints c = new GridBagConstraints( );
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets( 2, 2, 2, 2 );
            c.weightx = 1;
            c.weighty = 0;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            c.gridheight = 1;
            
            r.add( components[0], c );
            
            int k = 1;
            c.gridwidth = 1;
            c.weightx = 0.5;
            for(int i = 1; i < 3; i++)
                for(int j = 0; j < 2; j++){
                    c.gridx = j;
                    c.gridy = i;
                    r.add( components[k], c );
                    k++;
                }
         }
        
        private class BookImagePanel extends ImagePanel {

            /**
             * Required
             */
            private static final long serialVersionUID = 1L;
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
            public BookImagePanel ( String imagePath, String arrowLeftNormalPath, String arrowRightNormalPath ){
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
                arrowRightNormalImage = getScaledImage( arrowLeftNormalImage, -1.0f, 1.0f );
                
              }
              // If we have onlye right arrow, we use the mirror image for the left arrow
              else if ( arrowLeftNormalPath == null && arrowRightNormalPath != null ){
                  arrowRightNormalImage = AssetsController.getImage( arrowRightNormalPath );
                  arrowLeftNormalImage =  getScaledImage( arrowRightNormalImage, -1.0f, 1.0f );
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
            
            private Image getScaledImage( Image image, float x, float y ) {

                Image newImage = null;

                if( image != null ) {

                    // set up the transform
                    AffineTransform transform = new AffineTransform( );
                    transform.scale( x, y );
                    transform.translate( ( x - 1 ) * image.getWidth( null ) / 2, ( y - 1 ) * image.getHeight( null ) / 2 );

                    // create a transparent (not translucent) image
                    newImage = new BufferedImage( image.getWidth( null ), image.getHeight( null ), Transparency.BITMASK );

                    // draw the transformed image
                    Graphics2D g = (Graphics2D) newImage.getGraphics( );

                    g.drawImage( image, transform, null );
                    g.dispose( );
                }

                return newImage;
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

    }

}
