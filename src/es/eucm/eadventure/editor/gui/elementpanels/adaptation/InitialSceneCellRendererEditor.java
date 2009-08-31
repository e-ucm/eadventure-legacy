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
package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationRuleDataControl;

public class InitialSceneCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 8128260157985286632L;

    private AdaptationRuleDataControl value;

    public Object getCellEditorValue( ) {

        return value;
    }

    public Component getTableCellEditorComponent( JTable table, Object value2, boolean isSelected, int row, int col ) {

        this.value = (AdaptationRuleDataControl) value2;
        return createComponent( isSelected, table.getSelectionBackground( ) );
    }

    public Component getTableCellRendererComponent( JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column ) {

        this.value = (AdaptationRuleDataControl) value2;
        if( table.getSelectedRow( ) == row ) {
            return createComponent( isSelected, table.getSelectionBackground( ) );
        }
        return new JLabel( value.getInitialScene( ) != null ? value.getInitialScene( ) : TextConstants.getText( "GeneralText.NotSelected" ) );
    }

    private Component createComponent( boolean isSelected, Color background ) {

        JPanel temp = new JPanel( );
        if( isSelected )
            temp.setBackground( background );

        String[] scenes = Controller.getInstance( ).getIdentifierSummary( ).getAllSceneIds( );
        String[] isValues = new String[ scenes.length + 1 ];
        isValues[0] = TextConstants.getText( "GeneralText.NotSelected" );
        for( int i = 0; i < scenes.length; i++ ) {
            isValues[i + 1] = scenes[i];
        }
        final JComboBox comboBox = new JComboBox( isValues );
        if( value.getInitialScene( ) == null ) {
            comboBox.setSelectedIndex( 0 );
        }
        else {
            comboBox.setSelectedItem( value.getInitialScene( ) );
        }
        comboBox.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                if( comboBox.getSelectedIndex( ) == 0 ) {
                    value.setInitialScene( null );
                }
                else {
                    value.setInitialScene( (String) comboBox.getSelectedItem( ) );
                }
            }
        } );

        temp.add( comboBox );
        return temp;
    }
}
