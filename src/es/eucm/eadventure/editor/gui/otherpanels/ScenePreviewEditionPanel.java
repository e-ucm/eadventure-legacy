package es.eucm.eadventure.editor.gui.otherpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.Trajectory.Node;
import es.eucm.eadventure.common.data.chapter.Trajectory.Side;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.NormalScenePreviewEditionController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.ScenePreviewEditionController;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElement;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementActiveArea;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementBarrier;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementExit;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementNode;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementPlayer;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementReference;

/**
 * This panel show the scene in different configurations, allowing objects
 * to be moved around, hidden, etc.
 * 
 * @author Eugenio Marchiori
 *
 */
public class ScenePreviewEditionPanel extends JPanel {

	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Key for a general unidentified category
	 */
	public static final int CATEGORY_NONE = 0;
	
	/**
	 * Key for the object reference category
	 */
	public static final int CATEGORY_OBJECT = 1;
	
	/**
	 * Key for the character reference category
	 */
	public static final int CATEGORY_CHARACTER = 2;
	
	/**
	 * Key for the atrezzo object category
	 */
	public static final int CATEGORY_ATREZZO = 3;
	
	/**
	 * Key for the barrier category
	 */
	public static final int CATEGORY_BARRIER = 4;
	
	/**
	 * Key for the active area category
	 */
	public static final int CATEGORY_ACTIVEAREA = 5;
	
	/**
	 * Key for the player reference category
	 */
	public static final int CATEGORY_PLAYER = 6;
	
	/**
	 * Key for the exit category
	 */
	public static final int CATEGORY_EXIT = 7;
	
	/**
	 * Key for the node category
	 */
	public static final int CATEGORY_NODE = 8;

	/**
	 * Default margin value 
	 */
	private static final int MARGIN = 20;


	private static final int LIGHT_BORDER = 1;

	private static final int HARD_BORDER = 2;
	
	private static final int RESCALE_BORDER = 3;

	private static final int RESCALE_BORDER_ACTIVE = 4;

	private static final int RESIZE_BORDER = 5;

	private static final int RESIZE_BORDER_ACTIVE = 6;

	private HashMap<Integer, List<ImageElement>> elements;
	
	private HashMap<Integer, Boolean> displayCategory;
	
	private HashMap<Integer, Boolean> movableCategory;
	
	private Trajectory trajectory;
	
	private Image background;
	
	private double sizeRatio;
		
	private int marginX;
	
	private int marginY;
	
	private int backgroundWidth;
	
	private int backgroundHeight;
	
	private boolean rescale;

	private boolean resize;
	
	private ImageElement firstElement;

	private ElementReferenceSelectionListener elementReferenceSelectionListener;
	
	private ScenePreviewEditionController spec;
	
	/**
	 * Image to be used as a backbuffer
	 */
	private BufferedImage backBuffer;
	
	/**
	 * The selected element in the panel
	 */
	private ImageElement selectedElement;
	
	/**
	 * Default constructor
	 */
	public ScenePreviewEditionPanel() {
		super();
		elements = new HashMap<Integer, List<ImageElement>>();
		displayCategory = new HashMap<Integer, Boolean>();
		movableCategory = new HashMap<Integer, Boolean>();
		setLayout(new BorderLayout());
		spec = new NormalScenePreviewEditionController(this);
		this.addMouseListener(spec);
		this.addMouseMotionListener(spec);
	}
	
	/**
	 * Constructor with the path to the background image
	 * 
	 * @param imagePath path to the background image
	 */
	public ScenePreviewEditionPanel(String imagePath) {
		this();
		loadBackground(imagePath);
	}
	
	/**
	 * Add new element to the panel, in a given category
	 * 
	 * @param category the category of the element
	 * @param element said element
	 */
	public void addElement(int category, ElementReferenceDataControl element) {
		Integer key = new Integer(category);
		addCategory(category, true, true);
		List<ImageElement> list = elements.get(key);
		list.add(new ImageElementReference(element));
	}
	
