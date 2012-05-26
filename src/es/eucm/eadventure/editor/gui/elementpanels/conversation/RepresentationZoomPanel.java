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
package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.gui.TC;

public class RepresentationZoomPanel extends JPanel {

    private static final long serialVersionUID = -205324436088837002L;

    private RepresentationPanel representationPanel;

    public RepresentationZoomPanel( RepresentationPanel representationPanel2 ) {

        this.representationPanel = representationPanel2;

        Icon zoomOutIcon = new ImageIcon( "img/icons/zoomout.png" );
        JButton zoomout = new JButton( zoomOutIcon );
        zoomout.setPreferredSize( new Dimension( 20, 20 ) );
        zoomout.setContentAreaFilled( false );
        zoomout.setToolTipText( TC.get( "DrawPanel.ZoomOut" ) );
        zoomout.setFocusable( false );
        zoomout.setMargin( new Insets( 0, 0, 0, 0 ) );
        zoomout.setBorder( BorderFactory.createEmptyBorder( ) );
        add( zoomout );

        final JSlider slider = new JSlider( 1, 10 );
        slider.setValue( 10 );
        slider.addChangeListener( new ChangeListener( ) {

            public void stateChanged( ChangeEvent arg0 ) {

                float zoom = slider.getValue( ) / 10.0f;
                representationPanel.setScale( zoom );
                representationPanel.updateRepresentation( );
            }
        } );
        slider.setToolTipText( TC.get( "DrawPanel.ZoomSlider" ) );
        slider.setFocusable( false );
        add( slider );

        Icon zoomInIcon = new ImageIcon( "img/icons/zoomin.png" );
        JButton zoomin = new JButton( zoomInIcon );
        zoomin.setPreferredSize( new Dimension( 20, 20 ) );
        zoomin.setContentAreaFilled( false );
        zoomin.setToolTipText( TC.get( "DrawPanel.ZoomIn" ) );
        zoomin.setFocusable( false );
        zoomin.setMargin( new Insets( 0, 0, 0, 0 ) );
        zoomin.setBorder( BorderFactory.createEmptyBorder( ) );
        add( zoomin );

        zoomin.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                slider.setValue( slider.getValue( ) + 1 );
            }
        } );
        zoomout.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                slider.setValue( slider.getValue( ) - 1 );
            }
        } );
    }

}
