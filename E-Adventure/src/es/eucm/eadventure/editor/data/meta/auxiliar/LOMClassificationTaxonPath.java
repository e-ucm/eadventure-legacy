package es.eucm.eadventure.editor.data.meta.auxiliar;

import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMClassificationTaxonPath implements LOMESComposeType{

    	//9.2.1
	private LangString source;
	//9.2.2
	private LOMTaxon taxon;
	
	public LOMClassificationTaxonPath(){
	    source = new LangString("");
	    taxon = new LOMTaxon();
	}
	
	public LOMClassificationTaxonPath(LangString source,LOMTaxon taxon){
	    this.source = source ;
	    this.taxon=taxon;
	}

	/**
	 * @return the source
	 */
	public LangString getSource() {
	    return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(LangString source) {
	    this.source = source;
	}

	/**
	 * @return the taxon
	 */
	public LOMTaxon getTaxon() {
	    return taxon;
	}

	/**
	 * @param taxon the taxon to set
	 */
	public void setTaxon(LOMTaxon taxon) {
	    this.taxon = taxon;
	}

	@Override
	public String getTitle() {
	    // TODO Auto-generated method stub
	    return null;
	}
	
	
    
}
