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
package es.eucm.eadventure.editor.gui.editdialogs.animationeditdialog;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.data.animation.Transition;
import es.eucm.eadventure.editor.control.controllers.animation.TransitionDataControl;

public class TransitionConfigPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private TransitionDataControl transition;

    private JList list;

    private JComboBox comboBox;

    private JSpinner spinner;

    public TransitionConfigPanel( TransitionDataControl transitionDataControl, JList list ) {

        this.transition = transitionDataControl;
        this.list = list;
        this.setLayout( new GridLayout( 1, 1 ) );

        JPanel temp2 = new JPanel( );
        temp2.add( new JLabel( "Duration" + ": " ) );
        SpinnerModel sm = new SpinnerNumberModel( transitionDataControl.getTime( ), 0, 10000, 100 );
        spinner = new JSpinner( sm );
        spinner.addChangeListener( new ChangeListener( ) {

            public void stateChanged( ChangeEvent arg0 ) {

                modifyTransitionTime( );
            }
        } );
        temp2.add( spinner );
        this.add( temp2 );

        JPanel temp = new JPanel( );
        comboBox = new JComboBox( );
        comboBox.addItem( "NONE" );
        comboBox.addItem( "FADEIN" );
        comboBox.addItem( "HORIZONTAL" );
        comboBox.addItem( "VERTICAL" );

        switch( transitionDataControl.getType( ) ) {
            case Transition.TYPE_NONE:
                comboBox.setSelectedIndex( 0 );
                break;
            case Transition.TYPE_FADEIN:
                comboBox.setSelectedIndex( 1 );
                break;
            case Transition.TYPE_HORIZONTAL:
                comboBox.setSelectedIndex( 2 );
                break;
            case Transition.TYPE_VERTICAL:
                comboBox.setSelectedIndex( 3 );
                break;
        }

        comboBox.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                modifyTransition( );
            }
        } );
        temp.add( comboBox );
        this.add( temp );
    }

    protected void modifyTransitionTime( ) {

        transition.setTime( ( (Double) spinner.getModel( ).getValue( ) ).longValue( ) );
        list.updateUI( );
    }

    protected void modifyTransition( ) {

        switch( comboBox.getSelectedIndex( ) ) {
            case 0:
                transition.setType( Transition.TYPE_NONE );
                break;
            case 1:
                transition.setType( Transition.TYPE_FADEIN );
                break;
            case 2:
                transition.setType( Transition.TYPE_HORIZONTAL );
                break;
            case 3:
                transition.setType( Transition.TYPE_VERTICAL );
                break;
        }
        list.updateUI( );
    }

}