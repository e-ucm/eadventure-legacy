package es.eucm.eadventure.editor.gui.elementpanels.condition;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import es.eucm.eadventure.editor.control.controllers.ConditionsController;

public class CompositeConditionButtonsPanel extends JPanel{

	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	
	private ConditionsController controller;
	
	private int layout;
	
	private JButton editButton;
	private JButton addButton;
	private JButton deleteButton;
	private JButton moveLeftUpButton;
	private JButton moveRightDownButton;
	
	public CompositeConditionButtonsPanel (ConditionsController controller, int layout){
		this.controller = controller;
		this.layout = layout;
		if (layout == HORIZONTAL){
			setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
			moveLeftUpButton = new JButton("img/icons/moveNodeLeft.png");
			moveRightDownButton = new JButton("img/icons/moveNodeRight.png");
		}
		else if (layout == VERTICAL){
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			moveLeftUpButton = new JButton("img/icons/moveNodeUp.png");
			moveRightDownButton = new JButton("img/icons/moveNodeDown.png");
		}
		editButton = new JButton("img/icons/edit.png");
		addButton = new JButton("img/icons/addNode.png");
		deleteButton = new JButton("img/icons/deleteNode.png");
		add(editButton);
		add(addButton);
		add(deleteButton);
		add(moveLeftUpButton);
		add(moveRightDownButton);
	}
	
}
