package es.eucm.eadventure.engine.core.control.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import es.eucm.eadventure.engine.core.control.DebugLog;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.control.functionaldata.TalkingElement;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.OptionConversationNode;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.engine.core.gui.GUI;

/**
 * A game main loop during a conversation
 */
public class GameStateConversation extends GameState {
    
    /**
     * Left most point of the response text block
     */
    private final int RESPONSE_TEXT_X;
    
    /**
     * Upper most point of the response text block
     */
    private final int RESPONSE_TEXT_Y;
    
    /**
     * Number of response lines to display
     */
    private final int RESPONSE_TEXT_NUMBER_LINES;
    
    /**
     * Height of the text of the responses
     */
    private final int RESPONSE_TEXT_HEIGHT;
    
    /**
     * Ascent of the text of the response
     */
    private final int RESPONSE_TEXT_ASCENT;
    
    /**
     * Color of the normal response text
     */
    private static final Color RESPONSE_TEXT_NORMAL = Color.YELLOW;
    
    /**
     * Color of the highlighted response text
     */
    private static final Color RESPONSE_TEXT_HIGHLIGHTED = Color.RED;
    
    /**
     * Color of the border of the response text
     */
    private static final Color RESPONSE_TEXT_BORDER = Color.BLACK;
    
    /**
     * Constant for no mouse button clicked
     */
    private static final int MOUSE_BUTTON_NONE = 0;
    
    /**
     * Constant for left mouse button clicked
     */
    private static final int MOUSE_BUTTON_LEFT = 1;
    
    /**
     * Constant for right mouse button clicked
     */
    private static final int MOUSE_BUTTON_RIGHT = 2;
    
    /**
     * Current conversational node being played
     */
    private ConversationNode currentNode;
    
    /**
     * Index of the line being played
     */
    private int currentLine;
    
    /**
     * Index of the first line displayed in an option node
     */
    private int firstLineDisplayed;
    
    /**
     * Index of the option currently highlighted
     */
    private int optionHighlighted;
    
    /**
     * Last mouse button pressed
     */
    private int mouseClickedButton = MOUSE_BUTTON_NONE;
    
    /**
     * Variable to control the access to doRandom()
     */
    private boolean firstTime;
    
    /**
     * Store the option selected to use it when it come back to the running effects Game State
     */
    private int optionSelected;
    
    /**
     * 
     */
    private boolean isOptionSelected;
    
    /**
     * 
     */
    private boolean keyPressed;

    /**
     * Number of options that has been displayed in the screen
     */
    private int numberDisplayedOptions;
    
    /**
     * Creates a new GameStateConversation
     */
    public GameStateConversation( ) {
        RESPONSE_TEXT_X = GUI.getInstance( ).getResponseTextX( );
        RESPONSE_TEXT_Y = GUI.getInstance( ).getResponseTextY( );
        RESPONSE_TEXT_NUMBER_LINES = GUI.getInstance( ).getResponseTextNumberLines( );
        RESPONSE_TEXT_ASCENT = GUI.getInstance( ).getGraphics( ).getFontMetrics( ).getAscent( );
        RESPONSE_TEXT_HEIGHT = RESPONSE_TEXT_ASCENT + 2;
        
        currentNode = game.getConversation( ).getRootNode( );
        currentLine = 0;
        firstLineDisplayed = 0;
        optionHighlighted = -1;
        
        isOptionSelected = false;

        game.addToTheStack(new ArrayList<FunctionalEffect>());
    }

    public synchronized void mainLoop( long elapsedTime, int fps ) {
    	Graphics2D g = setUpGUI(elapsedTime);

        if( currentNode.getType( ) == ConversationNode.DIALOGUE )
        	processDialogNode();
        else if( currentNode.getType( ) == ConversationNode.OPTION )
        	processOptionNode(g);
        
        GUI.getInstance( ).endDraw( );
        g.dispose( );
    }
    
