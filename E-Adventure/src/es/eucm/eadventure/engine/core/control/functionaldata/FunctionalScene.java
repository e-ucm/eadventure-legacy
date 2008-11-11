package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.ItemSummary;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.core.data.gamedata.ElementReference;
import es.eucm.eadventure.engine.core.data.gamedata.Exit;
import es.eucm.eadventure.engine.core.data.gamedata.GameData;
import es.eucm.eadventure.engine.core.data.gamedata.elements.ActiveArea;
import es.eucm.eadventure.engine.core.data.gamedata.elements.Barrier;
import es.eucm.eadventure.engine.core.data.gamedata.elements.Item;
import es.eucm.eadventure.engine.core.data.gamedata.elements.NPC;
import es.eucm.eadventure.engine.core.data.gamedata.resources.Asset;
import es.eucm.eadventure.engine.core.data.gamedata.resources.Resources;
import es.eucm.eadventure.engine.core.data.gamedata.scenes.Scene;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * A scene in the game
 */
public class FunctionalScene implements Renderable {
    
    /**
     * Margins of the scene (for use in the scroll)
     */
    private final static int MAX_OFFSET_X = 300;

    /**
     * Scene data
     */
    private Scene scene;
    
    /**
     * Resources being used in the scene
     */
    private Resources resources;
    
    /**
     * Background image for the scene
     */
    private Image background;
    
    /**
     * Foreground image for the scene
     */
    private Image foreground;
    
    /**
     * Background music
     */
    private long backgroundMusicId;

    /**
     * Functional player present in the scene
     */
    private FunctionalPlayer player;

    /**
     * Functional items present in the scene
     */
    private ArrayList<FunctionalItem> items;

    /**
     * Functional characters present in the scene
     */
    private ArrayList<FunctionalNPC> npcs;
    
    /**
     * Functional areas present in the scene
     */
    private ArrayList<FunctionalActiveArea> areas;
    
    /**
     * Functional barriers present in the scene;
     */
    private ArrayList<FunctionalBarrier> barriers;

    /**
     * Offset of the scroll.
     */
    private int offsetX;
    
    /**
     * Creates a new FunctionalScene loading the background music.
     * @param scene the scene's data
     * @param player the reference to the player
     */
    public FunctionalScene( Scene scene, FunctionalPlayer player ) {
        this( scene, player, -1 );
    }

