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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
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
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitsListDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.gui.DataControlsPanel;
import es.eucm.eadventure.editor.gui.editdialogs.EffectsDialog;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ExitsTable;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class ExitsListPanel extends JPanel implements DataControlsPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	private static final int HORIZONTAL_SPLIT_POSITION = 140;

	public static final int VERTICAL_SPLIT_POSITION = 150;

	private ExitsListDataControl dataControl;
	
	private IrregularAreaEditionPanel iaep;
	
	private ExitsTable table;
	
	private JPanel auxPanel;
	
	private JButton deleteButton;
	
	private JSplitPane previewAuxSplit;
	
	/**
	 * Constructor.
	 * 
	 * @param exitsListDataControl
	 *            Exits list controller
	 */
	public ExitsListPanel( ExitsListDataControl exitsListDataControl ) {
		this.dataControl = exitsListDataControl;
		String scenePath = Controller.getInstance( ).getSceneImagePath( exitsListDataControl.getParentSceneId( ) );
		iaep = new IrregularAreaEditionPanel(scenePath, null, dataControl.getSceneDataControl().getTrajectory().hasTrajectory(), Color.RED);
		ScenePreviewEditionPanel spep = iaep.getScenePreviewEditionPanel();
		
		if (dataControl.getSceneDataControl().getTrajectory().hasTrajectory()) {
			spep.setTrajectory((Trajectory) dataControl.getSceneDataControl().getTrajectory().getContent());
			for (NodeDataControl nodeDataControl: dataControl.getSceneDataControl().getTrajectory().getNodes())
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
		
		JPanel tablePanel = createTablePanel(iaep, previewAuxSplit);
		JSplitPane tableWithSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel, previewAuxSplit);
		tableWithSplit.setOneTouchExpandable(true);
		tableWithSplit.setDividerLocation(HORIZONTAL_SPLIT_POSITION);
		tableWithSplit.setContinuousLayout(true);
		tableWithSplit.setResizeWeight(0);
		tableWithSplit.setDividerSize(10);
	
		add(tableWithSplit,BorderLayout.CENTER);
		
		addElementsToPreview(spep, scenePath);
	}

	private JPanel createTablePanel(IrregularAreaEditionPanel iaep2, JSplitPane previewAuxSplit2) {
		JPanel tablePanel = new JPanel();
		
		table = new ExitsTable(dataControl, iaep, previewAuxSplit);
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
				addExit();
			}
		});
		deleteButton = new JButton(new ImageIcon("img/icons/deleteNode.png"));
		deleteButton.setContentAreaFilled( false );
		deleteButton.setMargin( new Insets(0,0,0,0) );
		deleteButton.setToolTipText( TextConstants.getText( "ItemReferenceTable.Delete" ) );
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				deleteExit();
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

	private void addElementsToPreview(ScenePreviewEditionPanel spep, String scenePath) {
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
			for( BarrierDataControl barrier : dataControl.getParentSceneBarriers( ) ) {
				spep.addBarrier(barrier);
			}
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_BARRIER, false);
			for( ActiveAreaDataControl activeArea : dataControl.getParentSceneActiveAreas( ) ) {
				spep.addActiveArea(activeArea);
			}
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_ACTIVEAREA, false);

			for( ExitDataControl exit : dataControl.getExits( ) ) {
				spep.addExit(exit);
			}
		}
	}
	
	protected void addExit() {
		if (dataControl.addElement(dataControl.getAddableElements()[0], null)) {
			iaep.getScenePreviewEditionPanel().addExit(dataControl.getLastExit());
			iaep.repaint();
			table.getSelectionModel().setSelectionInterval(dataControl.getExits().size() - 1, dataControl.getExits().size() - 1);
			((AbstractTableModel) table.getModel()).fireTableDataChanged();
		}
	}
	
	protected void deleteExit() {
		iaep.getScenePreviewEditionPanel().removeElement(dataControl.getExits().get(table.getSelectedRow()));
		dataControl.deleteElement(dataControl.getExits().get(table.getSelectedRow()), true);
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
	}
	
	protected void updateAuxPanel() {
		if (auxPanel == null)
			return;
		auxPanel.removeAll();
		if (table.getSelectedRow() == -1) {
			previewAuxSplit.setDividerLocation(previewAuxSplit.getMaximumDividerLocation());
			return;
		}
		
		auxPanel.setLayout(new GridBagLayout());
		final ExitDataControl exit = dataControl.getExits().get(table.getSelectedRow());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		JButton editConditions = new JButton("Edit conditions");
		auxPanel.add(editConditions, c);
		
		c.gridy++;
		
		auxPanel.add(new JLabel("Conditions active"), c);
		
		c.gridy++;
		JButton editEffects = new JButton("Edit effects");
		editEffects.addActionListener(new EditEffectsListener(exit.getEffects()));
		auxPanel.add(editEffects, c);
		
		c.gridy++;
		JButton editPostEffects = new JButton("Edit post-effects");
		editPostEffects.addActionListener(new EditEffectsListener(exit.getPostEffects()));
		auxPanel.add(editPostEffects, c);
		
		c.gridy++;
		auxPanel.add(new JLabel("Conditions inactive"), c);
		
		c.gridy++;
		final JCheckBox activeWhenFalseConditions = new JCheckBox("Active when false conditions");
		activeWhenFalseConditions.setSelected(exit.isHasNotEffects());
		auxPanel.add(activeWhenFalseConditions, c);
		
		c.gridy++;
		final JButton editNotEffects = new JButton("Edit not-effects");
		editNotEffects.setEnabled(exit.isHasNotEffects());
		editNotEffects.addActionListener(new EditEffectsListener(exit.getNotEffects()));
		auxPanel.add(editNotEffects, c);
		
		activeWhenFalseConditions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exit.setHasNotEffects(activeWhenFalseConditions.isSelected());
				editNotEffects.setEnabled(activeWhenFalseConditions.isSelected());
			}
		});
		
		previewAuxSplit.setDividerLocation(Integer.MAX_VALUE);
	}
	
	private class EditEffectsListener implements ActionListener {
		private EffectsController effects;
		
		public EditEffectsListener(EffectsController effects) {
			this.effects = effects;
		}
		
		public void actionPerformed(ActionEvent e) {
			new EffectsDialog( effects );
		}
	}
	
	@Override
	public void setSelectedItem(List<DataControl> path) {
		if (path.size() > 0) {
			for (int i = 0 ; i < dataControl.getExits().size(); i++) {
				if (dataControl.getExits().get(i) == path.get(path.size() -1))
					table.changeSelection(i, i, false, false);
			}
		}
	}
	
}
