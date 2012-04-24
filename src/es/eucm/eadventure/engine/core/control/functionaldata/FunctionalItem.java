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
package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.common.data.chapter.ElementReference;
import es.eucm.eadventure.common.data.chapter.InfluenceArea;
import es.eucm.eadventure.common.data.chapter.elements.Element;
import es.eucm.eadventure.common.data.chapter.elements.Item;
import es.eucm.eadventure.common.data.chapter.elements.Item.BehaviourType;
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

    protected static final long GAP=10000L;
    
    /**
     * Resources being used in the item
     */
    protected Resources resources;

    /**
     * Image of the item, to display in the scene
     */
    protected Image image;

    ////////////Added v1.4
    /**
     * Image of the item when the mouse is over, to display in the scene
     */
    protected Image imageOver;
    protected ResourceTransition transitionNormal;
    protected ResourceTransition transitionOver;
    //protected FunctionalMoveObjectEffect animation;
    //////////////////
    
    /**
     * Image of the item, to display on the inventory
     */
    private Image icon;

    /**
     * Item containing the data
     */
    protected Item item;

    protected InfluenceArea influenceArea;

    private Image oldOriginalImage = null;

    private Image oldImage = null;
    
    private int width, height;

    ////////////Added v1.4
    private int width_normal, height_normal;
    private int width_over, height_over;
    /////////
    
    private int x1, y1, x2, y2;
    
    ////////////Added v1.4
    protected int x1_normal, y1_normal, x2_normal, y2_normal;
    protected int x1_over, y1_over, x2_over, y2_over;
    /**
     * Boolean that indicates whether image or imageOver must be rendered
     */
    protected boolean isMouseOver;
    
    protected long timeElapsed;
    protected boolean startCounting;
    /////////
    
    private float oldScale = -1;

    private ElementReference reference;

    public FunctionalItem( Item item, ElementReference reference ) {
        this( item, reference.getInfluenceArea( ), reference.getX( ), reference.getY( ) );
        this.scale = reference.getScale( );
        this.layer = reference.getLayer( );
        this.reference = reference;
    }

    /**
     * Creates a new FunctionalItem
     * 
     * @param item
     *            the item's data
     * @param x
     *            the item's horizontal position
     * @param y
     *            the item's vertical position
     */
    protected FunctionalItem( Item item, InfluenceArea influenceArea, int x, int y ) {
        super( x, y );
        this.item = item;
        this.influenceArea = influenceArea;
        Image tempimage = null;
        Image tempimage2 = null;
        
        x1 = x1_normal = x1_over = y1 = y1_normal = y1_over = x2 = x2_normal = x2_over = y2 = y2_normal = y2_over = Integer.MAX_VALUE;
        
        image = null;
        imageOver=null;
        icon = null;
        isMouseOver=false;
        timeElapsed=0L;
        startCounting=false;

        resources = createResourcesBlock( );

        // Load the resources
        MultimediaManager multimediaManager = MultimediaManager.getInstance( );
        /*if( resources.existAsset( Item.RESOURCE_TYPE_IMAGE ) ) {
            tempimage = multimediaManager.loadImageFromZip( resources.getAssetPath( Item.RESOURCE_TYPE_IMAGE ), MultimediaManager.IMAGE_SCENE );
            removeTransparentParts(tempimage);
            tempimage = null;
            Runtime.getRuntime( ).gc( );
        }*/
        
        if( resources.existAsset( Item.RESOURCE_TYPE_IMAGE ) ) {
            tempimage = multimediaManager.loadImageFromZip( resources.getAssetPath( Item.RESOURCE_TYPE_IMAGE ), MultimediaManager.IMAGE_SCENE );
            
        }

        if( resources.existAsset( Item.RESOURCE_TYPE_IMAGEOVER ) ) {
            tempimage2 = multimediaManager.loadImageFromZip( resources.getAssetPath( Item.RESOURCE_TYPE_IMAGEOVER ), MultimediaManager.IMAGE_SCENE );
            
        }
        
        if (tempimage2!=null&&tempimage!=null){
            int wo=tempimage2.getWidth( null );
            int ho=tempimage2.getHeight( null );
            int wn=tempimage.getWidth( null );
            int hn=tempimage.getHeight( null );
            
            if (wo>wn || ho>hn){
                BufferedImage temp = new BufferedImage(wo, ho, BufferedImage.TYPE_INT_ARGB);
                Graphics2D gr = temp.createGraphics( );
                gr.drawImage( tempimage, (int)Math.round( (wo-wn)/2.0 ), (int)Math.round( (ho-hn)/2.0 ), wn, hn, null  );
                tempimage=temp;
                //width_normal=width_over;
                //height_normal=height_over;
            }
            

        }
        
        if (tempimage!=null){
            image=removeTransparentParts(tempimage, true);
            tempimage = null;
            //invokeGC=true;
        }
        
        if (tempimage2!=null){
            imageOver=removeTransparentParts(tempimage2, false);
            tempimage2 = null;
            //invokeGC=true;            
        }
        
        int minH = 0;
        if (image!=null){
            minH = imageOver!=null?Math.min( image.getHeight( null ), imageOver.getHeight( null ) ):image.getHeight( null );            
        }
         
        //int minW = imageOver!=null?Math.min( image.getWidth( null ), imageOver.getWidth( null ) ):image.getWidth( null );
        
        //cx = x; cy = (int)(y-minH*scale/2.0);
        width = Math.max(width_over,width_normal); height=Math.max(height_over, height_normal);
        
        
        if( resources.existAsset( Item.RESOURCE_TYPE_ICON ) )
            icon = multimediaManager.loadImageFromZip( resources.getAssetPath( Item.RESOURCE_TYPE_ICON ), MultimediaManager.IMAGE_SCENE );
    }

    //Param added:boolean normal (v1.4)
    /*private void removeTransparentParts(Image tempimage, boolean normal) {
        x1 = tempimage.getWidth( null ); y1 = tempimage.getHeight( null ); x2 = 0; y2 = 0;
        width = x1;
        height = y1;
        for (int i = 0; i < tempimage.getWidth( null ); i++) {
            boolean x_clear = true;
            for (int j = 0; j < tempimage.getHeight( null ); j++) {
                boolean y_clear = true;
                BufferedImage bufferedImage = (BufferedImage) tempimage;
                int alpha = bufferedImage.getRGB( i, j ) >>> 24;
        
                //OLD VALUE: 128
                if (alpha > 0) {
                    if (x_clear)
                        x1 = Math.min( x1, i );
                    if (y_clear)
                        y1 = Math.min( y1, j );
                    x_clear = false;
                    y_clear = false;
                    x2 = Math.max( x2, i );
                    y2 = Math.max( y2, j );
                }
            }
        }
        
        // create a transparent (not translucent) image 
        //NEW! check the parameter is grater than 0, if not, pass the width of the image. This allow introduce completely transparent
        //images
        image = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( x2-x1>0?x2-x1:x1, y2-y1>0?y2-y1:y1, Transparency.TRANSLUCENT );

        // draw the transformed image
        Graphics2D g = (Graphics2D) image.getGraphics( );

        //NEW! check the parameter is grater than 0, if not, pass the width of the image. This allow introduce completely transparent
        //images
        g.drawImage( tempimage, 0, 0, x2-x1>0?x2-x1:x1, y2-y1>0?y2-y1:y1, x1, y1, x2, y2, null);
//        g.drawImage( image, transform, null );
        g.dispose( );
        
    }*/

    private Image removeTransparentParts(Image tempimage, boolean normal) {
        int x1p = tempimage.getWidth( null )-1; int y1p = tempimage.getHeight( null )-1; int x2p = 0; int y2p = 0;
        if (normal){
            width_normal = x1p;
            height_normal = y1p;
        } else {
            width_over = x1p;
            height_over = y1p;
        }
        for (int i = 0; i < tempimage.getWidth( null ); i++) {
            boolean x_clear = true;
            for (int j = 0; j < tempimage.getHeight( null ); j++) {
                boolean y_clear = true;
                BufferedImage bufferedImage = (BufferedImage) tempimage;
                int alpha = bufferedImage.getRGB( i, j ) >>> 24;
        
                //OLD VALUE: 128
                if (alpha > 0) {
                    if (x_clear)
                        x1p = Math.min( x1p, i );
                    if (y_clear)
                        y1p = Math.min( y1p, j );
                    x_clear = false;
                    y_clear = false;
                    x2p = Math.max( x2p, i );
                    y2p = Math.max( y2p, j );
                }
            }
        }
        
        //Check if max and min values are swapped. In that case, just use the whole image
        if (x2p<=x1p||y2p<=y1p){
            x2p=tempimage.getWidth( null )-1;
            y2p=tempimage.getHeight( null )-1;
            x1p=0;y1p=0;
        }
        
        // create a transparent (not translucent) image
        Image newImage = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( x2p - x1p, y2p - y1p, Transparency.TRANSLUCENT );
        //image = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( x2 - x1, y2 - y1, Transparency.TRANSLUCENT );

        // draw the transformed image
        Graphics2D g = (Graphics2D) newImage.getGraphics( );

        g.drawImage( tempimage, 0, 0, x2p-x1p, y2p-y1p, x1p, y1p, x2p, y2p, null);
//        g.drawImage( image, transform, null );
        g.dispose( );
        
        if (normal){
            x1_normal = x1p;
            x2_normal = x2p;
            y1_normal = y1p;
            y2_normal = y2p;
        } else {
            x1_over = x1p;
            x2_over = x2p;
            y1_over = y1p;
            y2_over = y2p;
        }
        
        return newImage;
        
    }
    
    
    /**
     * Creates a new FunctionalItem at position (0, 0)
     * 
     * @param item
     *            the item's data
     */
    public FunctionalItem( Item item, InfluenceArea influenceArea ) {

        this( item, influenceArea, 0, 0 );
    }

    /**
     * Updates the resources of the icon (if the current resources and the new
     * one are different)
     */
    //V1.4_forceLoad
    /**
     * Updates the resources of the icon (if the current resources and the new
     * one are different)
     */
    public void updateResources( ) {
        updateResources(false);
    }
    
    public void updateResources( boolean forceLoad ) {

        // Get the new resources
        Resources newResources = createResourcesBlock(  );
        
        // If the resources have changed, load the new one
        if( resources != newResources || forceLoad) {
            resources = newResources;

            // Load the resources
            MultimediaManager multimediaManager = MultimediaManager.getInstance( );
            Image tempimage = null;
            if( resources.existAsset( Item.RESOURCE_TYPE_IMAGE ) ) {
                tempimage = multimediaManager.loadImageFromZip( resources.getAssetPath( Item.RESOURCE_TYPE_IMAGE ), MultimediaManager.IMAGE_SCENE );
                Image oldImage = image;
                image = removeTransparentParts(tempimage, true);
                transitionNormal = new ResourceTransition((item.getResourcesTransitionTime( )), oldImage, image);
                transitionNormal.start( );
                tempimage = null;
                //Runtime.getRuntime( ).gc( );
            }
            if( resources.existAsset( Item.RESOURCE_TYPE_IMAGEOVER ) ) {
                tempimage = multimediaManager.loadImageFromZip( resources.getAssetPath( Item.RESOURCE_TYPE_IMAGEOVER ), MultimediaManager.IMAGE_SCENE );
                Image oldImageOver=imageOver;
                imageOver = removeTransparentParts(tempimage, false);
                transitionOver = new ResourceTransition((item.getResourcesTransitionTime( )), oldImageOver, imageOver);
                transitionOver.start( );

                tempimage = null;
            }
            if( resources.existAsset( Item.RESOURCE_TYPE_ICON ) )
                icon = multimediaManager.loadImageFromZip( resources.getAssetPath( Item.RESOURCE_TYPE_ICON ), MultimediaManager.IMAGE_SCENE );
        }
    }

    /**
     * Returns this item's data
     * 
     * @return this item's data
     */
    public Item getItem( ) {

        return item;
    }

    /**
     * Returns this item's icon image
     * 
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

        return width;//image.getWidth( null );
    }

    @Override
    public int getHeight( ) {

        return height;//image.getHeight( null );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.Renderable#update(long)
     */
    public void update( long elapsedTime ) {
        if (startCounting){
            timeElapsed-=elapsedTime;
            if (timeElapsed<=0){
                timeElapsed=0;
                isMouseOver=false;
                startCounting=false;
            }
        }
        
        if (transitionNormal!=null)
            transitionNormal.update( elapsedTime );
        
        if (transitionOver!=null)
            transitionOver.update( elapsedTime );
        
        // Animation
        /*if (animation!=null){
            animation.update( elapsedTime );
            if (!animation.isStillRunning( )){
                animation=null;
            }
        }*/
    }

    protected Image getImageToPaint(){
        //Image imageToPaint=(isMouseOver&&imageOver!=null)?imageOver:image;
        Image imageToPaint=(isMouseOver&&imageOver!=null)?
                (transitionOver!=null?transitionOver.getLastUpdate( ):imageOver):
                    (transitionNormal!=null?transitionNormal.getLastUpdate( ):image);
        
        //width = imageToPaint.getWidth( null );
        //height = imageToPaint.getHeight( null );
        
        x1=(isMouseOver&&imageOver!=null)?x1_over:x1_normal;
        x2=(isMouseOver&&imageOver!=null)?x2_over:x2_normal;
        y1=(isMouseOver&&imageOver!=null)?y1_over:y1_normal;
        y2=(isMouseOver&&imageOver!=null)?y2_over:y2_normal;
        width = (isMouseOver&&imageOver!=null)?width_over:width_normal;
        height = (isMouseOver&&imageOver!=null)?height_over:height_normal;
        
        return imageToPaint;
    }
    
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.Renderable#draw(java.awt.Graphics2D)
     */
    /*public void draw( ) {
        int x_image = Math.round( (x + x1 * scale) - ( getWidth( ) * scale / 2 ) ) - Game.getInstance( ).getFunctionalScene( ).getOffsetX( );
        int y_image = Math.round( (y + y1 * scale) - getHeight( ) * scale );
        if( scale != 1 ) {
            Image temp;
            if( image == oldOriginalImage && scale == oldScale ) {
                temp = oldImage;
            }
            else {
                temp = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( Math.round( image.getWidth( null ) * scale ),  Math.round( image.getHeight( null ) * scale ), Transparency.TRANSLUCENT );
                ((Graphics2D) temp.getGraphics( )).drawImage( image, AffineTransform.getScaleInstance( scale, scale ), null );

                oldImage = temp;
                oldOriginalImage = image;
                oldScale = scale;
            }
            if( layer == -1 )
                GUI.getInstance( ).addElementToDraw( temp, x_image, y_image, Math.round( y ), Math.round( y ), highlight, this );
            else
                GUI.getInstance( ).addElementToDraw( temp, x_image, y_image, layer, Math.round( y ), highlight, this );
        }
        else if( layer == -1 )
            GUI.getInstance( ).addElementToDraw( image, x_image, y_image, Math.round( y ), Math.round( y ), highlight, this  );
        else
            GUI.getInstance( ).addElementToDraw( image, x_image, y_image, layer, Math.round( y ), highlight, this  );
    }

    @Override
    public boolean isPointInside( float x, float y ) {
        boolean isInside = false;

        int mousex = (int) ( x - ( this.x - getWidth( ) * scale / 2 ) );
        int mousey = (int) ( y - ( this.y - getHeight( ) * scale ) );
        
        if (mousex < x1 * scale || mousey < y1 * scale || mousex >= x2 * scale || mousey >= y2 * scale)
            return false;
        mousex = mousex - (int) (x1 * scale);
        mousey = mousey - (int) (y1 * scale);

        if( ( mousex >= 0 ) && ( mousex < (x2 - x1) * scale ) && ( mousey >= 0 ) && ( mousey < (y2 - y1) * scale ) ) {
            BufferedImage bufferedImage = (BufferedImage) image;
            int alpha = bufferedImage.getRGB( (int) ( mousex / scale ), (int) ( mousey / scale ) ) >>> 24;
            isInside = alpha > 128;
        }

        return isInside;
    }*/

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.Renderable#draw(java.awt.Graphics2D)
     */
    public void draw( ) {
        
        Image imageToPaint=getImageToPaint();
        
        //int x_image = Math.round( (x + x1_normal * scale) - ( width_normal * scale / 2 ) ) - Game.getInstance( ).getFunctionalScene( ).getOffsetX( );
        //int y_image = Math.round( (y + y1_normal * scale) - height_normal * scale );
        
        //int x_image = Math.round(cx-width/2+x1);
        //int y_image = Math.round(cy-height/2+y1);
        int x_image = Math.round(x-(width/2-x1)*scale)- Game.getInstance( ).getFunctionalScene( ).getOffsetX( );
        int y_image = Math.round(y-(height-y1)*scale);

        if( scale != 1 ) {
            Image temp;
            if( imageToPaint == oldOriginalImage && scale == oldScale ) {
                temp = oldImage;
            }
            else {
                temp = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( Math.round( imageToPaint.getWidth( null ) * scale ),  Math.round( imageToPaint.getHeight( null ) * scale ), Transparency.TRANSLUCENT );
                ((Graphics2D) temp.getGraphics( )).drawImage( imageToPaint, AffineTransform.getScaleInstance( scale, scale ), null );
                //temp = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( Math.round( image.getWidth( null ) * scale ),  Math.round( image.getHeight( null ) * scale ), Transparency.TRANSLUCENT );
                //((Graphics2D) temp.getGraphics( )).drawImage( image, AffineTransform.getScaleInstance( scale, scale ), null );

                oldImage = temp;
                oldOriginalImage = imageToPaint;
                oldScale = scale;
            }
            if( layer == -1 ){
                //lastImage = temp;
                GUI.getInstance( ).addElementToDraw( temp, x_image, y_image, Math.round( y ), Math.round( y ), highlight, this );
            }else{
                //lastImage = temp;
                GUI.getInstance( ).addElementToDraw( temp, x_image, y_image, layer, Math.round( y ), highlight, this );
            }
        }
        else if( layer == -1 ){
            //lastImage = imageToPaint;
            GUI.getInstance( ).addElementToDraw( imageToPaint, x_image, y_image, Math.round( y ), Math.round( y ), highlight, this  );
        }else{
            //lastImage = imageToPaint;
            GUI.getInstance( ).addElementToDraw( imageToPaint, x_image, y_image, layer, Math.round( y ), highlight, this  );
        }
    }

    @Override
    public boolean isPointInside( float x, float y ) {
        boolean isInside = false;
        
        // XXX INREDIS
        if (item.getBehaviour( )==BehaviourType.ATREZZO){
            return false;
        }
        // XXX FININREDIS

        int mousex = (int) ( x - ( this.x - getWidth( ) * scale / 2 ) );
        int mousey = (int) ( y - ( this.y - getHeight( ) * scale ) );
        
        if (mousex < x1 * scale || mousey < y1 * scale || mousex >= x2 * scale || mousey >= y2 * scale)
            return false;
        mousex = mousex - (int) (x1 * scale);
        mousey = mousey - (int) (y1 * scale);

        if( ( mousex >= 0 ) && ( mousex < (x2 - x1) * scale ) && ( mousey >= 0 ) && ( mousey < (y2 - y1) * scale ) ) {
            BufferedImage bufferedImage = (BufferedImage) getImageToPaint();
            int coordX = (int) ( mousex / scale );
            int coordY = (int) ( mousey / scale );
            // Check coordX, coordY are in bounds.
            if (coordX<0)  coordX=0;
            if (coordY<0)  coordY=0;
            if (coordX>=bufferedImage.getWidth( ))  coordX=bufferedImage.getWidth( )-1;
            if (coordY>=bufferedImage.getHeight( ))  coordY=bufferedImage.getHeight( )-1;
            int alpha = bufferedImage.getRGB( coordX, coordY ) >>> 24;
            isInside = alpha > 124;
        }

        if (isInside){
            isMouseOver=true;
            FunctionalScene fscene = Game.getInstance( ).getFunctionalScene( );
            if (fscene!=null){
                fscene.resetAllItems( this );
            }
            startCounting=true;
            timeElapsed=GAP;
        } else {
            isMouseOver=false;
            startCounting=false;
        }
        //isMouseOver=isInside;
        
        return isInside;
    }
    
    //XXX INREDIS
    public void resetItem(){
        isMouseOver=false;
        startCounting=false;
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
            case ActionManager.ACTION_CUSTOM:
            case ActionManager.ACTION_CUSTOM_INTERACT:
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
                    FunctionalEffects.storeAllEffects( action.getEffects( ) );
                    examined = true;
                }
            }
        }

        // if no actions can be launched (because its conditions are't OK), lunch the first action which has activated not-effects
        for( int i = 0; i < item.getActions( ).size( ) && !examined; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.EXAMINE ) {
                if( action.isActivatedNotEffects( ) ) {
                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getNotEffects( ) );
                    examined = true;
                }
            }
        }
        return examined;
    }

    @Override
    public boolean canBeUsedAlone( ) {

        boolean canBeUsedAlone = false;
        Action grabAction = getFirstValidAction( Action.GRAB );
        Action useWithAction = getFirstValidAction( Action.USE_WITH );
        Action giveToAction = getFirstValidAction( Action.GIVE_TO );
        // if the conditions are not met but there are a grabAction, useWith or giveTo defined for this item and its conditions are met, can't be use alone
        boolean grabActionPartial;
        if (grabAction == null)
            grabActionPartial = true;
        else 
            grabActionPartial = !grabAction.isConditionsAreMeet( );
        boolean useWithActionPartial;
        if (useWithAction == null)
            useWithActionPartial = true;
        else 
            useWithActionPartial = !useWithAction.isConditionsAreMeet( );
        boolean giveToActionPartial;
        if (giveToAction == null)
            giveToActionPartial = true;
        else 
            giveToActionPartial = !giveToAction.isConditionsAreMeet( );
        boolean ifUseNotMeetConditionsNeitherOther =  grabActionPartial && useWithActionPartial && giveToActionPartial;
                                                      
        // Check only for one valid use action
        for( int i = 0; i < item.getActions( ).size( ) && !canBeUsedAlone; i++ ) {
            Action action = item.getAction( i );
            
            if( action.getType( ) == Action.USE ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    canBeUsedAlone = true;   
                } else if (action.isActivatedNotEffects( ) && ifUseNotMeetConditionsNeitherOther)
                    canBeUsedAlone = true;
                
             }
        }

        return canBeUsedAlone;
    }
    
    @Override
    public boolean canBeDragged() {
        boolean canBeDragged = false;
        for (int i = 0; i < item.getActions().size( ) && !canBeDragged; i++) {
            Action action = item.getAction( i );
            if (action.getType( ) == Action.DRAG_TO) {
                if ( new FunctionalConditions( action.getConditions( )).allConditionsOk( )){
                    canBeDragged = true;
                } else if (action.isActivatedNotEffects( ))
                    canBeDragged = true;
            }
        }
        return canBeDragged;
    }

    
    
    @Override
    public Action getFirstValidAction( int actionType ) {

        for( Action action : item.getActions( ) ) {
            if( action.getType( ) == actionType ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    action.setConditionsAreMeet( true );
                    return action;
                }
            }
        }

        // if no actions can be launched (because its conditions are't OK), lunch the first action which has activated not-effects
        for( Action action : item.getActions( ) ) {
            if( action.getType( ) == actionType ) {
                if( action.isActivatedNotEffects( ) ) {
                    action.setConditionsAreMeet( false );
                    return action;
                }
            }
        }
        return null;
    }

    @Override
    public CustomAction getFirstValidCustomAction( String actionName ) {

        for( Action action : item.getActions( ) ) {
            if( action.getType( ) == Action.CUSTOM && ( (CustomAction) action ).getName( ).equals( actionName ) ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    return (CustomAction) action;
                }
            }
        }

        // if no actions can be launched (because its conditions are't OK), lunch the first action which has activated not-effects
        for( Action action : item.getActions( ) ) {
            if( action.getType( ) == Action.CUSTOM && ( (CustomAction) action ).getName( ).equals( actionName ) ) {
                if( action.isActivatedNotEffects( ) ) {
                    return (CustomAction) action;
                }
            }
        }

        return null;
    }

    @Override
    public CustomAction getFirstValidCustomInteraction( String actionName ) {

        for( Action action : item.getActions( ) ) {
            if( action.getType( ) == Action.CUSTOM_INTERACT && ( (CustomAction) action ).getName( ).endsWith( actionName ) ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    return (CustomAction) action;
                }
            }
        }
        // if no actions can be launched (because its conditions are't OK), lunch the first action which has activated not-effects
        for( Action action : item.getActions( ) ) {
            if( action.getType( ) == Action.CUSTOM_INTERACT && ( (CustomAction) action ).getName( ).endsWith( actionName ) ) {
                if( action.isActivatedNotEffects( ) ) {
                    return (CustomAction) action;
                }
            }
        }
        return null;
    }

    /**
     * Triggers the grabbing action associated with the item
     * 
     * @return True if the item was grabbed, false otherwise
     */
    public boolean grab( ) {

        boolean grabbed = false;

        // Only take the FIRST valid action
        for( int i = 0; i < item.getActions( ).size( ) && !grabbed; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.GRAB ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    // If the it has not a cancel action, grab the item
                    if( !action.getEffects( ).hasCancelAction( ) )
                        Game.getInstance( ).grabItem( item.getId( ) );

                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getEffects( ) );
                    grabbed = true;
                }
            }
        }
        // if no actions can be launched (because its conditions are't OK), lunch the first action which has activated not-effects
        for( int i = 0; i < item.getActions( ).size( ) && !grabbed; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.GRAB ) {
                if( action.isActivatedNotEffects( ) ) {
                    // When not effects are active, the item hasn't be grabbed
                    //if( !action.getEffects( ).hasCancelAction( ) )
                      //  Game.getInstance( ).grabItem( item.getId( ) );

                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getNotEffects( ) );
                    grabbed = true;
                }
            }
        }
        return grabbed;
    }

    /**
     * Triggers the use action associated with the item
     * 
     * @return True if the item was used, false otherwise
     */
    @Override
    public boolean use( ) {

        boolean used = false;

        // Only take the FIRST valid action
        for( int i = 0; i < item.getActions( ).size( ) && !used; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.USE ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getEffects( ) );
                    used = true;
                }
            }
        }
        // if no actions can be launched (because its conditions are't OK), lunch the first action which has activated not-effects
        for( int i = 0; i < item.getActions( ).size( ) && !used; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.USE ) {
                if( action.isActivatedNotEffects( ) ) {
                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getNotEffects( ) );
                    used = true;
                }
            }
        }
        return used;
    }

    public boolean custom( String actionName ) {

        boolean custom = false;

        // Only take the FIRST valid action
        for( int i = 0; i < item.getActions( ).size( ) && !custom; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.CUSTOM && ( (CustomAction) action ).getName( ).equals( actionName ) ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getEffects( ) );
                    custom = true;
                }
            }
        }
        // if no actions can be launched (because its conditions are't OK), lunch the first action which has activated not-effects
        for( int i = 0; i < item.getActions( ).size( ) && !custom; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.CUSTOM && ( (CustomAction) action ).getName( ).equals( actionName ) ) {
                if( action.isActivatedNotEffects( ) ) {
                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getNotEffects( ) );
                    custom = true;
                }
            }
        }
        return custom;
    }

    /**
     * Triggers the using with action associated with the item
     * 
     * @param anotherItem
     *            The second item necessary for the use with action
     * @return True if the items were used, false otherwise
     */
    public boolean useWith( FunctionalItem anotherItem ) {

        boolean usedWith = false;

        // Only take the FIRST valid action
        for( int i = 0; i < item.getActions( ).size( ) && !usedWith; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.USE_WITH && action.getTargetId( ).equals( anotherItem.getItem( ).getId( ) ) ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getEffects( ) );
                    usedWith = true;
                }
            }
        }
        // if no actions can be launched (because its conditions are't OK), lunch the first action which has activated not-effects
        for( int i = 0; i < item.getActions( ).size( ) && !usedWith; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.USE_WITH && action.getTargetId( ).equals( anotherItem.getItem( ).getId( ) ) ) {
                if( action.isActivatedNotEffects( ) ) {
                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getNotEffects( ) );
                    usedWith = true;
                }
            }
        }
        return usedWith;
    }

    /**
     * Triggers the drag to action associated with the item
     * 
     * @param anotherItem
     *            The second item necessary for the use with action
     * @return True if the items were used, false otherwise
     */
    public boolean dragTo( FunctionalItem anotherItem ) {
        boolean dragTo = false;

        // Only take the FIRST valid action
        for( int i = 0; i < item.getActions( ).size( ) && !dragTo; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.DRAG_TO && action.getTargetId( ).equals( anotherItem.getItem( ).getId( ) ) ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getEffects( ) );
                    dragTo = true;
                }
            }
        }
        // if no actions can be launched (because its conditions are't OK), lunch the first action which has activated not-effects
        for( int i = 0; i < item.getActions( ).size( ) && !dragTo; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.DRAG_TO && action.getTargetId( ).equals( anotherItem.getItem( ).getId( ) ) ) {
                if( action.isActivatedNotEffects( ) ) {
                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getNotEffects( ) );
                    dragTo = true;
                }
            }
        }
        return dragTo;
    }

    /**
     * Triggers the drag to action associated with the item
     * 
     * @param npc
     *            The second item necessary for the use with action
     * @return True if the items were used, false otherwise
     */
    public boolean dragTo( FunctionalNPC npc ) {
        boolean dragTo = false;

        // Only take the FIRST valid action
        for( int i = 0; i < item.getActions( ).size( ) && !dragTo; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.DRAG_TO && action.getTargetId( ).equals( npc.getNPC( ).getId( ) ) ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getEffects( ) );
                    dragTo = true;
                }
            }
        }
        // if no actions can be launched (because its conditions are't OK), lunch the first action which has activated not-effects
        for( int i = 0; i < item.getActions( ).size( ) && !dragTo; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.DRAG_TO && action.getTargetId( ).equals( npc.getNPC( ).getId( ) ) ) {
                if( action.isActivatedNotEffects( ) ) {
                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getNotEffects( ) );
                    dragTo = true;
                }
            }
        }
        return dragTo;
    }

    public boolean customInteract( String actionName, FunctionalItem anotherItem ) {

        boolean customInteract = false;

        // Only take the FIRST valid action
        for( int i = 0; i < item.getActions( ).size( ) && !customInteract; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.CUSTOM_INTERACT && ( (CustomAction) action ).getName( ).equals( actionName ) && action.getTargetId( ) != null && action.getTargetId( ).equals( anotherItem.getItem( ).getId( ) ) ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getEffects( ) );
                    customInteract = true;
                }
            }
        }
        // if no actions can be launched (because its conditions are't OK), lunch the first action which has activated not-effects
        for( int i = 0; i < item.getActions( ).size( ) && !customInteract; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.CUSTOM_INTERACT && ( (CustomAction) action ).getName( ).equals( actionName ) && action.getTargetId( ) != null && action.getTargetId( ).equals( anotherItem.getItem( ).getId( ) ) ) {
                if( action.isActivatedNotEffects( ) ) {
                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getNotEffects( ) );
                    customInteract = true;
                }
            }
        }

        return customInteract;
    }

    public boolean customInteract( String actionName, FunctionalNPC npc ) {

        boolean customInteract = false;

        // Only take the FIRST valid action
        for( int i = 0; i < item.getActions( ).size( ) && !customInteract; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.CUSTOM_INTERACT && action.getTargetId( ).equals( npc.getNPC( ).getId( ) ) && ( (CustomAction) action ).getName( ).equals( actionName ) ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getEffects( ) );
                    customInteract = true;
                }
            }
        }
        // if no actions can be launched (because its conditions are't OK), lunch the first action which has activated not-effects
        for( int i = 0; i < item.getActions( ).size( ) && !customInteract; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.CUSTOM_INTERACT && action.getTargetId( ).equals( npc.getNPC( ).getId( ) ) && ( (CustomAction) action ).getName( ).equals( actionName ) ) {
                if( action.isActivatedNotEffects( ) ) {
                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getNotEffects( ) );
                    customInteract = true;
                }
            }
        }
        return customInteract;
    }

    /**
     * Triggers the giving action associated with the item
     * 
     * @param npc
     *            The character receiver of the item
     * @return True if the item was given, false otherwise
     */
    public boolean giveTo( FunctionalNPC npc ) {

        boolean givenTo = false;

        // Only take the FIRST valid action
        for( int i = 0; i < item.getActions( ).size( ) && !givenTo; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.GIVE_TO && action.getTargetId( ).equals( npc.getElement( ).getId( ) ) ) {
                if( new FunctionalConditions( action.getConditions( ) ).allConditionsOk( ) ) {
                    // If the item has not a cancel action, consume the item
                    if( !action.getEffects( ).hasCancelAction( ) )
                        Game.getInstance( ).consumeItem( item.getId( ) );

                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getEffects( ) );
                    givenTo = true;
                }
            }
        }
        // if no actions can be launched (because its conditions are't OK), lunch the first action which has activated not-effects
        for( int i = 0; i < item.getActions( ).size( ) && !givenTo; i++ ) {
            Action action = item.getAction( i );
            if( action.getType( ) == Action.GIVE_TO && action.getTargetId( ).equals( npc.getElement( ).getId( ) ) ) {
                if( action.isActivatedNotEffects( ) ) {
                    // When not effects are active, the item hasn't be grabbed
                    //if( !action.getEffects( ).hasCancelAction( ) )
                      //  Game.getInstance( ).consumeItem( item.getId( ) );

                    // Store the effects
                    FunctionalEffects.storeAllEffects( action.getNotEffects( ) );
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
        if( newResources == null ) {
            newResources = new Resources( );
            newResources.addAsset( new Asset( Item.RESOURCE_TYPE_ICON, ResourceHandler.DEFAULT_ICON ) );
            newResources.addAsset( new Asset( Item.RESOURCE_TYPE_IMAGE, ResourceHandler.DEFAULT_IMAGE ) );
        }
        return newResources;
    }

    @Override
    public InfluenceArea getInfluenceArea( ) {

        return influenceArea;
    }

    public ElementReference getReference( ) {

        return reference;
    }
    
    /**
     * Returns the X coordinate of the left-top vertex of this item.
     * It takes into account scene offset, transparent parts, and scale.  
     * @return The Current Absolute X position of the let-top vertex of the item 
     */
    @Override
    public int getXImage(){
        return Math.round( (x + x1 * scale) - ( getWidth( ) * scale / 2 ) );
    }

    /**
     * Returns the Y coordinate of the left-top vertex of this item.
     * It takes into account transparent parts, and scale.  
     * @return The Current Absolute Y position of the let-top vertex of the item 
     */
    @Override
    public int getYImage(){
        return  Math.round( (y + y1 * scale) - getHeight( ) * scale );
    }
    
    /**
     * Returns the current height of this item.
     * It takes into account transparent parts, and scale.  
     * @return The Current height of the item 
     */
    @Override
    public int getHImage(){
        return  Math.round( (y2-y1)*scale );//Math.round( scale*getHeight());
    }
    
    /**
     * Returns the current width of this item.
     * It takes into account scene offset, transparent parts, and scale.  
     * @return The Current width of the item 
     */
    @Override
    public int getWImage(){
        return  Math.round( (x2-x1)*scale );//Math.round( scale*getWidth());
    }    
    
    //XXX INREDIS-MASTERMED
    private class ResourceTransition{
        private static final long DEFAULT_TRANSITION_TIME=1200;
        
        private Image image1;
        
        private Image image2;
        
        private BufferedImage lastUpdate;
        
        private long transitionTime;
        
        private boolean started;
        
        private boolean finished;
        
        private long elapsedTime;
        
        private int imgWidth;
        
        private int imgHeight;
        
        public ResourceTransition ( long transitionTime, Image image1, Image image2){
            this.image1=image1;
            this.image2=image2;
            this.transitionTime=transitionTime;
            started=false;
            finished=false;
            
            imgWidth = Math.max( image1==null?Integer.MIN_VALUE:image1.getWidth( null ), 
                    image2==null?Integer.MIN_VALUE:image2.getWidth( null ) );
            imgHeight = Math.max( image1==null?Integer.MIN_VALUE:image1.getHeight( null ), 
                    image2==null?Integer.MIN_VALUE:image2.getHeight( null ) );
        }
        
        public ResourceTransition(Image image1, Image image2){
            this (DEFAULT_TRANSITION_TIME,image1, image2);
        }
        
        public Image update (long elapsedTime){
            if (hasStarted()&&!hasFinished()){
                this.elapsedTime+=elapsedTime;
                float ratio = (this.elapsedTime<transitionTime)?(float)this.elapsedTime/(float)transitionTime:1.0F;
                
                lastUpdate=new BufferedImage( imgWidth, imgHeight,BufferedImage.TYPE_4BYTE_ABGR );
                Graphics2D g=lastUpdate.createGraphics( );
                if (image1!=null){
                    AlphaComposite alphaComposite1 = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 1 - ratio );
                    g.setComposite( alphaComposite1 );
                    g.drawImage( image1, 0, 0, null );
                }
                
                if (image2!=null){
                    AlphaComposite alphaComposite2 = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, ratio );
                    g.setComposite( alphaComposite2 );
                    g.drawImage( image2, 0, 0, null );
                }
                
                g.dispose( );
                
                if (ratio==1.0F)
                    finished=true;
                
                return lastUpdate;
                
            } else if (!hasStarted()){
                return image1;
            } else if (hasFinished()){
                return image2;
            }
            return null;
        }
        
        public boolean hasStarted(){
            return started;
        }
        
        public boolean hasFinished(){
            return finished;
        }
        
        public void start(){
            if (image1!=null || image2!=null)
                started=true;
        }

        public Image getLastUpdate( ) {
            if (lastUpdate!=null)
                return lastUpdate;
            else if (!hasStarted())
                return image1;
            else return image2;
        }
    }

}
