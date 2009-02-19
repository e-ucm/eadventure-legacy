package es.eucm.eadventure.common.data.chapter;

public class ExitLook implements Cloneable{
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
    
	public Object clone() throws CloneNotSupportedException {
		ExitLook el = (ExitLook) super.clone();
		el.cursorPath = (cursorPath != null ? new String(cursorPath) : null);
		el.exitText = (exitText != null ? new String(exitText) : null);
		return el;
	}

}
