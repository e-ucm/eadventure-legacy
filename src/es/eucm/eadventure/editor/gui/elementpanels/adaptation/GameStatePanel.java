package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
class GameStatePanel extends JPanel {

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
	private JTable actionFlagsTable;

	/**
	 * Scroll panel that holds the table
	 */
	private JScrollPane tableScrollPanel;

	/**
	 * "Insert property" button
	 */
	private JButton insertActionFlagButton;

	/**
	 * "Delete property" button
	 */
	private JButton deleteActionFlagButton;
	
	/**
	 * Combo box for the initial scene
	 */
	private JComboBox initialSceneCB;
	

	/* Methods */

	/**
	 * Constructor
	 * 
	 * @param principalPanel
	 *            Link to the principal panel, for sending signals
	 * @param adaptationRuleDataControl
	 *            Data controller to edit the lines
	 */
	public GameStatePanel( AdaptationRuleDataControl adpDataControl ) {
		// Set the initial values
		this.adaptationRuleDataControl = adpDataControl;

		// Create and set border (titled border in this case)
		border = BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( EtchedBorder.LOWERED ), TextConstants.getText( "AdaptationRule.GameState.Title" ), TitledBorder.CENTER, TitledBorder.TOP );
		setBorder( border );

		// Set a GridBagLayout
		setLayout( new BorderLayout() );

		/* Common elements (for Node and Option panels) */
		// Create the table with an empty model
		actionFlagsTable = new JTable( new NodeTableModel( ) );

		// Column size properties
		actionFlagsTable.setAutoCreateColumnsFromModel( false );
		//propertiesTable.getColumnModel( ).getColumn( 0 ).setMaxWidth( 60 );
		//propertiesTable.getColumnModel( ).getColumn( 1 ).setMaxWidth( 60 );
		

		// Selection properties
		actionFlagsTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		actionFlagsTable.setCellSelectionEnabled( false );
		actionFlagsTable.setColumnSelectionAllowed( false );
		actionFlagsTable.setRowSelectionAllowed( true );

		//Edition of column 0: combo box (activate, deactivate)
		String[] actionValues = new String[]{"activate", "deactivate"};
		JComboBox actionValuesCB = new JComboBox (actionValues);
		actionFlagsTable.getColumnModel( ).getColumn( 0 ).setCellEditor( new DefaultCellEditor( actionValuesCB ) );
		
		//Edition of column 1: combo box (flags list)
		String [] flags = Controller.getInstance( ).getVarFlagSummary( ).getFlags( );
		JComboBox flagsCB = new JComboBox (flags);
		actionFlagsTable.getColumnModel( ).getColumn( 1 ).setCellEditor( new DefaultCellEditor( flagsCB ) );
		// Misc properties
		//propertiesTable.setTableHeader( null );
		actionFlagsTable.setIntercellSpacing( new Dimension( 1, 1 ) );

		// Add selection listener to the table
		actionFlagsTable.getSelectionModel( ).addListSelectionListener( new NodeTableSelectionListener( ) );

