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
package es.eucm.eadventure.engine.resourcehandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

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
    public URL buildURL( String path ) {

        return isCreator.buildURL( path );
    }

    @Override
    public URL getResourceAsURL( String path ) {

        return buildURL( path );
    }

}
