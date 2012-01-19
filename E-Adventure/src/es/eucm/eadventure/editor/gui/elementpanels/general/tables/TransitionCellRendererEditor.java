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
package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

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
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;

public class TransitionCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 4870491701696223525L;

    private JComboBox combo;

    private ExitDataControl exit;

    private JSpinner spinner;

    public Component getTableCellEditorComponent( JTable table, Object value, boolean isSelected, int row, int col ) {

        this.exit = (ExitDataControl) value;
        return comboPanel( table );
    }

    public Object getCellEditorValue( ) {

        return exit;
    }

    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {

        this.exit = (ExitDataControl) value;
        if( !isSelected ) {
            switch( exit.getTransitionType( ) ) {
                case 0:
                    return new JLabel( TC.get( "Exit.NoTransition" ) );
                case 1:
                    return new JLabel( TC.get( "Exit.TopToBottom" ) );
                case 2:
                    return new JLabel( TC.get( "Exit.BottomToTop" ) );
                case 3:
                    return new JLabel( TC.get( "Exit.LeftToRight" ) );
                case 4:
                    return new JLabel( TC.get( "Exit.RightToLeft" ) );
                case 5:
                    return new JLabel( TC.get( "Exit.FadeIn" ) );
                default:
            }
            return new JLabel( "" + exit.getTransitionType( ) );
        }
        else {
            return comboPanel( table );
        }
    }

    private JPanel comboPanel( JTable table ) {

        JPanel tempPanel = new JPanel( );
        tempPanel.setLayout( new GridBagLayout( ) );
        tempPanel.setBorder( BorderFactory.createMatteBorder( 2, 0, 2, 0, table.getSelectionBackground( ) ) );

        GridBagConstraints c = new GridBagConstraints( );

        combo = new JComboBox( );
        combo.addItem( makeObj( TC.get( "Exit.NoTransition" ) ) );
        combo.addItem( makeObj( TC.get( "Exit.TopToBottom" ) ) );
        combo.addItem( makeObj( TC.get( "Exit.BottomToTop" ) ) );
        combo.addItem( makeObj( TC.get( "Exit.LeftToRight" ) ) );
        combo.addItem( makeObj( TC.get( "Exit.RightToLeft" ) ) );
        combo.addItem( makeObj( TC.get( "Exit.FadeIn" ) ) );
        combo.setSelectedIndex( exit.getTransitionType( ) );

        combo.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                exit.setTransitionType( combo.getSelectedIndex( ) );
                if( combo.getSelectedIndex( ) == 0 )
                    spinner.setEnabled( false );
                else
                    spinner.setEnabled( true );
            }
        } );

        SpinnerModel sm = new SpinnerNumberModel( exit.getTransitionTime( ), 0, 5000, 100 );
        spinner = new JSpinner( sm );
        spinner.addChangeListener( new ChangeListener( ) {

            public void stateChanged( ChangeEvent arg0 ) {

                if( ( (Integer) spinner.getValue( ) ).intValue( ) == 900 )
                    spinner.setValue( new Integer( 0 ) );
                else if( ( (Integer) spinner.getValue( ) ).intValue( ) == 100 )
                    spinner.setValue( new Integer( 1000 ) );
                exit.setTransitionTime( (Integer) spinner.getValue( ) );
            }
        } );
        spinner.setEnabled( combo.getSelectedIndex( ) != 0 );

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 2.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        tempPanel.add( combo, c );

        c.gridy++;
        tempPanel.add( spinner, c );

        return tempPanel;
    }

    private Object makeObj( final String item ) {

        return new Object( ) {

            @Override
            public String toString( ) {

                return item;
            }
        };
    }

}
