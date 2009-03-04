package es.eucm.eadventure.editor.control.tools.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.tools.general.assets.SelectResourceTool;
import es.eucm.eadventure.editor.data.AssetInformation;

public class SelectLineAudioPathTool extends SelectResourceTool{

	protected static final String AUDIO_STR = "audio";
	
	protected ConversationLine line;
	
	protected static AssetInformation[] createAssetInfoArray (){
		AssetInformation[] array = new AssetInformation[1];
		array[0] = new AssetInformation( "", AUDIO_STR, true, AssetsController.CATEGORY_AUDIO, AssetsController.FILTER_NONE );
		return array;
	}
	
	protected static Resources createResources(ConversationLine line){
		Resources resources = new Resources();
		resources.addAsset(AUDIO_STR, line.getAudioPath());
		return resources;
	}
	
	public SelectLineAudioPathTool( ConversationLine line )
			throws CloneNotSupportedException {
		super(createResources(line), createAssetInfoArray(), Controller.CONVERSATION_GRAPH, 0);
		this.line = line;
	}

	@Override
	public boolean undoTool(){
		boolean done = super.undoTool();
		if (!done)
			return false;
		else {
			line.setAudioPath( resources.getAssetPath(AUDIO_STR) );
			//System.out.println("NEW CURSOR PATH = "+resources.getAssetPath(CURSOR_STR));
			controller.updatePanel();
			return true;
		}
		
	}
	
	@Override
	public boolean redoTool(){
		boolean done = super.redoTool();
		if (!done)
			return false;
		else {
			//System.out.println("NEW CURSOR PATH = "+resources.getAssetPath(CURSOR_STR));
			line.setAudioPath( resources.getAssetPath(AUDIO_STR) );
			controller.updatePanel();
			return true;
		}		
	}
	
	@Override
	public boolean doTool(){
		boolean done = super.doTool();
		if (!done)
			return false;
		else {
			line.setAudioPath( resources.getAssetPath(AUDIO_STR) );
			return true;
		}		
	}


}
