package es.eucm.eadventure.editor.control.tools.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.control.tools.general.ChangeDescriptionTool;

public class DescriptionChangeListener implements DocumentListener {

	private Described described;
	
	private JTextComponent textComponent;
	
	public DescriptionChangeListener(JTextComponent textComponent, Described described) {
		this.textComponent = textComponent;
		this.described = described;
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {
		// DO NOTHING
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		Tool tool = new ChangeDescriptionTool(described, textComponent.getText());
		Controller.getInstance().addTool(tool);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		Tool tool = new ChangeDescriptionTool(described, textComponent.getText());
		Controller.getInstance().addTool(tool);
	}

}
