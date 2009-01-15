package es.eucm.eadventure.editor.gui.otherpanels;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class PreviewPanel extends JPanel {

	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private ScenePreviewEditionPanel spep;
	
	private JPanel checkBoxPanel;
	
	public PreviewPanel() {
		this(null);
	}
	
	public PreviewPanel(String scenePath) {
		super();
		if (scenePath == null)
			spep = new ScenePreviewEditionPanel();
		else
			spep = new ScenePreviewEditionPanel(scenePath);
		setLayout(new BorderLayout());
		add(spep, BorderLayout.CENTER);
		
		checkBoxPanel = spep.createCheckBoxPanel();
		add(checkBoxPanel, BorderLayout.SOUTH);
	}

	public void recreateCheckBoxPanel() {
		remove(checkBoxPanel);
		checkBoxPanel = spep.createCheckBoxPanel();
		add(checkBoxPanel, BorderLayout.SOUTH);
		
	}
	
	public ScenePreviewEditionPanel getScenePreviewEditionPanel() {
		return spep;
	}
}
