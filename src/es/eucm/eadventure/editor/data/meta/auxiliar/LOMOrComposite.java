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

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMOrComposite implements LOMESComposeType {

    private static final int numberTypeValues = 2;

    private static final int numberNameValues = 13;

    //4.4.1.1
    private Vocabulary type;

    //4.4.1.2
    private Vocabulary name;

    //4.4.1.3
    private String minimumVersion;

    //4.4.1.4
    private String maximumVersion;

    public LOMOrComposite( ) {

        type = new Vocabulary( Vocabulary.TE_TYPE_4_4_1_1, Vocabulary.LOM_ES_SOURCE, 0 );
        name = new Vocabulary( Vocabulary.TE_NAME_4_4_1_2, Vocabulary.LOM_ES_SOURCE, 0 );
        this.minimumVersion = Controller.getInstance( ).getEditorMinVersion( );
        this.maximumVersion = Controller.getInstance( ).getEditorVersion( );
    }

    public LOMOrComposite( Vocabulary type, Vocabulary name, String minimumVersion, String maximumVersion ) {

        this.type = type;
        this.name = name;
        this.minimumVersion = minimumVersion;
        this.maximumVersion = maximumVersion;
    }

    public static String[] getTypeOptions( ) {

        String[] options = new String[ numberTypeValues ];
        for( int i = 0; i < options.length; i++ ) {
            options[i] = TC.get( "LOMES.Technical.Type" + i );
        }
        return options;
    }

    public static String[] getNameOptions( ) {

        String[] options = new String[ numberNameValues ];
        for( int i = 0; i < options.length; i++ ) {
            options[i] = TC.get( "LOMES.Technical.Name" + i );
        }
        return options;
    }

    public String getTitle( ) {

        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return the type
     */
    public Vocabulary getType( ) {

        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType( Vocabulary type ) {

        this.type = type;
    }

    /**
     * @return the name
     */
    public Vocabulary getName( ) {

        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName( Vocabulary name ) {

        this.name = name;
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
