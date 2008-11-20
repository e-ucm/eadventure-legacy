package es.eucm.eadventure.engine.core.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;

import es.eucm.eadventure.engine.core.control.FlagSummary;
import es.eucm.eadventure.engine.core.control.ItemSummary;
import es.eucm.eadventure.common.data.chapter.elements.Item;

/**
 * This class manages the saved games in eAdventure
 */
public class SaveGame implements Serializable {

    /**
     * Required by serializable
     */
    private static final long serialVersionUID = 1L;

    private SaveGameData saveGameData;

    /**
     * Creates a new SaveGame
     */
    public SaveGame( ) {
        saveGameData = new SaveGameData( );
    }
   
    /**
     * Loads a game from the given file.
     * @param filename the file to load
     * @return true if the load was succesful, false otherwise
     */
    public boolean loadTxt( String filename ) {
        boolean loaded = true;
        try {
            BufferedReader  file = new BufferedReader ( new FileReader( filename ) );
            saveGameData = new SaveGameData( );
            String line;
            line = file.readLine().trim();
            saveGameData.title = line;
            line = file.readLine().trim();
            saveGameData.chapter = Integer.parseInt( line );
            line = file.readLine().trim();
            saveGameData.saveTime = line;
            line = file.readLine().trim();
            saveGameData.totalTime = Long.parseLong(line);
            line = file.readLine().trim();
            saveGameData.idScene = line;
            line = file.readLine().trim();
            saveGameData.playerX = Float.parseFloat( line.split(";")[0] );
            saveGameData.playerY = Float.parseFloat( line.split(";")[1] );
            line = file.readLine().trim();
            String[] placedItems = line.split(";");
            line = file.readLine().trim();
            String[] consumedItems = line.split(";");
            line = file.readLine().trim();
            String[] grabbedItems = line.split(";");
            ArrayList<Item> items = new ArrayList<Item>();
            for(int i=0; i<placedItems.length; i++){
                if ( !placedItems[i].trim().equals("") )
                    items.add( new Item( placedItems[i] ) ); 
            }
            for(int i=0; i<consumedItems.length; i++){
                if ( !consumedItems[i].trim().equals("") )
                    items.add( new Item( consumedItems[i] ) ); 
            }
            for(int i=0; i<grabbedItems.length; i++){
                if ( !grabbedItems[i].trim().equals("") )
                    items.add( new Item( grabbedItems[i] ) ); 
            }
            saveGameData.itemSummary = new ItemSummary( items );
            for(int i=0; i<consumedItems.length; i++){
                if ( !consumedItems[i].trim().equals("") )
                    saveGameData.itemSummary.consumeItem( consumedItems[i] ); 
            }
            for(int i=0; i<grabbedItems.length; i++){
                if ( !grabbedItems[i].trim().equals("") )
                    saveGameData.itemSummary.grabItem( grabbedItems[i] ); 
            }
            line = file.readLine().trim();
            String[] activeFlags = line.split(";");
            line = file.readLine().trim();
            String[] inactiveFlags = line.split(";");
            ArrayList<String> flags = new ArrayList<String>();
            for(int i=0; i<activeFlags.length; i++){
                if ( !activeFlags[i].trim().equals("") )
                    flags.add( activeFlags[i] );
            }
            for(int i=0; i<inactiveFlags.length; i++){
                if ( !inactiveFlags[i].trim().equals("") )
                    flags.add( inactiveFlags[i] );
            }
            saveGameData.flags = new FlagSummary( flags );
            for(int i=0; i<activeFlags.length; i++){
                if ( !activeFlags[i].trim().equals("") )
                    saveGameData.flags.activateFlag( activeFlags[i] ); 
            }
        } catch( FileNotFoundException e ) {
            loaded=false;
        } catch( IOException e ) {
            loaded=false;
        }
        return loaded;
    }
    /**
     * Saves a game to the given file.
     * @param filename the file to save to
     * @return true if the save was succesful, false otherwise
     */
    public boolean saveTxt( String filename ) {
        boolean saved = true;
        try {
            PrintStream file = new PrintStream(new FileOutputStream( filename ));
            file.println( saveGameData.title );
            file.println( saveGameData.chapter );
            file.println( saveGameData.saveTime );
            file.println( saveGameData.totalTime );
            file.println( saveGameData.idScene );
            file.println( saveGameData.playerX + ";" + saveGameData.playerY );
            for(String item : saveGameData.itemSummary.getNormalItems() ){
                file.print( item + ";" );
            }
            file.println();
            for(String item : saveGameData.itemSummary.getConsumedItems() ){
                file.print( item + ";" );
            }
            file.println();
            for(String item : saveGameData.itemSummary.getGrabbedItems() ){
                file.print( item + ";" );
            }
            file.println();
            for(String flag : saveGameData.flags.getActiveFlags()){
                file.print( flag + ";" );
            }
            file.println();
            for(String flag : saveGameData.flags.getInactiveFlags() ){
                file.print( flag + ";" );
            }
            file.println();
            file.close();
        } catch( FileNotFoundException e ) {
            saved = false;
        }
        
        return saved;
    }

