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

import es.eucm.eadventure.common.data.chapter.Action;

/**
 * This class holds the data of an item in eAdventure
 */
public class Item extends Element {

    /**
     * The tag of the item's image
     */
    public static final String RESOURCE_TYPE_IMAGE = "image";

    /**
     * The tag of the item's icon
     */
    public static final String RESOURCE_TYPE_ICON = "icon";
    
    
    /**
     * The tag of the item's over image (added v1.4)
     */
    public static final String RESOURCE_TYPE_IMAGEOVER = "imageover";

    /**
     * Behaviour: Added in 1.4. An item now can behave as:
     *      NORMAL: reacts to mouse overs by changing mouse cursor and text. Displays action buttons on click
     *      FIRST_ACTION: devised to simulate buttons. On click, triggers the first valid action defined (no buttons are displayed)
     *      ATREZZO: reacts to nothing. Useful to simulate atrezzos. NOTE: THIS OPTION WILL NOT BE PRESENT IN PUBLIC RELEASE
     * @author Javier Torrente
     *
     */
    public enum BehaviourType {
        NORMAL, FIRST_ACTION, ATREZZO;
    }


    /**
     * Item's Behaviour (see {@link BehaviourTYpe}
     */
    private BehaviourType behaviour;
    
    /**
     * Feature added to release v1.4. When an item's appearance changes, it is possible to define a fade-in-out.
     * Very useful if combined with behaviours. Allows full implementation of buttons.
     */
    private long resourcesTransitionTime=0; //By default transitions are instantaneous

    
    /**
     * Creates a new Item
     * 
     * @param id
     *            the id of the item
     */
    public Item( String id ) {

        super( id );
        this.behaviour=BehaviourType.NORMAL;
    }

    /*
     *  (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString( ) {

        StringBuffer sb = new StringBuffer( 40 );

        sb.append( "\n" );
        sb.append( super.toString( ) );
        for( Action action : actions )
            sb.append( action.toString( ) );

        sb.append( "\n" );

        return sb.toString( );
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Item i = (Item) super.clone( );
        i.setBehaviour( this.behaviour );
        i.setResourcesTransitionTime( this.resourcesTransitionTime );
        return i;
    }
    
    /**
     * Returns this item's behaviour. 
     * @return Behaviour
     * see {@link BehaviourType for more info}
     */
    public BehaviourType getBehaviour( ) {
        return behaviour;
    }
    //For tools
    public Integer getBehaviourInteger( ) {
        return behaviour.ordinal( );
    }
    
    public void setBehaviour( BehaviourType behaviour ) {
    
        this.behaviour = behaviour;
    }
    
    // For tools
    public void setBehaviourInteger( Integer behaviour ) {
        if (behaviour.intValue( ) == BehaviourType.ATREZZO.ordinal( )){
            this.behaviour = BehaviourType.ATREZZO;
        }
        else if (behaviour.intValue( ) == BehaviourType.NORMAL.ordinal( )){
            this.behaviour = BehaviourType.NORMAL;
        }
        else if (behaviour.intValue( ) == BehaviourType.FIRST_ACTION.ordinal( )){
            this.behaviour = BehaviourType.FIRST_ACTION;
        }
    }
    
    /**
     * Returns the transition time between changes of appearances. 
     * If it's 0, new appearance replaces the old one instantly.
     * If it's>0, a fade-in-out is performed to render this item for X miliseconds.
     * @return  X miliseconds for transition between changes of appearances.
     */
    //Long is used for tools
    public Long getResourcesTransitionTime( ) {
    
        return resourcesTransitionTime;
    }
    //Long is used for tools
    public void setResourcesTransitionTime( Long resourcesTransitionTime ) {
    
        this.resourcesTransitionTime = resourcesTransitionTime;
    }

}
