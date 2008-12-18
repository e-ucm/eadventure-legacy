package es.eucm.eadventure.common.data.chapter.elements;

import java.util.ArrayList;

import es.eucm.eadventure.common.data.chapter.Action;


/**
 * This class holds the data of an item in eAdventure
 */
public class Atrezzo extends Element{

	
	/**
     * The tag of the item's image
     */
    public static final String RESOURCE_TYPE_IMAGE = "image";
	
	/**
	 * Creates a new Atrezzo item
	 * 
	 * @param id
	 *            the id of the atrezzo item
	 */
	public Atrezzo( String id ) {
		super( id );
		
	}
	
	/**
	 * Convenient constructor for ActiveAreas 
	 */
	public Atrezzo( String id, String name, String description, String detailedDescription ){
		this (id);
		this.name = name;
		this.description = description;
		this.detailedDescription = detailedDescription;
	}
	
}
