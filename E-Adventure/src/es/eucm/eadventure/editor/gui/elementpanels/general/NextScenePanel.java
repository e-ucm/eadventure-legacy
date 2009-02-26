package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.NextSceneDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ScenesListDataControl;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.EffectsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.PlayerPositionDialog;
import es.eucm.eadventure.editor.gui.treepanel.TreeNodeControl;

public class NextScenePanel extends JTabbedPane {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the cutscene.
	 */
	private NextSceneDataControl nextSceneDataControl;

	/**
	 * Combo box for the possible scenes.
	 */
	private JComboBox scenesComboBox;

	/**
	 * Destiny position check box (to use it or not).
	 */
	private JCheckBox destinyPositionCheckBox;

	/**
	 * Destiny position button.
	 */
	private JButton destinyPositionButton;
	
	private JSpinner spinner;

	private JComboBox transitionCombo;
	
	/**
	 * Constructor.
	 * 
	 * @param nextSceneDataControl
	 *            Cutscene controller
	 */
	public NextScenePanel( NextSceneDataControl nextSceneDataControl ) {

		JPanel mainPanel = new JPanel();
		
		// Set the controller
		this.nextSceneDataControl = nextSceneDataControl;

		// Set the layout
		mainPanel.setLayout( new GridBagLayout( ) );
		//setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NextScene.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the combo box of scenes
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JPanel nextSceneIdPanel = new JPanel( );
		nextSceneIdPanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints c2 = new GridBagConstraints();
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.gridx = 0;
		c2.gridy = 0;
		c2.weightx = 1.0;		
		
		scenesComboBox = new JComboBox( Controller.getInstance( ).getIdentifierSummary( ).getGeneralSceneIds( ) );
		scenesComboBox.setSelectedItem( nextSceneDataControl.getNextSceneId( ) );
		scenesComboBox.addActionListener( new NextSceneComboBoxListener( ) );
		nextSceneIdPanel.add( scenesComboBox , c2);

		Icon goToIcon = new ImageIcon( "img/icons/moveNodeRight.png" );
		JButton goToButton = new JButton (goToIcon);
		goToButton.setPreferredSize(new Dimension(20,20));
		goToButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ScenesListDataControl cldc = Controller.getInstance().getSelectedChapterDataControl().getScenesList();
				for (SceneDataControl cdc : cldc.getScenes()) {
					if (cdc.getId().equals(NextScenePanel.this.nextSceneDataControl.getNextSceneId())) {
						TreeNodeControl.getInstance().changeTreeNode(cdc);
					}
				}
			}
		});
		c2.gridx = 1;
		c2.weightx = 0.1;
		nextSceneIdPanel.add( goToButton, c2);		
		
		
		nextSceneIdPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NextScene.NextSceneId" ) ) );
		mainPanel.add( nextSceneIdPanel, c );

		// Create the button for the initial point
		c.gridy = 1;
		JPanel destinyPositionPanel = new JPanel( );
		destinyPositionPanel.setLayout( new GridLayout( 0, 1 ) );
		destinyPositionCheckBox = new JCheckBox( TextConstants.getText( "NextScene.UseDestinyPosition" ), nextSceneDataControl.hasDestinyPosition( ) );
		destinyPositionCheckBox.setEnabled( Controller.getInstance( ).getIdentifierSummary( ).isScene( nextSceneDataControl.getNextSceneId( ) ) );
		destinyPositionCheckBox.addActionListener( new DestinyPositionCheckBoxListener( ) );
		
		destinyPositionButton = new JButton( TextConstants.getText( "NextScene.EditDestinyPosition" ) );
		destinyPositionButton.setEnabled( nextSceneDataControl.hasDestinyPosition( ) );
		destinyPositionButton.addActionListener( new DestinyPositionButtonListener( ) );
		
		if (!Controller.getInstance( ).isPlayTransparent( )){
			destinyPositionPanel.add( destinyPositionCheckBox );
			destinyPositionPanel.add( destinyPositionButton );
			destinyPositionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NextScene.DestinyPosition" ) ) );
		}
		
		mainPanel.add( destinyPositionPanel, c );

		// Create the button for the conditions
		c.gridy = 2;
		JPanel conditionsPanel = new JPanel( );
		conditionsPanel.setLayout( new GridLayout( ) );
		JButton conditionsButton = new JButton( TextConstants.getText( "GeneralText.EditConditions" ) );
		conditionsButton.addActionListener( new ConditionsButtonListener( ) );
		conditionsPanel.add( conditionsButton );
		conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NextScene.Conditions" ) ) );
		mainPanel.add( conditionsPanel, c );

		// Create the button for the effects
		c.gridy = 3;
		JPanel effectsPanel = new JPanel( );
		effectsPanel.setLayout( new GridLayout( ) );
		JButton effectsButton = new JButton( TextConstants.getText( "GeneralText.EditEffects" ) );
		effectsButton.addActionListener( new EffectsButtonListener( ) );
		effectsPanel.add( effectsButton );
		effectsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NextScene.Effects" ) ) );
		mainPanel.add( effectsPanel, c );

		// Create the button for the post-effects
		c.gridy = 4;
		JPanel postEffectsPanel = new JPanel( );
		postEffectsPanel.setLayout( new GridLayout( ) );
		JButton postEffectsButton = new JButton( TextConstants.getText( "GeneralText.EditPostEffects" ) );
		postEffectsButton.addActionListener( new PostEffectsButtonListener( ) );
		postEffectsPanel.add( postEffectsButton );
		postEffectsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NextScene.PostEffects" ) ) );
		mainPanel.add( postEffectsPanel, c );

		
		c.gridy = 5;
		JPanel transitionPanel = new JPanel();
		transitionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NextScene.Transition" ) ) );
		String[] options = new String[]{ TextConstants.getText("NextScene.NoTransition"),
				TextConstants.getText("NextScene.TopToBottom"),
				TextConstants.getText("NextScene.BottomToTop"),
				TextConstants.getText("NextScene.LeftToRight"),
				TextConstants.getText("NextScene.RightToLeft"),
				TextConstants.getText("NextScene.FadeIn")};
		transitionCombo = new JComboBox(options);
		transitionCombo.setSelectedIndex(nextSceneDataControl.getTransitionType());
		transitionCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				comboModified();
			}
		});
		transitionPanel.add(transitionCombo);
	
		JPanel temp = new JPanel();
		temp.add(new JLabel(TextConstants.getText("Animation.Duration") + ": "));
	    SpinnerModel sm = new SpinnerNumberModel(nextSceneDataControl.getTransitionTime(), 0, 5000, 100);
	    spinner = new JSpinner(sm);
	    spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				spinnerModified();
			}});
	    temp.add(spinner);

	    transitionPanel.add(temp);
		
		mainPanel.add(transitionPanel, c);
		
		// Add a filler at the end
		c.gridy++;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		mainPanel.add( new JFiller( ), c );
		this.insertTab( TextConstants.getText( "NextScene.Title" ), null, mainPanel, TextConstants.getText( "NextScene.Title" ), 0 );
		
		this.insertTab( TextConstants.getText( "NextScene.AdvancedOptions" ), null, new ExitLookPanel(nextSceneDataControl.getExitLookDataController( )), TextConstants.getText( "NextScene.AdvancedOptions" ), 1 );
		
	}

	protected void comboModified() {
		nextSceneDataControl.setTransitionType(transitionCombo.getSelectedIndex());
	}

	protected void spinnerModified() {
		nextSceneDataControl.setTransitionTime((Integer) spinner.getValue());					
	}

	/**
	 * Listener for next scene combo box.
	 */
	private class NextSceneComboBoxListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			nextSceneDataControl.setNextSceneId( scenesComboBox.getSelectedItem( ).toString( ) );

			// If the selected target is a scene, enable the destiny position check box
			if( Controller.getInstance( ).getIdentifierSummary( ).isScene( scenesComboBox.getSelectedItem( ).toString( ) ) )
				destinyPositionCheckBox.setEnabled( true );

			// If it is a cutscene, disable the check box (and erase the data if it was present)
			else {
				destinyPositionCheckBox.setEnabled( false );

				// If the next scene had a destiny position, erase it
				if( nextSceneDataControl.hasDestinyPosition( ) ) {
					nextSceneDataControl.toggleDestinyPosition( );
					destinyPositionCheckBox.setSelected( false );
					destinyPositionButton.setEnabled( false );
				}
			}
		}
	}

	/**
	 * Listener for the "Use destiny position in this next scene" check box.
	 */
	private class DestinyPositionCheckBoxListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			nextSceneDataControl.toggleDestinyPosition( );
			destinyPositionButton.setEnabled( nextSceneDataControl.hasDestinyPosition( ) );
		}
	}

	/**
	 * Listener for the "Set destiny player position" button
	 */
	private class DestinyPositionButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent arg0 ) {
			// Create the dialog with the destiny and show it
			PlayerPositionDialog destinyPositionDialog = new PlayerPositionDialog( scenesComboBox.getSelectedItem( ).toString( ), nextSceneDataControl.getDestinyPositionX( ), nextSceneDataControl.getDestinyPositionY( ) );

			// Set the new data
			nextSceneDataControl.setDestinyPosition( destinyPositionDialog.getPositionX( ), destinyPositionDialog.getPositionY( ) );
			System.out.println("POSITION SET");
		}
	}

	/**
	 * Listener for the edit conditions button.
	 */
	private class ConditionsButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			new ConditionsDialog( nextSceneDataControl.getConditions( ) );
		}
	}

	/**
	 * Listener for the edit effects button.
	 */
	private class EffectsButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			new EffectsDialog( nextSceneDataControl.getEffects( ) );
		}
	}

	/**
	 * Listener for the edit post-effects button.
	 */
	private class PostEffectsButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			new EffectsDialog( nextSceneDataControl.getPostEffects( ) );
		}
	}
}
