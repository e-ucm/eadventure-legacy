package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Image;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.elements.Element;
import es.eucm.eadventure.common.data.chapter.elements.Item;
import es.eucm.eadventure.common.data.chapter.resources.Asset;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * An object in the game
 */
public class FunctionalItem extends FunctionalElement {
    
    /**
     * Resources being used in the item
     */
    protected Resources resources;

    /**
     * Image of the item, to display in the scene
     */
    protected Image image;

    /**
     * Image of the item, to display on the inventory
     */
    private Image icon;

    /**
     * Item containing the data
     */
    protected Item item;

    /**
     * Creates a new FunctionalItem
     * @param item the item's data
     * @param x the item's horizontal position
     * @param y the item's vertical position
     */
    public FunctionalItem( Item item, int x, int y ) {
        super( x, y );
        this.item = item;

        image = null;
        icon = null;
        
        resources = createResourcesBlock();
        
        // Load the resources
        MultimediaManager multimediaManager = MultimediaManager.getInstance( );
        if( resources.existAsset( Item.RESOURCE_TYPE_IMAGE ) )
            image = multimediaManager.loadImageFromZip( resources.getAssetPath( Item.RESOURCE_TYPE_IMAGE ), MultimediaManager.IMAGE_SCENE );
        if( resources.existAsset( Item.RESOURCE_TYPE_ICON ) )
            icon = multimediaManager.loadImageFromZip( resources.getAssetPath( Item.RESOURCE_TYPE_ICON ), MultimediaManager.IMAGE_SCENE );
    }

    /**
     * Creates a new FunctionalItem at position (0, 0)
     * @param item the item's data
     */
    public FunctionalItem( Item item ) {
        this( item, 0, 0 );
    }
    
    /**
     * Updates the resources of the icon (if the current resources and the new one are different)
     */
    public void updateResources( ) {
        // Get the new resources
        Resources newResources = createResourcesBlock ( );
        
        // If the resources have changed, load the new one
        if( resources != newResources ) {
            resources = newResources;
            
            // Load the resources
            MultimediaManager multimediaManager = MultimediaManager.getInstance( );
            if( resources.existAsset( Item.RESOURCE_TYPE_IMAGE ) )
                image = multimediaManager.loadImageFromZip( resources.getAssetPath( Item.RESOURCE_TYPE_IMAGE ), MultimediaManager.IMAGE_SCENE );
            if( resources.existAsset( Item.RESOURCE_TYPE_ICON ) )
                icon = multimediaManager.loadImageFromZip( resources.getAssetPath( Item.RESOURCE_TYPE_ICON ), MultimediaManager.IMAGE_SCENE );
        }
    }

    /**
     * Returns this item's data
     * @return this item's data
     */
    public Item getItem( ) {
        return item;
    }

    /**
     * Returns this item's icon image
     * @return this item's icon image
     */
    public Image getIconImage( ) {
        return icon;
    }

    @Override
    public Element getElement( ) {
        return item;
    }

    @Override
    public int getWidth( ) {
        return image.getWidth( null );
    }

    @Override
    public int getHeight( ) {
        return image.getHeight( null );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.Renderable#update(long)
     */
    public void update( long elapsedTime ) {
        // Do nothing
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.Renderable#draw(java.awt.Graphics2D)
     */
    public void draw( ) {
        GUI.getInstance( ).addElementToDraw( image, Math.round( x - ( getWidth( ) / 2 ) ) - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ), Math.round( y - getHeight( ) ), Math.round( y ) );
    }
    
    @Override
    public boolean isPointInside( float x, float y ) {
        boolean isInside = false;
        
        int mousex = (int)( x - ( this.x - getWidth( ) / 2 ) );
        int mousey = (int)( y - ( this.y - getHeight( ) ) );
        
        if( ( mousex >= 0 ) && ( mousex < getWidth() ) && ( mousey >= 0 ) && ( mousey < getHeight() ) ) {
            BufferedImage bufferedImage = (BufferedImage) image;
            int alpha = bufferedImage.getRGB( mousex, mousey ) >>> 24;
            isInside = alpha > 128;
        }
        
        return isInside;
    }

    @Override
    public boolean canPerform( int action ) {
        boolean canPerform = false;

        // The item can't be given to, nor talked to
        switch( action ) {
            case ActionManager.ACTION_LOOK:
            case ActionManager.ACTION_GRAB:
            case ActionManager.ACTION_EXAMINE:
            case ActionManager.ACTION_USE:
            case ActionManager.ACTION_GIVE:
            case ActionManager.ACTION_GOTO:
            case ActionManager.ACTION_USE_WITH:
                canPerform = true;
                break;

            case ActionManager.ACTION_GIVE_TO:
            case ActionManager.ACTION_TALK:
                canPerform = false;
                break;
        }

        return canPerform;
    }

    @Override
    public boolean isInInventory( ) {
        return Game.getInstance( ).getItemSummary( ).isItemGrabbed( item.getId( ) );
    }

    @Override
    public boolean examine( ) {
        boolean examined = false;
        
        // Only take the FIRST valid action
        for( int i = 0; i < item.getActions( ).size( ) && !examined; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.EXAMINE ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    // Store the effects
                	FunctionalEffects.storeAllEffects(action.getEffects( ));
                    examined = true;
                }
            }
        }
            
