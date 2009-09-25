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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.config.SCORMConfigData;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentRuleDataControl;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;

/**
 * This class is the panel used to display and edit nodes. It holds node
 * operations, like adding and removing lines, editing end effects, remove links
 * and reposition lines and children
 */
class AssessmentPropertiesPanel extends JPanel {

    /**
     * Required
     */
    private static final long serialVersionUID = 1L;

    /**
     * Assessment rule data controller.
     */
    private AssessmentRuleDataControl assessmentRuleDataControl;

    /**
     * Border of the panel
     */
    private TitledBorder border;

    /**
     * Table in which the node lines are represented
     */
    private JTable propertiesTable;

    /**
     * Scroll panel that holds the table
     */
    private JScrollPane tableScrollPanel;

    /**
     * Move property up ( /\ ) button
     */
    private JButton movePropertyUpButton;

    /**
     * Move property down ( \/ ) button
     */
    private JButton movePropertyDownButton;

    /**
     * "Insert property" button
     */
    private JButton insertPropertyButton;

    /**
     * "Delete property" button
     */
    private JButton deletePropertyButton;

    private int currentIndex;

    /* Methods */

    /**
     * Constructor
     * 
     * @param assessmentRuleDataControl
     *            Data controller to edit the lines
     * 
     * @param scorm12
     *            Show if it is a Scorm 1.2 profile, to take its data model
     * 
     * @param scorm2004
     *            Show if it is a Scorm 2004 profile, to take its data model
     */
    public AssessmentPropertiesPanel( AssessmentRuleDataControl assDataControl, boolean scorm12, boolean scorm2004 ) {

        // Set the initial values
        this.assessmentRuleDataControl = assDataControl;

        // Create and set border (titled border in this case)
        border = BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( EtchedBorder.LOWERED ), TC.get( "AssessmentRule.Effect.PropertiesTitle" ), TitledBorder.CENTER, TitledBorder.TOP );
        setBorder( border );

        // Set a GridBagLayout
        setLayout( new BorderLayout( ) );

        /* Common elements (for Node and Option panels) */
        // Create the table with an empty model
        propertiesTable = new JTable( new NodeTableModel( ) );

        // Column size properties
        propertiesTable.setAutoCreateColumnsFromModel( false );
        //propertiesTable.getColumnModel( ).getColumn( 0 ).setMaxWidth( 60 );
        //propertiesTable.getColumnModel( ).getColumn( 1 ).setMaxWidth( 60 );

        // Selection properties
        propertiesTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        propertiesTable.setCellSelectionEnabled( false );
        propertiesTable.setColumnSelectionAllowed( false );
        propertiesTable.setRowSelectionAllowed( true );

        if( scorm12 ) {
            //Edition of column 0: combo box (activate, deactivate)
            JComboBox actionValuesCB = new JComboBox( takesCorrectElements( SCORMConfigData.getPartsOfModel12( ), false ) );
            propertiesTable.getColumnModel( ).getColumn( 0 ).setCellEditor( new DefaultCellEditor( actionValuesCB ) );
        }

        if( scorm2004 ) {
            //Edition of column 0: combo box (activate, deactivate)
            JComboBox actionValuesCB = new JComboBox( takesCorrectElements( SCORMConfigData.getPartsOfModel2004( ), true ) );
            propertiesTable.getColumnModel( ).getColumn( 0 ).setCellEditor( new DefaultCellEditor( actionValuesCB ) );

        }

        // Misc properties
        //propertiesTable.setTableHeader( null );
        propertiesTable.setIntercellSpacing( new Dimension( 1, 1 ) );

        // Add selection listener to the table
        propertiesTable.getSelectionModel( ).addListSelectionListener( new NodeTableSelectionListener( ) );

        // Table scrollPane
        tableScrollPanel = new JScrollPane( propertiesTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );

        // Up and down buttons
        movePropertyUpButton = new JButton( new ImageIcon( "img/icons/moveNodeUp.png" ) );
        movePropertyUpButton.setContentAreaFilled( false );
        movePropertyUpButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        movePropertyUpButton.setBorder( BorderFactory.createEmptyBorder( ) );
        movePropertyUpButton.setToolTipText( TC.get( "AssessmentProperties.MoveUp" ) );
        movePropertyUpButton.addActionListener( new ListenerButtonMoveLineUp( ) );
        movePropertyUpButton.setEnabled( false );

        movePropertyDownButton = new JButton( new ImageIcon( "img/icons/moveNodeDown.png" ) );
        movePropertyDownButton.setContentAreaFilled( false );
        movePropertyDownButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        movePropertyDownButton.setBorder( BorderFactory.createEmptyBorder( ) );
        movePropertyDownButton.setToolTipText( TC.get( "AssessmentProperties.MoveDown" ) );
        movePropertyDownButton.addActionListener( new ListenerButtonMoveLineDown( ) );
        movePropertyDownButton.setEnabled( false );

