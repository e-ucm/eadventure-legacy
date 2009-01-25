package es.eucm.eadventure.engine.core.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
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
	
	public DebugFrame(FlagSummary flagSummary, VarSummary varSummary) {
		super("Debug info");
		this.setSize(400, 400);
		this.setLayout(new BorderLayout());
		
		this.flagSummary = flagSummary;
		this.varSummary = varSummary;

		table = new JTable();
		
		dtm = new DebugTableModel(flagSummary, varSummary);
		table.setModel(dtm);
		table.setDefaultRenderer(Object.class, dtm);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		this.add(scrollPane, BorderLayout.CENTER);
		
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
			dtm.setChanges(changes);
			table.updateUI();
		}
	}

}
