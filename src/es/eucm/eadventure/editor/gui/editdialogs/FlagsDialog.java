package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.FlagsController;

/**
 * This class is the editing dialog of the flags. Here the user can add new flags to use them in the script. Also, the
 * flags can be deleted.
 * 
 * @author Bruno Torijano Bueno
 */
public class FlagsDialog extends JDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller for the flags.
	 */
	private FlagsController flagsController;

	/**
	 * Table holding the flags.
	 */
	private JTable flagsTable;

	/**
	 * Constructor.
	 * 
	 * @param flagController
	 *            Controller for the flags
	 */
	public FlagsDialog( FlagsController flagController ) {

		// Call to the JDialog constructor
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "Flags.Title" ), Dialog.ModalityType.APPLICATION_MODAL );

		// Push the dialog into the stack, and add the window listener to pop in when closing
		Controller.getInstance( ).pushWindow( this );
		addWindowListener( new WindowAdapter( ) {
			public void windowClosing( WindowEvent e ) {
				Controller.getInstance( ).popWindow( );
			}
		} );

		// Set the flags controller
		this.flagsController = flagController;

		// Create a container panel, and set the properties
		JPanel mainPanel = new JPanel( );
		mainPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Flags.Title" ) ) );
		mainPanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 10, 5, 10 );

		// Create the table and add it
		flagsTable = new JTable( new FlagsTableModel( ) );
		flagsTable.getColumnModel( ).getColumn( 1 ).setMaxWidth( 60 );
		flagsTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		mainPanel.add( new JScrollPane( flagsTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), c );

		// Add an "Add flag" button
		JButton addFlag = new JButton( TextConstants.getText( "Flags.AddFlag" ) );
		addFlag.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				if( flagsController.addFlag( ) )
					flagsTable.updateUI( );
			}
		} );
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0;
		c.gridy = 1;
		mainPanel.add( addFlag, c );

		// Add an "Delete flag" button
		JButton deleteFlag = new JButton( TextConstants.getText( "Flags.DeleteFlag" ) );
		deleteFlag.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				if( flagsTable.getSelectedRow( ) >= 0 )
					if( flagsController.deleteFlag( flagsTable.getSelectedRow( ) ) )
						flagsTable.updateUI( );
			}
		} );
		c.gridy = 2;
		mainPanel.add( deleteFlag, c );

		// Add the panel
		setLayout( new GridBagLayout( ) );
		c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		add( mainPanel, c );

		// Set the size, position and properties of the dialog
		setResizable( false );
		setSize( 500, 400 );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );
	}

	/**
	 * Table model to display the flags.
	 */
	private class FlagsTableModel extends AbstractTableModel {

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
			// Two columns, always
			return 2;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		public int getRowCount( ) {
			return flagsController.getFlagCount( );
		}

		@Override
		public String getColumnName( int columnIndex ) {
			String columnName = "";

			// The first column is the name
			if( columnIndex == 0 )
				columnName = TextConstants.getText( "Flags.FlagName" );

			// The second one the references number
			else if( columnIndex == 1 )
				columnName = TextConstants.getText( "Flags.FlagReferences" );

			return columnName;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt( int rowIndex, int columnIndex ) {
			Object value = null;

			// The first column has the name
			if( columnIndex == 0 )
				value = flagsController.getFlag( rowIndex );

			// The second one the references number
			else if( columnIndex == 1 )
				value = flagsController.getFlagReferences( rowIndex );

			return value;
		}
	}
}
