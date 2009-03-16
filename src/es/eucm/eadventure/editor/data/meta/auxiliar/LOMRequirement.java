package es.eucm.eadventure.editor.data.meta.auxiliar;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMRequirement extends LOMESContainer{
    

   
        public LOMRequirement(){
        	super();
        }
        
        public LOMRequirement(Vocabulary type,Vocabulary name,String minimumVersion,String maximumVersion){
        	this();
        	add(new LOMOrComposite(type,name,minimumVersion,maximumVersion));
        }
        
        public void addOrComposite(Vocabulary type,Vocabulary name,String minimumVersion,String maximumVersion){
        	add(new LOMOrComposite(type,name,minimumVersion,maximumVersion));
        }
        
        public void addOrComposite(Vocabulary type,Vocabulary name,String minimumVersion,String maximumVersion,int index){
        	if (index ==0){
        		add(new LOMOrComposite(type,name,minimumVersion,maximumVersion));
        	}else {
        	    delete(index-1);
        	    add(new LOMOrComposite(type,name,minimumVersion,maximumVersion),index-1);
        
        	}
        }
        
        
        public String[] elements(){
        	String[] elements= new String[container.size()];
        	for (int i=0; i<container.size();i++)
        		elements[i] = ((LOMOrComposite)container.get(i)).getType().getValue();
        	return elements;
        }
        
        @Override
        public String getTitle() {
        	return TextConstants.getText( "LOMES.GeneralIdentifier.DialogTitle" );
        }
        
        @Override
        public String[] attributes() {
        	// TODO Auto-generated method stub
        	return null;
        }
        

}