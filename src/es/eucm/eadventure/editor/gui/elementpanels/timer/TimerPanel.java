package es.eucm.eadventure.editor.gui.elementpanels.timer;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.timer.TimerDataControl;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.EffectsDialog;

public class TimerPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the timer.
	 */
	private TimerDataControl timerDataControl;
	
	/**
	 * Documentation text area
	 */
	private JTextArea documentationTextArea;
	
	/**
	 * End conditions button
	 */
	private JButton conditions2Button;
	
	/**
	 * Text field for the display name
	 */
	private JTextField displayName;
	
	/**
	 * Check box that set the timer to count-down
	 */
	private JCheckBox countDown;
	
	/**
	 * Check box that sets the timer to show when stopped
	 */
	private JCheckBox showWhenStopped;
	
	/**
	 * Constructor.
	 * 
	 * @param timerDataControl
	 *            timer controller
	 */
	public TimerPanel( TimerDataControl timerDataControl ) {

		// Set the controller
		this.timerDataControl = timerDataControl;

		// Set the layout
		setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the documentation panel
		c.fill = GridBagConstraints.BOTH;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.3;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( timerDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Timer.Documentation" ) ) );
		add( documentationPanel, c );

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0;
		
		// Create the time panel
		c.gridy = 1;
		
		add ( createTimePanel(), c );

		
		// Create loop control panel
		c.gridy++;
		add ( createLoopControlPanel(), c );

		// Create the button for the conditions
		c.gridy++;
		JPanel conditionsPanel = new JPanel( );
		conditionsPanel.setLayout( new GridLayout( ) );
		JButton conditionsButton = new JButton( TextConstants.getText( "GeneralText.EditInitConditions" ) );
		conditionsButton.addActionListener( new InitConditionsButtonListener( ) );
		conditionsPanel.add( conditionsButton );
		conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Timer.InitConditions" ) ) );
		add( conditionsPanel, c );
		
		// Create the button for the conditions
		c.gridy++;
		JPanel conditions2Panel = new JPanel( );
		conditions2Panel.setLayout( new GridLayout( 2, 1) );
		
		JCheckBox usesEndCondition = new JCheckBox(TextConstants.getText("Timer.UsesEndCondition"));
		usesEndCondition.setSelected(timerDataControl.isUsesEndCondition());
		usesEndCondition.addChangeListener(new CheckBoxChangeListener(CheckBoxChangeListener.USESENDCONDITION));
		conditions2Panel.add(usesEndCondition);
		
		conditions2Button = new JButton( TextConstants.getText( "GeneralText.EditEndConditions" ) );
		conditions2Button.addActionListener( new EndConditionsButtonListener( ) );
		conditions2Button.setEnabled(timerDataControl.isUsesEndCondition());
		conditions2Panel.add( conditions2Button );
		conditions2Panel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Timer.EndConditions" ) ) );
		add( conditions2Panel, c );

		// Create the button for the effects
		c.gridy++;
		JPanel effectsPanel = new JPanel( );
		effectsPanel.setLayout( new GridLayout( ) );
		JButton effectsButton = new JButton( TextConstants.getText( "GeneralText.EditEffects" ) );
		effectsButton.addActionListener( new EffectsButtonListener( ) );
		effectsPanel.add( effectsButton );
		effectsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Timer.Effects" ) ) );
		add( effectsPanel, c );

		// Create the button for the post-effects
		c.gridy++;
		JPanel postEffectsPanel = new JPanel( );
		postEffectsPanel.setLayout( new GridLayout( ) );
		JButton postEffectsButton = new JButton( TextConstants.getText( "GeneralText.EditPostEffects" ) );
		postEffectsButton.addActionListener( new PostEffectsButtonListener( ) );
		postEffectsPanel.add( postEffectsButton );
		postEffectsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Timer.PostEffects" ) ) );
		add( postEffectsPanel, c );

		// Add a filler at the end
		c.gridy++;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		add( new JFiller( ), c );
		
	}

	private JPanel createTimePanel() {
		JPanel timePanel = new JPanel();
		timePanel.setLayout(new GridBagLayout());
		GridBagConstraints c_time = new GridBagConstraints();
		c_time.gridx = 0;
		c_time.gridy = 0;
		long current = timerDataControl.getTime( );
		long min = 1;
		long max = 99 * 3600;
		long increment = 1;
		JLabel totalTime = new JLabel("seg");
		JSpinner timeSpinner = new JSpinner(new SpinnerNumberModel(current, min, max, increment));
		timeSpinner.addChangeListener(new TimeSpinnerListener());
		JPanel temp = new JPanel();
		temp.add(timeSpinner);
		temp.add(totalTime);
		timePanel.add( temp , c_time);
		
		c_time.gridx++;
		c_time.fill = GridBagConstraints.HORIZONTAL;
		c_time.insets = new Insets(5, 5, 0, 0);
		JCheckBox showTime = new JCheckBox(TextConstants.getText("Timer.ShowTimer"));
		showTime.setSelected(timerDataControl.isShowTime());
		showTime.addChangeListener(new CheckBoxChangeListener(CheckBoxChangeListener.SHOW_TIME));
		timePanel.add(showTime, c_time);
		c_time.gridx = 0;
		c_time.gridy++;
		
		JLabel dnLabel = new JLabel(TextConstants.getText("Timer.DisplayName"));
		timePanel.add(dnLabel, c_time);
		c_time.gridx++;
		
		displayName = new JTextField(8);
		displayName.setText(timerDataControl.getDisplayName());
		displayName.setEnabled(timerDataControl.isShowTime());
		displayName.getDocument().addDocumentListener(new DisplayNameChangesListener());
		timePanel.add(displayName, c_time);
		c_time.gridx = 0;
		c_time.gridy++;
		
		countDown = new JCheckBox(TextConstants.getText("Timer.CountDown"));
		countDown.setSelected(timerDataControl.isCountDown());
		countDown.setEnabled(timerDataControl.isShowTime());
		countDown.addChangeListener(new CheckBoxChangeListener(CheckBoxChangeListener.COUNTDOWN));
		timePanel.add(countDown, c_time);
		c_time.gridx++;
		
		showWhenStopped = new JCheckBox(TextConstants.getText("Timer.ShowWhenStopped"));
		showWhenStopped.setSelected(timerDataControl.isShowWhenStopped());
		showWhenStopped.setEnabled(timerDataControl.isShowTime());
		showWhenStopped.addChangeListener(new CheckBoxChangeListener(CheckBoxChangeListener.SHOWWHENSTOPPED));
		timePanel.add(showWhenStopped, c_time);
		c_time.gridx++;
		
		timePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Timer.Time" ) ) );
		return timePanel;
	}
	
	private JPanel createLoopControlPanel() {
		JPanel loopControlPanel = new JPanel();
		loopControlPanel.setLayout(new GridBagLayout());
		GridBagConstraints c2 = new GridBagConstraints();
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.anchor = GridBagConstraints.WEST;
		c2.gridx = 0;
		c2.gridy = 0;
		c2.weightx = 0.1;
		
		JCheckBox multipleStart = new JCheckBox(TextConstants.getText("Timer.MultipleStarts"));
		multipleStart.setSelected(timerDataControl.isMultipleStarts());
		multipleStart.addChangeListener(new CheckBoxChangeListener(CheckBoxChangeListener.MULTIPLESTARTS));
		loopControlPanel.add( multipleStart , c2);
		c2.gridy++;
		c2.weightx = 1.0;
		
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		Font font = informationTextPane.getFont();
		font = font.deriveFont(10.0f);
		informationTextPane.setFont(font);
		informationTextPane.setText(TextConstants.getText("Timer.MultipleStartsDesc"));
		loopControlPanel.add( informationTextPane , c2);
		c2.gridy++;
		c2.weightx = 0.1;
		
		JCheckBox runsInLoop = new JCheckBox(TextConstants.getText("Timer.RunsInLoop"));
		runsInLoop.setSelected(timerDataControl.isRunsInLoop());
		runsInLoop.addChangeListener(new CheckBoxChangeListener(CheckBoxChangeListener.RUNSINLOOP));
		loopControlPanel.add( runsInLoop , c2);		
		c2.gridy++;
		c2.weightx = 1.0;

		informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setFont(font);
		informationTextPane.setText(TextConstants.getText("Timer.RunsInLoopDesc"));
		loopControlPanel.add( informationTextPane , c2);
		
		loopControlPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Timer.LoopControl" ) ) );
		return loopControlPanel;
	}
	
	/**
	 * Listener for the edit conditions button.
	 */
	private class InitConditionsButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			new ConditionsDialog( timerDataControl.getInitConditions( ) );
		}
	}
	
	/**
	 * Listener for the edit conditions button.
	 */
	private class EndConditionsButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			new ConditionsDialog( timerDataControl.getEndConditions( ) );
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
			new EffectsDialog( timerDataControl.getEffects( ) );
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
			new EffectsDialog( timerDataControl.getPostEffects( ) );
		}
	}
	
	/**
	 * Listener for the text area. It checks the value of the area and updates the documentation.
	 */
	private class DocumentationTextAreaChangesListener implements DocumentListener {
		public void changedUpdate( DocumentEvent arg0 ) {
			// Do nothing
		}

		public void insertUpdate( DocumentEvent arg0 ) {
			timerDataControl.setDocumentation( documentationTextArea.getText( ) );
		}

		public void removeUpdate( DocumentEvent arg0 ) {
			timerDataControl.setDocumentation( documentationTextArea.getText( ) );
		}
	}

	private class DisplayNameChangesListener implements DocumentListener {
		public void changedUpdate( DocumentEvent arg0 ) {
			// Do nothing
		}

		public void insertUpdate( DocumentEvent arg0 ) {
			timerDataControl.setDisplayName( displayName.getText( ) );
		}

		public void removeUpdate( DocumentEvent arg0 ) {
			timerDataControl.setDisplayName( displayName.getText( ) );
		}
	}

	/**
	 * Listener for the time spinner
	 */
	private class TimeSpinnerListener implements ChangeListener {

			public void stateChanged(ChangeEvent e) {
				JSpinner timeSpinner = (JSpinner)e.getSource( );
				SpinnerNumberModel model = (SpinnerNumberModel) timeSpinner.getModel();
				timerDataControl.setTime( model.getNumber( ).longValue( ) );
			}
	}

	/**
	 * A ChangeListener for all the JCheckBox components in the panel
	 */
	private class CheckBoxChangeListener implements ChangeListener {

		public static final int USESENDCONDITION = 0;
		
		public static final int MULTIPLESTARTS = 1;
		
		public static final int RUNSINLOOP = 2;
		
		public static final int SHOW_TIME = 3;

		public static final int COUNTDOWN = 4;
		
		public static final int SHOWWHENSTOPPED = 5;

		private int type;
		
		public CheckBoxChangeListener (int type) {
			this.type = type;
		}
		
		public void stateChanged(ChangeEvent arg0) {
			boolean selected = ((JCheckBox)arg0.getSource()).isSelected();
			if (type == USESENDCONDITION) {
				timerDataControl.setUsesEndCondition(selected);
				conditions2Button.setEnabled(timerDataControl.isUsesEndCondition());
			} else if (type == MULTIPLESTARTS) {
				timerDataControl.setMultipleStarts(selected);
			} else if (type == RUNSINLOOP) {
				timerDataControl.setRunsInLoop(selected);
			} else if (type == SHOW_TIME) {
				timerDataControl.setShowTime(selected);
				if (timerDataControl.isShowTime()) {
					displayName.setEnabled(true);
					countDown.setEnabled(true);
					showWhenStopped.setEnabled(true);
				} else {
					displayName.setEnabled(false);
					countDown.setEnabled(false);
					showWhenStopped.setEnabled(false);
				}
			} else if (type == COUNTDOWN) {
				timerDataControl.setCountDown(selected);
			} else if (type == SHOWWHENSTOPPED) {
				timerDataControl.setShowWhenStopped(selected);
			}
		}
		
	}
	
}
