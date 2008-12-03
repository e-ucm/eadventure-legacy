package es.eucm.eadventure.editor.control.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class HTMLEditController extends DataControlWithResources {

	private String filename;
	
	private boolean newFile;
	
	private ArrayList<File> images = new ArrayList<File>();
	
	@Override
	public ResourcesDataControl getLastResources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResourcesDataControl> getResources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getResourcesCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSelectedResources() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSelectedResources(int selectedResources) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean addElement(int type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canAddElement(int type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBeDeleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBeDuplicated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBeMoved() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canBeRenamed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int countAssetReferences(String assetPath) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countIdentifierReferences(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteAssetReferences(String assetPath) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deleteElement(DataControl dataControl) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteIdentifierReferences(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getAddableElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getAssetReferences(List<String> assetPaths,
			List<Integer> assetTypes) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid(String currentPath, List<String> incidences) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveElementDown(DataControl dataControl) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveElementUp(DataControl dataControl) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean renameElement() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void replaceIdentifierReferences(String oldId, String newId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateVarFlagSummary(VarFlagSummary varFlagSummary) {
		// TODO Auto-generated method stub

	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

	public void setNewFile(boolean newFile) {
		this.newFile = newFile;
	}

	public boolean isNewFile() {
		return newFile;
	}
	
	public void addImage(File imageFile) {
		images.add(imageFile);
	}
	
	public void removeImage(File imageFile) {
		images.remove(imageFile);
	}
	
	public ArrayList<File> getImages() {
		return images;
	}

}
