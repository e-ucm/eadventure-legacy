package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationRuleDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DescriptionChangeListener;
import es.eucm.eadventure.editor.gui.Updateable;

public class AdaptationRulePanel extends JPanel implements Updateable{

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Link to the element reference controller.
	 */
	private AdaptationRuleDataControl adaptationRuleDataControl;

	/**
	 * Text area for the description.
	 */
	private JTextArea descriptionTextArea;
	
	/**
	 * Panel conaining the UOL state panel (LMS-side state)
	 */
	private UOLPropertiesPanel uolPanel;
	
	/**
	 * Panel conaining the game state panel (based on flags/vars)
	 */
	private GameStatePanel gsPanel;
	

	/**
	 * Constructor.
	 * 
	 * @param adpRuleDataControl
	 *            Controller of the element reference
	 *            
	 * @param  scorm12 
	 * 				Show if it is a Scorm 1.2 profile      
	 * 
	 *  @param  scorm2004
	 * 				Show if it is a Scorm 2004 profile      
	 *            
	 */
	public AdaptationRulePanel( AdaptationRuleDataControl adpRuleDataControl, boolean scorm12, boolean scorm2004 ) {

		// Set the controller
		this.adaptationRuleDataControl = adpRuleDataControl;

		// Set the layout
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AdaptationRule.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the combo box of importance
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "AdaptationRule.Information" ) );
		JPanel informationPanel = new JPanel( );
		informationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "GeneralText.Information" ) ) );
		informationPanel.setLayout( new BorderLayout( ) );
		informationPanel.add( informationTextPane, BorderLayout.CENTER );
		add( informationPanel, c );

		// Create the text area for the description
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;c.weightx=1;
		c.weighty = 0.3;

		JPanel conceptPanel = new JPanel( );
		conceptPanel.setLayout( new GridLayout( ) );
		descriptionTextArea = new JTextArea( adpRuleDataControl.getDescription( ), 4, 0 );
		descriptionTextArea.setLineWrap( true );
		descriptionTextArea.setWrapStyleWord( true );
		descriptionTextArea.getDocument( ).addDocumentListener( new DescriptionChangeListener( descriptionTextArea, (Described) adaptationRuleDataControl.getContent() ) );
		conceptPanel.add( new JScrollPane( descriptionTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		conceptPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AdaptationRule.Description" ) ) );
		add( conceptPanel, c );

		// Create the uol-state panel
		uolPanel = new UOLPropertiesPanel( this.adaptationRuleDataControl,scorm12, scorm2004  ); 
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		add( uolPanel, c );

		// Create the game-state panel
		GridBagConstraints c3 = new GridBagConstraints( );
		
		c3.gridx=0;c3.gridy=0;
		c3.insets = new Insets( 5, 5, 5, 5 );
		c3.weightx=1; 
		c3.weighty=0.8; c3.fill=GridBagConstraints.BOTH;
		gsPanel = new GameStatePanel( this.adaptationRuleDataControl ); 
		
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		add( gsPanel, c );
	}

	public boolean updateFields() {
		descriptionTextArea.setText( adaptationRuleDataControl.getDescription( ) );
		return uolPanel.updateFields() && gsPanel.updateFields();
	}
}
