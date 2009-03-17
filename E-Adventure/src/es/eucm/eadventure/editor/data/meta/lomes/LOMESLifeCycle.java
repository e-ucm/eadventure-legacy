package es.eucm.eadventure.editor.data.meta.lomes;

import java.util.ArrayList;

import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMContribute;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESLifeCycleContribute;

public class LOMESLifeCycle {

    
    	public static final String VERSION_DEFAULT="V1.0";
	//2.1
	private LangString version;
	
	//2.2 
	private ArrayList<Vocabulary> status;
	
	//2.3 Contribute
	private LOMContribute contribute;
	
	public LOMESLifeCycle (){
		version = new LangString(VERSION_DEFAULT);
		contribute = null;
		status = new ArrayList<Vocabulary>();
	
}
	
	
	/***********************************ADD METHODS **************************/
	public void addVersion(LangString version){
		this.version=version;
	}
	
	public void addStatus(int index){
		this.status.add(new Vocabulary(Vocabulary.LC_STAUS_VALUE_2_2,Vocabulary.LOM_ES_SOURCE,index));
	}
	/*********************************** SETTERS **************************/
	
	public void setStatus(int index){
		this.status= new ArrayList<Vocabulary>();
		this.status.add( new Vocabulary(Vocabulary.LC_STAUS_VALUE_2_2,Vocabulary.LOM_ES_SOURCE,index) );
	}
	
	public void setVersion(LangString version){
		this.version=version;
	}
	
	

	
	/*********************************** GETTERS **************************/
	//VERSION
	public LangString getVersion(){
		return version;
	}
	
	public Vocabulary getStatus(){
		return status.get(0);
	}


	//CONTRIBUTION
	/**
	 * @return the contribute
	 */
	public LOMContribute getContribute() {
		return contribute;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(ArrayList<Vocabulary> status) {
		this.status = status;
	}


	/**
	 * @param contribute the contribute to set
	 */
	public void setContribute(LOMContribute contribute) {
		this.contribute = contribute;
	}
	
	
	
	
	
}
