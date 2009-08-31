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
package es.eucm.eadventure.editor.control.controllers.animation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.animation.ChangeImageURITool;
import es.eucm.eadventure.editor.control.tools.animation.ChangeSoundUriTool;
import es.eucm.eadventure.editor.control.tools.animation.ChangeTimeTool;
import es.eucm.eadventure.editor.control.tools.animation.ChangeWaitForClick;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class FrameDataControl extends DataControlWithResources {

	private Frame frame;
	
	public FrameDataControl(Frame frame) {
		this.frame = frame;
		resourcesDataControlList = new ArrayList<ResourcesDataControl>();
		for (Resources resources : frame.getResources()) {
			resourcesDataControlList.add(new ResourcesDataControl(resources, Controller.ANIMATION ));
		}
	}
	
	public long getTime() {
		return frame.getTime();
	}
	
	public void setTime(long time) {
		Controller.getInstance().addTool(new ChangeTimeTool(frame, time));
	}
	
	public void setImageURI(String uri) {
		Controller.getInstance().addTool(new ChangeImageURITool(frame, uri));
	}
	
	public String getImageURI() {
		return frame.getUri();
	}
	
	public boolean isWaitForClick() {
		return frame.isWaitforclick();
	}
	
	public void setWaitForClick(boolean waitForClick) {
		Controller.getInstance().addTool(new ChangeWaitForClick(frame, waitForClick));
	}
	
	public String getSoundUri() {
		return frame.getSoundUri();
	}
		
	public void setSoundURI(String uri) {
		Controller.getInstance().addTool(new ChangeSoundUriTool(frame, uri));
	}

	@Override
	public boolean addElement(int type, String id) {
		return false;
	}

	@Override
	public boolean canAddElement(int type) {
		return false;
	}

	@Override
	public boolean canBeDeleted() {
		return false;
	}
	
	@Override
	public boolean canBeDuplicated() {
		return false;
	}

	@Override
	public boolean canBeMoved() {
		return false;
	}

	@Override
	public boolean canBeRenamed() {
		return false;
	}

	@Override
	public int countAssetReferences(String assetPath) {
		return 0;
	}

	@Override
	public int countIdentifierReferences(String id) {
		return 0;
	}

	@Override
	public void deleteAssetReferences(String assetPath) {

	}

	@Override
	public boolean deleteElement(DataControl dataControl,
			boolean askConfirmation) {
		return false;
	}

	@Override
	public void deleteIdentifierReferences(String id) {

	}

	@Override
	public int[] getAddableElements() {
		return null;
	}

	@Override
	public void getAssetReferences(List<String> assetPaths,
			List<Integer> assetTypes) {

	}

	@Override
	public Object getContent() {
		return frame;
	}

	@Override
	public boolean isValid(String currentPath, List<String> incidences) {
		return false;
	}

	@Override
	public boolean moveElementDown(DataControl dataControl) {
		return false;
	}

	@Override
	public boolean moveElementUp(DataControl dataControl) {
		return false;
	}

	@Override
	public void recursiveSearch() {

	}

	@Override
	public String renameElement(String newName) {
		return null;
	}

	@Override
	public void replaceIdentifierReferences(String oldId, String newId) {

	}

	@Override
	public void updateVarFlagSummary(VarFlagSummary varFlagSummary) {

	}

	@Override
	public ResourcesDataControl getLastResources() {
		if (resourcesDataControlList.size() == 0)
			return null;
		return resourcesDataControlList.get(resourcesDataControlList.size() - 1);
	}

	@Override
	public List<Searchable> getPathToDataControl(Searchable dataControl) {
		return getPathFromChild(dataControl, resourcesDataControlList);
	}

}