		// Table scrollPane
		tableScrollPanel = new JScrollPane( actionFlagsTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
		tableScrollPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( EtchedBorder.LOWERED ), TextConstants.getText( "AdaptationRule.GameState.ActionFlags" ) ));
		/* End of common elements */

		/* Dialogue panel elements */
		//TODO TextConstants
		insertActionFlagButton = new JButton( "Insert statement" );
		insertActionFlagButton.addActionListener( new ListenerButtonInsertLine( ) );
		deleteActionFlagButton = new JButton( "Delete statement" );
		deleteActionFlagButton.addActionListener( new ListenerButtonDeleteLine( ) );
		
		String[] scenes = Controller.getInstance( ).getIdentifierSummary( ).getSceneIds( );
		String[] isValues = new String[scenes.length+1];
		isValues[0] = "< Not selected >";
		for (int i=0; i<scenes.length; i++){
			isValues[i+1]=scenes[i];
		}
		this.initialSceneCB = new JComboBox(isValues);
		
		if (adaptationRuleDataControl.getInitialScene( )==null){
			initialSceneCB.setSelectedIndex( 0 );
		}else{
			initialSceneCB.setSelectedItem( adaptationRuleDataControl.getInitialScene( ) );
		}
		initialSceneCB.addActionListener( new ActionListener(){

			public void actionPerformed( ActionEvent e ) {
				if (initialSceneCB.getSelectedIndex( )>0)
					adaptationRuleDataControl.setInitialScene( initialSceneCB.getSelectedItem( ).toString( ) );
			}
			
		});
		
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
		deleteActionFlagButton.setEnabled( false );

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
		
		c.gridy = 1;

		c.gridy = 2;

		c.gridy = 3;

		
		// Add the insert, delete and edit buttons
		JPanel insertDeletePanel = new JPanel();
		insertDeletePanel.add( insertActionFlagButton );
		insertDeletePanel.add( deleteActionFlagButton );
		//c.fill = GridBagConstraints.BOTH;
		//c.weightx = 0.015;
		//c.gridx = 2;
		//c.gridy = 1;
		//add( insertPropertyButton, c );

		//c.gridy = 2;
		//add( deletePropertyButton, c );
		
		JPanel initialScenePanel = new JPanel();
		initialScenePanel.setLayout( new GridLayout() );
		initialScenePanel.add( initialSceneCB );
		
		initialScenePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AdaptationRule.GameState.InitialScene" ) ) );

		
		add (tablePanel, BorderLayout.CENTER);
		add (insertDeletePanel, BorderLayout.SOUTH);
		add (initialScenePanel, BorderLayout.NORTH);
		
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
			int selectedRow = actionFlagsTable.getSelectedRow( );

			// If no row is selected, set the insertion position at the end
			if( selectedRow == -1 )
				selectedRow = actionFlagsTable.getRowCount( ) - 1;


			// Insert the dialogue line in the selected position
			if (adaptationRuleDataControl.addFlagAction( selectedRow + 1 )){

				// Select the inserted line
				actionFlagsTable.setRowSelectionInterval( selectedRow + 1, selectedRow + 1 );
				actionFlagsTable.scrollRectToVisible( actionFlagsTable.getCellRect( selectedRow + 1, 0, true ) );
	
				// Update the table
				actionFlagsTable.revalidate( );
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
			int selectedRow = actionFlagsTable.getSelectedRow( );

			// Delete the selected line
			adaptationRuleDataControl.deleteFlagAction( selectedRow );

			// If there are no more lines, clear selection (this disables the "Delete line" button)
			if( adaptationRuleDataControl.getFlagActionCount( ) == 0 )
				actionFlagsTable.clearSelection( );

			// If the deleted line was the last one, select the new last line in the node
			else if(adaptationRuleDataControl.getFlagActionCount( ) == selectedRow )
				actionFlagsTable.setRowSelectionInterval( selectedRow - 1, selectedRow - 1 );

			// Update the table
			actionFlagsTable.revalidate( );
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
				deleteActionFlagButton.setEnabled( false );
			}

			// If there is a line selected
			else {
				// Enable all options
				deleteActionFlagButton.setEnabled( true );
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
				name = "Action";
			else if (columnIndex == 1)
				name = "Flag";
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
				rowCount = adaptationRuleDataControl.getFlagActionCount( );

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
			if( value!=null && !value.toString( ).trim( ).equals( "" ) ) {
				// If the name is being edited, and it has really changed
				if( columnIndex == 0 )
					adaptationRuleDataControl.setAction( rowIndex, value.toString( ) );

				// If the text is being edited, and it has really changed
				if( columnIndex == 1 )
					adaptationRuleDataControl.setFlag( rowIndex, value.toString( ) );


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
					value = adaptationRuleDataControl.getAction( rowIndex );
					break;
				case 1:
					// Property value
					value = adaptationRuleDataControl.getFlag( rowIndex );
					break;
			}

			return value;
		}
	}
}
