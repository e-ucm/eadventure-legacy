package es.eucm.eadventure.editor.control.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HTMLEditController {

	private String filename;
	
	private boolean newFile;
	
	private List<File> images = new ArrayList<File>();
	
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
	
	public List<File> getImages() {
		return images;
	}

}
