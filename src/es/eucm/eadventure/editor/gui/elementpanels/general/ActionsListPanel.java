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
package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ActionsListDataControl;
import es.eucm.eadventure.editor.control.controllers.general.CustomActionDataControl;
import es.eucm.eadventure.editor.control.tools.general.actions.AddActionTool;
import es.eucm.eadventure.editor.control.tools.general.actions.DeleteActionTool;
import es.eucm.eadventure.editor.control.tools.general.actions.DuplicateActionTool;
import es.eucm.eadventure.editor.gui.DataControlsPanel;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ActionsTable;

public class ActionsListPanel extends JPanel implements DataControlsPanel, Updateable {

    private static final long serialVersionUID = 1L;

    protected ActionsListDataControl dataControl;

    private JPanel actionPropertiesPanel;

    protected JButton deleteButton;

    protected JButton duplicateButton;

    protected JButton moveUpButton;

    protected JButton moveDownButton;

    protected JTable table;

    protected JPanel actionPanel;

    private static final int HORIZONTAL_SPLIT_POSITION = 140;

    /**
     * Constructor.
     * 
     * @param actionsListDataControl
     *            Actions list controller
     */
    public ActionsListPanel( ActionsListDataControl actionsListDataControl ) {

        this.dataControl = actionsListDataControl;

        setLayout( new BorderLayout( ) );

        actionPropertiesPanel = new JPanel( );
        actionPropertiesPanel.setLayout( new BorderLayout( ) );
        JPanel tablePanel = createTablePanel( );

        JSplitPane tableWithSplit = new JSplitPane( JSplitPane.VERTICAL_SPLIT, tablePanel, actionPropertiesPanel );
        tableWithSplit.setOneTouchExpandable( true );
        tableWithSplit.setDividerLocation( HORIZONTAL_SPLIT_POSITION );
        tableWithSplit.setContinuousLayout( true );
        tableWithSplit.setResizeWeight( 0.5 );
        tableWithSplit.setDividerSize( 10 );

        add( tableWithSplit, BorderLayout.CENTER );
    }

