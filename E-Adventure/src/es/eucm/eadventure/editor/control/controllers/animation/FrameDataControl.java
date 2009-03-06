package es.eucm.eadventure.editor.control.controllers.animation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.animation.ChangeImageURITool;
import es.eucm.eadventure.editor.control.tools.animation.ChangeSoundUriTool;
import es.eucm.eadventure.editor.control.tools.animation.ChangeTimeTool;
import es.eucm.eadventure.editor.control.tools.animation.ChangeWaitForClick;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class FrameDataControl extends DataControlWithResources {

	private Frame frame;
	
	private List<ResourcesDataControl> resourcesDataControls;
	
	private int selectedResources;
	
	public FrameDataControl(Frame frame) {
		this.frame = frame;
		resourcesDataControls = new ArrayList<ResourcesDataControl>();
		for (Resources resources : frame.getResources()) {
			resourcesDataControls.add(new ResourcesDataControl(resources, Controller.ANIMATION));
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
	public boolean addElement(int type) {
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
		if (resourcesDataControls.size() == 0)
			return null;
		return resourcesDataControls.get(resourcesDataControls.size() - 1);
	}

	@Override
	public List<ResourcesDataControl> getResources() {
		return resourcesDataControls;
	}

	@Override
	public int getResourcesCount() {
		return resourcesDataControls.size();
	}

	@Override
	public int getSelectedResources() {
		return selectedResources;
	}

	@Override
	public void setSelectedResources(int selectedResources) {
		this.selectedResources = selectedResources;
	}


}
