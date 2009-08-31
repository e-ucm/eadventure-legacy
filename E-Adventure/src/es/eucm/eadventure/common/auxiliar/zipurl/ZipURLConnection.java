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
package es.eucm.eadventure.common.auxiliar.zipurl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.io.FileInputStream;

/**
 * @author Cañizal, G., Del Blanco, A., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * 
 */
public class ZipURLConnection extends URLConnection {

    private String assetPath;

    private String zipFile;

    /**
     * @param url
     * @throws MalformedURLException
     */
    public ZipURLConnection( URL assetURL, String zipFile, String assetPath ) {

        super( assetURL );
        this.assetPath = assetPath;
        this.zipFile = zipFile;
    }

    /**
     * @param url
     * @throws MalformedURLException
     */
    public ZipURLConnection( URL assetURL, String assetPath ) {

        super( assetURL );
        this.assetPath = assetPath;
        zipFile = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.net.URLConnection#connect()
     */
    @Override
    public void connect( ) throws IOException {

    }

    @Override
    public InputStream getInputStream( ) {

        if( assetPath != null ) {
            return buildInputStream( );
        }
        else {
            try {
                return url.openStream( );
            }
            catch( IOException e ) {
                e.printStackTrace( );
                return null;
            }
            // return AssetsController.getInputStream(assetPath);
        }
    }

    private InputStream buildInputStream( ) {

        try {
            if( zipFile != null ) {
                return new FileInputStream( zipFile + "/" + assetPath );
            }
            else {
                return new FileInputStream( assetPath );
            }
        }
        catch( FileNotFoundException e ) {
            e.printStackTrace( );
            return null;
        }
    }

}