        return examined;
    }

    @Override
    public boolean canBeUsedAlone( ) {
        boolean canBeUsedAlone = false;
        
        // Check only for one valid use action
        for( int i = 0; i < item.getActions( ).size( ) && !canBeUsedAlone; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.USE ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    canBeUsedAlone = true;
                } 
            }
        }
        
        return canBeUsedAlone;
    }
    
    /* Own methods */

    /**
     * Triggers the grabbing action associated with the item
     * @return True if the item was grabbed, false otherwise
     */
    public boolean grab( ) {
        boolean grabbed = false;
        
        // Only take the FIRST valid action
        for( int i = 0; i < item.getActions( ).size( ) && !grabbed; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.GRAB ) {
                if( new FunctionalConditions(action.getConditions( ) ).allConditionsOk( ) ) {
                    // If the it has not a cancel action, grab the item
                    if( !action.getEffects( ).hasCancelAction( ) )
                        Game.getInstance( ).grabItem( item.getId( ) );
                    
                    // Store the effects
                    FunctionalEffects.storeAllEffects(action.getEffects( ));
                    grabbed = true;
                } 
            }
        }
        
        return grabbed;
    }
    
    /**
     * Triggers the use action associated with the item
     * @return True if the item was used, false otherwise
     */
    public boolean use( ) {
        boolean used = false;
        
        // Only take the FIRST valid action
        for( int i = 0; i < item.getActions( ).size( ) && !used; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.USE ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    // Store the effects
                	FunctionalEffects.storeAllEffects(action.getEffects( ));
                    used = true;
                } 
            }
        }
        
        return used;
    }

    /**
     * Triggers the using with action associated with the item
     * @param anotherItem The second item necessary for the use with action
     * @return True if the items were used, false otherwise
     */
    public boolean useWith( FunctionalItem anotherItem ) {
        boolean usedWith = false;
        
        // Only take the FIRST valid action
        for( int i = 0; i < item.getActions( ).size( ) && !usedWith; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.USE_WITH && action.getIdTarget( ).equals( anotherItem.getItem( ).getId( ) ) ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    // Store the effects
                	FunctionalEffects.storeAllEffects(action.getEffects( ));
                    usedWith = true;
                }
            }
        }
        
        return usedWith;
    }

    /**
     * Triggers the giving action associated with the item
     * @param npc The character receiver of the item
     * @return True if the item was given, false otherwise
     */
    public boolean giveTo( FunctionalNPC npc ) {
        boolean givenTo = false;
        
        // Only take the FIRST valid action
        for( int i = 0; i < item.getActions( ).size( ) && !givenTo; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.GIVE_TO && action.getIdTarget( ).equals( npc.getElement( ).getId( ) ) ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    // If the item has not a cancel action, consume the item
                    if( !action.getEffects( ).hasCancelAction( ) )
                        Game.getInstance( ).consumeItem( item.getId( ) );
                    
                    // Store the effects
                    FunctionalEffects.storeAllEffects(action.getEffects( ));
                    givenTo = true;
                }
            }
        }
        
        return givenTo;
    }

    /**
     * Creates the current resource block to be used
     */
    public Resources createResourcesBlock( ) {
        
        // Get the active resources block
        Resources newResources = null;
        for( int i = 0; i < item.getResources( ).size( ) && newResources == null; i++ )
            if( new FunctionalConditions( item.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                newResources = item.getResources( ).get( i );

        // If no resource block is available, create a default one 
        if (newResources == null){
            newResources = new Resources();
            newResources.addAsset( new Asset( Item.RESOURCE_TYPE_ICON, ResourceHandler.DEFAULT_ICON ) );
            newResources.addAsset( new Asset( Item.RESOURCE_TYPE_IMAGE, ResourceHandler.DEFAULT_IMAGE ) );
        }
        return newResources;
    }
}