    /**
     * Set up the basic gui of the scene.
     * 
     * @param elapsedTime The time elapsed since the last update
     * @return The graphics element for the scene
     */
    private Graphics2D setUpGUI(long elapsedTime) {
        GUI.getInstance( ).toggleHud( false );   
        GUI.getInstance( ).setDefaultCursor( );
        
        game.getFunctionalScene( ).update( elapsedTime );
        GUI.getInstance( ).update( elapsedTime );
        
        Graphics2D g = GUI.getInstance( ).getGraphics( );
        g.clearRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
        
        game.getFunctionalScene( ).draw( );
        GUI.getInstance().drawScene( g , elapsedTime);

        return g;
    }
    
    /**
     * Processed mouse clicks when in a dialog node.
     * If no button was pressed, the conversation goes on normally (goes to the next line only if no
     * character is talking or the one talking has finished).
     * If the left button was pressed, the current line is skipped.
     * If the right button was pressed, all the lines are skipped.
     */
    private void processDialogNode() {
        if( mouseClickedButton == MOUSE_BUTTON_NONE ) {
            if( game.getCharacterCurrentlyTalking( ) == null || 
              ( game.getCharacterCurrentlyTalking( ) != null &&
               !game.getCharacterCurrentlyTalking( ).isTalking( ) ) ) {
                playNextLine( );
            }
        }
        else if( mouseClickedButton == MOUSE_BUTTON_LEFT ) {
        	DebugLog.user("Skipped line in conversation");
            playNextLine( );
            mouseClickedButton = MOUSE_BUTTON_NONE;
        }
        else if( mouseClickedButton == MOUSE_BUTTON_RIGHT ) {
            DebugLog.user("Skipped conversation");
        	currentLine = currentNode.getLineCount( );
            playNextLine( );
            mouseClickedButton = MOUSE_BUTTON_NONE;
        }
        firstTime = true;
    }

    /**
     * Process all the information available when in an option node.
     * Two cases are distinguished, when there is a selected option and where there is
     * no selected options.
     * @param g The graphics in which to draw the options
     */
    private void processOptionNode(Graphics2D g) {
    	if (!isOptionSelected)
    		optionNodeNoOptionSelected(g);
		else
			optionNodeWithOptionSelected();
    }
    
    /**
     * When in an option node, if no option is selected all the possible options must be displayed
     * on screen.
     * 
     * @param g The graphics to draw the options in.
     */
    private void optionNodeNoOptionSelected(Graphics2D g) {
		if (firstTime) {
			((OptionConversationNode) currentNode).doRandom();
			firstTime = false;
		}
		numberDisplayedOptions = 0;

		if (currentNode.getLineCount() <= RESPONSE_TEXT_NUMBER_LINES) {
			for (int i = 0; i < currentNode.getLineCount(); i++) {
				drawLine(g, currentNode.getLine(i).getText(), i, i);
				numberDisplayedOptions++;
			}
		} else {
			int i, indexLastLine = Math.min(firstLineDisplayed + RESPONSE_TEXT_NUMBER_LINES - 1, currentNode.getLineCount());
			for (i = firstLineDisplayed; i < indexLastLine; i++) {
				drawLine(g, currentNode.getLine(i).getText(), (i - firstLineDisplayed), i);
				numberDisplayedOptions++;
			}
			drawLine(g, TextConstants.getText("GameText.More"), (i - firstLineDisplayed), i);
		}
    }
    
