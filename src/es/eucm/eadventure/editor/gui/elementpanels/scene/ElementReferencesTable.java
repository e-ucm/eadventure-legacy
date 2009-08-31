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
package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.Component;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ElementContainer;
import es.eucm.eadventure.editor.control.controllers.scene.ReferencesListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.book.IconTextPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ConditionsCellRendererEditor;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.InfoHeaderRenderer;
import es.eucm.eadventure.editor.gui.otherpanels.ElementReferenceSelectionListener;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class ElementReferencesTable extends JTable implements ElementReferenceSelectionListener {

    private static final long serialVersionUID = 1L;

    private ReferencesListDataControl dataControl;

    private ScenePreviewEditionPanel spep;

    public ElementReferencesTable( ReferencesListDataControl dControl, ScenePreviewEditionPanel spep ) {

        super( );
        this.spep = spep;

        this.setModel( new ElementsTableModel( ) );
        this.getColumnModel( ).setColumnSelectionAllowed( false );

        this.getColumnModel( ).getColumn( 0 ).setHeaderRenderer( new InfoHeaderRenderer( ) );
        this.getColumnModel( ).getColumn( 1 ).setHeaderRenderer( new InfoHeaderRenderer( ) );
        this.getColumnModel( ).getColumn( 2 ).setHeaderRenderer( new InfoHeaderRenderer( ) );
        this.getColumnModel( ).getColumn( 3 ).setHeaderRenderer( new InfoHeaderRenderer( "general/Conditions.html" ) );

        this.setDragEnabled( false );
        this.getColumnModel( ).getColumn( 0 ).setMaxWidth( 55 );
        this.getColumnModel( ).getColumn( 0 ).setPreferredWidth( 50 );

        TableColumn tc = this.getColumnModel( ).getColumn( 1 );
        tc.setMaxWidth( 30 );
        tc.setCellEditor( this.getDefaultEditor( Boolean.class ) );
        tc.setCellRenderer( this.getDefaultRenderer( Boolean.class ) );

        this.getModel( ).addTableModelListener( new TableModelListener( ) {

            public void tableChanged( TableModelEvent tme ) {

                if( tme.getType( ) == TableModelEvent.UPDATE ) {
                    int row = tme.getFirstRow( );
                    int col = tme.getColumn( );
                    List<ElementContainer> references = dataControl.getAllReferencesDataControl( );
                    if( col == 1 ) {
                        references.get( row ).setVisible( ( (Boolean) ElementReferencesTable.this.getModel( ).getValueAt( row, col ) ).booleanValue( ) );
                        ElementReferencesTable.this.spep.repaint( );
                    }
                }
            }
        } );

        this.getColumnModel( ).getColumn( 2 ).setCellRenderer( new ElementsReferencesTableCellRenderer( ) );
        this.getColumnModel( ).getColumn( 3 ).setCellRenderer( new ConditionsCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 3 ).setCellEditor( new ConditionsCellRendererEditor( ) );
        this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        setRowHeight( 18 );
        this.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent arg0 ) {

                setRowHeight( 18 );
                if( getSelectedRow( ) != -1 )
                    setRowHeight( getSelectedRow( ), 24 );
            }
        } );
        this.dataControl = dControl;
        this.setSize( 200, 150 );
    }

    private class ElementsTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        public int getColumnCount( ) {

            return 4;
        }

        public int getRowCount( ) {

            return dataControl.getAllReferencesDataControl( ).size( );
        }

        public Object getValueAt( int rowIndex, int columnIndex ) {

            if( columnIndex == 0 )
                return Integer.toString( rowIndex );
            List<ElementContainer> references = dataControl.getAllReferencesDataControl( );
            if( columnIndex == 1 )
                return new Boolean( references.get( rowIndex ).isVisible( ) );
            if( columnIndex == 2 )
                return references.get( rowIndex );
            if( columnIndex == 3 && !references.get( rowIndex ).isPlayer( ) )
                return references.get( rowIndex ).getErdc( ).getConditions( );
            return null;
        }

        @Override
        public String getColumnName( int columnIndex ) {

            if( columnIndex == 0 )
                return TextConstants.getText( "ElementList.Layer" );
            if( columnIndex == 2 )
                return TextConstants.getText( "ElementList.Title" );
            if( columnIndex == 3 )
                return TextConstants.getText( "ElementList.Conditions" );
            return "";
        }

        @Override
        public void setValueAt( Object value, int rowIndex, int columnIndex ) {

            if( columnIndex == 1 ) {
                Boolean bvalue = (Boolean) value;
                List<ElementContainer> references = dataControl.getAllReferencesDataControl( );
                references.get( rowIndex ).setVisible( bvalue.booleanValue( ) );
            }
        }

        @Override
        public boolean isCellEditable( int row, int column ) {

            return row == getSelectedRow( ) && ( column == 1 || column == 3 );
        }
    }

    private class ElementsReferencesTableCellRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {

            if( value instanceof ElementContainer ) {

                String elementName;
                if( ( (ElementContainer) value ).isPlayer( ) ) {
                    elementName = "player";
                    return new IconTextPanel( "img/icons/npc.png", elementName, isSelected );
                }
                else {
                    elementName = ( (ElementContainer) value ).getErdc( ).getElementId( );
                    if( ( (ElementContainer) value ).getErdc( ).getType( ) == Controller.ITEM_REFERENCE ) {
                        return new IconTextPanel( "img/icons/item.png", elementName, isSelected );
                    }
                    else if( ( (ElementContainer) value ).getErdc( ).getType( ) == Controller.ATREZZO_REFERENCE ) {
                        return new IconTextPanel( "img/icons/Atrezzo-1.png", elementName, isSelected );
                    }
                    else if( ( (ElementContainer) value ).getErdc( ).getType( ) == Controller.NPC_REFERENCE ) {
                        return new IconTextPanel( "img/icons/npc.png", elementName, isSelected );
                    }
                    else
                        return null;
                }
            }
            else if( value != null ) {
                return new JLabel( value.toString( ) );
            }
            else {
                return new JLabel( );
            }
        }

    }

    public void elementReferenceSelected( int layer ) {

        if( layer != -1 )
            this.changeSelection( layer, 1, false, false );
        else
            this.clearSelection( );
    }

}
