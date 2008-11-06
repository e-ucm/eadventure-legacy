package es.eucm.eadventure.adventureeditor.gui.elementpanels.general;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.adventureeditor.control.controllers.EffectsController;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;

public class EffectsPanel extends JPanel {

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
		setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Add the description of the panel
		c.anchor = GridBagConstraints.LINE_START;
		c.gridwidth = 4;
		add( new JLabel( TextConstants.getText( "Effects.Description" ) ), c );

		// Add the table with the data
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		effectsTable = new JTable( new EffectsTableModel( ) );
		effectsTable.getColumnModel( ).getColumn( 0 ).setMaxWidth( 60 );
		effectsTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		effectsTable.addMouseListener( new EffectsTableMouseListener( ) );
		JScrollPane tableScrollPane = new JScrollPane( effectsTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		add( tableScrollPane, c );

		// Add the move back effect button
		c.gridy = 2;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.weighty = 0;
		JButton moveBackEffectButton = new JButton( "<" );
		moveBackEffectButton.addActionListener( new MoveUpEffectListener( ) );
		add( moveBackEffectButton, c );

		// Add the add effect button
		c.gridx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JButton addConditionButton = new JButton( TextConstants.getText( "Effects.AddEffectButton" ) );
		addConditionButton.addActionListener( new AddEffectListener( ) );
		add( addConditionButton, c );

		// Add the delete effect button
		c.gridx = 2;
		JButton deleteConditionButton = new JButton( TextConstants.getText( "Effects.DeleteEffectButton" ) );
		deleteConditionButton.addActionListener( new DeleteEffectListener( ) );
		add( deleteConditionButton, c );

		// Add the move forward effect button
		c.gridx = 3;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		JButton moveForwardEffectButton = new JButton( ">" );
		moveForwardEffectButton.addActionListener( new MoveDownEffectListener( ) );
		add( moveForwardEffectButton, c );
	}

	/**
	 * Listener for the add effect button.
	 */
	private class AddEffectListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			if( effectsController.addEffect( ) ) {
				effectsTable.updateUI( );
			}

		}
	}

	/**
	 * Listener for the delete effect button.
	 */
	private class DeleteEffectListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			int effectIndex = effectsTable.getSelectedRow( );

			if( effectIndex >= 0 ) {
				effectsController.deleteEffect( effectIndex );
				effectsTable.clearSelection( );
				effectsTable.updateUI( );
			}
		}
	}

	/**
	 * Listener for the move up effect button.
	 */
	private class MoveUpEffectListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			int effectIndex = effectsTable.getSelectedRow( );

			if( effectIndex >= 0 ) {
				if( effectsController.moveUpEffect( effectIndex ) ) {
					effectsTable.setRowSelectionInterval( effectIndex - 1, effectIndex - 1 );
					effectsTable.updateUI( );
				}
			}
		}
	}

	/**
	 * Listener for the move down effect button.
	 */
	private class MoveDownEffectListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			int effectIndex = effectsTable.getSelectedRow( );

			if( effectIndex >= 0 ) {
				effectsController.moveDownEffect( effectIndex );
				effectsTable.setRowSelectionInterval( effectIndex + 1, effectIndex + 1 );
				effectsTable.updateUI( );
			}
		}
	}

	/**
	 * Action listener for the effects table. It displays a dialog that allows to edit the effects when a double click
	 * has been performed.
	 */
	private class EffectsTableMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked( MouseEvent e ) {
			int effectSelected = effectsTable.getSelectedRow( );

			// If it was a double click and there is an effect selected
			if( e.getClickCount( ) == 2 && effectSelected >= 0 )
				if( effectsController.editEffect( effectSelected ) )
					effectsTable.updateUI( );
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		public int getColumnCount( ) {
			// Two columns
			return 2;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
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

			return columnName;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt( int rowIndex, int columnIndex ) {
			Object value = null;

			if( columnIndex == 0 )
				value = rowIndex + 1;

			else if( columnIndex == 1 )
				value = effectsController.getEffectInfo( rowIndex );

			return value;
		}
	}
}
