package es.eucm.eadventure.editor.gui.elementpanels;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.gui.DataControlsPanel;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;

public class ElementPanel extends JTabbedPane implements Updateable, DataControlsPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1546563540388226634L;
	
	private List<PanelTab> tabs;

	private JComponent component = null;
	
	private int selected = -1;
	
	public ElementPanel() {
		super();
		tabs = new ArrayList<PanelTab>();
		
		this.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent arg0) {
				if (selected != getSelectedIndex()) {
					selected = getSelectedIndex();
					((JPanel) getSelectedComponent()).removeAll();
					PanelTab tab = tabs.get(getSelectedIndex());
					StructureControl.getInstance().visitDataControl(tab.getDataControl());
					component = tab.getComponent();
					((JPanel) getSelectedComponent()).add(component, BorderLayout.CENTER);
					((JPanel) getSelectedComponent()).updateUI();
				}
			}
		});
	}
	
	public void addTab(PanelTab tab) {
		tabs.add(tab);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		if (tab.getToolTipText() != null)
			this.addTab(tab.getTitle(), tab.getIcon(), panel, tab.getToolTipText());
		else
			this.addTab(tab.getTitle(), tab.getIcon(), panel);
	}

	@Override
	public boolean updateFields() {
		boolean update = false;
		if (getSelectedComponent() instanceof Updateable)
			update = ((Updateable) tabs.get(this.getSelectedIndex())).updateFields();
		if (!update) {
	    	((JPanel) getSelectedComponent()).removeAll();
	    	PanelTab tab = tabs.get(getSelectedIndex());
	    	((JPanel) getSelectedComponent()).add(tab.getComponent(), BorderLayout.CENTER);
	    	((JPanel) getSelectedComponent()).updateUI();
		}
		return true;
	}

	@Override
	public void setSelectedItem(List<DataControl> path) {
		if (path.size() > 0) {
			for (int i = 0; i < tabs.size(); i++) {
				if (tabs.get(i).getDataControl() == path.get(path.size() - 1)) {
					path.remove(path.size() - 1);
					this.setSelectedIndex(i);
					if (component != null) {
						if (component instanceof DataControlsPanel) {
					    	((DataControlsPanel) component).setSelectedItem(path);
					    	path = null;
					    }
					}
					return;
				}
			}
		}
	}
}