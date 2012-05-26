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
package es.eucm.eadventure.common.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Transition;

public class TransitionSubParser extends SubParser {

    private Animation animation;

    private Transition transition;

    public TransitionSubParser( Animation animation ) {
        super(null);
        this.animation = animation;
        transition = new Transition( );
    }

    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

        if( qName.equals( "transition" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "type" ) ) {
                    if( attrs.getValue( i ).equals( "none" ) )
                        transition.setType( Transition.TYPE_NONE );
                    else if( attrs.getValue( i ).equals( "fadein" ) )
                        transition.setType( Transition.TYPE_FADEIN );
                    else if( attrs.getValue( i ).equals( "vertical" ) )
                        transition.setType( Transition.TYPE_VERTICAL );
                    else if( attrs.getValue( i ).equals( "horizontal" ) )
                        transition.setType( Transition.TYPE_HORIZONTAL );
                }
                else if( attrs.getQName( i ).equals( "time" ) ) {
                    transition.setTime( Long.parseLong( attrs.getValue( i ) ) );
                }
            }
        }
    }

    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        if( qName.equals( "transition" ) ) {
            animation.getTransitions( ).add( transition );
        }
    }
    
}
