package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.animations.Animation;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

public class FunctionalCustom extends FunctionalAction {

	private FunctionalElement element;
	
	private String actionName;
	
	private long totalTime;
		
	private boolean hasAnimation;
	
	public FunctionalCustom(FunctionalElement element, String actionName) {
		super(null);
		this.element = element;
		this.actionName = actionName;
		originalAction = element.getFirstValidCustomAction(actionName);
		this.needsGoTo = ((CustomAction) originalAction).isNeedsGoTo();
		this.keepDistance = ((CustomAction) originalAction).getKeepDistance();
		this.hasAnimation = false;
	}

	@Override
	public void drawAditionalElements() {
	}

	@Override
	public void setAnotherElement(FunctionalElement element) {
	}

	@Override
	public void start(FunctionalPlayer functionalPlayer) {
		this.functionalPlayer = functionalPlayer;
		
        Resources resources = null;
        CustomAction customAction = (CustomAction) originalAction;
        for( int i = 0; i < customAction.getResources( ).size( ) && resources == null; i++ )
            if( new FunctionalConditions(customAction.getResources( ).get( i ).getConditions( )).allConditionsOk( ) )
            	resources = customAction.getResources( ).get( i );

		MultimediaManager multimedia = MultimediaManager.getInstance( );
		Animation animation = null;

		if (resources.getAssetPath("actionAnimation") != null && !resources.getAssetPath("actionAnimation").equals("")) {
			if (element.getX() > functionalPlayer.getX()) {
				animation = multimedia.loadAnimation( resources.getAssetPath( "actionAnimation" ), false, MultimediaManager.IMAGE_PLAYER );
			} else {
				animation = multimedia.loadAnimation( resources.getAssetPath( "actionAnimation" ), true, MultimediaManager.IMAGE_PLAYER );
			}
			functionalPlayer.setAnimation(animation, -1);
			hasAnimation = true;
		} 
		totalTime = 0;
		finished = false;
	}

	@Override
	public void stop() {
		finished = true;
	}

	@Override
	public void update(long elapsedTime) {
        totalTime += elapsedTime;
        if( totalTime > 1000 ) {
    		
            finished = true;
            if (hasAnimation)
            	functionalPlayer.popAnimation();
            
            if (element instanceof FunctionalItem) {
            	FunctionalItem item = (FunctionalItem) element;
	            if( !item.custom(actionName) )
	                functionalPlayer.speak( GameText.getTextCustomCannot( ) );
            } else if (element instanceof FunctionalNPC) {
            	FunctionalNPC npc = (FunctionalNPC) element;
            	if (!npc.custom(actionName))
            		functionalPlayer.speak( GameText.getTextCustomCannot());
            }
        }

	}

}
