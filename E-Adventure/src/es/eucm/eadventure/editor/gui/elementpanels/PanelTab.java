package es.eucm.eadventure.editor.gui.elementpanels;

import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.gui.Updateable;

public abstract class PanelTab implements Updateable {

	private static final long serialVersionUID = -7666569367118952601L;

	private String title;
	
	private String toolTipText;
	
	private JComponent component;
	
	private DataControl dataControl;
	
	public PanelTab(String title, DataControl dataControl) {
		super();
		this.title = title;
		this.dataControl = dataControl;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}
	
	public String getToolTipText() {
		return toolTipText;
	}

	public JComponent getComponent() {
		component = getTabComponent();
		return component;
	}
	
	protected abstract JComponent getTabComponent();
	
	public boolean updateFields() {
		if (component instanceof Updateable) {
			return ((Updateable) component).updateFields();
		}
		return false;
	}

	public DataControl getDataControl() {
		return dataControl;
	}
	
}	
	
	
	

