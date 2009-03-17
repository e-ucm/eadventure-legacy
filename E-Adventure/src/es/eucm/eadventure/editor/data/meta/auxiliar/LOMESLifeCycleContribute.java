package es.eucm.eadventure.editor.data.meta.auxiliar;

import java.util.ArrayList;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMESLifeCycleContribute implements LOMESComposeType{

    public static final int NUMBER_ATTR = 3;
	
	private static final int numberRoleLifecycleValues = 14;
	
	private static final int numberRoleMetametaValues = 2;
	//2.3.1
	private Vocabulary role; 
	
	//2.3.2 
	private ArrayList<String> entity;
	
	private LOMESLifeCycleDate date;
	
	public LOMESLifeCycleContribute(String[] roleValues){
		role = new Vocabulary(roleValues, Vocabulary.LOM_ES_SOURCE, 0);
		entity = new ArrayList<String>();
		entity.add(new String("Empty"));
		date = new LOMESLifeCycleDate();
	}
	
	
	public LOMESLifeCycleContribute(Vocabulary role,ArrayList<String> entity,LOMESLifeCycleDate date){
		this.role = role;
		this.entity = entity;
		this.date = date;
	}

	public Vocabulary getRole() {
		return role;
	}

	public void setRole(Vocabulary role) {
		this.role = role;
	}

	public ArrayList<String> getEntity() {
		return entity;
	}

	public void setEntity(ArrayList<String> entity) {
		this.entity = entity;
	}

	public static String[] attributes() {
		String[] attr = new String[NUMBER_ATTR];
		attr[0] = TextConstants.getText("LOMES.GeneralId.CatalogName")+" "+ATTR_STRING;
		attr[0] = TextConstants.getText("LOMES.GeneralId.EntryName")+" "+ATTR_STRING;
		return attr;
	}


	/**
	 * @return the date
	 */
	public LOMESLifeCycleDate getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(LOMESLifeCycleDate date) {
		this.date = date;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static String[] getRoleLifeCycleVocabularyType(){
		return Vocabulary.LC_CONTRIBUTION_TYPE_2_3_1;
	}
	
	public static String[] getRoleMetametaVocabularyType(){
		return Vocabulary.MD_CONTRIBUTION_TYPE_2_3_1;
	}
	
	public static String[] getRoleLifeCycleOptions( ) {
		String[] options = new String[numberRoleLifecycleValues];
		for (int i=0; i<options.length; i++){
			options[i]=TextConstants.getText("LOMES.LifeCycle.Role"+i);
		}
		return options;
	}
	
	public static String[] getRoleMetametaOptions( ) {
		String[] options = new String[numberRoleMetametaValues];
		for (int i=0; i<options.length; i++){
			options[i]=TextConstants.getText("LOMES.Metameta.Role"+i);
		}
		return options;
	}

}
