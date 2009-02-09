package es.eucm.eadventure.engine.core.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.engine.core.control.DebugTableModel;
import es.eucm.eadventure.engine.core.control.FlagSummary;
import es.eucm.eadventure.engine.core.control.VarSummary;

public class DebugFrame extends JFrame {
	
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Stores flags for the chapter
	 */
	private FlagSummary flagSummary;
	
	/**
	 * Stores vars for the chapter
	 */
	private VarSummary varSummary;
	
	/**
	 * The table where values of flags and vars are shown
	 */
	private JTable table;
	
	/**
	 * Table model for the flags and vars table
	 */
	private DebugTableModel dtm;
	
	/**
	 * Table model for the changes table
	 */
	private DebugTableModel dtmChanges;
	
	/**
	 * The table that shows only the vars and flags that have changed
	 */
	private JTable changeTable;
	
	/**
	 * Constructor for the class DebugFrame
	 * 
	 * @param flagSummary The flags of the chapter
	 * @param varSummary The vars of the chapter
	 */
	public DebugFrame(FlagSummary flagSummary, VarSummary varSummary) {
		super(TextConstants.getText("DebugFrame.Title"));
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		this.setSize(screenSize.width - GUIFrame.WINDOW_WIDTH, GUIFrame.WINDOW_HEIGHT);
		this.setLocation(0, (screenSize.height - GUIFrame.WINDOW_HEIGHT)/2);
		this.setLayout(new BorderLayout());
		
		this.flagSummary = flagSummary;
		this.varSummary = varSummary;
		
		JTabbedPane panel = new JTabbedPane();

		table = new JTable();
		
		dtm = new DebugTableModel(flagSummary, varSummary);
		table.setModel(dtm);
		table.setDefaultRenderer(Object.class, dtm);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		panel.addTab(TextConstants.getText("DebugFrame.AllFlagsAndVars"), null, scrollPane, TextConstants.getText("DebugFrame.AllFlagsAndVarsTip"));
		
		changeTable = new JTable();
		
		dtmChanges = new DebugTableModel(flagSummary, varSummary, true);
		changeTable = new JTable();
		
		JScrollPane scrollPane2 = new JScrollPane(changeTable);
		changeTable.setFillsViewportHeight(true);
		
		panel.addTab(TextConstants.getText("DebugFrame.Changes"), null, scrollPane2, TextConstants.getText("DebugFrame.ChangesTip"));
		
		
		this.add(panel, BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	public void setFlagSummary(FlagSummary flagSummary) {
		this.flagSummary = flagSummary;
	}
	
	public void setVarSummary(VarSummary varSummary) {
		this.varSummary = varSummary;
	}

	public void close() {
		this.setVisible(false);
	}

	/**
	 * Updated the values in the tables
	 */
	public void updateValues() {
		List<String> changes = varSummary.getChanges();
		changes.addAll(flagSummary.getChanges());
		if (!changes.isEmpty()) {
			dtmChanges.setChanges(changes);
			dtmChanges.fireTableStructureChanged();
			dtm.setChanges(changes);
			table.updateUI();
			changeTable.setModel(dtmChanges);
		}
	}

}
