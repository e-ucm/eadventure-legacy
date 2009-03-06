package es.eucm.eadventure.editor.data.meta.lomes;


import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMESTechnical {

	//4.4.1.1
	private Vocabulary type;
	
	//4.4.1.2
	private Vocabulary name;
	
	//4.4.1.3
	private String minimumVersion;
	
	//4.4.1.4
	private String maximumVersion;
	

	
	public LOMESTechnical (){
		type = new Vocabulary(Vocabulary.TE_TYPE_4_4_1_1);
		name = new Vocabulary(Vocabulary.TE_NAME_4_4_1_2);
		minimumVersion = null;
		maximumVersion = null;
	}

	/**
	 * @return the minimumVersion
	 */
	public String getMinimumVersion( ) {
		return minimumVersion;
	}

	/**
	 * @param minimumVersion the minimumVersion to set
	 */
	public void setMinimumVersion( String minimumVersion ) {
		this.minimumVersion = minimumVersion;
	}

	/**
	 * @return the maximumVersion
	 */
	public String getMaximumVersion( ) {
		return maximumVersion;
	}

	/**
	 * @param maximumVersion the maximumVersion to set
	 */
	public void setMaximumVersion( String maximumVersion ) {
		this.maximumVersion = maximumVersion;
	}

	/**
	 * @return the type
	 */
	public Vocabulary getType() {
		return type;
	}


	public void setType(int index) {
		this.type = new Vocabulary(Vocabulary.TE_TYPE_4_4_1_1,index);
	}

	/**
	 * @return the name
	 */
	public Vocabulary getName() {
		return name;
	}

	public void setName(int index) {
		this.name = new Vocabulary(Vocabulary.TE_NAME_4_4_1_2,index);
	}
	
	
	
	
}
