package es.eucm.eadventure.engine.core.gui.hud.contextualhud;

import java.awt.Graphics2D;
import java.awt.Image;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

public class ActionButton {
	
    /**
     * Width of an action button
     */
    public static final int ACTIONBUTTON_WIDTH = 40;

    /**
     * Height of an action button
     */
    public static final int ACTIONBUTTON_HEIGHT = 40;

	/**
	 * Constant that represent the hand button
	 */
	public static final int HAND_BUTTON = 0;
	
	/**
	 * Constant that represents the eye button
	 */
	public static final int EYE_BUTTON = 1;
	
	/**
	 * Constant that represents the mouth button
	 */
	public static final int MOUTH_BUTTON = 2;
	
	
	/**
	 * Image of the button in it's normal state
	 */
	private Image buttonNormal;
	
	/**
	 * Image of the button when it's pressed
	 */
	private Image buttonPressed;
	
	/**
	 * Image of the button when it has the mouse over
	 */
	private Image buttonOver;
	
	/**
	 * Position of the button in the x-axis
	 */
	private int posX;
	
	/**
	 * Position of the button in the y-axis
	 */
	private int posY;
	
	/**
	 * Boolean that indicates the mouse is over the button
	 */
	private boolean over;
	
	/**
	 * Boolean that indicates that the button is being pressed with the mouse
	 */
	private boolean pressed;
	
	/**
	 * Custom action of the button
	 */
	private CustomAction customAction;
	
	/**
	 * Name of the action represeted by the button
	 */
	private String actionName;
	
	/**
	 * The type of the button
	 */
	private int type;
	
	/**
	 * Construct a button form it's type
	 * 
	 * @param type the type of the button
	 */
	public ActionButton(int type) {
		
		DescriptorData descriptor = Game.getInstance().getGameDescriptor();
		
		String customNormalPath = null; 
		String customHighlightedPath = null;
		String customPressedPath = null;
		
		
		switch(type) {
		case HAND_BUTTON:
			customNormalPath = descriptor.getButtonPath(DescriptorData.USE_GRAB_BUTTON, DescriptorData.NORMAL_BUTTON); 
			customHighlightedPath = descriptor.getButtonPath(DescriptorData.USE_GRAB_BUTTON, DescriptorData.HIGHLIGHTED_BUTTON);
			customPressedPath = descriptor.getButtonPath(DescriptorData.USE_GRAB_BUTTON, DescriptorData.PRESSED_BUTTON);
			
			if (customNormalPath==null)
				buttonNormal = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnHand.png", MultimediaManager.IMAGE_MENU );
			else
				buttonNormal = MultimediaManager.getInstance( ).loadImageFromZip( customNormalPath, MultimediaManager.IMAGE_MENU );
			
			if (customHighlightedPath==null)
				buttonOver = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnHandHighlighted.png", MultimediaManager.IMAGE_MENU );
			else
				buttonOver = MultimediaManager.getInstance( ).loadImageFromZip( customHighlightedPath, MultimediaManager.IMAGE_MENU );
			
			if (customPressedPath==null)
				buttonPressed = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnHandPressed.png", MultimediaManager.IMAGE_MENU );
			else
				buttonPressed = MultimediaManager.getInstance( ).loadImageFromZip( customPressedPath, MultimediaManager.IMAGE_MENU );
			
            break;
		case EYE_BUTTON:
			customNormalPath = descriptor.getButtonPath(DescriptorData.EXAMINE_BUTTON, DescriptorData.NORMAL_BUTTON); 
			customHighlightedPath = descriptor.getButtonPath(DescriptorData.EXAMINE_BUTTON, DescriptorData.HIGHLIGHTED_BUTTON);
			customPressedPath = descriptor.getButtonPath(DescriptorData.EXAMINE_BUTTON, DescriptorData.PRESSED_BUTTON);
			
			if (customNormalPath==null)
				buttonNormal = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnEye.png", MultimediaManager.IMAGE_MENU );
			else
				buttonNormal = MultimediaManager.getInstance( ).loadImageFromZip( customNormalPath, MultimediaManager.IMAGE_MENU );
			
			if (customHighlightedPath==null)
				buttonOver = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnEyeHighlighted.png", MultimediaManager.IMAGE_MENU );
			else
				buttonOver = MultimediaManager.getInstance( ).loadImageFromZip( customHighlightedPath, MultimediaManager.IMAGE_MENU );
			
			if (customPressedPath==null)
				buttonPressed = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnEyePressed.png", MultimediaManager.IMAGE_MENU );
			else
				buttonPressed = MultimediaManager.getInstance( ).loadImageFromZip( customPressedPath, MultimediaManager.IMAGE_MENU );
			
			break;
		case MOUTH_BUTTON:
			customNormalPath = descriptor.getButtonPath(DescriptorData.TALK_BUTTON, DescriptorData.NORMAL_BUTTON); 
			customHighlightedPath = descriptor.getButtonPath(DescriptorData.TALK_BUTTON, DescriptorData.HIGHLIGHTED_BUTTON);
			customPressedPath = descriptor.getButtonPath(DescriptorData.TALK_BUTTON, DescriptorData.PRESSED_BUTTON);
			if (customNormalPath==null)
				buttonNormal = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnMouth.png", MultimediaManager.IMAGE_MENU );
			else
				buttonNormal = MultimediaManager.getInstance( ).loadImageFromZip( customNormalPath, MultimediaManager.IMAGE_MENU );
			
			if (customHighlightedPath==null)
				buttonOver = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnMouthHighlighted.png", MultimediaManager.IMAGE_MENU );
			else
				buttonOver = MultimediaManager.getInstance( ).loadImageFromZip( customHighlightedPath, MultimediaManager.IMAGE_MENU );
			
			if (customPressedPath==null)
				buttonPressed = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnMouthPressed.png", MultimediaManager.IMAGE_MENU );
			else
				buttonPressed = MultimediaManager.getInstance( ).loadImageFromZip( customPressedPath, MultimediaManager.IMAGE_MENU );
			
			break;
		}
		actionName = "";
		this.type = type;
	}

