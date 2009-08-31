/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
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

	private JButton addNewOptionButton;

	private JButton deleteNodeButton;
	
	private JButton deleteLinkButton;

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

		addNewOptionButton = new JButton(TextConstants.getText( "Conversation.OptionAddNewOption"), dialog);
		addNewOptionButton.addActionListener( new AddChildActionListener( ConversationNode.DIALOGUE));
		
		ImageIcon option = new ImageIcon("img/icons/optionNode.png");
		addOptionButton = new JButton(TextConstants.getText( "Conversation.OptionAddOptionNode" ), option);
		addOptionButton.addActionListener( new AddChildActionListener( ConversationNode.OPTION ));

		ImageIcon delete = new ImageIcon("img/icons/deleteNode.png");
		deleteNodeButton = new JButton(TextConstants.getText( "Conversation.OptionDeleteNode" ), delete);
		deleteNodeButton.addActionListener( new DeleteNodeActionListener ());
		
		ImageIcon link = new ImageIcon("img/icons/linkNode.png");
		linkToButton = new JButton( TextConstants.getText( "Conversation.OptionLinkNode" ), link);
		linkToButton.addActionListener( new LinkNodeActionListener());

		ImageIcon deleteLink = new ImageIcon("img/icons/deleteNodeLink.png");
		deleteLinkButton = new JButton( TextConstants.getText( "Conversation.OperationDeleteLink" ), deleteLink);
		deleteLinkButton.addActionListener( new DeleteLinkActionListener());
		
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
		add(deleteLinkButton);
		deleteLinkButton.setEnabled(conversationDataControl.canDeleteLink( conversationPanel.getSelectedNode()));
		setSize(200, 100);
	}

	/**
	 * Removes all elements in the panel,and sets a option node panel
	 */
	public void setOptionPanel( ) {
		removeAll();
		setLayout(new GridLayout(0,1));
		add(editEffectButton);
		add(addNewOptionButton);
		addNewOptionButton.setEnabled( conversationDataControl.canAddChild( conversationPanel.getSelectedNode(), ConversationNode.DIALOGUE ) );
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
			this.linkToButton.setEnabled(conversationDataControl.canLinkNode( conversationPanel.getSelectedNode()));
			this.deleteLinkButton.setEnabled(conversationDataControl.canDeleteLink( conversationPanel.getSelectedNode()));
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
				linkToButton.setEnabled(conversationDataControl.canLinkNode( conversationPanel.getSelectedNode()));
				deleteLinkButton.setEnabled(conversationDataControl.canDeleteLink( conversationPanel.getSelectedNode()));
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
	 * Listener for the "Delete node" option
	 */
	private class DeleteLinkActionListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			if( conversationDataControl.deleteNodeLink( conversationPanel.getSelectedNode( ) ) ) {
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
