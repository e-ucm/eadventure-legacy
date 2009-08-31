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

import es.eucm.eadventure.common.gui.TextConstants;
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
        JButton button = new JButton( TextConstants.getText( "ActionList.EditEffect" ) );
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
