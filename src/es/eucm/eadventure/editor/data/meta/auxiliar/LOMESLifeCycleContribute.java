package es.eucm.eadventure.editor.data.meta.auxiliar;

import java.util.ArrayList;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMESLifeCycleContribute implements LOMESComposeType{

	
	private static final int numberRoleValues = 14;
	
	//2.3.1
	private Vocabulary role; 
	
	//2.3.2 
	private ArrayList<String> entity;
	
	private LOMESLifeCycleDate date;
	
	public LOMESLifeCycleContribute(){
		role = new Vocabulary(Vocabulary.LC_CONTRIBUTION_TYPE_2_3_1);
		entity = new ArrayList<String>();
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
		String[] attr = new String[LOMESGeneralId.NUMBER_ATTR];
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


	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static String[] getRoleVocabularyType(){
		return Vocabulary.LC_CONTRIBUTION_TYPE_2_3_1;
	}
	
	public static String[] getRoleOptions( ) {
		String[] options = new String[numberRoleValues];
		for (int i=0; i<options.length; i++){
			options[i]=TextConstants.getText( "LOMES.LifeCycle.Role"+i );
		}
		return options;
	}

}
