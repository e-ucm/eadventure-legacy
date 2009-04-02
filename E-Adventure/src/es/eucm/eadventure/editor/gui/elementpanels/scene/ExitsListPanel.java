package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitsListDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.ExitsTable;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class ExitsListPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	private static final int HORIZONTAL_SPLIT_POSITION = 140;
	
	private ExitsListDataControl dataControl;
	
	private IrregularAreaEditionPanel iaep;
	
	private ExitsTable table;
	
	private JButton deleteButton;
	
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

		JPanel tablePanel = createTablePanel(iaep);

		JSplitPane tableWithSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel, iaep);
		tableWithSplit.setOneTouchExpandable(true);
		tableWithSplit.setDividerLocation(HORIZONTAL_SPLIT_POSITION);
		tableWithSplit.setContinuousLayout(true);
		tableWithSplit.setResizeWeight(0.5);
		tableWithSplit.setDividerSize(10);
	
		setLayout( new BorderLayout( ) );
		add(tableWithSplit,BorderLayout.CENTER);
		
		
		addElementsToPreview(spep, scenePath);
	}

	private JPanel createTablePanel(IrregularAreaEditionPanel iaep2) {
		JPanel tablePanel = new JPanel();
		
		table = new ExitsTable(dataControl, iaep);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setMinimumSize(new Dimension(0, 	HORIZONTAL_SPLIT_POSITION));

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (table.getSelectedRow() >= 0)
					deleteButton.setEnabled(true);
				else
					deleteButton.setEnabled(false);
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
		buttonsPanel.setLayout(new GridLayout(1,2));
		buttonsPanel.add(newButton);
		buttonsPanel.add(deleteButton);
		
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
}