    /**
     * Creates a new FunctionalScene with the given background music.
     * @param scene the scene's data
     * @param player the reference to the player
     * @param backgroundMusicId Background music identifier
     */
    public FunctionalScene( Scene scene, FunctionalPlayer player, long backgroundMusicId ) {
        this.scene = scene;
        this.player = player;

        // Create lists for the characters, items and active areas
        npcs = new ArrayList<FunctionalNPC>( );
        items = new ArrayList<FunctionalItem>( );
        areas = new ArrayList<FunctionalActiveArea>( );
        barriers = new ArrayList<FunctionalBarrier>( );

        // Pick the item summary
        GameData gameData = Game.getInstance( ).getGameData( );
        ItemSummary itemSummary = Game.getInstance( ).getItemSummary( );
        
        // Select the resources
        resources = createResourcesBlock( );

        // Load the background image
        background = null;
        if( resources.existAsset( Scene.RESOURCE_TYPE_BACKGROUND ) )
            background = MultimediaManager.getInstance( ).loadImageFromZip( resources.getAssetPath( Scene.RESOURCE_TYPE_BACKGROUND ), MultimediaManager.IMAGE_SCENE );

        // Load the foreground image
        foreground = null;
        if( background!=null && resources.existAsset( Scene.RESOURCE_TYPE_FOREGROUND ) ){
            BufferedImage bufferedBackground = (BufferedImage) background;
            BufferedImage foregroundHardMap = (BufferedImage) MultimediaManager.getInstance( ).loadImageFromZip( resources.getAssetPath( Scene.RESOURCE_TYPE_FOREGROUND ), MultimediaManager.IMAGE_SCENE );
            BufferedImage bufferedForeground = (BufferedImage)GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( foregroundHardMap.getWidth( null ), foregroundHardMap.getHeight( null ), Transparency.BITMASK );
            
            for(int i = 0; i < foregroundHardMap.getWidth( null ); i++){
                for(int j = 0; j < foregroundHardMap.getHeight( null ); j++){
                    if( foregroundHardMap.getRGB( i, j ) == 0xFFFFFFFF )
                        bufferedForeground.setRGB( i, j, 0x00000000 );
                    else
                        bufferedForeground.setRGB( i, j, bufferedBackground.getRGB( i, j ) );
                }                
            }
            
            foreground = bufferedForeground;
        }
        

        // Load the background music (if it is not loaded)
        this.backgroundMusicId = backgroundMusicId;
        if( backgroundMusicId == -1 )
            playBackgroundMusic( );

        // Add the functional items
        for( ElementReference itemReference : scene.getItemReferences( ) )
            if( new FunctionalConditions(itemReference.getConditions( )).allConditionsOk( ) )
                if( itemSummary.isItemNormal( itemReference.getIdTarget( ) ) )
                    for( Item currentItem : gameData.getItems( ) )
                        if( itemReference.getIdTarget( ).equals( currentItem.getId( ) ) )
                            items.add( new FunctionalItem( currentItem, itemReference.getX( ), itemReference.getY( ) ) );

        // Add the functional characters
        for( ElementReference npcReference : scene.getCharacterReferences( ) )
            if( new FunctionalConditions(npcReference.getConditions( )).allConditionsOk( ) )
                for( NPC currentNPC : gameData.getCharacters( ) )
                    if( npcReference.getIdTarget( ).equals( currentNPC.getId( ) ) )
                        npcs.add( new FunctionalNPC( currentNPC, npcReference.getX( ), npcReference.getY( ) ) );
        
        // Add the functional active areas
        for( ActiveArea activeArea : scene.getActiveAreas( ) )
            if( new FunctionalConditions(activeArea.getConditions( )).allConditionsOk( ) )
                this.areas.add( new FunctionalActiveArea( activeArea ) );
        
        // Add the functional barriers
        for( Barrier barrier : scene.getBarriers( ) )
            if( new FunctionalConditions(barrier.getConditions( )).allConditionsOk( ) )
                this.barriers.add( new FunctionalBarrier( barrier ) );



        updateOffset( );
    }
    
