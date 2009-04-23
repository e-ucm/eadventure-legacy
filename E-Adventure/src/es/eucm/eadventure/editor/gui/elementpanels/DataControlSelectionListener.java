package es.eucm.eadventure.editor.gui.elementpanels;

import es.eucm.eadventure.editor.control.controllers.DataControl;

/**
 * Interface for a listener to changes in the selection of a data control.
 * 
 * @author Eugenio Marchiori
 */
public interface DataControlSelectionListener {

	/**
	 * Set the that control that was selected.
	 * 
	 * @param dataControl The selected data control.
	 */
	public void dataControlSelected(DataControl dataControl);
	
}