	/**
	 * Construct a button from a custom action
	 * 
	 * @param action the custom action
	 */
	public ActionButton(CustomAction action) {
		actionName = action.getName();
		customAction = action;

        Resources resources = null;
        for( int i = 0; i < action.getResources( ).size( ) && resources == null; i++ )
            if( new FunctionalConditions(action.getResources( ).get( i ).getConditions( )).allConditionsOk( ) )
            	resources = action.getResources( ).get( i );

        if (resources.getAssetPath("buttonNormal") != null)
        	buttonNormal = ResourceHandler.getInstance().getResourceAsImageFromZip(resources.getAssetPath("buttonNormal"));
        if (resources.getAssetPath("buttonOver") != null)
        	buttonOver = ResourceHandler.getInstance().getResourceAsImageFromZip(resources.getAssetPath("buttonOver"));
        if (resources.getAssetPath("buttonPressed") != null)
        	buttonPressed = ResourceHandler.getInstance().getResourceAsImageFromZip(resources.getAssetPath("buttonPressed"));
		
		
		if (buttonPressed == null || buttonOver == null || buttonNormal == null)
			fixButtons();
		
		buttonNormal = scaleButton(buttonNormal);
		buttonOver = scaleButton(buttonOver);
		buttonPressed = scaleButton(buttonPressed);
		
		this.type = ActionButtons.ACTIONBUTTON_CUSTOM;
	}
	
	private void fixButtons() {
		if (buttonNormal == null && buttonOver == null && buttonPressed == null) {
            buttonNormal = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnHand.png", MultimediaManager.IMAGE_MENU );
            buttonOver = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnHandHighlighted.png", MultimediaManager.IMAGE_MENU );
            buttonPressed = MultimediaManager.getInstance( ).loadImage( "gui/hud/contextual/btnHandPressed.png", MultimediaManager.IMAGE_MENU );
		} 
		if (buttonNormal == null) {
			if (buttonOver != null)
				buttonNormal = buttonOver;
			else if (buttonPressed != null)
				buttonNormal = buttonPressed;
		}
		if (buttonOver == null) {
			if (buttonPressed != null)
				buttonOver = buttonPressed;
			else if (buttonNormal != null)
				buttonOver = buttonNormal;
		}
		if (buttonPressed == null) {
			if (buttonOver != null)
				buttonPressed = buttonOver;
			if (buttonNormal != null)
				buttonPressed = buttonNormal;
		}
	}

	/**
	 * Scale an image of a button
	 * 
	 * @param button the image to scale
	 * @return the scaled image
	 */
	private Image scaleButton(Image button) {		
		if (button.getWidth(null) > ActionButtons.MAX_BUTTON_WIDTH)
			button = button.getScaledInstance(ActionButtons.MAX_BUTTON_WIDTH, button.getHeight(null), Image.SCALE_SMOOTH);
		if (button.getWidth(null) < ActionButtons.MIN_BUTTON_WIDTH)
			button = button.getScaledInstance(ActionButtons.MIN_BUTTON_WIDTH, button.getHeight(null), Image.SCALE_SMOOTH);
		if (button.getHeight(null) > ActionButtons.MAX_BUTTON_HEIGHT)
			button = button.getScaledInstance(button.getWidth(null), ActionButtons.MAX_BUTTON_HEIGHT, Image.SCALE_SMOOTH);
		if (button.getHeight(null) < ActionButtons.MIN_BUTTON_HEIGHT)
			button = button.getScaledInstance(button.getWidth(null), ActionButtons.MIN_BUTTON_HEIGHT, Image.SCALE_SMOOTH);
		return button;
	}

	/**
	 * @return the buttonNormal
	 */
	public Image getButtonNormal() {
		return buttonNormal;
	}

	/**
	 * @return the buttonPressed
	 */
	public Image getButtonPressed() {
		return buttonPressed;
	}

	/**
	 * @return the buttonOver
	 */
	public Image getButtonOver() {
		return buttonOver;
	}

	/**
	 * @param posX the posX to set
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * @param posY the posY to set
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void draw(Graphics2D g) {
		int x = (posX - ACTIONBUTTON_WIDTH / 2);
		int y = posY - ACTIONBUTTON_HEIGHT / 2;
		if (pressed)
			g.drawImage(buttonNormal,x ,y , null );
		else if (over)
			g.drawImage(buttonOver, x, y, null);
		else
			g.drawImage(buttonNormal, x, y, null);
	}
	
	public String getName() {
		return actionName;
	}
	
	public int getType() {
		return type;
	}

	/**
	 * Returns true if the coordinates are inside the button
	 * 
	 * @param x the x-axis value
	 * @param y the y-axis value
	 * @return true if inside the button
	 */
	public boolean isInside(int x, int y) {
       if ( x > ( posX - ACTIONBUTTON_WIDTH / 2 ) 
              && x < ( posX + ACTIONBUTTON_WIDTH / 2 ) 
              && y > ( posY - ACTIONBUTTON_HEIGHT / 2 ) 
              && y < ( posY + ACTIONBUTTON_HEIGHT / 2 ) )
    	   return true;
       return false;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	
	public void setOver(boolean over) {
		this.over = over;
	}
	
	public CustomAction getCustomAction() {
		return customAction;
	}
}