    /**
     * Draw an option line in a given graphics object.
     * 
     * @param g The graphics where to draw the line
     * @param text The text of the option
     * @param optionIndex The index of the option
     */
    private void drawLine(Graphics2D g, String text, int optionIndex, int lineIndex) {
    	Color textColor = (optionIndex == optionHighlighted ? RESPONSE_TEXT_HIGHLIGHTED : RESPONSE_TEXT_NORMAL);
    	int y = RESPONSE_TEXT_Y + optionIndex * RESPONSE_TEXT_HEIGHT + RESPONSE_TEXT_ASCENT;
    	String fullText = (lineIndex + 1) + ".- " + text;
    	GUI.drawStringOnto(g, fullText, RESPONSE_TEXT_X, y, false, textColor, RESPONSE_TEXT_BORDER, true);
    }
    
    
    /**
     * In an option node with an option selected:
     * If there is a character talking, do nothing.
     * If the current node has effects, pile them.
     * If the current node has consumed it's effects or doesn't have them,
     *    finish the conversation.
     * If the current node isn't terminal, follow the selected option.
     */
    private void optionNodeWithOptionSelected() {
    	if (game.getCharacterCurrentlyTalking() != null && game.getCharacterCurrentlyTalking().isTalking())
    		return;
    	
		if (currentNode.hasValidEffect() && !currentNode.isEffectConsumed()) {
			currentNode.consumeEffect();
			game.pushCurrentState(this);
			FunctionalEffects.storeAllEffects(currentNode.getEffects());
			GUI.getInstance().toggleHud(true);
		}
		else if ((!currentNode.hasValidEffect() || currentNode.isEffectConsumed()) && currentNode.isTerminal()) {
			for (ConversationNode node : game.getConversation().getAllNodes())
				node.resetEffect();
			GUI.getInstance().toggleHud(true);
			game.endConversation();
		}
		else if (!currentNode.isTerminal()) {
			if (optionSelected >= 0 && optionSelected < currentNode.getChildCount()) {
				currentNode = currentNode.getChild(optionSelected);
				isOptionSelected = false;
			}
		}
    }
    
    /**
     * If the user chooses a valid option, it is selected and its text show.
     */
    private void selectDisplayedOption(){
    	if(optionSelected >= 0 && optionSelected < currentNode.getLineCount( ) ) {
            if( game.getCharacterCurrentlyTalking( ) != null && game.getCharacterCurrentlyTalking( ).isTalking( ) )
                game.getCharacterCurrentlyTalking( ).stopTalking( );

            FunctionalPlayer player = game.getFunctionalPlayer( );
            ConversationLine line = currentNode.getLine( optionSelected );
            
            if (line.isValidAudio( )){
                player.speak( line.getText(), line.getAudioPath( ));
            }else if (line.getSynthesizerVoice()||player.isAlwaysSynthesizer()){
            		player.speakWithFreeTTS(line.getText(), player.getPlayerVoice());
            }else{	
                player.speak( line.getText( ) );
            }

            game.setCharacterCurrentlyTalking( player );
            isOptionSelected = true;
            keyPressed = false;
        }
    }
    
    
    /**
     * Select an option when all options do not fit in the screen
     */
    private void selectNoAllDisplayedOption() {
    	if (!keyPressed)
    		optionSelected += firstLineDisplayed;
        
        int indexLastLine = Math.min( firstLineDisplayed + RESPONSE_TEXT_NUMBER_LINES - 1, currentNode.getLineCount( ) );
        
        if( optionSelected == indexLastLine ) {
            firstLineDisplayed += RESPONSE_TEXT_NUMBER_LINES - 1;
            if( firstLineDisplayed >= currentNode.getLineCount( ) )
                firstLineDisplayed = 0;
        }
        else 
        	selectDisplayedOption();
    }
    
    @Override
    public synchronized void mouseClicked(MouseEvent e) {
		if (currentNode.getType() == ConversationNode.OPTION
				&& RESPONSE_TEXT_Y <= e.getY()
				&& RESPONSE_TEXT_Y + currentNode.getLineCount()	* RESPONSE_TEXT_HEIGHT + RESPONSE_TEXT_ASCENT >= e.getY() 
				&& !isOptionSelected) {
			optionSelected = (e.getY() - RESPONSE_TEXT_Y) / RESPONSE_TEXT_HEIGHT;
			if (currentNode.getLineCount() <= RESPONSE_TEXT_NUMBER_LINES)
				selectDisplayedOption();
			else
				selectNoAllDisplayedOption();
		} else if (currentNode.getType() == ConversationNode.DIALOGUE) {
			if (e.getButton() == MouseEvent.BUTTON1)
				mouseClickedButton = MOUSE_BUTTON_LEFT;
			else if (e.getButton() == MouseEvent.BUTTON3)
				mouseClickedButton = MOUSE_BUTTON_RIGHT;
		}
	}

