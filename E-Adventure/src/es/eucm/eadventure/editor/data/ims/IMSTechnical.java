package es.eucm.eadventure.editor.data.meta.ims;


import java.util.ArrayList;



public class IMSTechnical {

	//4.1
	private ArrayList<String> format;
	
	//4.3 Each location begins with a number which identifies the type attribute:
	//    0- URI
	//	  1- TEXT
	private ArrayList<String> location;
	
	
	//4.4.1.3
	private String minimumVersion;
	
	//4.4.1.4
	private String maximumVersion;

	
	public IMSTechnical (){
		format = new ArrayList<String>();
		location = new ArrayList<String>();
		minimumVersion = null;
		maximumVersion = null;
	}

	public void setFormat(String format){
		this.format.add(format);
		
	}
	
	public String getFormat(int i){
		return format.get(i);
	}
	
	/**
	 * 
	 * Add new location
	 * 
	 * @param location
	 * 				The String with the location
	 * @param URI
	 * 			Indicate if is URI or TEXT
	 */
	public void setLocation(String location, boolean URI){
		this.location.add(URI?"0 ":"1 "+location);
	}
	
	/**
	 * Returns the specified location
	 * 
	 * @param i
	 * @return
	 */
	public String getLocation(int i){
		return this.location.get(i);
	}
	
	/**
	 * Returns the first location
	 * @return
	 */
	public String getLocation(){
		return location.get(0);
	}
	
	/**
	 * Returns the first format
	 * 
	 * @return
	 */
	public String getFormat(){
		return format.get(0);
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
	
	
	
}
