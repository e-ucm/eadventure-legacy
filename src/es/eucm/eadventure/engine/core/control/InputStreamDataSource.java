/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fern‡ndez-Manj—n, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, availabe at http://e-adventure.e-ucm.es.
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
 */
package es.eucm.eadventure.engine.core.control;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.media.Duration;
import javax.media.MediaLocator;
import javax.media.Time;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.PullDataSource;
import javax.media.protocol.PullSourceStream;
import javax.media.protocol.Seekable;
import javax.media.protocol.SourceCloneable;

import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

public class InputStreamDataSource extends PullDataSource implements SourceCloneable {

    private PullSourceStream[] pullSourceStreams;

    private String resource;

    public InputStreamDataSource( String resource ) {
        this.resource = resource;
        this.pullSourceStreams = new PullSourceStream[ 1 ];
        pullSourceStreams[0] = new InputStreamPullSourceStream( resource );
    }

    @Override
    public PullSourceStream[] getStreams( ) {
        System.out.println( "getStreams" );
        return pullSourceStreams;
    }

    @Override
    public void connect( ) throws IOException {

    }

    @Override
    public void disconnect( ) {

    }

    @Override
    public String getContentType( ) {
        return pullSourceStreams[0].getContentDescriptor( ).getContentType( );
    }

    @Override
    public Object getControl( String arg0 ) {

        return null;
    }

    @Override
    public Object[] getControls( ) {

        return new Object[ 0 ];
    }

    @Override
    public Time getDuration( ) {
        return Duration.DURATION_UNKNOWN;
    }

    @Override
    public void start( ) throws IOException {
    }

    @Override
    public void stop( ) throws IOException {
    }

    public javax.media.protocol.DataSource createClone( ) {
        InputStreamDataSource ds = new InputStreamDataSource( resource );
        MediaLocator ml = this.getLocator( );
        ds.setLocator( ml );
        return ds;
    }

    private class InputStreamPullSourceStream implements PullSourceStream, Seekable {

        private static final int BUFFER_SIZE = 100000;

        private byte[] buffer = new byte[ BUFFER_SIZE ];

        private int inputStreamOffset = 0;

        private int bufferFill = 0;

        private BufferedInputStream is;

        private String resource;

        private boolean end = false;

        private long currentPosition = 0;
        
        private String type = null;

        public InputStreamPullSourceStream( String resource2 ) {
            this.resource = resource2;
            String ext = ResourceHandler.getExtension(resource);
            ext = ext.toLowerCase( );
            setTypeFromExtension(ext);
            is = new BufferedInputStream( ResourceHandler.getInstance( ).getResourceAsStreamFromZip( resource ) );
        }

        /**
         * Mime types extracted from: http://www.w3schools.com/media/media_mimeref.asp
         * 
         * @param ext
         */
        private void setTypeFromExtension( String ext ) {
            if (ext.equals( "mp2" ) || ext.equals( "mpa" ) || ext.equals( "mpe" ) || ext.equals("mpeg") || ext.equals( "mpg" ) || ext.equals("mpv2")) {
                type = "video.mpeg";
            } 
            if (ext.equals( "mov") || ext.equals("qt")) {
                type = "video.quicktime";
            } 
            if (ext.equals( "lsf") || ext.equals("lsx")) {
                type = "video.x_la_asf";
            }
            if (ext.equals( "asf") || ext.equals("asr") || ext.equals( "asx" )) {
                type = "video.x_ms_asf";
            }
            if (ext.equals( "avi" )) {
                type = "video.x_msvideo";
            } 
            if (ext.equals( "movie" )) {
                type = "video.x-sgi-movie";
            } 
        }

        public int read( byte[] arg0, int arg1, int arg2 ) throws IOException {

            if( bufferFill == 0 ) {
                fillBuffer( );
            }
            if( bufferFill == -1 )
                return -1;
            else {
                if( currentPosition < inputStreamOffset ) {
                    is = new BufferedInputStream( ResourceHandler.getInstance( ).getResourceAsStreamFromZip( resource ) );
                    bufferFill = 0;
                    inputStreamOffset = 0;
                    end = false;

                    try {
                        while( currentPosition > inputStreamOffset + bufferFill && bufferFill != -1 ) {
                            fillBuffer( );
                        }

                        if( bufferFill == -1 ) {
                            currentPosition = Math.min( inputStreamOffset + bufferFill, currentPosition );
                        }

                    }
                    catch( Exception e ) {
                        return -1;
                    }

                }
            }

            int available = (int) Math.min( arg2, inputStreamOffset + bufferFill - currentPosition );
            System.arraycopy( buffer, (int) ( currentPosition - inputStreamOffset ), arg0, arg1, available );
            currentPosition += available;

            if( currentPosition == inputStreamOffset + bufferFill )
                fillBuffer( );

            if( available == arg2 ) {
                return arg2;
            }

            int temp = read( arg0, arg1 + available, arg2 - available );
            if( temp != -1 )
                return temp + available;
            return available;

        }

        public boolean willReadBlock( ) {

            return bufferFill != BUFFER_SIZE;
        }

        public boolean endOfStream( ) {

            return end;
        }

        public ContentDescriptor getContentDescriptor( ) {
            return new ContentDescriptor( ContentDescriptor.mimeTypeToPackageName( type ));
        }

        public long getContentLength( ) {
            //return 1278622;
            return -1;
        }

        public Object getControl( String arg0 ) {

            return null;
        }

        public Object[] getControls( ) {

            return new Object[ 0 ];
        }

        public boolean isRandomAccess( ) {

            return true;
        }

        public long seek( long arg0 ) {

            if( arg0 < this.inputStreamOffset ) {
                is = new BufferedInputStream( ResourceHandler.getInstance( ).getResourceAsStreamFromZip( resource ) );
                bufferFill = 0;
                inputStreamOffset = 0;
                end = false;
                currentPosition = 0;
            }
            try {

                while( arg0 > inputStreamOffset + bufferFill && bufferFill != -1 ) {
                    fillBuffer( );
                }

                if( bufferFill == -1 ) {
                    currentPosition = Math.min( inputStreamOffset + bufferFill, arg0 );
                    return currentPosition;
                }
                else {
                    currentPosition = arg0;
                    return arg0;
                }

            }
            catch( Exception e ) {
                return -1;
            }
        }

        public long tell( ) {

            return currentPosition;
        }

        public void fillBuffer( ) throws IOException {

            inputStreamOffset = inputStreamOffset + bufferFill;
            bufferFill = 0;
            int temp = 0;
            byte[] tempBuffer = new byte[ BUFFER_SIZE ];

            while( bufferFill < BUFFER_SIZE && temp != -1 ) {
                temp = is.read( tempBuffer, 0, BUFFER_SIZE );

                if( temp == -1 && bufferFill == 0 )
                    bufferFill = -1;

                if( temp != -1 ) {
                    System.arraycopy( tempBuffer, 0, buffer, bufferFill, temp );
                    bufferFill += temp;
                }
            }
        }

    }
}