    @Override
    public void keyPressed( KeyEvent e ) {
    	if (currentNode.getType( ) == ConversationNode.OPTION && !isOptionSelected){
	    	if (e.getKeyCode() >= KeyEvent.VK_1 && e.getKeyCode() <= KeyEvent.VK_9)
	    		optionSelected = e.getKeyCode() - KeyEvent.VK_1;
	    	else
	    		optionSelected = -1;
	    	keyPressed=true;
	    	
	    	if( currentNode.getLineCount( ) <= RESPONSE_TEXT_NUMBER_LINES )
	    		selectDisplayedOption();
	    	else if (optionSelected >= firstLineDisplayed && optionSelected <= numberDisplayedOptions + firstLineDisplayed)
	    		selectNoAllDisplayedOption();
	    }
    }
    
    @Override
    public void mouseMoved( MouseEvent e ) {
        if( RESPONSE_TEXT_Y <= e.getY( ) )
            optionHighlighted = ( e.getY( ) - RESPONSE_TEXT_Y ) / RESPONSE_TEXT_HEIGHT;
        else
            optionHighlighted = -1;
    }
    
    /**
     * Jumps to the next conversation line. If the current line was the last,
     * end the conversation and trigger the efects or jump to the next node
     */
    private void playNextLine( ) {
        if( game.getCharacterCurrentlyTalking( ) != null && game.getCharacterCurrentlyTalking( ).isTalking( ) )
            game.getCharacterCurrentlyTalking( ).stopTalking();
        
        if( currentLine < currentNode.getLineCount( ) )
        	playNextLineInNode();
        else
        	skipToNextNode();
    }

    /**
     * Play the next line in the current conversation Nod
     */
    private void playNextLineInNode() {
        ConversationLine line = currentNode.getLine( currentLine );
        TalkingElement talking = null;

        if( line.isPlayerLine( ) )
            talking = game.getFunctionalPlayer( );
        else {
            if( line.getName().equals("NPC") )
            	talking = game.getCurrentNPC( );
            else
            	talking = game.getFunctionalScene( ).getNPC( line.getName( ) );
        }
        
        if (talking != null) {
            if (line.isValidAudio( ))
            	talking.speak( line.getText(), line.getAudioPath( ));
            else if (line.getSynthesizerVoice() || talking.isAlwaysSynthesizer())
            	talking.speakWithFreeTTS(line.getText(), talking.getPlayerVoice());
            else
            	talking.speak( line.getText( ));
        }
        game.setCharacterCurrentlyTalking(talking);
        
        currentLine++;
    }
    
    /**
     * Skip to the next node in the conversation of finish the conversation. Consume the
     * effects of the current node.
     */
    private void skipToNextNode() {
        if(currentNode.hasValidEffect( ) && !currentNode.isEffectConsumed( ) ) {
            currentNode.consumeEffect( );
            game.pushCurrentState(this);
            FunctionalEffects.storeAllEffects(currentNode.getEffects( ));
            GUI.getInstance().toggleHud( true );
        }
        else if ((!currentNode.hasValidEffect( ) || currentNode.isEffectConsumed( ) ) && currentNode.isTerminal( )){
            for (ConversationNode node: game.getConversation( ).getAllNodes( )){
                node.resetEffect( );
            }
            GUI.getInstance().toggleHud( true );
            game.endConversation();
        }
        else if (!currentNode.isTerminal( )){
            currentNode = currentNode.getChild( 0 );
            firstLineDisplayed = 0;
            currentLine = 0;
        }
    }    
}
