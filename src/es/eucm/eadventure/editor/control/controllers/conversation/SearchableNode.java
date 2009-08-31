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
package es.eucm.eadventure.editor.control.controllers.conversation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.editor.control.controllers.Searchable;

public class SearchableNode extends Searchable {

    private ConversationNodeView cnv;

    public SearchableNode( ConversationNodeView cnv ) {

        this.cnv = cnv;
    }

    @Override
    protected List<Searchable> getPath( Searchable dataControl ) {

        List<Searchable> path = getPathFromChild( dataControl, getConversationLines( ) );
        if( path != null )
            return path;
        if( dataControl instanceof SearchableNode && ( (SearchableNode) dataControl ).getConversationNodeView( ) == cnv ) {
            path = new ArrayList<Searchable>( );
            path.add( this );
            return path;
        }
        return null;
    }

    private List<ConversationLine> getConversationLines( ) {

        List<ConversationLine> lines = new ArrayList<ConversationLine>( );
        for( int i = 0; i < cnv.getLineCount( ); i++ ) {
            ConversationLine line = new ConversationLine( this, i );
            lines.add( line );
        }
        return lines;
    }

    @Override
    public void recursiveSearch( ) {

        for( ConversationLine line : getConversationLines( ) )
            line.recursiveSearch( );
    }

    public ConversationNodeView getConversationNodeView( ) {

        return cnv;
    }

}
