package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionDialog;

public class ConditionsPanel extends JPanel implements Updateable{

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Icon for deleting either block.
	 */
	private Icon deleteIcon;

	/**
	 * Controller for the conditions.
	 */
	private ConditionsController conditionsController;

	/**
	 * Principal panel for the conditions.
	 */
	private JTabbedPane conditionsTabbedPanel;

	/**
	 * Table of the main conditions.
	 */
	private JTable mainConditionsTable;

	/**
	 * List of either conditions tables.
	 */
	private List<JTable> eitherConditionsTables;
	
	/**
	 * Constant for table rendering only. 
	 */
	private static final String TYPE_ID="ID";
	
	/**
	 * Constant for table rendering only. 
	 */
	private static final String TYPE_OTHER="OTHER";
	
	/**
	 * Constant for table rendering only. 
	 */
	private static final String COND_FLAG="FLAG";
	
	/**
	 * Constant for table rendering only. 
	 */
	private static final String COND_VAR="VAR";
	
	/**
	 * Constant for table rendering only. 
	 */
	private static final String COND_GLOBAL_STATE="GS";


	public static final Color FLAG_COLOR = new Color(1f, 0.05f, 0.05f, 0.1f); 
	public static final Color VAR_COLOR = new Color(0.05f, 0.1f, 1f, 0.1f);
	public static final Color GLOBAL_STATE_COLOR = new Color(1f, 0.05f, 1f, 0.1f);

	/**
	 * Constructor.
	 * 
	 * @param conditionController
	 *            Controller for the conditions
	 * @param keyListener 
	 */
	public ConditionsPanel( ConditionsController conditionController ) {
		// Parent constructor
		super( );
		// Set the conditions controller and the icon
		this.conditionsController = conditionController;
		this.updateFields();
	}

	/**
	 * Adds a new either conditions block panel to the main panel, placed in the last position.
	 */
	private void addEitherConditionsBlockPanel( ) {
		// Store the panel index and the either block index
		int panelIndex = conditionsTabbedPanel.getTabCount( );
		int eitherBlockIndex = panelIndex - 1;

		// Create the printipal panel
		JPanel eitherBlockPanel = new JPanel( );
		eitherBlockPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Conditions.EitherBlockTitle", String.valueOf( panelIndex ) ) ) );
		eitherBlockPanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 0, 5, 0 );

		// Add the table with the data
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		JTable conditionsTable = new JTable( new ConditionsTableModelRenderer( eitherBlockIndex ) );
		conditionsTable.addMouseListener( new ConditionsTableMouseListener( ) );
		//conditionsTable.getColumnModel( ).getColumn( 0 ).setMaxWidth( 60 );
		for (int i=0; i<conditionsTable.getColumnModel().getColumnCount(); i++ ){
			TableColumn column=conditionsTable.getColumnModel().getColumn( i );
			column.setCellRenderer( new ConditionsTableModelRenderer( eitherBlockIndex ) );
		}
		conditionsTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		JScrollPane tableScrollPane = new JScrollPane( conditionsTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		tableScrollPane.setPreferredSize( new Dimension( 0, 140 ) );
		eitherBlockPanel.add( tableScrollPane, c );
		eitherConditionsTables.add( conditionsTable );

		// Add the add condition button
		c.insets = new Insets( 5, 0, 5, 5 );
		c.gridy = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0;
		JButton addConditionButton = new JButton( TextConstants.getText( "Conditions.AddCondition" ) );
		addConditionButton.addActionListener( new AddConditionListener( eitherBlockIndex ) );
		eitherBlockPanel.add( addConditionButton, c );

		// Add the delete condition button
		c.insets = new Insets( 5, 5, 5, 0 );
		c.gridx = 1;
		JButton deleteConditionButton = new JButton( TextConstants.getText( "Conditions.DeleteCondition" ) );
		deleteConditionButton.addActionListener( new DeleteConditionListener( eitherBlockIndex ) );
		eitherBlockPanel.add( deleteConditionButton, c );

		// Add the panel to the tabbed panel
		conditionsTabbedPanel.add( eitherBlockPanel );

		// Panel for the tab
		JPanel tabPanel = new JPanel( );
		tabPanel.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		// Add the label with the number of the block
		tabPanel.add( new JLabel( TextConstants.getText( "Conditions.EitherBlockTab", String.valueOf( panelIndex ) ) ) );

		// Add the delete button
		JButton deleteBlockButton = new JButton( deleteIcon );
		deleteBlockButton.addActionListener( new DeleteEitherConditionsBlockListener( eitherBlockIndex ) );
		deleteBlockButton.setPreferredSize( new Dimension( 20, 20 ) );
		tabPanel.add( deleteBlockButton, c );

		// Set the tab panel
		conditionsTabbedPanel.setTabComponentAt( panelIndex, tabPanel );
	}

