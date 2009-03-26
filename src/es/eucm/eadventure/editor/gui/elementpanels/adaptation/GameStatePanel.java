package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationRuleDataControl;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.editdialogs.VarDialog;

/**
 * This class is the panel used to display and edit nodes. It holds node operations, like adding and removing lines,
 * editing end effects, remove links and reposition lines and children
 */
class GameStatePanel extends JPanel implements Updateable{

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
	
	/**
	 * Combo box to show the flags and vars in chapter
	 */
	private JComboBox flagsCB;

	
	

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
		actionFlagsTable.getColumnModel( ).getColumn( 0 ).setMaxWidth( 60 );
		actionFlagsTable.getColumnModel( ).getColumn( 1 ).setMaxWidth( 60 );
		
		
		// Selection properties
		actionFlagsTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		actionFlagsTable.setCellSelectionEnabled( false );
		actionFlagsTable.setColumnSelectionAllowed( false );
		actionFlagsTable.setRowSelectionAllowed( true );

		
		// Misc properties
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
		isValues[0] = TextConstants.getText("GeneralText.NotSelected");
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
		 * Store if each element in table is flag or variable (true for flags and false for vars)
		 */
		//private ArrayList<Boolean> isFlagVar;
		
		/**
		 * Constructor
		 */
		public NodeTableModel(  ) {
		   // isFlagVar = new ArrayList<Boolean>();
		}

		public String getColumnName ( int columnIndex ){
			String name = "";
			if (columnIndex == 0)
				name = "Var?";
			else if (columnIndex == 1)
				name = "Flag?";
			if (columnIndex == 2)
				name = "Action";
			else if (columnIndex == 3)
				name = "Var/Flag";
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
			// All line tables has four columns
			return 4;
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
			    	// The two first check box aren´t editable
			    	if( columnIndex == 0){
			    	    // if not selected
			    	    if (adaptationRuleDataControl.isFlag(rowIndex)){
			    		
			    		
			    		String[] names = Controller.getInstance( ).getVarFlagSummary( ).getVars();
			    		// take any var if there are at least one 
			    		if (names.length==0){
			    		    Controller.getInstance().showErrorDialog(TextConstants.getText("Error.NoVarsAvailable.Title"), TextConstants.getText("Error.NoVarsAvailable.Message"));
			    		    // change to var
			    		    adaptationRuleDataControl.change(rowIndex, "");
			    		}else 
			    		    // change to var
			    		    adaptationRuleDataControl.change(rowIndex, names[0]);
			    	    }
			    	    
			    		
			    	}
			    
			    	if( columnIndex == 1){
			    	    // if not selected
			    	if (!adaptationRuleDataControl.isFlag(rowIndex)){
			    		
			    		
			    		String[] names = Controller.getInstance( ).getVarFlagSummary( ).getFlags();
			    		// take any flag if there are at least one 
			    		if (names.length==0){
			    		    Controller.getInstance().showErrorDialog(TextConstants.getText("Error.NoFlagsAvailable.Title"), TextConstants.getText("Error.NoFlagsAvailable.Message"));
			    		    // change to flag
			    		    adaptationRuleDataControl.change(rowIndex, "");
			    		}   else
			    		 // change to flag
			    		    adaptationRuleDataControl.change(rowIndex, names[0]);		
			    	    }
			    		
			    	}
			    	
			    	// If the action is being edited, and it has really changed
				if( columnIndex == 2){
				    // if is a "set value" action, ask for that value
				    if (value.toString().equals(AdaptedState.VALUE)){
					VarDialog dialog= new VarDialog(adaptationRuleDataControl.getValueToSet(rowIndex));
					if (!dialog.getValue().equals("error"))
					    adaptationRuleDataControl.setAction( rowIndex, value.toString() + " " +dialog.getValue() );
				    }
				    else 
					adaptationRuleDataControl.setAction( rowIndex, value.toString( ) );
				}

				// If the flag is being edited, and it has really changed
				if( columnIndex == 3 ){
				    if ((Boolean)isFlag(rowIndex,0)){
    				    if (!Controller.getInstance().getVarFlagSummary().existsVar(value.toString( ))){
    					Controller.getInstance().getVarFlagSummary().addVar(value.toString());
    					Controller.getInstance().getVarFlagSummary().addVarReference(value.toString());
    					flagsCB.addItem( value.toString( ) );
    				    }
				    }else if ((Boolean)isFlag(rowIndex,1)){
    				    if (!Controller.getInstance().getVarFlagSummary().existsFlag(value.toString( ))){
        					Controller.getInstance().getVarFlagSummary().addFlag(value.toString());
        					Controller.getInstance().getVarFlagSummary().addFlagReference(value.toString());
        					flagsCB.addItem( value.toString( ) );
        				     }	
				    }
				    adaptationRuleDataControl.setFlag( rowIndex, value.toString( ) );
				}

				fireTableRowsUpdated( rowIndex, rowIndex );
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
				
				case 0: // IsVar 
				    	
				    	value = !adaptationRuleDataControl.isFlag(rowIndex);
				    	setRowEditor(rowIndex,(Boolean)value);
				    	break;
				    	
				case 1: // IsFlag 
				    	value = adaptationRuleDataControl.isFlag(rowIndex);
				    	setRowEditor(rowIndex,(Boolean)value);
				    	break;
			
				case 2:
					// Action
					value = adaptationRuleDataControl.getAction( rowIndex );
					break;
				case 3:
					// Flag/Var name
					value = adaptationRuleDataControl.getFlag( rowIndex );
					break;
			}