    /**
     * Returns the loaded flags
     * @return the loaded flags
     */
    public FlagSummary getFlags( ) {
        return saveGameData.flags;
    }

    /**
     * Changes the flags to save
     * @param flags the flags to save
     */
    public void setFlags( FlagSummary flags ) {
        saveGameData.flags = flags;
    }

    /**
     * Returns the loaded current scene id
     * @return the loaded current scene id
     */
    public String getIdScene( ) {
        return saveGameData.idScene;
    }

    /**
     * Changes the current scene id to save
     * @param idScene the current scene id to save
     */
    public void setIdScene( String idScene ) {
        saveGameData.idScene = idScene;
    }

    /**
     * Returns the loaded item summary
     * @return the loaded item summary
     */
    public ItemSummary getItemSummary( ) {
        return saveGameData.itemSummary;
    }

    /**
     * Changes the item summary to save
     * @param itemSummary the item summary to save
     */
    public void setItemSummary( ItemSummary itemSummary ) {
        saveGameData.itemSummary = itemSummary;
    }

    /**
     * Returns the loaded player's horizontal position
     * @return the loaded player's horizontal position
     */
    public float getPlayerX( ) {
        return saveGameData.playerX;
    }

    /**
     * Changes the player's horizontal position to save
     * @param playerX the player's horizontal position to save
     */
    public void setPlayerX( float playerX ) {
        saveGameData.playerX = playerX;
    }

    /**
     * Returns the loaded player's vertical position
     * @return the loaded player's vertical position
     */
    public float getPlayerY( ) {
        return saveGameData.playerY;
    }
    
    /**
     * Changes the player's vertical position to save
     * @param playerY the player's vertical position to save
     */
    public void setPlayerY( float playerY ) {
        saveGameData.playerY = playerY;
    }
    
    /**
     * Returns the loaded game title
     * @return the loaded game title
     */
    public String getTitle( ) {
        return saveGameData.title;
    }

    /**
     * Changes the game title to save
     * @param title the game title to save
     */
    public void setTitle( String title ) {
        saveGameData.title = title;
    }
    
    public String getSaveTime( ) {
        return saveGameData.saveTime;
    }

    public void setSaveTime( String saveTime ) {
        saveGameData.saveTime = saveTime;
    }

    public long getTotalTime( ) {
        return saveGameData.totalTime;
    }

    public void setTotalTime( long totalTime ) {
        saveGameData.totalTime = totalTime;
    }
    
    public int getChapter( ) {
        return saveGameData.chapter;
    }

    public void setChapter( int chapter ) {
        saveGameData.chapter = chapter;
    }
    
    private class SaveGameData {
        
        private String title;
        
        private int chapter;
        
        private long totalTime;
        
        private String saveTime;

        private String idScene;

        private FlagSummary flags;

        private ItemSummary itemSummary;

        private float playerX, playerY;

     }

}
