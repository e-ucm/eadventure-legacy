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
package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.editor.control.controllers.DataControl;

public class ResizableTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private List<DataControl> list;

    private int size = 1;

    /**
     * Constructor.
     * 
     * @param itemsInfo
     *            Container array of the information of the items
     */
    public ResizableTableModel( List<DataControl> list, int size ) {

        this.list = list;
        this.size = size;
    }

    public int getColumnCount( ) {

        if( size == 0 )
            return 1;
        if( size == 1 )
            return 4;
        if( size == 2 )
            return 2;
        return 2;
    }

    public int getRowCount( ) {

        if( size == 0 )
            return list.size( );
        if( size == 1 )
            return list.size( ) / 4 + ( list.size( ) % 4 > 0 ? 1 : 0 );
        if( size == 2 )
            return list.size( ) / 2 + list.size( ) % 2;
        return 0;
    }

    public Object getValueAt( int rowIndex, int columnIndex ) {

        if( size == 2 && rowIndex * 2 + columnIndex < list.size( ) )
            return list.get( rowIndex * 2 + columnIndex );
        if( size == 1 && rowIndex * 4 + columnIndex < list.size( ) )
            return list.get( rowIndex * 4 + columnIndex );
        if( size == 0 )
            return list.get( rowIndex );
        return null;
    }

    @Override
    public boolean isCellEditable( int row, int column ) {

        return true;
    }

    public void setSize( int size ) {

        this.size = size;
    }

}
