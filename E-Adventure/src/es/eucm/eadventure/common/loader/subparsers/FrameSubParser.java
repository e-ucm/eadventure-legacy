/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.common.loader.subparsers;

import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.loader.InputStreamCreator;

public class FrameSubParser extends DefaultHandler {

    private Animation animation;

    private Frame frame;

    private Resources currentResources;
    
    /**
     * InputStreamCreator used in resolveEntity to find dtds (only required in
     * Applet mode)
     */
    private InputStreamCreator isCreator;

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
    
    /*
     *  (non-Javadoc)
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    @Override
    public InputSource resolveEntity( String publicId, String systemId ) {
        int startFilename = systemId.lastIndexOf( "/" ) + 1;
        String filename = systemId.substring( startFilename, systemId.length( ) );
        InputStream inputStream = isCreator.buildInputStream( filename );
        return new InputSource( inputStream );
    }

}
