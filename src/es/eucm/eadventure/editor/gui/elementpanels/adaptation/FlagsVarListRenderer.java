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
package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationRuleDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.GenericOptionPaneDialog;
import es.eucm.eadventure.editor.gui.editdialogs.VarDialog;

public class FlagsVarListRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Data Control
     */
    private DataControl value;

    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col ) {

        this.value = (DataControl) value;

        boolean isFlag = false;

        if( this.value instanceof AdaptationRuleDataControl ) {
            isFlag = ( (AdaptationRuleDataControl) value ).isFlag( row );
        }
        else if( this.value instanceof AdaptationProfileDataControl ) {
            isFlag = ( (AdaptationProfileDataControl) value ).isFlag( row );
        }

        if( col == 2 )
            return prepareAction( isFlag, row, table, isSelected );
        else if( col == 3 )
            return prepareValue( isFlag, row, isSelected, table );
        return null;
    }

    public Component getTableCellEditorComponent( JTable table, Object value, boolean isSelected, int row, int col ) {

        this.value = (DataControl) value;

        boolean isFlag = false;

        if( this.value instanceof AdaptationRuleDataControl ) {
            isFlag = ( (AdaptationRuleDataControl) value ).isFlag( row );
        }
        else if( this.value instanceof AdaptationProfileDataControl ) {
            isFlag = ( (AdaptationProfileDataControl) value ).isFlag( row );
        }

        if( col == 2 )
            return prepareAction( isFlag, row, table, isSelected );
        else if( col == 3 )
            return prepareValue( isFlag, row, isSelected, table );
        return null;
    }

    public Object getCellEditorValue( ) {

        return value;
    }

    private JComboBox prepareValue( boolean isFlag, int rowIndex, boolean isSelected, JTable table ) {

        JComboBox values = null;
        String selectedFlagVar = null;
        // get the flag/var from the data control
        if( value instanceof AdaptationRuleDataControl ) {
            selectedFlagVar = ( (AdaptationRuleDataControl) value ).getFlag( rowIndex );
        }
        else if( this.value instanceof AdaptationProfileDataControl ) {
            selectedFlagVar = ( (AdaptationProfileDataControl) value ).getFlag( rowIndex );
        }
        // get the values for the combo box
        String[] flagsvars = null;
        if( isFlag )
            flagsvars = Controller.getInstance( ).getVarFlagSummary( ).getFlags( );
        else
            flagsvars = Controller.getInstance( ).getVarFlagSummary( ).getVars( );

        values = new JComboBox( flagsvars );
        values.setEditable( true );

        // if there are no flag/var for this property, and there are some flag/var in chapter, take the first one
        if( selectedFlagVar == null || selectedFlagVar.equals( "" ) && flagsvars.length > 0 )
            selectedFlagVar = flagsvars[0];

        if( flagsvars.length == 0 ) {
            if( isFlag ) {
                selectedFlagVar = TC.get( "Flags.DefaultFlagName" );
                Controller.getInstance( ).getVarFlagSummary( ).addVar( selectedFlagVar );
                Controller.getInstance( ).getVarFlagSummary( ).addVarReference( selectedFlagVar );

            }
            else if( !isFlag ) {
                selectedFlagVar = TC.get( "Vars.DefaultVarName" );
                Controller.getInstance( ).getVarFlagSummary( ).addVar( selectedFlagVar );
                Controller.getInstance( ).getVarFlagSummary( ).addVarReference( selectedFlagVar );

            }
            values.addItem( selectedFlagVar );
        }

        values.setSelectedItem( selectedFlagVar );

        //add action listener
        values.addActionListener( new ComboListener( rowIndex, values, isFlag ) );
        // create border if it is selected
        if( isSelected )
            values.setBorder( BorderFactory.createMatteBorder( 2, 0, 2, 0, table.getSelectionBackground( ) ) );

        return values;
    }

    /**
     * Prepare the panel for second column
     * 
     * @param isFlag
     * @param rowIndex
     * @return
     */
    private JPanel prepareAction( boolean isFlag, int rowIndex, JTable table, boolean isSelected ) {

        String[] operations = null;
        String selectedAction = null;
        int varVal = -1;
        // get the action and value (only for vars) from the data control
        if( value instanceof AdaptationRuleDataControl ) {
            selectedAction = ( (AdaptationRuleDataControl) value ).getAction( rowIndex );

        }
        else if( this.value instanceof AdaptationProfileDataControl ) {
            selectedAction = ( (AdaptationProfileDataControl) value ).getAction( rowIndex );
        }

        if( !isFlag ) {
            // if is var, get the value from the action
            String[] split = selectedAction.split( " " );
            if( split.length < 2 ) {
                varVal = 0;
            }
            else {
                varVal = Integer.parseInt( split[1] );
            }
            selectedAction = split[0];
            // set the operations
            operations = new String[] { "increment", "decrement", "set-value" };
        }
        else {
            // set the operations
            operations = new String[] { "activate", "deactivate" };
        }

        // label for action name
        JLabel actionName = new JLabel( selectedAction );
        JPanel labelCont = new JPanel( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.weightx = 0.5;
        actionName.setOpaque( false );
        labelCont.add( actionName, c );
        labelCont.setBackground( Color.white );

        // button to edit the action
        JButton edit = new JButton( "Edit" );
        edit.addActionListener( new EditButtonListener( operations, varVal, rowIndex, selectedAction ) );

        // the component to show
        JPanel component = new JPanel( new GridLayout( 0, 2 ) );
        component.add( labelCont );
        // Label for value
        if( !isFlag ) {
            //component.setLayout(new GridLayout(0,3));
            c.gridx = 1;
            c.weightx = 0.5;
            JLabel valueLabel = new JLabel( String.valueOf( varVal ) );
            labelCont.add( valueLabel, c );
        }
        component.add( edit );
        // create border if it is selected
        if( isSelected )
            component.setBorder( BorderFactory.createMatteBorder( 2, 0, 2, 0, table.getSelectionBackground( ) ) );

        return component;

    }

    /**
     * Listener for var/flags combo box
     */
    private class ComboListener implements ActionListener {

        private int rowIndex;

        private JComboBox combo;

        private boolean isFlag;

        public ComboListener( int rowIndex, JComboBox combo, boolean isFlag ) {

            this.rowIndex = rowIndex;
            this.combo = combo;
            this.isFlag = isFlag;
        }

        public void actionPerformed( ActionEvent e ) {

            String selectedValue = (String) combo.getSelectedItem( );
            // get the flags/vars from current chapter
            if( !isFlag ) {
                if( !Controller.getInstance( ).getVarFlagSummary( ).existsVar( selectedValue ) && !selectedValue.equals( "" ) ) {
                    Controller.getInstance( ).getVarFlagSummary( ).addVar( selectedValue );
                    Controller.getInstance( ).getVarFlagSummary( ).addVarReference( selectedValue );
                    // add new value to combo
                    combo.addItem( selectedValue );
                }
            }
            else if( isFlag ) {
                if( !Controller.getInstance( ).getVarFlagSummary( ).existsFlag( selectedValue ) && !selectedValue.equals( "" ) ) {
                    Controller.getInstance( ).getVarFlagSummary( ).addFlag( selectedValue );
                    Controller.getInstance( ).getVarFlagSummary( ).addFlagReference( selectedValue );
                    // add new value to combo
                    combo.addItem( selectedValue );
                }
            }

            //Set the flag/var    
            if( value instanceof AdaptationRuleDataControl ) {
                ( (AdaptationRuleDataControl) value ).setFlag( rowIndex, selectedValue );

            }
            else if( value instanceof AdaptationProfileDataControl ) {
                ( (AdaptationProfileDataControl) value ).setFlag( rowIndex, selectedValue );
            }

        }

    }

    /**
     * Listener for edit button
     */
    private class EditButtonListener implements ActionListener {

        /**
         * Actions: active/inactive for flags and set,increment, decrement for
         * vars
         */
        private String[] actions;

        /**
         * The previous var val, -1 if it is flag
         */
        private int varVal;

        /**
         * the row index
         */
        private int rowIndex;

        /**
         * The name of the action
         */
        private String action;

        /**
         * 
         * @param values
         * @param varVal
         */
        public EditButtonListener( String[] values, int varVal, int rowIndex, String action ) {

            this.actions = values;
            this.varVal = varVal;
            this.rowIndex = rowIndex;
            this.action = action;
        }

        public void actionPerformed( ActionEvent e ) {

            // ask for new values
            VarDialog dialog = new VarDialog( varVal, actions, action );
            // set new values
            if( dialog.getValue( ).equals( VarDialog.ERROR ) ) {
                GenericOptionPaneDialog.showMessageDialog( null, "asiiii no", "asiiii no", JOptionPane.ERROR_MESSAGE );
            }
            else {
                if( !dialog.getValue( ).equals( VarDialog.CLOSE ) ) {
                    if( value instanceof AdaptationRuleDataControl ) {
                        ( (AdaptationRuleDataControl) value ).setAction( rowIndex, dialog.getValue( ) );
                    }
                    else if( value instanceof AdaptationProfileDataControl ) {
                        ( (AdaptationProfileDataControl) value ).setAction( rowIndex, dialog.getValue( ) );
                    }
                    // actualize varVal with new introduced value
                    if( varVal >= 0 )
                        varVal = Integer.parseInt( dialog.getValue( ).split( " " )[1] );
                    stopCellEditing( );
                }
            }

        }
    }

}
