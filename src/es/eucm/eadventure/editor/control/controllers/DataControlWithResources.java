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
package es.eucm.eadventure.editor.control.controllers;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.general.assets.DeleteResourcesBlockTool;
import es.eucm.eadventure.editor.control.tools.general.assets.DuplicateResourcesBlockTool;

public abstract class DataControlWithResources extends DataControl {

	/**
	 * List of resources.
	 */
	protected List<Resources> resourcesList;

	/**
	 * List of resources controllers.
	 */
	protected List<ResourcesDataControl> resourcesDataControlList;

	/**
	 * The resources that must be used in the previews.
	 */
	protected int selectedResources;

	public List<ResourcesDataControl> getResources( ) {
		return resourcesDataControlList;
	}

	public int getResourcesCount( ) {
		return resourcesDataControlList.size();
	}

	/**
	 * Returns the last resources controller of the list.
	 * 
	 * @return Last resources controller
	 */
	public ResourcesDataControl getLastResources( ) {
		return resourcesDataControlList.get( resourcesDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the selected resources block of the list.
	 * 
	 * @return Selected block of resources
	 */
	public int getSelectedResources( ) {
		return selectedResources;
	}

	/**
	 * Sets the new selected resources block of the list.
	 * 
	 * @param selectedResources
	 *            New selected block of resources
	 */
	public void setSelectedResources( int selectedResources ) {
		this.selectedResources = selectedResources;
	}
	
	@Override
	// This method only caters for deleting RESOURCES. Subclasses should override this method
	// to implement removal of other element types
	public boolean deleteElement( DataControl dataControl , boolean askConfirmation) {
		return controller.addTool( new DeleteResourcesBlockTool(resourcesList, resourcesDataControlList, dataControl, this) );
	}
	
	public boolean duplicateResources(DataControl dataControl) {
		return controller.addTool( new DuplicateResourcesBlockTool(dataControl, resourcesList, resourcesDataControlList, this) );
	}
	
}