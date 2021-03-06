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

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import es.eucm.eadventure.common.data.chapter.conditions.FlagCondition;
import es.eucm.eadventure.common.data.chapter.conditions.GlobalStateCondition;
import es.eucm.eadventure.common.data.chapter.conditions.VarCondition;
import es.eucm.eadventure.common.gui.TC;

public class AtomicConditionPanel extends EditablePanel {

    /**
     * Required
     */
    private static final long serialVersionUID = -7026686497412446434L;

    /*
     * Fields for displaying. The structure is the following: ICON Subject(id) Verb(Action) directComplement (Value)?
     */
    private JLabel iconLabel;

    private List<Component> renderComponents;

    private JButton deleteButton;

    private JButton duplicateButton;

    private JButton editButton;

    private int index2;

    public AtomicConditionPanel( ConditionsPanelController controller, int i, int index2 ) {

        super( controller, i );
        this.index2 = index2;

        // Condition properties
        HashMap<String, String> properties = controller.getCondition( i, index2 );
        String type = properties.get( ConditionsPanelController.CONDITION_TYPE );
        String id = properties.get( ConditionsPanelController.CONDITION_ID );
        String value = properties.get( ConditionsPanelController.CONDITION_VALUE );
        String state = properties.get( ConditionsPanelController.CONDITION_STATE );

        // Create rendering elements
        iconLabel = new JLabel( getIcon( type ) );

        renderComponents = new ArrayList<Component>( );
        Component subjectRender = getSubjectRender( id );
        if( subjectRender != null )
            renderComponents.add( subjectRender );
        Component verbRender = getVerbRender( type, state );
        if( verbRender != null )
            renderComponents.add( verbRender );
        Component directComplementRender = getDirectComplementRender( type, value );
        if( directComplementRender != null )
            renderComponents.add( directComplementRender );

        setState( NO_SELECTED );
    }

    @Override
    protected void addComponents( int newState ) {

        // Add items
        add( iconLabel );
        for( Component component : renderComponents )
            add( component );

    }

    private Icon getIcon( String type ) {

        Icon icon = null;
        if( type.equals( ConditionsPanelController.CONDITION_TYPE_FLAG ) ) {
            icon = new ImageIcon( "img/icons/flag16.png" );
        }
        else if( type.equals( ConditionsPanelController.CONDITION_TYPE_VAR ) ) {
            icon = new ImageIcon( "img/icons/var16.png" );
        }
        else if( type.equals( ConditionsPanelController.CONDITION_TYPE_GS ) ) {
            icon = new ImageIcon( "img/icons/group16.png" );
        }
        return icon;
    }

    private Component getSubjectRender( String id ) {

        JLabel label1 = new JLabel( id );
        return label1;
    }

    /**
     * 
     * @param state
     * @return
     */
    private Component getVerbRender( String type, String state ) {

        if( type.equals( ConditionsPanelController.CONDITION_TYPE_FLAG ) ) {
            return new JLabel( TC.get( "GeneralText.Is.Singular" ) );
        }

        else if( type.equals( ConditionsPanelController.CONDITION_TYPE_VAR ) ) {
            if( state.equals( Integer.toString( VarCondition.VAR_EQUALS ) ) ) {
                return new JLabel( "=" );
            }

            else if( state.equals( Integer.toString( VarCondition.VAR_NOT_EQUALS ) ) ) {
                return new JLabel( "!=" );
            }

            else if( state.equals( Integer.toString( VarCondition.VAR_GREATER_EQUALS_THAN ) ) ) {
                return new JLabel( ">=" );
            }
            else if( state.equals( Integer.toString( VarCondition.VAR_GREATER_THAN ) ) ) {
                return new JLabel( ">" );
            }
            else if( state.equals( Integer.toString( VarCondition.VAR_LESS_EQUALS_THAN ) ) ) {
                return new JLabel( "<=" );
            }
            else if( state.equals( Integer.toString( VarCondition.VAR_LESS_THAN ) ) ) {
                return new JLabel( "<" );
            }
        }

        else if( type.equals( ConditionsPanelController.CONDITION_TYPE_GS ) ) {
            if( state.equals( Integer.toString( GlobalStateCondition.GS_SATISFIED ) ) ) {
                return new JLabel( TC.get( "Conditions.ConditionGroup.Satisfied" ) );
            }

            else if( state.equals( Integer.toString( GlobalStateCondition.GS_NOT_SATISFIED ) ) ) {
                return new JLabel( TC.get( "Conditions.ConditionGroup.NotSatisfied" ) );
            }
        }

        return null;
    }

    /**
     * 
     * @param state
     * @return
     */
    private Component getDirectComplementRender( String type, String value ) {

        if( type.equals( ConditionsPanelController.CONDITION_TYPE_FLAG ) ) {
            if( value.equals( Integer.toString( FlagCondition.FLAG_ACTIVE ) ) ) {
                return new JLabel( TC.get( "Conditions.Flag.Active" ) );
            }
            else if( value.equals( Integer.toString( FlagCondition.FLAG_INACTIVE ) ) ) {
                return new JLabel( TC.get( "Conditions.Flag.Inactive" ) );
            }
        }

        else if( type.equals( ConditionsPanelController.CONDITION_TYPE_VAR ) ) {
            return new JLabel( value );
        }

        else if( type.equals( ConditionsPanelController.CONDITION_TYPE_GS ) ) {
            return null;
        }

        return null;
    }

    /**
     * Panel with nice alpha effect for buttons
     * 
     * @author Javier
     * 
     */
    private class AtomicConditionButtonsPanel extends ButtonsPanel {

        private static final long serialVersionUID = -4384802457254649642L;

        @Override
        protected void createAddButtons( ) {

            deleteButton = new JButton( new ImageIcon( "img/icons/deleteNode.png" ) );
            deleteButton.setMargin( new Insets( 0, 0, 0, 0 ) );
            deleteButton.setContentAreaFilled( false );
            deleteButton.addActionListener( new ActionListener( ) {

                public void actionPerformed( ActionEvent e ) {

                    controller.deleteCondition( index1, index2 );
                }
            } );

            duplicateButton = new JButton( new ImageIcon( "img/icons/duplicateNode.png" ) );
            duplicateButton.setMargin( new Insets( 0, 0, 0, 0 ) );
            duplicateButton.setContentAreaFilled( false );
            duplicateButton.addActionListener( new ActionListener( ) {

                public void actionPerformed( ActionEvent e ) {

                    controller.duplicateCondition( index1, index2 );
                }
            } );

            editButton = new JButton( new ImageIcon( "img/icons/edit.png" ) );
            editButton.setMargin( new Insets( 0, 0, 0, 0 ) );
            editButton.setContentAreaFilled( false );
            editButton.addActionListener( new ActionListener( ) {

                public void actionPerformed( ActionEvent e ) {

                    controller.editCondition( index1, index2 );
                }
            } );

            add( deleteButton );
            add( duplicateButton );
            add( editButton );

        }
    }

    @Override
    protected ButtonsPanel createButtonsPanel( ) {

        return new AtomicConditionButtonsPanel( );
    }

}
