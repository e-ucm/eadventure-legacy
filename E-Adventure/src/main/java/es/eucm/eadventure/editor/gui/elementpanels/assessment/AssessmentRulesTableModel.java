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
package es.eucm.eadventure.editor.gui.elementpanels.assessment;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;

/**
 * Table model to display the scenes information.
 */
public class AssessmentRulesTableModel extends AbstractTableModel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Array of data to display.
     */
    private AssessmentProfileDataControl dataControl;

    private JTable table;

    /**
     * Constructor.
     * 
     * @param assRulesInfo
     *            Container array of the information of the scenes
     */
    public AssessmentRulesTableModel( AssessmentProfileDataControl dataControl ) {

        this.dataControl = dataControl;
    }

    public int getColumnCount( ) {

        return 4;
    }

    public int getRowCount( ) {

        return dataControl.getAssessmentRules( ).size( );
    }

    @Override
    public String getColumnName( int columnIndex ) {

        String columnName = "";
        if( columnIndex == 0 )
            columnName = TC.get( "AssessmentRulesList.ColumnHeader0" );
        else if( columnIndex == 1 )
            columnName = TC.get( "AssessmentRulesList.ColumnHeader1" );
        else if( columnIndex == 2 )
            columnName = TC.get( "AssessmentRulesList.ColumnHeader2" );
        else if( columnIndex == 3 )
            columnName = TC.get( "Assessment.Column.RepeatRule" );
        return columnName;
    }

    public Object getValueAt( int rowIndex, int columnIndex ) {

        if( columnIndex == 0 )
            return dataControl.getAssessmentRules( ).get( rowIndex ).getId( );
        else if( columnIndex == 1 )
            return getImportance( dataControl.getAssessmentRules( ).get( rowIndex ).getImportance( ) );
        else if( columnIndex == 2 )
            return dataControl.getAssessmentRules( ).get( rowIndex ).getConditions( );
        else if( columnIndex == 3 )
            return dataControl.getAssessmentRules( ).get( rowIndex ).isRepeatRule( );
        return null;
    }

    @Override
    public void setValueAt( Object value, int rowIndex, int columnIndex ) {

        if( columnIndex == 0 ) {
            dataControl.getAssessmentRules( ).get( rowIndex ).setId( (String) value );
        }
        if( columnIndex == 1 ) {
            dataControl.getAssessmentRules( ).get( rowIndex ).setImportance( getImportance( (String) value ) );
        }
        if( columnIndex == 3 ) {
            dataControl.getAssessmentRules( ).get( rowIndex ).setRepeatRule( (Boolean) value );
        }
    }

    @Override
    public boolean isCellEditable( int rowIndex, int columnIndex ) {

        return table.getSelectedRow( ) == rowIndex;
    }

    private String getImportance( int importance ) {

        switch( importance ) {
            case 0:
                return TC.get( "AssessmentRule.Importance.VeryLow" );
            case 1:
                return TC.get( "AssessmentRule.Importance.Low" );
            case 2:
                return TC.get( "AssessmentRule.Importance.Normal" );
            case 3:
                return TC.get( "AssessmentRule.Importance.High" );
            case 4:
                return TC.get( "AssessmentRule.Importance.VeryHigh" );
        }
        return "";
    }

    private int getImportance( String importance ) {

        for( int i = 0; i <= 4; i++ ) {
            if( importance.equals( getImportance( i ) ) )
                return i;
        }
        return 0;
    }

    public void setTable( JTable informationTable ) {

        table = informationTable;
    }

}