    /**
     * Update the resources and elements of the scene, depending on
     * the state of the flags.
     */
    public void updateScene( ) {
        
        // Update the resources and the player's resources
        updateResources( );
        player.updateResources( );
        
        // Pick the game data
        GameData gameData = Game.getInstance( ).getGameData( );
            
        // Check the item references of the scene
        for( ElementReference itemReference : scene.getItemReferences( ) ) {
            
            // For every item that should be there
            if( new FunctionalConditions(itemReference.getConditions( )).allConditionsOk( ) ) {
                boolean found = false;
                
                // If the functional item is present, update its resources
                for( FunctionalItem currentItem : items ) {
                    if( itemReference.getIdTarget( ).equals( currentItem.getItem( ).getId( ) ) ) {
                        currentItem.updateResources( );
                        found = true;
                    }
                }
                
                // If it was not found, search for it and add it
                if( !found ) {
                    if( Game.getInstance( ).getItemSummary( ).isItemNormal( itemReference.getIdTarget( ) ) ) {
                        for( Item currentItem : gameData.getItems( ) ) {
                            if( itemReference.getIdTarget( ).equals( currentItem.getId( ) ) ) {
                                items.add( new FunctionalItem( currentItem, itemReference.getX( ), itemReference.getY( ) ) );
                            }
                        }
                    }
                }
            }
        }

        // Check the character references of the scene
        for( ElementReference npcReference : scene.getCharacterReferences( ) ) {
            
            // For every item that should be there
            if( new FunctionalConditions(npcReference.getConditions( )).allConditionsOk( ) ) {
                boolean found = false;
                
                // If the functional character is present, update its resources
                for( FunctionalNPC currentNPC : npcs ) {
                    if( npcReference.getIdTarget( ).equals( currentNPC.getNPC( ).getId( ) ) ) {
                        currentNPC.updateResources( );
                        found = true;
                    }
                }
                
                // If it was not found, search for it and add it
                if( !found ) {
                    for( NPC currentNPC : gameData.getCharacters( ) ) {
                        if( npcReference.getIdTarget( ).equals( currentNPC.getId( ) ) ) {
                            npcs.add( new FunctionalNPC( currentNPC, npcReference.getX( ), npcReference.getY( ) ) );
                        }
                    }
                }
            }
        }
        
        // Check the active areas of the scene
        for( ActiveArea activeArea : scene.getActiveAreas( ) ) {
            
            // For every item that should be there
            if( new FunctionalConditions(activeArea.getConditions( )).allConditionsOk( ) ) {
                boolean found = false;
                
                // If the functional item is present, update its resources
                for( FunctionalActiveArea currentFunctionalActiveArea : areas ) {
                    if( activeArea.getId( ).equals( currentFunctionalActiveArea.getItem( ).getId( ) ) ) {
                        found = true;
                        break;
                    }
                }
                
                // If it was not found, search for it and add it
                if( !found ) {
                    areas.add( new FunctionalActiveArea( activeArea ) );
                }
            }
        }

        
        // Create a list with the items to remove
        ArrayList<FunctionalItem> itemsToRemove = new ArrayList<FunctionalItem>( );
        for( FunctionalItem currentItem : items ) {
            boolean keepItem = false;
            
            // For every present item, check if it must be kept
            for( ElementReference itemReference : scene.getItemReferences( ) ) {
                if( itemReference.getIdTarget( ).equals( currentItem.getItem( ).getId( ) ) &&
                		new FunctionalConditions(itemReference.getConditions( )).allConditionsOk( ) ) {
                    keepItem = true;
                }
            }
            
            // If it must not be kept, add it to the remove list
            if( !keepItem )
                itemsToRemove.add( currentItem );
        }
        
        // Remove the elements
        for( FunctionalItem itemToRemove : itemsToRemove )
            items.remove( itemToRemove );
        
        
        // Create a list with the characters to remove
        ArrayList<FunctionalNPC> npcsToRemove = new ArrayList<FunctionalNPC>( );
        for( FunctionalNPC currentNPC : npcs ) {
            boolean keepNPC = false;
            
            // For every present character, check if it must be kept
            for( ElementReference npcReference : scene.getCharacterReferences( ) ) {
                if( npcReference.getIdTarget( ).equals( currentNPC.getNPC( ).getId( ) ) &&
                		new FunctionalConditions(npcReference.getConditions( )).allConditionsOk( ) ) {
                    keepNPC = true;
                }
            }
            
            // If it must not be kept, add it to the remove list
            if( !keepNPC )
                npcsToRemove.add( currentNPC );
        }
        
        // Remove the elements
        for( FunctionalNPC npcToRemove : npcsToRemove )
            npcs.remove( npcToRemove );
        
        // Create a list with the active areas to remove
        ArrayList<FunctionalActiveArea> activeAreasToRemove = new ArrayList<FunctionalActiveArea>( );
        for( FunctionalActiveArea currentActiveArea : areas ) {
            boolean keepActiveArea = false;
            
            // For every present item, check if it must be kept
            for( ActiveArea activeArea : scene.getActiveAreas( ) ) {
                if( activeArea.getId( ).equals( currentActiveArea.getItem( ).getId( ) ) &&
                		new FunctionalConditions(activeArea.getConditions( )).allConditionsOk( ) ) {
                    keepActiveArea = true;
                }
            }
            
            // If it must not be kept, add it to the remove list
            if( !keepActiveArea )
                activeAreasToRemove.add( currentActiveArea );
        }
        
        // Remove the elements
        for( FunctionalActiveArea areaToRemove : activeAreasToRemove )
            areas.remove( areaToRemove );

    }
    
