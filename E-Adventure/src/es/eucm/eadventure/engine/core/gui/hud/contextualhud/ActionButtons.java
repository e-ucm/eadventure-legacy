package es.eucm.eadventure.engine.core.gui.hud.contextualhud;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;
import es.eucm.eadventure.engine.core.gui.GUI;

/**
 * This class contains all the graphical information about the action buttons
 */
public class ActionButtons {

    /**
     * Hand index action button
     */
    public static final int ACTIONBUTTON_HAND = 0;

    /**
     * Eye index action button
     */
    public static final int ACTIONBUTTON_EYE = 1;

    /**
     * Mouth index action button
     */
    public static final int ACTIONBUTTON_MOUTH = 2;

	public static final int ACTIONBUTTON_CUSTOM = 3;

	public static final int MAX_BUTTON_WIDTH = 50;
	
	public static final int MAX_BUTTON_HEIGHT = 50;
	
	public static final int MIN_BUTTON_WIDTH = 35;
	
	public static final int MIN_BUTTON_HEIGHT = 35;
	
	private double radius = 50;
    
    /**
     * X coordinate of the center of the action buttons
     */
    private int centerX;

    /**
     * Y coordinate of the center of the action buttons
     */
    private int centerY;

    /**
     * Index of the overed action button
     */
    private ActionButton buttonOver;

    /**
     * Index of the pressed action button
     */
    private ActionButton buttonPressed;

    private List<ActionButton> buttons;
    
    /*
     * Default action buttons, so they don't have to be generated each time
     */
    private ActionButton handButton;
    private ActionButton mouthButton;
    private ActionButton eyeButton;
    
    /**
     * Constructor of the class.
     * Requires that the MultimediaManager class is loaded.
     * @param customized True if the graphics of the HUD are customized, false otherwise
     */
    public ActionButtons( boolean customized ) {
    	buttons = new ArrayList<ActionButton>();
    	
        //No action button is overed or pressed
        buttonOver = null;
        buttonPressed = null;
        
        handButton = new ActionButton(ActionButton.HAND_BUTTON);
        mouthButton = new ActionButton(ActionButton.MOUTH_BUTTON);
        eyeButton = new ActionButton(ActionButton.EYE_BUTTON);
    }

    
	/**
	 * Recreates the list of action buttons, depending of the type
	 * of the element.
	 * 
	 * @param functionalElement
	 */
	public void recreate(int posX, int posY, FunctionalElement functionalElement) {
    	centerX = posX;
    	centerY = posY;
		buttons.clear();
		if (functionalElement instanceof FunctionalItem) {
			FunctionalItem item = (FunctionalItem) functionalElement;
			addDefaultObjectButtons(item);
			addCustomActionButtons(((FunctionalItem) functionalElement).getItem().getActions(), functionalElement);
		}
		if (functionalElement instanceof FunctionalNPC) {
			addDefaultCharacterButtons();
			addCustomActionButtons(((FunctionalNPC) functionalElement).getNPC().getActions(), functionalElement);
		}
		recalculateButtonsPositions();
		clearButtons();
	}
	
	/**
	 * Method that adds the necessary custom action buttons to the list
	 * of buttons
	 * 
	 * @param actions the actions of the element
	 * @param functionalElement the functional element with the actions
	 */
	private void addCustomActionButtons(List<Action> actions, FunctionalElement functionalElement) {
		List<CustomAction> added = new ArrayList<CustomAction>();
		for (Action action : actions) {
			if (action.getType() == Action.CUSTOM) {
				boolean add = true;
				for (CustomAction customAction: added) {
					if (customAction.getName().equals(((CustomAction) action).getName()))
						add = false;
				}
				if (add) {
					buttons.add(new ActionButton((CustomAction) action));
					added.add((CustomAction) action);
				}
			} if (action.getType() == Action.CUSTOM_INTERACT && functionalElement.isInInventory()) {
				boolean add = true;
				for (CustomAction customAction: added) {
					if (customAction.getName().equals(((CustomAction) action).getName()))
						add = false;
				}
				if (add) {
					buttons.add(new ActionButton((CustomAction) action));
					added.add((CustomAction) action);
			}
			}
		} 
	}


	/**
	 * Clear buttons of any pressed or over information
	 */
	private void clearButtons() {
		for (ActionButton ab : buttons) {
			ab.setPressed(false);
			ab.setOver(false);
		}
		buttonOver = null;
		buttonPressed = null;	
	}

