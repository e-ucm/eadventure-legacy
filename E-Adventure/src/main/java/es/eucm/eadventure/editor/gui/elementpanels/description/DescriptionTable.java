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
package es.eucm.eadventure.editor.gui.elementpanels.description;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.DescriptionsController;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ConditionsCellRendererEditor;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.InfoHeaderRenderer;



public class DescriptionTable extends JTable {

    
    private static final long serialVersionUID = 1L;

    protected DescriptionsController descriptionController;

    public DescriptionTable( DescriptionsController dControl ) {

        super( );
       // this.looksPanel = looksPanel2;
        descriptionController = dControl;

        this.setModel( new DescriptionsTableModel( this ) );
        this.getColumnModel( ).setColumnSelectionAllowed( false );
        this.setDragEnabled( false );

        //TODO add info panel about descriptions
        this.getColumnModel( ).getColumn( 1 ).setHeaderRenderer( new InfoHeaderRenderer( "general/Conditions.html" ) );

        this.getColumnModel( ).getColumn( 1 ).setCellRenderer( new ConditionsCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 1 ).setCellEditor( new ConditionsCellRendererEditor( ) );

        this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        this.setSize( 200, 200 );
    }
    
    public int getSelectedIndex( ) {

        return getSelectedRow( );
    }

    public void setSelectedIndex( int index ) {

        getSelectionModel( ).setSelectionInterval( index, index );
    }
    
    private class DescriptionsTableModel extends AbstractTableModel {
        
        
        private DescriptionTable table;
        
        public DescriptionsTableModel(DescriptionTable table){
            this.table = table;
        }

        public int getColumnCount( ) {
            return 2;
        }

        public int getRowCount( ) {

            
            return descriptionController.getNumberOfDescriptions();
        }

        public Object getValueAt( int rowIndex, int columnIndex ) {

            if( columnIndex == 0) 
               return  TC.get( "DescriptionList.Description" ) + " " + rowIndex;
            
            if( columnIndex == 1 ) {
                if( descriptionController.getNumberOfDescriptions( ) == 1 )
                    return null;
                return descriptionController.getDescriptionController( rowIndex ).getConditionsController( );
            }
            return null;
        }
        
        @Override
        public boolean isCellEditable( int row, int column ) {

            boolean isEditable = false;
            if( column == 1 )
                isEditable = true;
            return isEditable && row == table.getSelectedRow( );
        }
        
        @Override
        public String getColumnName( int columnIndex ) {

            if( columnIndex == 0 )
                return TC.get( "DescriptionList.Descriptions" );
            if( columnIndex == 1 )
                return TC.get( "ActiveAreasList.Conditions" );
            return "";
        }
        
        
        
    }
    
    
}
