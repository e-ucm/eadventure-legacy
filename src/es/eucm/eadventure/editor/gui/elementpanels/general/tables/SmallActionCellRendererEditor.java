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
package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.editor.control.controllers.general.CustomActionDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.EffectsDialog;

public class SmallActionCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 8128260157985286632L;

    private ActionDataControl value;

    public Object getCellEditorValue( ) {

        return value;
    }

    public Component getTableCellEditorComponent( JTable table, Object value2, boolean isSelected, int row, int col ) {

        this.value = (ActionDataControl) value2;
        return createComponent( );
    }

    public Component getTableCellRendererComponent( JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column ) {

        this.value = (ActionDataControl) value2;
        if( table.getSelectedRow( ) == row ) {
            return createComponent( );
        }
        String text = getTypeText( value.getType( ) );
        if( value.hasIdTarget( ) )
            text += value.getIdTarget( );
        return new JLabel( text );
    }

    private Component createComponent( ) {

        JPanel temp = new JPanel( );
        temp.setLayout( new GridBagLayout( ) );
        temp.setBorder( BorderFactory.createLineBorder( Color.BLUE, 2 ) );
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

        if( value.hasIdTarget( ) ) {
            c.gridy++;
            JComboBox combo = new JComboBox( );
            combo = new JComboBox( value.getElementsList( ) );
            combo.setSelectedItem( value.getIdTarget( ) );
            combo.addActionListener( new ActionListener( ) {

                public void actionPerformed( ActionEvent arg0 ) {

                    value.setIdTarget( ( (JComboBox) arg0.getSource( ) ).getSelectedItem( ).toString( ) );
                }
            } );
            temp.add( combo, c );
        }

        if( value.getType( ) == Controller.ACTION_CUSTOM || value.getType( ) == Controller.ACTION_CUSTOM_INTERACT ) {
            c.gridy++;
            JButton appearence = new JButton( "Appearence" );
            appearence.setFocusable( false );
            temp.add( appearence, c );
        }

        c.gridy++;
        JButton conditionsButton = new JButton( "Conditions" );
        conditionsButton.setFocusable( false );
        conditionsButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                new ConditionsDialog( value.getConditions( ) );
            }
        } );
        temp.add( conditionsButton, c );

        c.gridy++;
        JButton effectsButton = new JButton( "Effects" );
        effectsButton.setFocusable( false );
        effectsButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                new EffectsDialog( value.getEffects( ) );
            }
        } );
        temp.add( effectsButton, c );

        c.gridy++;

        final JCheckBox enableNotEff = new JCheckBox( TextConstants.getText( "ActiveAreasList.ActiveWhenConditionsArent" ) );
        final JButton notEffectsButton = new JButton( TextConstants.getText( "Exit.EditNotEffects" ) );

        enableNotEff.setSelected( value.isActivatedNotEffects( ) );
        enableNotEff.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                value.setActivatedNotEffects( enableNotEff.isSelected( ) );
                notEffectsButton.setEnabled( enableNotEff.isSelected( ) );
            }
        } );
        enableNotEff.setSelected( value.isActivatedNotEffects( ) );
        temp.add( enableNotEff, c );

        c.gridy++;
        notEffectsButton.setFocusable( false );
        notEffectsButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                new EffectsDialog( value.getNotEffectsController( ) );
            }
        } );
        notEffectsButton.setEnabled( enableNotEff.isSelected( ) );
        temp.add( notEffectsButton, c );

        c.gridy++;
        final JCheckBox getToCheckBox = new JCheckBox( "Needs get to" );
        getToCheckBox.setSelected( value.getNeedsGoTo( ) );
        if( Controller.getInstance( ).isPlayTransparent( ) )
            getToCheckBox.setEnabled( false );
        temp.add( getToCheckBox, c );

        c.gridy++;
        SpinnerModel sm = new SpinnerNumberModel( value.getKeepDistance( ), 0, 100, 5 );
        final JSpinner keepDistanceSpinner = new JSpinner( sm );
        if( Controller.getInstance( ).isPlayTransparent( ) )
            keepDistanceSpinner.setEnabled( false );
        temp.add( keepDistanceSpinner, c );

        getToCheckBox.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                value.setNeedsGoTo( getToCheckBox.isSelected( ) );
                keepDistanceSpinner.setEnabled( getToCheckBox.isSelected( ) );
            }
        } );
        keepDistanceSpinner.setEnabled( value.getNeedsGoTo( ) );
        keepDistanceSpinner.addChangeListener( new KeepDistanceSpinnerListener( keepDistanceSpinner ) );

        return temp;
    }

    private String getTypeText( int type ) {

        String text = "";
        switch( type ) {
            case Controller.ACTION_EXAMINE:
                text = TextConstants.getText( "ActionsList.ExamineAction" );
                break;
            case Controller.ACTION_GRAB:
                text = TextConstants.getText( "ActionsList.GrabAction" );
                break;
            case Controller.ACTION_USE:
                text = TextConstants.getText( "ActionsList.UseAction" );
                break;
            case Controller.ACTION_CUSTOM:
                text = ( (CustomActionDataControl) value ).getName( );
                break;
            case Controller.ACTION_USE_WITH:
                text = TextConstants.getText( "ActionsList.UseWithAction", "" );
                break;
            case Controller.ACTION_GIVE_TO:
                text = TextConstants.getText( "ActionsList.GiveToAction", "" );
                break;
            case Controller.ACTION_CUSTOM_INTERACT:
                text = ( (CustomActionDataControl) value ).getName( ) + ": ";
                break;
            case Controller.ACTION_TALK_TO:
                text = TextConstants.getText( "ActionsList.TalkToAction" );
                break;
        }
        return text;

    }

    /**
     * Listener for the changes in the keepDistances spinner
     */
    private class KeepDistanceSpinnerListener implements ChangeListener {

        private JSpinner spinner;

        public KeepDistanceSpinnerListener( JSpinner spinner ) {

            this.spinner = spinner;
        }

        public void stateChanged( ChangeEvent arg0 ) {

            value.setKeepDistance( ( (Integer) spinner.getModel( ).getValue( ) ).intValue( ) );
        }
    }

}
