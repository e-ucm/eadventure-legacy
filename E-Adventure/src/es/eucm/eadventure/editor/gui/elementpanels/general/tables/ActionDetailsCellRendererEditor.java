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
package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;

public class ActionDetailsCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 8128260157985286632L;

    private ActionDataControl value;

    public Object getCellEditorValue( ) {

        return value;
    }

    public Component getTableCellEditorComponent( JTable table, Object value2, boolean isSelected, int row, int col ) {

        this.value = (ActionDataControl) value2;
        return createComponent( isSelected, table );
    }

    public Component getTableCellRendererComponent( JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column ) {

        this.value = (ActionDataControl) value2;
        return createComponent( isSelected, table );
    }

    private Component createComponent( boolean isSelected, JTable table ) {

        JPanel temp = new JPanel( );
        temp.setBackground( table.getBackground( ) );
        if( isSelected )
            temp.setBorder( BorderFactory.createMatteBorder( 2, 0, 2, 0, table.getSelectionBackground( ) ) );

        temp.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        SpinnerModel sm = new SpinnerNumberModel( value.getKeepDistance( ), 0, 100, 5 );
        final JSpinner keepDistanceSpinner = new JSpinner( sm );

        final JCheckBox checkBox = new JCheckBox( "" );
        checkBox.setSelected( value.getNeedsGoTo( ) );
        checkBox.setOpaque( false );
        checkBox.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                value.setNeedsGoTo( checkBox.isSelected( ) );
                keepDistanceSpinner.setEnabled( checkBox.isSelected( ) );
            }
        } );
        temp.add( checkBox, c );

        c.gridx++;
        if( isSelected ) {
            keepDistanceSpinner.setEnabled( value.getNeedsGoTo( ) );
            keepDistanceSpinner.addChangeListener( new KeepDistanceSpinnerListener( keepDistanceSpinner ) );
            temp.add( keepDistanceSpinner, c );
        }
        else {
            temp.add( new JLabel( "" + value.getKeepDistance( ) ), c );
        }

        if( Controller.getInstance( ).isPlayTransparent( ) ) {
            temp.removeAll( );
            temp.add( new JLabel( TC.get( "ActionsList.NotRelevant" ) ) );
        }

        return temp;
    }

    /**
     * Listener for the changes in the keepDistances spinner
     */
    private class KeepDistanceSpinnerListener implements ChangeListener {

        private JSpinner spinner;

        public KeepDistanceSpinnerListener( JSpinner spinner ) {

            this.spinner = spinner;
        }

        public void stateChanged( ChangeEvent arg0 ) {

            value.setKeepDistance( ( (Integer) spinner.getModel( ).getValue( ) ).intValue( ) );
        }
    }

}
