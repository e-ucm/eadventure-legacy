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
package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import es.eucm.eadventure.common.gui.TextConstants;

public class TableScrollPane extends JScrollPane {

    private static final long serialVersionUID = 2423910731205436129L;

    private JTable table;

    private String textMessage;

    public TableScrollPane( JTable table, int verticalPolicy, int horizontalPolicy ) {

        super( table, verticalPolicy, horizontalPolicy );
        this.table = table;
        table.getModel( ).addTableModelListener( new TableModelListener( ) {

            public void tableChanged( TableModelEvent arg0 ) {

                repaint( );
            }
        } );
        this.textMessage = TextConstants.getText( "GeneralText.EmptyTable" );
    }

    @Override
    public void paint( Graphics g ) {

        super.paint( g );
        if( table.getRowCount( ) == 0 ) {
            int x = this.getWidth( ) / 2;
            int y = this.getHeight( ) / 2;
            if( y < 55 )
                y = 55;
            g.setColor( Color.BLACK );
            x -= g.getFontMetrics( ).stringWidth( textMessage ) / 2;
            g.drawString( textMessage, x, y );
        }
    }

}
