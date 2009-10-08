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