    private JPanel createTablePanel( ) {

        JPanel tablePanel = new JPanel( new BorderLayout( ) );

        table = new ActionsTable( dataControl );

        tablePanel.add( new TableScrollPane( table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ), BorderLayout.CENTER );

        table.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent e ) {

                updateSelectedAction( );
            }
        } );

        //Create the buttons panel (SOUTH)
        JPanel buttonsPanel = new JPanel( );
        JButton newButton = new JButton( new ImageIcon( "img/icons/addNode.png" ) );
        newButton.setContentAreaFilled( false );
        newButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        newButton.setBorder( BorderFactory.createEmptyBorder( ) );
        newButton.setToolTipText( TextConstants.getText( "ItemReferenceTable.AddParagraph" ) );
        newButton.addMouseListener( new MouseAdapter( ) {

            @Override
            public void mouseClicked( MouseEvent evt ) {

                JPopupMenu menu = getAddChildPopupMenu( );
                menu.show( evt.getComponent( ), evt.getX( ), evt.getY( ) );
            }
        } );

        deleteButton = new JButton( new ImageIcon( "img/icons/deleteNode.png" ) );
        deleteButton.setContentAreaFilled( false );
        deleteButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        deleteButton.setBorder( BorderFactory.createEmptyBorder( ) );
        deleteButton.setToolTipText( TextConstants.getText( "ActionsList.Delete" ) );
        deleteButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                delete( );
            }
        } );
        deleteButton.setEnabled( false );

        duplicateButton = new JButton( new ImageIcon( "img/icons/duplicateNode.png" ) );
        duplicateButton.setContentAreaFilled( false );
        duplicateButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        duplicateButton.setBorder( BorderFactory.createEmptyBorder( ) );
        duplicateButton.setToolTipText( TextConstants.getText( "ActionsList.Duplicate" ) );
        duplicateButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                duplicate( );
            }
        } );
        duplicateButton.setEnabled( false );

        moveUpButton = new JButton( new ImageIcon( "img/icons/moveNodeUp.png" ) );
        moveUpButton.setContentAreaFilled( false );
        moveUpButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        moveUpButton.setBorder( BorderFactory.createEmptyBorder( ) );
        moveUpButton.setToolTipText( TextConstants.getText( "ItemReferenceTable.MoveUp" ) );
        moveUpButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                moveUp( );
            }
        } );
        moveUpButton.setEnabled( false );
        moveDownButton = new JButton( new ImageIcon( "img/icons/moveNodeDown.png" ) );
        moveDownButton.setContentAreaFilled( false );
        moveDownButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        moveDownButton.setBorder( BorderFactory.createEmptyBorder( ) );
        moveDownButton.setToolTipText( TextConstants.getText( "ItemReferenceTable.MoveDown" ) );
        moveDownButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                moveDown( );
            }
        } );
        moveDownButton.setEnabled( false );

        buttonsPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.gridx = 0;
        c.gridy = 0;
        buttonsPanel.add( newButton, c );
        c.gridy = 1;
        buttonsPanel.add( duplicateButton, c );
        c.gridy = 2;
        buttonsPanel.add( moveUpButton, c );
        c.gridy = 3;
        buttonsPanel.add( moveDownButton, c );
        c.gridy = 5;
        buttonsPanel.add( deleteButton, c );
        c.gridy = 4;
        c.weighty = 2.0;
        c.fill = GridBagConstraints.VERTICAL;
        buttonsPanel.add( new JFiller( ), c );

        tablePanel.add( buttonsPanel, BorderLayout.EAST );
        return tablePanel;
    }

    private void updateSelectedAction( ) {

        int selectedAction = table.getSelectedRow( );
        actionPropertiesPanel.removeAll( );

        if( selectedAction != -1 && selectedAction < dataControl.getActions( ).size( ) ) {
            ActionDataControl action = dataControl.getActions( ).get( selectedAction );
            if( action instanceof CustomActionDataControl ) {
                actionPanel = new CustomActionPropertiesPanel( (CustomActionDataControl) action );
                actionPropertiesPanel.add( actionPanel, BorderLayout.CENTER );
            }
            else if( action instanceof ActionDataControl ) {
                actionPanel = new ActionPropertiesPanel( action );
                actionPropertiesPanel.add( actionPanel, BorderLayout.CENTER );
            }
            actionPropertiesPanel.updateUI( );
            deleteButton.setEnabled( true );
            duplicateButton.setEnabled( true );
            //Enable moveUp and moveDown buttons when there is more than one element
            moveUpButton.setEnabled( dataControl.getActions( ).size( ) > 1 && selectedAction > 0 );
            moveDownButton.setEnabled( dataControl.getActions( ).size( ) > 1 && selectedAction < table.getRowCount( ) - 1 );
        }
        else {
            actionPropertiesPanel.removeAll( );
            actionPanel = null;
            actionPropertiesPanel.updateUI( );
            deleteButton.setEnabled( false );
            duplicateButton.setEnabled( false );
            moveUpButton.setEnabled( false );
            moveDownButton.setEnabled( false );
        }
    }

    /**
     * Returns a popup menu with the add operations.
     * 
     * @return Popup menu with child adding operations
     */
    public JPopupMenu getAddChildPopupMenu( ) {

        JPopupMenu addChildPopupMenu = new JPopupMenu( );

        for( final int type : dataControl.getAddableElements( ) ) {
            JMenuItem addChildMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.AddElement" + type ) );
            addChildMenuItem.setEnabled( true );
            addChildMenuItem.addActionListener( new ActionListener( ) {

                public void actionPerformed( ActionEvent arg0 ) {

                    Controller.getInstance( ).addTool( new AddActionTool( dataControl, type ) );
                    ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );
                    table.changeSelection( table.getRowCount( ) - 1, table.getRowCount( ) - 1, false, false );
                }
            } );
            addChildPopupMenu.add( addChildMenuItem );
        }

        return addChildPopupMenu;
    }

    protected void duplicate( ) {

        Controller.getInstance( ).addTool( new DuplicateActionTool( dataControl, table.getSelectedRow( ) ) );

        ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );
        table.changeSelection( dataControl.getActions( ).size( ) - 1, 0, false, false );
    }

    protected void delete( ) {

        Controller.getInstance( ).addTool( new DeleteActionTool( dataControl, table.getSelectedRow( ) ) );
        ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );
    }

    protected void moveUp( ) {

        int selectedRow = table.getSelectedRow( );
        table.clearSelection( );
        dataControl.moveElementUp( dataControl.getActions( ).get( selectedRow ) );
        ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );
        table.changeSelection( selectedRow - 1, selectedRow - 1, false, false );
        table.editCellAt( selectedRow + 1, 0 );
    }

    protected void moveDown( ) {

        int selectedRow = table.getSelectedRow( );
        table.clearSelection( );
        dataControl.moveElementDown( dataControl.getActions( ).get( selectedRow ) );
        ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );
        table.changeSelection( selectedRow + 1, selectedRow + 1, false, false );
        table.editCellAt( selectedRow + 1, 0 );
    }

    public void setSelectedItem( List<Searchable> path ) {

        if( path.size( ) > 0 ) {
            for( int i = 0; i < dataControl.getActions( ).size( ); i++ ) {
                if( dataControl.getActions( ).get( i ) == path.get( path.size( ) - 1 ) )
                    table.changeSelection( i, i, false, false );
            }
        }
    }

    public boolean updateFields( ) {

        int index = 0;
        if( actionPanel instanceof CustomActionPropertiesPanel )
            index = ( (CustomActionPropertiesPanel) actionPanel ).getSelectedIndex( );

        int selected = table.getSelectedRow( );
        int items = table.getRowCount( );
        if( table.getCellEditor( ) != null )
            table.getCellEditor( ).cancelCellEditing( );

        ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );

        if( items == table.getRowCount( ) ) {
            if( selected != -1 ) {
                table.changeSelection( selected, 0, false, false );
                boolean updated = false;
                if( actionPanel != null && actionPanel instanceof Updateable ) {
                    updated = ( (Updateable) actionPanel ).updateFields( );
                }
                if( !updated && actionPanel != null ) {
                    actionPropertiesPanel.removeAll( );
                    if( selected != -1 && selected < dataControl.getActions( ).size( ) ) {
                        ActionDataControl action = dataControl.getActions( ).get( selected );
                        if( action instanceof CustomActionDataControl ) {
                            actionPanel = new CustomActionPropertiesPanel( (CustomActionDataControl) action );
                            ( (CustomActionPropertiesPanel) actionPanel ).setSelectedIndex( index );
                            actionPropertiesPanel.add( actionPanel, BorderLayout.CENTER );
                        }
                        else if( action instanceof ActionDataControl ) {
                            actionPanel = new ActionPropertiesPanel( action );
                            actionPropertiesPanel.add( actionPanel, BorderLayout.CENTER );
                        }
                    }
                }
            }
        }

        return true;
    }
}
