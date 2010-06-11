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
package es.eucm.eadventure.editor.data.meta.ims;

import java.util.ArrayList;

public class IMSTechnical {

    //4.1
    private ArrayList<String> format;

    //4.3 Each location begins with a number which identifies the type attribute:
    //    0- URI
    //	  1- TEXT
    private ArrayList<String> location;

    //4.4.1.3
    private String minimumVersion;

    //4.4.1.4
    private String maximumVersion;

    public IMSTechnical( ) {

        format = new ArrayList<String>( );
        location = new ArrayList<String>( );
        minimumVersion = null;
        maximumVersion = null;
    }

    public void setFormat( String format ) {

        this.format.add( format );

    }

    public String getFormat( int i ) {

        return format.get( i );
    }

    /**
     * 
     * Add new location
     * 
     * @param location
     *            The String with the location
     * @param URI
     *            Indicate if is URI or TEXT
     */
    public void setLocation( String location, boolean URI ) {

        this.location.add( URI ? "0 " : "1 " + location );
    }

    /**
     * Returns the specified location
     * 
     * @param i
     * @return
     */
    public String getLocation( int i ) {

        return this.location.get( i );
    }

    /**
     * Returns the first location
     * 
     * @return
     */
    public String getLocation( ) {

        return location.get( 0 );
    }

    /**
     * Returns the first format
     * 
     * @return
     */
    public String getFormat( ) {

        return format.get( 0 );
    }

    /**
     * @return the minimumVersion
     */
    public String getMinimumVersion( ) {

        return minimumVersion;
    }

    /**
     * @param minimumVersion
     *            the minimumVersion to set
     */
    public void setMinimumVersion( String minimumVersion ) {

        this.minimumVersion = minimumVersion;
    }

    /**
     * @return the maximumVersion
     */
    public String getMaximumVersion( ) {

        return maximumVersion;
    }

    /**
     * @param maximumVersion
     *            the maximumVersion to set
     */
    public void setMaximumVersion( String maximumVersion ) {

        this.maximumVersion = maximumVersion;
    }

}
