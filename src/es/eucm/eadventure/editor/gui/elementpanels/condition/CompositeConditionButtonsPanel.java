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
