package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
import javax.swing.table.TableCellEditor;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationRuleDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentRuleDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;

/**
 * This class is the panel used to display and edit nodes. It holds node operations, like adding and removing lines,
 * editing end effects, remove links and reposition lines and children
 */
class UOLPropertiesPanel extends JPanel {

	/**
	 * Required
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Adaptation rule data controller.
	 */
	private AdaptationRuleDataControl adaptationRuleDataControl;


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
	

	/* Methods */

	/**
	 * Constructor
	 * 
	 * @param principalPanel
	 *            Link to the principal panel, for sending signals
	 * @param adaptationRuleDataControl
	 *            Data controller to edit the lines
	 */
	public UOLPropertiesPanel( AdaptationRuleDataControl adpDataControl ) {
		// Set the initial values
		this.adaptationRuleDataControl = adpDataControl;

		// Create and set border (titled border in this case)
		border = BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( EtchedBorder.LOWERED ), TextConstants.getText( "AdaptationRule.UOLState.Title" ), TitledBorder.CENTER, TitledBorder.TOP );
		setBorder( border );

		// Set a GridBagLayout
		setLayout( new BorderLayout() );

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

		// Misc properties
		//propertiesTable.setTableHeader( null );
		propertiesTable.setIntercellSpacing( new Dimension( 1, 1 ) );

		// Add selection listener to the table
		propertiesTable.getSelectionModel( ).addListSelectionListener( new NodeTableSelectionListener( ) );

		// Table scrollPane
		tableScrollPanel = new JScrollPane( propertiesTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );

		// Up and down buttons
		movePropertyUpButton = new JButton( new ImageIcon("img/icons/moveNodeUp.png"));
		movePropertyUpButton.addActionListener( new ListenerButtonMoveLineUp( ) );
		movePropertyDownButton = new JButton( new ImageIcon("img/icons/moveNodeDown.png") );
		movePropertyDownButton.addActionListener( new ListenerButtonMoveLineDown( ) );
		/* End of common elements */

		/* Dialogue panel elements */
		//TODO TextConstants
		insertPropertyButton = new JButton( "Insert property" );
		insertPropertyButton.addActionListener( new ListenerButtonInsertLine( ) );
		deletePropertyButton = new JButton( "Delete property" );
		deletePropertyButton.addActionListener( new ListenerButtonDeleteLine( ) );
		/* End of dialogue panel elements */

		addComponents();
	}

	/**
	 * Removes all elements in the panel, and sets a dialogue node panel
	 */
	private void addComponents( ) {
		// Remove all elements
		removeAll( );

		// Disable all buttons
		movePropertyUpButton.setEnabled( false );
		movePropertyDownButton.setEnabled( false );
		deletePropertyButton.setEnabled( false );

		// Create constraints
		GridBagConstraints c = new GridBagConstraints( );

		// Add the scroll panel (with the table)
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout( new GridBagLayout() );
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets( 2, 2, 2, 2 );
		c.weightx = 0.98;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 4;
		tablePanel.add( tableScrollPanel, c );

		// Add the up and down buttons
		c.fill = GridBagConstraints.NONE;
		//c.weightx = 0.005;
		c.weightx=0; c.weighty=0.25;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		tablePanel.add( movePropertyUpButton, c );
		
		c.gridy = 1;
		tablePanel.add( new JFiller(), c );

		c.gridy = 2;
		tablePanel.add( new JFiller(), c );

		c.gridy = 3;
		tablePanel.add( movePropertyDownButton, c );

		
		// Add the insert, delete and edit buttons
		JPanel insertDeletePanel = new JPanel();
		insertDeletePanel.add( insertPropertyButton );
		insertDeletePanel.add( deletePropertyButton );
		//c.fill = GridBagConstraints.BOTH;
		//c.weightx = 0.015;
		//c.gridx = 2;
		//c.gridy = 1;
		//add( insertPropertyButton, c );

		//c.gridy = 2;
		//add( deletePropertyButton, c );
		
		add (tablePanel, BorderLayout.CENTER);
		add (insertDeletePanel, BorderLayout.SOUTH);
		
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
			if( adaptationRuleDataControl.moveUOLPropertyUp( selectedRow ) ) {

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
			if( adaptationRuleDataControl.moveUOLPropertyDown( selectedRow ) ) {

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
			adaptationRuleDataControl.addBlankUOLProperty( selectedRow + 1 );

			// Select the inserted line
			propertiesTable.setRowSelectionInterval( selectedRow + 1, selectedRow + 1 );
			propertiesTable.scrollRectToVisible( propertiesTable.getCellRect( selectedRow + 1, 0, true ) );

			// Update the table
			propertiesTable.revalidate( );
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
			adaptationRuleDataControl.deleteUOLProperty( selectedRow );

			// If there are no more lines, clear selection (this disables the "Delete line" button)
			if( adaptationRuleDataControl.getUOLPropertyCount( ) == 0 )
				propertiesTable.clearSelection( );

			// If the deleted line was the last one, select the new last line in the node
			else if(adaptationRuleDataControl.getUOLPropertyCount( ) == selectedRow )
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
		public NodeTableModel(  ) {
		}

		public String getColumnName ( int columnIndex ){
			String name = "";
			if (columnIndex == 0)
				name = "Id";
			else if (columnIndex == 1)
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
			if( adaptationRuleDataControl != null )
				rowCount = adaptationRuleDataControl.getUOLPropertyCount( );

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
		public Class<?> getColumnClass( int c ) {
			return getValueAt( 0, c ).getClass( );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#isCellEditable(int, int)
		 */
		public boolean isCellEditable( int rowIndex, int columnIndex ) {
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
		 */
		public void setValueAt( Object value, int rowIndex, int columnIndex ) {

			// If the value isn't an empty string
			if( !value.toString( ).trim( ).equals( "" ) ) {
				// If the name is being edited, and it has really changed
				if( columnIndex == 0 )
					adaptationRuleDataControl.setUOLPropertyId( rowIndex, value.toString( ) );

				// If the text is being edited, and it has really changed
				if( columnIndex == 1 )
					adaptationRuleDataControl.setUOLPropertyValue( rowIndex, value.toString( ) );


				fireTableCellUpdated( rowIndex, columnIndex );
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
					value = adaptationRuleDataControl.getUOLPropertyId ( rowIndex );
					break;
				case 1:
					// Property value
					value = adaptationRuleDataControl.getUOLPropertyValue ( rowIndex );
					break;
			}

			return value;
		}
	}
}
