package es.eucm.eadventure.engine.core.data.gamedata;

public class ExitLook{
    private String exitText;
    
    private String cursorPath;

    public ExitLook(){
        exitText="";
        cursorPath="";
    }
    
    /**
     * @return the exitText
     */
    public String getExitText( ) {
        return exitText;
    }

    /**
     * @param exitText the exitText to set
     */
    public void setExitText( String exitText ) {
        this.exitText = exitText;
    }

    /**
     * @return the cursorPath
     */
    public String getCursorPath( ) {
        return cursorPath;
    }

    /**
     * @param cursorPath the cursorPath to set
     */
    public void setCursorPath( String cursorPath ) {
        this.cursorPath = cursorPath;
    }
}
