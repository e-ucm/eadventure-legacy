package es.eucm.eadventure.editor.gui.elementpanels.item;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.editor.control.controllers.item.ItemsListDataControl;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;

public class ItemsListPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	private ItemsListDataControl itemsListDataControl;
	
	/**
	 * Constructor.
	 * 
	 * @param itemsListDataControl2
	 *            Items list controller
	 */
	public ItemsListPanel( ItemsListDataControl itemsListDataControl2 ) {
		this.itemsListDataControl = itemsListDataControl2;
		// Set the layout and the border
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemsList.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "ItemsList.Information" ) );
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
		JTable informationTable = new JTable( new ItemsInfoTableModel( itemsListDataControl2.getItems() ) );
		informationTable.removeEditor( );
		informationTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable table = (JTable) e.getSource();
					int row = table.rowAtPoint(e.getPoint());
					int column = table.columnAtPoint(e.getPoint());
					int index = row * 2 + column;
					if (index < itemsListDataControl.getItems().size()) {
						DataControl dataControl = itemsListDataControl.getItems().get(index);
						StructureControl.getInstance().changeDataControl(dataControl);
					}
				}
			}
		});
		
		informationTable.getColumnModel().getColumn(0).setCellRenderer(new ItemCellRenderer());
		informationTable.getColumnModel().getColumn(1).setCellRenderer(new ItemCellRenderer());
		informationTable.setRowHeight(200);
		
		JPanel listPanel = new JPanel( );
		listPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemsList.ListTitle" ) ) );
		listPanel.setLayout( new BorderLayout( ) );
		listPanel.add( new JScrollPane( informationTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), BorderLayout.CENTER );
		add( listPanel, c );
	}

	/**
	 * Table model to display the items information.
	 */
	private class ItemsInfoTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		
		private List<ItemDataControl> list;
		
		/**
		 * Constructor.
		 * 
		 * @param itemsInfo
		 *            Container array of the information of the items
		 */
		public ItemsInfoTableModel( List<ItemDataControl> list ) {
			this.list = list;
		}

		public int getColumnCount( ) {
			return 2;
		}

		public int getRowCount( ) {
			return list.size() / 2 + list.size() % 2;
		}

		@Override
		public String getColumnName( int columnIndex ) {
			String columnName = "";

			return columnName;
		}

		public Object getValueAt( int rowIndex, int columnIndex ) {
			if (rowIndex * 2 + columnIndex < list.size())
				return list.get(rowIndex * 2 + columnIndex);
			return null;
		}
	}
}
