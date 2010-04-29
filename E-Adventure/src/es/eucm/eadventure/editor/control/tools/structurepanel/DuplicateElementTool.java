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
package es.eucm.eadventure.editor.control.tools.structurepanel;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureListElement;

public class DuplicateElementTool extends Tool {

    private StructureElement element;

    private JTable table;

    private StructureListElement parent;

    private StructureElement newElement;

    public DuplicateElementTool( StructureElement element, JTable table, StructureListElement parent ) {

        this.element = element;
        this.parent = parent;
        this.table = table;
    }

    @Override
    public boolean canUndo( ) {

        return true;
    }

    @Override
    public boolean doTool( ) {

        if( parent.getDataControl( ).duplicateElement( element.getDataControl( ) ) ) {
           // ( (StructureElement) table.getModel( ).getValueAt( parent.getChildCount( ) - 1, 0 ) ).setJustCreated( true );
            ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );
            // 
            /*SwingUtilities.invokeLater( new Runnable( ) {

                public void run( ) {

                    if( table.editCellAt( parent.getChildCount( ) - 1, 0 ) )
                        ( (StructureElementCell) table.getEditorComponent( ) ).requestFocusInWindow( );
                }
            } );*/
            table.changeSelection( parent.getChildCount( ) - 1, 0, false, false );
            newElement = parent.getChild( parent.getChildCount( ) - 1 );
            return true;
        }
        return false;
    }

    @Override
    public String getToolName( ) {

        return "Duplicate element";
    }

    @Override
    public boolean undoTool( ) {

        newElement.delete( false );
        ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );
        table.clearSelection( );
        return true;
    }

    @Override
    public boolean canRedo( ) {

        return false;
    }

    @Override
    public boolean redoTool( ) {

        return false;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

}
