package es.eucm.eadventure.common.data.adaptation;

/**
 * This interface must be implemented both by AdaptationProfile and AdaptationRule
 * @author Javier
 *
 */
public interface ContainsAdaptedState {
	public AdaptedState getAdaptedState();
	public void setAdaptedState ( AdaptedState adaptedState );
	
}
