package es.eucm.eadventure.editor.data.meta.auxiliar;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMTaxonPath extends LOMESContainer{

	
	public LOMTaxonPath(){
		super();
	}
	
	public LOMTaxonPath(LangString source,LOMTaxon taxon){
		this();
		add(new LOMClassificationTaxonPath(source,taxon));
	}
	
	public void addTaxonPath(LangString source,LOMTaxon taxon){
		add(new LOMClassificationTaxonPath(source,taxon));
	}

	public void addTaxonPath(LangString source,LOMTaxon taxon,int index){
		if (index ==0){
			add(new LOMClassificationTaxonPath(source,taxon));
		}else {
		    delete(index-1);
		    add(new LOMClassificationTaxonPath(source,taxon),index-1);

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

