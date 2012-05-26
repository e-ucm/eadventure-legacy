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
/**
 * E-Adventure3D project. E-UCM group, Department of Software Engineering and
 * Artificial Intelligence. Faculty of Informatics, Complutense University of
 * Madrid (Spain).
 * 
 * @author Cañizal, G., Del Blanco, A., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @year 2007
 */
package es.eucm.eadventure.engine.resourcehandler.zipurl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Cañizal, G., Del Blanco, A., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * 
 */
public class ZipURL {

    public ZipURL( ) {

    }

    public static URL createAssetURL( String zipFile, String assetPath ) throws MalformedURLException {

        URL url = null;

        // File parentFile = new File(Controller.getInstance().getZipFile());
        File parentFile = new File( zipFile );
        File file = new File( parentFile, assetPath );

        url = file.toURI( ).toURL( );
        url = new URL( url.getProtocol( ), url.getHost( ), url.getPort( ), url.getFile( ), new ZipURLStreamHandler( zipFile, assetPath ) );

        return url;
    }

    public static URL createAssetURL( File file ) throws MalformedURLException {

        URL url = null;

        url = file.toURI( ).toURL( );
        url = new URL( url.getProtocol( ), url.getHost( ), url.getPort( ), url.getFile( ), new ZipURLStreamHandler( file.getAbsolutePath( ) ) );

        return url;
    }

}
