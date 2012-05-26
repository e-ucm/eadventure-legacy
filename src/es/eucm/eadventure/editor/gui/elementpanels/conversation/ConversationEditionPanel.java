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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.SearchableNode;
import es.eucm.eadventure.editor.control.tools.conversation.AddNamesTagInConversationLines;
import es.eucm.eadventure.editor.control.tools.conversation.RemoveNamesTagInConversationLines;
import es.eucm.eadventure.editor.gui.DataControlsPanel;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.displaydialogs.ConversationDialog;

/**
 * This class centralizes all the operations for conversation structures and
 * nodes. It has two panels, a panel to represent the conversation graphically
 * (RepresentationPanel), and a panel to display and edit the content of nodes
 * (LinesPanel). It also has a status bar which informs the user of the status
 * of the application
 */
public class ConversationEditionPanel extends JPanel implements Updateable, DataControlsPanel {

    /**
     * Required
     */
    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_SPLIT = 200;

    /**
     * Scroll pane containing the graphic representation of the tree.
     */
    private JScrollPane scrollPanel;

    /**
     * Panel in which the conversation is graphically representated
     */
    private RepresentationPanel representationPanel;

    /**
     * Panel in which the nodes are visible and editable
     */
    private LinesPanel linesPanel;

    /**
     * Selected node
     */
    private ConversationNodeView selectedNode;

    /**
     * Selected child (always a child of the selected node)
     */
    private ConversationNodeView selectedChild;

    private JButton previewFromNode;

    private ConversationDataControl conversationDataControl;

    private JSplitPane linesSplit;

