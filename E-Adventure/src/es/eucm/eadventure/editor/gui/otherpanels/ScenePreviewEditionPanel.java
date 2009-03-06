package es.eucm.eadventure.editor.gui.otherpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.data.chapter.Rectangle;
import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.Trajectory.Node;
import es.eucm.eadventure.common.data.chapter.Trajectory.Side;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.NormalScenePreviewEditionController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.ScenePreviewEditionController;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.InfluenceAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.PointDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElement;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementActiveArea;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementBarrier;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementExit;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementInfluenceArea;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementNode;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementPlayer;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementPoint;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementReference;
import es.eucm.eadventure.engine.core.gui.GUI;

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
	 * Key for the point category
	 */
	public static final int CATEGORY_POINT = 9;

	/**
	 * Key for the influence area category
	 */
	public static final int CATEGORY_INFLUENCEAREA = 10;

	private static final int LIGHT_BORDER = 1;

	private static final int HARD_BORDER = 2;
	
	private static final int RESCALE_BORDER = 3;

	private static final int RESCALE_BORDER_ACTIVE = 4;

	private static final int RESIZE_BORDER = 5;

	private static final int RESIZE_BORDER_ACTIVE = 6;


	/**
	 * Hashmap with a list of imageElement for every Integer representing a category
	 */
	private HashMap<Integer, List<ImageElement>> elements;
	
	/**
	 * Hashmap with a Boolean indicating if the category represented by the Integer must be displayed
	 */
	private HashMap<Integer, Boolean> displayCategory;
	
	/**
	 * Hashmap with a Boolean indicating if the category represented by the Integer must be displayed
	 */
	private HashMap<Integer, Boolean> movableCategory;
	
	/**
	 * The trajectory asociated with the scene
	 */
	private Trajectory trajectory;
	
	private Rectangle rectangle;
	
	private Color rectangleColor = Color.RED;
	
	/**
	 * Boolean indicating if the user is rescaling
	 */
	private boolean rescale;

	/**
	 * Boolean indicating if the user is resizeing
	 */
	private boolean resize;
	
	/**
	 * The first ImageElement selected for tools that need another one
	 */
	private ImageElement firstElement;

	private ElementReferenceSelectionListener elementReferenceSelectionListener;
	
	/**
	 * The ScenePreviewEditionController being currently used
	 */
	private ScenePreviewEditionController spec;
		
	/**
	 * The selected element in the panel
	 */
	private ImageElement selectedElement;
	
	private ImageElementInfluenceArea influenceArea;
	
	private boolean fixedSelectedElement;
	
	/**
	 * Panel where the elements are drawn
	 */
	private DrawPanel drawPanel;

	/**
	 * Panel where the checkboxes are shown
	 */
	private JPanel checkBoxPanel;
	
	/**
	 * Boolean indicating if the checkboxes should be shown
	 */
	private boolean showCheckBoxes = true;
		
	/**
	 * Panel with the spinners for the precise edition
	 */
	private JPanel preciseEditionPanel;
	
	/**
	 * Spinner to edit the x position of the selected element
	 */
	private JSpinner posXSpinner;
	
	/**
	 * Spinner to edit the y position of the selected element
	 */
	private JSpinner posYSpinner;
	
	/**
	 * Spinner to edit the scale of the selected element
	 */
	private JSpinner scaleSpinner;
	
	/**
	 * Spinner to edit the width of the selected element
	 */
	private JSpinner widthSpinner;
	
	/**
	 * Spinner to edit the height of the selected element
	 */
	private JSpinner heightSpinner;
	
	/**
	 * Boolean that determines if the precise edition is enabled
	 */
	private boolean showTextEdition = false;

	private boolean resizeInfluenceArea;
	
	/**
	 * Default constructor
	 */
	public ScenePreviewEditionPanel(boolean zoomable) {
		super();
		elements = new HashMap<Integer, List<ImageElement>>();
		displayCategory = new HashMap<Integer, Boolean>();
		movableCategory = new HashMap<Integer, Boolean>();
		BorderLayout bl = new BorderLayout();
		bl.setHgap(10);
		bl.setVgap(10);
		setLayout(bl);
		drawPanel = new DrawPanel(zoomable);
		add(drawPanel, BorderLayout.CENTER);
		recreateCheckBoxPanel();
		recreateTextEditionPanel();
		spec = new NormalScenePreviewEditionController(this);
		changeController(spec);
	}
	
	private void recreateTextEditionPanel() {
		if (preciseEditionPanel != null)
			remove(preciseEditionPanel);
		if (showTextEdition && selectedElement != null) {
			preciseEditionPanel = createTextEditionPanel();
			add(preciseEditionPanel, BorderLayout.NORTH);
		}
	}
	
	private void recreateCheckBoxPanel() {
		if (checkBoxPanel != null)
			remove(checkBoxPanel);
		if (showCheckBoxes) {
			checkBoxPanel = createCheckBoxPanel();
			add(checkBoxPanel, BorderLayout.SOUTH);
		}
	}
		
	/**
	 * Constructor with the path to the background image
	 * 
	 * @param imagePath path to the background image
	 */
	public ScenePreviewEditionPanel(boolean zoomable, String imagePath) {
		this(zoomable);
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
		recreateCheckBoxPanel();
	}
	
	/**
	 * Add the player to the panel
	 * 
	 * @param scene the scene's data control
	 * @param image the image of the player
	 */
	public void addPlayer(SceneDataControl scene, Image image){
		Integer key = new Integer(CATEGORY_PLAYER);
		addCategory(key, true, true);
		List<ImageElement> list = elements.get(key);
		list.add(new ImageElementPlayer(image, scene));
		recreateCheckBoxPanel();
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
		recreateCheckBoxPanel();
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
		recreateCheckBoxPanel();
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
		recreateCheckBoxPanel();
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
		recreateCheckBoxPanel();
	}
	
	public void addPoint(PointDataControl pointDataControl) {
		Integer key = new Integer(CATEGORY_POINT);
		addCategory(key, true, true);
		List<ImageElement> list = elements.get(key);
		list.add(new ImageElementPoint(pointDataControl));
		recreateCheckBoxPanel();
	}

	
	/**
	 * Add an influenceArea for an element to the panel
	 * 
	 * @param influenceArea the elements influence area
	 */
	public void addInfluenceArea(InfluenceAreaDataControl influenceArea) {
		Integer key = new Integer(CATEGORY_INFLUENCEAREA);
		addCategory(key, true, true);
		List<ImageElement> list = elements.get(key);
		list.clear();
		ImageElement temp = null;
		for (Integer key2 : elements.keySet()) {
			for (ImageElement imageElement : elements.get(key2)) {	
				if (imageElement.getDataControl() != null &&
						imageElement.getDataControl() == influenceArea.getDataControl()) {
					temp = imageElement;
				}
			}
		}
		if (temp == null)
			temp = selectedElement;
		this.influenceArea = new ImageElementInfluenceArea(influenceArea, temp);
		list.add(this.influenceArea);
	}
	
	public void addInfluenceArea(ImageElementInfluenceArea influenceArea) {
		Integer key = new Integer(CATEGORY_INFLUENCEAREA);
		addCategory(key, true, true);
		List<ImageElement> list = elements.get(key);
		list.clear();
		this.influenceArea = influenceArea;
		list.add(this.influenceArea);
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


	/**
	 * Set a new value to trajectory
	 * @param trajectory the new value of trajectory
	 */
	public void setTrajectory(Trajectory trajectory) {
		this.trajectory = trajectory;
	}
	
	public void setIrregularRectangle(Rectangle rectangle, Color color) {
		this.rectangle = rectangle;
		this.rectangleColor = color;
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
		int index;
		if (list!= null){
			for ( index=0; index < list.size();index++)	
					if (list.get(index).getDataControl().equals(element))
						break;
			if (index>=0){
			list.remove(index);
				
		}
		}
		
		repaint();
	}

	/**
	 * Remove all element from a given category
	 * 
	 * @param category the category of the element
	 */
	public void removeElements(int category) {
		Integer key = new Integer(category);
		List<ImageElement> list = elements.get(key);
		list.clear();
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
		recreateTextEditionPanel();
	}
	
	/**
	 * Set the background image
	 * 
	 * @param background the background image
	 */
	public void setBackground(Image background) {
		drawPanel.setBackground(background);
		repaint();
	}
	
	public void repaint() {
		super.repaint();
	}
	
	public void paint(Graphics g) {
		paintBackBuffer();
		super.paint(g);
	}
			
	/**
	 * Paint the components to the backbuffer
	 */
	private void paintBackBuffer() {
		Graphics g = drawPanel.getGraphics();
		
		drawPanel.paintBackground();
		
		if (influenceArea != null)
			influenceArea.recreateImage();
		
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
			if (imageElement.isVisible())
				drawPanel.paintRelativeImage(imageElement.getImage(), imageElement.getX(), imageElement.getY(), imageElement.getScale());
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
		if (influenceArea != null) {
			if (resizeInfluenceArea)
				paintBorders(g, influenceArea, RESIZE_BORDER_ACTIVE);
			else
				paintBorders(g, influenceArea, RESIZE_BORDER);
		}
		paintTrajectory(g);
		paintIrregularRectangle(g);
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
		
		int x = (int) (element.getX() - element.getImage().getWidth(null) *element.getScale() / 2);
		x = drawPanel.getRelativeX(x);
		int y = (int) (element.getY() - element.getImage().getHeight(null) * element.getScale());
		y = drawPanel.getRelativeY(y);
		int width = drawPanel.getRelativeWidth((int) (element.getImage().getWidth(null) * element.getScale()));
		int height = drawPanel.getRelativeHeight((int) (element.getImage().getHeight(null) * element.getScale()));
		
		if (border_type == LIGHT_BORDER) {
			Color color = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y, width, height);
			g.setColor(color);
			drawPanel.paintRelativeImage(element.getImage(), element.getX(), element.getY(), element.getScale());
//			g.drawImage(element.getImage(), x, y, width, height, null);
		} else if (border_type == HARD_BORDER) {
			Color color = g.getColor();
			g.setColor(Color.RED);
			g.fillRect(x - 4, y - 4, width + 8, 4);
			g.fillRect(x - 4, y - 4, 4, height + 4);
			g.fillRect(x + width, y, 4, height + 4);
			g.fillRect(x - 4, y + height, width + 4, 4);
			g.setColor(color);
			drawPanel.paintRelativeImage(element.getImage(), element.getX(), element.getY(), element.getScale());
//			g.drawImage(element.getImage(), x, y, width, height, null);
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
			drawPanel.setBackground(AssetsController.getImage( imagePath ));
		repaint();
	}



	

	/**
	 * Draw the sides of the trajectory in the graphics component
	 * 
	 * @param g the graphics component where to draw
	 */
	private void paintTrajectory(Graphics g) {
		Integer key = new Integer(CATEGORY_NODE);
		if (trajectory == null || !(displayCategory.get(key) != null ? displayCategory.get(key) : false ))
			return;
		
		for (Side side : trajectory.getSides()) {
			Node start = trajectory.getNodeForId(side.getIDStart());
			Node end = trajectory.getNodeForId(side.getIDEnd());
			drawPanel.drawRelativeLine(start.getX(), start.getY(), end.getX(), end.getY());
		}
		
		if (firstElement != null) {
			int mouseX = (int) getMousePosition().getX();
			int mouseY = (int) getMousePosition().getY();
			int x = drawPanel.getRealX(mouseX);
			int y = drawPanel.getRealY(mouseY);
			drawPanel.drawRelativeLine(firstElement.getX(), firstElement.getY() - 10, x, y);
		}
	}

	
	/**
	 * Draw the sides of the trajectory in the graphics component
	 * 
	 * @param g the graphics component where to draw
	 */
	private void paintIrregularRectangle(Graphics g) {
		Integer key = new Integer(CATEGORY_POINT);
		if (rectangle == null || rectangle.isRectangular() || !(displayCategory.get(key) != null ? displayCategory.get(key) : false ))
			return;
		
		if (rectangle.getPoints().size() > 0) {
			int x[] = new int[rectangle.getPoints().size()];
			int y[] = new int[rectangle.getPoints().size()];
			for (int i = 0; i < rectangle.getPoints().size(); i++) {
				x[i] = rectangle.getPoints().get(i).x;
				y[i] = rectangle.getPoints().get(i).y;
			}
			drawPanel.fillRelativePoly(x, y, rectangleColor, 0.4f);
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
				if (imageElement.getDataControl() != null &&
						imageElement.getDataControl() == element) {
					imageElement.recreateImage();
				}
			}
		}
		if (selectedElement != null && selectedElement.getDataControl() != null &&
				selectedElement.getDataControl() == element) {
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
		if (fixedSelectedElement) {
			double scale = selectedElement.getScale();
			int minX = (int) (selectedElement.getX() - selectedElement.getImage().getWidth(null) * scale / 2);
			int minY = (int) (selectedElement.getY() - selectedElement.getImage().getHeight(null) * scale);
			int maxX = (int) (minX + selectedElement.getImage().getWidth(null) * scale);
			int maxY = (int) (minY + selectedElement.getImage().getHeight(null) * scale);
			if (x > minX && x < maxX && y > minY && y < maxY && !selectedElement.transparentPoint(x - minX, y - minY)) {
				return selectedElement;
			}
		}
		for (Integer key : movableCategory.keySet()) {
			if (movableCategory.get(key) && (displayCategory.get(key) != null ? displayCategory.get(key) : false)) {
				for (ImageElement imageElement : elements.get(key)) {
					double scale = imageElement.getScale();
					double width = imageElement.getWidth();
					double height = imageElement.getHeight();
					int minX = (int) (imageElement.getX() - width * scale / 2);
					int minY = (int) (imageElement.getY() - height * scale);
					int maxX = (int) (minX + width * scale);
					int maxY = (int) (minY + height * scale);
					if (x > minX && x < maxX && y > minY && y < maxY && !imageElement.transparentPoint(x - minX, y - minY)) {
						return imageElement;
					}
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

		int margin = drawPanel.getRealHeight(8);
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
		ImageElement tempElement = selectedElement;

		if ((tempElement == null || !tempElement.canResize()) && (influenceArea == null))
			return null;

		if (tempElement != null) {
			int x_image = (int) ((tempElement.getX() + tempElement.getImage().getWidth(null) * tempElement.getScale() / 2));
			int y_image = (int) ((tempElement.getY() - tempElement.getImage().getHeight(null) * tempElement.getScale()));
			int height = (int) ((tempElement.getImage().getHeight(null)* tempElement.getScale()));
			
			int margin = drawPanel.getRealWidth(8);
			if (x > x_image - margin &&
					x < x_image + margin &&
					y > y_image + height - margin &&
					y < y_image + height + margin) {
				return tempElement;
			}
		}
		if (influenceArea != null && movableCategory.get(new Integer(CATEGORY_INFLUENCEAREA))){
			tempElement = influenceArea;
			int x_image = (int) ((tempElement.getX() + tempElement.getImage().getWidth(null) * tempElement.getScale() / 2));
			int y_image = (int) ((tempElement.getY() - tempElement.getImage().getHeight(null) * tempElement.getScale()));
			int height = (int) ((tempElement.getImage().getHeight(null)* tempElement.getScale()));
			
			int margin = drawPanel.getRealWidth(8);
			if (x > x_image - margin &&
					x < x_image + margin &&
					y > y_image + height - margin &&
					y < y_image + height + margin) {
				return tempElement;
			}
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
				elementReferenceSelectionListener.elementReferenceSelected(selectedElement.getLayer());
			else
				elementReferenceSelectionListener.elementReferenceSelected(-1);
		}
		recreateTextEditionPanel();
	}
	
	public void setSelectedElement(ActiveAreaDataControl activeAreaDataControl) {
		this.setSelectedElement(new ImageElementActiveArea(activeAreaDataControl));
	}

	public void setSelectedElement(ExitDataControl exitDataControl) {
		this.setSelectedElement(new ImageElementExit(exitDataControl));
	}
	
	public void setSelectedElement(BarrierDataControl barrierDataControl) {
		this.setSelectedElement(new ImageElementBarrier(barrierDataControl));
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
	
	/**
	 * Recreates the images of all elements in a category
	 * 
	 * @param category the category of the elements to recreate
	 */
	public void recreateElements(int category) {
		Integer key = new Integer(category);
		for (ImageElement imageElement : elements.get(key)) {
			imageElement.recreateImage();
		}		
	}
	
	/**
	 * Returns the selected image element
	 * 
	 * @return the selected image element
	 */
	public ImageElement getSelectedElement() {
		return selectedElement;
	}
	
	/**
	 * Returns the value of rescale
	 * @return the value of rescale
	 */
	public boolean isRescale() {
		return rescale;
	}
	
	/**
	 * Set the value of rescale
	 * @param rescale the new value of rescale
	 */
	public void setRescale(boolean rescale) {
		if (rescale) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
		} else if (!resize && !resizeInfluenceArea){
			this.setCursor(Cursor.getDefaultCursor());
		}
		this.rescale = rescale;
	}
	
	/**
	 * Set a new mouse controller for the ScenePreviewEditiorPanel
	 * 
	 * @param controller the new controller
	 */
	public void changeController(ScenePreviewEditionController controller) {
		if (spec != null) {
			drawPanel.removeMouseListener(spec);
			drawPanel.removeMouseMotionListener(spec);
		}
		spec = controller;
		drawPanel.addMouseListener(controller);
		drawPanel.addMouseMotionListener(controller);
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
	
	/**
	 * Set the value of firstElement
	 * 
	 * @param firstElement the new value of firstElement
	 */
	public void setFirstElement(ImageElement firstElement) {
		this.firstElement = firstElement;
	}
	
	/**
	 * Returns the value of firstElement
	 * @return the value of firstElement
	 */
	public ImageElement getFirstElement() {
		return firstElement;
	}

	/**
	 * Returns the value of resize
	 * @return the value of resize
	 */
	public boolean isResize() {
		return resize;
	}
	
	public boolean isResizeInflueceArea() {
		return resizeInfluenceArea;
	}
	
	/**
	 * Set a new value to resize
	 * @param resize the new value of resize
	 */
	public void setResize(boolean resize) {
		if (resize || resizeInfluenceArea) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
		} else if (!rescale){
			this.setCursor(Cursor.getDefaultCursor());
		}
		this.resize = resize;
	}
	
	public void setResizeInflueceArea(boolean resizeInfluenceArea) {
		if (resize || resizeInfluenceArea) {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
		} else if (!rescale){
			this.setCursor(Cursor.getDefaultCursor());
		}
		this.resizeInfluenceArea = resizeInfluenceArea;
	}
	
	/**
	 * Creates the checkbox panel where the user can select
	 * which elements to show
	 * 
	 * @return The created panel
	 */
	public JPanel createCheckBoxPanel() {
		JPanel checkBoxPanel = new JPanel();
		
		List<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
		for (Integer key : displayCategory.keySet()) {
			JCheckBox newCheckBox = null;
			if (elements.get(key).size() > 0) {
				newCheckBox = createCheckBox(key);
			}
			if (newCheckBox != null)
				checkBoxList.add(newCheckBox);
		}
		
		checkBoxPanel.setLayout(new GridLayout(2,0));
		checkBoxPanel.setAutoscrolls(true);
		for (JCheckBox checkBox : checkBoxList)
			checkBoxPanel.add(checkBox);
		
		
		return checkBoxPanel;
	}
	
	/**
	 * Create the checkbox for a given category
	 * 
	 * @param category The category of the checkbox
	 * @return the created checkbox
	 */
	private JCheckBox createCheckBox(final Integer category) {
		String title = null;
		if (category.intValue() == CATEGORY_ACTIVEAREA)
			title = TextConstants.getText("SPEP.ShowActiveAreas");
		else if (category.intValue() == CATEGORY_ATREZZO)
			title = TextConstants.getText("SPEP.ShowAtrezzo");
		else if (category.intValue() == CATEGORY_BARRIER)
			title = TextConstants.getText("SPEP.ShowBarriers");
		else if (category.intValue() == CATEGORY_CHARACTER)
			title = TextConstants.getText("SPEP.ShowCharacterReferences");
		else if (category.intValue() == CATEGORY_EXIT)
			title = TextConstants.getText("SPEP.ShowExits");
		else if (category.intValue() == CATEGORY_NONE)
			title = TextConstants.getText("SPEP.ShowUncategorized");
		else if (category.intValue() == CATEGORY_OBJECT)
			title = TextConstants.getText("SPEP.ShowObjectReferences");
		else if (category.intValue() == CATEGORY_PLAYER)
			title = TextConstants.getText("SPEP.ShowPlayer");
		else if (category.intValue() == CATEGORY_NODE)
			title = TextConstants.getText("SPEP.ShowTrajectory");
		if (title == null)
			return null;
		
		JCheckBox temp = new JCheckBox(title);
		temp.setSelected(displayCategory.get(category));
		temp.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				boolean isSelected = ((JCheckBox) arg0.getSource()).isSelected();
				displayCategory.put(category, new Boolean(isSelected));
				ScenePreviewEditionPanel.this.repaint();
			}
		});
		return temp;
	}
	
	public void updateTextEditionPanel() {
		if (preciseEditionPanel != null) {
			if (posXSpinner != null)
				posXSpinner.setValue(new Integer(selectedElement.getX()));
			if (posYSpinner != null)
				posYSpinner.setValue(selectedElement.getY());
			if (scaleSpinner != null)
				scaleSpinner.setValue(selectedElement.getScale());
			if (widthSpinner != null)
				widthSpinner.setValue(selectedElement.getWidth());
			if (heightSpinner != null)
				heightSpinner.setValue(selectedElement.getHeight());
		}
	}
	
	public JPanel createTextEditionPanel() {
		JPanel textInputPanel = new JPanel();
		textInputPanel.add(new JLabel(TextConstants.getText("SPEP.XCoordinate")));
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(selectedElement.getX(), -200, drawPanel.getBackgroundWidth() + 200, 5);
		posXSpinner = new JSpinner(spinnerModel);
		posXSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int y = selectedElement.getY();
				selectedElement.changePosition(((Integer) posXSpinner.getValue()).intValue(), y);
				ScenePreviewEditionPanel.this.repaint();
			}
		});
		textInputPanel.add(posXSpinner);

		textInputPanel.add(new JLabel("   " + TextConstants.getText("SPEP.YCoordinate")));
		spinnerModel = new SpinnerNumberModel(selectedElement.getY(), -200, GUI.WINDOW_HEIGHT + 200, 5);
		posYSpinner = new JSpinner(spinnerModel);
		posYSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int x = selectedElement.getX();
				selectedElement.changePosition(x, (Integer) posYSpinner.getValue());
				ScenePreviewEditionPanel.this.repaint();
			}
		});
		textInputPanel.add(posYSpinner);
		
		
		if (selectedElement.canRescale()) {
			textInputPanel.add(new JLabel("   " + TextConstants.getText("SPEP.Scale")));
			spinnerModel = new SpinnerNumberModel(new Float(selectedElement.getScale()), new Float(0.02), new Float(15.00f), new Float(0.02f));
			scaleSpinner = new JSpinner(spinnerModel);
			scaleSpinner.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					selectedElement.setScale(((Float) scaleSpinner.getValue()).floatValue());
					ScenePreviewEditionPanel.this.repaint();
				}
			});
			textInputPanel.add(scaleSpinner);
		} else {
			scaleSpinner = null;
		}

		if (selectedElement.canResize()) {
			textInputPanel.add(new JLabel("   " + TextConstants.getText("SPEP.Width")));
			spinnerModel = new SpinnerNumberModel(selectedElement.getWidth(), 1, GUI.WINDOW_WIDTH + 200, 5);
			widthSpinner = new JSpinner(spinnerModel);
			widthSpinner.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					int height = selectedElement.getHeight();
					selectedElement.changeSize((Integer) widthSpinner.getValue(), height);
					selectedElement.recreateImage();
					ScenePreviewEditionPanel.this.repaint();
				}
			});
			textInputPanel.add(widthSpinner);

			textInputPanel.add(new JLabel("   " + TextConstants.getText("SPEP.Height")));
			spinnerModel = new SpinnerNumberModel(selectedElement.getHeight(), 1, GUI.WINDOW_HEIGHT + 200, 5);
			heightSpinner = new JSpinner(spinnerModel);
			heightSpinner.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					int width = selectedElement.getWidth();
					selectedElement.changeSize(width, (Integer) heightSpinner.getValue());
					selectedElement.recreateImage();
					ScenePreviewEditionPanel.this.repaint();
				}
			});
			textInputPanel.add(heightSpinner);
		} else {
			widthSpinner = null;
			heightSpinner = null;
		}

		return textInputPanel;
	}

	public void setFixedSelectedElement(boolean fixedSelectedElement) {
		this.fixedSelectedElement = fixedSelectedElement;
	}
	
	public boolean getFixedSelectedElement() {
		return fixedSelectedElement;
	}


	public ImageElement getInfluenceArea() {
		return influenceArea;
	}
	
	public void setShowCheckBoxes(boolean showCheckBoxes) {
		this.showCheckBoxes = showCheckBoxes;
		recreateCheckBoxPanel();
	}
	
	public void setShowTextEdition(boolean textEdition) {
		this.showTextEdition = textEdition;
	}

	public int getRealWidth(int i) {
		return drawPanel.getRealWidth(i);
	}
	
	public int getRealHeight(int i) {
		return drawPanel.getRealHeight(i);
	}

	public int getRealX(int mouseX) {
		return drawPanel.getRealX(mouseX);
	}
	
	public int getRealY(int mouseY) {
		return drawPanel.getRealY(mouseY);
	}

}
