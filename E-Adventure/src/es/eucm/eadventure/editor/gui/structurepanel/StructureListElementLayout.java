package es.eucm.eadventure.editor.gui.structurepanel;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.List;

public class StructureListElementLayout implements LayoutManager2 {

	List<StructureComponent> components = new ArrayList<StructureComponent>();
	
	@Override
	public void addLayoutComponent(Component arg0, Object arg1) {
		components.add(new StructureComponent(arg0, (String) arg1));
	}

	@Override
	public float getLayoutAlignmentX(Container arg0) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container arg0) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container arg0) {
	}

	@Override
	public Dimension maximumLayoutSize(Container arg0) {
		return null;
	}

	@Override
	public void addLayoutComponent(String arg0, Component arg1) {
	}

	@Override
	public void layoutContainer(Container parent) {
	    int width = parent.getWidth();
	    for (StructureComponent c : components) {
	    	if (c.getType().equals("title")) {
	    		c.getComponent().setBounds(0, 0, width, 39);
	    	} else if (c.getType().equals("addButton")) {
	    		c.getComponent().setBounds(width - 25, 7, 20, 20);
	    	}
	    	else 
	    		c.getComponent().setBounds(0, 39, width, parent.getHeight() - 39);
	    }
	}

	@Override
	public Dimension minimumLayoutSize(Container arg0) {
		return new Dimension(0,0);
	}

	@Override
	public Dimension preferredLayoutSize(Container arg0) {
		return new Dimension(arg0.getSize().width, arg0.getSize().height);
	}

	@Override
	public void removeLayoutComponent(Component arg0) {
		int k = -1;
		for (int i = 0; i < components.size(); i++)
			if (components.get(i).getComponent() == arg0)
				k = i;
		if (k != -1)
			components.remove(k);
	}
	
	private class StructureComponent {
		
		private Component component;
		
		private String type;
		
		public StructureComponent(Component component, String type) {
			this.component = component;
			this.type = type;
		}
		
		public Component getComponent() {
			return component;
		}
		
		public String getType() {
			return type;
		}
	}

}
