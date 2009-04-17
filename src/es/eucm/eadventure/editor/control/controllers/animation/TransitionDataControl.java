package es.eucm.eadventure.editor.control.controllers.animation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.animation.Transition;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.animation.ChangeTimeTool;
import es.eucm.eadventure.editor.control.tools.animation.ChangeTransitionTypeTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class TransitionDataControl extends DataControl {

	private Transition transition;
	
	public TransitionDataControl(Transition transition) {
		this.transition = transition;
	}
	
	public long getTime() {
		return transition.getTime();
	}
	
	public void setTime(long newTime) {
		Controller.getInstance().addTool(new ChangeTimeTool(transition, newTime));
	}
	
	public int getType() {
		return transition.getType();
	}
	
	public void setType(int type) {
		Controller.getInstance().addTool(new ChangeTransitionTypeTool(transition, type));
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
		return null;
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
	public List<DataControl> getPathToDataControl(DataControl dataControl) {
		return null;
	}
}
