package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.editdialogs.VarDialog;


/**
 * This class is the panel used to display and edit nodes. It holds node operations, like adding and removing lines,
 * editing end effects, remove links and reposition lines and children
 */
class InitialStatePanel extends JPanel{

	/**
	 * Required
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Adaptation rule data controller.
	 */
	private AdaptationProfileDataControl adaptationProfileDataControl;


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
	
	/**
	 * The table model
	 */
	private NodeTableModel tableModel;

	/* Methods */

	/**
	 * Constructor
	 * 
	 * @param adaptationProfileDataControl
	 *            Data controller to edit the lines
	 * @param showExpand
	 * 		To show or not the button expand           
	 */
	public InitialStatePanel( AdaptationProfileDataControl adpDataControl, boolean showExpand ) {
		// Set the initial values
		this.adaptationProfileDataControl = adpDataControl;

		// Create and set border (titled border in this case)
		border = BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( EtchedBorder.LOWERED ), TextConstants.getText( "AdaptationRule.InitialState.Title" ), TitledBorder.CENTER, TitledBorder.TOP );
		setBorder( border );

		// Set a GridBagLayout
		setLayout( new BorderLayout() );

		/* Common elements (for Node and Option panels) */
		// Create the table with an empty model
		tableModel = new NodeTableModel( ) ;
		actionFlagsTable = new JTable( tableModel);

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
		//propertiesTable.setTableHeader( null );
		actionFlagsTable.setIntercellSpacing( new Dimension( 1, 1 ) );

		// Add selection listener to the table
		actionFlagsTable.getSelectionModel( ).addListSelectionListener( new NodeTableSelectionListener( ) );

