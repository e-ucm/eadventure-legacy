package es.eucm.eadventure.common.data.chapter;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.adventure.ChapterSummary;
import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.conditions.GlobalState;
import es.eucm.eadventure.common.data.chapter.conversation.Conversation;
import es.eucm.eadventure.common.data.chapter.effects.Macro;
import es.eucm.eadventure.common.data.chapter.elements.Item;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.scenes.Cutscene;
import es.eucm.eadventure.common.data.chapter.scenes.GeneralScene;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;

/**
 * This class hold the data of a chapter in eAdventure.
 */
public class Chapter extends ChapterSummary{

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
     * List of the flags present in the game
     */
    private List<String> flags;
    
    /**
     * List of the vars present in the game
     */
    private List<String> vars;
    
    /**
     * List of global states
     */
    private List<GlobalState> globalStates;
    
    /**
     * List of macros
     */
    private List<Macro> macros;


	
	/**
	 * Empty constructor. Sets values to null and creates empty lists.
	 */
	public Chapter( ) {
		super();
		// Create lists
		scenes = new ArrayList<Scene>( );
		cutscenes = new ArrayList<Cutscene>( );
		books = new ArrayList<Book>( );
		items = new ArrayList<Item>( );
		player = new Player( );
		characters = new ArrayList<NPC>( );
		conversations = new ArrayList<Conversation>( );
		timers = new ArrayList<Timer>();
		flags = new ArrayList<String>( );
		vars = new ArrayList<String>( );
		globalStates = new ArrayList<GlobalState>( );
		macros = new ArrayList<Macro>( );
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
		super(title);
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
		globalStates = new ArrayList<GlobalState>( );
		macros = new ArrayList<Macro>( );
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
     * Returns the initial scene
     * @return the initial scene
     */
    public GeneralScene getInitialGeneralScene( ) {
        GeneralScene initialGeneralScene = null;
        if (initialScene!=null){
        	initialGeneralScene = getGeneralScene ( initialScene );
        } else {
            // Return the FIRST initial scene stored
            for( int i = 0; i < getGeneralScenes().size( ) && initialGeneralScene == null; i++ )
                if( getGeneralScenes().get( i ).isInitialScene( ) )
                    initialGeneralScene = getGeneralScenes().get( i );

            // If there is no initial scene, return the first scene
            if (initialGeneralScene==null)
                initialGeneralScene=getGeneralScenes().get( 0 );
        	
        }

        return initialGeneralScene;
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
	 * Adds a global state to the list of global states in the game
	 * 
	 * @param globalState
	 *            the global state to add
	 */
	public void addGlobalState(GlobalState globalState ) {
		globalStates.add( globalState );
	}
	
	/**
	 * Adds a macro to the list of macros in the game
	 * 
	 * @param macro
	 *            the macro to add
	 */
	public void addMacro( Macro macro ) {
		macros.add( macro );
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
	 * Returns a cutscene with the given id.
	 * 
	 * @param sceneId
	 *            Scene id
	 * @return Scene requested, null if it was not found
	 */
	public Cutscene getCutscene( String sceneId ) {
		Cutscene selectedScene = null;

		for( Cutscene scene : cutscenes )
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
	 * Returns a global state with the given id.
	 * 
	 * @param globalStateId
	 *            Global State id
	 * @return GlobalState requested, null if it was not found
	 */
	public GlobalState getGlobalState( String globalStateId ) {
		GlobalState selectedGlobalState = null;

		for( GlobalState gs : globalStates )
			if( gs.getId( ).equals( globalStateId ) )
				selectedGlobalState = gs;

		return selectedGlobalState;
	}
	
	/**
	 * Returns a macro with the given id.
	 * 
	 * @param macroId
	 *            Macro id
	 * @return Macro requested, null if it was not found
	 */
	public Macro getMacro( String macroId ) {
		Macro selectedMacro = null;

		for( Macro m : macros )
			if( m.getId( ).equals( macroId ) )
				selectedMacro = m;

		return selectedMacro;
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
     * Returns the list of flags in the game
     * @return the list of flags in the game
     */
    public List<String> getFlags( ) {
        return flags;
    }
    
    /**
     * Returns the list of vars in the game
     * @return the list of vars in the game
     */
    public List<String> getVars( ) {
        return vars;
    }
    
    /**
     * Adds a flag to the list of flags in the game
     * @param flag the flag to add
     */
    public void addFlag( String flag ) {
        if( !flags.contains( flag ) )
            flags.add( flag );
    }
    
    /**
     * Adds a var to the list of Vars in the game
     * @param Var the var to add
     */
    public void addVar( String var ) {
        if( !vars.contains( var ) )
            vars.add( var );
    }
    
    /**
     * Returns the scene with the given id. If the scene is not found, null is returned
     * @param generalSceneId the id of the scene to find
     * @return the scene with the given id
     */
    public GeneralScene getGeneralScene( String generalSceneId ) {
        GeneralScene scene = getScene (generalSceneId);
        if (scene == null)
        	scene = getCutscene (generalSceneId);

        return scene;
    }
    
    /**
     * Returns the list of general scenes in the game
     * @return the list of general scenes in the game
     */
    public List<GeneralScene> getGeneralScenes( ) {
    	List<GeneralScene> generalScenes = new ArrayList<GeneralScene>();
    	for (Scene scene: scenes){
    		generalScenes.add(scene);
    	}
    	for (Cutscene cutscene: cutscenes){
    		generalScenes.add(cutscene);
    	}
        return generalScenes;
    }

	/**
	 * Returns an book with the given id.
	 * 
	 * @param bookId
	 *            book id
	 * @return book requested, null if it was not found
	 */
	public Book getBook( String bookId ) {
		Book selectedbook = null;

		for( Book book : books )
			if( book.getId( ).equals( bookId ) )
				selectedbook = book;

		return selectedbook;
	}
	
	/**
	 * Returns a Conversation with the given id.
	 * 
	 * @param ConversationId
	 *            Conversation id
	 * @return Conversation requested, null if it was not found
	 */
	public Conversation getConversation( String conversationId ) {
		Conversation selectedConversation = null;

		for( Conversation conversation : conversations )
			if( conversation.getId( ).equals( conversationId ) )
				selectedConversation = conversation;

		return selectedConversation;
	}
	
	/**
	 * Returns true if the argumented id matches to a cutscene
	 */
	public boolean isCutscene ( String id ){
		return getCutscene(id)!=null;
	}

	/**
	 * @return the globalStates
	 */
	public List<GlobalState> getGlobalStates() {
		return globalStates;
	}

	/**
	 * @param globalStates the globalStates to set
	 */
	public void setGlobalStates(List<GlobalState> globalStates) {
		this.globalStates = globalStates;
	}

	/**
	 * @return the macros
	 */
	public List<Macro> getMacros() {
		return macros;
	}

	/**
	 * @param macros the macros to set
	 */
	public void setMacros(List<Macro> macros) {
		this.macros = macros;
	}
}
