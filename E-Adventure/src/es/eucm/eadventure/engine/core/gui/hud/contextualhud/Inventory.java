package es.eucm.eadventure.engine.core.gui.hud.contextualhud;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;

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
     * Left most point of the panel that contains the inventory
     */
    public static final int INVENTORY_PANEL_X = 0;

    /**
     * Upper most point of the panel that contains the upper inventory
     */
    public static final int UPPER_INVENTORY_PANEL_Y = 0;

    /**
     * Upper most point of the panel that contains the bottom inventory
     */
    public static final int BOTTOM_INVENTORY_PANEL_Y = GUI.WINDOW_HEIGHT - INVENTORY_PANEL_HEIGHT;

    
    /**
     * Number of inventory lines in the inventory panel
     */
    public static final int INVENTORY_LINES = 1;

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
    public static final int SCROLL_LEFT_X = INVENTORY_PANEL_X;
    
    /**
     * Top left position of the uppder scroll up button 
     */
    public static final int UPPER_SCROLL_LEFT_Y = UPPER_INVENTORY_PANEL_Y;
    
    /**
     * Top left position of the bottom scroll up button 
     */
    public static final int BOTTOM_SCROLL_LEFT_Y = BOTTOM_INVENTORY_PANEL_Y;
    
    /**
     * Top left position of the scroll down button
     */
    public static final int SCROLL_RIGHT_X = INVENTORY_PANEL_X + INVENTORY_PANEL_WIDTH - SCROLL_WIDTH;
    
    /**
     * Top left position of the upper scroll down button
     */
    public static final int UPPER_SCROLL_RIGHT_Y = UPPER_INVENTORY_PANEL_Y;
    
    /**
     * Top left position of the bottom scroll down button
     */
    public static final int BOTTOM_SCROLL_RIGHT_Y = BOTTOM_INVENTORY_PANEL_Y;

    /**
     * Left most point of the panel that contains the inventory items
     */
    public static final int ITEMS_PANEL_X = INVENTORY_PANEL_X + SCROLL_WIDTH;

    /**
     * Upper most point of the panel that contains the upper inventory items
     */
    public static final int UPPER_ITEMS_PANEL_Y = UPPER_INVENTORY_PANEL_Y;
    
    /**
     * Upper most point of the panel that contains the bottom inventory items
     */
    public static final int BOTTOM_ITEMS_PANEL_Y = BOTTOM_INVENTORY_PANEL_Y;

    /**
     * Width of the panel that contains the inventory items
     */
    public static final int ITEMS_PANEL_WIDTH = INVENTORY_PANEL_WIDTH - 2*SCROLL_WIDTH;

    /**
     * Height of the panel that contains the inventory items
     */
    public static final int ITEMS_PANEL_HEIGHT = INVENTORY_PANEL_HEIGHT;

    /**
     * Left most point of the first inventory item
     */
    public static final int FIRST_ITEM_X = ITEMS_PANEL_X;

    /**
     * Upper most point of the first upper inventory item
     */
    public static final int UPPER_FIRST_ITEM_Y = UPPER_ITEMS_PANEL_Y;
    
    /**
     * Upper most point of the first bottom inventory item
     */
    public static final int BOTTOM_FIRST_ITEM_Y = BOTTOM_ITEMS_PANEL_Y;

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

    /**
     * Height of the spacing between inventory items
     */
    public static final int ITEM_SPACING_Y = ITEM_HEIGHT;

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
    
    /**
     * Constructor
     */
    public Inventory( ) {
        indexFirstItemDisplayed = 0;
        //If the GUI is customized
        if( Game.getInstance().getGameDescriptor().isGUICustomized() ) {
            //Load the left and right custom buttons
            
            left = MultimediaManager.getInstance( ).loadImageFromZip( "gui/hud/contextual/left.png", MultimediaManager.IMAGE_MENU );
            
            // IF the image has not been customized, use defaults
            if (left == null){
                left = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/left.png", MultimediaManager.IMAGE_MENU );
            }
            
            right = MultimediaManager.getInstance( ).loadImageFromZip( "gui/hud/contextual/right.png", MultimediaManager.IMAGE_MENU );
            // IF the image has not been customized, use defaults
            if (right == null){
                right = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/right.png", MultimediaManager.IMAGE_MENU );
            }

        } 
        //if not
        else {
            //load the default ones
            left = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/left.png", MultimediaManager.IMAGE_MENU );
            right = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/right.png", MultimediaManager.IMAGE_MENU );
        }
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
        if( (dx == 0.0) && (Game.getInstance( ).getInventory( ).getItemCount( ) > indexFirstItemDisplayed + INVENTORY_ITEMS_PER_LINE * INVENTORY_LINES) ) {
            dx = -1.0;
        }
    }

    /**
     * Draws the inventory
     * @param g Graphics to draw the inventory
     */
    public void draw( Graphics2D g ) {
        
        int indexLastItemDisplayed;
        
        Composite temp = g.getComposite();
		Color tempColor = g.getColor();
        Composite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
		g.setComposite(alphaComposite);
		g.setColor(Color.DARK_GRAY);
		if (upperInventory) {
			g.fillRect(0, (int) -dy, GUI.WINDOW_WIDTH, INVENTORY_PANEL_HEIGHT);
        } else {
			g.fillRect(0, GUI.WINDOW_HEIGHT - INVENTORY_PANEL_HEIGHT + (int) dy, GUI.WINDOW_WIDTH, INVENTORY_PANEL_HEIGHT);        	
        }
    	g.setComposite(temp);
    	g.setColor(tempColor);

        
        if (Game.getInstance().getInventory().getItemCount() < INVENTORY_LINES * INVENTORY_ITEMS_PER_LINE) {
        	indexFirstItemDisplayed = 0;
        	drawLeft = false;
        	drawRight = false;
        	indexLastItemDisplayed = Game.getInstance().getInventory().getItemCount() - 1;
        } else {
        	if (indexFirstItemDisplayed > 0) {
        		drawLeft = true;
        	} else
        		drawLeft = false;
        	if (Game.getInstance().getInventory().getItemCount() - indexFirstItemDisplayed >  INVENTORY_LINES * INVENTORY_ITEMS_PER_LINE) {
        		drawRight = true;
        		indexLastItemDisplayed = indexFirstItemDisplayed + INVENTORY_LINES * INVENTORY_ITEMS_PER_LINE;
        	} else {
        		indexLastItemDisplayed = Game.getInstance().getInventory().getItemCount() - 1;
        		drawRight = false;
        	}
        }
        if (dx < 0 && indexFirstItemDisplayed == 0) {
        	drawLeft = true;
        }
        if (dx > 0 && (indexLastItemDisplayed - indexFirstItemDisplayed) + 2> INVENTORY_LINES * INVENTORY_ITEMS_PER_LINE )
        	drawRight = true;
        
        int x = FIRST_ITEM_X + (int)dx;
        int y = BOTTOM_FIRST_ITEM_Y + (int)dy;
        if( upperInventory )y = UPPER_FIRST_ITEM_Y - (int)dy;
        int firstItem = indexFirstItemDisplayed - 1;
        if( firstItem < 0 ) firstItem = 0;
        else x -= ITEM_WIDTH;
        int lastItem = indexLastItemDisplayed + 1;
        if( lastItem > Game.getInstance( ).getInventory( ).getItemCount( ) ) lastItem = Game.getInstance( ).getInventory( ).getItemCount( );
        //for( int i = indexFirstItemDisplayed; i < indexLastItemDisplayed; i++ ) {
        for( int i = firstItem; i < lastItem; i++ ) {
            g.drawImage( Game.getInstance( ).getInventory( ).getItem( i ).getIconImage( ), x, y, null );
            x += ITEM_SPACING_X;
        }
        
        if( upperInventory ){
        	if (drawLeft)
        		g.drawImage( left, SCROLL_LEFT_X, UPPER_SCROLL_LEFT_Y - (int)dy, null );
            if (drawRight)
            	g.drawImage( right, SCROLL_RIGHT_X, UPPER_SCROLL_RIGHT_Y - (int)dy, null );
        }else{
        	if (drawLeft)
        		g.drawImage( left, SCROLL_LEFT_X, BOTTOM_SCROLL_LEFT_Y + (int)dy, null );
            if (drawRight)
            	g.drawImage( right, SCROLL_RIGHT_X, BOTTOM_SCROLL_RIGHT_Y + (int)dy, null );
        }
    }
    
    /**
     * Tells the inventory that a click has been performed
     * @param x X position of the click
     * @param y Y position of the click
     */
    public FunctionalElement mouseClicked( MouseEvent e ) {
        FunctionalElement element = null;
        int items_panel_y = BOTTOM_ITEMS_PANEL_Y;
        if( upperInventory ) items_panel_y = UPPER_ITEMS_PANEL_Y;
        if ( e.getX( ) > ITEMS_PANEL_X && e.getX( ) < ITEMS_PANEL_X + ITEMS_PANEL_WIDTH && e.getY( ) > items_panel_y && e.getY( ) < items_panel_y + ITEMS_PANEL_HEIGHT ){
            
            int indexLastItemDisplayed;
         
            if( indexFirstItemDisplayed + INVENTORY_LINES * INVENTORY_ITEMS_PER_LINE > Game.getInstance( ).getInventory( ).getItemCount( ) )
                indexLastItemDisplayed = Game.getInstance( ).getInventory( ).getItemCount( );
            else
                indexLastItemDisplayed = indexFirstItemDisplayed + INVENTORY_LINES * INVENTORY_ITEMS_PER_LINE;
        
            int indexX = FIRST_ITEM_X;
            int indexY = BOTTOM_FIRST_ITEM_Y;
            if( upperInventory ) indexY = UPPER_FIRST_ITEM_Y;
            for( int i = indexFirstItemDisplayed; i < indexLastItemDisplayed; i++ ) {
        
                if( indexX < e.getX( ) && e.getX( ) < indexX + ITEM_WIDTH && indexY < e.getY( ) && e.getY( ) < indexY + ITEM_HEIGHT )
                    element = Game.getInstance( ).getInventory( ).getItem( i );
        
                if( indexX == FIRST_ITEM_X + ITEM_SPACING_X * (INVENTORY_ITEMS_PER_LINE-1) ) {
                    indexX = FIRST_ITEM_X;
                    indexY += ITEM_SPACING_Y;
                } else
                    indexX += ITEM_SPACING_X;
            }
        
        } else {
            
            int scroll_left_y = BOTTOM_SCROLL_LEFT_Y;
            int scroll_right_y = BOTTOM_SCROLL_RIGHT_Y;
            if( upperInventory ){ 
                scroll_left_y = UPPER_SCROLL_LEFT_Y;
                scroll_right_y = UPPER_SCROLL_RIGHT_Y;
            }
            if( e.getY( ) > scroll_left_y && e.getY( ) < scroll_left_y + SCROLL_HEIGHT && e.getX( ) > SCROLL_LEFT_X && e.getX( ) < SCROLL_LEFT_X + SCROLL_WIDTH )
                scrollInventoryLeft();
            else if( e.getY( ) > scroll_right_y && e.getY( ) < scroll_right_y + SCROLL_HEIGHT && e.getX( ) > SCROLL_RIGHT_X && e.getX( ) < SCROLL_RIGHT_X + SCROLL_WIDTH )
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

        if( indexFirstItemDisplayed + INVENTORY_LINES * INVENTORY_ITEMS_PER_LINE > Game.getInstance( ).getInventory( ).getItemCount( ) )
            indexLastItemDisplayed = Game.getInstance( ).getInventory( ).getItemCount( );
        else
            indexLastItemDisplayed = indexFirstItemDisplayed + INVENTORY_LINES * INVENTORY_ITEMS_PER_LINE;

        int indexX = FIRST_ITEM_X;
        int indexY = BOTTOM_FIRST_ITEM_Y;
        if( upperInventory ) indexY = UPPER_FIRST_ITEM_Y;
        for( int i = indexFirstItemDisplayed; i < indexLastItemDisplayed; i++ ) {

            if( indexX < e.getX( ) && e.getX( ) < indexX + ITEM_WIDTH && indexY < e.getY( ) && e.getY( ) < indexY + ITEM_HEIGHT )
                element = Game.getInstance( ).getInventory( ).getItem( i );

            if( indexX == FIRST_ITEM_X + ITEM_SPACING_X * (INVENTORY_ITEMS_PER_LINE-1) ) {
                indexX = FIRST_ITEM_X;
                indexY += ITEM_SPACING_Y;
            } else
                indexX += ITEM_SPACING_X;
        }

        if( element != null )
            Game.getInstance( ).getActionManager( ).setElementOver( element );
        else
            Game.getInstance( ).getActionManager( ).setElementOver( null );
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
            //if( indexFirstItemDisplayed > 1 )
                indexFirstItemDisplayed++;
            dx = 0.0;
        }
        if( dx > ITEM_WIDTH ) {
            //if( Game.getInstance( ).getInventory( ).getItemCount( ) > indexFirstItemDisplayed + INVENTORY_ITEMS_PER_LINE * INVENTORY_LINES )
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

