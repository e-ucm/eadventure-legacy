package es.eucm.eadventure.common.data.chapter.effects;

public class TriggerLastSceneEffect extends AbstractEffect{

    
    public TriggerLastSceneEffect(){
	super();
    }
	
	public int getType( ) {
		return Effect.TRIGGER_LAST_SCENE;
	}
	
	public Object clone() throws CloneNotSupportedException {
		TriggerLastSceneEffect tlse = (TriggerLastSceneEffect) super.clone();
		return tlse;
	}
}
