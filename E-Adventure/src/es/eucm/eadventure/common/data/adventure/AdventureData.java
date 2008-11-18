package es.eucm.eadventure.common.data.adventure;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Chapter;

/**
 * Stores the description of the eAdventure file
 */
public class AdventureData extends DescriptorData{
    
    /**
     * Stores if the GUI's graphics are customized
     */
    private boolean guiCustomized;
    
    /**
     * List of chapters of the game
     */
    private List<Chapter> chapters;
    
    /**
     * List of custom cursors
     */
    private List<CustomCursor> cursors;

    /**
     * This flag tells if the adventure should show automatic commentaries.
     */
    private boolean commentaries = false;

    /**
     * Constructor
     */
    public AdventureData( ) {
        chapters = new ArrayList<Chapter>( );
        cursors = new ArrayList<CustomCursor>();
    }
    
    
    /**
     * Returns whether the GUI is customized
     * @return True if the GUI is customized, false otherwise
     */
    public boolean isGUICustomized( ) {
        return guiCustomized;
    }

    /**
     * Sets the parameters of the GUI
     * @param guiType Type of the GUI
     * @param guiCustomized False if the GUI should be customized, false otherwise
     */
    public void setGUI( int guiType, boolean guiCustomized ) {
        this.guiType = guiType;
        this.guiCustomized = guiCustomized;
    }
    
    /**
     * Returns the list of chapters of the game
     * @return List of chapters of the game
     */
    public List<Chapter> getChapters( ) {
        return chapters;
    }

    /**
     * Adds a new chapter to the list
     * @param chapter Chapter to be added
     */
    public void addChapter( Chapter chapter ) {
        chapters.add( chapter );
    }

    public void addCursor(CustomCursor cursor){
        cursors.add( cursor );
        this.guiCustomized=true;
    }
    
    public List<CustomCursor> getCursors(){
        return cursors;
    }
    
    public void addCursor(String type, String path){
        addCursor(new CustomCursor(type,path));
    }
    
    public String getCursorPath(String type){
        for (CustomCursor cursor: cursors){
            if (cursor.getType( ).equals( type )){
                return cursor.getPath( );
            }
        }
        return null;
    }
    
    public boolean isCommentaries() {
        return commentaries;
    }

    public void setCommentaries(boolean commentaries) {
        this.commentaries = commentaries;
    }
    
    
}
