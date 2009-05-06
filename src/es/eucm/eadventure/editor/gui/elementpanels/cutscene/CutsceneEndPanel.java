package es.eucm.eadventure.editor.gui.elementpanels.cutscene;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;

public class CutsceneEndPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param cutsceneDataControl
	 *            Cutscene controller
	 */
	public CutsceneEndPanel( CutsceneDataControl cutsceneDataControl ) {
		// Set the layout
		setLayout( new GridBagLayout( ) );

		GridBagConstraints c = new GridBagConstraints( );

		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.3;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		add( new NextScenePanel(cutsceneDataControl) , c);
	}
}