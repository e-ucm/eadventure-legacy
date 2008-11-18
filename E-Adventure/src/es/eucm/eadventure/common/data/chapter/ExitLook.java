package es.eucm.eadventure.common.data.chapter;

public class ExitLook{
    private String exitText;
    
    private String cursorPath;

    public ExitLook(){
        exitText=null;
        cursorPath=null;
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
