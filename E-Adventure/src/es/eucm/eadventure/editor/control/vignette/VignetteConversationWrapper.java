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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import es.eucm.eadventure.common.data.chapter.conversation.Conversation;
import es.eucm.eadventure.common.data.chapter.conversation.GraphConversation;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.character.NPCDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.GraphConversationDataControl;


public class VignetteConversationWrapper {

    private static ArrayList<Color> backgroundColors;
    private static Iterator<Color> iterator;
    
    private static Color getNextColor(){
        if (iterator.hasNext( ))
            return iterator.next( );
        else{
            iterator=backgroundColors.iterator( );
            return iterator.next( );
        }
    }
    
    static{
        backgroundColors = new ArrayList<Color>();
        Properties properties = new Properties();
        try {
            properties.load( new FileInputStream ("vignette.config") );
            String colors = properties.getProperty( "background-colors" );
            if (colors!=null){
                StringTokenizer st = new StringTokenizer(colors, " ", false);
                while (st.hasMoreTokens( )){
                    String colorStr = st.nextToken( );
                    Color color = fromHexToColor( colorStr );
                    if (color!=null){
                        backgroundColors.add( color );
                    }
                }
            }
            
            if (backgroundColors.size()<4){
                backgroundColors.add( Color.white );
                backgroundColors.add( Color.gray );
                backgroundColors.add( Color.red );
                backgroundColors.add( Color.yellow );
                backgroundColors.add( Color.blue );
            }
            
            iterator = backgroundColors.iterator( );
        }
        catch( FileNotFoundException e ) {
            e.printStackTrace();
        }
        catch( IOException e ) {
            e.printStackTrace();
        }
    }
    
    private ConversationDataControl dataControl;
    
    private GraphConversation conversation;
    
    private VignetteUICallback callback;
    
    /**
     * Converts a String following the pattern #HEX_COLOR to java.awt.Color.
     * The string should start with "#". HEX_COLOR must be a valid hex 6 or 8 digits-length
     * hexadecimal value. It will be interpreted as RGB or RGBA depending on the length.
     * @param hex
     * @return
     */
    public static Color fromHexToColor( String hex ){
        Color color=null;
        if (hex!=null && hex.startsWith( "#" ) && hex.length( )>=7){
            int red = Integer.parseInt( hex.substring( 1,3 ), 16 );
            int green = Integer.parseInt( hex.substring( 3,5 ), 16 );
            int blue = Integer.parseInt( hex.substring( 5,7 ), 16 );
            if (hex.length( )>=9){
                int alpha = Integer.parseInt( hex.substring( 7,9 ), 16 );
                color = new Color(red, green, blue, alpha);
            } else {
                color = new Color(red, green, blue);
            }
        }
        return color;
    }
    
    public VignetteConversationWrapper (ConversationDataControl dataControl){
        this.dataControl = dataControl;
        this.conversation = (GraphConversation)dataControl.getConversation( );
    }

    
    public ConversationDataControl getDataControl( ) {
    
        return dataControl;
    }

    
    public void setDataControl( ConversationDataControl dataControl ) {
    
        this.dataControl = dataControl;
    }

    
    /**
     * Returns the graph that represents this conversation.
     * VignetteController should use this method when exporting the conversation from eAdventure to Vignette.
     * @return
     */
    public GraphConversation getConversation( ) {
    
        return conversation;
    }

    
    /**
     * This method updates the conversation with the one given as a parameter.
     * VignetteController should invoke this method when importing a conversation from Vignette to eAdventure.
     * 
     * Note: this method makes sure the UI gets updated, if necessary
     * @param newConversation
     */
    public void updateConversation( GraphConversation newConversation ) {
    
        // Replace old conversation with the new one
        for (int i=0; i<Controller.getInstance( ).getSelectedChapterDataControl( ).getConversationsList( ).getConversations( ).size( );i++){
            ConversationDataControl gdc=Controller.getInstance( ).getSelectedChapterDataControl( ).getConversationsList( ).getConversations().get( i );
            if (gdc.getId( ).equals( this.conversation.getId( ))){
                this.conversation = newConversation;
                this.dataControl = new GraphConversationDataControl(newConversation);
                Controller.getInstance( ).getSelectedChapterDataControl( ).getConversationsList( ).getConversations().remove( i );
                Controller.getInstance( ).getSelectedChapterDataControl( ).getConversationsList( ).getConversations().add( i, this.dataControl );
                
                List<Conversation> conversations = (List<Conversation>)(Controller.getInstance( ).getSelectedChapterDataControl( ).getConversationsList( ).getContent( ));
                conversations.remove( i );
                conversations.add( i, this.conversation );
                
                this.callback.updateConversation( this.dataControl );
                break; 
            }
        }
        
    }

    /**
     * Returns the id that vignette has previously given to this conversation.
     * If Vignette has not been used yet to edit the conversation, then this id is null.
     * @return
     */
    public String getId( ) {
    
        return conversation.getVignetteId( );
    }

    
    /**
     * This method should be invoked only by VignetteController, ideally only once. It updates
     * the vignette Id associated to this conversation, which will be used to go back and forth
     * from eAdventure to Vignette.
     * 
     * Note: This method makes sure the UI gets updated if necessary.
     * @param id
     */
    public void setId( String id ) {
        conversation.setVignetteId( id );
        if (conversation.getVignetteId( )!=null){
            if (this.callback!=null){
                callback.updatePermission( VignetteUICallback.OPERATION_IMPORT, true );
            }
        }
    }

    public boolean getPermission(int operation){
        if (operation == VignetteUICallback.OPERATION_IMPORT){
            return conversation.getVignetteId( )!=null;
        }else if (operation == VignetteUICallback.OPERATION_EXPORT){
            return true;
        }
        return false;
    }
    
