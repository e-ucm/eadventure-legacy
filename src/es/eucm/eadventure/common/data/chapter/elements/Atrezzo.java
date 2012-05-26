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

import es.eucm.eadventure.common.data.HasDescriptionSound;
import es.eucm.eadventure.common.data.Named;

/**
 * This class holds the data of an item in eAdventure
 */
public class Atrezzo extends Element implements Named, HasDescriptionSound {

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
        descriptions.add( new Description()  );

    }

 
    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Atrezzo a = (Atrezzo) super.clone( );
        return a;
    }


    public String getName( ) {

        return this.descriptions.get( 0 ).getName( );
        
    }


    public void setName( String name ) {

       this.descriptions.get( 0 ).setName( name );
        
    }


    public String getDescriptionSoundPath( ) {

        // TODO Auto-generated method stub
        return null;
    }


    public String getDetailedDescriptionSoundPath( ) {

        // TODO Auto-generated method stub
        return null;
    }


    public String getNameSoundPath( ) {

        return this.descriptions.get( 0 ).getNameSoundPath( );
    }


    public void setDescriptionSoundPath( String descriptionSoundPath ) {

       
        
    }


    public void setDetailedDescriptionSoundPath( String detailedDescriptionSoundPath ) {

        // TODO Auto-generated method stub
        
    }


    public void setNameSoundPath( String nameSoundPath ) {

        this.descriptions.get( 0 ).setNameSoundPath( nameSoundPath );
        
    }


}
