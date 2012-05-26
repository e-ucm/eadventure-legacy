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
package es.eucm.eadventure.editor.gui.elementpanels.condition;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicComboBoxUI;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;

public class EvalFunctionPanel extends EditablePanel {

    /**
     * REQUIRED
     */
    private static final long serialVersionUID = 8504903213813852589L;

    public static final int AND = ConditionsController.EVAL_FUNCTION_AND;

    public static final int OR = ConditionsController.EVAL_FUNCTION_OR;

    private JComboBox comboBox;

    private JLabel label;

    private int value;

    private int index2;

    private JButton editButton;

    public EvalFunctionPanel( ConditionsPanelController controller, int index1, int index2, int value ) {

        super( controller, index1 );
        this.value = value;
        this.index2 = index2;
        createLabel( );
        createComboBox( );
        setState( NO_SELECTED );
    }

    private void createLabel( ) {

        if( value == AND ) {
            label = new JLabel( TC.get( "Conditions.And" ) );
        }
        else if( value == OR ) {
            label = new JLabel( TC.get( "Conditions.Or" ) );
        }
        label.setFont( new Font( "Default", Font.BOLD | Font.ITALIC, 13 ) );
    }

    private void createComboBox( ) {

        comboBox = new JComboBox( new String[] { TC.get( "Conditions.And" ), TC.get( "Conditions.Or" ) } );
        comboBox.setEditable( false );
        comboBox.setUI( new BasicComboBoxUI( ) );

        if( value == AND ) {
            comboBox.setSelectedIndex( 0 );
        }
        else if( value == OR ) {
            comboBox.setSelectedIndex( 1 );
        }

        comboBox.addItemListener( new ItemListener( ) {

            public void itemStateChanged( ItemEvent e ) {

                if( e.getStateChange( ) == ItemEvent.SELECTED ) {
                    String newValue = (String) e.getItem( );
                    if( newValue.equals( TC.get( "Conditions.Or" ) ) ) {
                        if( EvalFunctionPanel.this.value != OR ) {
                            EvalFunctionPanel.this.value = OR;
                            EvalFunctionPanel.this.controller.evalFunctionChanged( EvalFunctionPanel.this, index1, index2, EvalFunctionPanel.AND, EvalFunctionPanel.OR );
                        }
                    }
                    else if( newValue.equals( TC.get( "Conditions.And" ) ) ) {
                        if( EvalFunctionPanel.this.value != AND ) {
                            EvalFunctionPanel.this.value = AND;
                            EvalFunctionPanel.this.controller.evalFunctionChanged( EvalFunctionPanel.this, index1, index2, EvalFunctionPanel.OR, EvalFunctionPanel.AND );
                        }
                    }

                }
            }

        } );

    }

    @Override
    protected void addComponents( int newState ) {

        if( newState == NO_SELECTED || newState == OVER ) {
            createLabel( );
            this.add( label );
        }
        else if( newState == SELECTED ) {
            createComboBox( );
            this.add( comboBox );
        }

    }

    /**
     * Panel with nice alpha effect for buttons
     * 
     * @author Javier
     * 
     */
    private class EvalFunctionButtonsPanel extends ButtonsPanel {

        /**
         * REQUIRED
         */
        private static final long serialVersionUID = 1L;

        @Override
        protected void createAddButtons( ) {

            editButton = new JButton( new ImageIcon( "img/icons/edit.png" ) );
            editButton.setMargin( new Insets( 0, 0, 0, 0 ) );
            editButton.setContentAreaFilled( false );
            editButton.addActionListener( new ActionListener( ) {

                public void actionPerformed( ActionEvent e ) {

                    if( EvalFunctionPanel.this.state == OVER ) {
                        EvalFunctionPanel.this.setState( SELECTED );
                    }
                }
            } );

            add( editButton );

        }
    }

    @Override
    protected ButtonsPanel createButtonsPanel( ) {

        return new EvalFunctionButtonsPanel( );
    }

}
