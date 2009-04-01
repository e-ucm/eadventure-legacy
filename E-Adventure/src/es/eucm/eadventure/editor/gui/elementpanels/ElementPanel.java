package es.eucm.eadventure.editor.gui.elementpanels;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

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
	
	public ElementPanel() {
		super();
		tabs = new ArrayList<PanelTab>();
		
		this.addChangeListener(new ChangeListener() {

			public void stateChanged(final ChangeEvent arg0) {
				SwingUtilities.invokeLater(new Runnable()
				{
				    public void run()
				    {
				    	ElementPanel.this.getSelectedComponent().repaint();
				    }
				});
			}
			
		});
	}
	
	public void addTab(PanelTab tab) {
		tabs.add(tab);
		
		tab.getComponent().addFocusListener(new FocusListener() {
			public void focusGained(final FocusEvent arg0) {
				SwingUtilities.invokeLater(new Runnable()
				{
				    public void run()
				    {
				    	System.out.println("Called...");
				    	arg0.getComponent().repaint();
				    }
				});
			}

			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		this.addTab(tab.getTitle(), tab.getComponent());
	}

	@Override
	public boolean updateFields() {
		boolean result = true;
		for (PanelTab tab : tabs) {
			if (tab instanceof Updateable) {
				result = result && ((Updateable) tab).updateFields();
			} else 
				return false;
		}
		return result;
	}
	
	
}