    /**
     * Updates the resources of the scene (if the current resources and the new one are different)
     */
    public void updateResources( ) {
        // Get the new resources
        Resources newResources = createResourcesBlock( );

        // If the resources have changed, load the new one
        if( resources != newResources ) {
            resources = newResources;
            
            if( resources.existAsset( Scene.RESOURCE_TYPE_BACKGROUND ) )
                background = MultimediaManager.getInstance( ).loadImageFromZip( resources.getAssetPath( Scene.RESOURCE_TYPE_BACKGROUND ), MultimediaManager.IMAGE_SCENE );

            // If there was a foreground, delete it
            if( foreground != null )
                foreground.flush( );
            
            // Load the foreground image
            foreground = null;
            if( background!=null && resources.existAsset( Scene.RESOURCE_TYPE_FOREGROUND ) ){
                BufferedImage bufferedBackground = (BufferedImage) background;
                BufferedImage foregroundHardMap = (BufferedImage) MultimediaManager.getInstance( ).loadImageFromZip( resources.getAssetPath( Scene.RESOURCE_TYPE_FOREGROUND ), MultimediaManager.IMAGE_SCENE );
                BufferedImage bufferedForeground = (BufferedImage)GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( foregroundHardMap.getWidth( null ), foregroundHardMap.getHeight( null ), Transparency.BITMASK );
                
                for(int i = 0; i < foregroundHardMap.getWidth( null ); i++){
                    for(int j = 0; j < foregroundHardMap.getHeight( null ); j++){
                        if( foregroundHardMap.getRGB( i, j ) == 0xFFFFFFFF )
                            bufferedForeground.setRGB( i, j, 0x00000000 );
                        else
                            bufferedForeground.setRGB( i, j, bufferedBackground.getRGB( i, j ) );
                    }                
                }
                
                foreground = bufferedForeground;
            }
            
            playBackgroundMusic();
        }
    }
    
    /**
     * Returns the contained scene
     * @return Contained scene
     */
    public Scene getScene( ) {
        return scene;
    }

    /**
     * Returns the npc with the given id
     * @param npcId the id of the npc
     * @return the npc with the given id
     */
    public FunctionalNPC getNPC( String npcId ) {
        FunctionalNPC npc = null;

        if( npcId != null ) {
            for( FunctionalNPC currentNPC : npcs )
                if( currentNPC.getElement( ).getId( ).equals( npcId ) )
                    npc = currentNPC;
        }

        return npc;
    }
    
    /**
     * Returns the list of npcs in this scene
     * @return the list of npcs in this scene
     */
    public ArrayList<FunctionalNPC> getNPCs( ) {
        return npcs;
    }
    
    /**
     * Returns the list of items in this scene
     * @return the list of items in this scene
     */
    public ArrayList<FunctionalItem> getItems( ) {
        return items;
    }
    
    /**
     * Returns the list of items in this scene
     * @return the list of items in this scene
     */
    public ArrayList<FunctionalActiveArea> getActiveAreas( ) {
        return areas;
    }


    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.functionaldata.Renderable#update(long)
     */
    public void update( long elapsedTime ) {
        
        // Update the items
        for( FunctionalItem item : items )
            item.update( elapsedTime );
        
        // Update the active areas
        for( FunctionalActiveArea activeArea : areas )
            activeArea.update( elapsedTime );
        
        // Update the characters
        for( FunctionalNPC npc : npcs )
            npc.update( elapsedTime );
        
        // Update the player
        player.update( elapsedTime );
        
        // Update the offset
        if( updateOffset( ) && Game.getInstance( ).getLastMouseEvent( )!=null  )
            Game.getInstance( ).mouseMoved( Game.getInstance( ).getLastMouseEvent( ) );
    }
    
    /**
     * Returns the offset of the scroll.
     * @return Offset of the scroll
     */
    public int getOffsetX( ) {
        return offsetX;
    }
    
