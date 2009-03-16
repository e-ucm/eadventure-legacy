package es.eucm.eadventure.editor.data.meta.lomes;


import es.eucm.eadventure.editor.data.meta.Vocabulary;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMRequirement;

public class LOMESTechnical {

    	// requirements has orComposite elements
	private LOMRequirement requirement;
	
	public LOMESTechnical (){
	    requirement = new LOMRequirement();
	    
	}

	public void addOrComposite(Vocabulary type,Vocabulary name,String minimumVersion,String maximumVersion){
	    requirement.addOrComposite(type, name, minimumVersion, maximumVersion);
	}
	
	public void setRequirement(LOMRequirement requirement){
	    this.requirement = requirement;
	}

	/**
	 * @return the requirement
	 */
	public LOMRequirement getRequirement() {
	    return requirement;
	}
	
	
	
	
	
}
