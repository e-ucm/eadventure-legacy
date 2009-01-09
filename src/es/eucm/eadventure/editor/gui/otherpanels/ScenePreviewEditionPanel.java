package es.eucm.eadventure.editor.gui.otherpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ReferencesListDataControl;

/**
 * This panel show the scene in different configurations, allowing objects
 * to be moved around, hidden, etc.
 * 
 * @author Eugenio Marchiori
 *
 */
public class ScenePreviewEditionPanel extends JPanel implements MouseListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int CATEGORY_NONE = 0;
	
	public static final int CATEGORY_OBJECT = 1;
	
	public static final int CATEGORY_CHARACTER = 2;
	
	public static final int CATEGORY_ATREZZO = 3;
	
	public static final int CATEGORY_BARRIER = 4;
	
	public static final int CATEGORY_ACTIVEAREA = 5;

	private static final int MARGIN = 20;

	private static final int LIGHT_BORDER = 1;

	private static final int HARD_BORDER = 2;
	
	private static final int RESIZE_BORDER = 3;
	
	private HashMap<Integer, List<ImageElement>> elements;
	
	private HashMap<Integer, Boolean> displayCategory;
	
	private HashMap<Integer, Boolean> movableCategory;
	
	private Image background;
	
	private double sizeRatio;

	private ImageElement underMouse;
		
	private int startDragX;
	
	private int startDragY;
	
	private int originalX;
	
	private int originalY;

	private float originalScale;
	
	private int marginX;
	
	private int marginY;
	
	private int backgroundWidth;
	
	private int backgroundHeight;
	
	private boolean resize;
	
	private ElementReferenceSelectionListener elementReferenceSelectionListener;
	
	/**
	 * Image to be used as a backbuffer
	 */
	private BufferedImage backBuffer;
	
	/**
	 * The movable element, for one there is just one
	 */
	private ImageElement movableElement;
	
	/**
	 * Default constructor
	 */
	public ScenePreviewEditionPanel() {
		super();
		elements = new HashMap<Integer, List<ImageElement>>();
		displayCategory = new HashMap<Integer, Boolean>();
		movableCategory = new HashMap<Integer, Boolean>();
		setLayout(new BorderLayout());
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
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
		if (!elements.containsKey(key))
			elements.put(key, new ArrayList<ImageElement>());
		if (!displayCategory.containsKey(key))
			displayCategory.put(key, new Boolean(true));
		if (!movableCategory.containsKey(key))
			movableCategory.put(key, new Boolean(true));
		List<ImageElement> list = elements.get(key);
		list.add(new ImageElement(element));
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
			list.remove(new ImageElement(element));
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
		movableElement = new ImageElement(element);
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
	private void flip() {
		this.getGraphics().drawImage(backBuffer, marginX, marginY, marginX + backgroundWidth, marginY + backgroundHeight, marginX, marginY, marginX + backgroundWidth, marginY + backgroundHeight, null);
	}
	
	/**
	 * Paint the components to the backbuffer
	 */
	private void paintBackBuffer() {
		Graphics g = backBuffer.getGraphics();
		paintRelativeImage( g, background, background.getWidth(null)/2, background.getHeight(null), 1);
		
		for (Integer key : displayCategory.keySet()) {
			if (displayCategory.get(key)) {
				for (ImageElement imageElement : elements.get(key)) {
					paintRelativeImage( g, imageElement.getImage(), imageElement.getX(), imageElement.getY(), imageElement.getScale());
				}
			}
		}
		
		if (underMouse != null) {
			paintBorders(g, underMouse, LIGHT_BORDER);
		}
		if (movableElement != null) {
			paintBorders(g, movableElement, HARD_BORDER);
			paintBorders(g, movableElement, RESIZE_BORDER);
		}
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
		} else if (border_type == RESIZE_BORDER) {
			Color color = g.getColor();
			g.setColor(Color.GREEN);
			g.drawRect(x + width - 8, y - 8, 16, 16);
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
		if( background != null && getWidth( ) > 0 && getHeight( ) > 0 ) {
			double panelRatio = (double) getWidth( ) / (double) getHeight( );
			double imageRatio = (double) background.getWidth( null ) / (double) background.getHeight( null );
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

			sizeRatio = (double) width / (double) background.getWidth( null );
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

			int posX = (int) (x * sizeRatio - width  * scale / 2);
			int posY = (int) (y * sizeRatio - height * scale);
			posX += marginX;
			posY += marginY;

			g.drawImage( image, posX, posY, (int) (width * scale), (int) (height * scale), null );
		}
	}

	/**
	 * Class that represents an ElementReferenceDataControl and its image
	 */
	private class ImageElement {
		
		private Image image;
		
		private ElementReferenceDataControl elementReferenceDataControl;
		
		public ImageElement(ElementReferenceDataControl elementReferenceDataControl) {
			this.elementReferenceDataControl = elementReferenceDataControl;
			String imagePath = Controller.getInstance( ).getElementImagePath( elementReferenceDataControl.getElementId( ) );
			if (imagePath != null)
				image = AssetsController.getImage( imagePath );
			else
				image = (new ImageIcon("img/assets/EmptyImage.png")).getImage();
		}
		
		public float getScale() {
			return elementReferenceDataControl.getElementScale();
		}

		public ElementReferenceDataControl getElementReferenceDataControl() {
			return elementReferenceDataControl;
		}
		
		public Image getImage() {
			return image;
		}
		
		public int getX() {
			return elementReferenceDataControl.getElementX();
		}
		
		public int getY() {
			return elementReferenceDataControl.getElementY();
		}

		public void recreateImage() {
			String imagePath = Controller.getInstance( ).getElementImagePath( elementReferenceDataControl.getElementId( ) );
			if (imagePath != null)
				image = AssetsController.getImage( imagePath );
			else
				image = (new ImageIcon("img/assets/EmptyImage.png")).getImage();
		}
		
		public boolean equals(Object o) {
			if (o == null)
				return false;
			if (!(o instanceof ImageElement))
				return false;
			ImageElement temp = (ImageElement) o;
			if (temp.getElementReferenceDataControl().getElementId().equals(this.getElementReferenceDataControl().getElementId()))
				return true;
			return false;
		}
			
	}

	/**
	 * Get the visible element at position (x,y)
	 * 
	 * @param x the x-axis value
	 * @param y the y-axis value
	 * @return The visible ImageElement at (x,y)
	 */
	private ImageElement getVisibleElement(int x, int y) {
		for (Integer key : displayCategory.keySet()) {
			if (displayCategory.get(key)) {
				for (ImageElement imageElement : elements.get(key)) {
					int minX = imageElement.getX() - imageElement.getImage().getWidth(null) / 2;
					int minY = imageElement.getY() - imageElement.getImage().getHeight(null);
					int maxX = minX + imageElement.getImage().getWidth(null);
					int maxY = minY + imageElement.getImage().getHeight(null);
					if (x > minX && x < maxX && y > minY && y < maxY)
						return imageElement;
				}
			}
		}
		return null;
	}

	/**
	 * Recreate the image of an element
	 * 
	 * @param element The element to be recrated
	 */
	public void recreateElement(ElementReferenceDataControl element) {
		for (Integer key : elements.keySet()) {
				for (ImageElement imageElement : elements.get(key)) {
					if (imageElement.getElementReferenceDataControl() == element) {
						imageElement.recreateImage();
					}
				}
		}
		if (movableElement != null && movableElement.getElementReferenceDataControl() == element) {
			movableElement.recreateImage();
		}
	}

	/**
	 * Get the movable element at position (x,y)
	 * 
	 * @param x the x-axis value
	 * @param y the y-axis value
	 * @return The movable ImageElement at (x,y)
	 */
	private ImageElement getMovableElement(int x, int y) {
		if (movableElement != null) {
			double scale = movableElement.getScale();
			int minX = (int) (movableElement.getX() - movableElement.getImage().getWidth(null) * scale / 2);
			int minY = (int) (movableElement.getY() - movableElement.getImage().getHeight(null) * scale);
			int maxX = (int) (minX + movableElement.getImage().getWidth(null) * scale);
			int maxY = (int) (minY + movableElement.getImage().getHeight(null) * scale);
			if (x > minX && x < maxX && y > minY && y < maxY)
				return movableElement;
			else
				return null;
		}
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

	
	/* Mouse Listeners methods */

	public void mouseClicked(MouseEvent e) {
		if (movableElement != null) {
			int x = (int) ((e.getX() - marginX) / sizeRatio);
			int y = (int) ((e.getY() - marginY) / sizeRatio);
			movableElement.getElementReferenceDataControl().setElementPosition(x, y);
			paintBackBuffer();
			flip();
		}
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

		if (underMouse != null) {
			startDragX = e.getX();
			startDragY = e.getY();
			originalX = underMouse.getX();
			originalY = underMouse.getY();
			originalScale = underMouse.getScale();
			if (elementReferenceSelectionListener != null){
				elementReferenceSelectionListener.elementReferenceSelected(underMouse.getElementReferenceDataControl());
			}
		}
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent e) {
		if (underMouse != null && !resize) {
			int changeX = (int) ((e.getX() - startDragX) / sizeRatio);
			int changeY = (int) ((e.getY() - startDragY) / sizeRatio);
			int x = originalX + changeX;
			int y = originalY + changeY;
			underMouse.getElementReferenceDataControl().setElementPosition(x, y);			
			paintBackBuffer();
			flip();
		} else if (underMouse != null && resize) {
			double changeX = (e.getX() - startDragX);
			double changeY = - (e.getY() - startDragY);
			double width = underMouse.getImage().getWidth(null);
			double heigth = underMouse.getImage().getHeight(null);
			
			double temp = changeX / width;
			double temp2 = changeY / heigth;
			
			float scale = originalScale;
			if (temp*temp > temp2*temp2)
				scale += temp;
			
			underMouse.getElementReferenceDataControl().setElementScale(scale);
			paintBackBuffer();
			flip();
		}
	}

	public void mouseMoved(MouseEvent e) {
		int x = (int) ((e.getX() - marginX) / sizeRatio);
		int y = (int) ((e.getY() - marginY) / sizeRatio);
		ImageElement imageElement = getMovableElement(x, y);
		ImageElement resizeElement = getResizeElement(x, y);
		if (resizeElement == null && imageElement != underMouse) {
			underMouse = imageElement;
			resize = false;
			paintBackBuffer();
			flip();
		} else if (resizeElement != null && (!resize || imageElement != underMouse)) {
			underMouse = resizeElement;
			resize = true;
			paintBackBuffer();
			flip();
		} else if (resizeElement == null) {
			underMouse = null;
			resize = false;
		}
	}

	private ImageElement getResizeElement(int x, int y) {
		if (movableElement == null)
			return null;
		int x_image = (int) ((movableElement.getX() - movableElement.getImage().getWidth(null) *movableElement.getScale() / 2));
		int y_image = (int) ((movableElement.getY() - movableElement.getImage().getHeight(null) * movableElement.getScale()));
		int width = (int) (movableElement.getImage().getWidth(null)*movableElement.getScale());

		if (x > x_image + width - 8 &&
				x < x_image + width + 8 &&
				y > y_image - 8 &&
				y < y_image + 8) {
			return movableElement;
		}

		return null;
	}
	
	/**
	 * Changes the current underMouse attribute created from a ElementReferenceDataControl.
	 * 
	 * @param erdc
	 * 				The new ElementReferenceDataControl that will be created to underMouse attribute
	 */
	public void setUnderMouse(ElementReferenceDataControl erdc){
		this.underMouse = new ImageElement(erdc);
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


}
