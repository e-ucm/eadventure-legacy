package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Image;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.common.data.chapter.InfluenceArea;
import es.eucm.eadventure.common.data.chapter.elements.Atrezzo;
import es.eucm.eadventure.common.data.chapter.elements.Element;
import es.eucm.eadventure.common.data.chapter.elements.Item;
import es.eucm.eadventure.common.data.chapter.resources.Asset;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * An atrezzo object in the game
 */
public class FunctionalAtrezzo extends FunctionalElement {
    
    /**
     * Resources being used in the atrezzo item
     */
    protected Resources resources;

    /**
     * Image of the atrezo item, to display in the scene
     */
    protected Image image;

    /**
     * Image of the atrezzo item, to display on the inventory
     */
   // private Image icon;

    private Image oldImage = null;
    
    private float oldScale = -1;
    
    private Image oldOriginalImage = null;
    
    /**
     * Atrezzo item containing the data
     */
    protected Atrezzo atrezzo;

    /**
     * Creates a new FunctionalItem
     * @param atrezzo the atrezzo's data
     * @param x the atrezzo's horizontal position
     * @param y the atrezzo's vertical position
     */
    public FunctionalAtrezzo( Atrezzo atrezzo, int x, int y ) {
        super( x, y );
        this.atrezzo = atrezzo;
        this.scale = 1;
        image = null;
        //icon = null;
        
        resources = createResourcesBlock();
        
        // Load the resources
        MultimediaManager multimediaManager = MultimediaManager.getInstance( );
        if( resources.existAsset( Item.RESOURCE_TYPE_IMAGE ) )
            image = multimediaManager.loadImageFromZip( resources.getAssetPath( Item.RESOURCE_TYPE_IMAGE ), MultimediaManager.IMAGE_SCENE );
        //if( resources.existAsset( Item.RESOURCE_TYPE_ICON ) )
           // icon = multimediaManager.loadImageFromZip( resources.getAssetPath( Item.RESOURCE_TYPE_ICON ), MultimediaManager.IMAGE_SCENE );
    }
    /**
     * Creates a new FunctionalItem
     * @param atrezzo the atrezzo's data
     * @param x the atrezzo's horizontal position
     * @param y the atrezzo's vertical position
     * @param layer the atrezzo�s layer, it means, it will be painted in that position
     */
    public FunctionalAtrezzo( Atrezzo atrezzo, int x, int y, int layer ) {
    	 super( x, y );
         this.atrezzo = atrezzo;
         this.scale = 1;
         image = null;
         this.layer = layer;
         //icon = null;
         
         resources = createResourcesBlock();
         
         // Load the resources
         MultimediaManager multimediaManager = MultimediaManager.getInstance( );
         if( resources.existAsset( Item.RESOURCE_TYPE_IMAGE ) )
             image = multimediaManager.loadImageFromZip( resources.getAssetPath( Item.RESOURCE_TYPE_IMAGE ), MultimediaManager.IMAGE_SCENE );
    }

    /**
     * Creates a new FunctionalAtrezzo at position (0, 0)
     * @param atrezzo the atrezzo's data
     */
    public FunctionalAtrezzo( Atrezzo atrezzo ) {
        this( atrezzo, 0, 0 );
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
           // if( resources.existAsset( Item.RESOURCE_TYPE_ICON ) )
             //   icon = multimediaManager.loadImageFromZip( resources.getAssetPath( Item.RESOURCE_TYPE_ICON ), MultimediaManager.IMAGE_SCENE );
        }
    }

    /**
     * Returns this atrezzo's data
     * @return this atrezzo's data
     */
    public Atrezzo getAtrezzo( ) {
        return atrezzo;
    }

    /**
     * Returns this atrezzo's icon image
     * @return this atrezzo's icon image
     */
   // public Image getIconImage( ) {
     //   return icon;
    //}

    @Override
    public Element getElement( ) {
        return atrezzo;
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
    	int x_image = Math.round( x - ( getWidth( ) * scale / 2 ) ) - Game.getInstance( ).getFunctionalScene( ).getOffsetX( );
    	int y_image = Math.round( y - getHeight( ) * scale);
    	
    	if (scale != 1) {
    		Image temp;
    		if (scale == oldScale && image == oldOriginalImage) {
    			temp = oldImage;
    		} else {
    			temp = image.getScaledInstance(Math.round(image.getWidth(null) * scale), Math.round(image.getHeight(null) * scale), Image.SCALE_SMOOTH);
    			oldImage = temp;
    			oldOriginalImage = image;
    			oldScale = scale;
    		}
    		if (layer==-1)
    			GUI.getInstance().addElementToDraw(temp, x_image, y_image, Math.round(y),Math.round(y));
    		else 
    			GUI.getInstance().addElementToDraw(temp, x_image, y_image, layer, Math.round(y));
    	
    	
    	} else 
    		if (layer==-1)
    			GUI.getInstance( ).addElementToDraw( image, x_image, y_image, Math.round( y ), Math.round(y) );
    		else 
    			GUI.getInstance( ).addElementToDraw( image, x_image, y_image, layer, Math.round(y) );
    }
    
    @Override
    public boolean isPointInside( float x, float y ) {
        boolean isInside = false;
        
        int mousex = (int)( x - ( this.x - getWidth( ) * scale / 2 ) );
        int mousey = (int)( y - ( this.y - getHeight( ) * scale) );
        
        if( ( mousex >= 0 ) && ( mousex < getWidth() * scale) && ( mousey >= 0 ) && ( mousey < getHeight() * scale) ) {
            BufferedImage bufferedImage = (BufferedImage) image;
            int alpha = bufferedImage.getRGB( (int) (mousex/ scale), (int) (mousey / scale) ) >>> 24;
            isInside = alpha > 128;
        }
        
        return isInside;
    }

    
    //TODO creo k hay que quitarlo
    @Override
    public boolean isInInventory( ) {
        return false;//Game.getInstance( ).getItemSummary( ).isItemGrabbed( atrezzo.getId( ) );
    }

    @Override
    public boolean examine( ) {
        return false;
    }

    @Override
    public boolean canBeUsedAlone( ) {
    		return false;
    }
    
    /* Own methods */

    

    /**
     * Creates the current resource block to be used
     */
    public Resources createResourcesBlock( ) {
        
        // Get the active resources block
        Resources newResources = null;
        for( int i = 0; i < atrezzo.getResources( ).size( ) && newResources == null; i++ )
            if( new FunctionalConditions( atrezzo.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                newResources = atrezzo.getResources( ).get( i );

        // If no resource block is available, create a default one 
        if (newResources == null){
            newResources = new Resources();
          //  newResources.addAsset( new Asset( Item.RESOURCE_TYPE_ICON, ResourceHandler.DEFAULT_ICON ) );
            newResources.addAsset( new Asset( Item.RESOURCE_TYPE_IMAGE, ResourceHandler.DEFAULT_IMAGE ) );
        }
        return newResources;
    }

	@Override
	public boolean canPerform(int action) {
		return false;
	}
	
    public Action getFirstValidAction(int actionType) {
        return null;
    }


	@Override
	public CustomAction getFirstValidCustomAction(String actionName) {
		return null;
	}

	@Override
	public CustomAction getFirstValidCustomInteraction(String actionName) {
		return null;
	}

	@Override
	public InfluenceArea getInfluenceArea() {
		return null;
	}
}
