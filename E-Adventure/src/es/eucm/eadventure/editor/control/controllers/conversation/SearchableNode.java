package es.eucm.eadventure.editor.control.controllers.conversation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.editor.control.controllers.Searchable;

public class SearchableNode extends Searchable {

	private ConversationNodeView cnv;
	
	public SearchableNode(ConversationNodeView cnv) {
		this.cnv = cnv;
	}
	
	@Override
	protected List<Searchable> getPath(Searchable dataControl) {
		List<Searchable> path = getPathFromChild(dataControl, getConversationLines());
		if (path != null) return path;
		if (dataControl instanceof SearchableNode && ((SearchableNode) dataControl).getConversationNodeView() == cnv) {
			path = new ArrayList<Searchable>();
			path.add(this);
			return path;
	    }
		return null;
	}

	private List<ConversationLine> getConversationLines() {
		List<ConversationLine> lines = new ArrayList<ConversationLine>();
		for (int i = 0; i < cnv.getLineCount(); i++) {
			ConversationLine line = new ConversationLine(this, i);
			lines.add(line);
		}
		return lines;
	}
	
	@Override
	public void recursiveSearch() {
		for (ConversationLine line : getConversationLines())
			line.recursiveSearch();
	}

	public ConversationNodeView getConversationNodeView() {
		return cnv;
	}

}
