package es.eucm.eadventure.editor.gui.otherpanels.scenelistelements;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.data.chapter.effects.TriggerSceneEffect;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.config.SceneLinksConfigData;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;

public class SceneElement {

	private SceneDataControl sceneDataControl;
	
	private List<ExitElement> exitElements;
	
	private List<ActiveAreaElement> activeAreaElements;
	
	private List<ItemReferenceElement> itemReferenceElements;
	
	private int posX;
	
	private int posY;
	
	private Image image;
	
	private Color color;
	
	private boolean showName;

	private boolean visible;
	
	public SceneElement(SceneDataControl sceneDataControl) {
		this.sceneDataControl = sceneDataControl;
		visible = true;
		String temp = sceneDataControl.getPreviewBackground();
		image = AssetsController.getImage(temp);
		if (image == null) {
        	ImageIcon icon = new ImageIcon("img/icons/noImageFrame.png");
        	image = new BufferedImage(800, 600, BufferedImage.TYPE_4BYTE_ABGR);
        	image.getGraphics().drawImage(icon.getImage(), 0, 0, 800, 600, null);
		}
		setColor();
		exitElements = new ArrayList<ExitElement>();
		activeAreaElements = new ArrayList<ActiveAreaElement>();
		itemReferenceElements = new ArrayList<ItemReferenceElement>();
		Graphics2D g = (Graphics2D) image.getGraphics();
		AlphaComposite alphaComposite = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.3f);
		g.setComposite(alphaComposite);
		g.setColor(color);
		for (ExitDataControl exit : sceneDataControl.getExitsList().getExits()) {
			int x = exit.getX();
			int y = exit.getY();
			int width = exit.getWidth();
			int height = exit.getHeight();
			g.fillRect(x, y, width, height);
			exitElements.add(new ExitElement(exit));
		}
		for (ActiveAreaDataControl aadc : sceneDataControl.getActiveAreasList().getActiveAreas()) {
			boolean hasTriggerScene = false;
			List<String> sceneIds = new ArrayList<String>();
			for (ActionDataControl adc : aadc.getActionsList().getActions()) {
				for (Effect e : adc.getEffects().getEffects()) {
					if (e.getType() == Effect.TRIGGER_SCENE) {
						TriggerSceneEffect tse = (TriggerSceneEffect) e;
						sceneIds.add(tse.getTargetId());
						hasTriggerScene = true;
					}
				}
			}
			if (hasTriggerScene) {
				int x = aadc.getX();
				int y = aadc.getY();
				int width = aadc.getWidth();
				int height = aadc.getHeight();
				g.fillRect(x, y, width, height);
				activeAreaElements.add(new ActiveAreaElement(aadc, sceneIds));
			}
		}
		for (ElementReferenceDataControl irdc : sceneDataControl.getReferencesList().getItemReferences()) {
			boolean hasTriggerScene = false;
			List<String> sceneIds = new ArrayList<String>();
			for (ActionDataControl adc : ((ItemDataControl) irdc.getReferencedElementDataControl()).getActionsList().getActions()) {
				for (Effect e : adc.getEffects().getEffects()) {
					if (e.getType() == Effect.TRIGGER_SCENE) {
						TriggerSceneEffect tse = (TriggerSceneEffect) e;
						sceneIds.add(tse.getTargetId());
						hasTriggerScene = true;
					}
				}
			}
			if (hasTriggerScene) {
				Image image;
				String imagePath = Controller.getInstance().getElementImagePath(irdc.getElementId());
				if (imagePath != null)
					image = AssetsController.getImage(imagePath);
				else
					image = (new ImageIcon("img/assets/EmptyImage.png")).getImage();
				int width = (int) (irdc.getElementScale() * image.getWidth(null));
				int height = (int) (irdc.getElementScale() * image.getHeight(null));
				int x = irdc.getElementX() - width / 2;
				int y = irdc.getElementY() - height;
				g.drawImage(image, x, y, width, height, null);
				itemReferenceElements.add(new ItemReferenceElement(irdc, sceneIds));
			}
		}
		
		posX = (new Random()).nextInt(780);
		posY = (new Random()).nextInt(580);	
	}

	private void setColor() {
		switch ((new Random()).nextInt(9)) {
		case 0:
			color = Color.CYAN;
			break;
		case 1:
			color = Color.BLUE;
			break;
		case 2:
			color = Color.RED;
			break;
		case 3:
			color = Color.GREEN;
			break;
		case 4:
			color = Color.MAGENTA;
			break;
		case 5:
			color = Color.ORANGE;
			break;
		case 6:
			color = Color.PINK;
			break;
		case 7:
			color = Color.WHITE;
			break;
		case 8:
			color = Color.YELLOW;
			break;
		}
	}
	
	public Image getImage() {
		return image;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public Color getColor() {
		return color;
	}
	public int getWidth() {
		return image.getWidth(null);
	}
	
	public int getHeight()  {
		return image.getHeight(null);
	}

	public String getId() {
		return sceneDataControl.getId();
	}

	public List<ExitElement> getExitElements() {
		return exitElements;
	}

	public void changePosition(int x, int y) {
		if (x < 0)
			this.posX = 0;
		else if(x > 780)
			this.posX = 780;
		else
			this.posX = x;
		if (y < 0)
			this.posY = 0;
		else if(y > 580)
			this.posY = 580;
		else
			this.posY = y;
		SceneLinksConfigData.setSceneX(getId(), x);
		SceneLinksConfigData.setSceneY(getId(), y);
	}

	public DataControl getDataControl() {
		return sceneDataControl;
	}

	public void setShowName(boolean b) {
		showName = b;		
	}
	
	public boolean isShowName() {
		return showName;
	}

	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
		SceneLinksConfigData.setSceneVisible(getId(), visible);
	}

	public List<ActiveAreaElement> getActiveAreaElements() {
		return activeAreaElements;
	}

	public List<ItemReferenceElement> getItemReferenceElements() {
		return itemReferenceElements;
	}
}
