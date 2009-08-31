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
package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarriersListDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.control.tools.scene.AddBarrierTool;
import es.eucm.eadventure.editor.control.tools.scene.DeleteBarrierTool;
import es.eucm.eadventure.editor.gui.DataControlsPanel;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.elementpanels.DataControlSelectionListener;
import es.eucm.eadventure.editor.gui.elementpanels.general.TableScrollPane;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElement;

public class BarriersListPanel extends JPanel implements Updateable, DataControlsPanel, DataControlSelectionListener {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	private	ScenePreviewEditionPanel spep;
	
	private BarriersListDataControl dataControl;
	
	private JTable table;
	
	private JButton deleteButton;
	
	private JButton duplicateButton;
	
	private static final int HORIZONTAL_SPLIT_POSITION = 140;

	/**
	 * Constructor.
	 * 
	 * @param barriersListDataControl
	 *            ActiveAreas list controller
	 */
	public BarriersListPanel( BarriersListDataControl barriersListDataControl ) {
		this.dataControl = barriersListDataControl;
		String scenePath = Controller.getInstance( ).getSceneImagePath( barriersListDataControl.getParentSceneId( ) );
		spep = new ScenePreviewEditionPanel(false, scenePath);
		spep.setDataControlSelectionListener(this);
		addElementsToPreview(scenePath);
		
		setLayout( new BorderLayout( ) );
		
		JPanel tablePanel = createTablePanel();
		
		JSplitPane tableWithSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel, spep);
		tableWithSplit.setOneTouchExpandable(true);
		tableWithSplit.setDividerLocation(HORIZONTAL_SPLIT_POSITION);
		tableWithSplit.setContinuousLayout(true);
		tableWithSplit.setResizeWeight(0.5);
		tableWithSplit.setDividerSize(10);
	
