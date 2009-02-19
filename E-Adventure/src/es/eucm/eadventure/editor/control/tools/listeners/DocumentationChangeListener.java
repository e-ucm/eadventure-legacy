package es.eucm.eadventure.editor.control.tools.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.control.tools.general.ChangeDocumentationTool;

public class DocumentationChangeListener implements DocumentListener {

	private Documented documented;
	
	private JTextComponent textComponent;
	
	public DocumentationChangeListener(JTextComponent textComponent, Documented documented) {
		this.textComponent = textComponent;
		this.documented = documented;
	}
	
	public void changedUpdate(DocumentEvent e) {
		// DO NOTHING
	}

	public void insertUpdate(DocumentEvent e) {
		Tool tool = new ChangeDocumentationTool(documented, textComponent.getText());
		Controller.getInstance().addTool(tool);
	}

	public void removeUpdate(DocumentEvent e) {
		Tool tool = new ChangeDocumentationTool(documented, textComponent.getText());
		Controller.getInstance().addTool(tool);
	}

}
