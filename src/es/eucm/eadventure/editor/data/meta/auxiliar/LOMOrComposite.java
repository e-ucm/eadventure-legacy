package es.eucm.eadventure.editor.data.meta.auxiliar;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMOrComposite implements LOMESComposeType{

    
    
    	private static final int numberTypeValues=2;
    	
    	private static final int numberNameValues=13;

    	//4.4.1.1
	private Vocabulary type;
	
	//4.4.1.2
	private Vocabulary name;
	
	//4.4.1.3
	private String minimumVersion;
	
	//4.4.1.4
	private String maximumVersion;
	

	
	public LOMOrComposite(){
	    	type = new Vocabulary(Vocabulary.TE_TYPE_4_4_1_1, Vocabulary.LOM_ES_SOURCE, 0);
		name = new Vocabulary(Vocabulary.TE_NAME_4_4_1_2, Vocabulary.LOM_ES_SOURCE, 0);
		this.minimumVersion = Controller.getInstance().getEditorMinVersion();
		this.maximumVersion = Controller.getInstance().getEditorVersion();
	}
	
	public LOMOrComposite(Vocabulary type,Vocabulary name,String minimumVersion,String maximumVersion){
	    	
	    	this.type = type;
		this.name = name;
		this.minimumVersion = minimumVersion;
		this.maximumVersion = maximumVersion;
	}

	
	
	public static String[] getTypeOptions( ) {
		String[] options = new String[numberTypeValues];
		for (int i=0; i<options.length; i++){
			options[i]=TextConstants.getText( "LOMES.Technical.Type"+i );
		}
		return options;
	}
	
	public static String[] getNameOptions( ) {
		String[] options = new String[numberNameValues];
		for (int i=0; i<options.length; i++){
			options[i]=TextConstants.getText( "LOMES.Technical.Name"+i );
		}
		return options;
	}
    
    public String getTitle() {
		// TODO Auto-generated method stub
		return null;
    }

    /**
     * @return the type
     */
    public Vocabulary getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Vocabulary type) {
        this.type = type;
    }

    /**
     * @return the name
     */
    public Vocabulary getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(Vocabulary name) {
        this.name = name;
    }

    /**
     * @return the minimumVersion
     */
    public String getMinimumVersion() {
        return minimumVersion;
    }

    /**
     * @param minimumVersion the minimumVersion to set
     */
    public void setMinimumVersion(String minimumVersion) {
        this.minimumVersion = minimumVersion;
    }

    /**
     * @return the maximumVersion
     */
    public String getMaximumVersion() {
        return maximumVersion;
    }

    /**
     * @param maximumVersion the maximumVersion to set
     */
    public void setMaximumVersion(String maximumVersion) {
        this.maximumVersion = maximumVersion;
    }

}
