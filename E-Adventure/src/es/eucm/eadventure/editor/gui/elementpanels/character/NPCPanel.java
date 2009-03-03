package es.eucm.eadventure.editor.gui.elementpanels.character;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.common.data.Detailed;
import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.common.loader.Loader;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.character.NPCDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DescriptionChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.DetailedDescriptionChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.auxiliar.components.TextPreviewPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.AnimationPanel;

public class NPCPanel extends JPanel implements Updateable {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the NPC.
	 */
	private NPCDataControl npcDataControl;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	/**
	 * Text color preview panel.
	 */
	private TextPreviewPanel textPreviewPanel;

	/**
	 * Text field for the name.
	 */
	private JTextField nameTextField;

	/**
	 * Text field for the description.
	 */
	private JTextField descriptionTextField;

	/**
	 * Text field for the detailed description.
	 */
	private JTextField detailedDescriptionTextField;
	

	/**
	 * Combo box to select between the existent voices
	 */
	private JComboBox voicesComboBox;
	
	/**
	 * Text field to get the text to try the current voice in the synthesizer
	 */
	private JTextField trySynthesizer;
	
	/**
	 * Check box to set that the conversation lines of the player must be read by synthesizer
	 */
	private JCheckBox alwaysSynthesizer;

	private JTabbedPane tabPanel;

	private JPanel docPanel;

	private LooksPanel looksPanel;
	
	private String[] checkVoices; 

	private JButton bubbleBkgButton;
	
	private JButton bubbleBorderButton;
	
	private JCheckBox showsSpeechBubbles;


	/**
	 * Constructor.
	 * 
	 * @param playerDataControl
	 *            Player controller
	 */
	public NPCPanel( NPCDataControl npcDataControl2 ) {
		// Create the doc panel and layout
		docPanel = new JPanel( );
		docPanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints cDoc = new GridBagConstraints( );

		// Set the controller
		this.npcDataControl = npcDataControl2;

		// Set the layout
		setLayout( new BorderLayout( ) );
		//setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.Title" ) ) );
		//GridBagConstraints c = new GridBagConstraints( );
		cDoc.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		cDoc.fill = GridBagConstraints.HORIZONTAL;
		cDoc.weightx = 1;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( npcDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) npcDataControl.getContent()) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.Documentation" ) ) );
		docPanel.add( documentationPanel, cDoc );

		// Create the field for the text color
		cDoc.gridy = 1;
		JPanel textColorPanel = createTextColorPanel();
		docPanel.add( textColorPanel, cDoc );

		// Create the field for the name
		cDoc.gridy = 2;
		JPanel namePanel = new JPanel( );
		namePanel.setLayout( new GridLayout( ) );
		nameTextField = new JTextField( npcDataControl.getName( ) );
		nameTextField.getDocument().addDocumentListener( new NameChangeListener(nameTextField, (Named) npcDataControl.getContent()));
		namePanel.add( nameTextField );
		namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.Name" ) ) );
		docPanel.add( namePanel, cDoc );

		// Create the field for the brief description
		cDoc.gridy = 3;
		JPanel descriptionPanel = new JPanel( );
		descriptionPanel.setLayout( new GridLayout( ) );
		descriptionTextField = new JTextField( npcDataControl.getBriefDescription( ) );
		descriptionTextField.getDocument().addDocumentListener(new DescriptionChangeListener(descriptionTextField, (Described) npcDataControl.getContent()));
		descriptionPanel.add( descriptionTextField );
		descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.Description" ) ) );
		docPanel.add( descriptionPanel, cDoc );

		// Create the field for the detailed description
		cDoc.gridy = 4;
		JPanel detailedDescriptionPanel = new JPanel( );
		detailedDescriptionPanel.setLayout( new GridLayout( ) );
		detailedDescriptionTextField = new JTextField( npcDataControl.getDetailedDescription( ) );
		detailedDescriptionTextField.getDocument().addDocumentListener(new DetailedDescriptionChangeListener(detailedDescriptionTextField, (Detailed) npcDataControl.getContent()));
		detailedDescriptionPanel.add( detailedDescriptionTextField );
		detailedDescriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.DetailedDescription" ) ) );
		docPanel.add( detailedDescriptionPanel, cDoc );

