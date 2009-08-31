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

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;

public class DeleteNodeOptionTool extends DeleteNodeLinkTool{

	protected ConversationLine deletedLine;
	
	public DeleteNodeOptionTool(ConversationNode parent, int optionIndex) {
		super(parent);
		this.confirmText = TextConstants.getText( "Conversation.ConfirmationDeleteOption" );
		this.confirmTitle = TextConstants.getText( "Conversation.OperationDeleteOption" );
		this.linkIndex = optionIndex;
	}
	
	public DeleteNodeOptionTool(ConversationNodeView parent, int optionIndex) {
		super(parent);
		this.confirmText = TextConstants.getText( "Conversation.ConfirmationDeleteOption" );
		this.confirmTitle = TextConstants.getText( "Conversation.OperationDeleteOption" );
		this.linkIndex = optionIndex;
	}
	
	public boolean doTool(){
		boolean done = super.doTool();
		if (done){
			deletedLine = parent.getLine(linkIndex);
			parent.removeLine( linkIndex );
		}
		return done;
	}
	
	public boolean undoTool(){
		boolean done = super.undoTool();
		if (done){
			parent.addLine( linkIndex, deletedLine );
		}
		return done;
	}

	public boolean redoTool(){
		boolean done = super.redoTool();
		if (done){
			parent.removeLine( linkIndex );
		}
		return done;
	}

}
