/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.editor.control.tools.conversation;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;


public class AddNamesTagInConversationLines extends Tool {
    
    

    private List<ConversationNode> nodeList;
    
    public AddNamesTagInConversationLines(List<ConversationNode> nodeList){
        this.nodeList = nodeList;
        
    }

    @Override
    public boolean doTool( ) {
        
        for (ConversationNode node: nodeList){
            for (int i=0;i<node.getLineCount( ); i++){
                ConversationLine cl = node.getLine( i );
                String text = cl.getText( );
                if ( !text.contains( "[]" ) ){
                    // look for other marks in text (whisper, etc)
                    if (text.startsWith( "#:*" ) ){
                        text = text.subSequence( 0 , 3 ) + " [] " + text.subSequence( 4 ,text.length( )  );
                    }
                    else  if (text.startsWith( "#O" ) || text.startsWith( "#!" ) )
                        text = text.subSequence( 0 , 2 ) + " [] " + text.subSequence( 3 ,text.length( )  );
                    else
                        text = "[] " + text;
                    
                    cl.setText( text );
                }
                    
            }
        }
        Controller.getInstance().updatePanel();
        
        return true;
    }

    @Override
    public boolean redoTool( ) {

       
        return doTool();
    }

    @Override
    public boolean undoTool( ) {

        for (ConversationNode node: nodeList){
            for (int i=0;i<node.getLineCount( ); i++){
                ConversationLine cl = node.getLine( i );
                String text = cl.getText( );
                if ( text.contains( " [] " ) )
                    cl.setText(text.replace( " [] ", ""));
                if ( text.contains( "[] " ) )
                    cl.setText(text.replace( "[] ", ""));
            }
        }
        
        Controller.getInstance().updatePanel();
        return true;
    }
    
    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return true;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

}
