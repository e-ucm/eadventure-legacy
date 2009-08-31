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
package es.eucm.eadventure.editor.data.meta.auxiliar;

import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMClassificationTaxon implements LOMESComposeType {

    //9.2.2 TAXON
    //9.2.2.1 
    private String identifier;

    //9.2.2.2
    private LangString entry;

    public LOMClassificationTaxon( ) {

        identifier = new String( "" );
        entry = new LangString( "" );
    }

    public LOMClassificationTaxon( String identifier, LangString entry ) {

        this.identifier = identifier;
        this.entry = entry;
    }

    /**
     * @return the identifier
     */
    public String getIdentifier( ) {

        return identifier;
    }

    /**
     * @param identifier
     *            the identifier to set
     */
    public void setIdentifier( String identifier ) {

        this.identifier = identifier;
    }

    /**
     * @return the entry
     */
    public LangString getEntry( ) {

        return entry;
    }

    /**
     * @param entry
     *            the entry to set
     */
    public void setEntry( LangString entry ) {

        this.entry = entry;
    }

    public String getTitle( ) {

        // TODO Auto-generated method stub
        return null;
    }

}
