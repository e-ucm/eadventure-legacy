package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.NormalScenePreviewEditionController;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreasListDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.control.tools.scene.AddActiveAreaTool;
import es.eucm.eadventure.editor.control.tools.scene.DeleteActiveAreaTool;
import es.eucm.eadventure.editor.control.tools.scene.DuplicateActiveAreaTool;
import es.eucm.eadventure.editor.gui.DataControlsPanel;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.elementpanels.DataControlSelectionListener;
import es.eucm.eadventure.editor.gui.elementpanels.general.SmallActionsListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.TableScrollPane;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class ActiveAreasListPanel extends JPanel implements DataControlsPanel, DataControlSelectionListener,Updateable {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	private static final int HORIZONTAL_SPLIT_POSITION = 140;

	public static final int VERTICAL_SPLIT_POSITION = 150;

	private JButton deleteButton;
	
	private JButton duplicateButton;
	
	private ActiveAreasTable table;
	
	private ActiveAreasListDataControl dataControl;
	
	private IrregularAreaEditionPanel iaep;
	
	private JSplitPane previewAuxSplit;
	
	private JPanel auxPanel;
	
	private SmallActionsListPanel smallActions = null;
		
	/**
	 * Constructor.
	 * 
	 * @param activeAreasListDataControl
	 *            ActiveAreas list controller
	 */
	public ActiveAreasListPanel( ActiveAreasListDataControl activeAreasListDataControl ) {
		this.dataControl = activeAreasListDataControl;
		String scenePath = Controller.getInstance( ).getSceneImagePath( activeAreasListDataControl.getParentSceneId( ) );
		iaep = new IrregularAreaEditionPanel(scenePath, null, activeAreasListDataControl.getSceneDataControl().getTrajectory().hasTrajectory(), Color.GREEN);
		ScenePreviewEditionPanel spep = iaep.getScenePreviewEditionPanel();
		if (activeAreasListDataControl.getSceneDataControl().getTrajectory().hasTrajectory()) {
			spep.setTrajectory((Trajectory) activeAreasListDataControl.getSceneDataControl().getTrajectory().getContent());
			for (NodeDataControl nodeDataControl: activeAreasListDataControl.getSceneDataControl().getTrajectory().getNodes())
				spep.addNode(nodeDataControl);
			spep.setShowInfluenceArea(true);
		}
		
		
		setLayout( new BorderLayout( ) );
		
		auxPanel = new JPanel();
		auxPanel.setMaximumSize(new Dimension(VERTICAL_SPLIT_POSITION, Integer.MAX_VALUE));
		auxPanel.setMinimumSize(new Dimension(VERTICAL_SPLIT_POSITION, 0));
		
		previewAuxSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, iaep, auxPanel);
		previewAuxSplit.setDividerSize(10);
		previewAuxSplit.setContinuousLayout(true);
		previewAuxSplit.setOneTouchExpandable(true);
		previewAuxSplit.setResizeWeight(1);
		previewAuxSplit.setDividerLocation(Integer.MAX_VALUE);
		JPanel tablePanel = createTablePanel(iaep);
		
		JSplitPane tableWithSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel, previewAuxSplit);
		tableWithSplit.setOneTouchExpandable(true);
		tableWithSplit.setDividerLocation(HORIZONTAL_SPLIT_POSITION);
		tableWithSplit.setContinuousLayout(true);
		tableWithSplit.setResizeWeight(0);
		tableWithSplit.setDividerSize(10);
	
		add(tableWithSplit,BorderLayout.CENTER);
		
		addElementsToPreview(spep, scenePath, activeAreasListDataControl);
	}
	
	private JPanel createTablePanel(IrregularAreaEditionPanel iaep) {
		JPanel tablePanel = new JPanel();
		
		table = new ActiveAreasTable(dataControl, iaep, previewAuxSplit);
		JScrollPane scroll = new TableScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setMinimumSize(new Dimension(0, 	HORIZONTAL_SPLIT_POSITION));

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (table.getSelectedRow() >= 0) {
					deleteButton.setEnabled(true);
					duplicateButton.setEnabled(true);
				} else {
					deleteButton.setEnabled(false);
					duplicateButton.setEnabled(false);
				}
				updateAuxPanel();
				deleteButton.repaint();
			}
		});
		
		JPanel buttonsPanel = new JPanel();
		JButton newButton = new JButton(new ImageIcon("img/icons/addNode.png"));
		newButton.setContentAreaFilled( false );
		newButton.setMargin( new Insets(0,0,0,0) );
		newButton.setBorder(BorderFactory.createEmptyBorder());
		newButton.setFocusable(false);
		newButton.setToolTipText( TextConstants.getText( "ActiveAreasList.AddActiveArea" ) );
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addActiveArea();
			}
		});
		deleteButton = new JButton(new ImageIcon("img/icons/deleteNode.png"));
		deleteButton.setContentAreaFilled( false );
		deleteButton.setMargin( new Insets(0,0,0,0) );
		deleteButton.setBorder(BorderFactory.createEmptyBorder());
		deleteButton.setToolTipText( TextConstants.getText( "ActiveAreasList.DeleteActiveArea" ) );
		deleteButton.setEnabled(false);
		deleteButton.setFocusable(false);
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				deleteActiveArea();
			}
		});
		duplicateButton = new JButton(new ImageIcon("img/icons/duplicateNode.png"));
		duplicateButton.setContentAreaFilled( false );
		duplicateButton.setMargin( new Insets(0,0,0,0) );
		duplicateButton.setBorder(BorderFactory.createEmptyBorder());
		duplicateButton.setToolTipText( TextConstants.getText( "ActiveAreasList.DuplicateActiveArea" ) );
		duplicateButton.setEnabled(false);
		duplicateButton.setFocusable(false);
		duplicateButton.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				duplicateActiveArea();
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
	
	protected void addActiveArea() {
		String defaultId = dataControl.getDefaultId(dataControl.getAddableElements()[0]);
		String id = defaultId;
		int count = 0;
		while (!Controller.getInstance().isElementIdValid( id, false )) {
			count++;
			id = defaultId + count;
		}
		Controller.getInstance().addTool(new AddActiveAreaTool(dataControl, id, iaep));
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
		table.changeSelection(dataControl.getActiveAreas().size() - 1, dataControl.getActiveAreas().size() - 1, false, false);
		table.editCellAt(dataControl.getActiveAreas().size() - 1, 0);
		if (table.isEditing()) {
			table.getEditorComponent().requestFocusInWindow();
		}
	}

	protected void duplicateActiveArea() {
		Controller.getInstance().addTool(new DuplicateActiveAreaTool(dataControl, iaep, table));
	}

	protected void deleteActiveArea() {
		Controller.getInstance().addTool(new DeleteActiveAreaTool(dataControl, iaep, table));
		
	}
	
	private void addElementsToPreview(ScenePreviewEditionPanel spep, String scenePath, ActiveAreasListDataControl activeAreasListDataControl) {
		if( scenePath != null ) {
			for( ElementReferenceDataControl elementReference : activeAreasListDataControl.getParentSceneItemReferences( ) ) {
				spep.addElement(ScenePreviewEditionPanel.CATEGORY_OBJECT, elementReference);
			}
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_OBJECT, false);
			for( ElementReferenceDataControl elementReference : activeAreasListDataControl.getParentSceneNPCReferences( ) ) {
				spep.addElement(ScenePreviewEditionPanel.CATEGORY_CHARACTER, elementReference);
			}
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_CHARACTER, false);
			for( ElementReferenceDataControl elementReference : activeAreasListDataControl.getParentSceneAtrezzoReferences( ) ) {
				spep.addElement(ScenePreviewEditionPanel.CATEGORY_ATREZZO, elementReference);
			}
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_ATREZZO, false);
			for( ExitDataControl exit : activeAreasListDataControl.getParentSceneExits( ) ) {
				spep.addExit(exit);
			}
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_EXIT, false);
			for( BarrierDataControl barrier : activeAreasListDataControl.getParentSceneBarriers( ) ) {
				spep.addBarrier(barrier);
			}
	
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_BARRIER, false);

			for( ActiveAreaDataControl activeArea : activeAreasListDataControl.getActiveAreas( ) ) {
				spep.addActiveArea(activeArea);
			}
			
			spep.changeController(new NormalScenePreviewEditionController(spep));
			spep.setDataControlSelectionListener(this);
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_ACTIVEAREA, true);

		}
	}
	
	protected void updateAuxPanel() {
		if (auxPanel == null)
			return;
		auxPanel.removeAll();
		smallActions = null;
		if (table.getSelectedRow() == -1) {
			previewAuxSplit.setDividerLocation(Integer.MAX_VALUE);
			return;
		}
		
		auxPanel.setLayout(new BorderLayout());
		smallActions = new SmallActionsListPanel(dataControl.getActiveAreas().get(this.table.getSelectedRow()).getActionsList());
		auxPanel.add(smallActions);

		previewAuxSplit.setDividerLocation(Integer.MAX_VALUE);
	}
	
	public void setSelectedItem(List<Searchable> path) {
		if (path.size() > 0) {
			for (int i = 0 ; i < dataControl.getActiveAreas().size(); i++) {
				if (dataControl.getActiveAreas().get(i) == path.get(path.size() -1))
					table.changeSelection(i, i, false, false);
			}
		}
	}

	public void dataControlSelected(DataControl dataControl2) {
		if (dataControl2 != null) {
			for (int i = 0 ; i < dataControl.getActiveAreas().size(); i++) {
				if (dataControl.getActiveAreas().get(i) == dataControl2)
					table.changeSelection(i, i, false, false);
			}
		} else
			table.clearSelection();
	}

	public boolean updateFields() {
		int selected = table.getSelectedRow();
		int items = table.getRowCount();
		if (table.getCellEditor() != null) {
			table.getCellEditor().cancelCellEditing();
		}		
		
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
		
		if (items > 0 && items == dataControl.getActiveAreas().size()) {
			if (selected != -1 && selected < table.getRowCount()) {
				table.changeSelection(selected, 0, false, false);
				if (smallActions != null && smallActions instanceof Updateable) {
					((Updateable) smallActions).updateFields();
				}
			}
		}
		iaep.repaint();
		return true;
	}
}
