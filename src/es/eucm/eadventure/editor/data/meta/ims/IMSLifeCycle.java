package es.eucm.eadventure.editor.data.meta.ims;

import java.util.ArrayList;

import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class IMSLifeCycle {

	//2.1
	private LangString version;
	
	//2.2 
	private ArrayList<Vocabulary> status;
	
	
	
	public IMSLifeCycle (){
		version = null;
		status = new ArrayList<Vocabulary>();
}
	
	
	/***********************************ADD METHODS **************************/
	public void addVersion(LangString version){
		this.version=version;
	}
	
	public void addStatus(int index){
		this.status.add(new Vocabulary(Vocabulary.LC_STAUS_VALUE_2_2,index));
	}
	
	/*********************************** SETTERS **************************/
	public void setVersion(LangString version){
		this.version=version;
	}
	public void setStatus(int index){
		this.status= new ArrayList<Vocabulary>();
		this.status.add( new Vocabulary(Vocabulary.LC_STAUS_VALUE_2_2,index) );
	}
	
	/*********************************** GETTERS **************************/

	public LangString getVersion(){
		return version;
	}
	
	public Vocabulary getStatus(){
		return status.get(0);
	}

}
