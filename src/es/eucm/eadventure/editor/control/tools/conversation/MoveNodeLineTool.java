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
package es.eucm.eadventure.editor.control.tools.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class MoveNodeLineTool extends Tool {

    public static final int UP = 1;

    public static final int DOWN = 2;

    protected ConversationNode parent;

    protected int lineIndex;

    protected int mode;

    public MoveNodeLineTool( ConversationNodeView nodeView, int lineIndex, int mode ) {

        this( (ConversationNode) nodeView, lineIndex, mode );
    }

    public MoveNodeLineTool( ConversationNode parent, int lineIndex, int mode ) {

        this.parent = parent;
        this.lineIndex = lineIndex;
        this.mode = mode;
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return true;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        if( mode == UP ) {
            return moveUp( );
        }
        else if( mode == DOWN ) {
            return moveDown( );
        }
        else
            return false;

    }

    @Override
    public boolean redoTool( ) {

        boolean moved = false;
        if( mode == UP ) {
            moved = moveUp( );
        }
        else if( mode == DOWN ) {
            moved = moveDown( );
        }

        if( moved )
            Controller.getInstance( ).updatePanel( );
        return moved;
    }

    @Override
    public boolean undoTool( ) {

        boolean moved = false;
        if( mode == UP ) {
            moved = moveDown( );
        }
        else if( mode == DOWN ) {
            moved = moveUp( );
        }

        if( moved )
            Controller.getInstance( ).updatePanel( );
        return moved;

    }

    protected boolean moveDown( ) {

        boolean lineMoved = false;

        // Cannot move the line down if it is the last position
        if( lineIndex < parent.getLineCount( ) - 1 ) {
            // Remove the line and insert it in the lower position
            parent.addLine( lineIndex + 1, parent.removeLine( lineIndex ) );

            // If the node is a OptionNode, move the respective child along the line
            if( parent.getType( ) == ConversationNodeView.OPTION ) {
                ConversationNode nodeMoved = parent.removeChild( lineIndex );
                parent.addChild( lineIndex + 1, nodeMoved );
            }

            lineMoved = true;
        }

        return lineMoved;

    }

    protected boolean moveUp( ) {

        boolean lineMoved = false;

        // Cannot move the line up if it is in the first position
        if( lineIndex > 0 ) {
            // Remove the line and insert it in the upper position
            parent.addLine( lineIndex - 1, parent.removeLine( lineIndex ) );

            // If the node is a OptionNode, move the respective child along the line
            if( parent.getType( ) == ConversationNodeView.OPTION ) {
                ConversationNode nodeMoved = parent.removeChild( lineIndex );
                parent.addChild( lineIndex - 1, nodeMoved );
            }

            lineMoved = true;
        }

        return lineMoved;
    }

}
