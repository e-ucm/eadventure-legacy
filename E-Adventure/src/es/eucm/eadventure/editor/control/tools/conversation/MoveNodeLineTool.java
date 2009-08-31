/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.control.tools.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class MoveNodeLineTool extends Tool{

	public static final int UP = 1;
	public static final int DOWN = 2;
	
	protected ConversationNode parent;
	
	protected int lineIndex;
	
	protected int mode;
	
	public MoveNodeLineTool ( ConversationNodeView nodeView, int lineIndex, int mode ){
		this ((ConversationNode) nodeView, lineIndex, mode);
	}
	
	public MoveNodeLineTool ( ConversationNode parent, int lineIndex, int mode ){
		this.parent = parent;
		this.lineIndex = lineIndex;
		this.mode = mode;
	}
	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		if (mode == UP){
			return moveUp();
		}
		else if (mode == DOWN){
			return moveDown();
		} else
			return false;

	}

	@Override
	public boolean redoTool() {
		boolean moved = false;
		if (mode == UP){
			moved= moveUp();
		}
		else if (mode == DOWN){
			moved =  moveDown();
		} 
		
		if (moved)
			Controller.getInstance().updatePanel();
		return moved;
	}

	@Override
	public boolean undoTool() {
		boolean moved = false;
		if (mode == UP){
			moved = moveDown();
		}
		else if (mode == DOWN){
			moved = moveUp();
		} 

		if (moved)
			Controller.getInstance().updatePanel();
		return moved;

		
	}
	
	protected boolean moveDown(){
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
	
	protected boolean moveUp(){
		boolean lineMoved = false;

		// Cannot move the line up if it is in the first position
		if( lineIndex > 0 ) {
			// Remove the line and insert it in the upper position
			parent.addLine( lineIndex - 1, parent.removeLine( lineIndex ) );

			// If the node is a OptionNode, move the respective child along the line
			if( parent.getType( ) == ConversationNode.OPTION ) {
				ConversationNode nodeMoved = parent.removeChild( lineIndex );
				parent.addChild( lineIndex - 1, nodeMoved );
			}

			lineMoved = true;
		}

		return lineMoved;		
	}

}
