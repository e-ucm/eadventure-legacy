package es.eucm.eadventure.editor.gui.otherpanels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.editor.control.controllers.TrajectoryScenePreviewEditionController;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.TrajectoryDataControl;
import es.eucm.eadventure.common.gui.TextConstants;

/**
 * A Panel for the edition of trajectorys and barriers, including the necessary
 * buttons.
 * 
 * @author Eugenio Marchiori
 */
public class TrajectoryEditionPanel extends JPanel {

	/**
	 * Default generated serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The preview and edition panel
	 */
	private ScenePreviewEditionPanel spep;
	
	/**
	 * The mouse controller for the panel
	 */
	protected TrajectoryScenePreviewEditionController tspec;
	
	/**
	 * Default constructor, with the path to the background and the trajectoryDataControl
	 * 
	 * @param scenePath path to the background image
	 * @param trajectoryDataControl the trajectoryDataControl
	 */
	public TrajectoryEditionPanel(String scenePath, TrajectoryDataControl trajectoryDataControl) {
		setLayout(new BorderLayout());
		spep = new ScenePreviewEditionPanel(scenePath);
		tspec = new TrajectoryScenePreviewEditionController(spep, trajectoryDataControl);
		spep.changeController(tspec);
		spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_NODE, true);
		for (NodeDataControl nodeDataControl: trajectoryDataControl.getNodes())
			spep.addNode(nodeDataControl);
		spep.setTrajectory((Trajectory) trajectoryDataControl.getContent());
		
		add(createButtonPanel(), BorderLayout.NORTH);
		
		add(spep, BorderLayout.CENTER);		
	}
	
	/**
	 * Create the button panel
	 * 
	 * @return the new button panel
	 */
	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(createToolButton("BarriersList.EditNodes", TrajectoryScenePreviewEditionController.NODE_EDIT));
		buttonPanel.add(createToolButton("BarriersList.EditSides", TrajectoryScenePreviewEditionController.SIDE_EDIT));
		buttonPanel.add(createToolButton("BarriersList.SelectInitialNode", TrajectoryScenePreviewEditionController.SELECT_INITIAL));
		buttonPanel.add(createToolButton("BarriersList.DeleteTool", TrajectoryScenePreviewEditionController.DELETE_TOOL));
		buttonPanel.add(createToolButton("BarriersList.EditBarriers", TrajectoryScenePreviewEditionController.EDIT_BARRIERS));
		return buttonPanel;
	}
	
	/**
	 * Create a tool button
	 * 
	 * @param text The text of the button
	 * @param tool The tool asosiated with the button
	 * @return The new button
	 */
	private JButton createToolButton(String text, final int tool) {
		
		JButton button;
		if (tool == TrajectoryScenePreviewEditionController.NODE_EDIT) {
			ImageIcon icon = new ImageIcon("img/icons/nodeEdit.png");
			button = new JButton(icon);
		}else 
			button = new JButton(TextConstants.getText(text));

		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tspec.setSelectedTool(tool);
			}
		});
		return button;
		
	}
	
	/**
	 * Returns the preview panel
	 * 
	 * @return the preview panel
	 */
	public ScenePreviewEditionPanel getScenePreviewEditionPanel() {
		return spep;
	}
}
