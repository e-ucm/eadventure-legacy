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
package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.Color;
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

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookPagesListDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.ChangePageMarginsDialog;

public class MarginsCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 8128260157985286632L;

    private BookPage value;

    private BookPagesListDataControl control;

    private BookPagesPanel parentPanel;

    public MarginsCellRendererEditor( BookPagesListDataControl control, BookPagesPanel parentPanel ) {

        this.control = control;
        this.parentPanel = parentPanel;
    }

    public Object getCellEditorValue( ) {

        return value;
    }

    public Component getTableCellEditorComponent( JTable table, Object value2, boolean isSelected, int row, int col ) {

        this.value = (BookPage) value2;
        return createComponent( isSelected, table.getSelectionBackground( ) );
    }

    public Component getTableCellRendererComponent( JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column ) {

        this.value = (BookPage) value2;
        if( table.getSelectedRow( ) == row ) {
            return createComponent( isSelected, table.getSelectionBackground( ) );
        }

        JButton button = new JButton( TC.get( "BookPage.Margin" ) );
        button.setEnabled( false );
        return button;
    }

    private Component createComponent( boolean isSelected, Color color ) {

        JPanel temp = new JPanel( );
        if( isSelected )
            temp.setBorder( BorderFactory.createMatteBorder( 2, 2, 2, 0, color ) );

        JButton changeMargins = new JButton( TC.get( "BookPage.Margin" ) );
        changeMargins.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                BookDataControl dControl = parentPanel.getDataControl( );              
                new ChangePageMarginsDialog( control, dControl );
                parentPanel.updatePreview( );
            }
        } );
        changeMargins.setEnabled( value != null && ( value.getType( ) == BookPage.TYPE_IMAGE || value.getType( ) == BookPage.TYPE_RESOURCE ) );
        temp.add( changeMargins );

        return temp;
    }
}