		add(tableWithSplit,BorderLayout.CENTER);
	}

	private JPanel createTablePanel() {
		JPanel tablePanel = new JPanel();
		
		table = new BarriersTable(dataControl);
		JScrollPane scroll = new TableScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		scroll.setMinimumSize(new Dimension(0, 	HORIZONTAL_SPLIT_POSITION));

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (table.getSelectedRow() >= 0 && table.getSelectedRow() < dataControl.getBarriers().size()) {
					deleteButton.setEnabled(true);
					duplicateButton.setEnabled(true);
					spep.setSelectedElement(dataControl.getBarriers().get(table.getSelectedRow()));
					spep.repaint();
				} else {
					deleteButton.setEnabled(false);
					duplicateButton.setEnabled(false);
				}
				deleteButton.repaint();
			}
		});

		
		JPanel buttonsPanel = new JPanel();
		JButton newButton = new JButton(new ImageIcon("img/icons/addNode.png"));
		newButton.setContentAreaFilled( false );
		newButton.setMargin( new Insets(0,0,0,0) );
		newButton.setBorder(BorderFactory.createEmptyBorder());
		newButton.setToolTipText( TextConstants.getText( "BarriersList.AddBarrier" ) );
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addBarrier();
			}
		});
		deleteButton = new JButton(new ImageIcon("img/icons/deleteNode.png"));
		deleteButton.setContentAreaFilled( false );
		deleteButton.setMargin( new Insets(0,0,0,0) );
		deleteButton.setBorder(BorderFactory.createEmptyBorder());
		deleteButton.setToolTipText( TextConstants.getText( "BarriersList.DeleteBarrier" ) );
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				deleteBarrier();
			}
		});
		duplicateButton = new JButton(new ImageIcon("img/icons/duplicateNode.png"));
		duplicateButton.setContentAreaFilled( false );
		duplicateButton.setMargin( new Insets(0,0,0,0) );
		duplicateButton.setBorder(BorderFactory.createEmptyBorder());
		duplicateButton.setToolTipText( TextConstants.getText( "BarriersList.DuplicateBarrier" ) );
		duplicateButton.setEnabled(false);
		duplicateButton.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				duplicateBarrier();
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
		c.fill = GridBagConstraints.VERTICAL;
		c.weighty = 2.0;
		buttonsPanel.add(new JFiller(), c);

		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(scroll, BorderLayout.CENTER);
		tablePanel.add(buttonsPanel, BorderLayout.EAST);
		
		return tablePanel;
	}

	private void addElementsToPreview(String scenePath) {
		spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_BARRIER, true);

		if( scenePath != null ) {
			for( ElementReferenceDataControl elementReference : dataControl.getParentSceneItemReferences( ) ) {
				spep.addElement(ScenePreviewEditionPanel.CATEGORY_OBJECT, elementReference);
			}
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_OBJECT, false);
			for( ElementReferenceDataControl elementReference : dataControl.getParentSceneNPCReferences( ) ) {
				spep.addElement(ScenePreviewEditionPanel.CATEGORY_CHARACTER, elementReference);
			}
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_CHARACTER, false);
			for( ElementReferenceDataControl elementReference : dataControl.getParentSceneAtrezzoReferences( ) ) {
				spep.addElement(ScenePreviewEditionPanel.CATEGORY_ATREZZO, elementReference);
			}
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_ATREZZO, false);
			for( ExitDataControl exit : dataControl.getParentSceneExits( ) ) {
				spep.addExit(exit);
			}
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_EXIT, false);
			for( ActiveAreaDataControl activeArea : dataControl.getParentSceneActiveAreas( ) ) {
				spep.addActiveArea(activeArea);
			}
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_ACTIVEAREA, false);
			for( BarrierDataControl barrier : dataControl.getBarriers( ) ) {
				spep.addBarrier(barrier);
			}
			if (dataControl.getParentSceneTrajectory().hasTrajectory()) {
				spep.setTrajectory((Trajectory) dataControl.getParentSceneTrajectory().getContent());
				for (NodeDataControl nodeDataControl: dataControl.getParentSceneTrajectory().getNodes())
					spep.addNode(nodeDataControl);
			}

		}
	}
	
	protected void addBarrier() {
		Controller.getInstance().addTool(new AddBarrierTool(dataControl, spep));
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
		table.changeSelection(dataControl.getBarriers().size() - 1, dataControl.getBarriers().size() - 1, false, false);
	}

	protected void duplicateBarrier() {
		if (dataControl.duplicateElement(dataControl.getBarriers().get(table.getSelectedRow()))) {
			spep.addBarrier(dataControl.getLastBarrier());
			spep.repaint();
			((AbstractTableModel) table.getModel()).fireTableDataChanged();
			table.changeSelection(dataControl.getBarriers().size() - 1, dataControl.getBarriers().size() - 1, false, false);
		}
	}

	protected void deleteBarrier() {
		Controller.getInstance().addTool(new DeleteBarrierTool(dataControl, table, spep));
	}

	
	public boolean updateFields() {
		int selected = table.getSelectedRow();
		int items = table.getRowCount();
		
		if (table.getCellEditor() != null)
			table.getCellEditor().cancelCellEditing();
		
		((AbstractTableModel) table.getModel()).fireTableDataChanged();

		if (items != 0 && items == table.getRowCount()) {
			if (selected != -1) {
				table.changeSelection(selected, 0, false, false);
			}
		} else {
			spep.setSelectedElement((ImageElement) null);
		}
	    spep.repaint();
		return true;
	}
	
	public void setSelectedItem(List<Searchable> path) {
		if (path.size() > 0) {
			for (int i = 0 ; i < dataControl.getBarriers().size(); i++) {
				if (dataControl.getBarriers().get(i) == path.get(path.size() -1))
					table.changeSelection(i, i, false, false);
			}
		}
	}

	public void dataControlSelected(DataControl dataControl2) {
		if (dataControl2 != null) {
			for (int i = 0 ; i < dataControl.getBarriers().size(); i++) {
				if (dataControl.getBarriers().get(i) == dataControl2)
					table.changeSelection(i, i, false, false);
			}
		} else
			table.clearSelection();
	}
}
