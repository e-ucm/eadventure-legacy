package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.gui.elementpanels.general.ExitLookPanel;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementExit;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementInfluenceArea;

public class ExitPanel extends JTabbedPane {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the exit.
	 */
	private ExitDataControl exitDataControl;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	/**
	 * Panel in which the exit area is painted.
	 */
	private ScenePreviewEditionPanel spep;

	/**
	 * Constructor.
	 * 
	 * @param exitDataControl
	 *            Exit controller
	 */
	public ExitPanel( ExitDataControl exitDataControl ) {

		JPanel mainPanel = new JPanel();
		// Set the controller
		this.exitDataControl = exitDataControl;


		JPanel looksPanel;

		String scenePath = Controller.getInstance( ).getSceneImagePath( exitDataControl.getParentSceneId( ) );
		
		if (exitDataControl.isRectangular()) {
			spep = new ScenePreviewEditionPanel(false, scenePath);
			spep.setShowTextEdition(true);
			spep.setSelectedElement(exitDataControl);
			spep.setFixedSelectedElement(true);
			if (exitDataControl.getSceneDataControl().getTrajectory().hasTrajectory()) {
				spep.setTrajectory((Trajectory) exitDataControl.getSceneDataControl().getTrajectory().getContent());
				for (NodeDataControl nodeDataControl: exitDataControl.getSceneDataControl().getTrajectory().getNodes())
					spep.addNode(nodeDataControl);
				if (exitDataControl.getInfluenceArea() != null)
					spep.addInfluenceArea(exitDataControl.getInfluenceArea());
			}
			looksPanel = spep;
		} else {
			looksPanel = new IrregularAreaEditionPanel(scenePath, exitDataControl, true, Color.RED);
			spep = ((IrregularAreaEditionPanel) looksPanel).getScenePreviewEditionPanel();
			spep.setFixedSelectedElement(false);
			if (exitDataControl.getSceneDataControl().getTrajectory().hasTrajectory()) {
				spep.setTrajectory((Trajectory) exitDataControl.getSceneDataControl().getTrajectory().getContent());
				for (NodeDataControl nodeDataControl: exitDataControl.getSceneDataControl().getTrajectory().getNodes())
					spep.addNode(nodeDataControl);
				if (exitDataControl.getInfluenceArea() != null) {
					ImageElementInfluenceArea ieia =new ImageElementInfluenceArea(exitDataControl.getInfluenceArea(), new ImageElementExit(exitDataControl)); 
					spep.addInfluenceArea(ieia);
					if (exitDataControl.getPoints().size() < 3) {
						ieia.setVisible(false);
					}
				}
			}
			spep.setMovableCategory(ScenePreviewEditionPanel.CATEGORY_INFLUENCEAREA, false);
			spep.setShowTextEdition(false);		
		}
		
		// Set the layout of the principal panel
		mainPanel.setLayout( new GridBagLayout( ) );
		//setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Exit.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create and add te documenation panel
		c.weightx = 1;
		c.weighty = 0.2;
		c.fill = GridBagConstraints.BOTH;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new BorderLayout( ) );
		documentationTextArea = new JTextArea( exitDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) exitDataControl.getContent() ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) , BorderLayout.CENTER);
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Exit.Documentation" ) ) );
		mainPanel.add( documentationPanel, c );

		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.8;
		c.gridy = 1;
		
		JPanel temp = new JPanel();
		temp.setLayout(new BorderLayout());
		
		final JCheckBox rectangular = new JCheckBox(TextConstants.getText("Exit.IsRectangular"));
		rectangular.setSelected(exitDataControl.isRectangular());
		rectangular.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (ExitPanel.this.exitDataControl.isRectangular() != rectangular.isSelected()) {
					ExitPanel.this.exitDataControl.setRectangular(rectangular.isSelected());
					Controller.getInstance().reloadPanel();
				}
			}
		});

		temp.add(rectangular, BorderLayout.NORTH);
		temp.add(looksPanel, BorderLayout.CENTER);
		
		mainPanel.add(temp, c);
		
		this.insertTab(  TextConstants.getText( "Exit.Title" ), null, mainPanel,  TextConstants.getText( "Exit.Title" ), 0 );
		this.insertTab( TextConstants.getText( "Exit.AdvancedOptions" ), null, new ExitLookPanel(this.exitDataControl.getExitLookDataControl( )), TextConstants.getText( "Exit.AdvancedOptions" ), 1 );
	}
}
