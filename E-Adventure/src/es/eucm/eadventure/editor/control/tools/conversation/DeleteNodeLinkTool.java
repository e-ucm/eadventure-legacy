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
package es.eucm.eadventure.editor.control.tools.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteNodeLinkTool extends Tool {

    protected ConversationNode parent;

    protected int linkIndex;

    protected ConversationNode linkDeleted;

    protected String confirmTitle;

    protected String confirmText;

    public DeleteNodeLinkTool( ConversationNodeView nodeView ) {

        this( (ConversationNode) nodeView );
    }

    public DeleteNodeLinkTool( ConversationNode parent ) {

        this.parent = parent;
        this.linkIndex = 0;
        this.confirmTitle = TextConstants.getText( "Conversation.OperationDeleteLink" );
        this.confirmText = TextConstants.getText( "Conversation.ConfirmationDeleteLink" );
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return linkDeleted != null;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        // Ask for confirmation
        if( Controller.getInstance( ).showStrictConfirmDialog( confirmTitle, confirmText ) ) {
            linkDeleted = parent.removeChild( linkIndex );
            return true;
        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        parent.removeChild( linkIndex );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        parent.addChild( linkIndex, linkDeleted );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
