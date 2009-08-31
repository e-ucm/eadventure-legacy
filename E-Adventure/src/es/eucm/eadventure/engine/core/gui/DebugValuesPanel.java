/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.engine.core.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import es.eucm.eadventure.common.data.chapter.conditions.GlobalState;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.engine.core.control.DebugTableModel;
import es.eucm.eadventure.engine.core.control.FlagSummary;
import es.eucm.eadventure.engine.core.control.VarSummary;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;

public class DebugValuesPanel extends JPanel {
	
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
	
	private List<GlobalState> globalStates;
	
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
	
	private JTable globalTable;
	
	private DefaultTableModel globalDtm;
	
	/**
	 * Constructor for the class DebugFrame
	 * 
	 * @param flagSummary The flags of the chapter
	 * @param varSummary The vars of the chapter
	 * @param list 
	 */
	public DebugValuesPanel(FlagSummary flagSummary, VarSummary varSummary, List<GlobalState> globalStates) {
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		this.setSize(screenSize.width - GUIFrame.WINDOW_WIDTH, GUIFrame.WINDOW_HEIGHT);
		this.setLocation(0, 0);
		this.setLayout(new BorderLayout());
		
		this.flagSummary = flagSummary;
		this.varSummary = varSummary;
		this.globalStates = globalStates;
		
		JTabbedPane panel = new JTabbedPane();

		table = new JTable();
		
		dtm = new DebugTableModel(flagSummary, varSummary);
		table.setModel(dtm);
		table.setRowHeight(25);
		table.setDefaultRenderer(Object.class, dtm);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		panel.addTab(TextConstants.getText("DebugFrame.AllFlagsAndVars"), null, scrollPane, TextConstants.getText("DebugFrame.AllFlagsAndVarsTip"));

		
		globalTable = new JTable();
		globalDtm = new DefaultTableModel();
		String[] ids = {TextConstants.getText("DebugFrame.id"), TextConstants.getText("DebugFrame.value")};
		globalDtm.setColumnIdentifiers(ids);
		for (GlobalState state: globalStates) {
			FunctionalConditions fc = new FunctionalConditions(state);	
			String[] row = {state.getId() , (fc.allConditionsOk() ? "true" : "false")};
			globalDtm.addRow(row);
		}
		globalTable.setModel(globalDtm);
		globalTable.setRowHeight(25);
		globalTable.setDefaultRenderer(Object.class, dtm);
		
		JScrollPane scrollPane3 = new JScrollPane(globalTable);
		globalTable.setFillsViewportHeight(true);
		
		panel.addTab(TextConstants.getText("DebugFrame.GlobalStates"), null, scrollPane3, TextConstants.getText("DebugFrame.GlobalStatesTip"));

		
		changeTable = new JTable();
		
		dtmChanges = new DebugTableModel(flagSummary, varSummary, true);
		dtmChanges.addGlobalStates(globalStates);
		changeTable.setModel(dtmChanges);
		changeTable.setRowHeight(25);
		changeTable.setDefaultRenderer(Object.class, dtmChanges);
		
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
		List<String> changes = new ArrayList<String>();
		changes.addAll(varSummary.getChanges());
		changes.addAll(flagSummary.getChanges());
		if (!changes.isEmpty()) {
			for (int i = 0; i < globalStates.size(); i++) {
				FunctionalConditions fc = new FunctionalConditions(globalStates.get(i));	
				boolean b = globalDtm.getValueAt(i, 1).equals("true");
				if (b ^ fc.allConditionsOk()) {
					changes.add(globalStates.get(i).getId());
					globalDtm.setValueAt((!b ? "true" : "false"), i, 1);
				}
			}
			dtmChanges.setChanges(changes);
			dtmChanges.fireTableStructureChanged();
			final Rectangle r = table.getVisibleRect();
			dtm.setChanges(changes);
			dtm.fireTableStructureChanged();
			SwingUtilities.invokeLater(new Runnable()
			{
			    public void run()
			    {
					table.scrollRectToVisible(r);
			    }
			});
			changeTable.setModel(dtmChanges);
		}
	}

}
