/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.editor.control.vignette;

import java.awt.Desktop;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.Random;

import javax.swing.JOptionPane;

import es.eucm.eadventure.common.data.chapter.conversation.GraphConversation;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.DialogueConversationNode;


public class VignetteController {

    private static Random r = new Random();
    
    private static String serviceURL;
    
    public static boolean isInit(){
        return serviceURL!=null;
    }
    
    public static void init(){
        if (!isInit()){
            Properties properties = new Properties();
            try {
                properties.load( new FileInputStream ("vignette.config") );
                serviceURL = properties.getProperty( "service-url" );
            }
            catch( FileNotFoundException e ) {
                e.printStackTrace();
            }
            catch( IOException e ) {
                e.printStackTrace();
            }
        }
    }
    
    public static void exportConversation(VignetteConversationWrapper conversation){
        if (!isInit()){
            init();
        }
        
        JOptionPane.showMessageDialog( null, "Exporting conversation with id \""+conversation.getId( )+"\" and internal eAvdventure name \""+conversation.getConversation( ).getId( )+"\" to Vignette" );
        
        boolean error = false;
        if(Desktop.isDesktopSupported() && isInit()){
          try {
            Desktop.getDesktop().browse(new URI(serviceURL));
          }
            catch( IOException e ) {
                error=true;
                e.printStackTrace();
            }
            catch( URISyntaxException e ) {
                error=true;
                e.printStackTrace();
            }
        }
        
        if (!error && conversation.getId( )==null)
            conversation.setId( ""+r.nextInt( Integer.MAX_VALUE ) );
    }
    
    public static void importConversation(VignetteConversationWrapper conversation){
        JOptionPane.showMessageDialog( null, "Importing conversation with id \""+conversation.getId( )+"\" and internal eAvdventure name \""+conversation.getConversation( ).getId( )+"\" from Vignette" );
        
        ConversationNode root = new DialogueConversationNode( ) ;
        root.addLine( new ConversationLine("Player", "Esto es una prueba") );
        root.addLine( new ConversationLine("Player", "Y aquí va otra prueba") );
        root.addLine( new ConversationLine("Player", "Y aquí la última") );
        GraphConversation graphConversation = new GraphConversation(conversation.getConversation( ).getId( ), conversation.getId( ), root);
        
        conversation.updateConversation( graphConversation );
    }
    
}
