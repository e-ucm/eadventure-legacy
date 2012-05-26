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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.editor.control.controllers.general.CustomActionDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;

public class ActionCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 8128260157985286632L;

    private ActionDataControl value;

    public Object getCellEditorValue( ) {

        return value;
    }

    public Component getTableCellEditorComponent( JTable table, Object value2, boolean isSelected, int row, int col ) {

        this.value = (ActionDataControl) value2;
        return createComponent( isSelected, table );
    }

    public Component getTableCellRendererComponent( JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column ) {

        this.value = (ActionDataControl) value2;
        if( table.getSelectedRow( ) == row ) {
            return createComponent( isSelected, table );
        }

        String text = getTypeText( value.getType( ) );
        if( value.hasIdTarget( ) )
            text += value.getIdTarget( );

        return new JLabel( text );
    }

    private Component createComponent( boolean isSelected, JTable table ) {

        JPanel temp = new JPanel( );
        temp.setBackground( table.getBackground( ) );
        if( isSelected )
            temp.setBorder( BorderFactory.createMatteBorder( 2, 2, 2, 0, table.getSelectionBackground( ) ) );

        if( !value.hasIdTarget( ) && value.getType( ) != Controller.ACTION_CUSTOM ) {
            temp.setLayout( new BorderLayout( ) );
            temp.add( new JLabel( getTypeText( value.getType( ) ) ), BorderLayout.CENTER );
            return temp;
        }

        temp.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        if( value.getType( ) == Controller.ACTION_CUSTOM || value.getType( ) == Controller.ACTION_CUSTOM_INTERACT ) {
            JTextField textField = new JTextField( );
            textField.setText( ( (CustomActionDataControl) value ).getName( ) );
            textField.getDocument( ).addDocumentListener( new NameChangeListener( textField, (Named) value.getContent( ) ) );
            temp.add( textField, c );
        }
        else {
            temp.add( new JLabel( getTypeText( value.getType( ) ) ), c );
        }

        c.gridy++;
        if( value.hasIdTarget( ) ) {
            JComboBox combo = new JComboBox( );
            combo = new JComboBox( value.getElementsList( ) );
            combo.setSelectedItem( value.getIdTarget( ) );
            combo.setFocusable( false );
            combo.addActionListener( new ActionListener( ) {

                public void actionPerformed( ActionEvent arg0 ) {

                    value.setIdTarget( ( (JComboBox) arg0.getSource( ) ).getSelectedItem( ).toString( ) );
                }
            } );
            temp.add( combo, c );
        }

        return temp;
    }

    private String getTypeText( int type ) {

        String text = "";
        switch( type ) {
            case Controller.ACTION_EXAMINE:
                text = TC.get( "ActionsList.ExamineAction" );
                break;
            case Controller.ACTION_GRAB:
                text = TC.get( "ActionsList.GrabAction" );
                break;
            case Controller.ACTION_USE:
                text = TC.get( "ActionsList.UseAction" );
                break;
            case Controller.ACTION_CUSTOM:
                text = ( (CustomActionDataControl) value ).getName( );
                break;
            case Controller.ACTION_USE_WITH:
                text = TC.get( "ActionsList.UseWithAction", "" );
                break;
            case Controller.ACTION_GIVE_TO:
                text = TC.get( "ActionsList.GiveToAction", "" );
                break;
            case Controller.ACTION_CUSTOM_INTERACT:
                text = ( (CustomActionDataControl) value ).getName( ) + ": ";
                break;
            case Controller.ACTION_TALK_TO:
                text = TC.get( "ActionsList.TalkToAction" );
                break;
            case Controller.ACTION_DRAG_TO:
                text = TC.get( "ActionsList.DragToAction", "" );
                break;
        }
        return text;

    }

}
