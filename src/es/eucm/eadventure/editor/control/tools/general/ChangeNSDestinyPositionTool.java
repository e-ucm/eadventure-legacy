package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.Positioned;
import es.eucm.eadventure.editor.control.Controller;


/**
 * Edition tool for changing the destiny position of a NextScene
 * @author Javier
 *
 */
public class ChangeNSDestinyPositionTool extends ChangePositionTool {

	public ChangeNSDestinyPositionTool(Positioned nextScene, int newX, int newY){
		super (nextScene, newX, newY);
		this.addListener(new ChangePositionToolListener(){
			public void positionUpdated(int newX, int newY) {
				Controller.getInstance().reloadPanel();
			}
		});
	}
	

}