		// Table scrollPane
		tableScrollPanel = new JScrollPane( actionFlagsTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
		tableScrollPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( EtchedBorder.LOWERED ), TextConstants.getText( "AdaptationRule.InitialState.ActionFlags" ) ));
		/* End of common elements */

		/* Dialogue panel elements */
		//TODO TextConstants
		insertActionFlagButton = new JButton( TextConstants.getText("Operation.AdaptationPanel.InsertButton") );
		insertActionFlagButton.addActionListener( new ListenerButtonInsertLine( ) );
		deleteActionFlagButton = new JButton( TextConstants.getText("Operation.AdaptationPanel.DeleteButton") );
		deleteActionFlagButton.addActionListener( new ListenerButtonDeleteLine( ) );
		
		String[] scenes = Controller.getInstance( ).getIdentifierSummary( ).getSceneIds( );
		String[] isValues = new String[scenes.length+1];
		isValues[0] = TextConstants.getText("GeneralText.NotSelected");
		for (int i=0; i<scenes.length; i++){
			isValues[i+1]=scenes[i];
		}
		this.initialSceneCB = new JComboBox(isValues);
		
		if (adaptationProfileDataControl.getInitialScene( )==null){
			initialSceneCB.setSelectedIndex( 0 );
		}else{
			initialSceneCB.setSelectedItem( adaptationProfileDataControl.getInitialScene( ) );
		}
		initialSceneCB.addActionListener( new ActionListener(){

			public void actionPerformed( ActionEvent e ) {
				if (initialSceneCB.getSelectedIndex( )>0)
					adaptationProfileDataControl.setInitialScene( initialSceneCB.getSelectedItem( ).toString( ) );
			}
			
		});
		
		/* End of dialogue panel elements */

		addComponents(showExpand);
	}

	/**
	 * Removes all elements in the panel, and sets a dialogue node panel
	 */
	private void addComponents(boolean showExpand ) {
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
		if (showExpand){
		    JButton expand = new JButton(TextConstants.getText("AdaptationProfile.Expand"));
		    expand.addActionListener(new ActionListener(){

			@Override
			    public void actionPerformed(ActionEvent e) {
			    
			    new InitialStateDialog();
			}
			
		    });
		    insertDeletePanel.add( expand);
		}
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
		
		initialScenePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AdaptationRule.InitialState.InitialScene" ) ) );

		
		add (tablePanel, BorderLayout.CENTER);
		add (insertDeletePanel, BorderLayout.SOUTH);
		add (initialScenePanel, BorderLayout.NORTH);
		
	}

	/**
	 * Class to show the initial state in a new window
	 */
	 private class InitialStateDialog extends JDialog{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public InitialStateDialog(){
		    super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "AdaptationProfile.InitialDialog.Title" ), Dialog.ModalityType.APPLICATION_MODAL);
		    Controller.getInstance().pushWindow(this);
		    
		    this.add(new InitialStatePanel(adaptationProfileDataControl,false));
		    
		    addWindowListener( new WindowAdapter (){
			@Override
			public void windowClosed(WindowEvent e) {
				Controller.getInstance().popWindow();
				
			}
			
		    });
		    
		    this.setSize( new Dimension(500,300) );
			Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
			setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
			setResizable( false );
			setVisible( true );
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
			int selectedRow = actionFlagsTable.getSelectedRow( );

			// If no row is selected, set the insertion position at the end
			if( selectedRow == -1 )
				selectedRow = actionFlagsTable.getRowCount( ) - 1;


			// Insert the dialogue line in the selected position
			if(adaptationProfileDataControl.addFlagAction( selectedRow + 1 )){

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
			adaptationProfileDataControl.deleteFlagAction( selectedRow );

			// If there are no more lines, clear selection (this disables the "Delete line" button)
			if( adaptationProfileDataControl.getFlagActionCount( ) == 0 )
				actionFlagsTable.clearSelection( );

			// If the deleted line was the last one, select the new last line in the node
			else if(adaptationProfileDataControl.getFlagActionCount( ) == selectedRow )
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
			if( adaptationProfileDataControl != null )
				rowCount = adaptationProfileDataControl.getFlagActionCount( );

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
			    	
			    	if( columnIndex == 0){
			    	    // if not selected
			    	    if (adaptationProfileDataControl.isFlag(rowIndex)){
			    		
			    		
			    		String[] names = Controller.getInstance( ).getVarFlagSummary( ).getVars();
			    		// take any var if there are at least one 
			    		if (names.length==0){
			    		    Controller.getInstance().showErrorDialog(TextConstants.getText("Error.NoVarsAvailable.Title"), TextConstants.getText("Error.NoVarsAvailable.Message"));
			    		    // change to var
			    		    adaptationProfileDataControl.change(rowIndex, "");
			    		}else 
			    		    // change to var
			    		    adaptationProfileDataControl.change(rowIndex, names[0]);
			    	    }
			    	    
			    	    
			    		
			    	}
			    
			    	if( columnIndex == 1){
			    	    // if not selected
			    	if (!adaptationProfileDataControl.isFlag(rowIndex)){
			    		
			    		
			    		String[] names = Controller.getInstance( ).getVarFlagSummary( ).getFlags();
			    		// take any flag if there are at least one 
			    		if (names.length==0){
			    		    Controller.getInstance().showErrorDialog(TextConstants.getText("Error.NoFlagsAvailable.Title"), TextConstants.getText("Error.NoFlagsAvailable.Message"));
			    		    // change to flag
			    		    adaptationProfileDataControl.change(rowIndex, "");
			    		}else    
			    		 // change to flag
			    		    adaptationProfileDataControl.change(rowIndex, names[0]);		
			    	    
			    	    }
			    		
			    	}
			    	
			    	// If the action is being edited, and it has really changed
				if( columnIndex == 2){
				    // if is a "set value" action, ask for that value
				    if (value.toString().equals(AdaptedState.VALUE)){
					VarDialog dialog= new VarDialog(adaptationProfileDataControl.getValueToSet(rowIndex));
					if (!dialog.getValue().equals("error"))
					    adaptationProfileDataControl.setAction( rowIndex, value.toString() + " " +dialog.getValue() );
				    }
				    else 
					adaptationProfileDataControl.setAction( rowIndex, value.toString( ) );
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
				    adaptationProfileDataControl.setFlag( rowIndex, value.toString( ) );
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
				    	
				    	value = !adaptationProfileDataControl.isFlag(rowIndex);
				    	setRowEditor(rowIndex,(Boolean)value);
				    	break;
				    	
				case 1: // IsFlag 
				    	value = adaptationProfileDataControl.isFlag(rowIndex);
				    	setRowEditor(rowIndex,(Boolean)value);
				    	break;
			
				case 2:
					// Id of the property
					value = adaptationProfileDataControl.getAction( rowIndex );
					break;
				case 3:
					// Property value
					value = adaptationProfileDataControl.getFlag( rowIndex );
					break;
			}

			return value;
	}
		
		private boolean isFlag(int rowIndex, int columnIndex){
		    boolean value=false;
		    switch( columnIndex ) {
		    case 0: // IsVar 
		    	
		    	value = !adaptationProfileDataControl.isFlag(rowIndex);
		    	break;
		    	
		    case 1: // IsFlag 
		    	value = adaptationProfileDataControl.isFlag(rowIndex);
		    	break;
		    }
		    return value;
		}
		
		
	}

	
	private void setRowEditor(int index, boolean isFlag){
	    	
	    
	    	//Edition of column 2: combo box (activate, deactivate for flags; increment, decrement, set value for vars )
		String[] actionValues;
		if (isFlag)
		    actionValues = new String[]{"activate", "deactivate"};
		else 
		    actionValues = new String[]{"increment", "decrement", "set-value"};
		JComboBox actionValuesCB = new JComboBox (actionValues);
		actionValuesCB.setFocusable(false);
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
	
	/*public boolean updateFields() {
	    
	    	
		deleteActionFlagButton.setEnabled( false );
		
		String[] scenes = Controller.getInstance( ).getIdentifierSummary( ).getSceneIds( );
		String[] isValues = new String[scenes.length+1];
		isValues[0] = TextConstants.getText("GeneralText.NotSelected");
		for (int i=0; i<scenes.length; i++){
			isValues[i+1]=scenes[i];
		}
		
		this.initialSceneCB.setModel(new DefaultComboBoxModel(isValues) );
		
		if (adaptationProfileDataControl.getInitialScene( )==null){
			initialSceneCB.setSelectedIndex( 0 );
		}else{
			initialSceneCB.setSelectedItem( adaptationProfileDataControl.getInitialScene( ) );
		}
		actionFlagsTable.clearSelection();
		initialSceneCB.requestFocus();
		actionFlagsTable.updateUI();
		
		
		
		initialSceneCB.updateUI();
		
		return true;
	}*/
}
