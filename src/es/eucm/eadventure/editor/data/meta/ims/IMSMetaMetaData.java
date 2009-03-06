package es.eucm.eadventure.editor.data.meta.ims;

import java.util.ArrayList;

import es.eucm.eadventure.editor.data.meta.LangString;

public class IMSMetaMetaData {

	//3.4
	private ArrayList<String> metadatascheme;
	
	
	public IMSMetaMetaData (){
		metadatascheme = new ArrayList<String>();
}
	
	
	/***********************************ADD METHODS **************************/
	public void addMetadatascheme(String metadatascheme){
		this.metadatascheme.add(metadatascheme);
	}
	
	/*********************************** SETTERS **************************/
	public void setMetadatascheme(String metadatascheme){
		this.metadatascheme = new ArrayList();
		this.metadatascheme.add(metadatascheme);
	}
	
	/*********************************** GETTERS **************************/
	public String getMetadatascheme(int i){
		return metadatascheme.get(i);
	}
	public String getMetadatascheme(){
		return metadatascheme.get(0);
	}
	
	
}
