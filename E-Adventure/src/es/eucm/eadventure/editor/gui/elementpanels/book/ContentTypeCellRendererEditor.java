/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.book.BookPagesListDataControl;

public class ContentTypeCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 8128260157985286632L;

    private BookPage value;

    private int row;

    private JComboBox typeCombo;

    private BookPagesListDataControl control;

    public ContentTypeCellRendererEditor( BookPagesListDataControl control ) {

        this.control = control;
    }

    public Object getCellEditorValue( ) {

        return value;
    }

    public Component getTableCellEditorComponent( JTable table, Object value2, boolean isSelected, int row, int col ) {

        this.value = (BookPage) value2;
        this.row = row;
        return createComponent( isSelected, table.getSelectionBackground( ), table );
    }

    public Component getTableCellRendererComponent( JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column ) {

        this.value = (BookPage) value2;
        this.row = row;
        if( table.getSelectedRow( ) == row ) {
            return createComponent( isSelected, table.getSelectionBackground( ), table );
        }

        if( value.getType( ) == BookPage.TYPE_RESOURCE )
            return new JLabel( TC.get( "BookPageType.Resource" ) );
        else if( value.getType( ) == BookPage.TYPE_URL )
            return new JLabel( TC.get( "BookPageType.URL" ) );
        else if( value.getType( ) == BookPage.TYPE_IMAGE )
            return new JLabel( TC.get( "BookPageType.Image" ) );
        return new JLabel( "" );
    }

    private Component createComponent( boolean isSelected, Color color, JTable table ) {

        JPanel temp = new JPanel( );
        if( isSelected )
            temp.setBorder( BorderFactory.createMatteBorder( 2, 2, 2, 0, color ) );

        String[] types = new String[] { TC.get( "BookPageType.Resource" ), TC.get( "BookPageType.URL" ), TC.get( "BookPageType.Image" ) };
        typeCombo = new JComboBox( types );
        if( value.getType( ) == BookPage.TYPE_RESOURCE )
            typeCombo.setSelectedIndex( 0 );
        else if( value.getType( ) == BookPage.TYPE_URL )
            typeCombo.setSelectedIndex( 1 );
        else if( value.getType( ) == BookPage.TYPE_IMAGE )
            typeCombo.setSelectedIndex( 2 );

        typeCombo.addActionListener( new OptionChangedListener( table ) );

        temp.add( typeCombo );

        return temp;
    }

    private class OptionChangedListener implements ActionListener {

        private JTable table;

        public OptionChangedListener( JTable table ) {

            this.table = table;
        }

        public void actionPerformed( ActionEvent e ) {

            if( typeCombo.getSelectedIndex( ) == 0 ) {
                control.setType( BookPage.TYPE_RESOURCE );
            }
            else if( typeCombo.getSelectedIndex( ) == 1 ) {
                control.setType( BookPage.TYPE_URL );
            }
            else if( typeCombo.getSelectedIndex( ) == 2 ) {
                control.setType( BookPage.TYPE_IMAGE );
            }
            ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );
            table.changeSelection( row, 0, false, false );
        }
    }

}
