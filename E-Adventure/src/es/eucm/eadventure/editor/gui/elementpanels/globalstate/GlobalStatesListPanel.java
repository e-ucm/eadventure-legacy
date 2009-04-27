package es.eucm.eadventure.editor.gui.elementpanels.globalstate;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateDataControl;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateListDataControl;
import es.eucm.eadventure.editor.gui.DataControlsPanel;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;

public class GlobalStatesListPanel extends JPanel implements DataControlsPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int HORIZONTAL_SPLIT_POSITION = 100;

	private GlobalStateListDataControl dataControl;

	private JPanel globalStateInfoPanel;
	
	private JTable table;
	
	private JButton deleteButton;
	
	private JButton duplicateButton;
	
	/**
	 * Constructor.
	 * 
	 * @param gloabalStatesListDataControl
	 *            Scenes list controller
	 */
	public GlobalStatesListPanel( GlobalStateListDataControl gloabalStatesListDataControl ) {
		this.dataControl = gloabalStatesListDataControl;
		setLayout( new BorderLayout() );

		globalStateInfoPanel = new JPanel();
		globalStateInfoPanel.setLayout(new BorderLayout());
		
		JPanel tablePanel = createTablePanel();
		
		JSplitPane tableWithSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel, globalStateInfoPanel);
		tableWithSplit.setOneTouchExpandable(true);
		tableWithSplit.setDividerLocation(HORIZONTAL_SPLIT_POSITION);
		tableWithSplit.setContinuousLayout(true);
		tableWithSplit.setResizeWeight(0);
		tableWithSplit.setDividerSize(10);

		add(tableWithSplit, BorderLayout.CENTER);
	}

	
	private JPanel createTablePanel() {
		JPanel tablePanel = new JPanel();
		
		table = new GlobalStatesTable(dataControl);
		JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setMinimumSize(new Dimension(0, 	HORIZONTAL_SPLIT_POSITION));

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (table.getSelectedRow() >= 0) {
					deleteButton.setEnabled(true);
					duplicateButton.setEnabled(true);
				}else {
					deleteButton.setEnabled(false);
					duplicateButton.setEnabled(false);
				}
				updateInfoPanel(table.getSelectedRow());
				deleteButton.repaint();
			}			
		});
		
		JPanel buttonsPanel = new JPanel();
		JButton newButton = new JButton(new ImageIcon("img/icons/addNode.png"));
		newButton.setContentAreaFilled( false );
		newButton.setMargin( new Insets(0,0,0,0) );
		newButton.setToolTipText( TextConstants.getText( "GlobalStatesList.AddGlobalState" ) );
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addGlobalState();
			}
		});
		deleteButton = new JButton(new ImageIcon("img/icons/deleteNode.png"));
		deleteButton.setContentAreaFilled( false );
		deleteButton.setMargin( new Insets(0,0,0,0) );
		deleteButton.setToolTipText( TextConstants.getText( "GlobalStatesList.DeleteGlobalState" ) );
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				deleteGlobalState();
			}
		});
		duplicateButton = new JButton(new ImageIcon("img/icons/duplicateNode.png"));
		duplicateButton.setContentAreaFilled( false );
		duplicateButton.setMargin( new Insets(0,0,0,0) );
		duplicateButton.setToolTipText( TextConstants.getText( "GlobalStatesList.DuplicateGlobalState" ) );
		duplicateButton.setEnabled(false);
		duplicateButton.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				duplicateGlobalState();
			}
		});
		
		buttonsPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		buttonsPanel.add(newButton, c);
		c.gridy = 1;
		buttonsPanel.add(duplicateButton, c);
		c.gridy = 3;
		buttonsPanel.add(deleteButton, c);
		c.gridy = 2;
		c.weighty = 2.0;
		c.fill = GridBagConstraints.VERTICAL;
		buttonsPanel.add(new JFiller(), c);
		
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(scroll, BorderLayout.CENTER);
		tablePanel.add(buttonsPanel, BorderLayout.EAST);
		
		return tablePanel;
	}

	public void updateInfoPanel(int row) {
		globalStateInfoPanel.removeAll();		
		if (row >= 0) {
			GlobalStateDataControl globalState = dataControl.getGlobalStates().get(row);
			JPanel timerPanel = new GlobalStatePanel(globalState);
			globalStateInfoPanel.add(timerPanel);
		}
		globalStateInfoPanel.updateUI();
	}
	
	protected void addGlobalState() {
		if (dataControl.addElement(dataControl.getAddableElements()[0], null)) {
			((AbstractTableModel) table.getModel()).fireTableDataChanged();
			table.changeSelection(dataControl.getGlobalStates().size() - 1, 0, false, false);
		}
	}

	protected void duplicateGlobalState() {
		if (dataControl.duplicateElement(dataControl.getGlobalStates().get(table.getSelectedRow()))) {
			((AbstractTableModel) table.getModel()).fireTableDataChanged();
			table.changeSelection(dataControl.getGlobalStates().size() - 1, 0, false, false);
		}
	}
	
	protected void deleteGlobalState() {
		dataControl.deleteElement(dataControl.getGlobalStates().get(table.getSelectedRow()), true);
		table.clearSelection();
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
	}


	@Override
	public void setSelectedItem(List<DataControl> path) {
		if (path.size() > 0) {
			for (int i = 0 ; i < dataControl.getGlobalStates().size(); i++) {
				if (dataControl.getGlobalStates().get(i) == path.get(path.size() -1))
					table.changeSelection(i, i, false, false);
			}
		}
	}

}
