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
package es.eucm.eadventure.editor.gui.elementpanels.assessment;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
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

        return 3;
    }

    public int getRowCount( ) {

        return dataControl.getAssessmentRules( ).size( );
    }

    @Override
    public String getColumnName( int columnIndex ) {

        String columnName = "";
        if( columnIndex == 0 )
            columnName = TextConstants.getText( "AssessmentRulesList.ColumnHeader0" );
        else if( columnIndex == 1 )
            columnName = TextConstants.getText( "AssessmentRulesList.ColumnHeader1" );
        else if( columnIndex == 2 )
            columnName = TextConstants.getText( "AssessmentRulesList.ColumnHeader2" );
        return columnName;
    }

    public Object getValueAt( int rowIndex, int columnIndex ) {

        if( columnIndex == 0 )
            return dataControl.getAssessmentRules( ).get( rowIndex ).getId( );
        else if( columnIndex == 1 )
            return getImportance( dataControl.getAssessmentRules( ).get( rowIndex ).getImportance( ) );
        else if( columnIndex == 2 )
            return dataControl.getAssessmentRules( ).get( rowIndex ).getConditions( );
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
    }

    @Override
    public boolean isCellEditable( int rowIndex, int columnIndex ) {

        return table.getSelectedRow( ) == rowIndex;
    }

    private String getImportance( int importance ) {

        switch( importance ) {
            case 0:
                return TextConstants.getText( "AssessmentRule.Importance.VeryLow" );
            case 1:
                return TextConstants.getText( "AssessmentRule.Importance.Low" );
            case 2:
                return TextConstants.getText( "AssessmentRule.Importance.Normal" );
            case 3:
                return TextConstants.getText( "AssessmentRule.Importance.High" );
            case 4:
                return TextConstants.getText( "AssessmentRule.Importance.VeryHigh" );
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
