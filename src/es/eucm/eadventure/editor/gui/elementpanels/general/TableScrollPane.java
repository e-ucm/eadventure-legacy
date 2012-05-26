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
package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import es.eucm.eadventure.common.gui.TC;

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
        this.textMessage = TC.get( "GeneralText.EmptyTable" );
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
