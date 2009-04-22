package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.data.chapter.effects.Macro;
import es.eucm.eadventure.common.data.chapter.effects.MacroReferenceEffect;
import es.eucm.eadventure.engine.core.control.Game;

public class FunctionalMacroReferenceEffect extends FunctionalEffect{

	private MacroReferenceEffect macroRefEffect;
	
	public FunctionalMacroReferenceEffect(MacroReferenceEffect effect) {
		super(effect);
		macroRefEffect = effect;
	}

	@Override
	public boolean isInstantaneous() {
		return true;
	}

	@Override
	public boolean isStillRunning() {
		return false;
	}

	@Override
	public void triggerEffect() {
		// Get the macro
		Macro macro = Game.getInstance().getCurrentChapterData().getMacro( macroRefEffect.getTargetId() );
		
		// Build list of functional effects
		List<FunctionalEffect> fEffects = new ArrayList<FunctionalEffect>( );
		for ( AbstractEffect effect: macro.getEffects() ){
			fEffects.add( FunctionalEffect.buildFunctionalEffect( effect ) );
		}
		
		// Add functional effects to the stack-queue. The GameStateEffects will trigger them normally
		//Game.getInstance().addToTheStack( fEffects );
		Game.getInstance().storeEffectsInQueue(fEffects,false);
		}

}
