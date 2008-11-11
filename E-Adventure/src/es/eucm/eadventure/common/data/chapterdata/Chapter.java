package es.eucm.eadventure.common.data.chapterdata;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapterdata.book.Book;
import es.eucm.eadventure.common.data.chapterdata.conversation.Conversation;
import es.eucm.eadventure.common.data.chapterdata.elements.Item;
import es.eucm.eadventure.common.data.chapterdata.elements.NPC;
import es.eucm.eadventure.common.data.chapterdata.elements.Player;
import es.eucm.eadventure.common.data.chapterdata.scenes.Cutscene;
import es.eucm.eadventure.common.data.chapterdata.scenes.Scene;
import es.eucm.eadventure.editor.data.adaptation.AdaptationRule;
import es.eucm.eadventure.editor.data.adaptation.AdaptedState;
import es.eucm.eadventure.editor.data.assessment.AssessmentRule;

/**
 * This class hold the data of a chapter in eAdventure.
 */
public class Chapter {

	/**
	 * Chapter's title.
	 */
	private String title;

	/**
	 * Chapter's description.
	 */
	private String description;

	/**
	 * Adaptation file's path, if there is any.
	 */
	private String adaptationPath;

	/**
	 * Assessment file's path, if there is any.
	 */
	private String assessmentPath;
	
	/**
	 * Relative name to the zip where it was contained. Used for replacing 
	 */
	private String name;

	/**
	 * Identifier of the initial scene.
	 */
	private String initialScene;

	/**
	 * List of playable scenes.
	 */
	private List<Scene> scenes;

	/**
	 * List of cutscenes.
	 */
	private List<Cutscene> cutscenes;

	/**
	 * List of books.
	 */
	private List<Book> books;

	/**
	 * List of items (objects).
	 */
	private List<Item> items;

	/**
	 * The player.
	 */
	private Player player;

	/**
	 * List of characters.
	 */
	private List<NPC> characters;

	/**
	 * List of conversations.
	 */
	private List<Conversation> conversations;
	
	/**
	 * The list of timers (advanced options)
	 */
	private List<Timer> timers;


	/**
	 * Empty constructor. Sets values to null and creates empty lists.
	 */
	public Chapter( ) {
		title = null;
		description = null;
		adaptationPath = "";
		assessmentPath = "";
		initialScene = null;

		// Create lists
		scenes = new ArrayList<Scene>( );
		cutscenes = new ArrayList<Cutscene>( );
		books = new ArrayList<Book>( );
		items = new ArrayList<Item>( );
		player = new Player( );
		characters = new ArrayList<NPC>( );
		conversations = new ArrayList<Conversation>( );
		timers = new ArrayList<Timer>();
	}

	/**
	 * Constructor with title for the chapter. Sets empty values and creates empty lists, plus one scene.
	 * 
	 * @param title
	 *            Title for the chapter
	 * @param sceneId
	 *            Identifier for the scene
	 */
	public Chapter( String title, String sceneId ) {
		this.title = title;
		description = "";
		adaptationPath = "";
		assessmentPath = "";
		initialScene = sceneId;

		// Create lists
		scenes = new ArrayList<Scene>( );
		cutscenes = new ArrayList<Cutscene>( );
		books = new ArrayList<Book>( );
		items = new ArrayList<Item>( );
		player = new Player( );
		characters = new ArrayList<NPC>( );
		conversations = new ArrayList<Conversation>( );
		timers = new ArrayList<Timer>();
		// Add the scene
		scenes.add( new Scene( sceneId ) );
	}

	/**
	 * Returns the title of the chapter
	 * 
	 * @return Chapter's title
	 */
	public String getTitle( ) {
		return title;
	}

	/**
	 * Returns the description of the chapter.
	 * 
	 * @return Chapter's description
	 */
	public String getDescription( ) {
		return description;
	}

	/**
	 * Returns the path of the adaptation file.
	 * 
	 * @return the path of the adaptation file
	 */
	public String getAdaptationPath( ) {
		return adaptationPath;
	}

	/**
	 * Returns the path of the assessment file.
	 * 
	 * @return the path of the assessment file
	 */
	public String getAssessmentPath( ) {
		return assessmentPath;
	}

	/**
	 * Returns the initial scene identifier.
	 * 
	 * @return Initial scene identifier
	 */
	public String getInitialScene( ) {
		return initialScene;
	}

	/**
	 * Returns the list of playable scenes in the game.
	 * 
	 * @return List of playable scenes
	 */
	public List<Scene> getScenes( ) {
		return scenes;
	}

	/**
	 * Returns the list of cutscenes in the game.
	 * 
	 * @return List of cutscenes
	 */
	public List<Cutscene> getCutscenes( ) {
		return cutscenes;
	}

	/**
	 * Returns the list of books in the game
	 * 
	 * @return the list of books in the game
	 */
	public List<Book> getBooks( ) {
		return books;
	}

