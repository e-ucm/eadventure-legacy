/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.common.loader.subparsers;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

public class FrameSubParser extends DefaultHandler {

    private Animation animation;

    private Frame frame;

    private Resources currentResources;

    public FrameSubParser( Animation animation ) {

        this.animation = animation;
        frame = new Frame( animation.getImageLoaderFactory( ) );
    }

    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

        if( qName.equals( "frame" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "uri" ) )
                    frame.setUri( attrs.getValue( i ) );
                if( attrs.getQName( i ).equals( "type" ) ) {
                    if( attrs.getValue( i ).equals( "image" ) )
                        frame.setType( Frame.TYPE_IMAGE );
                    if( attrs.getValue( i ).equals( "video" ) )
                        frame.setType( Frame.TYPE_VIDEO );
                }
                if( attrs.getQName( i ).equals( "time" ) ) {
                    frame.setTime( Long.parseLong( attrs.getValue( i ) ) );
                }
                if( attrs.getQName( i ).equals( "waitforclick" ) )
                    frame.setWaitforclick( attrs.getValue( i ).equals( "yes" ) );
                if( attrs.getQName( i ).equals( "soundUri" ) )
                    frame.setSoundUri( attrs.getValue( i ) );
                if( attrs.getQName( i ).equals( "maxSoundTime" ) )
                    frame.setMaxSoundTime( Integer.parseInt( attrs.getValue( i ) ) );
            }
        }

        if( qName.equals( "resources" ) ) {
            currentResources = new Resources( );
            
            for (int i = 0; i < attrs.getLength( ); i++) {
                if (attrs.getQName( i ).equals( "name" ))
                    currentResources.setName( attrs.getValue( i ) );
            }

        }

        if( qName.equals( "asset" ) ) {
            String type = "";
            String path = "";

            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "type" ) )
                    type = attrs.getValue( i );
                if( attrs.getQName( i ).equals( "uri" ) )
                    path = attrs.getValue( i );
            }

            // If the asset is not an special one
            //if( !AssetsController.isAssetSpecial( path ) )
            currentResources.addAsset( type, path );
        }
    }

    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        if( qName.equals( "frame" ) ) {
            animation.getFrames( ).add( frame );
        }

        if( qName.equals( "resources" ) ) {
            frame.addResources( currentResources );
        }
    }

}
