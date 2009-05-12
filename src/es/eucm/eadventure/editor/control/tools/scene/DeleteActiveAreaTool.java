package es.eucm.eadventure.editor.control.tools.scene;

import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreasListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ActiveAreasTable;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElement;

public class DeleteActiveAreaTool extends Tool {

	private ActiveAreasListDataControl dataControl;
	
	private IrregularAreaEditionPanel iaep;
	
	private ActiveAreasTable table;

	private ActiveAreaDataControl element;
	
	private int position;

	private ChapterDataControl chapterDataControl;
	
	private Chapter chapter;

	
	public DeleteActiveAreaTool(ActiveAreasListDataControl dataControl2,
			IrregularAreaEditionPanel iaep, ActiveAreasTable table2) {
		this.dataControl = dataControl2;
		this.table = table2;
		this.iaep = iaep;
		
		chapterDataControl = Controller.getInstance().getSelectedChapterDataControl();
		try {
			chapter = (Chapter) (((Chapter) chapterDataControl.getContent()).clone());
		} catch (Exception e) {
			ReportDialog.GenerateErrorReport(e, true, "Could not clone chapter");	
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
		position = table.getSelectedRow();
		element = dataControl.getActiveAreas().get(position);
		if (dataControl.deleteElement(element, true)) {
			iaep.getScenePreviewEditionPanel().removeElement(element);
			iaep.getScenePreviewEditionPanel().setSelectedElement((ImageElement) null);
			((AbstractTableModel) table.getModel()).fireTableDataChanged();
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
