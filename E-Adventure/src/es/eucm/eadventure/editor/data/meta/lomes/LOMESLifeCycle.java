package es.eucm.eadventure.editor.data.meta.lomes;

import java.util.ArrayList;

import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMESLifeCycle {

	//2.1
	private LangString version;
	
	//2.2 
	private ArrayList<Vocabulary> status;
	
	//2.3 Contribute
	//2.3.1
	private Vocabulary role; 
	
	//2.3.2 
	private String entity;
	
	//2.3.3 Date; It has 2 values, dateTime and description
	private String dateTime;
	
	private String description;
	
	public LOMESLifeCycle (){
		version = null;
		role = new Vocabulary(Vocabulary.LC_CONTRIBUTION_TYPE_2_3_1);
		status = new ArrayList<Vocabulary>();
		entity = null;
		dateTime = null;
		description = null;
}
	
	
	/***********************************ADD METHODS **************************/
	public void addVersion(LangString version){
		this.version=version;
	}
	
	public void addStatus(int index){
		this.status.add(new Vocabulary(Vocabulary.LC_STAUS_VALUE_2_2,index));
	}
	/*********************************** SETTERS **************************/
	
	public void setStatus(int index){
		this.status= new ArrayList<Vocabulary>();
		this.status.add( new Vocabulary(Vocabulary.LC_STAUS_VALUE_2_2,index) );
	}
	
	public void setVersion(LangString version){
		this.version=version;
	}
	
	public void setRole(int index){
		this.role = new Vocabulary(Vocabulary.LC_CONTRIBUTION_TYPE_2_3_1,index);
	}
	
	
	
	public void setEntity(String entity) {
		this.entity = entity;
	}
	
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
	
	public Vocabulary getRole(){
		return role;
	}


	public String getEntity() {
		return entity;
	}
	
	public String getDateTime() {
		return dateTime;
	}

	public String getDescription() {
		return description;
	}

	
}
