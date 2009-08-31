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
package es.eucm.eadventure.editor.data.meta.ims;

import java.util.ArrayList;

import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class IMSClassification {

    //private Vocabulary vocPurpose;
    // 9.1
    private Vocabulary purpose;

    // 9.2
    private LangString description;

    // 9.4
    private ArrayList<LangString> keyword;

    public IMSClassification( ) {

        purpose = new Vocabulary( Vocabulary.IMS_CL_PURPOSE_9_1 );
        description = null;
        keyword = new ArrayList<LangString>( );

    }

    public void addKeyword( LangString keyword ) {

        this.keyword.add( keyword );
    }

    public void setKeyword( LangString keyword ) {

        this.keyword = new ArrayList<LangString>( );
        this.keyword.add( keyword );
    }

    public LangString getKeyword( ) {

        return keyword.get( 0 );
    }

    public LangString getKeyword( int i ) {

        return keyword.get( i );
    }

    public int getNKeyword( ) {

        return keyword.size( );
    }

    public Vocabulary getPurpose( ) {

        return purpose;
    }

    public void setPurpose( int index ) {

        this.purpose.setValueIndex( index );

    }

    public LangString getDescription( ) {

        return description;
    }

    public void setDescription( LangString description ) {

        this.description = description;
    }

    public void setKeyword( ArrayList<LangString> keyword ) {

        this.keyword = keyword;
    }

}