    /**
     * Updates the offset value of the screen.
     * @return True if the offset has changed, false otherwise
     */
    private boolean updateOffset( ) {
        // TODO Francis: Comentar
        boolean updated = false;
        
        // Scroll
        int iw = background.getWidth( null );
        if( player.getX( ) - offsetX > ( GUI.WINDOW_WIDTH-MAX_OFFSET_X ) ) {
            updated = true;
            offsetX += player.getX( ) - offsetX - ( GUI.WINDOW_WIDTH-MAX_OFFSET_X );
            if( offsetX + GUI.WINDOW_WIDTH > iw )
                offsetX = iw - GUI.WINDOW_WIDTH;
        }
        
        else if( player.getX( ) - offsetX < MAX_OFFSET_X ) {
            updated = true;
            offsetX -= MAX_OFFSET_X - player.getX( ) + offsetX;
            if( offsetX < 0 )
                offsetX = 0;
        }
        
        return updated;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.functionaldata.Renderable#draw(java.awt.Graphics2D)
     */
    public void draw( ) {
        
        GUI.getInstance().addBackgroundToDraw(background, offsetX);
               
        for( FunctionalItem item : items ) 
            item.draw( );
        for( FunctionalNPC npc : npcs ) 
            npc.draw( );
        player.draw( );

        if(foreground != null)
            GUI.getInstance().addForegroundToDraw(foreground, offsetX);
        
    }

    /**
     * Returns the element in the given position.
     * If there is no element, null is returned
     * @param x the horizontal position
     * @param y the vertical position
     * @return the element in the given position
     */
    public FunctionalElement getElementInside( int x, int y ) {
        FunctionalElement element = null;

        Iterator<FunctionalItem> ito = items.iterator( );
        while( ito.hasNext( ) && element == null ) {
            FunctionalItem currentItem = ito.next( );
            if( currentItem.isPointInside( x + Game.getInstance().getFunctionalScene( ).getOffsetX(), y ) )
                element = currentItem;
        }
        
        Iterator<FunctionalActiveArea> ita = areas.iterator( );
        while( ita.hasNext( ) && element == null ) {
            FunctionalActiveArea currentActiveArea = ita.next( );
            if( currentActiveArea.isPointInside( x + Game.getInstance().getFunctionalScene( ).getOffsetX(), y ) )
                element = currentActiveArea;
        }

        Iterator<FunctionalNPC> itp = npcs.iterator( );
        while( itp.hasNext( ) && element == null ) {
            FunctionalNPC currentNPC = itp.next( );
            if( currentNPC.isPointInside( x + Game.getInstance().getFunctionalScene( ).getOffsetX(), y ) )
                element = currentNPC;
        }

        return element;
    }

    /**
     * Returns the exit in the given position.
     * If there is no exit, null is returned.
     * @param x the horizontal position
     * @param y the vertical position
     * @return the exit in the given position
     */
    public Exit getExitInside( int x, int y ) {
        Exit exit = null;
        boolean found = false;
        Iterator<Exit> ito = scene.getExits( ).iterator( );
        while( ito.hasNext( ) && !found ) {
            exit = ito.next( );
            found = exit.isPointInside( x + offsetX, y );
        }
        if( !found )
            exit = null;
        return exit;
    }

    /**
     * Notify that the user has clicked the scene
     * @param x the horizontal position of the click
     * @param y the vertical position of the click
     * @param actionSelected the current action selected (use, give, grab, look, ...)
     */
    public void mouseClicked( int x, int y ) {
        // FIXME Francis: Aclarar el uso del offset, ya que se añade en sitios que no deberia y viceversa
        FunctionalElement element = getElementInside( x + offsetX, y );
        if( Game.getInstance( ).getActionManager( ).getActionSelected( ) == ActionManager.ACTION_GOTO || element == null ) {
    
            // Check barriers (only 3rd person mode)
            int destX = x+offsetX;
            int destY = y;
            int[] finalPos = checkPlayerAgainstBarriers( destX, destY );
            /*if (!player.isTransparent( )){
                List<Barrier> barriers = Game.getInstance( ).getFunctionalScene( ).getScene( ).getBarriers( );
                for (Barrier barrier: barriers){
                    if (barrier.getConditions( ).allConditionsOk( )){
                        float intersectionX= playerIntersectsBarrier (barrier, destX, destY);
                        // Intersection
                        if (intersectionX!=Integer.MIN_VALUE){
                            //System.out.println("INTERSECTION X:"+intersectionX );
                            if (intersectionX<destX)
                                destX = (int)(intersectionX-player.getWidth( )/2.0);
                            else if (intersectionX>destX)
                                destX = (int)(intersectionX+player.getWidth( )/2.0);
                        }
                    }
                }
            }*/
            //player.setDestiny( destX, destY );
            player.setDestiny( finalPos[0], finalPos[1] );
            Exit exit = Game.getInstance( ).getFunctionalScene( ).getExitInside( x, y );
            if( exit == null )
                player.setState( FunctionalPlayer.WALK );
            else {
                player.setTargetExit( exit );
                player.setState( FunctionalPlayer.WALKING_EXIT );
            }
            Game.getInstance( ).getActionManager( ).setActionSelected( ActionManager.ACTION_GOTO );
        } else {
            performActionInElement( element );
        }
    }

    //private static final float SEC_GAP = 5;
    
    public int[] checkPlayerAgainstBarriers (int destX, int destY){
        int[] finalPos = new int [2];
        finalPos[0] = destX;
        finalPos[1] = destY;
        for (FunctionalBarrier barrier: barriers){
            int[] newDest = barrier.checkPlayerAgainstBarrier( player, finalPos[0], finalPos[1] );
            finalPos[0] = newDest[0];
            finalPos[1] = newDest[1];
        }
        return finalPos;
    }
    
    /*private float playerIntersectsBarrier (Barrier barrier, int targetX, int targetY){
        float returnValue = Integer.MIN_VALUE;
        float secGap = SEC_GAP;
        
        // Player data
        float px = player.getX( );
        float py = player.getY( );
        float w = player.getWidth( );
        float h = player.getHeight( );
        
        //secGap = (float)(w/2.0);
        
        // Barrier data
        float bx1 = barrier.getX( );
        float bx2 = barrier.getX( ) + barrier.getWidth( );
        float byh = barrier.getY( );
        float byl = barrier.getY( ) + barrier.getHeight( );
        
        // Direction vector
        float dx = targetX - px;
        float dy = 0;
        
        // Determine closer side of the barrier
        float bx = Integer.MIN_VALUE;
        if (dx>0){
            bx = Math.min( bx1, bx2 );
        } else if (dx<0){
            bx = Math.max( bx1, bx2 );
        }
        //Up corner:
        float ucx1 = 0;
        float ucx2 = 0;
        if (dx>=0){
            ucx1 = (float)(px + w/2.0);
            ucx2 = (float)(px - w/2.0);
        }else{
            ucx1 = (float)(px - w/2.0);
            ucx2 = (float)(px + w/2.0);
        }
        float ucy = py+h;
        float dcy = py;
        
        // Test up corner:
        boolean intersectsUp = false;
        boolean intersectsDown = false;
        float tx = (bx-ucx1)/dx;
        if (tx>=0 && tx<=1){
            // Check y
            if (ucy>=byh && ucy<=byl){
                intersectsUp = true;
                if (dx>=0)
                    returnValue=(float)(bx-secGap);
                else
                    returnValue=(float)(bx+secGap);
            } else {
                if (dcy>=byh && dcy<=byl){
                    intersectsDown = true;
                    if (dx>=0)
                        returnValue=(float)(bx-secGap);
                    else
                        returnValue=(float)(bx+secGap);                }
            }
        }
        
        if (!intersectsUp && !intersectsDown){
            tx = (bx-ucx2)/dx;
            if (tx>=0 && tx<=1){
                // Check y
                if (ucy>=byh && ucy<=byl){
                    intersectsUp = true;
                    if (dx>=0)
                        returnValue=(float)(bx-secGap);
                    else
                        returnValue=(float)(bx+secGap);                
                } else {
                    if (dcy>=byh && dcy<=byl)
                        intersectsDown = true;
                    if (dx>=0)
                        returnValue=(float)(bx-secGap);
                    else
                        returnValue=(float)(bx+secGap);

                }
            }
        }
        return returnValue;
    }*/
    
    /**
     * Performs the given action with the given element
     * @param clickedElement the element that will receive the action
     * @param actionSelected the action to be performed
     */
    public void performActionInElement( FunctionalElement clickedElement ) {
        Game game = Game.getInstance( );
        int actionSelected = Game.getInstance( ).getActionManager( ).getActionSelected( );
        switch( actionSelected ) {
            case ActionManager.ACTION_LOOK:
                player.setFinalElement( clickedElement );
                player.setState( FunctionalPlayer.LOOK );
                break;
            case ActionManager.ACTION_EXAMINE:
                player.setFinalElement( clickedElement );
                player.setState( FunctionalPlayer.WALKING_EXAMINE );
                break;
            case ActionManager.ACTION_GIVE:
                if( clickedElement.canPerform( actionSelected ) ) {
                    if( clickedElement.isInInventory( ) ) {
                        player.setOptionalElement( clickedElement );
                        game.getActionManager( ).setActionSelected( ActionManager.ACTION_GIVE_TO );
                    } else
                        player.speak( GameText.getTextGiveObjectNotInventory( ) );
                } else
                    player.speak( GameText.getTextGiveNPC( ) );
                break;
            case ActionManager.ACTION_GIVE_TO:
                if( clickedElement.canPerform( actionSelected ) ) {
                    player.setFinalElement( clickedElement );
                    player.setState( FunctionalPlayer.WALKING_GIVE );
                } else
                    player.speak( GameText.getTextGiveCannot( ) );
                break;
            case ActionManager.ACTION_GRAB:
                if( clickedElement.canPerform( actionSelected ) ) {
                    if( !clickedElement.isInInventory( ) ) {
                        player.setFinalElement( clickedElement );
                        player.setState( FunctionalPlayer.WALKING_GRAB );
                    } else
                        player.speak( GameText.getTextGrabObjectInventory( ) );
                } else
                    player.speak( GameText.getTextGrabNPC( ) );
                break;
            case ActionManager.ACTION_TALK:
                if( clickedElement.canPerform( actionSelected ) ) {
                    player.setFinalElement( clickedElement );
                    player.setState( FunctionalPlayer.WALKING_TALK );
                } else
                    player.speak( GameText.getTextTalkObject( ) );
                break;
            case ActionManager.ACTION_USE:
                if( clickedElement.canPerform( actionSelected ) ) {
                    // If the item can be used alone, use it
                    if( clickedElement.canBeUsedAlone( ) ) {
                        player.setFinalElement( clickedElement );
                        player.setState( FunctionalPlayer.WALKING_USE_SINGLE );
                    }
                    
                    // If the element must be used with another one, switch to a new state
                    else {
                        player.setOptionalElement( clickedElement );
                        game.getActionManager( ).setActionSelected( ActionManager.ACTION_USE_WITH );
                    }
                } else
                    player.speak( GameText.getTextUseNPC( ) );
                break;
            case ActionManager.ACTION_USE_WITH:
                if( clickedElement.canPerform( actionSelected ) ) {
                    player.setFinalElement( clickedElement );
                    player.setState( FunctionalPlayer.WALKING_USE );
                } else
                    player.speak( GameText.getTextUseNPC( ) );
                break;
        }
    }
    
    /**
     * Returns the identifier of the backgrounds music.
     * @return Identifier number of the background music
     */
    public long getBackgroundMusicId( ) {
        return backgroundMusicId;
    }
    
    /**
     * Stops the background music of the scene
     */
    public void stopBackgroundMusic(){
        MultimediaManager.getInstance( ).stopPlaying( backgroundMusicId );
    }
    
    /**
     * Load and play the background music if is not active.
     * If its the first time it loads, it obtains the ID of the background
     * music to be able to identify itself from other sounds.
     */
    public void playBackgroundMusic(){
        if( Game.getInstance( ).getOptions( ).isMusicActive( ) ){
            if( backgroundMusicId != -1 ){
                if( !MultimediaManager.getInstance( ).isPlaying( backgroundMusicId ) ){
                    backgroundMusicId = MultimediaManager.getInstance( ).loadMusic( resources.getAssetPath( Scene.RESOURCE_TYPE_MUSIC ), true );
                    MultimediaManager.getInstance( ).startPlaying( backgroundMusicId );
                }
            }else{
                if( resources.existAsset( Scene.RESOURCE_TYPE_MUSIC ) ) {
                    backgroundMusicId = MultimediaManager.getInstance( ).loadMusic( resources.getAssetPath( Scene.RESOURCE_TYPE_MUSIC ), true );
                    MultimediaManager.getInstance( ).startPlaying( backgroundMusicId );
                }
            }
        }
    }
    
    /**
     * Creates the current resource block to be used
     */
    public Resources createResourcesBlock( ) {
        
        // Get the active resources block
        Resources newResources = null;
        for( int i = 0; i < scene.getResources( ).size( ) && newResources == null; i++ )
            if( new FunctionalConditions(scene.getResources( ).get( i ).getConditions( )).allConditionsOk( ) )
                newResources = scene.getResources( ).get( i );

        // If no resource block is available, create a default one 
        if (newResources == null){
            newResources = new Resources();
            newResources.addAsset( new Asset( Scene.RESOURCE_TYPE_BACKGROUND, ResourceHandler.DEFAULT_BACKGROUND ) );
            newResources.addAsset( new Asset( Scene.RESOURCE_TYPE_FOREGROUND, ResourceHandler.DEFAULT_FOREGROUND ) );
            newResources.addAsset( new Asset( Scene.RESOURCE_TYPE_HARDMAP, ResourceHandler.DEFAULT_HARDMAP ) );
        }
        return newResources;
    }
}
