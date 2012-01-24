
/************************************************
 * PEDRO - GRADIANT
 ************************************************/

package es.eucm.eadventure.engine.core.control.interaction.auxiliar;


public class GridPosition {
    
    /**
     * X position 
     */
    private int x;

    /**
     * Y position
     */
    private int y;
    
    /**
     * Scene Identifier
     */
    private String IdScene;
    
    /**
     * Element Type
     */
    private String Type;
    
    /**
     * Element Identifier
     */
    private String IdElement;
    
    
    /**
     * Constructor
     */
    
    public GridPosition (String IdScene, int x, int y, String Type, String IdElement) {
        
        this.IdScene = IdScene;
        this.Type = Type;
        this.x=x;
        this.y=y;
        this.IdElement = IdElement;
        
        
    }

        
    /**
     * @param x
     *            the x position to set
     * @param width
     *            the width of the object
     */
    public void setX( int x, int width) {

        this.x = x + (width/2);
    }
    
    /**
     * @param y
     *            the y position to set
     * @param height
     *            the height of the object
     */
    public void setY( int y , int height) {
        this.y = y + (height/2);
    }
    
    
    /**
     * @param IdScene
     *            the scene identifier

     */
    public void setIdScene( String IdScene) {
        this.IdScene = IdScene;
    }
    
    /**
     * @param IdElement
     *            the element identifier

     */
    public void setType( String Type) {
        this.Type = Type;
    }
    
    /**
    * @param IdElement
    *            the element identifier

    */
   public void setIdElement( String IdElement) {
       this.IdElement = IdElement;
   }
    
    
    /**
     * Returns the horizontal coordinate of the center
     * 
     * @return the horizontal coordinate of the center
     */
    public int getX( ) {
       return x;
    }
    
    
    /**
     * Returns the vertical coordinate of the center
     * 
     * @return the vertical coordinate of the center
     */
    public int getY( ) {
       return y;
    }
    
    /**
     * @param y
     *            the y position to set
     * @param height
     *            the height of the object
     */
    public int getInvert( int n) {
        int w = -n;
        return w;
    }

    /**
     * Returns the scene identifier
     * 
     * @return the scene identifier
     */
    public String getIdScene( ) {
       return IdScene;
    }
    
    /**
     * Returns the element type
     * 
     * @return the element type
     */
    public String getType( ) {
       return Type;
    }
    
    /**
     * Returns the element identifier
     * 
     * @return the element identifier
     */
    public String getIdElement( ) {
       return IdElement;
    }
    
    @Override
    public String toString(){
        return "GridPosition [Type="+Type+"  X="+x+"  Y="+y+"]";
    }
}
