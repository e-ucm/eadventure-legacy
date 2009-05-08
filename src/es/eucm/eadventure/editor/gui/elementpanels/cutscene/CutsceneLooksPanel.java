package es.eucm.eadventure.editor.gui.elementpanels.cutscene;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;

import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public class CutsceneLooksPanel extends LooksPanel implements Updateable{

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	private ImagePanel imagePanel;

	/**
	 * Constructor.
	 * 
	 * @param cutsceneDataControl
	 *            Cutscene controller
	 */
	public CutsceneLooksPanel( CutsceneDataControl cutsceneDataControl ) {
		super( cutsceneDataControl );
	}

	@Override
	protected void createPreview( ) {
		String imagePath = ((CutsceneDataControl)dataControl).getPreviewImage();
		if (imagePath == null)
			imagePath = "";
		JPanel previewPanel = new JPanel();
		previewPanel.setLayout( new BorderLayout() );
		imagePanel = new ImagePanel( imagePath );
		previewPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Book.Preview" ) ) );
		previewPanel.add( imagePanel, BorderLayout.CENTER );
		lookPanel.add( previewPanel, cLook );
		lookPanel.setPreferredSize( new Dimension( 0, 90 ) );
	}

	@Override
	public void updatePreview( ) {
		String imagePath = ((CutsceneDataControl)dataControl).getPreviewImage();
		if (imagePath == null)
			imagePath = "";
		imagePanel.loadImage(imagePath);
		imagePanel.repaint( );
		getParent( ).getParent( ).repaint( );
	}

	public void updateResources( ) {
		super.updateResources( );
		getParent( ).getParent( ).repaint( );
	}

	@Override
	public boolean updateFields() {
		updatePreview();
		return true;
	}
}
