/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
