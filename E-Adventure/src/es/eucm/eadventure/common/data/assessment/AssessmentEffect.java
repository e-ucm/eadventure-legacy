package es.eucm.eadventure.common.data.assessment;

import java.util.ArrayList;
import java.util.List;

public class AssessmentEffect implements Cloneable {

	/**
     * Text of the effect of the rule, if present (null if not)
     */   
    protected String text;
    
    /**
     * List of properties to be set
     */
    protected List<AssessmentProperty> properties;
    
    public AssessmentEffect (){
    	text = null;
        properties = new ArrayList<AssessmentProperty>( );
    }
    
    /**
     * Sets the text of the rule
     * @param text Text of the rule
     */
    public void setText( String text ) {
        this.text = text;
    }
    
    /**
     * Adds a new assessment property
     * @param property Assessment property to be added
     */
    public void addProperty( AssessmentProperty property ) {
        properties.add( property );
    }
    
    /**
     * Returns the rule's text (if present)
     * @return Text of the rule if present, null otherwise
     */
    public String getText( ) {
        return text;
    }


    public List<AssessmentProperty> getAssessmentProperties( ) {
        return properties;
    }
    
	public Object clone() throws CloneNotSupportedException {
		AssessmentEffect ae = (AssessmentEffect) super.clone();
		if (properties != null) {
			ae.properties = new ArrayList<AssessmentProperty>();
			for (AssessmentProperty ap : properties)
				ae.properties.add((AssessmentProperty) ap.clone());
		}
		ae.text = (text != null ? new String(text) : null);
		return ae;
	
	}
    
}
