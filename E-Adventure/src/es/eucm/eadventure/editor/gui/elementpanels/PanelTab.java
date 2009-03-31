package es.eucm.eadventure.editor.gui.elementpanels;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class PanelTab {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7666569367118952601L;

	private String title;
	
	private JComponent panel;
	
	public PanelTab(String title, JComponent panel) {
		super();
		this.title = title;
		this.panel = panel;
	}
	
	public String getTitle() {
		return title;
	}
	
	public JComponent getComponent() {
		return panel;
	}
	
}
