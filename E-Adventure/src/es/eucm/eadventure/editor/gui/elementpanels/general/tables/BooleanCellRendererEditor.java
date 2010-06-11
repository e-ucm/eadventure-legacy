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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class BooleanCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 8128260157985286632L;

    private Boolean value;

    public Object getCellEditorValue( ) {

        return value;
    }

    public Component getTableCellEditorComponent( final JTable table, Object value, boolean isSelected, final int row, final int col ) {

        this.value = (Boolean) value;
        return createPanel( table, isSelected );
    }

    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {

        this.value = (Boolean) value;
        return createPanel( table, isSelected );
    }

    private JPanel createPanel( JTable table, boolean isSelected ) {

        JCheckBox checkBox = new JCheckBox( );
        checkBox.setFocusable( false );
        checkBox.setSelected( value.booleanValue( ) );
        checkBox.setEnabled( isSelected );
        checkBox.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                boolean selected = ( (JCheckBox) arg0.getSource( ) ).isSelected( );
                if( selected != value.booleanValue( ) ) {
                    value = new Boolean( selected );
                    stopCellEditing( );
                }
            }
        } );

        JPanel panel = new JPanel( );
        panel.add( checkBox );
        if( isSelected ) {
            panel.setBackground( table.getSelectionBackground( ) );
            checkBox.setBackground( table.getSelectionBackground( ) );
        }
        return panel;
    }
}
