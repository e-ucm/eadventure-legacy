package es.eucm.eadventure.engine.core.data.gamedata;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapterdata.Timer;
import es.eucm.eadventure.common.data.chapterdata.book.Book;
import es.eucm.eadventure.common.data.chapterdata.conversation.Conversation;
import es.eucm.eadventure.common.data.chapterdata.elements.Item;
import es.eucm.eadventure.common.data.chapterdata.elements.NPC;
import es.eucm.eadventure.common.data.chapterdata.elements.Player;
import es.eucm.eadventure.common.data.chapterdata.scenes.GeneralScene;

/**
 * This class hold the data of a game in eAdventure
 */
public class GameData {
    
    /**
     * List of scenes
     */
    private ArrayList<GeneralScene> generalScenes;

    /**
     * List of books
     */
    private ArrayList<Book> books;

    /**
     * List of items (objects)
     */
    private ArrayList<Item> items;

    /**
     * The player
     */
    private Player player;

    /**
     * List of characters
     */
    private ArrayList<NPC> characters;

    /**
     * List of conversations
     */
    private ArrayList<Conversation> conversations;
    
    /**
     * List of the flags present in the game
     */
    private ArrayList<String> flags;
    
    /**
     * The list of timers (advanced options)
     */
    private List<Timer> timers;
    
    /**
     * Empty constructor
     */
    public GameData( ) {
        generalScenes = new ArrayList<GeneralScene>( );
        books = new ArrayList<Book>( );
        items = new ArrayList<Item>( );
        player = null;
        characters = new ArrayList<NPC>( );
        conversations = new ArrayList<Conversation>( );
        flags = new ArrayList<String>( );
        timers = new ArrayList<Timer>();
    }
    
    /**
     * Returns the list of general scenes in the game
     * @return the list of general scenes in the game
     */
    public ArrayList<GeneralScene> getGeneralScenes( ) {
        return generalScenes;
    }

    /**
     * Returns the list of books in the game
     * @return the list of books in the game
     */
    public ArrayList<Book> getBooks( ) {
        return books;
    }

    /**
     * Returns the list of items in the game
     * @return the list of items in the game
     */
    public ArrayList<Item> getItems( ) {
        return items;
    }

    /**
     * Returns the player of the game
     * @return the player of the gam
     */
    public Player getPlayer( ) {
        return player;
    }

    /**
     * Returns the list of characters in the game
     * @return the list of characters in the game
     */
    public ArrayList<NPC> getCharacters( ) {
        return characters;
    }

    /**
     * Returns the list of conversations in the game
     * @return the list of conversations in the game
     */
    public ArrayList<Conversation> getConversations( ) {
        return conversations;
    }
    
    /**
     * Returns the list of flags in the game
     * @return the list of flags in the game
     */
    public ArrayList<String> getFlags( ) {
        return flags;
    }

    /**
     * Adds a scene to the list of scenes in the game
     * @param generalScene the scene to add
     */
    public void addGeneralScene( GeneralScene generalScene ) {
        generalScenes.add( generalScene );
    }

    /**
     * Adds a book to the list of book in the game
     * @param book the book to add
     */
    public void addBook( Book book ) {
        books.add( book );
    }

    /**
     * Adds an item to the list of items in the game
     * @param item the item to add
     */
    public void addItem( Item item ) {
        items.add( item );
    }

    /**
     * Changes the player in the game
     * @param player the new player
     */
    public void setPlayer( Player player ) {
        this.player = player;
    }

    /**
     * Adds a character to the list of characters in the game
     * @param npc the new character
     */
    public void addCharacter( NPC npc ) {
        characters.add( npc );
    }

    /**
     * Adds a conversation to the list of conversation in the game
     * @param conversation the new conversation
     */
    public void addConversation( Conversation conversation ) {
        conversations.add( conversation );
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
     * Returns the initial scene
     * @return the initial scene
     */
    public GeneralScene getInitialGeneralScene( ) {
        GeneralScene initialScene = null;

        // Return the FIRST initial scene stored
        for( int i = 0; i < generalScenes.size( ) && initialScene == null; i++ )
            if( generalScenes.get( i ).isInitialScene( ) )
                initialScene = generalScenes.get( i );

        // If there is no initial scene, return the first scene
        if (initialScene==null)
            initialScene=generalScenes.get( 0 );
        return initialScene;
    }

    /**
     * Returns the scene with the given id. If the scene is not found, null is returned
     * @param generalSceneId the id of the scene to find
     * @return the scene with the given id
     */
    public GeneralScene getGeneralScene( String generalSceneId ) {
        GeneralScene scene = null;

        for( GeneralScene currentScene : generalScenes )
            if( currentScene.getId( ).equals( generalSceneId ) )
                scene = currentScene;

        return scene;
    }

    /**
     * Returns whether exists a cutscene with the given id
     * @param cutsceneId the id of the cutscene to find
     * @return true if exists a scene with the given id and it is a cutscene, false otherwise
     */
    public boolean isCutscene( String cutsceneId ) {
        boolean isCutscene = false;

        GeneralScene scene = getGeneralScene( cutsceneId );
        if( scene != null && scene.getType( ) != GeneralScene.SCENE )
            isCutscene = true;

        return isCutscene;
    }
    
    /**
     * Returns the book with the given id. If the book is not found,
     * null is returned
     * @param bookId The id of the book to find
     * @return The book with the given id
     */
    public Book getBook( String bookId ) {
        Book book = null;
        
        for( Book currentBook : books )
            if( currentBook.getId( ).equals( bookId ) )
                book = currentBook;
        
        return book;
    }

    /**
     * Returns the item with the given id. If the item is not found, null is returned
     * @param itemId the id of the item to find
     * @return the item with the given id
     */
    public Item getItem( String itemId ) {
        Item item = null;

        for( Item currentItem : items )
            if( currentItem.getId( ).equals( itemId ) )
                item = currentItem;

        return item;
    }
    
    /**
     * Returns the conversation with the given id. If the conversation is not found,
     * null is returned
     * @param conversationId The id of the conversation to find
     * @return The conversation with the given id
     */
    public Conversation getConversation( String conversationId ) {
        Conversation conversation = null;
        
        for( Conversation currentConversation : conversations )
            if( currentConversation.getId( ).equals( conversationId ) )
                conversation = currentConversation;
        
        return conversation;
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
}