    public void exportToVignette(){
        VignetteController.exportConversation( this );
        /*List<VignetteCharacterPreview> characters= this.getCharacters( 48, 48 );
        BufferedImage allPreviews = new BufferedImage ( 48*characters.size( ), 48, BufferedImage.TRANSLUCENT);
        int x=0;
        Graphics2D g = allPreviews.createGraphics( );
        for (VignetteCharacterPreview preview:characters){
            g.drawImage( preview.getImage( ), x, 0, null );
            System.out.println(preview.getName( ) );
            x+=48;
        }
        g.dispose( );
        try {
            File temp=File.createTempFile( "vignette", ".png" );
            ImageIO.write( allPreviews, "png", temp );
            System.out.println(temp.getAbsolutePath( ) );
            try {
                Desktop.getDesktop().browse(new URI("file:///"+temp.getAbsolutePath( ).replaceAll( "\\\\", "/" )));
              }
                catch( IOException e ) {
                    e.printStackTrace();
                }
                catch( URISyntaxException e ) {
                    e.printStackTrace();
                }
        }
        catch( IOException e ) {
            e.printStackTrace();
        }*/
    }
    
    public void importFromVignette(){
        VignetteController.importConversation( this );
    }


    
    public VignetteUICallback getCallback( ) {
    
        return callback;
    }


    
    public void setCallback( VignetteUICallback callback ) {
    
        this.callback = callback;
    }
    
    /**
     * Returns a list with all the information required for previewing the characters of this chapter in thumbnailWidthXthumbnailHeight pixels
     * @param thumbnailWidth    The width of the thumbnail (e.g. 48)
     * @param thumbnailHeight   The height of the thumbnail (e.g. 48)
     * @return
     */
    public List<VignetteCharacterPreview> getCharacters(int thumbnailWidth, int thumbnailHeight){
        List<VignetteCharacterPreview> list = new ArrayList<VignetteCharacterPreview>();
        if (thumbnailWidth<32 || thumbnailWidth>512){
            thumbnailWidth=48;
        }
        if (thumbnailHeight<32 || thumbnailHeight>512){
            thumbnailHeight=48;
        }
        
        VignetteCharacterPreview playerPreview = new VignetteCharacterPreview();
        playerPreview.setImage( getCharacterPreview(Controller.getInstance( ).getSelectedChapterDataControl( ).getPlayer( ), thumbnailWidth, thumbnailHeight) );
        playerPreview.setName( Controller.getInstance( ).getSelectedChapterDataControl( ).getPlayer( ).getId( ) );
        list.add( playerPreview );
        
        for (NPCDataControl npc:Controller.getInstance( ).getSelectedChapterDataControl( ).getNPCsList( ).getNPCs( )){
            VignetteCharacterPreview charPreview = new VignetteCharacterPreview();
            charPreview.setImage( getCharacterPreview(npc, thumbnailWidth, thumbnailHeight) );
            charPreview.setName( npc.getId( ) );
            list.add( charPreview );
        }
        
        return list;
    }
    
    private BufferedImage getCharacterPreview(NPCDataControl npc, int thumbnailWidth, int thumbnailHeight){
        String previewImagePath = npc.getPreviewImage( );
        BufferedImage finalImage = null;
        float thumbnailRatio = (float)thumbnailWidth/(float)thumbnailHeight;
        finalImage = new BufferedImage(thumbnailWidth, thumbnailHeight, BufferedImage.TRANSLUCENT);
        Graphics2D g = finalImage.createGraphics( );        
        if (previewImagePath!=null && !previewImagePath.contains( NPCDataControl.EMPTY_ANIMATION )){
            Image originalImage = AssetsController.getImage(previewImagePath);
            
            float originalRatio = (float)originalImage.getWidth( null )/(float)originalImage.getHeight( null );
            int originalWidth = originalImage.getWidth( null );
            int originalHeight = originalImage.getHeight( null );
            
            if (originalRatio<thumbnailRatio){
                int sourceWidth = originalWidth;
                int sourceHeight = Math.round( sourceWidth/thumbnailRatio );
                if (sourceHeight<=0 || sourceHeight>=originalHeight){
                    sourceHeight = originalHeight;
                }
                g.drawImage( originalImage, 0, 0, thumbnailWidth, thumbnailHeight, 0, 0, sourceWidth, sourceHeight, null);
            } else {
                int sourceWidth = originalWidth;
                int sourceHeight = originalHeight;
                g.drawImage( originalImage, 0, 0, thumbnailWidth, thumbnailHeight, 0, 0, sourceWidth, sourceHeight, null);
            }
            
            g.dispose( );
        } else {
            String initial ="P";
            if (npc.getContent( ) instanceof NPC){
                if (((NPC)npc.getContent( )).getId( )!=null && ((NPC)npc.getContent( )).getId( ).length( )>0){
                    initial = ((NPC)npc.getContent( )).getId( ).substring( 0, 1 ); 
                }
            }
            
            Color bkgColor, txtColor = null;
            
            if (npc.getBubbleBkgColor( )!=null)
                bkgColor =npc.getBubbleBkgColor( );
            else
                bkgColor = getNextColor() ;
            if (npc.getTextFrontColor( )!=null)
                txtColor=npc.getTextFrontColor( );
            else
                txtColor= Color.black;
            
            if (txtColor.equals( bkgColor )){
                bkgColor = getNextColor() ;txtColor= Color.black;
            }
            
            g.setColor( bkgColor );
            g.fillRect( 0, 0, thumbnailWidth, thumbnailHeight );
            g.setColor( txtColor );
            g.setFont( g.getFont( ).deriveFont( 22F ) );
            g.drawString( initial, 8, 25 );
            g.dispose( );
        }
        return finalImage;
    }
}
