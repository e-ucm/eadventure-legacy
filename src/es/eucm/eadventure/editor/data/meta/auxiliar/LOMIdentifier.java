package es.eucm.eadventure.editor.data.meta.auxiliar;

import java.util.ArrayList;

import es.eucm.eadventure.common.gui.TextConstants;

public class LOMIdentifier extends LOMESContainer{

	
	public LOMIdentifier(){
		super();
	}
	
	public LOMIdentifier(String catalog, String entry){
		this();
		add(new LOMESGeneralId(catalog,entry));
	}
	
	public void addIdentifier(String catalog, String entry){
		add(new LOMESGeneralId(catalog,entry));
	}
	
	
	public void addIdentifier(String catalog, String entry,int index){
		// index is related with the set of options in container dialog. The index 0 means add new element,
	    	// and the others index are index-1 because the selected index == 0 is reserved for "add new element" 
	    	if (index ==0){
			add(new LOMESGeneralId(catalog,entry));
		}else {
		    delete(index-1);
		    add(new LOMESGeneralId(catalog,entry),index-1);

		}
	}
	
	
	public String[] elements(){
		String[] elements= new String[container.size()];
		for (int i=0; i<container.size();i++)
			elements[i] = ((LOMESGeneralId)container.get(i)).getEntry();
		return elements;
	}

	@Override
	public String getTitle() {
		return TextConstants.getText( "LOMES.GeneralIdentifier.DialogTitle" );
	}

	@Override
	public String[] attributes() {
		LOMESGeneralId generalId = new LOMESGeneralId();
		return generalId.attributes();
	}
	
	
}
