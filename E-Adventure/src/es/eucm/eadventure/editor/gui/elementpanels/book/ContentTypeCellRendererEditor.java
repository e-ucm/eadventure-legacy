/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
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
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.book.BookPagesListDataControl;

public class ContentTypeCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	private BookPage value;
	
	private int row;
	
	private JComboBox typeCombo;
		
	private BookPagesListDataControl control;
	
	public ContentTypeCellRendererEditor(BookPagesListDataControl control) {
		this.control = control;
	}

	public Object getCellEditorValue() {
		return value;
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value2, boolean isSelected, int row, int col) {
		this.value = (BookPage) value2;
		this.row = row;
		return createComponent(isSelected, table.getSelectionBackground(), table);
	}

	public Component getTableCellRendererComponent(JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column) {
		this.value = (BookPage) value2;
		this.row = row;
		if (table.getSelectedRow() == row) {
			return createComponent(isSelected, table.getSelectionBackground(), table);
		}
		
		if( value.getType( ) == BookPage.TYPE_RESOURCE )
			return new JLabel(TextConstants.getText( "BookPageType.Resource" ));
		else if( value.getType( ) == BookPage.TYPE_URL )
			return new JLabel(TextConstants.getText( "BookPageType.URL" ));
		else if ( value.getType() == BookPage.TYPE_IMAGE )
			return new JLabel(TextConstants.getText( "BookPageType.Image" ));
		return new JLabel("");
	}

	private Component createComponent(boolean isSelected, Color color, JTable table) {
		JPanel temp = new JPanel();
		if (isSelected)
			temp.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 0, color));

		String[] types = new String[] {
			TextConstants.getText( "BookPageType.Resource" ),
			TextConstants.getText( "BookPageType.URL" ),
			TextConstants.getText( "BookPageType.Image" )
		};
		typeCombo = new JComboBox(types);
		if( value.getType( ) == BookPage.TYPE_RESOURCE )
			typeCombo.setSelectedIndex(0);
		else if( value.getType( ) == BookPage.TYPE_URL )
			typeCombo.setSelectedIndex(1);
		else if ( value.getType() == BookPage.TYPE_IMAGE )
			typeCombo.setSelectedIndex(2);
		
		typeCombo.addActionListener(new OptionChangedListener(table));

		temp.add(typeCombo);
		
		return temp;
	}	
	
	private class OptionChangedListener implements ActionListener {
		private JTable table;
		
		public OptionChangedListener(JTable table) {
			this.table = table;
		}

		public void actionPerformed( ActionEvent e ) {
			if( typeCombo.getSelectedIndex() == 0 ){
				control.setType(BookPage.TYPE_RESOURCE);
			}else if( typeCombo.getSelectedIndex() == 1 ){
				control.setType(BookPage.TYPE_URL);
			} else if ( typeCombo.getSelectedIndex() == 2 ) {
				control.setType(BookPage.TYPE_IMAGE);
			}
			((AbstractTableModel) table.getModel()).fireTableDataChanged();
			table.changeSelection(row, 0, false, false);
		}
	}

}
