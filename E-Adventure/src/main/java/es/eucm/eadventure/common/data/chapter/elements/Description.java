/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.common.data.chapter.elements;


import es.eucm.eadventure.common.auxiliar.AllElementsWithAssets;
import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.common.data.Detailed;
import es.eucm.eadventure.common.data.HasDescriptionSound;
import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;

/**
 * 
 * Class to store single description for an element
 * 
 * @author 
 *
 */
public class Description implements Named, Described, Detailed, HasDescriptionSound, Cloneable{
    
    /**
     * Condition
     */
    private Conditions conditions;
    
    /**
     * The element's name
     */
    private String name;
    
    /**
     * The path of the sound associated to element's name
     */
    private String nameSoundPath;

    /**
     * The element's brief description
     */
    private String description;

    /**
     * The path of the sound associated to element's description
     */
    private String descriptionSoundPath;

    /**
     * The element's detailed description
     */
    private String detailedDescription;
    
    /**
     * The path of the sound associated to element's detailed description
     */
    private String detailedDescriptionSoundPath;

    
    public Description(){
        this.name = "";
        this.description = "";
        this.detailedDescription = "";
    }
    
    
    
    /**
     * Returns the element's name
     * 
     * @return the element's name
     */
    public String getName( ) {

        return name;
    }

    /**
     * Returns the element's brief description
     * 
     * @return the element's brief description
     */
    public String getDescription( ) {

        return description;
    }

    /**
     * Returns the element's detailed description
     * 
     * @return the element's detailed description
     */
    public String getDetailedDescription( ) {

        return detailedDescription;
    }
    
    /**
     * Changes the element's name
     * 
     * @param name
     *            the new name
     */
    public void setName( String name ) {

        this.name = name;
    }

    /**
     * Changes the element's brief description
     * 
     * @param description
     *            the new brief description
     */
    public void setDescription( String description ) {

        this.description = description;
    }

    /**
     * Changes the element's detailed description
     * 
     * @param detailedDescription
     *            the new detailed description
     */
    public void setDetailedDescription( String detailedDescription ) {

        this.detailedDescription = detailedDescription;
    }
    
    public String getNameSoundPath( ) {
        
        return nameSoundPath;
    }

    
    public void setNameSoundPath( String nameSoundPath ) {
    
        this.nameSoundPath = nameSoundPath;
        if (nameSoundPath!=null){
            AllElementsWithAssets.addAsset( this );
        }
    }

    
    public String getDescriptionSoundPath( ) {
    
        return descriptionSoundPath;
    }

    
    public void setDescriptionSoundPath( String descriptionSoundPath ) {
    
        this.descriptionSoundPath = descriptionSoundPath;
        if (descriptionSoundPath!=null){
            AllElementsWithAssets.addAsset( this );
        }

    }

    
    public String getDetailedDescriptionSoundPath( ) {
    
        return detailedDescriptionSoundPath;
    }

    
    public void setDetailedDescriptionSoundPath( String detailedDescriptionSoundPath ) {
    
        this.detailedDescriptionSoundPath = detailedDescriptionSoundPath;
        if (detailedDescriptionSoundPath!=null){
            AllElementsWithAssets.addAsset( this );
        }

    }
    
       
    public Conditions getConditions( ) {
    
        return conditions;
    }

    public void setConditions( Conditions conditions ) {
    
        this.conditions = conditions;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {
        
        Description d = (Description) super.clone( );
        
        d.conditions = ( conditions != null ? (Conditions) conditions.clone( ) : null );
        d.name = ( name != null ? new String( name ) : null );
        d.nameSoundPath = ( nameSoundPath != null ? new String( nameSoundPath ) : null );
        d.description = ( description != null ? new String( description ) : null );
        d.descriptionSoundPath = ( descriptionSoundPath != null ? new String( descriptionSoundPath ) : null );
        d.detailedDescription = ( detailedDescription != null ? new String( detailedDescription ) : null );
        d.detailedDescriptionSoundPath = ( detailedDescriptionSoundPath != null ? new String( detailedDescriptionSoundPath ) : null );
        
        return d;
    }

    

}
