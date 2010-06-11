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
