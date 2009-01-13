package es.eucm.eadventure.editor.control.controllers.scene;

import java.awt.Image;


/**
 * esta clase es usada para contener en una misma estructura 2 tipos de objetos, o element reference data control o 
 * su simil para el player
 *
 */
public class ElementContainer {

	private ElementReferenceDataControl erdc;

	private int playerLayer;
	
	private Image image;
	
	/**
	 * Constructor. When erdc has value, takes the element reference data control for references to atrezzo, items or non-player characters,
	 * putting player layer with its non-valid value.
	 * Takes playerLayer for player, when erdc is null. 
	 * 
	 * @param erdc
	 * 			the element reference data control
	 * @param playerLayer
	 * 			the layer to show the player in the correct position 
	 * @param image
	 * 			the image of the player 
	 */
	public ElementContainer(ElementReferenceDataControl erdc, int playerLayer,Image image){
		if (erdc!=null){
			this.erdc = erdc;
			this.playerLayer = -1;
			this.image = null;
		} else	{
			this.playerLayer = playerLayer;
			this.image = image;
		}
	}
	
	/**
	 * Return the layer, checking if it is a player or not.
	 * 
	 * @return
	 * 		the layer.
	 */
	public int getLayer(){
		if (erdc==null)
			return playerLayer;
		else 
			return erdc.getElementReference().getLayer();
	}
	
	/**
	 * Return the y position, checking if it is a player or not.
	 * 
	 * @return
	 * 		the y position.
	 */
	public int getY(){
		if (erdc==null)
			return playerLayer;
		else 
			return erdc.getElementReference().getY();
	}
	
	/**
	 * Change the layer, checking if it is a player or not.
	 * 
	 * @param layer
	 *			the new layer.
	 */
	public void setLayer(int layer){
		if (erdc==null)
			playerLayer = layer;
		else 
			erdc.getElementReference().setLayer(layer);
	}
	
	/**
	 * Check if contains a player
	 * 
	 * @return
	 * 		true, if contains a player.
	 */
	public boolean isPlayer(){
		return erdc == null;
	}


	public int getPlayerLayer() {
		return playerLayer;
	}

	public void setPlayerLayer(int playerLayer) {
		this.playerLayer = playerLayer;
	}

	public ElementReferenceDataControl getErdc() {
		return erdc;
	}

	public Image getImage() {
		return image;
	}
	
}
