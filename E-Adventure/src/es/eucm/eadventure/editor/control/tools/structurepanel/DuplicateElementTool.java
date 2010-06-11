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
