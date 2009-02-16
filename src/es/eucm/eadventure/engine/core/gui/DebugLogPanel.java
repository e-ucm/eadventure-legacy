package es.eucm.eadventure.engine.core.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.engine.core.control.DebugLog;

public class DebugLogPanel extends JPanel {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	private DebugTable all;
	
	private DebugTable general;
	
	private DebugTable user;
	
	private DebugTable player;
	
	public DebugLogPanel() {
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		this.setSize(screenSize.width, screenSize.height - GUIFrame.WINDOW_HEIGHT - 25);
		this.setLocation(0, GUIFrame.WINDOW_HEIGHT);
		this.setLayout(new BorderLayout());		
		
		JTabbedPane pane = new JTabbedPane();
		
		all = new DebugTable();
		pane.addTab(TextConstants.getText("DebugFrameLog.All"), null, all.getScrollPane(), TextConstants.getText("DebugFrameLog.AllTip"));

		user = new DebugTable();
		pane.addTab(TextConstants.getText("DebugFrameLog.User"), null, user.getScrollPane(), TextConstants.getText("DebugFrameLog.UserTip"));
		
		player = new DebugTable();
		pane.addTab(TextConstants.getText("DebugFrameLog.Player"), null, player.getScrollPane(), TextConstants.getText("DebugFrameLog.PlayerTip"));
		
		general = new DebugTable();

		pane.addTab(TextConstants.getText("DebugFrameLog.General"), null, general.getScrollPane(), TextConstants.getText("DebugFrameLog.General"));
		
		this.add(pane, BorderLayout.CENTER);
		
		DebugLog.getInstance().setDebugFrameLog(this);
		this.setVisible(true);
	}
	
	public void addLine(int type, String time, String text) {
		try {
			all.addLine(time, text);
			if (type == DebugLog.PLAYER)
				player.addLine(time, text);
			if (type == DebugLog.GENERAL)
				general.addLine(time, text);
			if (type == DebugLog.USER)
				user.addLine(time, text);
		} catch (Exception e) {
			
		}
	}

	public void close() {
		this.setVisible(false);
		DebugLog.getInstance().clear();
	}
	
	private class DebugTable {
		
		private JTable table;
		
		private DefaultTableModel dtm;
		
		private JScrollPane scrollPane;
		
		public DebugTable() {
			table = new JTable();
			dtm = new DefaultTableModel();
			dtm.setColumnCount(2);
			String[] ids = {TextConstants.getText("DebugFrameLog.Time"), TextConstants.getText("DebugFrameLog.Entry")};
			dtm.setColumnIdentifiers(ids);
			table.setModel(dtm);
			table.getColumnModel().getColumn(0).setMaxWidth(80);
			scrollPane = new JScrollPane(table);
			table.setFillsViewportHeight(true);
		}
		
		public JScrollPane getScrollPane() {
			return scrollPane;
		}
		
		public void addLine(String time, String text) {
			String[] line = new String[2];
			line[0] = time;
			line[1] = text;
			dtm.addRow(line);
			table.updateUI();
			Rectangle r = table.getCellRect(table.getRowCount()-1, 0, true);
			table.scrollRectToVisible(r);
		}
	}
	
}