		// Create the field for voice selection
		cDoc.gridy = 5;
		JPanel voiceSelection = new JPanel();
		voiceSelection.setLayout(new GridLayout(2,2));
				// Create ComboBox for select the voices
		String[] voices = availableVoices();
		// alan voice doesn't work
		String[] usefullVoices = new String[voices.length-1]; 
		int j=0;
		boolean hasAlan=false;
		for (int k =0; k<voices.length;k++){
			if (k<=usefullVoices.length){
				if (!voices[k].equals("alan")){
					usefullVoices[j] = voices[k];
					j++;
				} else {
					hasAlan = true;
				}
			}
		}
		if (!hasAlan){
			voicesComboBox = new JComboBox(voices);
			checkVoices = voices;
		}
		else{ 
			voicesComboBox = new JComboBox(usefullVoices);
			checkVoices = usefullVoices;
		}
		voicesComboBox.addItemListener(new VoiceComboBoxListener());
		if (npcDataControl.getNPCVoice() != null){
			for (int i =1; i<checkVoices.length;i++)
				if (checkVoices[i].equals(npcDataControl.getNPCVoice()))
					voicesComboBox.setSelectedIndex(i);
		}			
		voiceSelection.add(voicesComboBox);
				// Create CheckBox for select if always synthesizer voices
		alwaysSynthesizer = new JCheckBox(TextConstants.getText("Synthesizer.CheckAlways"));
		alwaysSynthesizer.addItemListener(new VoiceCheckVoxListener());
		alwaysSynthesizer.setSelected(npcDataControl.isAlwaysSynthesizer());
		voiceSelection.add(alwaysSynthesizer);
				// Create a TextField to introduce text to try it in the synthesizer
		trySynthesizer = new JTextField();
		voiceSelection.add(trySynthesizer);
				// Create a Button to take the text and try it in the synthesizer
		JButton playText = new JButton(TextConstants.getText("Synthesizer.ButtonPlay"));
		playText.addActionListener(new VoiceButtonListener());
		voiceSelection.add(playText);
		
