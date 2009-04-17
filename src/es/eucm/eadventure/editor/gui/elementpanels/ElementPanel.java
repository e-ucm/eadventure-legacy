package es.eucm.eadventure.editor.gui.elementpanels;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.editor.gui.Updateable;

public class ElementPanel extends JTabbedPane implements Updateable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1546563540388226634L;
	
	private List<PanelTab> tabs;
	
	private int selected = -1;
	
	public ElementPanel() {
		super();
		tabs = new ArrayList<PanelTab>();
		
		this.addChangeListener(new ChangeListener() {
			public void stateChanged(final ChangeEvent arg0) {
				if (selected == getSelectedIndex())
					return;
				selected = getSelectedIndex();
				SwingUtilities.invokeLater(new Runnable()
				{
				    public void run()
				    {
				    	((JPanel) getSelectedComponent()).removeAll();
				    	PanelTab tab = tabs.get(getSelectedIndex());
				    	((JPanel) getSelectedComponent()).add(tab.getComponent(), BorderLayout.CENTER);
				    	((JPanel) getSelectedComponent()).updateUI();
				    }
				});
			}
		});
	}
	
	public void addTab(PanelTab tab) {
		tabs.add(tab);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		if (tab.getToolTipText() != null)
			this.addTab(tab.getTitle(), null, panel, tab.getToolTipText());
		else
			this.addTab(tab.getTitle(), panel);
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
}