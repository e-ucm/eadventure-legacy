package es.eucm.eadventure.editor.gui.elementpanels.cutscene;

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
import es.eucm.eadventure.editor.control.controllers.cutscene.CutscenesListDataControl;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;
import es.eucm.eadventure.editor.gui.treepanel.TreeNodeControl;

public class CutscenesListPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	private CutscenesListDataControl cutscenesListDataControl;
	
	/**
	 * Constructor.
	 * 
	 * @param cutscenesListDataControl
	 *            Cutscenes list controller
	 */
	public CutscenesListPanel( CutscenesListDataControl cutscenesListDataControl ) {
		this.cutscenesListDataControl = cutscenesListDataControl;
		// Set the layout and the border
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "CutscenesList.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "CutscenesList.Information" ) );
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
		JTable informationTable = new JTable( new CutscenesInfoTableModel( cutscenesListDataControl.getCutscenesInfo( ) ) );
		informationTable.removeEditor( );
		informationTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable table = (JTable) e.getSource();
					DataControl dataControl = CutscenesListPanel.this.cutscenesListDataControl.getCutscenes().get(table.getSelectedRow());
					StructureControl.getInstance().changeDataControl(dataControl);
				}
			}
		});
		JPanel listPanel = new JPanel( );
		listPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "CutscenesList.ListTitle" ) ) );
		listPanel.setLayout( new BorderLayout( ) );
		listPanel.add( new JScrollPane( informationTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), BorderLayout.CENTER );
		add( listPanel, c );
	}

	/**
	 * Table model to display the scenes information.
	 */
	private class CutscenesInfoTableModel extends AbstractTableModel {

		/**
		 * Required.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Array of data to display.
		 */
		private String[][] cutscenesInfo;

		/**
		 * Constructor.
		 * 
		 * @param cutscenesInfo
		 *            Container array of the information of the cutscenes
		 */
		public CutscenesInfoTableModel( String[][] cutscenesInfo ) {
			this.cutscenesInfo = cutscenesInfo;
		}

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
			return cutscenesInfo.length;
		}

		@Override
		public String getColumnName( int columnIndex ) {
			String columnName = "";

			// The first column is the cutscene identifier
			if( columnIndex == 0 )
				columnName = TextConstants.getText( "CutscenesList.ColumnHeader0" );

			// The second one is the type of cutscene
			else if( columnIndex == 1 )
				columnName = TextConstants.getText( "CutscenesList.ColumnHeader1" );

			return columnName;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt( int rowIndex, int columnIndex ) {
			return cutscenesInfo[rowIndex][columnIndex];
		}
	}
}
