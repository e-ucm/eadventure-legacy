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
package es.eucm.eadventure.engine.gamelauncher.gameentry;

/**
 * Contains the title and the story of an e-Adventure game
 */
public class GameEntry {

    /**
     * Filename of the adventure
     */
    private String filename;

    /**
     * Title of the adventure
     */
    private String title;

    /**
     * Description of the adventure
     */
    private String description;

    /**
     * True if the adventure is valid, false otherwise.
     */
    private boolean valid;

    /**
     * Default constructor
     */
    public GameEntry( ) {

        filename = null;
        title = "No title avalaible";
        description = "No description avalaible";
        valid = true;
    }

    /**
     * Sets the filename of the adventure
     * 
     * @param filename
     *            Filename of the adventure
     */
    public void setFilename( String filename ) {

        this.filename = filename;
    }

    /**
     * Sets the title of the adventure
     * 
     * @param title
     *            Title of the adventure
     */
    public void setTitle( String title ) {

        this.title = ( title.equals( "" ) ? "No title avalaible" : title );
    }

    /**
     * Sets the description of the adventure
     * 
     * @param description
     *            description of the adventure
     */
    public void setDescription( String description ) {

        this.description = ( description.equals( "" ) ? "No description avalaible" : description );
    }

    /**
     * Sets if the adventure is valid or not
     * 
     * @param valid
     *            True if the adventure is valid, false otherwise
     */
    public void setValid( boolean valid ) {

        this.valid = valid;
    }

    /**
     * Appends the filename of the adventure to the title
     * 
     * @param filename
     *            Filename of the adventure
     */
    public void appendFilenameToTitle( String filename ) {

        title = title + " (" + filename + ")";
    }

    /**
     * Returns the filename of the adventure
     * 
     * @return Filename of the adventure
     */
    public String getFilename( ) {

        return filename;
    }

    /**
     * Returns the title of the adventure
     * 
     * @return Title of the adventure
     */
    public String getTitle( ) {

        return title;
    }

    /**
     * Returns the description of the adventure
     * 
     * @return Description of the adventure
     */
    public String getDescription( ) {

        return description;
    }

    /**
     * Returns whether the adventure is valid or not
     * 
     * @return True if the adventure is valid, false otherwise
     */
    public boolean isValid( ) {

        return valid;
    }

    /*
     *  (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString( ) {

        return title;
    }
}