	/**
	 * Add the player to the panel
	 * 
	 * @param scene the scene's data control
	 * @param image the image of the player
	 */
	public void addPlayer(SceneDataControl scene, Image image){
	
		//if(scene.getPlayerLayer()!=-1){
			Integer key = new Integer(CATEGORY_PLAYER);
			addCategory(key, true, false);
			List<ImageElement> list = elements.get(key);
			list.add(new ImageElementPlayer(image, scene));
		//}
	}
	
	/**
	 * Add a barrier to the panel
	 * 
	 * @param barrierDataControl the barrier's data control
	 */
	public void addBarrier(BarrierDataControl barrierDataControl) {
		Integer key = new Integer(CATEGORY_BARRIER);
		addCategory(key, true, false);
		List<ImageElement> list = elements.get(key);
		list.add(new ImageElementBarrier(barrierDataControl));
	}

	/**
	 * Add an active area to the panel
	 * 
	 * @param activeAreaDataControl The active area's data control
	 */
	public void addActiveArea(ActiveAreaDataControl activeAreaDataControl) {
		Integer key = new Integer(CATEGORY_ACTIVEAREA);
		addCategory(key, true, true);
		List<ImageElement> list = elements.get(key);
		list.add(new ImageElementActiveArea(activeAreaDataControl));
	}
	
	/**
	 * Add an exit to the panel
	 * 
	 * @param exitDataControl the exit's data control
	 */
	public void addExit(ExitDataControl exitDataControl) {
		Integer key = new Integer(CATEGORY_EXIT);
		addCategory(key, true, true);
		List<ImageElement> list = elements.get(key);
		list.add(new ImageElementExit(exitDataControl));
	}

	/**
	 * Add a node for a trajectory to the panel
	 * 
	 * @param nodeDataControl the node's data control
	 */
	public void addNode(NodeDataControl nodeDataControl) {
		Integer key = new Integer(CATEGORY_NODE);
		addCategory(key, true, false);
		List<ImageElement> list = elements.get(key);
		list.add(new ImageElementNode(nodeDataControl));
	}

	/**
	 * Add a category to all the hashmaps
	 * 
	 * @param key The key of the category
	 * @param display Boolean indicating if it should be displayed
	 * @param movable Boolean indicating if the elmenets are movable
	 */
	private void addCategory(Integer key, boolean display, boolean movable) {
		if (!elements.containsKey(key))
			elements.put(key, new ArrayList<ImageElement>());
		if (!displayCategory.containsKey(key))
			displayCategory.put(key, new Boolean(display));
		if (!movableCategory.containsKey(key))
			movableCategory.put(key, new Boolean(movable));		
	}


	public void setTrajectory(Trajectory trajectory) {
		this.trajectory = trajectory;
	}
	
	/**
	 * Remove an element from a given category
	 * 
	 * @param category the category of the element
	 * @param element the element to remove
	 */
	public void removeElement(int category, ElementReferenceDataControl element) {
		Integer key = new Integer(category);
		List<ImageElement> list = elements.get(key);
		if (list!= null){
				list.remove(new ImageElementReference(element));
		}
		
		repaint();
	}
	
	/**
	 * Set a category to be display or hidden
	 * 
	 * @param category the category
	 * @param display boolean indicating if the category should be displayed
	 */
	public void setDisplayCategory(int category, boolean display) {
		Integer key = new Integer(category);
		displayCategory.put(key, new Boolean(display));	
		if (!elements.containsKey(key))
			elements.put(key, new ArrayList<ImageElement>());
		repaint();
	}
	
	/**
	 * Set a category to be movable or fixed
	 * 
	 * @param category the category
	 * @param movable boolean indicating if the category should be movable
	 */
	public void setMovableCategory(int category, boolean movable) {
		Integer key = new Integer(category);
		movableCategory.put(key, new Boolean(movable));
		if (!elements.containsKey(key))
			elements.put(key, new ArrayList<ImageElement>());
	}

	/**
	 * Set a single movable element, only this object will be moved
	 * 
	 * @param element The movable element
	 */
	public void setMovableElement(ElementReferenceDataControl element) {
		selectedElement = new ImageElementReference(element);
		for (Integer key : movableCategory.keySet()) {
			movableCategory.put(key, new Boolean(false));
		}
	}
	