        /* End of common elements */

        /* Dialogue panel elements */
        insertPropertyButton = new JButton( new ImageIcon( "img/icons/addNode.png" ) );
        insertPropertyButton.setContentAreaFilled( false );
        insertPropertyButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        insertPropertyButton.setBorder( BorderFactory.createEmptyBorder( ) );
        insertPropertyButton.setToolTipText( TC.get( "AssessmentProperties.InsertProperty" ) );
        insertPropertyButton.addActionListener( new ListenerButtonInsertLine( ) );

        deletePropertyButton = new JButton( new ImageIcon( "img/icons/deleteNode.png" ) );
        deletePropertyButton.setContentAreaFilled( false );
        deletePropertyButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        deletePropertyButton.setBorder( BorderFactory.createEmptyBorder( ) );
        deletePropertyButton.setToolTipText( TC.get( "AssessmentProperties.DeleteProperty" ) );
        deletePropertyButton.addActionListener( new ListenerButtonDeleteLine( ) );

        addComponents( );
    }

    /**
     * Only takes the elements that can be written
     * 
     * @param dataModel
     *            All scorm data model elements
     * @param s2004
     *            It is Scorm 2004?
     * @return The elements that can be written
     */
    private String[] takesCorrectElements( ArrayList<String> dataModel, boolean s2004 ) {

        ArrayList<String> canWrite = new ArrayList<String>( );
        // the format of scorm data model in datamodel.xml and datamodel2004.xml:
        // <entry key="part of scorm data model">#"can be read (0 or 1)"#"can be write (0 or 1)"#"Data type"</entry>
        for( int i = 0; i < dataModel.size( ); i++ ) {
            if( s2004 ) {
                if( SCORMConfigData.getProperty2004( dataModel.get( i ) ).charAt( 1 ) == '1' ) {
                    canWrite.add( dataModel.get( i ) );
                }
            }
            else {
                if( SCORMConfigData.getProperty12( dataModel.get( i ) ).charAt( 1 ) == '1' ) {
                    canWrite.add( dataModel.get( i ) );
                }
            }
        }
        String[] result = new String[ canWrite.size( ) ];
        return canWrite.toArray( result );

    }

    /**
     * Removes all elements in the panel, and sets a dialogue node panel
     */
    private void addComponents( ) {

        removeAll( );
        movePropertyUpButton.setEnabled( false );
        movePropertyDownButton.setEnabled( false );
        deletePropertyButton.setEnabled( false );

        add( tableScrollPanel, BorderLayout.CENTER );

        JPanel buttonsPanel = new JPanel( );
        buttonsPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.gridx = 0;
        c.gridy = 0;
        buttonsPanel.add( insertPropertyButton, c );
        c.gridy = 1;
        buttonsPanel.add( movePropertyUpButton, c );
        c.gridy = 2;
        buttonsPanel.add( movePropertyDownButton, c );
        c.gridy = 4;
        buttonsPanel.add( deletePropertyButton, c );
        c.gridy = 3;
        c.fill = GridBagConstraints.VERTICAL;
        c.weighty = 2.0;
        buttonsPanel.add( new JFiller( ), c );

        add( buttonsPanel, BorderLayout.EAST );
    }

    /**
     * Listener for the move line up ( /\ ) button
     */
    private class ListenerButtonMoveLineUp implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            // Take the selected row, and the selected node
            int selectedRow = propertiesTable.getSelectedRow( );

            // If the line was moved
            if( assessmentRuleDataControl.movePropertyUp( selectedRow, currentIndex ) ) {

                // Move the selection along with the line
                propertiesTable.setRowSelectionInterval( selectedRow - 1, selectedRow - 1 );
                propertiesTable.scrollRectToVisible( propertiesTable.getCellRect( selectedRow - 1, 0, true ) );

            }
        }
    }

    /**
     * Listener for the move line down ( \/ ) button
     */
    private class ListenerButtonMoveLineDown implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            // Take the selected row, and the selected node
            int selectedRow = propertiesTable.getSelectedRow( );

            // If the line was moved
            if( assessmentRuleDataControl.movePropertyDown( selectedRow, currentIndex ) ) {

                // Move the selection along with the line
                propertiesTable.setRowSelectionInterval( selectedRow + 1, selectedRow + 1 );
                propertiesTable.scrollRectToVisible( propertiesTable.getCellRect( selectedRow + 1, 0, true ) );

            }
        }
    }

    /**
     * Listener for the "Insert property" button
     */
    private class ListenerButtonInsertLine implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            // Take the selected row, and the selected node
            int selectedRow = propertiesTable.getSelectedRow( );

            // If no row is selected, set the insertion position at the end
            if( selectedRow == -1 )
                selectedRow = propertiesTable.getRowCount( ) - 1;

            // Insert the dialogue line in the selected position
            if( assessmentRuleDataControl.addBlankProperty( selectedRow + 1, currentIndex ) ) {

                // Select the inserted line
                propertiesTable.setRowSelectionInterval( selectedRow + 1, selectedRow + 1 );
                propertiesTable.scrollRectToVisible( propertiesTable.getCellRect( selectedRow + 1, 0, true ) );

                // Update the table
                propertiesTable.revalidate( );
            }
        }
    }

    /**
     * Listener for the "Delete line" button
     */
    private class ListenerButtonDeleteLine implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent e ) {

            // Take the selected row, and the selected node
            int selectedRow = propertiesTable.getSelectedRow( );

            // Delete the selected line
            assessmentRuleDataControl.deleteProperty( selectedRow, currentIndex );

            // If there are no more lines, clear selection (this disables the "Delete line" button)
            if( assessmentRuleDataControl.getPropertyCount( currentIndex ) == 0 )
                propertiesTable.clearSelection( );

            // If the deleted line was the last one, select the new last line in the node
            else if( assessmentRuleDataControl.getPropertyCount( currentIndex ) == selectedRow )
                propertiesTable.setRowSelectionInterval( selectedRow - 1, selectedRow - 1 );

            // Update the table
            propertiesTable.revalidate( );
        }
    }

    /**
     * Private class managing the selection listener of the table
     */
    private class NodeTableSelectionListener implements ListSelectionListener {

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
         */
        public void valueChanged( ListSelectionEvent e ) {

            // Extract the selection model of the list
            ListSelectionModel lsm = (ListSelectionModel) e.getSource( );

            // If there is no line selected
            if( lsm.isSelectionEmpty( ) ) {
                // Disable all options
                movePropertyUpButton.setEnabled( false );
                movePropertyDownButton.setEnabled( false );
                deletePropertyButton.setEnabled( false );
            }

            // If there is a line selected
            else {
                // Enable all options
                movePropertyUpButton.setEnabled( true );
                movePropertyDownButton.setEnabled( true );
                deletePropertyButton.setEnabled( true );
            }
        }
    }

    /**
     * Private class containing the model for the line table
     */
    private class NodeTableModel extends AbstractTableModel {

        /**
         * Required
         */
        private static final long serialVersionUID = 1L;

        /**
         * Constructor
         */
        public NodeTableModel( ) {

        }

        @Override
        public String getColumnName( int columnIndex ) {

            String name = "";
            if( columnIndex == 0 )
                name = "Id";
            else if( columnIndex == 1 )
                name = "Value";
            return name;
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.table.TableModel#getRowCount()
         */
        public int getRowCount( ) {

            int rowCount = 0;

            // If there is a node, the number of rows is the same as the number of lines
            if( assessmentRuleDataControl != null )
                rowCount = assessmentRuleDataControl.getPropertyCount( currentIndex );

            return rowCount;
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.table.TableModel#getColumnCount()
         */
        public int getColumnCount( ) {

            // All line tables has three columns
            return 2;
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.table.TableModel#getColumnClass(int)
         */
        @Override
        public Class<?> getColumnClass( int c ) {

            return getValueAt( 0, c ).getClass( );
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.table.TableModel#isCellEditable(int, int)
         */
        @Override
        public boolean isCellEditable( int rowIndex, int columnIndex ) {

            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
         */
        @Override
        public void setValueAt( Object value, int rowIndex, int columnIndex ) {

            if( value != null ) {
                // If the value isn't an empty string
                if( !value.toString( ).trim( ).equals( "" ) ) {
                    // If the name is being edited, and it has really changed
                    if( columnIndex == 0 )
                        assessmentRuleDataControl.setPropertyId( rowIndex, currentIndex, value.toString( ) );

                    // If the text is being edited, and it has really changed
                    if( columnIndex == 1 )
                        assessmentRuleDataControl.setPropertyValue( rowIndex, currentIndex, value.toString( ) );

                    fireTableCellUpdated( rowIndex, columnIndex );
                }
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
        public Object getValueAt( int rowIndex, int columnIndex ) {

            Object value = null;

            // Return value depending of the selected row
            switch( columnIndex ) {
                case 0:
                    // Id of the property
                    value = assessmentRuleDataControl.getPropertyId( rowIndex, currentIndex );
                    break;
                case 1:
                    // Property value
                    value = assessmentRuleDataControl.getPropertyValue( rowIndex, currentIndex );
                    break;
            }

            return value;
        }
    }

    /**
     * @return the currentIndex
     */
    public int getCurrentIndex( ) {

        return currentIndex;
    }

    /**
     * @param currentIndex
     *            the currentIndex to set
     */
    public void setCurrentIndex( int currentIndex ) {

        this.currentIndex = currentIndex;
        propertiesTable.setEditingRow( -1 );
        propertiesTable.setEditingColumn( -1 );
        propertiesTable.updateUI( );
        propertiesTable.clearSelection( );
    }
}
