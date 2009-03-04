package es.eucm.eadventure.editor.control.tools.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import es.eucm.eadventure.common.data.Detailed;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDetailedDescriptionTool;

public class DetailedDescriptionChangeListener implements DocumentListener {

	private Detailed detailed;
	
	private JTextComponent textComponent;
	
	public DetailedDescriptionChangeListener(JTextComponent textComponent, Detailed detailed) {
		this.textComponent = textComponent;
		this.detailed = detailed;
	}
	
	public void changedUpdate(DocumentEvent e) {
		// DO NOTHING
	}

	public void insertUpdate(DocumentEvent e) {
		Tool tool = new ChangeDetailedDescriptionTool(detailed, textComponent.getText());
		Controller.getInstance().addTool(tool);
	}

	public void removeUpdate(DocumentEvent e) {
		Tool tool = new ChangeDetailedDescriptionTool(detailed, textComponent.getText());
		Controller.getInstance().addTool(tool);
	}

}
