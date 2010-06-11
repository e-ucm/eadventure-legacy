/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.engine.core.control;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.data.chapter.conditions.GlobalState;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;

public class DebugTableModel extends AbstractTableModel implements TableCellRenderer {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private FlagSummary flagSummary;

    private VarSummary varSummary;

    private List<String> ids;

    private List<String> changes;

    private List<GlobalState> globalStates;

    private boolean onlyChanges;

    public DebugTableModel( FlagSummary flagSummary, VarSummary varSummary ) {

        this.flagSummary = flagSummary;
        this.varSummary = varSummary;
        ids = new ArrayList<String>( );
        for( String id : flagSummary.getFlags( ).keySet( ) ) {
            ids.add( id );
        }
        for( String id : varSummary.getVars( ).keySet( ) ) {
            ids.add( id );
        }
        Collections.sort( ids );
        this.onlyChanges = false;
    }

    public DebugTableModel( FlagSummary flagSummary, VarSummary varSummary, boolean onlyChanges ) {

        this( flagSummary, varSummary );
        this.onlyChanges = onlyChanges;
    }

    public void addGlobalStates( List<GlobalState> globalStates ) {

        this.globalStates = globalStates;
        for( GlobalState gb : globalStates )
            ids.add( gb.getId( ) );
    }

    @Override
    public String getColumnName( int col ) {

        if( col == 0 )
            return TC.get( "DebugFrame.id" );
        if( col == 1 )
            return TC.get( "DebugFrame.value" );
        return "";
    }

    public int getRowCount( ) {

        if( !onlyChanges )
            return ( ids.size( ) );
        else if( changes != null )
            return changes.size( );
        else
            return 0;
    }

    public int getColumnCount( ) {

        return 2;
    }

    public String getValueAt( int row, int col ) {

        String id = "";

        if( !onlyChanges )
            id = ids.get( row );
        else if( changes != null )
            id = changes.get( row );

        if( col == 0 ) {
            return id;
        }
        else {
            if( flagSummary.getFlags( ).containsKey( id ) ) {
                if( flagSummary.getFlagValue( id ) )
                    return "true";
                else
                    return "false";
            }
            else if( varSummary.getVars( ).containsKey( id ) ) {
                return "" + varSummary.getValue( id );
            }
            else {
                for( GlobalState gb : globalStates ) {
                    if( gb.getId( ).equals( id ) ) {
                        FunctionalConditions fc = new FunctionalConditions( gb );
                        if( fc.allConditionsOk( ) )
                            return "true";
                        else
                            return "false";
                    }
                }
            }
        }
        return "";
    }

    @Override
    public boolean isCellEditable( int row, int col ) {

        return false;
    }

    @Override
    public void setValueAt( Object value, int row, int col ) {

    }

    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {

        String string = (String) value;
        JTextArea label = new JTextArea( (String) value );

        if( string.equals( "false" ) ) {
            label.setForeground( Color.RED );
        }
        else if( string.equals( "true" ) ) {
            label.setForeground( Color.GREEN );
        }

        if( !onlyChanges && changes != null && changes.contains( ids.get( row ) ) ) {
            label.setBackground( Color.YELLOW );
        }

        return label;
    }

    public void setChanges( List<String> changes ) {

        this.changes = changes;
    }
}
