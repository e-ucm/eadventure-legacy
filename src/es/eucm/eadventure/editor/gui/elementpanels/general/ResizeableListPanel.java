package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizableTableModel;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;

public class ResizeableListPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ResizeableCellRenderer renderer;
	
	private ResizableTableModel model;
	
	private JTable informationTable;
	
	private List<DataControl> dataControlList;
	
	public ResizeableListPanel(List<DataControl> dataControlList, ResizeableCellRenderer renderer) {
		this.dataControlList = dataControlList;
		this.renderer = renderer;
		createPanel();
	}
	
	private void createPanel() {
		// Set the layout and the border
		setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		
		JPanel buttonsPanel = new JPanel();
		JButton sizeZero = new JButton("size  0");
		sizeZero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeSize(0);
			}
		});
		buttonsPanel.add(sizeZero);

		JButton sizeOne = new JButton("size  1");
		sizeOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeSize(1);
			}
		});
		buttonsPanel.add(sizeOne);

		JButton sizeTwo = new JButton("size  2");
		sizeTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeSize(2);
			}
		});
		buttonsPanel.add(sizeTwo);

		add( buttonsPanel, c );

		// Create the table with the data
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		
		model = new ResizableTableModel( dataControlList );
		
		informationTable = new JTable( model );
		informationTable.addMouseListener(new InformationTableMouseListener());
		
		for (int i = 0; i < model.getColumnCount(); i++) {
			informationTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
			informationTable.getColumnModel().getColumn(i).setCellEditor(renderer);
		}
		informationTable.setRowHeight(200);
		informationTable.setTableHeader(null);

		add( new JScrollPane( informationTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ), c );
	}
	
	private class InformationTableMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				JTable table = (JTable) e.getSource();
				int row = table.rowAtPoint(e.getPoint());
				int column = table.columnAtPoint(e.getPoint());
				int index = row * 2 + column;
				if (index < dataControlList.size()) {
					DataControl dataControl = dataControlList.get(index);
					StructureControl.getInstance().changeDataControl(dataControl);
				}
			}
		}
	}
	
	private void changeSize(int size) {
		renderer.setSize(size);
		model.setSize(size);
		model.fireTableStructureChanged();
		for (int i = 0; i < model.getColumnCount(); i++) {
			informationTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
			informationTable.getColumnModel().getColumn(i).setCellEditor(renderer);
		}
		if (size == 0)
			informationTable.setRowHeight(20);
		if (size == 1)
			informationTable.setRowHeight(100);
		if (size == 2)
			informationTable.setRowHeight(200);
	}
}
