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
package es.eucm.eadventure.editor.control.tools.general.assets;

import java.util.List;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DuplicateResourcesBlockTool extends Tool{

	private DataControl dataControl;
	/**
	 * List of resources.
	 */
	protected List<Resources> resourcesList;

	/**
	 * List of resources controllers.
	 */
	protected List<ResourcesDataControl> resourcesDataControlList;
	
	// Temp data
	private Resources newElement;
	private ResourcesDataControl newDataControl; 
	private DataControlWithResources parent;
	
	public DuplicateResourcesBlockTool ( DataControl dataControl, List<Resources> resourcesList, List<ResourcesDataControl> resourcesDataControlList, DataControlWithResources parent){
		this.dataControl = dataControl;
		this.resourcesList = resourcesList;
		this.resourcesDataControlList = resourcesDataControlList;
		this.parent = parent;
	}
	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		if (!(dataControl instanceof ResourcesDataControl))
			return false;
		
		try {
			newElement = (Resources) (((Resources) (dataControl.getContent())).clone());
			newDataControl = new ResourcesDataControl(newElement, ((ResourcesDataControl) dataControl).getResourcesType() );
			resourcesList.add(newElement);
			resourcesDataControlList.add( newDataControl );
			parent.setSelectedResources(resourcesList.size()-1);
			return true;
		} catch (CloneNotSupportedException e) {
			ReportDialog.GenerateErrorReport(e, true, "Could not clone resources");	
			return false;
		} 		
	}

	@Override
	public boolean redoTool() {
		resourcesList.add(newElement);
		resourcesDataControlList.add( newDataControl );
		parent.setSelectedResources(resourcesList.size()-1);
		Controller.getInstance().reloadPanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		boolean undone= resourcesList.remove(newElement) && resourcesDataControlList.remove(newDataControl);
		if (undone){
			parent.setSelectedResources(resourcesList.size()-1);
			Controller.getInstance().reloadPanel();
		}
		return undone;
	}

}
