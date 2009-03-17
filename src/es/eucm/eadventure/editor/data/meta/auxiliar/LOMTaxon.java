package es.eucm.eadventure.editor.data.meta.auxiliar;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMTaxon extends LOMESContainer{

	
	public LOMTaxon(){
		super();
		add(new  LOMClassificationTaxon());
	}
	
	public LOMTaxon(String identifier,LangString entry){
		this();
		add(new  LOMClassificationTaxon(identifier,entry));
	}
	
	public void addTaxon(String identifier,LangString entry){
		add(new LOMClassificationTaxon(identifier,entry));
	}

	public void addTaxon(String identifier,LangString entry,int index){
		if (index ==0){
			add(new LOMClassificationTaxon(identifier,entry));
		}else {
		    delete(index-1);
		    add(new LOMClassificationTaxon(identifier,entry),index-1);

		}
	}
	
	
	public String[] elements(){
		String[] elements= new String[container.size()];
		for (int i=0; i<container.size();i++)
			elements[i] = ((LOMClassificationTaxonPath)container.get(i)).getSource().getValue(0);
		return elements;
	}

	@Override
	public String getTitle() {
		return TextConstants.getText( "LOMES.ClassificationTaxonPath.DialogTitle" );
	}

	@Override
	public String[] attributes() {
		LOMESGeneralId generalId = new LOMESGeneralId();
		return generalId.attributes();
	}
	
	
}