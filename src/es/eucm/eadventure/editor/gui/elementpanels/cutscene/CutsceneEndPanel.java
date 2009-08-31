/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.gui.elementpanels.cutscene;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.gui.Updateable;

public class CutsceneEndPanel extends JPanel implements Updateable {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * NextScenePanel
	 */
	private NextScenePanel nextScenePanel;

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
		
		this.nextScenePanel = new NextScenePanel(cutsceneDataControl);
		add( nextScenePanel , c);
	}

	public boolean updateFields() {
		return nextScenePanel.updateFields();
	}
}
