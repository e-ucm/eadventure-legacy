package es.eucm.eadventure.editor.gui.elementpanels.atrezzo;

import java.awt.Dimension;

import javax.swing.BorderFactory;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.atrezzo.AtrezzoDataControl;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public class AtrezzoLooksPanel extends LooksPanel implements Updateable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Preview image panel.
	 */
	private ImagePanel imagePanel;
	
	private AtrezzoDataControl atrezzoDataControl;

	public AtrezzoLooksPanel( AtrezzoDataControl control ) {
		super( control );
		this.atrezzoDataControl = control;
	}

	@Override
	protected void createPreview( ) {
		atrezzoDataControl = (AtrezzoDataControl) this.dataControl;
		// Take the path to the current image of the atrezzo item
		String atrezzoImagePath = atrezzoDataControl.getPreviewImage( );

		imagePanel = new ImagePanel( atrezzoImagePath );
		imagePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Atrezzo.Preview" ) ) );
		lookPanel.add( imagePanel, cLook );
		// TODO Parche, arreglar
		lookPanel.setPreferredSize( new Dimension( 0, 90 ) );

	}

	@Override
	public void updatePreview( ) {
		imagePanel.loadImage( atrezzoDataControl.getPreviewImage( ) );
		imagePanel.repaint( );

	}

	public void updateResources( ) {
		super.updateResources( );
		getParent( ).repaint( );
	}

	public boolean updateFields() {
		updatePreview();
		return true;
	}
}