	/**
	 * Returns the JTable given by the index.
	 * 
	 * @param index
	 *            Index of the block
	 * @return The conditions JTable
	 */
	private JTable getConditionsTable( int index ) {
		JTable conditionsTable;

		if( index == ConditionsController.MAIN_CONDITIONS_BLOCK )
			conditionsTable = mainConditionsTable;
		else
			conditionsTable = eitherConditionsTables.get( index );

		return conditionsTable;
	}

	/**
	 * Listener for the add condition button.
	 */
	private class AddConditionListener implements ActionListener {

		/**
		 * Index of the conditions block.
		 */
		private int blockIndex;

		/**
		 * Constructor.
		 * 
		 * @param blockIndex
		 *            Index of the conditions block
		 */
		public AddConditionListener( int blockIndex ) {
			this.blockIndex = blockIndex;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {

			// Pick the JTable
			JTable conditionsTable = getConditionsTable( blockIndex );

			// Display the dialog to add a new condition
			ConditionDialog conditionDialog = new ConditionDialog( TextConstants.getText( "Conditions.AddCondition" ) );

			// If the data was approved
			if( conditionDialog.wasPressedOKButton( ) ) {
				// Set the new values and update the table
				conditionsController.addCondition( blockIndex, conditionDialog.getSelectedType(), conditionDialog.getSelectedId( ), conditionDialog.getSelectedState( ), conditionDialog.getSelectedValue( ) );
				conditionsTable.updateUI( );
			}
		}
	}

	/**
	 * Listener for the delete condition button.
	 */
	private class DeleteConditionListener implements ActionListener {

		/**
		 * Index of the conditions block.
		 */
		private int blockIndex;

		/**
		 * Constructor.
		 * 
		 * @param blockIndex
		 *            Index of the conditions block
		 */
		public DeleteConditionListener( int blockIndex ) {
			this.blockIndex = blockIndex;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			int conditionIndex = getConditionsTable( blockIndex ).getSelectedRow( );

			if( conditionIndex >= 0 ) {
				conditionsController.deleteCondition( blockIndex, conditionIndex );
				getConditionsTable( blockIndex ).clearSelection( );
				getConditionsTable( blockIndex ).updateUI( );
			}
		}
	}

