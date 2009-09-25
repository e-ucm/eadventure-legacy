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
