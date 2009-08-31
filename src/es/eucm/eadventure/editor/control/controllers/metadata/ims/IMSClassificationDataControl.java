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
package es.eucm.eadventure.editor.control.controllers.metadata.ims;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.ims.IMSClassification;
import es.eucm.eadventure.editor.data.meta.LangString;

public class IMSClassificationDataControl {

    private IMSClassification data;

    public IMSClassificationDataControl( IMSClassification data ) {

        this.data = data;
    }

    public IMSOptionsDataControl getPurpose( ) {

        return new IMSOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getPurpose( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TextConstants.getText( "IMS.Classification.Purpose" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.setPurpose( option );
            }

            public int getSelectedOption( ) {

                return data.getPurpose( ).getValueIndex( );
            }

        };
    }

    public IMSTextDataControl getDescription( ) {

        return new IMSTextDataControl( ) {

            public String getText( ) {

                return data.getDescription( ).getValue( 0 );
            }

            public void setText( String text ) {

                data.setDescription( new LangString( text ) );
            }

        };
    }

    public IMSTextDataControl getKeywordController( ) {

        return new IMSTextDataControl( ) {

            public String getText( ) {

                return data.getKeyword( ).getValue( 0 );
            }

            public void setText( String text ) {

                data.setKeyword( new LangString( text ) );
            }

        };
    }

    public IMSClassification getData( ) {

        return data;
    }

    public void setData( IMSClassification data ) {

        this.data = data;
    }

}