	/**
	 * Set the background image
	 * 
	 * @param background the background image
	 */
	public void setBackground(Image background) {
		this.background = background;
		repaint();
	}
	
	public void repaint() {
		super.repaint();
		if (getSize().width > 0 && getSize().height > 0) {
			calculateSize();
			backBuffer = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_4BYTE_ABGR);
			paintBackBuffer();
			flip();
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if (backBuffer != null) {
			g.drawImage(backBuffer, marginX, marginY, marginX + backgroundWidth, marginY + backgroundHeight, marginX, marginY, marginX + backgroundWidth, marginY + backgroundHeight, null);
		}
	}
		
	/**
	 * Flip the backbuffer
	 */
	public void flip() {
		this.getGraphics().drawImage(backBuffer, marginX, marginY, marginX + backgroundWidth, marginY + backgroundHeight, marginX, marginY, marginX + backgroundWidth, marginY + backgroundHeight, null);
	}
	
	/**
	 * Paint the components to the backbuffer
	 */
	public void paintBackBuffer() {
		Graphics g = backBuffer.getGraphics();
		
		if (background != null) {
			paintRelativeImage( g, background, background.getWidth(null)/2, background.getHeight(null), 1);
		} else {
	    	ImageIcon icon = new ImageIcon("img/icons/noImageFrame.png"); 
	    	Image image;
	    	if (icon != null && icon.getImage() != null)
	    		image = icon.getImage();
	    	else
	    		image = new BufferedImage(100,120,BufferedImage.TYPE_3BYTE_BGR);
			paintRelativeImage( g, image, image.getWidth(null)/2, image.getHeight(null), 1);
		}
		
		List<ImageElement> elementsToDraw = new ArrayList<ImageElement>();
		
		for (Integer key : displayCategory.keySet()) {
			if (displayCategory.get(key)) {
				for (ImageElement imageElement : elements.get(key)) {
					elementsToDraw.add(imageElement);
				}
			}
		}
		
		Collections.sort(elementsToDraw);
		
		for (ImageElement imageElement : elementsToDraw) {
			if (imageElement instanceof ImageElementPlayer){
				// player must be painted or not, depending on autor´s selection
				if (imageElement.getImage()!=null)
					paintRelativeImage( g, imageElement.getImage(), imageElement.getX(), imageElement.getY(), imageElement.getScale());
			}else{
				paintRelativeImage( g, imageElement.getImage(), imageElement.getX(), imageElement.getY(), imageElement.getScale());
			}
		}
		
		if (spec.getUnderMouse() != null) {
			paintBorders(g, spec.getUnderMouse(), LIGHT_BORDER);
		}
		if (selectedElement != null) {
			paintBorders(g, selectedElement, HARD_BORDER);
			if (selectedElement.canRescale()) {
				if (rescale)
					paintBorders(g, selectedElement, RESCALE_BORDER_ACTIVE);
				else
					paintBorders(g, selectedElement, RESCALE_BORDER);
			}
			if (selectedElement.canResize()) {
				if (resize)
					paintBorders(g, selectedElement, RESIZE_BORDER_ACTIVE);
				else
					paintBorders(g, selectedElement, RESIZE_BORDER);
			}
		}
		paintTrajectory(g);
	}

