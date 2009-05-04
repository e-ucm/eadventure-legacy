package es.eucm.eadventure.editor.control.tools.conversation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.data.chapter.conversation.node.DialogueConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.OptionConversationNode;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition tool for adding a child to a node in a conversation
 * @author Javier
 *
 */
public class AddConversationNodeTool extends Tool{

	protected ConversationNode newChild;
	
	protected ConversationNode parent;
	
	protected int index;
	
	protected int nodeType;
	
	protected Map<ConversationNodeView,List<ConditionsController>> allConditions;
	
	public AddConversationNodeTool ( ConversationNode parent, int nodeType ,Map<ConversationNodeView,List<ConditionsController>> allConditions){
		this.parent = parent;
		this.nodeType = nodeType;
		this.allConditions = allConditions;
	}
	
	public AddConversationNodeTool ( ConversationNodeView nodeView, int nodeType,Map<ConversationNodeView,List<ConditionsController>> allConditions ){
		this.parent = (ConversationNode)nodeView;
		this.nodeType = nodeType;
		this.allConditions = allConditions;
	}

	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return index!=-1&&newChild!=null;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {

		// By default, add the child
		boolean addChild = true;

		// If it's sure to add the child
		if( addChild ) {

			// Create the requested node (only accept dialogue and option node)
			if( nodeType == ConversationNode.DIALOGUE )
				newChild = new DialogueConversationNode( );
			if( nodeType == ConversationNode.OPTION )
				newChild = new OptionConversationNode( );

			// If a child has been created
			if( newChild != null ) {

				// Add the child to the given node
				parent.addChild( newChild );
				
				// Add to Conditions controller
				allConditions.put((ConversationNodeView)newChild, new ArrayList<ConditionsController>());

				// If the node was an option node, add a new line
				if( parent.getType( ) == ConversationNode.OPTION ){
					parent.addLine( new ConversationLine( ConversationLine.PLAYER, TextConstants.getText( "ConversationLine.NewOption" ) ) );
					allConditions.get((ConversationNodeView)parent).add(new ConditionsController(new Conditions(), 
			    			Controller.CONVERSATION_OPTION_LINE, Integer.toString(0)));
				}
				// Save the index of the newChild
				index = -1;
				for (int i=0; i<parent.getChildCount(); i++){
					if (parent.getChild( i ) == newChild) {
						index = i;
						break;
					}
				}

			}
		}
		
		
		
		return newChild != null;		
	}

	@Override
	public boolean redoTool() {
		parent.addChild(index, newChild);
		// Add to Conditions controller
		allConditions.put((ConversationNodeView)newChild, new ArrayList<ConditionsController>());
		// If the node was an option node, add a new line
		if( parent.getType( ) == ConversationNode.OPTION ){
			parent.addLine( index, new ConversationLine( ConversationLine.PLAYER, TextConstants.getText( "ConversationLine.NewOption" ) ) );
			allConditions.get((ConversationNodeView)parent).add(new ConditionsController(new Conditions(), 
	    			Controller.CONVERSATION_OPTION_LINE, Integer.toString(0)));
		}
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		parent.removeChild(index);
		allConditions.remove((ConversationNodeView)newChild);
		if( parent.getType( ) == ConversationNode.OPTION ){
			parent.removeLine(index);
			allConditions.get((ConversationNodeView)parent).remove(index);
			
		}
		Controller.getInstance().updatePanel();
		return true;
	}

}