    /**
     * Constructor.
     * 
     * @param conversationDataControl
     *            Controller of the conversation
     */
    public ConversationEditionPanel( ConversationDataControl cnvDataControl ) {

        selectedNode = null;
        selectedChild = null;
        this.conversationDataControl = cnvDataControl;

        
     // buttons to add and remove the name of character to all conversation lines
        JPanel nameTagPanel = new JPanel(new GridLayout(2,1));
        JButton addNames = new JButton(TC.get( "Conversation.AddNameTag" ));
        addNames.setMaximumSize( new Dimension(10,10) );
       addNames.setToolTipText( TC.get( "Conversation.AddNameTag.Tooltip" ) );
        addNames.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                
               Controller.getInstance().addTool( new AddNamesTagInConversationLines( conversationDataControl.getConversation( ).getAllNodes( )) );
            }
        });
        
        JButton removeNames = new JButton(TC.get( "Conversation.RemoveNameTag" ));
        removeNames.setToolTipText( TC.get( "Conversation.RemoveNameTag.Tooltip" ) );
        removeNames.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {

                Controller.getInstance().addTool( new RemoveNamesTagInConversationLines( conversationDataControl.getConversation( ).getAllNodes( )) );
                
            }
        });
        
        nameTagPanel.add( addNames );
        nameTagPanel.add( removeNames );        
        
        
        // Create the conversation and node panels
        representationPanel = new RepresentationPanel( this, conversationDataControl );
        linesPanel = new LinesPanel( this, conversationDataControl );
        RepresentationZoomPanel zoomPanel = new RepresentationZoomPanel( representationPanel );

        JPanel zoomPreviewPanel = new JPanel( );
        zoomPreviewPanel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.gridx = 0;
        c.gridy = 0;
        zoomPreviewPanel.add( nameTagPanel, c );
        
        c.gridx++;
        zoomPreviewPanel.add( zoomPanel, c );
        
        JButton preview = new JButton( TC.get( "Conversation.OptionPreviewConversation" ) );
        preview.addActionListener( new PreviewConversationActionListener( true ) );
        previewFromNode = new JButton( TC.get( "Conversation.OptionPreviewPartialConversation" ) );
        previewFromNode.addActionListener( new PreviewConversationActionListener( false ) );
        previewFromNode.setEnabled( false );

        c.gridx++;
        zoomPreviewPanel.add( preview, c );
        c.gridx++;
        zoomPreviewPanel.add( previewFromNode, c );

        // Create a new panel, to be placed down, containing the node panel and the status bar
        JPanel downPanel = new JPanel( );
        downPanel.setPreferredSize( new Dimension( 0, 200 ) );

        // Add the node panel and the status bar to the down panel
        downPanel.setLayout( new BorderLayout( ) );
        downPanel.add( linesPanel, BorderLayout.CENTER );

        // Create the scroll panel which contains the conversation panel
        scrollPanel = new JScrollPane( representationPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS );

        // Add the scroll panel (conversation panel) and the down panel (node panel and status bar) to the principal
        // panel
        setLayout( new BorderLayout( ) );
        add( zoomPreviewPanel, BorderLayout.NORTH );

        linesSplit = new JSplitPane( JSplitPane.VERTICAL_SPLIT, scrollPanel, downPanel );
        linesSplit.setDividerLocation( Integer.MAX_VALUE );
        linesSplit.setResizeWeight( 1.0 );
        linesSplit.setDividerSize( 10 );
        add( linesSplit, BorderLayout.CENTER );

        if( conversationDataControl.getRootNode( ).getChildCount( ) == 0 ) {
            this.setSelectedNode( conversationDataControl.getRootNode( ) );
        }
    }

    /**
     * Updates the graphic representation of the conversation panel, and informs
     * the main window that the file has been altered
     */
    public void changePerformedInNode( ) {

        representationPanel.updateRepresentation( );
    }

    /**
     * Reloads the button of the lines panel.
     */
    public void reloadOptions( ) {

        representationPanel.getMenuPanel( ).reloadOptions( );
        linesPanel.reloadOptions( );
    }

    /**
     * Called when the scroll needs to be refreshed
     */
    public void reloadScroll( ) {

        scrollPanel.getViewport( ).revalidate( );
    }

    /**
     * Sets the currently selected node
     * 
     * @param selectedNode
     *            New selected node
     */
    public void setSelectedNode( ConversationNodeView selectedNode ) {

        // Sets the new selected node, and sets an empty selected child
        this.selectedNode = selectedNode;
        this.selectedChild = null;

        if( selectedNode != null )
            linesSplit.setDividerLocation( -DEFAULT_SPLIT );
        else
            linesSplit.setDividerLocation( Integer.MAX_VALUE );
        previewFromNode.setEnabled( selectedNode != null );

        // Inform the node panel that a new node has been selected, and repaint the conversation panel

        linesPanel.newSelectedNode( );
        representationPanel.repaint( );
        revalidate( );
    }

    /**
     * Returns a reference to the selected node
     * 
     * @return The view node currently selected
     */
    public ConversationNodeView getSelectedNode( ) {

        return selectedNode;
    }

    /**
     * Sets the currently selected child (always a child of the selected node)
     * 
     * @param selectedChild
     *            New selected child
     */
    public void setSelectedChild( ConversationNodeView selectedChild ) {

        // Set selected child and repaint the conversation panel
        this.selectedChild = selectedChild;
        representationPanel.repaint( );
    }

    /**
     * Returns a reference to the selected child (always a child of the selected
     * node)
     * 
     * @return The child view node currently selected
     */
    public ConversationNodeView getSelectedChild( ) {

        return selectedChild;
    }

    public void changeScrollX( int value ) {

        int oldValue = scrollPanel.getHorizontalScrollBar( ).getValue( );
        int maxValue = scrollPanel.getHorizontalScrollBar( ).getMaximum( );
        value = oldValue + value;
        if( value > maxValue )
            value = maxValue;
        if( value < 0 )
            value = 0;
        scrollPanel.getHorizontalScrollBar( ).setValue( value );
        reloadScroll( );
    }

    public void changeScrollY( int value ) {

        int oldValue = scrollPanel.getVerticalScrollBar( ).getValue( );
        int maxValue = scrollPanel.getVerticalScrollBar( ).getMaximum( );
        value = oldValue + value;
        if( value > maxValue )
            value = maxValue;
        if( value < 0 )
            value = 0;
        scrollPanel.getVerticalScrollBar( ).setValue( value );
        reloadScroll( );
    }

    public Dimension getScrollSize( ) {

        return scrollPanel.getSize( );
    }

    public int getScrollXValue( ) {

        return scrollPanel.getHorizontalScrollBar( ).getValue( );
    }

    public int getScrollYValue( ) {

        return scrollPanel.getVerticalScrollBar( ).getValue( );
    }

    public void changeScale( float scale ) {

    }

    public boolean updateFields( ) {

        if( getSelectedNode( ) != null && !conversationDataControl.getAllNodes( ).contains( getSelectedNode( ) ) ) {
            setSelectedNode( null );
            setSelectedChild( null );
        }
        else if( getSelectedChild( ) != null && !conversationDataControl.getAllNodes( ).contains( getSelectedChild( ) ) ) {
            setSelectedChild( null );
        }
        else {
            setSelectedNode( getSelectedNode( ) );
            setSelectedChild( getSelectedChild( ) );
        }
        representationPanel.updateRepresentation( );
        representationPanel.repaint( );
        return true;
    }

    public void updateRepresentation( ) {

        representationPanel.updateRepresentation( );
    }

    public MenuPanel getMenuPanel( ) {

        return representationPanel.getMenuPanel( );
    }

    public void changeState( int state ) {

        representationPanel.changeState( state );
    }

    public int getState( ) {

        return representationPanel.getState( );
    }

    /**
     * Listener for the "Preview conversation" and "Preview conversation from
     * this node" options
     */
    private class PreviewConversationActionListener implements ActionListener {

        /**
         * True if the conversation must be played from the root node, false
         * from the selected node.
         */
        private boolean completePreview;

        /**
         * Constructor.
         * 
         * @param completePreview
         *            True if the conversation must be played from the root
         *            node, false from the selected node.
         */
        public PreviewConversationActionListener( boolean completePreview ) {

            this.completePreview = completePreview;
        }

        public void actionPerformed( ActionEvent e ) {

            // If it is a complete preview, show the dialog for the root node
            if( completePreview )
                new ConversationDialog( conversationDataControl, conversationDataControl.getRootNode( ) );

            // If not, take the selected node
            else
                new ConversationDialog( conversationDataControl, getSelectedNode( ) );
        }
    }

    public void setSelectedItem( List<Searchable> path ) {

        if( path.size( ) > 0 && path.get( path.size( ) - 1 ) instanceof SearchableNode ) {
            this.setSelectedNode( ( (SearchableNode) path.get( path.size( ) - 1 ) ).getConversationNodeView( ) );
            path.remove( path.size( ) - 1 );
            if( linesPanel != null ) {
                linesPanel.setSelectedItem( path );
            }
        }
    }

}
