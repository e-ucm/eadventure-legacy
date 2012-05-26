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
package es.eucm.eadventure.common.data.chapter.scenes;

/**
 * This class holds the data of a slidescene in eAdventure
 */
public class Slidescene extends Cutscene {

    /**
     * The tag for the slides
     */
    public static final String RESOURCE_TYPE_SLIDES = "slides";

    /**
     * The tag for the background music
     */
    public static final String RESOURCE_TYPE_MUSIC = "bgmusic";

    /**
     * Creates a new Slidescene
     * 
     * @param id
     *            the id of the slidescene
     */
    public Slidescene( String id ) {

        super( GeneralScene.SLIDESCENE, id );
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Slidescene s = (Slidescene) super.clone( );
        return s;
    }
}
