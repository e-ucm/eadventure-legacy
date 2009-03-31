package es.eucm.eadventure.editor.gui.elementpanels.scene;

import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

public class ScenePanel extends ElementPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	private SceneLooksPanel looksPanel;

	/**
	 * Constructor.
	 * 
	 * @param sceneDataControl
	 *            Scene controller
	 */
	public ScenePanel( SceneDataControl sDataControl ) {
		looksPanel = new SceneLooksPanel( sDataControl );
		JPanel docPanel = new SceneInfoPanel(sDataControl, looksPanel);
		ReferencesListPanel referencesPanel = new ReferencesListPanel(sDataControl.getReferencesList());
		BarriersListPanel barriersPanel = new BarriersListPanel(sDataControl.getBarriersList());
		ActiveAreasListPanel activeAreasPanel = new ActiveAreasListPanel(sDataControl.getActiveAreasList());
		ExitsListPanel exitsPanel = new ExitsListPanel(sDataControl.getExitsList());
		
		PanelTab tab1 = new PanelTab(TextConstants.getText( "Scene.LookPanelTitle" ), looksPanel);
		PanelTab tab2 = new PanelTab(TextConstants.getText("Scene.DocPanelTitle"), docPanel);
		PanelTab tab3 = new PanelTab(TextConstants.getText( "ItemReferencesList.Title" ), referencesPanel);
		PanelTab tab4 = new PanelTab(TextConstants.getText( "BarriersList.Title" ), barriersPanel);
		PanelTab tab5 = new PanelTab(TextConstants.getText( "ActiveAreasList.Title" ), activeAreasPanel);
		PanelTab tab6 = new PanelTab(TextConstants.getText( "ExitsList.Title" ), exitsPanel);
		this.addTab(tab1);
		this.addTab(tab2);
		this.addTab(tab3);
		this.addTab(tab4);
		this.addTab(tab5);
		this.addTab(tab6);
		
//		tabPanel.insertTab( TextConstants.getText( "Scene.LookPanelTitle" ), null, looksPanel, TextConstants.getText( "Scene.LookPanelTip" ), 0 );
//		tabPanel.insertTab( TextConstants.getText( "Scene.DocPanelTitle" ), null, docPanel, TextConstants.getText( "Scene.DocPanelTip" ), 1 );
//		setLayout( new BorderLayout( ) );
//		add( tabPanel, BorderLayout.CENTER );

	}


}
