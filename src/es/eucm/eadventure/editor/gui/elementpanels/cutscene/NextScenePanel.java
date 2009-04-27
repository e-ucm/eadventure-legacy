package es.eucm.eadventure.editor.gui.elementpanels.cutscene;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.data.chapter.scenes.Cutscene;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.EffectsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.PlayerPositionDialog;


public class NextScenePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9164972283091760862L;

	private CutsceneDataControl dataControl;

	private JRadioButton returnToPrevious;
	private JRadioButton goToNewScene;
	private JRadioButton endChapter;
	private JComboBox nextSceneCombo;
	private JCheckBox usePosition;
	private JButton setPosition;
	private JButton editEffects;
	private JComboBox transition;
	private JSpinner timeSpinner;

	public NextScenePanel(CutsceneDataControl cutsceneDataControl) {
		this.dataControl = cutsceneDataControl;
		
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Cutscene.CutsceneEndReached" ) ) );

		returnToPrevious = new JRadioButton(TextConstants.getText("Cutscene.ReturnToLastScene"));
		goToNewScene = new JRadioButton(TextConstants.getText("Cutscene.GoToNextScene"));
		endChapter = new JRadioButton(TextConstants.getText("Cutscene.ChapterEnd"));
		returnToPrevious.addActionListener(new ReturnToPreviousActionListener());
		goToNewScene.addActionListener(new GoToNewSceneActionListener());
		endChapter.addActionListener(new EndChapterActionListener());
		
		ButtonGroup group = new ButtonGroup();
		group.add(returnToPrevious);
		group.add(goToNewScene);
		group.add(endChapter);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,3));
		buttonPanel.add(returnToPrevious);
		buttonPanel.add(goToNewScene);
		buttonPanel.add(endChapter);

		setLayout(new BorderLayout());
		add(buttonPanel, BorderLayout.NORTH);

		JPanel detailsPanel = new JPanel();
		
		nextSceneCombo = new JComboBox(Controller.getInstance().getIdentifierSummary().getGeneralSceneIds());
		nextSceneCombo.addActionListener( new NextSceneComboBoxListener( ) );

		JPanel positionPanel = new JPanel();
		positionPanel.setLayout(new GridBagLayout());
		GridBagConstraints posC = new GridBagConstraints();
		posC.gridx = 0;
		posC.gridy = 0;
		usePosition = new JCheckBox(TextConstants.getText( "NextScene.UseDestinyPosition" ));
		usePosition.addActionListener( new DestinyPositionCheckBoxListener());

		setPosition = new JButton(TextConstants.getText( "NextScene.EditDestinyPositionShort" ));
		setPosition.addActionListener(new DestinyPositionButtonListener());
		positionPanel.add(usePosition, posC);
		posC.gridx++;
		positionPanel.add(setPosition, posC);
		
		editEffects = new JButton(TextConstants.getText( "GeneralText.EditEffects" ));
		editEffects.addActionListener(new EffectsButtonListener());

		JPanel transitionPanel = new JPanel();
		String[] options = new String[]{ TextConstants.getText("NextScene.NoTransition"),
				TextConstants.getText("NextScene.TopToBottom"),
				TextConstants.getText("NextScene.BottomToTop"),
				TextConstants.getText("NextScene.LeftToRight"),
				TextConstants.getText("NextScene.RightToLeft"),
				TextConstants.getText("NextScene.FadeIn")};
		transition = new JComboBox(options);
		transition.addActionListener(new TransitionComboChangeListener());
		
	    SpinnerModel sm = new SpinnerNumberModel(0, 0, 5000, 100);
		timeSpinner = new JSpinner(sm);
		timeSpinner.addChangeListener(new TransitionSpinnerChangeListener());
		transitionPanel.add(new JLabel(TextConstants.getText("NextScene.Transition")));
		transitionPanel.add(transition);
		transitionPanel.add(new JLabel(TextConstants.getText("NextScene.TransitionTime")));
		transitionPanel.add(timeSpinner);
		transitionPanel.add(new JLabel("seg"));
		
		detailsPanel.setLayout(new GridLayout(0,1));
		JPanel temp = new JPanel();
		temp.add(new JLabel(TextConstants.getText("NextScene.NextSceneId")));
		temp.add(nextSceneCombo);
		temp.add(editEffects);
		detailsPanel.add(temp);
		detailsPanel.add(positionPanel);
		detailsPanel.add(transitionPanel);
		
		add(detailsPanel, BorderLayout.CENTER);
		
		updateNextSceneInfo();
		
	}
	
	private void updateNextSceneInfo() {
		boolean enablePosition = false;
		boolean enablePositionButton = false;
		
		if (dataControl.getNext() == Cutscene.ENDCHAPTER) {
			endChapter.setSelected(true);
		} else if (dataControl.getNext() == Cutscene.GOBACK) {
			returnToPrevious.setSelected(true);
		} else {
			goToNewScene.setSelected(true);
			nextSceneCombo.setSelectedItem(dataControl.getNextSceneId());
			usePosition.setSelected(dataControl.hasDestinyPosition());
			transition.setSelectedIndex(dataControl.getTransitionType());
			timeSpinner.setValue(dataControl.getTransitionTime());
			enablePosition = Controller.getInstance( ).getIdentifierSummary( ).isScene( dataControl.getNextSceneId( ) );
			enablePositionButton = enablePosition && dataControl.hasDestinyPosition();
		}
		nextSceneCombo.setEnabled(goToNewScene.isSelected());
		usePosition.setEnabled(enablePosition && goToNewScene.isSelected());
		setPosition.setEnabled(enablePositionButton && goToNewScene.isSelected());
		editEffects.setEnabled(goToNewScene.isSelected());
		transition.setEnabled(goToNewScene.isSelected());
		timeSpinner.setEnabled(goToNewScene.isSelected());
		updateUI();
	}

	
	/**
	 * Listener for next scene combo box.
	 */
	private class NextSceneComboBoxListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			dataControl.setNextSceneId( nextSceneCombo.getSelectedItem( ).toString( ) );
			updateNextSceneInfo();
		}
	}

	/**
	 * Listener for the "Use destiny position in this next scene" check box.
	 */
	private class DestinyPositionCheckBoxListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			dataControl.toggleDestinyPosition( );
			updateNextSceneInfo();
		}
	}

	/**
	 * Listener for the "Set destiny player position" button
	 */
	private class DestinyPositionButtonListener implements ActionListener {
		public void actionPerformed( ActionEvent arg0 ) {
			PlayerPositionDialog destinyPositionDialog = new PlayerPositionDialog( nextSceneCombo.getSelectedItem( ).toString( ), dataControl.getDestinyPositionX( ), dataControl.getDestinyPositionY( ) );
			dataControl.setDestinyPosition( destinyPositionDialog.getPositionX( ), destinyPositionDialog.getPositionY( ) );
		}
	}

	/**
	 * Listener for the edit effects button.
	 */
	private class EffectsButtonListener implements ActionListener {

		public void actionPerformed( ActionEvent e ) {
			new EffectsDialog( dataControl.getEffects( ) );
		}
	}
	
	private class TransitionComboChangeListener implements ActionListener {

		public void actionPerformed( ActionEvent e ) {
			dataControl.setTransitionType(transition.getSelectedIndex());
		}
	}
	
	private class TransitionSpinnerChangeListener implements ChangeListener {

		public void stateChanged(ChangeEvent e) {
			dataControl.setTransitionTime((Integer) timeSpinner.getValue());					
		}
	}
	

	private class ReturnToPreviousActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (returnToPrevious.isSelected()) {
				dataControl.setNext(Cutscene.GOBACK);
				updateNextSceneInfo();
			}
		}
	}
	
	private class GoToNewSceneActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (goToNewScene.isSelected()) {
				dataControl.setNext(Cutscene.NEWSCENE);
				updateNextSceneInfo();
			}
		}
	}
	
	private class EndChapterActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (endChapter.isSelected()) {
				dataControl.setNext(Cutscene.ENDCHAPTER);
				updateNextSceneInfo();
			}
		}
		
	}
}
