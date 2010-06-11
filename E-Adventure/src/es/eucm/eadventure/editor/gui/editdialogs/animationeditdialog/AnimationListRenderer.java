/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.editdialogs.animationeditdialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import es.eucm.eadventure.common.data.animation.Transition;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.animation.FrameDataControl;
import es.eucm.eadventure.editor.control.controllers.animation.TransitionDataControl;

/**
 * Class that is responsible for creating the container for each type of cell in
 * the list
 */
public class AnimationListRenderer implements ListCellRenderer {

    private static final long serialVersionUID = 1L;

    private HashMap<String, Image> chache;
    
    private ImageIcon soundIcon;
    
    private ImageIcon noImage;
    
    private ImageIcon[] transitions;
    
    public AnimationListRenderer( ) {
        super( );
        chache = new HashMap<String, Image>();
        soundIcon = new ImageIcon( "img/icons/hasSound.png" );
        noImage = new ImageIcon( "img/icons/noImageFrame.png" );
        transitions = new ImageIcon[5];
        transitions[Transition.TYPE_NONE] = new ImageIcon( "img/icons/transitionNone.png" );
        transitions[Transition.TYPE_FADEIN] = new ImageIcon( "img/icons/transitionFadein.png" );
        transitions[Transition.TYPE_HORIZONTAL] = new ImageIcon( "img/icons/transitionHorizontal.png" );
        transitions[Transition.TYPE_VERTICAL] = new ImageIcon( "img/icons/transitionVertical.png" );
    }

    public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
        JPanel panel = new JPanel( );
        panel.setLayout( new BorderLayout( ) );

        if( isSelected ) {
            panel.setBackground( list.getSelectionBackground( ) );
            panel.setForeground( list.getSelectionForeground( ) );
        }
        else {
            panel.setBackground( list.getBackground( ) );
            panel.setForeground( list.getForeground( ) );
        }

        if( value instanceof FrameDataControl ) {
            panel.setBorder( BorderFactory.createLineBorder( Color.BLACK, 3 ) );
            FrameDataControl f = (FrameDataControl) value;

            JLabel temp = new JLabel( );

            temp.setIcon( getImage(f.getImageURI( ), f.getSoundUri( )) );

            panel.add( temp, BorderLayout.CENTER );

            temp = new JLabel( "" + f.getTime( ) );
            temp.setHorizontalAlignment( SwingConstants.CENTER );
            panel.add( temp, BorderLayout.SOUTH );
        }
        else if( value instanceof TransitionDataControl ) {
            JLabel temp = new JLabel( );
            temp.setHorizontalAlignment( SwingConstants.CENTER );
            temp.setVerticalAlignment( SwingConstants.CENTER );
            ImageIcon icon;
            TransitionDataControl t = (TransitionDataControl) value;
            icon = transitions[t.getType( )];
            temp.setIcon( icon );

            panel.add( temp, BorderLayout.CENTER );

            temp = new JLabel( "" + t.getTime( ) );
            temp.setHorizontalAlignment( SwingConstants.CENTER );
            panel.add( temp, BorderLayout.SOUTH );
        }
        return panel;
    }
    
    private ImageIcon getImage(String imageURI, String soundURI) {
        Image image = null;
        ImageIcon icon;
        if( imageURI != null && imageURI.length( ) > 0 ) {
            image = chache.get( imageURI );
            if (image == null) {
                Image tempImage = AssetsController.getImage( imageURI );
                if (tempImage != null) {
                    tempImage = tempImage.getScaledInstance( 100, -1, Image.SCALE_SMOOTH );
                    if (tempImage.getHeight( null ) > 100)
                        tempImage = tempImage.getScaledInstance( -1, 100, Image.SCALE_SMOOTH );
                }
                image = new BufferedImage(tempImage.getWidth( null ), tempImage.getHeight( null ), BufferedImage.TYPE_INT_RGB);
                Graphics g = image.getGraphics( );
                g.setColor( Color.WHITE );
                g.fillRect( 0, 0, image.getWidth( null ), image.getHeight( null ) );
                g.drawImage( tempImage, 0, 0, null );
                chache.put( imageURI, image );
                tempImage = null;
            }
            if( soundURI != null && !soundURI.equals("") && image != null ) {
                Image tempImage = new BufferedImage( image.getWidth( null ), image.getHeight( null ), BufferedImage.TYPE_INT_RGB);
                Graphics g = tempImage.getGraphics( );
                g.setColor( Color.WHITE );
                g.fillRect( 0, 0, image.getWidth( null ), image.getHeight( null ) );
                g.drawImage( image, 0, 0, null );
                g.drawImage( soundIcon.getImage( ), 0, 0, null );
                image = tempImage;
            }
        }

        if( image == null )
            icon = noImage;
        else
            icon = new ImageIcon(image);
        return icon;
    }

}
