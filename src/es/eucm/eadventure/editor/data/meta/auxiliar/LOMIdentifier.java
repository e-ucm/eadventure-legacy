package es.eucm.eadventure.editor.data.meta.auxiliar;

import java.util.ArrayList;

import es.eucm.eadventure.common.gui.TextConstants;

public class LOMIdentifier extends LOMESContainer{

    
    	//public static final String 
    
    	public static final String CATALOG_DEFAULT = "Catálogo unificado mec-red.es-ccaa de identificación de ODE";
	
    	//take care when this class is call from MetaMetaData: it must be added suffix "-meta" 
    	public static final String ENTRY_DEFAULT = "es-ma_20090317_2_1300009";
    	
    	public boolean isFromMeta;
    	
	public LOMIdentifier(boolean isFromMeta){
		super();
		this.isFromMeta = isFromMeta;
		if (isFromMeta)
		    add(new LOMESGeneralId(CATALOG_DEFAULT,ENTRY_DEFAULT+"-meta"));
		else 
		    add(new LOMESGeneralId(CATALOG_DEFAULT,ENTRY_DEFAULT));
	}
	
	public LOMIdentifier(String catalog, String entry,boolean isFromMeta){
		super();
		this.isFromMeta = isFromMeta;
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
