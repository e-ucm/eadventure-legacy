package es.eucm.eadventure.editor.gui.structurepanel;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;

public class StructureControl {

	static private StructureControl instance;
	
	static public StructureControl getInstance() {
		if (instance == null)
			instance = new StructureControl();
		return instance;
	}

	private StructurePanel structurePanel;
	
	private StructurePanel pastStructure;
	
	private List<DataControl> backList;
	
	private List<DataControl> forwardList;
	
	private StructureControl() {
		backList = new ArrayList<DataControl>();
		forwardList = new ArrayList<DataControl>();
	}
	
	public void setStructurePanel(StructurePanel structurePanel) {
	    if (structurePanel instanceof EffectsStructurePanel)
		pastStructure = structurePanel;
	    this.structurePanel = structurePanel;
	}
	
	public void resetStructurePanel(){
	    structurePanel=pastStructure;
	}
	
		
	public void changeDataControl(DataControl dataControl) {
		if (dataControl!=null){
	    	List<DataControl> path = Controller.getInstance().getSelectedChapterDataControl().getPath(dataControl);
		if (path != null) {
			structurePanel.setSelectedItem(path);
		}
		}
	}
	
	public void visitDataControl(DataControl dataControl) {
		if (backList.size() > 0) {
			if (dataControl != backList.get(backList.size() - 1))
				backList.add(dataControl);
		} else 
			backList.add(dataControl);
		forwardList.clear();
	}
	
	/**
	 * Go back in the list of visited tree nodes (if possible)
	 */
	public void goBack() {
		if (backList.size() > 1) {
			DataControl temp = backList.remove(backList.size() - 1);
			DataControl temp2 = backList.get(backList.size() - 1);
			List<DataControl> tempBack = new ArrayList<DataControl>();
			tempBack.addAll(backList);
			List<DataControl> tempFwrd = new ArrayList<DataControl>();
			tempFwrd.addAll(forwardList);
			changeDataControl(temp2);
			backList = tempBack;
			forwardList = tempFwrd;
			forwardList.add(temp);
		}
	}
	
	/**
	 * Go forward in the list of visited tree nodes (if possible) 
	 */
	public void goForward() {
		if (forwardList.size() > 0) {
			DataControl temp = forwardList.get(forwardList.size() - 1);
			forwardList.remove(temp);
			List<DataControl> tempBack = new ArrayList<DataControl>();
			tempBack.addAll(backList);
			List<DataControl> tempFwrd = new ArrayList<DataControl>();
			tempFwrd.addAll(forwardList);
			changeDataControl(temp);
			backList = tempBack;
			backList.add(temp);
			forwardList = tempFwrd;
		}
	}

	

}
