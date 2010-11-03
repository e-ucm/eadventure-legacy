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
import es.eucm.eadventure.common.gui.TC;
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
        temp2.add( new JLabel( TC.get( "Animation.Duration" ) + ": " ) );
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
