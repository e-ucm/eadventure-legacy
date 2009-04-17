package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreasListDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.SmallActionsListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ActiveAreasTable;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class ActiveAreasListPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	private static final int HORIZONTAL_SPLIT_POSITION = 140;

	public static final int VERTICAL_SPLIT_POSITION = 150;

	private JButton deleteButton;
	
	private ActiveAreasTable table;
	
	private ActiveAreasListDataControl dataControl;
	
	private IrregularAreaEditionPanel iaep;
	
	private JSplitPane previewAuxSplit;
	
	private JPanel auxPanel;
	
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
		tableWithSplit.setResizeWeight(0.5);
		tableWithSplit.setDividerSize(10);
	
		add(tableWithSplit,BorderLayout.CENTER);
		
		addElementsToPreview(spep, scenePath, activeAreasListDataControl);
	}
	
	private JPanel createTablePanel(IrregularAreaEditionPanel iaep) {
		JPanel tablePanel = new JPanel();
		
		table = new ActiveAreasTable(dataControl, iaep, previewAuxSplit);
		JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setMinimumSize(new Dimension(0, 	HORIZONTAL_SPLIT_POSITION));

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (table.getSelectedRow() >= 0)
					deleteButton.setEnabled(true);
				else
					deleteButton.setEnabled(false);
				updateAuxPanel();
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
				addActiveArea();
			}
		});
		deleteButton = new JButton(new ImageIcon("img/icons/deleteNode.png"));
		deleteButton.setContentAreaFilled( false );
		deleteButton.setMargin( new Insets(0,0,0,0) );
		deleteButton.setToolTipText( TextConstants.getText( "ItemReferenceTable.Delete" ) );
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				deleteActiveArea();
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
	
	protected void addActiveArea() {
		if (dataControl.addElement(dataControl.getAddableElements()[0], null)) {
			iaep.getScenePreviewEditionPanel().addActiveArea(dataControl.getLastActiveArea());
			iaep.repaint();
			table.getSelectionModel().setSelectionInterval(dataControl.getActiveAreas().size() - 1, dataControl.getActiveAreas().size() - 1);
			((AbstractTableModel) table.getModel()).fireTableDataChanged();
		}
	}
	
	protected void deleteActiveArea() {
		iaep.getScenePreviewEditionPanel().removeElement(dataControl.getActiveAreas().get(table.getSelectedRow()));
		dataControl.deleteElement(dataControl.getActiveAreas().get(table.getSelectedRow()), true);
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
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
		}
	}
	
	protected void updateAuxPanel() {
		if (auxPanel == null)
			return;
		auxPanel.removeAll();
		if (table.getSelectedRow() == -1) {
			previewAuxSplit.setDividerLocation(previewAuxSplit.getMaximumDividerLocation());
			return;
		}
		
		auxPanel.setLayout(new BorderLayout());
		auxPanel.add(new SmallActionsListPanel(dataControl.getActiveAreas().get(this.table.getSelectedRow()).getActionsList()));

		previewAuxSplit.setDividerLocation(Integer.MAX_VALUE);
	}
}