			return value;
		}
		
		private boolean isFlag(int rowIndex, int columnIndex){
		    boolean value=false;
		    switch( columnIndex ) {
		    case 0: // IsVar 
		    	
		    	value = !adaptationRuleDataControl.isFlag(rowIndex);
		    	break;
		    	
		    case 1: // IsFlag 
		    	value = adaptationRuleDataControl.isFlag(rowIndex);
		    	break;
		    }
		    return value;
		}
		
		private void setRowEditor(int index, boolean isFlag){
		    	
		    
		    	//Edition of column 2: combo box (activate, deactivate for flags; increment, decrement, set value for vars )
			String[] actionValues;
			if (isFlag)
			    actionValues = new String[]{"activate", "deactivate"};
			else 
			    actionValues = new String[]{"increment", "decrement", "set-value"};
			JComboBox actionValuesCB = new JComboBox (actionValues);
			actionFlagsTable.getColumnModel( ).getColumn( 2 ).setCellEditor( new DefaultCellEditor( actionValuesCB ) );
		
			//Edition of column 3: combo box (flags or var list)
			String [] flagsVars;
			if (isFlag)
			    flagsVars = Controller.getInstance( ).getVarFlagSummary( ).getFlags( );
			else
			    flagsVars = Controller.getInstance( ).getVarFlagSummary( ).getVars();
			flagsCB = new JComboBox (flagsVars);
			flagsCB.setEditable(true);
			actionFlagsTable.getColumnModel( ).getColumn( 3 ).setCellEditor( new DefaultCellEditor( flagsCB ) );
		}
		
		
	}
	
	

	
	
	

	public boolean updateFields() {
		actionFlagsTable.clearSelection();
		actionFlagsTable.updateUI();
		deleteActionFlagButton.setEnabled( false );
		
		String[] scenes = Controller.getInstance( ).getIdentifierSummary( ).getSceneIds( );
		String[] isValues = new String[scenes.length+1];
		isValues[0] = TextConstants.getText("GeneralText.NotSelected");
		for (int i=0; i<scenes.length; i++){
			isValues[i+1]=scenes[i];
		}
		
		this.initialSceneCB.setModel(new DefaultComboBoxModel(isValues) );
		
		if (adaptationRuleDataControl.getInitialScene( )==null){
			initialSceneCB.setSelectedIndex( 0 );
		}else{
			initialSceneCB.setSelectedItem( adaptationRuleDataControl.getInitialScene( ) );
		}
		
		initialSceneCB.updateUI();
		
		return true;
	}
}
