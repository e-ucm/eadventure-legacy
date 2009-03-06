package es.eucm.eadventure.editor.control.tools.treepanel;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class DeleteNodeTool extends Tool {

	private TreeNode treeNode;
	
	private ChapterDataControl chapterDataControl;
	
	private Chapter chapter;
	
	private int index;
	
	public DeleteNodeTool(TreeNode treeNode) {
		this.treeNode = treeNode;
		this.index = treeNode.getIndexInParent();
		chapterDataControl = Controller.getInstance().getSelectedChapterDataControl();
		try {
			chapter = (Chapter) (((Chapter) chapterDataControl.getContent()).clone());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return (chapter != null);
	}

	@Override
	public boolean doTool() {
		return treeNode.delete(true);
	}

	@Override
	public boolean redoTool() {
		return treeNode.delete(false);
	}

	@Override
	public boolean undoTool() {
		treeNode.addExistingChildInPosition(index, treeNode);
		Controller.getInstance().reloadData();
		//Controller.getInstance().replaceSelectedChapter(chapter);
		// TreeNodeControl.getInstance().changeTreeNodeDataControlContent(treeNode.getDataControl().getContent());
		return true;
	}
	
	@Override
	public boolean combine(Tool other) {
		return false;
	}


}
