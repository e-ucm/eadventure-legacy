package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ConditionsCellRendererEditor;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.EditEffectCellRenderEditor;

public class EffectsPanel extends JPanel implements Updateable{

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller for the effects.
	 */
	private EffectsController effectsController;

	/**
	 * Table of the effects.
	 */
	private JTable effectsTable;

	private JButton deleteButton;
	
	private JButton moveUpButton;
	
	private JButton moveDownButton;
	
	/**
	 * Constructor.
	 * 
	 * @param effectsController
	 *            Controller for the effects
	 */
	public EffectsPanel( EffectsController effectsController ) {

		// Parent constructor
		super( );

		// Set the conditions controller and the icon
		this.effectsController = effectsController;

		// Set properties of the panel
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Effects.Title" ) ) );

		// Set the panel
		setLayout( new BorderLayout( ) );

		effectsTable = new JTable( new EffectsTableModel( ) );
		effectsTable.getColumnModel( ).getColumn( 0 ).setMaxWidth( 60 );
		
		// Edit button
		effectsTable.getColumnModel().getColumn(2).setCellRenderer(new EditEffectCellRenderEditor(effectsTable));
		effectsTable.getColumnModel().getColumn(2).setCellEditor(new EditEffectCellRenderEditor(effectsTable));
		effectsTable.getColumnModel().getColumn(2).setMaxWidth(120);
		effectsTable.getColumnModel().getColumn(2).setMinWidth(120);
		effectsTable.getColumnModel().getColumn(2).setWidth(120);
		
		// Conditions  button
		effectsTable.getColumnModel().getColumn(3).setCellRenderer(new ConditionsCellRendererEditor());
		effectsTable.getColumnModel().getColumn(3).setCellEditor(new ConditionsCellRendererEditor());
		effectsTable.getColumnModel().getColumn(3).setMaxWidth(120);
		effectsTable.getColumnModel().getColumn(3).setMinWidth(120);
		effectsTable.getColumnModel().getColumn(3).setWidth(120);
		effectsTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		effectsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
			    effectsTable.setRowHeight(20);
			    effectsTable.setRowHeight(effectsTable.getSelectedRow(), 25);
			    updateButtons();
			}
		});
		
		JScrollPane tableScrollPane = new TableScrollPane( effectsTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		add( tableScrollPane, BorderLayout.CENTER );
		

		add( createButtonsPanel(), BorderLayout.SOUTH );
	}

	private void updateButtons() {
		int selected = effectsTable.getSelectedRow();
		if (selected == -1) {
			deleteButton.setEnabled(false);
			moveUpButton.setEnabled(false);
			moveDownButton.setEnabled(false);
		} else {
			deleteButton.setEnabled(true);
			moveUpButton.setEnabled(selected > 0);
			moveDownButton.setEnabled(selected < effectsTable.getRowCount() - 1);
		}
	}
	
	
	private JPanel createButtonsPanel() {
		//Create the buttons panel (SOUTH)
		JPanel buttonsPanel = new JPanel();
		JButton newButton = new JButton(new ImageIcon("img/icons/addNode.png"));
		newButton.setContentAreaFilled( false );
		newButton.setMargin( new Insets(0,0,0,0) );
		newButton.setToolTipText( TextConstants.getText( "ItemReferenceTable.AddParagraph" ) );
		newButton.addActionListener( new AddEffectListener( ));
		
		deleteButton = new JButton(new ImageIcon("img/icons/deleteNode.png"));
		deleteButton.setContentAreaFilled( false );
		deleteButton.setMargin( new Insets(0,0,0,0) );
		deleteButton.setToolTipText( TextConstants.getText( "ItemReferenceTable.Delete" ) );
		deleteButton.addActionListener(new DeleteEffectListener( ));
		deleteButton.setEnabled(false);
		
		moveUpButton = new JButton(new ImageIcon("img/icons/moveNodeUp.png"));
		moveUpButton.setContentAreaFilled( false );
		moveUpButton.setMargin( new Insets(0,0,0,0) );
		moveUpButton.setToolTipText( TextConstants.getText( "ItemReferenceTable.MoveUp" ) );
		moveUpButton.addActionListener( new MoveUpEffectListener( ) );
		moveUpButton.setEnabled(false);
		
		moveDownButton = new JButton(new ImageIcon("img/icons/moveNodeDown.png"));
		moveDownButton.setContentAreaFilled( false );
		moveDownButton.setMargin( new Insets(0,0,0,0) );
		moveDownButton.setToolTipText( TextConstants.getText( "ItemReferenceTable.MoveDown" ) );
		moveDownButton.addActionListener( new MoveDownEffectListener( ));
		moveDownButton.setEnabled(false);

		buttonsPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		buttonsPanel.add( newButton , c );
		c.gridx++;
		buttonsPanel.add( deleteButton , c );
		c.anchor = GridBagConstraints.SOUTH;
		c.gridx++;
		buttonsPanel.add( moveUpButton , c );
		c.gridx++;
		buttonsPanel.add( moveDownButton , c );
		
		return buttonsPanel;
	}
	
	
	
	/**
	 * Listener for the add effect button.
	 */
	private class AddEffectListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			if( effectsController.addEffect( ) ) {
				effectsTable.updateUI( );
				updateButtons();
			}

		}
	}

	/**
	 * Listener for the delete effect button.
	 */
	private class DeleteEffectListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			int effectIndex = effectsTable.getSelectedRow( );

			if( effectIndex >= 0 ) {
				effectsController.deleteEffect( effectIndex );
				effectsTable.clearSelection( );
				effectsTable.updateUI( );
				updateButtons();
			}
		}
	}

	/**
	 * Listener for the move up effect button.
	 */
	private class MoveUpEffectListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			int effectIndex = effectsTable.getSelectedRow( );
			if( effectIndex >= 0 ) {
				if( effectsController.moveUpEffect( effectIndex ) ) {
					effectsTable.changeSelection(effectIndex - 1, effectIndex - 1, false, false);
					effectsTable.updateUI( );
					updateButtons();
				}
			}
		}
	}

	/**
	 * Listener for the move down effect button.
	 */
	private class MoveDownEffectListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			int effectIndex = effectsTable.getSelectedRow( );
			if( effectIndex >= 0 && effectIndex<effectsTable.getRowCount()-1) {
				if (effectsController.moveDownEffect( effectIndex )) {
					effectsTable.changeSelection(effectIndex + 1, effectIndex + 1, false, false);
					effectsTable.updateUI( );
					updateButtons();
				}
			}
		}
	}

	/**
	 * Table model to display conditions blocks.
	 */
	private class EffectsTableModel extends AbstractTableModel {

		/**
		 * Required.
		 */
		private static final long serialVersionUID = 1L;

		public int getColumnCount( ) {
			// Four columns
			return 4;
		}

		public int getRowCount( ) {
			return effectsController.getEffectCount( );
		}

		@Override
		public String getColumnName( int columnIndex ) {
			String columnName = "";
			
				
			// The first column is the effect number
			if( columnIndex == 0 )
				columnName = TextConstants.getText( "Effects.EffectNumberColumnTitle" );

			// The second one the effect information
			else if( columnIndex == 1 )
				columnName = TextConstants.getText( "Effects.EffectDescriptionColumnTitle" );
			
			// The third one edit the effect
			else if( columnIndex == 2 )
				columnName = TextConstants.getText(  "ActionList.EditEffect" );
			
			// The fourth one has the condition of each effect
			else if( columnIndex == 3 )
				columnName = TextConstants.getText(  "ActionsList.Conditions" );
			
			
			
			return columnName;
		}

		public Object getValueAt( int rowIndex, int columnIndex ) {
			Object value = null;
			
			
			if( columnIndex == 0 )
				value = rowIndex + 1;

			else if( columnIndex == 1 )
				value = effectsController.getEffectInfo( rowIndex );
			
			else if (columnIndex == 2)
			    	value = effectsController;	

			
			else if (columnIndex == 3)
			    	value = effectsController.getConditionController(rowIndex);	


			return value;
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
		    boolean isEditable = false;
			if (column==2||column==3)
			    isEditable=true;
			return isEditable &&row == effectsTable.getSelectedRow();
		}
	}

	public boolean updateFields() {
		effectsTable.updateUI();
		return true;
	}
	
}