		TitledBorder border = BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( EtchedBorder.LOWERED ), TextConstants.getText( "Synthesizer.BorderVoices" ), TitledBorder.LEFT, TitledBorder.TOP );
		voiceSelection.setBorder(border);
		docPanel.add(voiceSelection,cDoc);
		
		// Add the tabs
		//Finally, add lookPanel to its scrollPane container, and insert it as a tab along with docPanel
		// Create the panels and layouts
		setLayout( new BorderLayout( ) );
		if( npcDataControl.buildResourcesTab( ) ) {
			tabPanel = new JTabbedPane( );
			looksPanel = new NPCLooksPanel( npcDataControl );
			tabPanel.insertTab( TextConstants.getText( "NPC.LookPanelTitle" ), null, looksPanel, TextConstants.getText( "NPC.LookPanelTip" ), 0 );
			tabPanel.insertTab( TextConstants.getText( "NPC.DocPanelTitle" ), null, docPanel, TextConstants.getText( "NPC.DocPanelTip" ), 1 );
			add( tabPanel, BorderLayout.CENTER );
		} else {
			docPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.Title" ) ) );
			add( docPanel, BorderLayout.CENTER );
		}

	}
	
	/**
	 * Return a string array with names of the available voices for synthesizer text
	 * @return
	 * 			string array with names of the available voices for synthesizer text
	 */
	private String[] availableVoices(){
		VoiceManager voiceManager = VoiceManager.getInstance();
		Voice[] availableVoices = voiceManager.getVoices();
		String[] voiceName = new String[availableVoices.length+1];
		voiceName[0] = TextConstants.getText( "Synthesizer.Empty" );
		for (int i=0; i<availableVoices.length;i++)
			voiceName[i+1] = availableVoices[i].getName();
		return voiceName;
	}
	
	private JPanel createTextColorPanel() {
		JPanel textColorPanel = new JPanel( );
		textColorPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 0.1;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		showsSpeechBubbles = new JCheckBox(TextConstants.getText( "Player.ShowsSpeechBubble" ));
		textColorPanel.add(showsSpeechBubbles, c);
		showsSpeechBubbles.setSelected( npcDataControl.getShowsSpeechBubbles() );
		showsSpeechBubbles.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (showsSpeechBubbles.isSelected() != npcDataControl.getShowsSpeechBubbles()) {
					npcDataControl.setShowsSpeechBubbles(showsSpeechBubbles.isSelected());
					if (bubbleBkgButton != null && bubbleBorderButton != null) {
						bubbleBkgButton.setEnabled(npcDataControl.getShowsSpeechBubbles());
						bubbleBorderButton.setEnabled(npcDataControl.getShowsSpeechBubbles());
					}
					if (textPreviewPanel != null) {
						textPreviewPanel.setShowsSpeechBubbles(showsSpeechBubbles.isSelected());
					}
					
				}
			}
		});
		textPreviewPanel = new TextPreviewPanel( npcDataControl.getTextFrontColor( ), npcDataControl.getTextBorderColor( ) , npcDataControl.getShowsSpeechBubbles(), npcDataControl.getBubbleBkgColor(), npcDataControl.getBubbleBorderColor());
		c.gridy++;
		c.weighty = 1.0;
		c.ipady = 40;
		textColorPanel.add( textPreviewPanel, c );
		JButton frontColorButton = new JButton( TextConstants.getText( "NPC.FrontColor" ) );
		frontColorButton.addActionListener( new ChangeTextColorListener( ChangeTextColorListener.FRONT_COLOR ) );
		c.gridy++;
		c.ipady = 0;
		c.weighty = 0.1;
		textColorPanel.add( frontColorButton,c );
		JButton borderColorButton = new JButton( TextConstants.getText( "NPC.BorderColor" ) );
		borderColorButton.addActionListener( new ChangeTextColorListener( ChangeTextColorListener.BORDER_COLOR ) );
		c.gridy++;
		textColorPanel.add( borderColorButton, c );
		bubbleBkgButton = new JButton( TextConstants.getText( "Player.BubbleBkgColor" ));
		bubbleBkgButton.addActionListener( new ChangeTextColorListener( ChangeTextColorListener.BUBBLEBKG_COLOR ));
		c.gridy++;
		textColorPanel.add(bubbleBkgButton,c);
		bubbleBkgButton.setEnabled(npcDataControl.getShowsSpeechBubbles());
		bubbleBorderButton = new JButton( TextConstants.getText( "Player.BubbleBorderColor" ));
		bubbleBorderButton.addActionListener( new ChangeTextColorListener( ChangeTextColorListener.BUBBLEBORDER_COLOR ));
		c.gridy++;
		textColorPanel.add(bubbleBorderButton, c);
		bubbleBorderButton.setEnabled(npcDataControl.getShowsSpeechBubbles());
		textColorPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPC.TextColor" ) ) );
		return textColorPanel;
	}
	
	/**
	 * Called when the demo synthesizer button has been pressed
	 */
	private class VoiceButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			if (voicesComboBox.getSelectedIndex()!=0 && trySynthesizer.getText()!=null){
				VoiceManager voiceManager = VoiceManager.getInstance();
				Voice voice = voiceManager.getVoice((String)voicesComboBox.getSelectedItem());
				voice.allocate();
				voice.speak(trySynthesizer.getText());
				}
		}
		
	}
	
	
	/**
	 * Called when change the state of checkbox
	 */

	private class VoiceCheckVoxListener implements ItemListener{

		public void itemStateChanged(ItemEvent arg0) {
			npcDataControl.setAlwaysSynthesizer(alwaysSynthesizer.isSelected());
			
		}
		
	}
	
	/**
	 * 
	 * Called when select a field in combo box voiceComboBox
	 *
	 */
	private class VoiceComboBoxListener implements ItemListener{

		public void itemStateChanged(ItemEvent arg0) {
			int selection = voicesComboBox.getSelectedIndex();
			if (selection != 0){
				npcDataControl.setNPCVoice((String)voicesComboBox.getSelectedItem());
			}else 
				npcDataControl.setNPCVoice(new String(""));
		}
		
	}


	/**
	 * Listener for the change color buttons.
	 */
	private class ChangeTextColorListener implements ActionListener {

		/**
		 * Constant for front color.
		 */
		public static final int FRONT_COLOR = 0;

		/**
		 * Constant for border color.
		 */
		public static final int BORDER_COLOR = 1;
		
		public static final int BUBBLEBKG_COLOR = 2;
		
		public static final int BUBBLEBORDER_COLOR = 3;
		
		private int color;

		/**
		 * Color chooser.
		 */
		private JColorChooser colorChooser;

		/**
		 * Text preview panel.
		 */
		private TextPreviewPanel colorPreviewPanel;

		/**
		 * Constructor.
		 * 
		 * @param frontColor
		 *            Whether the front or border color must be changed
		 */
		public ChangeTextColorListener( int color ) {
			this.color = color;

			// Create the color chooser
			colorChooser = new JColorChooser( );

			// Create and add the preview panel, attaching it to the color chooser
			colorPreviewPanel = new TextPreviewPanel( npcDataControl.getTextFrontColor( ), npcDataControl.getTextBorderColor( ), npcDataControl.getShowsSpeechBubbles(), npcDataControl.getBubbleBkgColor(), npcDataControl.getBubbleBorderColor() );
			colorPreviewPanel.setPreferredSize( new Dimension( 400, 40 ) );
			colorPreviewPanel.setBorder( BorderFactory.createEmptyBorder( 1, 1, 1, 1 ) );
			colorChooser.setPreviewPanel( colorPreviewPanel );
			colorChooser.getSelectionModel( ).addChangeListener( new UpdatePreviewPanelListener( ) );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Update the color on the color chooser and the preview panel
			if (color == FRONT_COLOR)
				colorChooser.setColor(npcDataControl.getTextFrontColor());
			else if (color == BORDER_COLOR)
				colorChooser.setColor(npcDataControl.getTextBorderColor());
			else if (color == BUBBLEBKG_COLOR)
				colorChooser.setColor(npcDataControl.getBubbleBkgColor());
			else if (color == BUBBLEBORDER_COLOR)
				colorChooser.setColor(npcDataControl.getBubbleBorderColor());
			
			colorPreviewPanel.setTextFrontColor( npcDataControl.getTextFrontColor( ) );
			colorPreviewPanel.setTextBorderColor( npcDataControl.getTextBorderColor( ) );

			// Create and show the dialog
			JDialog colorDialog = null;
			if (color == FRONT_COLOR)
				colorDialog = JColorChooser.createDialog( null, TextConstants.getText("Player.FrontColor"), true, colorChooser, new UpdateColorListener( ), null );
			else if (color == BORDER_COLOR)
				colorDialog = JColorChooser.createDialog( null, TextConstants.getText("Player.BorderColor"), true, colorChooser, new UpdateColorListener( ), null );
			else if (color == BUBBLEBKG_COLOR)
				colorDialog = JColorChooser.createDialog( null, TextConstants.getText("Player.BubbleBkgColor"), true, colorChooser, new UpdateColorListener( ), null );
			else if (color == BUBBLEBORDER_COLOR)
				colorDialog = JColorChooser.createDialog( null, TextConstants.getText("Player.BubbleBorderColor"), true, colorChooser, new UpdateColorListener( ), null );
			colorDialog.setResizable( false );
			colorDialog.setVisible( true );
		}

		/**
		 * Listener for the "Acept" button of the color chooser dialog.
		 */
		private class UpdateColorListener implements ActionListener {

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed( ActionEvent e ) {
				// Update the text color
				if( color == FRONT_COLOR ) {
					npcDataControl.setTextFrontColor( colorChooser.getColor( ) );
					textPreviewPanel.setTextFrontColor( colorChooser.getColor( ) );
				} else if (color == BORDER_COLOR){
					npcDataControl.setTextBorderColor( colorChooser.getColor( ) );
					textPreviewPanel.setTextBorderColor( colorChooser.getColor( ) );
				} else if (color == BUBBLEBKG_COLOR) {
					npcDataControl.setBubbleBkgColor( colorChooser.getColor() );
					textPreviewPanel.setBubbleBkgColor( colorChooser.getColor() );
				} else if (color == BUBBLEBORDER_COLOR) {
					npcDataControl.setBubbleBorderColor( colorChooser.getColor() );
					textPreviewPanel.setBubbleBorderColor( colorChooser.getColor() );
				}
			}
		}

		/**
		 * Listener for the color preview panel.
		 */
		private class UpdatePreviewPanelListener implements ChangeListener {

			/*
			 * (non-Javadoc)
			 * 
			 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
			 */
			public void stateChanged( ChangeEvent e ) {
				if( color == FRONT_COLOR )
					colorPreviewPanel.setTextFrontColor( colorChooser.getColor( ) );
				else if ( color == BORDER_COLOR )
					colorPreviewPanel.setTextBorderColor( colorChooser.getColor( ) );
				else if ( color == BUBBLEBKG_COLOR )
					colorPreviewPanel.setBubbleBkgColor( colorChooser.getColor() );
				else if ( color == BUBBLEBORDER_COLOR )
					colorPreviewPanel.setBubbleBorderColor( colorChooser.getColor() );
			}
		}
	}

	private class NPCLooksPanel extends LooksPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Preview image panel.
		 */
		private JPanel imagePanel;

		public NPCLooksPanel( DataControlWithResources control ) {
			super( control );
		}

		@Override
		protected void createPreview( ) {
			npcDataControl = (NPCDataControl) this.dataControl;
			
			if (imagePanel != null)
				lookPanel.remove(imagePanel);
			
			imagePanel = createImagePanel();
			
			lookPanel.add( imagePanel, cLook );

			lookPanel.setPreferredSize( new Dimension( 0, 800 ) );
		}

		@Override
		public void updatePreview( ) {
			if (imagePanel != null)
				lookPanel.remove(imagePanel);
			
			imagePanel = createImagePanel();
			
			lookPanel.add( imagePanel, cLook );
			
			lookPanel.repaint();

		}

		private JPanel createImagePanel() {
			imagePanel = new JPanel();
			
			String animationPath1 = null, title1 = null;
			String animationPath2 = null, title2 = null;
			String animationPath3 = null, title3 = null;
			String animationPath4 = null, title4 = null;
			if (this.selectedResourceGroup == LooksPanel.WALKING) {
				imagePanel.setLayout(new GridLayout(1,4));
				title2 = TextConstants.getText( "Resources.DescriptionCharacterAnimationWalkRight" );
				animationPath2 = npcDataControl.getAnimationPath(NPC.RESOURCE_TYPE_WALK_RIGHT);
				title1 = TextConstants.getText( "Resources.DescriptionCharacterAnimationWalkLeft" );
				animationPath1 = npcDataControl.getAnimationPath(NPC.RESOURCE_TYPE_WALK_LEFT);
				title3 = TextConstants.getText( "Resources.DescriptionCharacterAnimationWalkUp" );
				animationPath3 = npcDataControl.getAnimationPath(NPC.RESOURCE_TYPE_WALK_UP);
				title4 = TextConstants.getText( "Resources.DescriptionCharacterAnimationWalkDown" );
				animationPath4 = npcDataControl.getAnimationPath(NPC.RESOURCE_TYPE_WALK_DOWN);
			} else if (this.selectedResourceGroup == LooksPanel.STANDING) {
				imagePanel.setLayout(new GridLayout(1,4));
				title2 = TextConstants.getText( "Resources.DescriptionCharacterAnimationStandRight" );
				animationPath2 = npcDataControl.getAnimationPath(NPC.RESOURCE_TYPE_STAND_RIGHT);
				title1 = TextConstants.getText( "Resources.DescriptionCharacterAnimationStandLeft" );
				animationPath1 = npcDataControl.getAnimationPath(NPC.RESOURCE_TYPE_STAND_LEFT);
				title3 = TextConstants.getText( "Resources.DescriptionCharacterAnimationStandUp" );
				animationPath3 = npcDataControl.getAnimationPath(NPC.RESOURCE_TYPE_STAND_UP);
				title4 = TextConstants.getText( "Resources.DescriptionCharacterAnimationStandDown" );
				animationPath4 = npcDataControl.getAnimationPath(NPC.RESOURCE_TYPE_STAND_DOWN);
			} else if (this.selectedResourceGroup == LooksPanel.TALKING) {
				imagePanel.setLayout(new GridLayout(1,4));
				title2 = TextConstants.getText( "Resources.DescriptionCharacterAnimationSpeakRight" );
				animationPath2 = npcDataControl.getAnimationPath(NPC.RESOURCE_TYPE_SPEAK_RIGHT);
				title1 = TextConstants.getText( "Resources.DescriptionCharacterAnimationSpeakLeft" );
				animationPath1 = npcDataControl.getAnimationPath(NPC.RESOURCE_TYPE_SPEAK_LEFT);
				title3 = TextConstants.getText( "Resources.DescriptionCharacterAnimationSpeakUp" );
				animationPath3 = npcDataControl.getAnimationPath(NPC.RESOURCE_TYPE_SPEAK_UP);
				title4 = TextConstants.getText( "Resources.DescriptionCharacterAnimationSpeakDown" );
				animationPath4 = npcDataControl.getAnimationPath(NPC.RESOURCE_TYPE_SPEAK_DOWN);
			} else if (this.selectedResourceGroup == LooksPanel.USING) {
				imagePanel.setLayout(new GridLayout(1,2));
				title2 = TextConstants.getText( "Resources.DescriptionCharacterAnimationUseRight" );
				animationPath2 = npcDataControl.getAnimationPath(NPC.RESOURCE_TYPE_USE_RIGHT);
				title1 = TextConstants.getText( "Resources.DescriptionCharacterAnimationUseLeft" );
				animationPath1 = npcDataControl.getAnimationPath(NPC.RESOURCE_TYPE_USE_LEFT);
			}
			
			imagePanel.add(createAnimationPanel(animationPath1, title1));
			imagePanel.add(createAnimationPanel(animationPath2, title2));
			if (this.selectedResourceGroup != LooksPanel.USING) {
				imagePanel.add(createAnimationPanel(animationPath3, title3));
				imagePanel.add(createAnimationPanel(animationPath4, title4));
			}
			
			return imagePanel;
		}
		
		private JPanel createAnimationPanel(String animationPath, String title) {
			JPanel temp = new JPanel();
			temp.setLayout(new BorderLayout());
			temp.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), title ) );
			if (animationPath != null) {
				if (animationPath.endsWith(".eaa"))
					temp.add(new AnimationPanel(false, Loader.loadAnimation(AssetsController.getInputStreamCreator(), animationPath)), BorderLayout.CENTER);
				else
					temp.add(new AnimationPanel(false, animationPath + "_01.png" ), BorderLayout.CENTER);
			} else
				temp.add(new AnimationPanel(false, ""), BorderLayout.CENTER);
			
			return temp;
		}

		public void updateResources( ) {
			super.updateResources( );
			getParent( ).getParent( ).repaint( );
		}

	}
	
	@Override
	public boolean updateFields() {
		this.looksPanel.updateResources();
		this.looksPanel.updatePreview();
		this.descriptionTextField.setText(npcDataControl.getBriefDescription());
		this.detailedDescriptionTextField.setText(npcDataControl.getDetailedDescription());
		this.documentationTextArea.setText(npcDataControl.getDocumentation());
		this.nameTextField.setText(npcDataControl.getName());
		this.showsSpeechBubbles.setSelected(npcDataControl.getShowsSpeechBubbles());
		this.bubbleBkgButton.setEnabled(npcDataControl.getShowsSpeechBubbles());
		this.bubbleBorderButton.setEnabled(npcDataControl.getShowsSpeechBubbles());
		this.textPreviewPanel.setBubbleBkgColor(npcDataControl.getBubbleBkgColor());
		this.textPreviewPanel.setBubbleBorderColor(npcDataControl.getBubbleBorderColor());
		this.textPreviewPanel.setTextBorderColor(npcDataControl.getTextBorderColor());
		this.textPreviewPanel.setTextFrontColor(npcDataControl.getTextFrontColor());
		this.textPreviewPanel.setShowsSpeechBubbles(npcDataControl.getShowsSpeechBubbles());
		this.textPreviewPanel.updateUI();
		if (npcDataControl.getNPCVoice() != null){
			for (int i =1; i<checkVoices.length;i++)
				if (checkVoices[i].equals(npcDataControl.getNPCVoice()))
					voicesComboBox.setSelectedIndex(i);
		}
		alwaysSynthesizer.setSelected(npcDataControl.isAlwaysSynthesizer());
		this.repaint();
		return true;
	}
}
