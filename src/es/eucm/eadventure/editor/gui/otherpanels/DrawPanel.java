package es.eucm.eadventure.editor.gui.otherpanels;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A JPanel that allows double-buffering, zooming and other
 * advanced features.
 * 
 * @author Eugenio Marchiori
 *
 */
public class DrawPanel  extends JPanel {

	/**
	 * Default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The back-buffer
	 */
	private BufferedImage backBuffer;

	/**
	 * Default margin value 
	 */
	private static final int MARGIN = 0;

	/**
	 * The margin left along the x-axis
	 */
	private int marginX;
	
	/**
	 * The margin left along the y-axis
	 */
	private int marginY;
	
	/**
	 * Width of the background image
	 */
	private int backgroundWidth;
	
	/**
	 * Height of the background image
	 */
	private int backgroundHeight;

	/**
	 * The ratio between the size of the panel and the size of the background image
	 */
	private double sizeRatio;
	
	/**
	 * The background image
	 */
	private Image background;
	
	/**
	 * The width of the back-buffer
	 */
	private int width;
	
	/**
	 * The height of the back-buffer
	 */
	private int height;
	
	/**
	 * The zoom value (1.0 = no zoom)
	 */
	private double zoom;
	
	/**
	 * The horizontal scroll bar
	 */
	private JScrollBar scrollX;
	
	/**
	 * The vertical scroll bar
	 */
	private JScrollBar scrollY;
	
	/**
	 * The panel where the back-buffer is drawn
	 */
	private JPanel insidePanel;

	
	public DrawPanel(boolean zoomable) {
		zoom = 1.0;
		this.setLayout(new BorderLayout());
		insidePanel = createInsidePanel();
		this.add(insidePanel, BorderLayout.CENTER);
		
		addZoomElements(zoomable);
	}
	
