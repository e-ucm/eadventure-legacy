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
