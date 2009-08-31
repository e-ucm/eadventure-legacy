/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.control.config;

import java.awt.Point;

import es.eucm.eadventure.common.data.chapter.conversation.GraphConversation;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ChapterDataControl;

public class ConversationConfigData implements ProjectConfigDataConsumer {

    private static Controller controller = Controller.getInstance( );

    private static boolean checkParameters( String convId, int nodeIndex ) {

        boolean parametersOk = false;

        // If the id is a conversation, retrieve the conversation data
        if( controller.getIdentifierSummary( ).isConversation( convId ) ) {
            ConversationDataControl conversation = null;
            for( ConversationDataControl conversationDataControl : controller.getSelectedChapterDataControl( ).getConversationsList( ).getConversations( ) ) {
                if( conversationDataControl.getId( ).equals( convId ) ) {
                    // Only graph conversation can be configured
                    if( conversationDataControl.getType( ) == Controller.CONVERSATION_GRAPH ) {
                        conversation = conversationDataControl;
                        break;
                    }
                }
            }

            // If the conversation was found and not tree-shaped
            if( conversation != null ) {
                // Check the node index is right 
                GraphConversation graphConversation = (GraphConversation) conversation.getContent( );
                if( nodeIndex >= 0 && nodeIndex < graphConversation.getAllNodes( ).size( ) ) {
                    parametersOk = true;
                }
            }
        }
        return parametersOk;
    }

    private static String getXKey( String convId, int nodeIndex ) {

        int chapter = controller.getSelectedChapter( );
        return "Chapter" + chapter + "." + convId + "." + nodeIndex + ".X";
    }

    private static String getYKey( String convId, int nodeIndex ) {

        int chapter = controller.getSelectedChapter( );
        return "Chapter" + chapter + "." + convId + "." + nodeIndex + ".Y";
    }

    public static boolean isNodeConfig( String convId, int nodeIndex ) {

        boolean config = false;
        // Check input arguments are OK
        if( checkParameters( convId, nodeIndex ) ) {
            String keyX = getXKey( convId, nodeIndex );
            String keyY = getYKey( convId, nodeIndex );
            // If both X and Y are in the config file 
            if( ProjectConfigData.existsKey( keyX ) && ProjectConfigData.existsKey( keyY ) ) {
                config = true;
            }
        }
        return config;
    }

    public static int getNodeX( String convId, int nodeIndex ) {

        int X = Integer.MIN_VALUE;

        if( isNodeConfig( convId, nodeIndex ) ) {
            try {
                String keyX = getXKey( convId, nodeIndex );
                X = Integer.parseInt( ProjectConfigData.getProperty( keyX ) );
            }
            catch( Exception e ) {
                X = Integer.MIN_VALUE;
            }
        }
        return X;
    }

    public static int getNodeY( String convId, int nodeIndex ) {

        int Y = Integer.MIN_VALUE;

        if( isNodeConfig( convId, nodeIndex ) ) {
            try {
                String keyY = getYKey( convId, nodeIndex );
                Y = Integer.parseInt( ProjectConfigData.getProperty( keyY ) );
            }
            catch( Exception e ) {
                Y = Integer.MIN_VALUE;
            }
        }
        return Y;
    }

    public static Point getNodePos( String convId, int nodeIndex ) {

        Point point = new Point( Integer.MIN_VALUE, Integer.MIN_VALUE );
        if( isNodeConfig( convId, nodeIndex ) ) {
            point = new Point( getNodeX( convId, nodeIndex ), getNodeY( convId, nodeIndex ) );
        }
        return point;
    }

    public static void setNodeX( String convId, int nodeIndex, int X ) {

        if( X != Integer.MIN_VALUE ) {
            String keyX = getXKey( convId, nodeIndex );
            String xValue = Integer.toString( X );
            ProjectConfigData.setProperty( keyX, xValue );
        }
    }

    public static void setNodeY( String convId, int nodeIndex, int Y ) {

        if( Y != Integer.MIN_VALUE ) {
            String keyY = getYKey( convId, nodeIndex );
            String yValue = Integer.toString( Y );
            ProjectConfigData.setProperty( keyY, yValue );
        }
    }

    public static void setNodePos( String convId, int nodeIndex, Point point ) {

        setNodeX( convId, nodeIndex, point.x );
        setNodeY( convId, nodeIndex, point.y );
    }

    public static boolean isConversationConfig( String convId ) {

        boolean isConfig = false;

        // Get the controller
        Controller controller = Controller.getInstance( );

        // Get current ChapterData
        ChapterDataControl chapterDataControl = controller.getSelectedChapterDataControl( );

        // Find the conversation
        for( ConversationDataControl conversationDataControl : chapterDataControl.getConversationsList( ).getConversations( ) ) {
            if( conversationDataControl.getId( ).equals( convId ) && conversationDataControl.getType( ) == Controller.CONVERSATION_GRAPH ) {
                // Check that all the nodes are in config file
                isConfig = true;
                GraphConversation graphConv = (GraphConversation) ( conversationDataControl.getContent( ) );
                for( int i = 0; i < graphConv.getAllNodes( ).size( ); i++ ) {
                    isConfig &= isNodeConfig( convId, i );
                }
            }
        }

        return isConfig;
    }

    public void updateData( ) {

    }

}
