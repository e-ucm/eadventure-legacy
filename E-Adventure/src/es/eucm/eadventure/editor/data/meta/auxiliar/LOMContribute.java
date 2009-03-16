package es.eucm.eadventure.editor.data.meta.auxiliar;

import java.util.ArrayList;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMContribute extends LOMESContainer{

	
	public LOMContribute(String[] roleValues){
		super();
		add(new LOMESLifeCycleContribute(roleValues));
	
	}
	
	public LOMContribute(Vocabulary role,ArrayList<String> entity,LOMESLifeCycleDate date){
		super();
		add(new LOMESLifeCycleContribute(role,entity,date));
	}
	
	public void addContribute(Vocabulary role,ArrayList<String> entity,LOMESLifeCycleDate date){
		add(new LOMESLifeCycleContribute(role,entity,date));
	}
   
	public void addContribute(Vocabulary role,ArrayList<String> entity,LOMESLifeCycleDate date,int index){
		if (index ==0){
			add(new LOMESLifeCycleContribute(role,entity,date));
		}else {
		delete(index-1);
		add(new LOMESLifeCycleContribute(role,entity,date),index-1);

		}
	}
	
	
	public String[] elements(){
		String[] elements= new String[container.size()];
		for (int i=0; i<container.size();i++)
			elements[i] = ((LOMESLifeCycleContribute)container.get(i)).getRole().getValue();
		return elements;
	}

	@Override
	public String getTitle() {
		return TextConstants.getText( "LOMES.LCContribute.DialogTitle" );
	}

	@Override
	public String[] attributes() {
		return LOMESLifeCycleContribute.attributes();
	}
	
	

	

}