	/**
	 * Returns the list of items in the game
	 * 
	 * @return the list of items in the game
	 */
	public List<Item> getItems( ) {
		return items;
	}

	/**
	 * Returns the player of the game
	 * 
	 * @return the player of the gam
	 */
	public Player getPlayer( ) {
		return player;
	}

	/**
	 * Returns the list of characters in the game
	 * 
	 * @return the list of characters in the game
	 */
	public List<NPC> getCharacters( ) {
		return characters;
	}

	/**
	 * Returns the list of conversations in the game
	 * 
	 * @return the list of conversations in the game
	 */
	public List<Conversation> getConversations( ) {
		return conversations;
	}

	/**
	 * Sets the title of the chapter.
	 * 
	 * @param title
	 *            New title for the chapter
	 */
	public void setTitle( String title ) {
		this.title = title;
	}

	/**
	 * Sets the description of the chapter.
	 * 
	 * @param description
	 *            New description for the chapter
	 */
	public void setDescription( String description ) {
		this.description = description;
	}

	/**
	 * Changes the path of the adaptation file.
	 * 
	 * @param adaptationPath
	 *            the new path of the adaptation file
	 */
	public void setAdaptationPath( String adaptationPath ) {
		this.adaptationPath = adaptationPath;
	}

	/**
	 * Changes the path of the assessment file.
	 * 
	 * @param assessmentPath
	 *            the new path of the assessment file
	 */
	public void setAssessmentPath( String assessmentPath ) {
		this.assessmentPath = assessmentPath;
	}

	/**
	 * Changes the initial scene of the chapter.
	 * 
	 * @param initialScene
	 *            New initial scene identifier
	 */
	public void setInitialScene( String initialScene ) {
		this.initialScene = initialScene;
	}

	/**
	 * Adds a scene to the list of playable scenes in the game.
	 * 
	 * @param scene
	 *            the scene to add
	 */
	public void addScene( Scene scene ) {
		scenes.add( scene );
	}

	/**
	 * Adds a cutscene to the list of cutscenes in the game.
	 * 
	 * @param cutscene
	 *            The cutscene to add
	 */
	public void addCutscene( Cutscene cutscene ) {
		cutscenes.add( cutscene );
	}

	/**
	 * Adds a book to the list of book in the game
	 * 
	 * @param book
	 *            the book to add
	 */
	public void addBook( Book book ) {
		books.add( book );
	}

	/**
	 * Adds an item to the list of items in the game
	 * 
	 * @param item
	 *            the item to add
	 */
	public void addItem( Item item ) {
		items.add( item );
	}

	/**
	 * Changes the player in the game
	 * 
	 * @param player
	 *            the new player
	 */
	public void setPlayer( Player player ) {
		this.player = player;
	}

	/**
	 * Adds a character to the list of characters in the game
	 * 
	 * @param npc
	 *            the new character
	 */
	public void addCharacter( NPC npc ) {
		characters.add( npc );
	}

	/**
	 * Adds a conversation to the list of conversation in the game
	 * 
	 * @param conversation
	 *            the new conversation
	 */
	public void addConversation( Conversation conversation ) {
		conversations.add( conversation );
	}
	
	/**
	 * Adds a timer to the list of timers in the game
	 * 
	 * @param timer
	 *            the new timer
	 */
	public void addTimer( Timer timer ){ 
		timers.add( timer );
	}

	/**
	 * Returns a scene with the given id.
	 * 
	 * @param sceneId
	 *            Scene id
	 * @return Scene requested, null if it was not found
	 */
	public Scene getScene( String sceneId ) {
		Scene selectedScene = null;

		for( Scene scene : scenes )
			if( scene.getId( ).equals( sceneId ) )
				selectedScene = scene;

		return selectedScene;
	}

	/**
	 * Returns an item with the given id.
	 * 
	 * @param itemId
	 *            Item id
	 * @return Item requested, null if it was not found
	 */
	public Item getItem( String itemId ) {
		Item selectedItem = null;

		for( Item item : items )
			if( item.getId( ).equals( itemId ) )
				selectedItem = item;

		return selectedItem;
	}

	/**
	 * Returns a character with the given id.
	 * 
	 * @param npcId
	 *            Character id
	 * @return Character requested, null if it was not found
	 */
	public NPC getCharacter( String npcId ) {
		NPC selectedNPC = null;

		for( NPC npc : characters )
			if( npc.getId( ).equals( npcId ) )
				selectedNPC = npc;

		return selectedNPC;
	}

	/**
	 * Returns the list of timers (blocks of effects ruled by conditions which will get executed each TIME seconds
	 * @return The list of timers
	 */
	public List<Timer> getTimers(){
		return timers;
	}

	/**
	 * Set the list of timers
	 * @param timers The new list of timers
	 * @see #getTimers()
	 */
	public void setTimers( List<Timer> timers ){
		this.timers = timers;
	}

	/**
	 * @return the name
	 */
	public String getName( ) {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName( String name ) {
		this.name = name;
	}
}
