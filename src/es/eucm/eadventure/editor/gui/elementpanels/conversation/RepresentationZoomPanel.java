package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.gui.TextConstants;

public class RepresentationZoomPanel extends JPanel {

	private static final long serialVersionUID = -205324436088837002L;
	
	private RepresentationPanel representationPanel;
	
	public RepresentationZoomPanel(RepresentationPanel representationPanel2) {
		this.representationPanel = representationPanel2;
		
		Icon zoomOutIcon = new ImageIcon( "img/icons/zoomout.png" );
		JButton zoomout = new JButton(zoomOutIcon);
		zoomout.setPreferredSize(new Dimension(20,20));
		zoomout.setContentAreaFilled(false);
		zoomout.setToolTipText(TextConstants.getText("DrawPanel.ZoomOut"));
		zoomout.setFocusable(false);
		add(zoomout);
		
		final JSlider slider = new JSlider(1, 10);
		slider.setValue(10);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				float zoom = slider.getValue() / 10.0f;
				representationPanel.setScale(zoom);
				representationPanel.updateRepresentation();
			}
		});
		slider.setToolTipText(TextConstants.getText("DrawPanel.ZoomSlider"));
		slider.setFocusable(false);
		add(slider);

		Icon zoomInIcon = new ImageIcon( "img/icons/zoomin.png" );
		JButton zoomin = new JButton(zoomInIcon);
		zoomin.setPreferredSize(new Dimension(20,20));
		zoomin.setContentAreaFilled(false);
		zoomin.setToolTipText(TextConstants.getText("DrawPanel.ZoomIn"));
		zoomin.setFocusable(false);
		add(zoomin);

		zoomin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				slider.setValue(slider.getValue() + 1);
			}
		});
		zoomout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				slider.setValue(slider.getValue() - 1);
			}
		});
	}
	
	
	
	
}
