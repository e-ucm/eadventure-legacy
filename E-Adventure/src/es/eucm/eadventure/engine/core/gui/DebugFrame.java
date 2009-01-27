package es.eucm.eadventure.engine.core.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import es.eucm.eadventure.engine.core.control.DebugTableModel;
import es.eucm.eadventure.engine.core.control.FlagSummary;
import es.eucm.eadventure.engine.core.control.VarSummary;

public class DebugFrame extends JFrame {
	
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	private FlagSummary flagSummary;
	
	private VarSummary varSummary;
	
	private JTable table;
	
	private DebugTableModel dtm;
	
	private DebugTableModel dtmChanges;
	
	private JTable changeTable;
	
	public DebugFrame(FlagSummary flagSummary, VarSummary varSummary) {
		super("Debug info");
		this.setSize(400, 400);
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
		
		panel.addTab("TITLE", null, scrollPane, "TIP");
		
		changeTable = new JTable();
		
		dtmChanges = new DebugTableModel(flagSummary, varSummary, true);
		changeTable = new JTable();
		
		JScrollPane scrollPane2 = new JScrollPane(changeTable);
		changeTable.setFillsViewportHeight(true);
		
		panel.addTab("TITLE2", null, scrollPane2, "TIP");
		
		
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

	public void updateValues() {
		List<String> changes = varSummary.getChanges();
		changes.addAll(flagSummary.getChanges());
		if (!changes.isEmpty()) {
			dtmChanges.setChanges(changes);
			dtm.setChanges(changes);
			table.updateUI();
			changeTable.updateUI();
		}
	}

}
