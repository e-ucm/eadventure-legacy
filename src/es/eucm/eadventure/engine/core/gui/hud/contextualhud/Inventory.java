package es.eucm.eadventure.engine.core.gui.hud.contextualhud;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * This class renders all the items of the inventory
 */
public class Inventory {
    
    /**
     * Width of the panel that contains the inventory
     */
    public static final int INVENTORY_PANEL_WIDTH = 800;

    /**
     * Height of the panel that contains the inventory
     */
    public static final int INVENTORY_PANEL_HEIGHT = 48;
    
    /**
     * Upper most point of the panel that contains the upper inventory
     */
    public static final int UPPER_INVENTORY_PANEL_Y = 0;

    /**
     * Upper most point of the panel that contains the bottom inventory
     */
    public static final int BOTTOM_INVENTORY_PANEL_Y = GUI.WINDOW_HEIGHT - INVENTORY_PANEL_HEIGHT;

    /**
     * Number of inventory items in each line of the inventory panel
     */
    public static final int INVENTORY_ITEMS_PER_LINE = 8;
    
    /**
     * Witdh of the scroll buttons
     */
    public static final int SCROLL_WIDTH = 80;
    
    /**
     * Height of the scroll buttons
     */
    public static final int SCROLL_HEIGHT = 48;
    
    /**
     * Top left position of the scroll up button
     */
    public static final int SCROLL_LEFT_X = 0;
    
    /**
     * Top left position of the scroll down button
     */
    public static final int SCROLL_RIGHT_X = INVENTORY_PANEL_WIDTH - SCROLL_WIDTH;
    
    /**
     * Left most point of the panel that contains the inventory items
     */
    public static final int ITEMS_PANEL_X = SCROLL_WIDTH;

    /**
     * Width of the panel that contains the inventory items
     */
    public static final int ITEMS_PANEL_WIDTH = INVENTORY_PANEL_WIDTH - 2*SCROLL_WIDTH;

    /**
     * Left most point of the first inventory item
     */
    public static final int FIRST_ITEM_X = ITEMS_PANEL_X;

    /**
     * Width of an inventory item
     */
    public static final int ITEM_WIDTH = 80;

    /**
     * Height of an inventory item
     */
    public static final int ITEM_HEIGHT = 48;

    /**
     * Width of the spacing between inventory items
     */
    public static final int ITEM_SPACING_X = ITEM_WIDTH;

    public static final int MIN_OFFSET = 0;
   
    /**
     * Index of the first item displayed in the inventory
     */
    private int indexFirstItemDisplayed;
    
    /**
     * Image for the left button
     */
    private Image left;
    
    /**
     * Image for the right button
     */
    private Image right;
    

    /**
     * Image for the left button highlighted
     */
    private Image lefthigh;
    
    /**
     * Image for the right button highlighted
     */
    private Image righthigh;

    /**
     * Relative Y coordinate of the inventory
     * (the less the more inventory is shown)
     */
    private double dy = (double)INVENTORY_PANEL_HEIGHT - 15.0;
    
    /**
     * Relative X coordinate of the inventory
     * (Used for the movement of the inventory to left-right)
     */
    private double dx = 0.0;
    
    /**
     * Wether the inventory is the upper one
     */
    private boolean upperInventory;

    /**
     * Indicates whether the left arrow is drawn
     */
    private boolean drawLeft;
    
    /**
     * Indicates whether the right arrow is drawn
     */
    private boolean drawRight;
    
    private boolean highlightRight;
    
    private boolean highlightLeft;
    
    /**
     * Constructor
     */
    public Inventory( ) {
        indexFirstItemDisplayed = 0;

        DescriptorData descriptor = Game.getInstance().getGameDescriptor();
        String leftpath = descriptor.getArrowPath(DescriptorData.NORMAL_ARROW_LEFT);
        String rightpath = descriptor.getArrowPath(DescriptorData.NORMAL_ARROW_RIGHT);
        if (leftpath == null)
        	left = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/left.png", MultimediaManager.IMAGE_MENU );
        else
        	left = MultimediaManager.getInstance().loadImageFromZip(leftpath, MultimediaManager.IMAGE_MENU );
        if (rightpath == null)
        	right = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/right.png", MultimediaManager.IMAGE_MENU );
        else
        	right = MultimediaManager.getInstance().loadImageFromZip(rightpath, MultimediaManager.IMAGE_MENU );

        leftpath = descriptor.getArrowPath(DescriptorData.HIGHLIGHTED_ARROW_LEFT);
        rightpath = descriptor.getArrowPath(DescriptorData.HIGHLIGHTED_ARROW_RIGHT);
        if (leftpath == null)
        	lefthigh = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/left.png", MultimediaManager.IMAGE_MENU );
        else
        	lefthigh = MultimediaManager.getInstance().loadImageFromZip(leftpath, MultimediaManager.IMAGE_MENU );
        if (rightpath == null)
        	righthigh = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/right.png", MultimediaManager.IMAGE_MENU );
        else
        	righthigh = MultimediaManager.getInstance().loadImageFromZip(rightpath, MultimediaManager.IMAGE_MENU );

        
        upperInventory = false;
    }
    
