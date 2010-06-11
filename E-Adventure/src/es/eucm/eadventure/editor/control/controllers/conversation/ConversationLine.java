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
package es.eucm.eadventure.editor.control.controllers.conversation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.Searchable;

public class ConversationLine extends Searchable {

    private SearchableNode searchableNode;

    private int line;

    public ConversationLine( SearchableNode searchableNode, int i ) {

        this.searchableNode = searchableNode;
        this.line = i;
    }

    @Override
    protected List<Searchable> getPath( Searchable dataControl ) {

        if( dataControl instanceof ConversationLine && ( (ConversationLine) dataControl ).searchableNode.getConversationNodeView( ) == searchableNode.getConversationNodeView( ) && ( (ConversationLine) dataControl ).line == line ) {
            List<Searchable> path = new ArrayList<Searchable>( );
            path.add( this );
            return path;
        }
        return null;
    }

    @Override
    public void recursiveSearch( ) {

        check( searchableNode.getConversationNodeView( ).getLineName( line ), TC.get( "Search.LineName" ) );
        check( searchableNode.getConversationNodeView( ).getLineText( line ), TC.get( "Search.LineText" ) );
    }

    public int getLine( ) {

        return line;
    }

}
