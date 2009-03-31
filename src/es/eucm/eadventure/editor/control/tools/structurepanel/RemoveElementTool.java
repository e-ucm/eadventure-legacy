package es.eucm.eadventure.editor.control.tools.structurepanel;

import javax.swing.JTable;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElement;

public class RemoveElementTool extends Tool {

	private StructureElement element;
	
	private ChapterDataControl chapterDataControl;
	
	private Chapter chapter;
	
	private JTable table;
	
	public RemoveElementTool(JTable table, StructureElement element) {
		this.element = element;
		this.table = table;
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
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		if (element.delete(true)) {
			table.clearSelection();
			Controller.getInstance().updateTree();
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		Controller.getInstance().replaceSelectedChapter((Chapter)chapterDataControl.getContent());
		Controller.getInstance().reloadData();
		return true;
	}

	@Override
	public boolean undoTool() {
		Controller.getInstance().replaceSelectedChapter(chapter);
		Controller.getInstance().reloadData();
		return true;
	}

}