	/**
	 * Creates the zoom elements and adds them to the panel if necessary
	 * 
	 * @param zoomable boolean indicating if the zoom elements must be added
	 */
	private void addZoomElements(boolean zoomable) {
		JPanel sliderPanel = new JPanel();
		
		Icon zoomOutIcon = new ImageIcon( "img/icons/zoomout.png" );
		JButton zoomout = new JButton(zoomOutIcon);
		zoomout.setSize(20,20);
		sliderPanel.add(zoomout);
		
		
		final JSlider slider = new JSlider(10, 30);
		slider.setValue(10);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				double zoom = (double) slider.getValue() / 10.0;
				DrawPanel.this.setZoom(zoom);
				DrawPanel.this.getParent().repaint();
			}
		});
		sliderPanel.add(slider);

		Icon zoomInIcon = new ImageIcon( "img/icons/zoomin.png" );
		JButton zoomin = new JButton(zoomInIcon);
		zoomin.setSize(20,20);
		sliderPanel.add(zoomin);

		zoomin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				slider.setValue(slider.getValue() + 3);
			}
		});
		zoomout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				slider.setValue(slider.getValue() - 3);
			}
		});

		
		scrollX = new JScrollBar(JScrollBar.HORIZONTAL, 0, 10, 0, 100);
		scrollY = new JScrollBar(JScrollBar.VERTICAL, 0, 10, 0, 100);
		scrollX.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				DrawPanel.this.getParent().repaint();
			}
		});
		scrollY.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				DrawPanel.this.getParent().repaint();
			}
		});
		scrollX.setEnabled(false);
		scrollY.setEnabled(false);
		if (zoomable) {
			this.add(sliderPanel, BorderLayout.NORTH);
			this.add(scrollX, BorderLayout.SOUTH);
			this.add(scrollY, BorderLayout.EAST);
		}
	}
	
	/**
	 * Create the insidePanel where the back-buffer is drawn
	 * 
	 * @return the insidePanel
	 */
	private JPanel createInsidePanel() {
		insidePanel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			public void repaint() {
				super.repaint();
				if (getSize().width > 0 && getSize().height > 0) {
					calculateSize();
					if (backBuffer == null || width != getSize().width || height != getSize().height) {
						backBuffer = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_4BYTE_ABGR);
						width = getSize().width;
						height = getSize().height;
					}
				}
			}
			public void paint(Graphics g) {
				super.paint(g);
				if (backBuffer != null) {
					int dx = marginX + (scrollX != null ? (int) ((scrollX.getValue() / 90.0) * (1 - 1/zoom) * backgroundWidth) : 0);
					int dy = marginY + (scrollY != null ? (int) ((scrollY.getValue() / 90.0) * (1 - 1/zoom) * backgroundHeight) : 0);
					int dw = dx + (int) (backgroundWidth / zoom);
					int dh = dy + (int) (backgroundHeight / zoom);
					g.drawImage(backBuffer, marginX, marginY, marginX + backgroundWidth, marginY + backgroundHeight, dx, dy, dw, dh, null);
				}
			}
		};
		return insidePanel;
	}
	
	/**
	 * Set the value for the zoom
	 * 
	 * @param zoom the value
	 */
	public void setZoom(double zoom) {
		this.zoom = zoom;
		if (zoom > 1.0) {
			scrollX.setEnabled(true);
			scrollY.setEnabled(true);
		} else if (scrollX != null && scrollY != null){
			scrollX.setEnabled(false);
			scrollY.setEnabled(false);
		}
	}
	
	/**
	 * Set the background image for the panel
	 * 
	 * @param background the background image
	 */
	public void setBackground(Image background) {
		this.background = background;
		this.calculateSize();
	}
	
	public void repaint() {
		super.repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
	}
			
	public Graphics getGraphics() {
		if (backBuffer == null) {
			backBuffer = new BufferedImage(insidePanel.getSize().width, insidePanel.getSize().height, BufferedImage.TYPE_4BYTE_ABGR);
			height = insidePanel.getSize().height;
			width = insidePanel.getSize().width;
		}
		return backBuffer.getGraphics();
	}
	
	/**
	 * Paints the background in the back-buffer
	 */
	public void paintBackground() {
		if (background != null) {
			paintRelativeImage( background, background.getWidth(null)/2, background.getHeight(null), 1);
		} else {
	    	ImageIcon icon = new ImageIcon("img/icons/noImageFrame.png"); 
	    	Image image;
	    	if (icon != null && icon.getImage() != null)
	    		image = icon.getImage();
	    	else
	    		image = new BufferedImage(100,120,BufferedImage.TYPE_3BYTE_BGR);
			paintRelativeImage( image, image.getWidth(null)/2, image.getHeight(null), 1);
		}
	}
	
	/**
	 * Paints an rescaled image in the back-buffer
	 * 
	 * @param image
	 *            Image to be painted
	 * @param x
	 *            Absolute X position of the center of the image
	 * @param y
	 *            Absolute Y position of the bottom of the image
	 * @param scale
	 * 			  The scale of the image
	 */
	protected void paintRelativeImage(Image image, int x, int y, double scale) {
		if(checkBackBuffer() && image != null) {
			int width = (int) ( image.getWidth( null ) * sizeRatio );
			int height = (int) ( image.getHeight( null ) * sizeRatio );

			int posX = marginX + (int) (x * sizeRatio - width  * scale / 2);
			int posY = marginY + (int) (y * sizeRatio - height * scale);

			backBuffer.getGraphics().drawImage( image, posX, posY, (int) (width * scale), (int) (height * scale), null );
		}
	}

	/**
	 * Paints a rescaled image in the back-buffer
	 * 
	 * @param image
	 * 			The image to be painted
	 * @param x
	 * 			Absolute X position of the top left corner of the image
	 * @param y
	 * 			Absolute Y position of the top left corner of the image
	 * @param scale
	 * 			The scale of the image
	 */
	public void paintRelativeImageTop(Image image, int x, int y, double scale) {
		if(checkBackBuffer() && image != null) {
			int width = (int) ( image.getWidth( null ) * sizeRatio );
			int height = (int) ( image.getHeight( null ) * sizeRatio );

			int posX = marginX + (int) (x * sizeRatio);
			int posY = marginY + (int) (y * sizeRatio);

			backBuffer.getGraphics().drawImage( image, posX, posY, (int) (width * scale), (int) (height * scale), null );
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
	protected void drawRelativeLine( int x1, int y1, int x2, int y2) {
		if(checkBackBuffer()) {
			int posX1 = marginX + (int) (x1 * sizeRatio);
			int posY1 = marginY + (int) (y1 * sizeRatio);
			int posX2 = marginX + (int) (x2 * sizeRatio);
			int posY2 = marginY + (int) (y2 * sizeRatio);
			backBuffer.getGraphics().drawLine(posX1, posY1, posX2, posY2);
		}
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
		
		if( background != null && insidePanel.getWidth( ) > 0 && insidePanel.getHeight( ) > 0 ) {
			double panelRatio = (double) insidePanel.getWidth( ) / (double) insidePanel.getHeight( );
			double imageRatio = (double) backgroundWidth2 / (double) backgroundHeight2;
			int width, height;
			marginX = MARGIN;
			marginY = MARGIN;
			
			if( panelRatio <= imageRatio ) {
				int panelWidth = insidePanel.getWidth( ) - MARGIN * 2;
				width = panelWidth;
				height = (int) (panelWidth / imageRatio);
			}

			else {
				int panelHeight = insidePanel.getHeight( ) - MARGIN * 2;
				width = (int) ( panelHeight * imageRatio );
				height = panelHeight;
			}
			
			marginX = (insidePanel.getWidth() - width) / 2;
			marginY = (insidePanel.getHeight() - height) / 2;
			backgroundWidth = width;
			backgroundHeight = height;

			sizeRatio = (double) width / (double) backgroundWidth2;
		}
	}
	
	/**
	 * Returns the real X value in relation to the background of the X relative to the panel
	 * 
	 * @param mouseX
	 * @return
	 */
	public int getRealX(int mouseX) {
		int dx = marginX - (scrollX != null ? (int) ((scrollX.getValue() / (90.0) * zoom) * (1 - 1/zoom) * backgroundWidth) : 0);
		return (int) ((mouseX - dx) / sizeRatio / zoom);
	}

	/**
	 * Returns the X relative to the panel for a real X relative to the background
	 * 
	 * @param realX
	 * @return
	 */
	public int getRelativeX(int realX) {
		//int dx = marginX - (scrollX != null ? (int) ((scrollX.getValue() / (90.0) / zoom) * (1 - 1/zoom) * backgroundWidth) : 0);
		//return (int) ((realX) * sizeRatio * zoom) + dx;
		return marginX + (int) (realX * sizeRatio);
	}
	
	/**
	 * Returns the real Y value in relation to the background of the Y relative to the panel
	 * 
	 * @param mouseY
	 * @return
	 */
	public int getRealY(int mouseY) {
		int dy = marginY - (scrollY != null ? (int) ((scrollY.getValue() / (90.0) * zoom) * (1 - 1/zoom) * backgroundHeight) : 0);
		return (int) ((mouseY - dy) / sizeRatio / zoom);
	}

	/**
	 * Returns the Y relative to the panel for a real Y relative to the background
	 * 
	 * @param realY
	 * @return
	 */
	public int getRelativeY(int realY) {
//		int dy = marginY - (scrollY != null ? (int) ((scrollY.getValue() / (90.0) / zoom) * (1 - 1/zoom) * backgroundHeight) : 0);
//		return (int) ((realY) * sizeRatio * zoom) + dy;
		
		return marginY + (int) (realY * sizeRatio);

	}

	public int getRelativeWidth(int realWidth) {
		return (int) (realWidth * sizeRatio );
	}
	
	public int getRealWidth(int relativeWidth) {
		return (int) (relativeWidth / sizeRatio / zoom);
	}
	
	public int getRelativeHeight(int realHeight) {
		return (int) (realHeight * sizeRatio);
	}
	
	public int getRealHeight(int relativeHeight) {
		return (int) (relativeHeight / sizeRatio / zoom);
	}
	
	public void drawRelativeLine(int x1, int y1, int x2, int y2, Color color) {
		if(checkBackBuffer()) {
			int posX1 = marginX + (int) (x1 * sizeRatio);
			int posY1 = marginY + (int) (y1 * sizeRatio);
			int posX2 = marginX + (int) (x2 * sizeRatio);
			int posY2 = marginY + (int) (y2 * sizeRatio);
			Graphics g = backBuffer.getGraphics();
			g.setColor(color);
			g.drawLine(posX1, posY1, posX2, posY2);
		}
	}

	public void fillRelativeCircle(int x2, int y2, Color color, int i) {
		if(checkBackBuffer()) {
			int posX2 = marginX + (int) (x2 * sizeRatio);
			int posY2 = marginY + (int) (y2 * sizeRatio);
			Graphics g = backBuffer.getGraphics();
			g.setColor(color);
			g.fillOval(posX2 - i / 2, posY2 - i / 2, i, i);
		}
	}
	
	public void fillRelativePoly(int x[], int y[], Color color, float alpha) {
		if (checkBackBuffer()) {
			int x2[] = new int[x.length];
			int y2[] = new int[y.length];
			for (int i = 0; i < x2.length; i++) {
				x2[i] = marginX + (int) (x[i] * sizeRatio);
				y2[i] = marginY + (int) (y[i] * sizeRatio);
			}
			
			Graphics2D g = (Graphics2D) backBuffer.getGraphics();
			g.setColor(color);
			if (alpha != 1.0f) {
				AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
				g.setComposite(alphaComposite);
			}
			g.fillPolygon(x2, y2, x2.length);
		}
		
	}


	public void drawRelativeString(String id, int posX, int posY) {
		if(checkBackBuffer()) {
			int posX1 = marginX + (int) (posX * sizeRatio);
			int posY1 = marginY + (int) (posY * sizeRatio);
			backBuffer.getGraphics().drawString(id, posX1, posY1);
		}
	}

	public void drawRelativeArrowTip(int x1, int y1, int x2, int y2, Color color) {
		if(checkBackBuffer()) {
			Graphics g = backBuffer.getGraphics();
			g.setColor(color);
			int[] xPoints = new int[3];
			int[] yPoints = new int[3];
			int posX2 = marginX + (int) (x2 * sizeRatio);
			int posY2 = marginY + (int) (y2 * sizeRatio);
			
			double w = x2 - x1;
			double h = y2 - y1;
			double temp = Math.sqrt(w*w + h*h);
			if (temp != 0) {
				w = w/temp;
				h = h/temp;
				
				xPoints[0] = posX2;
				yPoints[0] = posY2;
				xPoints[1] = (int) (posX2 - 10*w + 5*h);
				yPoints[1] = (int) (posY2 - 5*w - 10*h);
				xPoints[2] = (int) (posX2 - 10*w - 5*h);
				yPoints[2] = (int) (posY2 + 5*w - 10*h);
							
				g.fillPolygon(xPoints, yPoints, 3);
			}
		}
	}
	
	private boolean checkBackBuffer() {
		if (backBuffer == null && insidePanel.getSize().width > 0 && insidePanel.getSize().height > 0) {
			backBuffer = new BufferedImage(insidePanel.getSize().width, insidePanel.getSize().height, BufferedImage.TYPE_4BYTE_ABGR);
			width = insidePanel.getSize().width;
			height = insidePanel.getSize().height;
		}
		return backBuffer != null;
	}
	
	public void removeMouseListener(MouseListener ml) {
		insidePanel.removeMouseListener(ml);
	}
	
	public void addMouseListener(MouseListener ml) {
		insidePanel.addMouseListener(ml);
	}
	
	public void removeMouseMotionListener(MouseMotionListener ml) {
		insidePanel.removeMouseMotionListener(ml);
	}
	
	public void addMouseMotionListener(MouseMotionListener ml) {
		insidePanel.addMouseMotionListener(ml);
	}
	
}