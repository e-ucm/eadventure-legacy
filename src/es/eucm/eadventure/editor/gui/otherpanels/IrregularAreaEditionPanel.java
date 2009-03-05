package es.eucm.eadventure.editor.gui.otherpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import es.eucm.eadventure.editor.control.controllers.IrregularAreaEditionController;
import es.eucm.eadventure.editor.control.controllers.TrajectoryScenePreviewEditionController;
import es.eucm.eadventure.editor.control.controllers.scene.PointDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.RectangleArea;
import es.eucm.eadventure.common.gui.TextConstants;

/**
 * A Panel for the edition of irregular active areas and exits
 * 
 * @author Eugenio Marchiori
 */
public class IrregularAreaEditionPanel extends JPanel {

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
	protected IrregularAreaEditionController iaec;
	
	/**
	 * Default constructor, with the path to the background and the trajectoryDataControl
	 * 
	 * @param scenePath path to the background image
	 * @param trajectoryDataControl the trajectoryDataControl
	 */
	public IrregularAreaEditionPanel(String scenePath, RectangleArea rectangleArea, Color color) {
		setLayout(new BorderLayout());
		spep = new ScenePreviewEditionPanel(false, scenePath);
		iaec = new IrregularAreaEditionController(spep, rectangleArea, color);
		spep.changeController(iaec);
		for (Point point: rectangleArea.getPoints())
			spep.addPoint(new PointDataControl(point));
		spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_POINT, true);
		
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
		ButtonGroup group = new ButtonGroup();
	    buttonPanel.add(createToolButton("BarriersList.EditNodes", IrregularAreaEditionController.POINT_EDIT, "img/icons/nodeEdit.png", group));
		buttonPanel.add(createToolButton("BarriersList.DeleteTool", IrregularAreaEditionController.DELETE_TOOL, "img/icons/deleteTool.png", group));
		return buttonPanel;
	}
	
	/**
	 * Create a tool button
	 * 
	 * @param text The text of the button
	 * @param tool The tool asosiated with the button
	 * @param group 
	 * @return The new button
	 */
	private AbstractButton createToolButton(String text, final int tool, String iconPath, ButtonGroup group) {
		AbstractButton button;
		ImageIcon icon = new ImageIcon(iconPath);
		button = new JToggleButton(icon);
		group.add(button);
		button.setToolTipText(TextConstants.getText(text));

		if (tool == TrajectoryScenePreviewEditionController.NODE_EDIT) {
			button.setSelected(true);
		}
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iaec.setSelectedTool(tool);
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
