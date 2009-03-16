package es.eucm.eadventure.editor.control.controllers.metadata.lomes;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMRequirement;
import es.eucm.eadventure.editor.data.meta.ims.IMSTechnical;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESTechnical;

public class LOMESTechnicalDataControl {

	private LOMESTechnical data;
	
	public LOMESTechnicalDataControl (LOMESTechnical data){
		this.data = data;
	}
	
	
	public LOMRequirement getRequirement(){
	  return  data.getRequirement();
	}
	
	/**
	 * @return the data
	 */
	public LOMESTechnical getData( ) {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData( LOMESTechnical data ) {
		this.data = data;
	}

}
