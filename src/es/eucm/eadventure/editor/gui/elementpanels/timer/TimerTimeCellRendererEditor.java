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
package es.eucm.eadventure.editor.gui.elementpanels.timer;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.editor.control.controllers.timer.TimerDataControl;

public class TimerTimeCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 8128260157985286632L;

    private TimerDataControl value;

    public Object getCellEditorValue( ) {

        return value;
    }

    public Component getTableCellEditorComponent( JTable table, Object value, boolean isSelected, int row, int col ) {

        this.value = (TimerDataControl) value;
        return createSpinner( table, isSelected );
    }

    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {

        this.value = (TimerDataControl) value;
        if( !isSelected )
            return new JLabel( "" + this.value.getTime( ) + " seg" );
        return createSpinner( table, isSelected );
    }

    private JPanel createSpinner( JTable table, boolean isSelected ) {

        long current = value.getTime( );
        long min = 1;
        long max = 99 * 3600;
        long increment = 1;
        JLabel totalTime = new JLabel( "seg" );
        JSpinner timeSpinner = new JSpinner( new SpinnerNumberModel( current, min, max, increment ) );
        timeSpinner.addChangeListener( new TimeSpinnerListener( ) );
        JPanel temp = new JPanel( );
        temp.add( timeSpinner );
        temp.add( totalTime );
        if( isSelected )
            temp.setBackground( table.getSelectionBackground( ) );
        return temp;
    }

    /**
     * Listener for the time spinner
     */
    private class TimeSpinnerListener implements ChangeListener {

        public void stateChanged( ChangeEvent e ) {

            JSpinner timeSpinner = (JSpinner) e.getSource( );
            SpinnerNumberModel model = (SpinnerNumberModel) timeSpinner.getModel( );
            value.setTime( model.getNumber( ).longValue( ) );
        }
    }
}