	/**
	 * Paint borders to an element
	 * 
	 * @param g The graphics where to paint
	 * @param element The element that needs borders
	 * @param border_type The type of the borders
	 */
	private void paintBorders(Graphics g, ImageElement element,
			int border_type) {
		
		int x = (int) ((element.getX() - element.getImage().getWidth(null) *element.getScale() / 2)* sizeRatio) + marginX;
		int y = (int) ((element.getY() - element.getImage().getHeight(null) * element.getScale()) * sizeRatio) + marginY;
		int width = (int) (element.getImage().getWidth(null) * element.getScale() * sizeRatio);
		int height = (int) (element.getImage().getHeight(null) * element.getScale() * sizeRatio);
		
		if (border_type == LIGHT_BORDER) {
			Color color = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y, width, height);
			g.setColor(color);
			g.drawImage(element.getImage(), x, y, width, height, null);
		} else if (border_type == HARD_BORDER) {
			Color color = g.getColor();
			g.setColor(Color.RED);
			g.fillRect(x - 4, y - 4, width + 8, 4);
			g.fillRect(x - 4, y - 4, 4, height + 4);
			g.fillRect(x + width, y, 4, height + 4);
			g.fillRect(x - 4, y + height, width + 4, 4);
			g.setColor(color);
			g.drawImage(element.getImage(), x, y, width, height, null);
		} else if (border_type == RESCALE_BORDER) {
			Color color = g.getColor();
			g.setColor(Color.GREEN);
			g.drawRect(x + width - 8, y - 8, 16, 16);
			g.setColor(color);
		} else if (border_type == RESCALE_BORDER_ACTIVE) {
			Color color = g.getColor();
			g.setColor(Color.BLUE);
			g.drawRect(x + width - 8, y - 8, 16, 16);
			g.drawRect(x + width - 7, y - 7, 14, 14);
			g.setColor(color);
		} else if (border_type == RESIZE_BORDER) {
			Color color = g.getColor();
			g.setColor(Color.GREEN);
			g.drawRect(x + width -8, y + height - 8, 16, 16);
			g.setColor(color);
		} else if (border_type == RESIZE_BORDER_ACTIVE) {
			Color color = g.getColor();
			g.setColor(Color.BLUE);
			g.drawRect(x + width - 8, y + height - 8, 16, 16);
			g.drawRect(x + width - 7, y + height - 7, 14, 14);
			g.setColor(color);
		}
		
	}

	/**
	 * Load the background image form a path
	 * 
	 * @param imagePath the path to the background image
	 */
	public void loadBackground( String imagePath ) {
		if( imagePath != null && imagePath.length( ) > 0 )
			background = AssetsController.getImage( imagePath );
		calculateSize();
		repaint();
	}

	/**
	 * Calculate the size of the images, depending on the size of the
	 * panel and the size of the background image
	 */
	private synchronized void calculateSize() {

		int backgroundWidth2 = 800;
		int backgroundHeight2 = 600;
		if (background != null) {
			backgroundWidth2 = background.getWidth(null);
			backgroundHeight2 = background.getHeight(null);
		}
		
		if( background != null && getWidth( ) > 0 && getHeight( ) > 0 ) {
			double panelRatio = (double) getWidth( ) / (double) getHeight( );
			double imageRatio = (double) backgroundWidth2 / (double) backgroundHeight2;
			int width, height;
			marginX = MARGIN;
			marginY = MARGIN;
			
			if( panelRatio <= imageRatio ) {
				int panelWidth = getWidth( ) - MARGIN * 2;
				width = panelWidth;
				height = (int) (panelWidth / imageRatio);
			}

			else {
				int panelHeight = getHeight( ) - MARGIN * 2;
				width = (int) ( panelHeight * imageRatio );
				height = panelHeight;
			}
			
			marginX = (getWidth() - width) / 2;
			marginY = (getHeight() - height) / 2;
			backgroundWidth = width;
			backgroundHeight = height;

			sizeRatio = (double) width / (double) backgroundWidth2;
		}
	}

	/**
	 * Paints an rescaled image in the given graphics.
	 * 
	 * @param g
	 *            Graphics to paint
	 * @param image
	 *            Image to be painted
	 * @param x
	 *            Absolute X position of the center of the image
	 * @param y
	 *            Absolute Y position of the bottom of the image
	 */
	protected void paintRelativeImage( Graphics g, Image image, int x, int y, double scale) {
		if( image != null ) {
			int width = (int) ( image.getWidth( null ) * sizeRatio );
			int height = (int) ( image.getHeight( null ) * sizeRatio );

			int posX = marginX + (int) (x * sizeRatio - width  * scale / 2);
			int posY = marginY + (int) (y * sizeRatio - height * scale);

			g.drawImage( image, posX, posY, (int) (width * scale), (int) (height * scale), null );
		}
	}

	
	/**
	 * Paints a rescaled line form and to the given points
	 * 
	 * @param g The graphics where the line is drawn
	 * @param x1 The absolute x of the start point
	 * @param y1 The absolute y of the start point
	 * @param x2 The absolute x of the end point
	 * @param y2 The absolute y of the end point
	 */
	protected void drawRelativeLine( Graphics g, int x1, int y1, int x2, int y2) {
		int posX1 = marginX + (int) (x1 * sizeRatio);
		int posY1 = marginY + (int) (y1 * sizeRatio);
		int posX2 = marginX + (int) (x2 * sizeRatio);
		int posY2 = marginY + (int) (y2 * sizeRatio);
		g.drawLine(posX1, posY1, posX2, posY2);
	}

	/**
	 * Draw the sides of the trajectory in the graphics component
	 * 
	 * @param g the graphics component where to draw
	 */
	private void paintTrajectory(Graphics g) {
		if (trajectory == null)
			return;
		
		for (Side side : trajectory.getSides()) {
			Node start = trajectory.getNodeForId(side.getIDStart());
			Node end = trajectory.getNodeForId(side.getIDEnd());
			drawRelativeLine(g, start.getX(), start.getY(), end.getX(), end.getY());
		}
		
		if (firstElement != null) {
			int mouseX = (int) getMousePosition().getX();
			int mouseY = (int) getMousePosition().getY();
			int x = (int) ((mouseX - marginX) / sizeRatio);
			int y = (int) ((mouseY - marginY) / sizeRatio);
			drawRelativeLine(g, firstElement.getX(), firstElement.getY() - 10, x, y);
		}
	}
	
	/**
	 * Recreate the image of an element
	 * 
	 * @param element The element to be recrated
	 */
	public void recreateElement(ElementReferenceDataControl element) {
		for (Integer key : elements.keySet()) {
			for (ImageElement imageElement : elements.get(key)) {	
				if (imageElement.getElementReferenceDataControl() != null &&
						imageElement.getElementReferenceDataControl() == element) {
					imageElement.recreateImage();
				}
			}
		}
		if (selectedElement != null && selectedElement.getElementReferenceDataControl() != null &&
				selectedElement.getElementReferenceDataControl() == element) {
			selectedElement.recreateImage();
		}
	}

	/**
	 * Get the movable element at position (x,y)
	 * 
	 * @param x the x-axis value
	 * @param y the y-axis value
	 * @return The movable ImageElement at (x,y)
	 */
	public ImageElement getMovableElement(int x, int y) {
		for (Integer key : movableCategory.keySet()) {
			if (movableCategory.get(key)) {
				for (ImageElement imageElement : elements.get(key)) {
					double scale = imageElement.getScale();
					int minX = (int) (imageElement.getX() - imageElement.getImage().getWidth(null) * scale / 2);
					int minY = (int) (imageElement.getY() - imageElement.getImage().getHeight(null) * scale);
					int maxX = (int) (minX + imageElement.getImage().getWidth(null) * scale);
					int maxY = (int) (minY + imageElement.getImage().getHeight(null) * scale);
					if (x > minX && x < maxX && y > minY && y < maxY)
						return imageElement;
				}
			}
		}
		return null;
	}

	/**
	 * Return the rescaleable element in the position x,y
	 * 
	 * @param x Position along the x-axis
	 * @param y Position along the y-axis
	 * @return The imageElement found or null
	 */
	public ImageElement getRescaleElement(int x, int y) {
		if (selectedElement == null || !selectedElement.canRescale())
			return null;
		int x_image = (int) ((selectedElement.getX() + selectedElement.getImage().getWidth(null) * selectedElement.getScale() / 2));
		int y_image = (int) ((selectedElement.getY() - selectedElement.getImage().getHeight(null) * selectedElement.getScale()));

		int margin = (int) (8.0f / getSizeRatio());
		if (x > x_image - margin &&
				x < x_image + margin &&
				y > y_image - margin &&
				y < y_image + margin) {
			return selectedElement;
		}
		return null;
	}

	/**
	 * Return the resizable element in the position x,y
	 * 
	 * @param x Position along the x-axis
	 * @param y Position along the y-axis
	 * @return The imageElement found or null
	 */
	public ImageElement getResizeElement(int x, int y) {
		if (selectedElement == null || !selectedElement.canResize())
			return null;
		int x_image = (int) ((selectedElement.getX() + selectedElement.getImage().getWidth(null) * selectedElement.getScale() / 2));
		int y_image = (int) ((selectedElement.getY() - selectedElement.getImage().getHeight(null) * selectedElement.getScale()));
		int height = (int) ((selectedElement.getImage().getHeight(null)* selectedElement.getScale()));
		
		int margin = (int) (8.0f / getSizeRatio());
		if (x > x_image - margin &&
				x < x_image + margin &&
				y > y_image + height - margin &&
				y < y_image + height + margin) {
			return selectedElement;
		}
		return null;
	}

	/**
	 * Changes the current selectedElement created from a ElementReferenceDataControl.
	 * 
	 * @param erdc
	 * 				The new ElementReferenceDataControl
	 */
	public void setSelectedElement(ElementReferenceDataControl erdc){
		this.selectedElement = new ImageElementReference(erdc);
	}
	
	/**
	 * Changes the current selectedElement created from a ElementReferenceDataControl.
	 * 
	 * @param erdc
	 * 				The new ElementReferenceDataControl
	 */
	public void setSelectedElement(DataControl erdc,Image image, SceneDataControl sceneDataControl){
		if (erdc!=null)
			this.selectedElement = new ImageElementReference((ElementReferenceDataControl)erdc);
		else 
			this.selectedElement = new ImageElementPlayer(image,sceneDataControl);
	}
	
	public void setSelectedElement(ImageElement imageElement) {
		this.selectedElement = imageElement;
		if (elementReferenceSelectionListener != null){
			if (selectedElement != null)
				elementReferenceSelectionListener.elementReferenceSelected(selectedElement.getElementReferenceDataControl());
			else
				elementReferenceSelectionListener.elementReferenceSelected(null);
		}
	}

	/**
	 * Changes the element selection listener that captures the event
	 * 
	 * @param elementReferenceSelectionListener
	 * 				the new element reference selection listener
	 */
	public void setElementReferenceSelectionListener(
			ElementReferenceSelectionListener elementReferenceSelectionListener) {
		this.elementReferenceSelectionListener = elementReferenceSelectionListener;
	}
	
	public void recreateElements(int category) {
		Integer key = new Integer(category);
		for (ImageElement imageElement : elements.get(key)) {
			imageElement.recreateImage();
		}		
	}

	public double getSizeRatio() {
		return sizeRatio;
	}

	public int getMarginX() {
		return marginX;
	}
	
	public int getMarginY() {
		return marginY;
	}
	
	public ImageElement getSelectedElement() {
		return selectedElement;
	}
	
	public boolean isRescale() {
		return rescale;
	}
	
	public void setRescale(boolean rescale) {
		this.rescale = rescale;
	}
	
	/**
	 * Set a new mouse controller for the ScenePreviewEditiorPanel
	 * 
	 * @param controller the new controller
	 */
	public void changeController(ScenePreviewEditionController controller) {
		this.removeMouseListener(spec);
		this.removeMouseMotionListener(spec);
		spec = controller;
		this.addMouseListener(controller);
		this.addMouseMotionListener(controller);
	}

	/**
	 * Remove a element form the hashmaps
	 * 
	 * @param category The category of the element
	 * @param imageElement The element to be removed
	 */
	public void removeElement(int category, ImageElement imageElement) {
		Integer key = new Integer(category);
		List<ImageElement> list = elements.get(key);
		list.remove(imageElement);
	}
	
	public void setFirstElement(ImageElement firstElement) {
		this.firstElement = firstElement;
	}
	
	public ImageElement getFirstElement() {
		return firstElement;
	}

	public boolean isResize() {
		return resize;
	}
	
	public void setResize(boolean resize) {
		this.resize = resize;
	}
}