    /**
     * Wether the inventory is the upper one
     * @return true if the inventory is the upper one
     */
    public boolean isUpperInventory(){
        return upperInventory;
    }
    
    /**
     * Set the upper inventory or the lower one
     * @param upper If the inventory is the upper one
     */
    public void setUpperInventory(boolean upper){
        upperInventory = upper;
    }

    /**
     * Scrolls the inventory left
     */
    private void scrollInventoryLeft( ) {
        if( (dx == 0.0) && (indexFirstItemDisplayed > 0) ) {
            dx = 1.0;
        }
    }

    /**
     * Scrolls the inventory right
     */
    private void scrollInventoryRight( ) {
        if( (dx == 0.0) && (Game.getInstance( ).getInventory( ).getItemCount( ) > indexFirstItemDisplayed + INVENTORY_ITEMS_PER_LINE) ) {
            dx = -1.0;
        }
    }

    /**
     * Draws the inventory
     * @param g Graphics to draw the inventory
     */
    public void draw( Graphics2D g ) {
        int indexLastItemDisplayed;

        BufferedImage inventory = new BufferedImage(INVENTORY_PANEL_WIDTH, INVENTORY_PANEL_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g_inv = (Graphics2D) inventory.getGraphics();
        Composite temp = g_inv.getComposite();
        Color tempColor = g_inv.getColor();
        Composite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
		g_inv.setComposite(alphaComposite);
		g_inv.setColor(Color.DARK_GRAY);
		
		g_inv.fillRect(0, 0, INVENTORY_PANEL_WIDTH, INVENTORY_PANEL_HEIGHT);
		
    	g_inv.setComposite(temp);
    	g_inv.setColor(tempColor);
		
    	
    	drawLeft = false;
    	drawRight = false;
    	int itemCount = Game.getInstance().getInventory().getItemCount();
        if (itemCount < INVENTORY_ITEMS_PER_LINE) {
        	indexFirstItemDisplayed = 0;
        	indexLastItemDisplayed = Game.getInstance().getInventory().getItemCount() - 1;
        } else {
        	if (indexFirstItemDisplayed > 0)
        		drawLeft = true;
        	if (itemCount - indexFirstItemDisplayed >  INVENTORY_ITEMS_PER_LINE) {
        		drawRight = true;
        		indexLastItemDisplayed = indexFirstItemDisplayed + INVENTORY_ITEMS_PER_LINE;
        	} else
        		indexLastItemDisplayed = itemCount - 1; 
        }
        
        int x = FIRST_ITEM_X + (int)dx;

        g_inv.setClip(SCROLL_WIDTH, 0, ITEMS_PANEL_WIDTH, INVENTORY_PANEL_HEIGHT);
        
        int firstItem = indexFirstItemDisplayed - 1;
        if( firstItem < 0 ) firstItem = 0;
        else x -= ITEM_WIDTH;
        int lastItem = indexLastItemDisplayed + 1;
        if( lastItem > Game.getInstance( ).getInventory( ).getItemCount( ) ) lastItem = Game.getInstance( ).getInventory( ).getItemCount( );
        for( int i = firstItem; i < lastItem; i++ ) {
            g_inv.drawImage( Game.getInstance( ).getInventory( ).getItem( i ).getIconImage( ), x, 0, null );
            x += ITEM_SPACING_X;
        }
        
        g_inv.setClip(0,0,INVENTORY_PANEL_WIDTH, INVENTORY_PANEL_HEIGHT);


        if (drawLeft)
        	g_inv.drawImage( (highlightLeft ? lefthigh : left), 0, 0, null );
        if (drawRight)
           	g_inv.drawImage( (highlightRight ? righthigh : right), INVENTORY_PANEL_WIDTH - SCROLL_WIDTH, 0, null );
    	
        g_inv.finalize();
        
       	g.drawImage(inventory, 0, (int) (upperInventory ? -dy : GUI.WINDOW_HEIGHT - SCROLL_HEIGHT + dy), null);
    }
    
    /**
     * Tells the inventory that a click has been performed
     * @param x X position of the click
     * @param y Y position of the click
     */
    public FunctionalElement mouseClicked( MouseEvent e ) {
        FunctionalElement element = null;
        int inventory_y = (!upperInventory ? BOTTOM_INVENTORY_PANEL_Y : UPPER_INVENTORY_PANEL_Y);
        
        if (e.getY() <= inventory_y || e.getY( ) >= inventory_y + INVENTORY_PANEL_HEIGHT )
        	return null;
        
        if ( e.getX( ) > ITEMS_PANEL_X && e.getX( ) < ITEMS_PANEL_X + ITEMS_PANEL_WIDTH ){
            int indexLastItemDisplayed;
         
            if( indexFirstItemDisplayed + INVENTORY_ITEMS_PER_LINE > Game.getInstance( ).getInventory( ).getItemCount( ) )
                indexLastItemDisplayed = Game.getInstance( ).getInventory( ).getItemCount( );
            else
                indexLastItemDisplayed = indexFirstItemDisplayed + INVENTORY_ITEMS_PER_LINE;
        
            int indexX = FIRST_ITEM_X;

            for( int i = indexFirstItemDisplayed; i < indexLastItemDisplayed; i++ ) {
                if( indexX < e.getX( ) && e.getX( ) < indexX + ITEM_WIDTH ) {
               		element = Game.getInstance( ).getInventory( ).getItem( i );                	
                }
                indexX += ITEM_SPACING_X;
            }
        } else {
            if( e.getX( ) > SCROLL_LEFT_X && e.getX( ) < SCROLL_LEFT_X + SCROLL_WIDTH )
                scrollInventoryLeft();
            else if( e.getX( ) > SCROLL_RIGHT_X && e.getX( ) < SCROLL_RIGHT_X + SCROLL_WIDTH )
                scrollInventoryRight();
        }
        return element;
    }

    /**
     * Tells the mouse has been moved over the inventory
     * @param x X position of the click
     * @param y Y position of the click
     */
    public void mouseMoved( MouseEvent e ) {
        int indexLastItemDisplayed;
        FunctionalElement element = null;

        int inventory_y = (!upperInventory ? BOTTOM_INVENTORY_PANEL_Y : UPPER_INVENTORY_PANEL_Y);
        highlightLeft = false;
        highlightRight = false;
        
        if (e.getY() <= inventory_y || e.getY( ) >= inventory_y + INVENTORY_PANEL_HEIGHT )
        	return;
        
        if ( e.getX( ) > ITEMS_PANEL_X && e.getX( ) < ITEMS_PANEL_X + ITEMS_PANEL_WIDTH ){         
            if( indexFirstItemDisplayed + INVENTORY_ITEMS_PER_LINE > Game.getInstance( ).getInventory( ).getItemCount( ) )
                indexLastItemDisplayed = Game.getInstance( ).getInventory( ).getItemCount( );
            else
                indexLastItemDisplayed = indexFirstItemDisplayed + INVENTORY_ITEMS_PER_LINE;
        
            int indexX = FIRST_ITEM_X;

            for( int i = indexFirstItemDisplayed; i < indexLastItemDisplayed; i++ ) {
                if( indexX < e.getX( ) && e.getX( ) < indexX + ITEM_WIDTH ) {
               		element = Game.getInstance( ).getInventory( ).getItem( i );                	
                }
                indexX += ITEM_SPACING_X;
            }
        } else {
            if( e.getX( ) > SCROLL_LEFT_X && e.getX( ) < SCROLL_LEFT_X + SCROLL_WIDTH )
            	highlightLeft = true;
            else if( e.getX( ) > SCROLL_RIGHT_X && e.getX( ) < SCROLL_RIGHT_X + SCROLL_WIDTH )
            	highlightRight = true;
        }
        
        Game.getInstance( ).getActionManager( ).setElementOver( element );
    }
    
    /**
     * Set the vertical position of the inventory to show/hide it
     * @param dy Vertical position of the inventory
     */
    public void setDY( double dy ) {
        if( dy > (double)INVENTORY_PANEL_HEIGHT - MIN_OFFSET )
            dy = (double)INVENTORY_PANEL_HEIGHT - MIN_OFFSET;
        if( dy < 0.0 )
            dy = 0.0;
        this.dy = dy;
    }
    
    /**
     * Returns the actual vertical position of the inventory
     * @return vertical position of the inventory
     */
    public double getDY() {
        return dy;
    }
    
    /**
     * Set the horizontal position of the inventory
     * @param dx Horizontal position of the inventory
     */
    public void setDX( double dx ) {
        if( dx < -ITEM_WIDTH ) {
        	indexFirstItemDisplayed++;
            dx = 0.0;
        }
        if( dx > ITEM_WIDTH ) {
            indexFirstItemDisplayed--;
            dx = 0.0;
        }
        this.dx = dx;
    }
    
    /**
     * Update the inventory
     * @param elapsedTime Time betweens updates
     */
    public void update( long elapsedTime ) {
        if( dx > 0.0 )
            setDX( dx + (double)elapsedTime / 5.0 );
        if( dx < 0.0 )
            setDX( dx - (double)elapsedTime / 5.0 );
    }
    
}

