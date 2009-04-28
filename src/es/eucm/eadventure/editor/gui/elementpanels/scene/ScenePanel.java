package es.eucm.eadventure.editor.gui.elementpanels.scene;

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

public class ScenePanel extends ElementPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param sceneDataControl
	 *            Scene controller
	 */
	public ScenePanel( SceneDataControl sDataControl ) {
		this.addTab(new SceneLookPanelTab(sDataControl));
		this.addTab(new SceneDocPanelTab(sDataControl));
		this.addTab(new ItemsPanelTab(sDataControl));
		if (!Controller.getInstance().isPlayTransparent())
			this.addTab(new BarriersPanelTab(sDataControl));
		this.addTab(new ActiveAreasPanelTab(sDataControl));
		this.addTab(new ExitsPanelTab(sDataControl));
		if (!Controller.getInstance().isPlayTransparent())
			this.addTab(new TrajectoryPanelTab(sDataControl));
	}

	private class SceneLookPanelTab extends PanelTab {
		private SceneDataControl sDataControl;
		
		public SceneLookPanelTab(SceneDataControl sDataControl) {
			super(TextConstants.getText("Scene.LookPanelTitle"), sDataControl);
			this.setHelpPath("test.html");
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new SceneLooksPanel( sDataControl );
		}
	}

	private class SceneDocPanelTab extends PanelTab {
		private SceneDataControl sDataControl;
		
		public SceneDocPanelTab(SceneDataControl sDataControl) {
			super(TextConstants.getText("Scene.DocPanelTitle"), sDataControl);
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new SceneInfoPanel( sDataControl );
		}
	}
	
	private class ItemsPanelTab extends PanelTab {
		private SceneDataControl sDataControl;
		
		public ItemsPanelTab(SceneDataControl sDataControl) {
			super(TextConstants.getText("ItemReferencesList.Title"), sDataControl.getReferencesList());
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new ReferencesListPanel(sDataControl.getReferencesList());
		}
	}
	
	private class BarriersPanelTab extends PanelTab {
		private SceneDataControl sDataControl;
		
		public BarriersPanelTab(SceneDataControl sDataControl) {
			super(TextConstants.getText("BarriersList.Title"), sDataControl.getBarriersList());
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new BarriersListPanel(sDataControl.getBarriersList());
		}
	}

	private class ActiveAreasPanelTab extends PanelTab {
		private SceneDataControl sDataControl;
		
		public ActiveAreasPanelTab(SceneDataControl sDataControl) {
			super(TextConstants.getText("ActiveAreasList.Title"), sDataControl.getActiveAreasList());
			setToolTipText(TextConstants.getText("ActiveAreasList.Information"));
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new ActiveAreasListPanel(sDataControl.getActiveAreasList());
		}
	}

	private class ExitsPanelTab extends PanelTab {
		private SceneDataControl sDataControl;
		
		public ExitsPanelTab(SceneDataControl sDataControl) {
			super(TextConstants.getText("ExitsList.Title"), sDataControl.getExitsList());
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new ExitsListPanel(sDataControl.getExitsList());
		}
	}

	private class TrajectoryPanelTab extends PanelTab {
		private SceneDataControl sDataControl;
		
		public TrajectoryPanelTab(SceneDataControl sDataControl) {
			super(TextConstants.getText("Trajectory.Title"), sDataControl.getTrajectory());
			setToolTipText(TextConstants.getText("Trajectory.Title.ToolTip"));
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new TrajectoryPanel(sDataControl.getTrajectory(), sDataControl);
		}
	}
}
