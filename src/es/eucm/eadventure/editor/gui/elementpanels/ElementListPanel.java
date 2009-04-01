package es.eucm.eadventure.editor.gui.elementpanels;

import javax.swing.JPanel;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitsListDataControl;

public class ElementListPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7985000472634193190L;

	private DataControl dataControl;
	
	private ExitsListDataControl exitsListDataControl; 
	
	public ElementListPanel(DataControl dataControl) {
		this.dataControl = dataControl;
		if (dataControl instanceof ExitsListDataControl) {
			exitsListDataControl = (ExitsListDataControl) dataControl;
		}
		
		
		
	}	
}
