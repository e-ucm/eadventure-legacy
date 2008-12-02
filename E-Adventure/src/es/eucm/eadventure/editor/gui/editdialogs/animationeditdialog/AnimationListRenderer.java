package es.eucm.eadventure.editor.gui.editdialogs.animationeditdialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.data.animation.Transition;
import es.eucm.eadventure.editor.control.controllers.AssetsController;

/**
 * Class that is responsible for creating the container for
 * each type of cell in the list
 */
public class AnimationListRenderer implements ListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AnimationListRenderer() {
		super();
	}

	@Override
	public Component getListCellRendererComponent(JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
		
		//int selectedIndex = ((Integer)value).intValue();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        if (isSelected) {
        	panel.setBackground(list.getSelectionBackground());
        	panel.setForeground(list.getSelectionForeground());
        } else {
        	panel.setBackground(list.getBackground());
        	panel.setForeground(list.getForeground());
        }

        if (value instanceof Frame) {
	        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
	        Frame f = (Frame) value;
	        
	        JLabel temp = new JLabel();
	        
	        
	        Image image = null;
	        if (f.getUri() != null && f.getUri().length() > 0)
	        	image = AssetsController.getImage(f.getUri());
	        ImageIcon icon;
	        if (image == null) {
	        	icon = new ImageIcon("img/icons/noImageFrame.png"); 
	        } else {
	        	icon = new ImageIcon(image.getScaledInstance(100, -1, Image.SCALE_SMOOTH));
	        	if (icon.getIconHeight() > 100) {
	        		icon = new ImageIcon(image.getScaledInstance(-1, 100, Image.SCALE_SMOOTH));
	        	}
	        }
        	temp.setIcon(icon);
        	
        	panel.add(temp, BorderLayout.CENTER);
        	
        	temp = new JLabel("" + f.getTime());
        	temp.setHorizontalAlignment(JLabel.CENTER);
        	panel.add(temp, BorderLayout.SOUTH);	        	
        }
        else if (value instanceof Transition) {
        	JLabel temp = new JLabel();
        	temp.setHorizontalAlignment(JLabel.CENTER);
        	temp.setVerticalAlignment(JLabel.CENTER);
        	ImageIcon icon;
        	Transition t = (Transition) value;
        	switch(t.getType()) {
        	case Transition.TYPE_NONE:
        		icon = new ImageIcon("img/icons/transitionNone.png"); 
        		break;
        	case Transition.TYPE_FADEIN:
        		icon = new ImageIcon("img/icons/transitionFadein.png"); 
        		break;
        	case Transition.TYPE_HORIZONTAL:
        		icon = new ImageIcon("img/icons/transitionHorizontal.png"); 
        		break;
        	case Transition.TYPE_VERTICAL:
        		icon = new ImageIcon("img/icons/transitionVertical.png");
        		break;
        	default:
        		icon = new ImageIcon("img/icons/transitionNone.png"); 
        	}
        	temp.setIcon(icon);
        	
        	panel.add(temp, BorderLayout.CENTER);

        	temp = new JLabel("" + t.getTime());
        	temp.setHorizontalAlignment(JLabel.CENTER);
        	panel.add(temp, BorderLayout.SOUTH);	        	
        }
        return panel;
	}
	
}