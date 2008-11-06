package es.eucm.eadventure.adventureeditor.data.lomdata;

import java.util.ArrayList;

import es.eucm.eadventure.adventureeditor.control.controllers.lom.LOMTextDataControl;

public class LOMLifeCycle {

	//2.1
	private LangString version;
	
	
	public LOMLifeCycle (){
		version = null;
}
	
	
	/***********************************ADD METHODS **************************/
	public void addVersion(LangString version){
		this.version=version;
	}
	
	/*********************************** SETTERS **************************/
	public void setVersion(LangString version){
		this.version=version;
	}
	
	/*********************************** GETTERS **************************/
	//TITLE
	public LangString getVersion(){
		return version;
	}

}
