/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.common.data.chapter.elements;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.HasId;
import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

/**
 * This class holds the common data for any element in eAdventure. Here, element
 * means item or character
 */
public abstract class Element implements Cloneable, HasId, Documented {

    /**
     * The element's id
     */
    protected String id;

    /**
     * Documentation of the element.
     */
    private String documentation;
    
    /**
     * List of descriptions
     */
    protected List<Description> descriptions;
    
    /**
     * The element's set of resources
     */
    private List<Resources> resources;

    /**
     * List of actions associated with the item
     */
    protected List<Action> actions;

    /**
     * If true, the element returns to its last position when dragged
     */
    private boolean returnsWhenDragged;
    
    /**
     * Creates a new element
     * 
     * @param id
     *            the element's id
     */
    public Element( String id ) {

        this.id = id;
        
        this.returnsWhenDragged = true;
        resources = new ArrayList<Resources>( );
        actions = new ArrayList<Action>( );
        descriptions = new ArrayList<Description>();
       // descriptions.add( new Description() );
    }

    /**
     * Returns the element's id
     * 
     * @return the element's id
     */
    public String getId( ) {

        return id;
    }

    /**
     * Returns the documentation of the element.
     * 
     * @return the documentation of the element
     */
    public String getDocumentation( ) {

        return documentation;
    }

    
    /**
     * Returns the element's list of resources
     * 
     * @return the element's list of resources
     */
    public List<Resources> getResources( ) {

        return resources;
    }

    /**
     * Sets the a new identifier for the element.
     * 
     * @param id
     *            New identifier
     */
    public void setId( String id ) {

        this.id = id;
    }

    /**
     * Changes the documentation of this element.
     * 
     * @param documentation
     *            The new documentation
     */
    public void setDocumentation( String documentation ) {

        this.documentation = documentation;
    }

   

    /**
     * Adds some resources to the list of resources
     * 
     * @param resources
     *            the resources to add
     */
    public void addResources( Resources resources ) {

        this.resources.add( resources );
    }

    /**
     * Adds an action to this item
     * 
     * @param action
     *            the action to add
     */
    public void addAction( Action action ) {

        actions.add( action );
    }

    /**
     * Returns the list of actions of the item
     * 
     * @return the list of actions of the item
     */
    public List<Action> getActions( ) {

        return actions;
    }

    /**
     * Returns the size of the list of actions
     * 
     * @return Size (int) of the list of actions
     */
    public int getActionsCount( ) {

        if( actions == null )
            return 0;
        else
            return actions.size( );
    }

    /**
     * Returns Action object at place i
     * 
     * @param i
     * @return
     */
    public Action getAction( int i ) {

        return actions.get( i );
    }

    /**
     * Changes the list of actions of the item
     * 
     * @param actions
     *            the new list of actions
     */
    public void setActions( ArrayList<Action> actions ) {

        this.actions = actions;
    }

    
    /**
     * Changes the returns when dragged value
     * 
     * @param returnsWhenDragged
     *              the new value
     */
    public void setReturnsWhenDragged(boolean returnsWhenDragged) {
        this.returnsWhenDragged = returnsWhenDragged;
    }
    
    /**
     * Returns if the element must return when dragged
     * 
     * @return true if the element must return when dragged
     */
    public boolean isReturnsWhenDragged() {
        return returnsWhenDragged;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString( ) {

        StringBuffer sb = new StringBuffer( 40 );

        //TODO ver que pasa ahora con  este toString!!!!
        sb.append( "Name: " );
        //sb.append( name );

        sb.append( "\nDescription: " );
        //sb.append( description );

        sb.append( "\nDetailed description:" );
        //sb.append( detailedDescription );

        sb.append( "\n" );

        return sb.toString( );
    }
    
    //TODO confirmar que se quita
    /*public void setName( String name, int index ){
        descriptions.get( index ).setName( name );
    }
    
    public String getName( int index ){
        return descriptions.get( index ).getName( );
    }
    
    public String getDescription( int index ){
        return descriptions.get( index ).getDescription( );
    }

    public void setDescription( String description, int index ){
        descriptions.get( index ).setDescription( description );
    }
    
    public void setDetailedDescription( String detailedDescription, int index ){
        descriptions.get( index ).setDetailedDescription( detailedDescription );
    }
    
    public String getDetailedDescription( int index ){
        return descriptions.get( index ).getDetailedDescription( );
    }
    
    public void setDescriptionSoundPath(String descriptionSoundPath, int index){
        descriptions.get( index ).setDescriptionSoundPath( descriptionSoundPath );
    }
    
    public String getDescriptionSoundPath(int index){
        return descriptions.get( index ).getDescriptionSoundPath( );
    }
    
    public String getDetailedDescriptionSoundPath(int index){
        return descriptions.get( index ).getDetailedDescriptionSoundPath( );
    }
    
    public void setDetailedDescriptionSoundPath( String detailedDescriptionSoundPath, int index ){
        descriptions.get( index ).setDetailedDescriptionSoundPath( detailedDescriptionSoundPath );
    }
    
    public String getNameSoundPath(int index){
        return descriptions.get( index ).getNameSoundPath( );
    }
    
    public void setNameSoundPath( String nameSoundPath, int index ){
        descriptions.get( index ).setNameSoundPath( nameSoundPath );
    }*/
    
    public List<Description> getDescriptions( ) {
        
        return descriptions;
    }
    
    public Description getDescription(int index){
        
        return descriptions.get( index );
        
    }

    
    public void setDescriptions( List<Description> descriptions ) {
    
        this.descriptions = descriptions;
    }

    
    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Element e = (Element) super.clone( );
        if( actions != null ) {
            e.actions = new ArrayList<Action>( );
            for( Action action : actions ) {
                e.actions.add( (Action) action.clone( ) );
            }
        }
        
        e.documentation = ( documentation != null ? new String( documentation ) : null );
        e.id = ( id != null ? new String( id ) : null );
        
        e.returnsWhenDragged = returnsWhenDragged;
        if( resources != null ) {
            e.resources = new ArrayList<Resources>( );
            for( Resources r : resources ) {
                e.resources.add( (Resources) r.clone( ) );
            }
        }
        return e;
    }

}
