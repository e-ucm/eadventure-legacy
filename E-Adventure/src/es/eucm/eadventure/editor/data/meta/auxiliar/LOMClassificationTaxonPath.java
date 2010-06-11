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
package es.eucm.eadventure.editor.data.meta.auxiliar;

import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMClassificationTaxonPath implements LOMESComposeType {

    //9.2.1
    private LangString source;

    //9.2.2
    private LOMTaxon taxon;

    public LOMClassificationTaxonPath( ) {

        source = new LangString( "" );
        taxon = new LOMTaxon( );
    }

    public LOMClassificationTaxonPath( LangString source, LOMTaxon taxon ) {

        this.source = source;
        this.taxon = taxon;
    }

    /**
     * @return the source
     */
    public LangString getSource( ) {

        return source;
    }

    /**
     * @param source
     *            the source to set
     */
    public void setSource( LangString source ) {

        this.source = source;
    }

    /**
     * @return the taxon
     */
    public LOMTaxon getTaxon( ) {

        return taxon;
    }

    /**
     * @param taxon
     *            the taxon to set
     */
    public void setTaxon( LOMTaxon taxon ) {

        this.taxon = taxon;
    }

    public String getTitle( ) {

        // TODO Auto-generated method stub
        return null;
    }

}
