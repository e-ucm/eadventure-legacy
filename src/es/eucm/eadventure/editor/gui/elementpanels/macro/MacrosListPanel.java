package es.eucm.eadventure.editor.gui.elementpanels.macro;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import es.eucm.eadventure.editor.control.controllers.macro.MacroDataControl;
import es.eucm.eadventure.editor.control.controllers.macro.MacroListDataControl;

public class MacrosListPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	private static final int HORIZONTAL_SPLIT_POSITION = 100;

	private MacroListDataControl dataControl;

	private JPanel globalStateInfoPanel;
	
	private JTable table;
	
	private JButton deleteButton;

	/**
	 * Constructor.
	 * 
	 * @param macrosListDataControl
	 *            Scenes list controller
	 */
	public MacrosListPanel( MacroListDataControl macrosListDataControl ) {
		this.dataControl = macrosListDataControl;
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
		
		table = new MacrosTable(dataControl);
		JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setMinimumSize(new Dimension(0, 	HORIZONTAL_SPLIT_POSITION));

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (table.getSelectedRow() >= 0)
					deleteButton.setEnabled(true);
				else
					deleteButton.setEnabled(false);
				updateInfoPanel(table.getSelectedRow());
				deleteButton.repaint();
			}			
		});
		
		JPanel buttonsPanel = new JPanel();
		JButton newButton = new JButton(new ImageIcon("img/icons/addNode.png"));
		newButton.setContentAreaFilled( false );
		newButton.setMargin( new Insets(0,0,0,0) );
		newButton.setToolTipText( TextConstants.getText( "ItemReferenceTable.AddParagraph" ) );
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addMacro();
			}
		});
		deleteButton = new JButton(new ImageIcon("img/icons/deleteNode.png"));
		deleteButton.setContentAreaFilled( false );
		deleteButton.setMargin( new Insets(0,0,0,0) );
		deleteButton.setToolTipText( TextConstants.getText( "ItemReferenceTable.Delete" ) );
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				deleteMacro();
			}
		});
		buttonsPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		buttonsPanel.add(newButton, c);
		c.gridy = 1;
		buttonsPanel.add(deleteButton, c);
		
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(scroll, BorderLayout.CENTER);
		tablePanel.add(buttonsPanel, BorderLayout.EAST);
		
		return tablePanel;
	}

	public void updateInfoPanel(int row) {
		globalStateInfoPanel.removeAll();		
		if (row >= 0) {
			MacroDataControl globalState = dataControl.getMacros().get(row);
			JPanel timerPanel = new MacroPanel(globalState);
			globalStateInfoPanel.add(timerPanel);
		}
		globalStateInfoPanel.updateUI();
	}
	
	protected void addMacro() {
		if (dataControl.addElement(dataControl.getAddableElements()[0], null)) {
			table.getSelectionModel().setSelectionInterval(dataControl.getMacros().size() - 1, dataControl.getMacros().size() - 1);
			((AbstractTableModel) table.getModel()).fireTableDataChanged();
		}
	}
	
	protected void deleteMacro() {
		dataControl.deleteElement(dataControl.getMacros().get(table.getSelectedRow()), true);
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
	}

}
