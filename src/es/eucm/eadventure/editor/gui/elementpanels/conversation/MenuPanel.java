package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.GraphConversationDataControl;

public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 5188735838478736939L;

	/**
	 * "Edit effect" button
	 */
	private JButton editEffectButton;
	
	private JButton addOptionButton;
	
	private JButton addDialogButton;
	
	private JButton deleteNodeButton;

	private JButton linkToButton;
	
	private ConversationDataControl conversationDataControl;

	private ConversationEditionPanel conversationPanel;
	
	public MenuPanel(ConversationDataControl conversationDataControl, ConversationEditionPanel conversationPanel2) {
		super();
		this.conversationDataControl = conversationDataControl;
		this.conversationPanel = conversationPanel2;
		this.setOpaque(false);
		this.setVisible(false);
		
		// Add the add operations
		ImageIcon dialog = new ImageIcon("img/icons/dialogNode.png");
		addDialogButton = new JButton(TextConstants.getText( "Conversation.OptionAddDialogueNode" ), dialog);
		addDialogButton.addActionListener( new AddChildActionListener( ConversationNode.DIALOGUE ) );

		ImageIcon option = new ImageIcon("img/icons/optionNode.png");
		addOptionButton = new JButton(TextConstants.getText( "Conversation.OptionAddOptionNode" ), option);
		addOptionButton.addActionListener( new AddChildActionListener( ConversationNode.OPTION ));

		ImageIcon delete = new ImageIcon("img/icons/deleteNode.png");
		deleteNodeButton = new JButton(TextConstants.getText( "Conversation.OptionDeleteNode" ), delete);
		deleteNodeButton.addActionListener( new DeleteNodeActionListener ());
		
		ImageIcon link = new ImageIcon("img/icons/linkNode.png");
		linkToButton = new JButton( TextConstants.getText( "Conversation.OptionLinkNode" ), link);
		linkToButton.addActionListener( new LinkNodeActionListener());

		editEffectButton = new JButton( TextConstants.getText( "Conversations.EditEffect" ) );
		editEffectButton.addActionListener( new ListenerButtonEditEffect( ) );

	}
	
	/**
	 * Removes all elements in the panel, and sets a dialogue node panel
	 */
	public void setDialoguePanel( ) {
		removeAll();
		setLayout(new GridLayout(0,1));
		editEffectButton.setEnabled( true );
		add(editEffectButton);
		add(addDialogButton);
		addDialogButton.setEnabled( conversationDataControl.canAddChild( conversationPanel.getSelectedNode(), ConversationNode.DIALOGUE ) );
		add(addOptionButton);
		addOptionButton.setEnabled( conversationDataControl.canAddChild( conversationPanel.getSelectedNode(), ConversationNode.OPTION ) );
		setVisible(true);
		add(deleteNodeButton);
		deleteNodeButton.setEnabled( conversationDataControl.canDeleteNode( conversationPanel.getSelectedNode() ));
		add(linkToButton);
		linkToButton.setEnabled(conversationDataControl.canLinkNode( conversationPanel.getSelectedNode() ));
		setSize(200, 100);
	}

	/**
	 * Removes all elements in the panel,and sets a option node panel
	 */
	public void setOptionPanel( ) {
		removeAll();
		setLayout(new GridLayout(0,1));
		add(editEffectButton);
		add(addDialogButton);
		addDialogButton.setEnabled( conversationDataControl.canAddChild( conversationPanel.getSelectedNode(), ConversationNode.DIALOGUE ) );
		add(deleteNodeButton);
		deleteNodeButton.setEnabled( conversationDataControl.canDeleteNode( conversationPanel.getSelectedNode() ));
		add(linkToButton);
		linkToButton.setEnabled(conversationDataControl.canLinkNode( conversationPanel.getSelectedNode() ));
		setSize(200, 80);
	}

	public void reloadOptions( ) {
		ConversationNodeView selectedNode = conversationPanel.getSelectedNode( );

		if( selectedNode.getType( ) == ConversationNodeView.DIALOGUE ) {
			editEffectButton.setEnabled( true );
			this.addDialogButton.setEnabled(conversationDataControl.canAddChild( conversationPanel.getSelectedNode(), ConversationNode.DIALOGUE ));
			this.addOptionButton.setEnabled(conversationDataControl.canAddChild( conversationPanel.getSelectedNode(), ConversationNode.OPTION ));
		}
	}

	/**
	 * Listener for the "Edit effect" button
	 */
	private class ListenerButtonEditEffect implements ActionListener {

		public void actionPerformed( ActionEvent e ) {
			ConversationNodeView selectedNode = conversationPanel.getSelectedNode( );
			conversationDataControl.editNodeEffects( selectedNode );
			conversationPanel.repaint( );
		}
	}

	/**
	 * Listener for the "Add child" option
	 */
	private class AddChildActionListener implements ActionListener {

		/**
		 * Type of node that this listener adds.
		 */
		private int nodeType;

		/**
		 * Constructor.
		 * 
		 * @param nodeType
		 *            Type of the node to be added
		 */
		public AddChildActionListener( int nodeType ) {
			this.nodeType = nodeType;
		}

		public void actionPerformed( ActionEvent e ) {
			if( conversationDataControl.addChild( conversationPanel.getSelectedNode( ), nodeType ,((GraphConversationDataControl)conversationDataControl).getAllConditions())){
				conversationPanel.changeState( RepresentationPanel.NORMAL );
				reloadOptions( );
				conversationPanel.updateRepresentation( );
				addDialogButton.setEnabled( conversationDataControl.canAddChild( conversationPanel.getSelectedNode(), ConversationNode.DIALOGUE ) );
				addOptionButton.setEnabled( conversationDataControl.canAddChild( conversationPanel.getSelectedNode(), ConversationNode.OPTION ) );
			}
		}
	}

	/**
	 * Listener for the "Delete node" option
	 */
	private class DeleteNodeActionListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			if( conversationDataControl.deleteNode( conversationPanel.getSelectedNode( ) ) ) {
				conversationPanel.setSelectedNode( null );
				conversationPanel.changeState( RepresentationPanel.NORMAL );
				conversationPanel.updateRepresentation( );
			}
		}
	}

	/**
	 * Listener for the "Add link to..." option
	 */
	private class LinkNodeActionListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			if( conversationPanel.getState() == RepresentationPanel.NORMAL )
				conversationPanel.changeState( RepresentationPanel.WAITING_SECOND_NODE_TO_LINK );
			conversationPanel.updateRepresentation();
		}
	}

	@Override
	public void paint(Graphics g) {
		if (this.getMousePosition() == null) {
			this.setOpaque(false);
			AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
			((Graphics2D) g).setComposite(alphaComposite);
			super.paint(g);
		} else {
			this.setOpaque(true);
			super.paint(g);
		}
	}
	
	
	
}
