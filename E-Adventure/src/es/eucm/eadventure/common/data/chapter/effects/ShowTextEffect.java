package es.eucm.eadventure.common.data.chapter.effects;


/**
 * An effect that show text in an specific zone in scene
 */
public class ShowTextEffect extends AbstractEffect{

    /**
     * The text which will be showed
     */
    private String text;
    
    /**
     * The x position in scene
     */
    private int x;
    
    /**
     * The y position in scene
     */
    private int y;
    
    /**
     * The text front color in RGB format
     */
    private int rgbFrontColor;
    
    /**
     * The text border color in RGB fotrmat
     */
    private int rgbBorderColor;
    
    
    
    /**
     * Constructor
     * 
     * @param text
     * @param x
     * @param y
     * @param front
     * @param border
     */
    public ShowTextEffect(String text,int x,int y, int front, int border){
	super();
	this.text =text;
	this.x = x;
	this.y = y;
	this.rgbFrontColor = front;
	this.rgbBorderColor = border; 
    }
    
    
  
    /**
     * @return the text
     */
    public String getText() {
        return text;
    }



    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }



    /**
     * @return the x
     */
    public int getX() {
        return x;
    }




    /**
     * @return the y
     */
    public int getY() {
        return y;
    }



    /**
	 * Sets the new text position
	 * 
	 * @param x
	 *            New text position X coordinate
	 * @param y
	 *            New text position Y coordinate
	 */
	public void setTextPosition( int x, int y ) {
		this.x = x;
		this.y = y;
	}

    /**
     * Return the effect type
     */
    public int getType() {
	return SHOW_TEXT;
    }
    
    public Object clone() throws CloneNotSupportedException {
	ShowTextEffect ste = (ShowTextEffect) super.clone();
	ste.text = (text != null ? new String(text) : null);
	ste.x = x;
	ste.y = y;
	ste.rgbBorderColor = rgbBorderColor;
	ste.rgbFrontColor = rgbFrontColor;
	return ste;
    }



    /**
     * @return the rgbFrontColor
     */
    public int getRgbFrontColor() {
        return rgbFrontColor;
    }



    /**
     * @param rgbFrontColor the rgbFrontColor to set
     */
    public void setRgbFrontColor(int rgbFrontColor) {
        this.rgbFrontColor = rgbFrontColor;
    }



    /**
     * @return the rgbBorderColor
     */
    public int getRgbBorderColor() {
        return rgbBorderColor;
    }



    /**
     * @param rgbBorderColor the rgbBorderColor to set
     */
    public void setRgbBorderColor(int rgbBorderColor) {
        this.rgbBorderColor = rgbBorderColor;
    }
}
