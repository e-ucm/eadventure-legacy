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
package es.eucm.eadventure.engine.resourcehandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.media.MediaLocator;

import es.eucm.eadventure.common.loader.InputStreamCreator;

public class ResourceHandlerExternalSource extends ResourceHandler {

    private InputStreamCreator isCreator;

    private static ResourceHandlerExternalSource instance;

    public static ResourceHandlerExternalSource getInstance( ) {

        return instance;
    }

    public static void create( InputStreamCreator isCreator ) {

        instance = new ResourceHandlerExternalSource( isCreator );
    }

    private ResourceHandlerExternalSource( InputStreamCreator isCreator ) {

        this.isCreator = isCreator;
    }

    @Override
    public OutputStream getOutputStream( String path ) {

        return null;
    }

    @Override
    public MediaLocator getResourceAsMediaLocator( String path ) {

        return isCreator.buildMediaLocator( path );
    }

    @Override
    public InputStream getResourceAsStream( String path ) {

        return buildInputStream( path );
    }

    @Override
    public URL getResourceAsURLFromZip( String path ) {

        return isCreator.buildURL( path );
    }

    @Override
    public void setZipFile( String zipFilename ) {

        // Do nothing (isCreator already knows the zip file)
    }

    public InputStream buildInputStream( String filePath ) {

        return isCreator.buildInputStream( filePath );
    }

    public String[] listNames( String filePath ) {

        return isCreator.listNames( filePath );
    }

    @Override
    public MediaLocator buildMediaLocator( String file ) {

        return isCreator.buildMediaLocator( file );
    }

    @Override
    public URL buildURL( String path ) {

        return isCreator.buildURL( path );
    }

    @Override
    public URL getResourceAsURL( String path ) {

        return buildURL( path );
    }

}
