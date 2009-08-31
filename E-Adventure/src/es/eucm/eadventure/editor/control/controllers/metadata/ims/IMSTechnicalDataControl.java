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

import es.eucm.eadventure.editor.data.meta.ims.IMSTechnical;

public class IMSTechnicalDataControl {

    private IMSTechnical data;

    public IMSTechnicalDataControl( IMSTechnical data ) {

        this.data = data;
    }

    public IMSTextDataControl getLocation( ) {

        return new IMSTextDataControl( ) {

            public String getText( ) {

                return data.getLocation( );
            }

            public void setText( String text ) {

                boolean uri;
                if( text.startsWith( "0" ) )
                    uri = true;
                else
                    uri = false;
                text = text.substring( 1 );
                data.setLocation( text, uri );
            }

        };
    }

    public IMSTextDataControl getFormatController( ) {

        return new IMSTextDataControl( ) {

            public String getText( ) {

                return data.getFormat( );
            }

            public void setText( String text ) {

                data.setFormat( text );
            }

        };
    }

    public IMSTextDataControl getMaximumVersionController( ) {

        return new IMSTextDataControl( ) {

            public String getText( ) {

                return data.getMaximumVersion( );
            }

            public void setText( String text ) {

                data.setMaximumVersion( text );
            }

        };
    }

    public IMSTextDataControl getMinimumVersionController( ) {

        return new IMSTextDataControl( ) {

            public String getText( ) {

                return data.getMinimumVersion( );
            }

            public void setText( String text ) {

                data.setMinimumVersion( text );
            }

        };
    }

    /**
     * @return the data
     */
    public IMSTechnical getData( ) {

        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData( IMSTechnical data ) {

        this.data = data;
    }

}
