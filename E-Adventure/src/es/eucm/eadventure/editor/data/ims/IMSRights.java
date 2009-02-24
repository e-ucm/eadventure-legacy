package es.eucm.eadventure.editor.data.ims;

import es.eucm.eadventure.editor.data.lom.LangString;
import es.eucm.eadventure.editor.data.lom.Vocabulary;

public class IMSRights {

	/*
	 * cost and copyrightandotherrestrictions only can be "yes" or "no"
	 */

	// 6.1
	private Vocabulary cost;
	
	// 6.2
	private Vocabulary copyrightandotherrestrictions;
	
	
	public IMSRights(){
		cost = new Vocabulary(Vocabulary.IMS_YES_NO);
		copyrightandotherrestrictions = new Vocabulary(Vocabulary.IMS_YES_NO);;
	}


	public Vocabulary getCost() {
		return cost;
	}


	public void setCost(int index) {
		this.cost.setValueIndex(index);
	}


	public Vocabulary getCopyrightandotherrestrictions() {
		return copyrightandotherrestrictions;
	}


	public void setCopyrightandotherrestrictions(int index) {
		this.copyrightandotherrestrictions.setValueIndex(index);
	}


	
	
	
}
