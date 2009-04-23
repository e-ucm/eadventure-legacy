package es.eucm.eadventure.editor.gui.elementpanels;

import javax.swing.Icon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.gui.Updateable;

/**
 * This class represents the content of a Tab in an ElementPanel.<br>
 * 
 */
public abstract class PanelTab implements Updateable {

	private static final long serialVersionUID = -7666569367118952601L;

	/**
	 * The title of the tab
	 */
	private String title;
	
	/**
	 * The tool tip text of the tab
	 */
	private String toolTipText;
	
	/**
	 * The JComponent inside the tab that represents the tabs content
	 */
	private JComponent component;
	
	/**
	 * The icon of the tab
	 */
	private Icon icon;
	
	/**
	 * The datacontrol associated with the tab
	 */
	private DataControl dataControl;
	
	/**
	 * Constructor.
	 * 
	 * @param title The title of the tab
	 * @param dataControl The data control associated with the tab
	 */
	public PanelTab(String title, DataControl dataControl) {
		super();
		this.title = title;
		this.dataControl = dataControl;
	}
	
	/**
	 * Returns the title of the tab
	 * 
	 * @return The title of the tab
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Set the toolTipText of the tab
	 * 
	 * @param toolTipText The new toolTipText
	 */
	public void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}
	
	/**
	 * Returns the toolTipText of the tab
	 * 
	 * @return The toolTipText of the tab
	 */
	public String getToolTipText() {
		return toolTipText;
	}

	/**
	 * Returns the JComponent inside the tab
	 * 
	 * @return The JComponent inside the tab
	 */
	public JComponent getComponent() {
		component = getTabComponent();
		return component;
	}
	
	/**
	 * Abstract method the returns the JComponent
	 * for a specific tab type.
	 *  
	 * @return The JComponent of the tab
	 */
	protected abstract JComponent getTabComponent();
	
	public boolean updateFields() {
		if (component instanceof Updateable) {
			return ((Updateable) component).updateFields();
		}
		return false;
	}

	/**
	 * Returns the data control associated with the tab
	 * 
	 * @return The data control associated with the tab
	 */
	public DataControl getDataControl() {
		return dataControl;
	}
	
	/**
	 * Sets the icon for the tab
	 * 
	 * @param icon The new icon for the tab
	 */
	public void setIcon (Icon icon) {
		this.icon = icon;
	}
	
	/**
	 * Returns the icon of the tab
	 * 
	 * @return The icon of the tab
	 */
	public Icon getIcon() {
		return icon;
	}
}