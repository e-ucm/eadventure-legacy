package es.eucm.eadventure.editor.control.controllers.conversation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.Searchable;

public class ConversationLine extends Searchable {

	private SearchableNode searchableNode;
	
	private int line;
	
	public ConversationLine(SearchableNode searchableNode, int i) {
		this.searchableNode = searchableNode;
		this.line = i;
	}

	@Override
	protected List<Searchable> getPath(Searchable dataControl) {
		if (dataControl instanceof ConversationLine &&
				((ConversationLine) dataControl).searchableNode.getConversationNodeView() == searchableNode.getConversationNodeView() &&
				((ConversationLine) dataControl).line == line) {
			List<Searchable> path = new ArrayList<Searchable>();
			path.add(this);
			return path;
		}
		return null;
	}

	@Override
	public void recursiveSearch() {
		check(searchableNode.getConversationNodeView().getLineName(line) , TextConstants.getText("Search.LineName"));
		check(searchableNode.getConversationNodeView().getLineText(line), TextConstants.getText("Search.LineText"));
	}

	public int getLine() {
		return line;
	}

}