	/**
	 * Listener for the add either conditions block.
	 */
	private class AddEitherConditionsBlockListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			conditionsController.addEitherConditionsBlock( );
			addEitherConditionsBlockPanel( );
			updateUI( );
		}
	}

	/**
	 * Listener for the delete either conditions block button.
	 */
	private class DeleteEitherConditionsBlockListener implements ActionListener {

		/**
		 * Index of the either conditions block.
		 */
		private int blockIndex;

		/**
		 * Constructor.
		 * 
		 * @param blockIndex
		 *            Index of the either conditions block
		 */
		public DeleteEitherConditionsBlockListener( int blockIndex ) {
			this.blockIndex = blockIndex;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Delete the block
			conditionsController.deleteEitherConditionsBlock( blockIndex );

			// If the selected block is the last one, move the selection to the previous one, as the current will be
			// deleted
			if( conditionsTabbedPanel.getSelectedIndex( ) == conditionsTabbedPanel.getTabCount( ) - 1 )
				conditionsTabbedPanel.setSelectedIndex( conditionsTabbedPanel.getTabCount( ) - 2 );

			// Take the last index, and delete the tab and the table
			conditionsTabbedPanel.removeTabAt( conditionsTabbedPanel.getTabCount( ) - 1 );
			eitherConditionsTables.remove( eitherConditionsTables.size( ) - 1 );

			// Update all the remaining tables
			for( JTable table : eitherConditionsTables )
				table.updateUI( );
			updateUI( );
		}
	}

	/**
	 * Action listener for the tables. It displays a dialog that allows to edit the single conditions.
	 */
	private class ConditionsTableMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked( MouseEvent e ) {
			// If it was a double click
			if( e.getClickCount( ) == 2 ) {

				// Pick the source JTable and the selected row
				JTable conditionsTable = (JTable) e.getComponent( );
				int selectedCondition = conditionsTable.getSelectedRow( );
				int selectedTable = -1;
				if ( conditionsTable == mainConditionsTable ){
					selectedTable = ConditionsController.MAIN_CONDITIONS_BLOCK;
				} else {
					selectedTable = eitherConditionsTables.indexOf( conditionsTable );
				}
				
				// If there was an element selected
				if( selectedCondition >= 0 ) {

					// Take the actual values of the condition, and display the editing dialog
					//String stateValue = conditionsTable.getValueAt( selectedCondition, 0 ).toString( );
					//String flagValue = conditionsTable.getValueAt( selectedCondition, 1 ).toString( );
					int defaultMode = conditionsController.getConditionType ( selectedTable, selectedCondition );
					String defaultFlag = null;
					String defaultState = conditionsController.getConditionState ( selectedTable, selectedCondition );;
					String defaultVar = null;
					String defaultValue = null;
					String defaultId = null;
					if ( defaultMode == ConditionsController.VAR_CONDITION ){
						defaultVar = conditionsController.getConditionId ( selectedTable, selectedCondition );;
						defaultValue = conditionsController.getConditionValue ( selectedTable, selectedCondition );;
					} else if ( defaultMode == ConditionsController.FLAG_CONDITION ){
						defaultFlag = conditionsController.getConditionId ( selectedTable, selectedCondition );;
					} else if ( defaultMode == ConditionsController.GLOBAL_STATE_CONDITION ){
						defaultId = conditionsController.getConditionId ( selectedTable, selectedCondition );;
					}


					ConditionDialog conditionDialog = new ConditionDialog( new Integer( defaultMode ), TextConstants.getText( "Conditions.EditCondition" ), defaultState, defaultFlag, defaultVar, defaultId, defaultValue );

					// If the data was approved
					if( conditionDialog.wasPressedOKButton( ) ) {

						// Take the index of the table
						int tableIndex = eitherConditionsTables.indexOf( conditionsTable );
						if( tableIndex == -1 )
							tableIndex = ConditionsController.MAIN_CONDITIONS_BLOCK;

						// Set the new values
						/*conditionsController.setConditionType( tableIndex, selectedCondition, conditionDialog.getSelectedType( ) );
						conditionsController.setConditionState( tableIndex, selectedCondition, conditionDialog.getSelectedState( ) );
						conditionsController.setConditionId( tableIndex, selectedCondition, conditionDialog.getSelectedId( ) );
						conditionsController.setConditionValue( tableIndex, selectedCondition, conditionDialog.getSelectedValue( ) );*/
						conditionsController.setCondition(tableIndex, selectedCondition, conditionDialog.getSelectedType( )
								, conditionDialog.getSelectedId( ), conditionDialog.getSelectedState( ), conditionDialog.getSelectedValue( ));
						
						// Update the table
						conditionsTable.updateUI( );
					}
				}
			}
		}
	}

	/**
	 * Table model to display conditions blocks.
	 */
	private class ConditionsTableModelRenderer extends AbstractTableModel implements TableCellRenderer {

		/**
		 * Required.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Index of the either conditions block.
		 */
		private int blockIndex;

		/**
		 * Constructor.
		 * 
		 * @param blockIndex
		 *            Index of the either conditions block
		 */
		public ConditionsTableModelRenderer( int blockIndex ) {
			this.blockIndex = blockIndex;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		public int getColumnCount( ) {
			// Three columns, always
			return 3;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		public int getRowCount( ) {
			return conditionsController.getConditionCount( blockIndex );
		}

		@Override
		public String getColumnName( int columnIndex ) {
			String columnName = "";

			// The first column is the name
			if( columnIndex == 0 )
				columnName = TextConstants.getText( "Conditions.Flag" );				

			// The second one the references number
			else if( columnIndex == 1 )
				columnName = TextConstants.getText( "Conditions.State" );
			
			// The second one the references number
			else if( columnIndex == 2 )
				columnName = TextConstants.getText( "Conditions.Value" );


			return columnName;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt( int rowIndex, int columnIndex ) {
			String value = null;
			String type = null;
			String conditionType = null;

			// Get the type of condition
			if ( conditionsController.isFlagCondtion(blockIndex, rowIndex) ){
				conditionType = COND_FLAG;
			}else if ( conditionsController.isVarCondtion(blockIndex, rowIndex) ){
				conditionType = COND_VAR;
			} else {
				conditionType = COND_GLOBAL_STATE;
			}
			
			// Get the value to render
			if( columnIndex == 0 ) {
				value = conditionsController.getConditionId( blockIndex, rowIndex );
				type = TYPE_ID;
			} else if( columnIndex == 1 ){
				if ( conditionsController.isFlagCondtion(blockIndex, rowIndex) ){
					value = TextConstants.getText("GeneralText.Is");
				} else if ( conditionsController.isVarCondtion(blockIndex, rowIndex) ){
					value = conditionsController.getConditionState(blockIndex, rowIndex);
				} else {
					value = "-";
				}
				type = TYPE_OTHER;
			}else if( columnIndex == 2 ) {
				if ( conditionsController.isFlagCondtion(blockIndex, rowIndex) ){
					value = conditionsController.getConditionState( blockIndex, rowIndex );
				} else if ( conditionsController.isVarCondtion(blockIndex, rowIndex) ){
					value = conditionsController.getConditionValue( blockIndex, rowIndex );	
				} else {
					value = "-";
				}
				type = TYPE_OTHER;
			}
			return type+"."+conditionType+"."+value;
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {

			if (value instanceof String){
				StringTokenizer tokenizer = new StringTokenizer(((String)value), "." );
				String type = tokenizer.nextToken( );
				String conditionType =tokenizer.nextToken( );
				String text = tokenizer.nextToken( );
				
				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout());
				if ( conditionType.equals( COND_FLAG )){
					panel.setBackground( FLAG_COLOR );
				} else if ( conditionType.equals( COND_VAR )){
					panel.setBackground( VAR_COLOR );
				} else if ( conditionType.equals( COND_GLOBAL_STATE )){
					panel.setBackground( GLOBAL_STATE_COLOR );
				}
				
				if (type.equals( TYPE_ID )){
					panel.add( new JLabel ( getIconForType(conditionType)), BorderLayout.WEST );
					JLabel label = new JLabel ( text );
					label.setHorizontalTextPosition( SwingConstants.CENTER );
					label.setAlignmentX( 0.5f );
					label.setHorizontalAlignment( SwingConstants.CENTER );
					panel.add( label , BorderLayout.CENTER );
				} else if (type.equals( TYPE_OTHER )){
					JLabel label = new JLabel ( text );
					label.setHorizontalTextPosition( SwingConstants.CENTER );
					label.setAlignmentX( 0.5f );
					label.setHorizontalAlignment( SwingConstants.CENTER );
					panel.add( label, BorderLayout.CENTER );
				}
				
				if ( isSelected ){
					panel.setBorder( BorderFactory.createLineBorder( Color.black, 2) );
				}
				
				return panel;
			} else
				return null;
		}
		
		private Icon getIconForType ( String type ){
			Icon icon = null;
			if ( type.equals( COND_FLAG ) ){
				icon = new ImageIcon ( "img/icons/flag16.png");
			} else if ( type.equals( COND_VAR ) ){
				icon = new ImageIcon ( "img/icons/var16.png");
			} else if ( type.equals( COND_GLOBAL_STATE ) ){
				icon = new ImageIcon ( "img/icons/group16.png");
			}
			return icon;
		}
	}

	public boolean updateFields() {
		int n= getComponentCount();
		for (int i=0; i<n; i++){
			this.remove(i);
			n--;
		}
		deleteIcon = new ImageIcon( "img/icons/deleteContent.png" );
		eitherConditionsTables = new ArrayList<JTable>( );

		// Set properties of the panel
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Conditions.Title" ) ) );
		setLayout( new BorderLayout( ) );

		// Create the tabbed panel
		conditionsTabbedPanel = new JTabbedPane( JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT );

		// Create the first panel for the tabbed panel
		JPanel mainConditions = new JPanel( );
		mainConditions.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Conditions.MainBlockTitle" ) ) );
		mainConditions.setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 0, 5, 0 );

		// Add the table with the data
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		mainConditionsTable = new JTable( new ConditionsTableModelRenderer( ConditionsController.MAIN_CONDITIONS_BLOCK ) );
		//mainConditionsTable.getColumnModel( ).getColumn( 0 ).setMaxWidth( 60 );
		for (int i=0; i<mainConditionsTable.getColumnModel().getColumnCount(); i++ ){
			TableColumn column=mainConditionsTable.getColumnModel().getColumn( i );
			column.setCellRenderer( new ConditionsTableModelRenderer( ConditionsController.MAIN_CONDITIONS_BLOCK ) );
		}
		
		mainConditionsTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		mainConditionsTable.addMouseListener( new ConditionsTableMouseListener( ) );
		JScrollPane tableScrollPane = new JScrollPane( mainConditionsTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		mainConditions.add( tableScrollPane, c );

		// Add the add condition button
		c.insets = new Insets( 5, 0, 5, 5 );
		c.gridy = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0;
		JButton addConditionButton = new JButton( TextConstants.getText( "Conditions.AddCondition" ) );
		addConditionButton.addActionListener( new AddConditionListener( ConditionsController.MAIN_CONDITIONS_BLOCK ) );
		mainConditions.add( addConditionButton, c );

		// Add the delete condition button
		c.insets = new Insets( 5, 5, 5, 0 );
		c.gridx = 1;
		JButton deleteConditionButton = new JButton( TextConstants.getText( "Conditions.DeleteCondition" ) );
		deleteConditionButton.addActionListener( new DeleteConditionListener( ConditionsController.MAIN_CONDITIONS_BLOCK ) );
		mainConditions.add( deleteConditionButton, c );

		// Add the "Add either block" button
		c.insets = new Insets( 5, 0, 5, 0 );
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		JButton addEitherBlockButton = new JButton( TextConstants.getText( "Conditions.AddEitherBlock" ) );
		addEitherBlockButton.addActionListener( new AddEitherConditionsBlockListener( ) );
		mainConditions.add( addEitherBlockButton, c );

		// Add the main conditions panel as the first tab
		conditionsTabbedPanel.addTab( TextConstants.getText( "Conditions.MainBlockTab" ), mainConditions );

		// Add the tabbed panel
		add( conditionsTabbedPanel, BorderLayout.CENTER );

		// Add as much panel as either blocks are
		for( int i = 0; i < conditionsController.getEitherConditionsBlockCount( ); i++ )
			addEitherConditionsBlockPanel( );

		updateUI();
		return true;
	}
}
