/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 * research group.
 * 
 * Copyright 2005-2010 <e-UCM> research group.
 * 
 * You can access a list of all the contributors to <e-Adventure> at:
 * http://e-adventure.e-ucm.es/contributors
 * 
 * <e-UCM> is a research group of the Department of Software Engineering and
 * Artificial Intelligence at the Complutense University of Madrid (School of
 * Computer Science).
 * 
 * C Profesor Jose Garcia Santesmases sn, 28040 Madrid (Madrid), Spain.
 * 
 * For more info please visit: <http://e-adventure.e-ucm.es> or
 * <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 * <e-Adventure> is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with <e-Adventure>. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.editor.gui.displaydialogs;

import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.imageedition.SelectImageController;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementSelectImage;

public class SelectImageDialog extends GraphicDialog {

    /**
     * 
     */
    private static final long serialVersionUID = -6270014367469640519L;

    private BufferedImage image;

    private String path;

    private ImageElementSelectImage selectImage;

    public SelectImageDialog( String imagePath ) {

        this.path = imagePath;

        GridBagConstraints c = new GridBagConstraints( );
        c.gridx = 0;
        c.gridy = 1;
        this.add( new JLabel( TC.get( "SelectImageDialog.Description" ) ), c );

        c.gridy++;
        // Load the image
        image = (BufferedImage) AssetsController.getImage( imagePath );

        setSize( image.getWidth( ) > 800 ? 800 : image.getWidth( ), image.getHeight( ) > 600 ? 600 : image.getHeight( ) );

        // Set the dialog and show it
        setTitle( TC.get( "SelectImageDialog.Title", AssetsController.getFilename( imagePath ) ) );

        selectImage = new ImageElementSelectImage( image, imagePath );

        SelectImageController controller = new SelectImageController( selectImage, this );
        imagePanel.addMouseListener( controller );
        imagePanel.addMouseMotionListener( controller );

        JPanel bottomPanel = new JPanel( );

        JButton automaticButton = new JButton( TC.get( "SelectImageDialog.AutomaticButton" ) );
        automaticButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                rescaleImage( );
                setVisible( false );
                deleteImages( );
                dispose( );

            }
        } );
        bottomPanel.add( automaticButton );
        JButton okButton = new JButton( TC.get( "SelectImageDialog.SaveButton" ) );
        okButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                saveImage( );
                setVisible( false );
                deleteImages( );
                dispose( );

            }
        } );
        bottomPanel.add( okButton );

        //if you close the windows, automatic rescale
        addWindowListener( new WindowAdapter( ) {

            @Override
            public void windowClosing( WindowEvent e ) {

                rescaleImage( );
                setVisible( false );
                deleteImages( );
                dispose( );
            }
        } );

        c.gridy++;
        this.add( bottomPanel, c );

        this.setVisible( true );

    }

    protected void saveImage( ) {

        //reload the image
        BufferedImage tempImage = (BufferedImage) AssetsController.getImage( path );
        image.getGraphics( ).drawImage( tempImage, 0, 0, null );
        BufferedImage newImage = null;

        // only cut a image of 800 x 600 px
        if( ( selectImage.getWidth( ) == 800 ) && ( selectImage.getHeight( ) == 600 ) ) {
            newImage = image.getSubimage( selectImage.getX( ), selectImage.getY( ), selectImage.getWidth( ), selectImage.getHeight( ) );
        }// cut and rescale any image
        else if( selectImage.getHeight( ) > 600 ) {
            newImage = image.getSubimage( selectImage.getX( ), selectImage.getY( ), selectImage.getWidth( ), selectImage.getHeight( ) );
            //scale the image to 800x600
            Image tempImage2 = newImage.getScaledInstance( 800, 600, 1 );
            image.getGraphics( ).drawImage( tempImage2, 0, 0, null );
            newImage = image.getSubimage( 0, 0, 800, 600 );
        }// cut a image of  (width x 600px) 
        else if( selectImage.getHeight( ) == 600 && selectImage.getWidth( ) > 800 ) {
            newImage = image.getSubimage( selectImage.getX( ), selectImage.getY( ), selectImage.getWidth( ), selectImage.getHeight( ) );
        }

        String ext = AssetsController.getFilename( path );
        ext = ext.substring( ext.lastIndexOf( "." ) + 1, ext.length( ) );
        File f = new File( Controller.getInstance( ).getProjectFolder( ), path );
        try {
            ImageIO.write( newImage, ext, f );
        }
        catch( IOException e1 ) {
            e1.printStackTrace( );
        }

    }

    protected void rescaleImage( ) {

        //scale the image to 800x600
        Image tempImage = ( AssetsController.getImage( path ).getScaledInstance( 800, 600, 1 ) );
        image.getGraphics( ).drawImage( tempImage, 0, 0, null );

        BufferedImage newImage = image.getSubimage( 0, 0, 800, 600 );

        String ext = AssetsController.getFilename( path );
        ext = ext.substring( ext.lastIndexOf( "." ) + 1, ext.length( ) );
        File f = new File( Controller.getInstance( ).getProjectFolder( ), path );
        try {
            ImageIO.write( newImage, ext, f );
        }
        catch( IOException e1 ) {
            e1.printStackTrace( );
        }
    }

    @Override
    protected void deleteImages( ) {

        if( image != null )
            image.flush( );

    }

    @Override
    protected Image getCurrentImage( ) {

        return image;
    }

    @Override
    public void repaint( ) {

        super.repaint( );
        this.selectImage.updateMargin( super.imagePanel.getMarginX( ), super.imagePanel.getMarginY( ) );
        this.selectImage.updateSize( super.imagePanel.getWidth( ), super.imagePanel.getHeight( ) );
    }

}
