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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.EffectsController;

public class EditEffectCellRenderEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private EffectsController value;

    private JTable table;

    public EditEffectCellRenderEditor( JTable table ) {

        this.table = table;
    }

    public Component getTableCellEditorComponent( JTable table, Object value, boolean isSelected, int row, int column ) {

        if( value == null )
            return null;
        this.value = (EffectsController) value;
        return getComponent( isSelected, table );
    }

    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {

        if( value == null )
            return null;
        this.value = (EffectsController) value;
        return getComponent( isSelected, table );
    }

    public Object getCellEditorValue( ) {

        return value;
    }

    private Component getComponent( boolean isSelected, JTable table ) {

        JPanel temp = new JPanel( );
        if( isSelected )
            temp.setBorder( BorderFactory.createMatteBorder( 2, 0, 2, 0, table.getSelectionBackground( ) ) );
        JButton button = new JButton( TC.get( "ActionList.EditEffect" ) );
        button.setFocusable( false );
        button.setEnabled( isSelected );
        button.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                EditEffectCellRenderEditor.this.value.editEffect( EditEffectCellRenderEditor.this.table.getSelectedRow( ) );
            }
        } );
        temp.setLayout( new BorderLayout( ) );
        temp.add( button, BorderLayout.CENTER );
        return temp;
    }
}
