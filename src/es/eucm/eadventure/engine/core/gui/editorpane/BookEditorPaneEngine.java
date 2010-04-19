/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, availabe at http://e-adventure.e-ucm.es.
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
 */
package es.eucm.eadventure.engine.core.gui.editorpane;

import java.net.URL;

import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.html.HTML;
import javax.swing.text.html.ImageView;
import javax.swing.text.html.HTMLEditorKit.HTMLFactory;

import es.eucm.eadventure.common.gui.BookEditorPane;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

public class BookEditorPaneEngine extends BookEditorPane {

    private static final long serialVersionUID = 5081543464254158693L;

    private String uri;

    public BookEditorPaneEngine( String uri ) {

        super( );
        this.uri = uri.substring( 0, uri.lastIndexOf( "/" ) + 1 );
        this.setEditorKit( new BookHTMLEditorKitEngine( ) );
    }

    private class BookHTMLEditorKitEngine extends BookHTMLEditorKit {

        private static final long serialVersionUID = -8639564774418040491L;

        BookHTMLFactory f = new BookHTMLFactory( );

        @Override
        public HTMLFactory getViewFactory( ) {

            return f;
        }
    }

    private class BookHTMLFactory extends HTMLFactory {

        @Override
        public View create( Element elem ) {

            Object o = elem.getAttributes( ).getAttribute( StyleConstants.NameAttribute );
            if( o instanceof HTML.Tag ) {
                HTML.Tag kind = (HTML.Tag) o;
                if( kind == HTML.Tag.IMG )
                    return new EngineImageView( elem );
            }

            return super.create( elem );
        }

    }

    private class EngineImageView extends ImageView {

        public EngineImageView( Element elem ) {

            super( elem );
        }

        @Override
        public URL getImageURL( ) {

            String src = (String) getElement( ).getAttributes( ).getAttribute( HTML.Attribute.SRC );
            if( src == null ) {
                return null;
            }
            String path = uri + src;
            return ResourceHandler.getInstance( ).getResourceAsURL( path );

        }
    }
}
