/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TC;
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

    private BufferedImage tempImage;
    
    public MenuPanel( ConversationDataControl conversationDataControl, ConversationEditionPanel conversationPanel2 ) {

        super( );
        this.conversationDataControl = conversationDataControl;
        this.conversationPanel = conversationPanel2;
        this.setOpaque( false );
        this.setVisible( false );

        // Add the add operations
        ImageIcon dialog = new ImageIcon( "img/icons/dialogNode.png" );
        addDialogButton = new JButton( TC.get( "Conversation.OptionAddDialogueNode" ), dialog );
        addDialogButton.addActionListener( new AddChildActionListener( ConversationNodeView.DIALOGUE ) );

        addNewOptionButton = new JButton( TC.get( "Conversation.OptionAddNewOption" ), dialog );
        addNewOptionButton.addActionListener( new AddChildActionListener( ConversationNodeView.DIALOGUE ) );

        ImageIcon option = new ImageIcon( "img/icons/optionNode.png" );
        addOptionButton = new JButton( TC.get( "Conversation.OptionAddOptionNode" ), option );
        addOptionButton.addActionListener( new AddChildActionListener( ConversationNodeView.OPTION ) );

        ImageIcon delete = new ImageIcon( "img/icons/deleteNode.png" );
        deleteNodeButton = new JButton( TC.get( "Conversation.OptionDeleteNode" ), delete );
        deleteNodeButton.addActionListener( new DeleteNodeActionListener( ) );

        ImageIcon link = new ImageIcon( "img/icons/linkNode.png" );
        linkToButton = new JButton( TC.get( "Conversation.OptionLinkNode" ), link );
        linkToButton.addActionListener( new LinkNodeActionListener( ) );

        ImageIcon deleteLink = new ImageIcon( "img/icons/deleteNodeLink.png" );
        deleteLinkButton = new JButton( TC.get( "Conversation.OperationDeleteLink" ), deleteLink );
        deleteLinkButton.addActionListener( new DeleteLinkActionListener( ) );

        editEffectButton = new JButton( TC.get( "Conversations.EditEffect" ) );
        editEffectButton.addActionListener( new ListenerButtonEditEffect( ) );

    }

    /**
     * Removes all elements in the panel, and sets a dialogue node panel
     */
    public void setDialoguePanel( ) {

        removeAll( );
        setLayout( new GridLayout( 0, 1 ) );
        editEffectButton.setEnabled( true );
        add( editEffectButton );
        add( addDialogButton );
        addDialogButton.setEnabled( conversationDataControl.canAddChild( conversationPanel.getSelectedNode( ), ConversationNodeView.DIALOGUE ) );
        add( addOptionButton );
        addOptionButton.setEnabled( conversationDataControl.canAddChild( conversationPanel.getSelectedNode( ), ConversationNodeView.OPTION ) );
        setVisible( true );
        add( deleteNodeButton );
        deleteNodeButton.setEnabled( conversationDataControl.canDeleteNode( conversationPanel.getSelectedNode( ) ) );
        add( linkToButton );
        linkToButton.setEnabled( conversationDataControl.canLinkNode( conversationPanel.getSelectedNode( ) ) );
        add( deleteLinkButton );
        deleteLinkButton.setEnabled( conversationDataControl.canDeleteLink( conversationPanel.getSelectedNode( ) ) );
        setSize( 200, 100 );
    }

    /**
     * Removes all elements in the panel,and sets a option node panel
     */
    public void setOptionPanel( ) {

        removeAll( );
        setLayout( new GridLayout( 0, 1 ) );
        add( editEffectButton );
        add( addNewOptionButton );
        addNewOptionButton.setEnabled( conversationDataControl.canAddChild( conversationPanel.getSelectedNode( ), ConversationNodeView.DIALOGUE ) );
        add( deleteNodeButton );
        deleteNodeButton.setEnabled( conversationDataControl.canDeleteNode( conversationPanel.getSelectedNode( ) ) );
        add( linkToButton );
        linkToButton.setEnabled( conversationDataControl.canLinkNode( conversationPanel.getSelectedNode( ) ) );
        setSize( 200, 80 );
    }

    public void reloadOptions( ) {

        ConversationNodeView selectedNode = conversationPanel.getSelectedNode( );

        if( selectedNode.getType( ) == ConversationNodeView.DIALOGUE ) {
            editEffectButton.setEnabled( true );
            this.addDialogButton.setEnabled( conversationDataControl.canAddChild( conversationPanel.getSelectedNode( ), ConversationNodeView.DIALOGUE ) );
            this.addOptionButton.setEnabled( conversationDataControl.canAddChild( conversationPanel.getSelectedNode( ), ConversationNodeView.OPTION ) );
            this.linkToButton.setEnabled( conversationDataControl.canLinkNode( conversationPanel.getSelectedNode( ) ) );
            this.deleteLinkButton.setEnabled( conversationDataControl.canDeleteLink( conversationPanel.getSelectedNode( ) ) );
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

            if( conversationDataControl.addChild( conversationPanel.getSelectedNode( ), nodeType, ( (GraphConversationDataControl) conversationDataControl ).getAllConditions( ) ) ) {
                conversationPanel.changeState( RepresentationPanel.NORMAL );
                reloadOptions( );
                conversationPanel.updateRepresentation( );
                addDialogButton.setEnabled( conversationDataControl.canAddChild( conversationPanel.getSelectedNode( ), ConversationNodeView.DIALOGUE ) );
                addOptionButton.setEnabled( conversationDataControl.canAddChild( conversationPanel.getSelectedNode( ), ConversationNodeView.OPTION ) );
                linkToButton.setEnabled( conversationDataControl.canLinkNode( conversationPanel.getSelectedNode( ) ) );
                deleteLinkButton.setEnabled( conversationDataControl.canDeleteLink( conversationPanel.getSelectedNode( ) ) );
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

            if( conversationPanel.getState( ) == RepresentationPanel.NORMAL )
                conversationPanel.changeState( RepresentationPanel.WAITING_SECOND_NODE_TO_LINK );
            conversationPanel.updateRepresentation( );
        }
    }

    @Override
    public void paint( Graphics g2 ) {
        if( this.getMousePosition( ) == null && tempImage == null) {
            tempImage = new BufferedImage(this.getWidth( ), this.getHeight( ), BufferedImage.TYPE_4BYTE_ABGR);
            Graphics g = tempImage.getGraphics( );
            AlphaComposite alphaComposite = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.3f );
            ( (Graphics2D) g ).setComposite( alphaComposite );
            this.paintComponents( g );
        }
        
        if (this.getMousePosition( ) == null && tempImage!= null)
            g2.drawImage( tempImage, 0, 0, null );
        else
            super.paint( g2 );
    }

}