    /**
     * Recalculate the buttons positions, acording to their size and place on
     * the screen
     */
    private void recalculateButtonsPositions() {
    	double degreeIncrement = Math.PI / (buttons.size() - 1);
    	double angle = Math.PI;
    	int newCenterY = centerY;
    	int newCenterX = centerX;
    	// TODO check the radius recalculation to get an appropiate distribution
    	radius = 20 * buttons.size();
    	if (centerY > GUI.WINDOW_HEIGHT / 2) {
    		if (centerY > GUI.WINDOW_HEIGHT - MAX_BUTTON_HEIGHT / 2)
    			newCenterY = GUI.WINDOW_HEIGHT - MAX_BUTTON_HEIGHT / 2;
    		degreeIncrement = - degreeIncrement;
    	} else {
    		if (centerY < MAX_BUTTON_HEIGHT / 2)
    			newCenterY = MAX_BUTTON_HEIGHT / 2;
    	}
        	
    	if (centerX > GUI.WINDOW_WIDTH - MAX_BUTTON_WIDTH / 2)
    		newCenterX = GUI.WINDOW_WIDTH - MAX_BUTTON_WIDTH / 2;
    	if (centerX < MAX_BUTTON_WIDTH)
    		newCenterX = MAX_BUTTON_WIDTH;
    	
    	if (newCenterX < (radius + MAX_BUTTON_WIDTH / 2)) {
    		if (newCenterY - radius < MAX_BUTTON_HEIGHT) {
        		angle = angle / 2;
    			degreeIncrement = degreeIncrement / 2;
    			radius = radius * 1.5;
    		} else if (newCenterY + radius > GUI.WINDOW_HEIGHT - MAX_BUTTON_HEIGHT / 2) {
    			angle = angle + angle / 2;
	    		degreeIncrement = degreeIncrement / 2;
	    		radius = radius * 1.5;
    		} else {
    			angle = angle / 2;
    		}
    	}
    	
    	if (newCenterX > (GUI.WINDOW_WIDTH - radius - MAX_BUTTON_WIDTH / 2)) {
    		if (newCenterY - radius < MAX_BUTTON_HEIGHT) {
        		angle = angle / 2;
    			degreeIncrement = - degreeIncrement / 2;
    			radius = radius * 1.5;
    		} else if (newCenterY + radius > GUI.WINDOW_HEIGHT - MAX_BUTTON_HEIGHT / 2) {
    			angle = angle + angle / 2;
	    		degreeIncrement = - degreeIncrement / 2;
	    		radius = radius * 1.5;
    		} else {
    			angle = - angle / 2;
    		}
    	}

    	
		for (ActionButton ab : buttons) {
			ab.setPosX((int) (Math.cos(angle) * radius + newCenterX));
			ab.setPosY((int) (Math.sin(angle) * radius + newCenterY));
			angle -= degreeIncrement;
		}
	
	}


	/**
	 * Adds the default buttons for a character element
	 */
	private void addDefaultCharacterButtons() {
		buttons.add(eyeButton);
		buttons.add(mouthButton);
	}

	/**
	 * Adds the default buttons for non-character elements
	 */
	private void addDefaultObjectButtons(FunctionalItem item) {
		buttons.add(eyeButton);
		
		if (!item.isInInventory()) {
			handButton.setName(TextConstants.getText("ActionButton.Grab"));
			if (item.getFirstValidAction(Action.USE) != null)
				handButton.setName(TextConstants.getText("ActionButton.Use"));
		} else {
			boolean useAlone = item.canBeUsedAlone();
			boolean giveTo = item.getFirstValidAction(Action.GIVE_TO) != null;
			boolean useWith = item.getFirstValidAction(Action.USE_WITH) != null;
			if (useAlone && !giveTo && !useWith) {
				handButton.setName(TextConstants.getText("ActionButton.Use"));
			} else if (!useAlone && giveTo && !useWith) {
				handButton.setName(TextConstants.getText("ActionButton.GiveTo"));
			} else if (!useAlone && !giveTo && useWith) {
				handButton.setName(TextConstants.getText("ActionButton.UseWith"));
			} else if (!useAlone && giveTo && useWith) {
				handButton.setName(TextConstants.getText("ActionButton.UseGive"));
			} else {
				handButton.setName(TextConstants.getText("ActionButton.Use"));
			}
		}
		buttons.add(handButton);
	}    

	/**
     * There has been a mouse moved over the action buttons
     * @param x int coordinate
     * @param y int coordinate
     */
    public void mouseMoved( MouseEvent e ) {
		clearButtons();
		if( e != null ) {
            ActionButton ab  = getButton( e.getX( ), e.getY( ) );
            if (ab != null) {
	            ab.setOver(true);
	            buttonOver = ab;
            }
        }
    }

    /**
     * There has been a click in the action buttons
     * @param x int coordinate
     * @param y int coordinate
     */
    public void mouseClicked( MouseEvent e ) {
		clearButtons();
		if( e != null ) {
            ActionButton ab  = getButton( e.getX( ), e.getY( ) );
            if (ab != null) {
            	ab.setPressed(true);
                buttonPressed = ab;
            }
        }
    }

    /**
     * Draw the action buttons given a Graphics2D
     * @param g Graphics2D to be used
     */
    public void draw( Graphics2D g ) {
        //For each action button
    	for (ActionButton ab : buttons) {
    		ab.draw(g);
    	}
    }

    /**
     * Get the current button pressed
     * @return button pressed
     */
    public ActionButton getButtonPressed( ) {
        return buttonPressed;
    }

    /**
     * Get the current button pressed
     * @return button pressed
     */
    public ActionButton getButtonOver( ) {
        return buttonOver;
    }
 
    /**
     * Returns the button at the given coordinates
     * @param x the x-axis value
     * @param y the y-axis value
     * @return the button in that place
     */
    public ActionButton getButton( int x, int y ) {
        for (ActionButton ab : buttons) {
        	if (ab.isInside(x,y)) {
        		return ab;
        	}
        }
        return null;
    }


}
