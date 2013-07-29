/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.tools.conversation;

import java.util.List;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddNodeLineTool extends Tool {

    protected ConversationNode parent;

    protected int lineIndex;

    protected ConversationLine lineAdded;

    protected String name;

    protected List<ConditionsController> node;

    public AddNodeLineTool( ConversationNodeView nodeView, int lineIndex, String name, List<ConditionsController> node ) {

        this( (ConversationNode) nodeView, lineIndex, name, node );
    }

    public AddNodeLineTool( ConversationNode parent, int lineIndex, String name, List<ConditionsController> node ) {

        this.parent = parent;
        this.lineIndex = lineIndex;
        this.name = name;
        this.node = node;
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return lineAdded != null;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        lineAdded = new ConversationLine( name, TC.get( "ConversationLine.DefaultText" ) );
        parent.addLine( lineIndex, lineAdded );
        node.add( lineIndex, new ConditionsController( lineAdded.getConditions( ), Controller.CONVERSATION_OPTION_LINE, Integer.toString( lineIndex ) ) );
        return true;
    }

    @Override
    public boolean redoTool( ) {

        parent.addLine( lineIndex, lineAdded );
        node.add( lineIndex, new ConditionsController( lineAdded.getConditions( ), Controller.CONVERSATION_OPTION_LINE, Integer.toString( lineIndex ) ) );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        parent.removeLine( lineIndex );
        node.remove( lineIndex );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
