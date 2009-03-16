package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;

/**
 * A null action that does nothing
 * 
 * @author Eugenio Marchiori
 *
 */
public class FunctionalNullAction extends FunctionalAction {

	/**
	 * Default constructor
	 */
	public FunctionalNullAction() {
		super(null);
		finished = false;
		type = -1;
	}

	@Override
	public void start(FunctionalPlayer functionalPlayer) {
		this.functionalPlayer = functionalPlayer;
	}

	@Override
	public void update(long elapsedTime) {
	}

	@Override
	public void drawAditionalElements() {
	}

	@Override
	public void stop() {
	}

	@Override
	public void setAnotherElement(FunctionalElement element) {
	}

}
