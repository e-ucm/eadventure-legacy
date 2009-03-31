package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;
import es.eucm.eadventure.editor.control.tools.scene.ChangeHasTrajectoryTool;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.editdialogs.PlayerPositionDialog;

public class SceneInfoPanel extends JPanel implements Updateable{

	private static final long serialVersionUID = -6436346206278699690L;

	private SceneDataControl sceneDataControl;
	
	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	/**
	 * Text field for the name.
	 */
	private JTextField nameTextField;

	/**
	 * Initial position button.
	 */
	private JButton initialPositionButton;

	private JCheckBox isAllowPlayerLayer;
	
	private SceneLooksPanel looksPanel;
	
	public SceneInfoPanel(SceneDataControl sDataControl, SceneLooksPanel looksPanel) {
		super();

		this.sceneDataControl = sDataControl;
		this.looksPanel = looksPanel;

		setLayout( new GridBagLayout( ) );
		GridBagConstraints cDoc = new GridBagConstraints( );


		cDoc.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		cDoc.fill = GridBagConstraints.BOTH;
		cDoc.weighty = 0.5;
		cDoc.weightx = 1;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( sceneDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) sceneDataControl.getContent()) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Scene.Documentation" ) ) );
		add( documentationPanel, cDoc );

		// Create the text field for the name
		cDoc.gridy = 1;
		cDoc.weighty = 0;
		cDoc.fill = GridBagConstraints.HORIZONTAL;
		JPanel namePanel = new JPanel( );
		namePanel.setLayout( new GridLayout( ) );
		nameTextField = new JTextField( sceneDataControl.getName( ) );
		nameTextField.getDocument().addDocumentListener( new NameChangeListener(nameTextField, (Named) sceneDataControl.getContent()));
		namePanel.add( nameTextField );
		namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Scene.Name" ) ) );
		add( namePanel, cDoc );

		if (!Controller.getInstance( ).isPlayTransparent( )){
			cDoc.gridy++;
			JPanel useTrajectoryPanel = new JPanel();
			useTrajectoryPanel.setLayout( new GridLayout(0,1));
			JCheckBox useTrajectoryCheckBox = new JCheckBox(TextConstants.getText("Scene.UseTrajectory"), sceneDataControl.getTrajectory().hasTrajectory());
			useTrajectoryCheckBox.addActionListener( new TrajectoryCheckBoxListener() );
			
			useTrajectoryPanel.add(useTrajectoryCheckBox);
			useTrajectoryPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Scene.UseTrajectoryPanel" ) ) );

			add( useTrajectoryPanel, cDoc );
		}
		
		cDoc.gridy++;
		JPanel initialPositionPanel = new JPanel( );
		initialPositionPanel.setLayout( new GridLayout( 0, 1 ) );
		JCheckBox initialPositionCheckBox = new JCheckBox( TextConstants.getText( "Scene.UseInitialPosition" ), sceneDataControl.hasDefaultInitialPosition( ) );
		initialPositionCheckBox.addActionListener( new InitialPositionCheckBoxListener( ) );
		
		initialPositionButton = new JButton( TextConstants.getText( "Scene.EditInitialPosition" ) );
		initialPositionButton.setEnabled( sceneDataControl.hasDefaultInitialPosition( ) );
		initialPositionButton.addActionListener( new InitialPositionButtonListener( ) );
		
		if (!Controller.getInstance( ).isPlayTransparent( )){
			initialPositionPanel.add( initialPositionCheckBox );
			initialPositionPanel.add( initialPositionButton );
			initialPositionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Scene.DefaultInitialPosition" ) ) );
		}
		
		add( initialPositionPanel, cDoc );
		
		cDoc.gridy++;
		JPanel allowPlayerLayer = new JPanel();
		allowPlayerLayer.setLayout(new GridLayout( 0, 1 ) );
		isAllowPlayerLayer = new JCheckBox(TextConstants.getText("Scene.AllowPlayer"),sceneDataControl.isAllowPlayer());
		isAllowPlayerLayer.setSelected( sceneDataControl.isAllowPlayer() );
		isAllowPlayerLayer.addActionListener(new PlayerLayerCheckBoxListener());
		allowPlayerLayer.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Scene.AllowPlayerBorder" ) ) );
		allowPlayerLayer.add(isAllowPlayerLayer);
		add(allowPlayerLayer, cDoc);
		
		cDoc.gridy++;
		cDoc.weightx = 1;
		cDoc.weighty = 0.5;
		add( new JFiller( ), cDoc );
	}
	
	private class TrajectoryCheckBoxListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			Controller.getInstance().addTool(new ChangeHasTrajectoryTool(((JCheckBox) e.getSource()).isSelected(), sceneDataControl));
		}
	}

	/**
	 * Listener for the "Set default initial position" button
	 */
	private class InitialPositionButtonListener implements ActionListener {

		public void actionPerformed( ActionEvent arg0 ) {
			PlayerPositionDialog initialPositionDialog = new PlayerPositionDialog( sceneDataControl.getId( ), sceneDataControl.getDefaultInitialPositionX( ), sceneDataControl.getDefaultInitialPositionY( ) );
			sceneDataControl.setDefaultInitialPosition( initialPositionDialog.getPositionX( ), initialPositionDialog.getPositionY( ) );
		}
	}
	
	/**
	 * Listener for the "Allow player layer" check box.
	 */
	private class PlayerLayerCheckBoxListener implements ActionListener {

		public void actionPerformed( ActionEvent e ) {
			sceneDataControl.changeAllowPlayerLayer(isAllowPlayerLayer.isSelected(),looksPanel);
		}

	}

	
	/**
	 * Listener for the "Use initial position in this scene" check box.
	 */
	private class InitialPositionCheckBoxListener implements ActionListener {

		public void actionPerformed( ActionEvent e ) {
			sceneDataControl.toggleDefaultInitialPosition( );
			initialPositionButton.setEnabled( sceneDataControl.hasDefaultInitialPosition( ) );
		}

	}


	public boolean updateFields() {
		documentationTextArea.setText( sceneDataControl.getDocumentation( ) );
		nameTextField.setText( sceneDataControl.getName( ) );
		initialPositionButton.setEnabled( sceneDataControl.hasDefaultInitialPosition( ) );
		isAllowPlayerLayer.setSelected( sceneDataControl.isAllowPlayer() );
		return true;
	}

}
