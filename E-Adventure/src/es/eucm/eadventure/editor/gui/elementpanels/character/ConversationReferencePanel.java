package es.eucm.eadventure.editor.gui.elementpanels.character;

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
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.character.ConversationReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationsListDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;
import es.eucm.eadventure.editor.gui.treepanel.TreeNodeControl;

public class ConversationReferencePanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Link to the conversation reference controller.
	 */
	private ConversationReferenceDataControl conversationReferenceDataControl;

	/**
	 * Combo box for the conversations in the script.
	 */
	private JComboBox conversationsComboBox;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	/**
	 * Constructor.
	 * 
	 * @param conversationReferenceDataControl
	 *            Controller of the conversation reference
	 */
	public ConversationReferencePanel( ConversationReferenceDataControl conversationReferenceDataControl ) {

		// Set the controller
		this.conversationReferenceDataControl = conversationReferenceDataControl;

		// Set the layout
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ConversationReference.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the combo box of characters
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JPanel namePanel = new JPanel( );
		namePanel.setLayout( new GridBagLayout() );
		
		GridBagConstraints c2 = new GridBagConstraints();
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.gridx = 0;
		c2.gridy = 0;
		c2.weightx = 1.0;
		conversationsComboBox = new JComboBox( Controller.getInstance( ).getIdentifierSummary( ).getConversationsIds( ) );
		conversationsComboBox.setSelectedItem( conversationReferenceDataControl.getIdTarget( ) );
		conversationsComboBox.addActionListener( new ConversationComboBoxListener( ) );
		namePanel.add( conversationsComboBox , c2);
		
		Icon goToIcon = new ImageIcon( "img/icons/moveNodeRight.png" );
		JButton goToButton = new JButton (goToIcon);
		goToButton.setPreferredSize(new Dimension(20,20));
		goToButton.setToolTipText(TextConstants.getText("ConversationReference.GoToReferencedConversation"));
		goToButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConversationsListDataControl cldc = Controller.getInstance().getSelectedChapterDataControl().getConversationsList();
				for (ConversationDataControl cdc : cldc.getConversations()) {
					if (cdc.getId().equals(ConversationReferencePanel.this.conversationReferenceDataControl.getIdTarget())) {
						TreeNodeControl.getInstance().changeTreeNode(cdc);
					}
				}
			}
		});
		c2.gridx = 1;
		c2.weightx = 0.1;
		namePanel.add( goToButton, c2);
		
		namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ConversationReference.ConversationId" ) ) );
		add( namePanel, c );

		// Create the text area for the documentation
		c.gridy = 1;
		JPanel descriptionPanel = new JPanel( );
		descriptionPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( conversationReferenceDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) conversationReferenceDataControl.getContent() ) );
		descriptionPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ConversationReference.Documentation" ) ) );
		add( descriptionPanel, c );

		// Create the button for the conditions
		c.gridy = 2;
		JPanel conditionsPanel = new JPanel( );
		conditionsPanel.setLayout( new GridLayout( ) );
		JButton conditionsButton = new JButton( TextConstants.getText( "GeneralText.EditConditions" ) );
		conditionsButton.addActionListener( new ConditionsButtonListener( ) );
		conditionsPanel.add( conditionsButton );
		conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ConversationReference.Conditions" ) ) );
		add( conditionsPanel, c );

		// Add a filler at the end
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		add( new JFiller( ), c );
	}

	/**
	 * Listener for the characters combo box.
	 */
	private class ConversationComboBoxListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			conversationReferenceDataControl.setIdTarget( conversationsComboBox.getSelectedItem( ).toString( ) );
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
			new ConditionsDialog( conversationReferenceDataControl.getConditions( ) );
		}
	}
}
