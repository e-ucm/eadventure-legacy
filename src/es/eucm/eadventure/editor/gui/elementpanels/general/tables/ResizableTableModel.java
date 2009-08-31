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
