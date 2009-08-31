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
package es.eucm.eadventure.editor.control.controllers.general;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeNameTool;

public class CustomActionDataControl extends ActionDataControl {

	/**
	 * Contained customAction structure
	 */
	private CustomAction customAction;
	
	/**
	 * Default constructor
	 * 
	 * @param action the custom Action
	 */
	public CustomActionDataControl(CustomAction action) {
		super(action);
		customAction = action;
		
		this.resourcesList = customAction.getResources( );
		if (this.resourcesList.size() == 0)
			this.resourcesList.add(new Resources());
		selectedResources = 0;

		resourcesDataControlList = new ArrayList<ResourcesDataControl>();
		for (Resources resources: resourcesList) {
			resourcesDataControlList.add(new ResourcesDataControl( resources, Controller.ACTION_CUSTOM ));
		}

	}
		
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		controller.addTool(new ChangeNameTool(customAction, name));
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = super.countAssetReferences(assetPath);
		
		for (ResourcesDataControl resources : resourcesDataControlList)
			count += resources.countAssetReferences(assetPath);
		
		return count;
	}

	/**
	 * @return the value of name
	 */
	public String getName() {
		return customAction.getName();
	}

	@Override
	public void recursiveSearch() {
		super.recursiveSearch();
		check(this.getName(), TextConstants.getText("Search.Name"));
	}

	@Override
	public List<Searchable> getPathToDataControl(Searchable dataControl) {
		return getPathFromChild(dataControl, resourcesDataControlList);
	}

}
