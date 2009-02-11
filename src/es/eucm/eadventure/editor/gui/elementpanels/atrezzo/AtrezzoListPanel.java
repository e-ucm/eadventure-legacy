package es.eucm.eadventure.editor.gui.elementpanels.atrezzo;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.atrezzo.AtrezzoListDataControl;
import es.eucm.eadventure.editor.gui.treepanel.TreeNodeControl;


public class AtrezzoListPanel extends JPanel{
	
	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	private AtrezzoListDataControl atrezzoListDataControl;
	
	/**
	 * Constructor.
	 * 
	 * @param atrezzoListDataControl
	 *            Items list controller
	 */
	public AtrezzoListPanel( AtrezzoListDataControl atrezzoListDataControl ) {
		this.atrezzoListDataControl = atrezzoListDataControl;
		// Set the layout and the border
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AtrezzoList.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "AtrezzoList.Information" ) );
		JPanel informationPanel = new JPanel( );
		informationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "GeneralText.Information" ) ) );
		informationPanel.setLayout( new BorderLayout( ) );
		informationPanel.add( informationTextPane, BorderLayout.CENTER );
		add( informationPanel, c );

		// Create the table with the data
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		JTable informationTable = new JTable( new AtrezzoInfoTableModel( atrezzoListDataControl.getItemsInfo( ) ) );
		informationTable.removeEditor( );
		informationTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable table = (JTable) e.getSource();
					DataControl dataControl = AtrezzoListPanel.this.atrezzoListDataControl.getAtrezzoList().get(table.getSelectedRow());
					TreeNodeControl.getInstance().changeTreeNode(dataControl);
				}
			}
		});
		JPanel listPanel = new JPanel( );
		listPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AtrezzoList.ListTitle" ) ) );
		listPanel.setLayout( new BorderLayout( ) );
		listPanel.add( new JScrollPane( informationTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), BorderLayout.CENTER );
		add( listPanel, c );
	}

	/**
	 * Table model to display the items information.
	 */
	private class AtrezzoInfoTableModel extends AbstractTableModel {

		/**
		 * Required.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Array of data to display.
		 */
		private String[][] atrezzoInfo;

		/**
		 * Constructor.
		 * 
		 * @param itemsInfo
		 *            Container array of the information of the items
		 */
		public AtrezzoInfoTableModel( String[][] atrezzoInfo ) {
			this.atrezzoInfo = atrezzoInfo;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		public int getColumnCount( ) {
			// One columns, always
			return 1;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		public int getRowCount( ) {
			return atrezzoInfo.length;
		}

		@Override
		public String getColumnName( int columnIndex ) {
		

			return TextConstants.getText( "AtrezzoList.ColumnHeader" );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt( int rowIndex, int columnIndex ) {
			return atrezzoInfo[rowIndex][columnIndex];
		}
	}

}
