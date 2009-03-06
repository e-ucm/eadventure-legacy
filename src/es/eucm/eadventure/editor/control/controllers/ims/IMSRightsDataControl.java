package es.eucm.eadventure.editor.control.controllers.metadata.ims;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.ims.IMSRights;


public class IMSRightsDataControl {

	public static final String[] AVAILABLE_OPTIONS= new String[]{"yes", "no"};
	
	private IMSRights data;
	
	public IMSRightsDataControl(IMSRights data){
		this.data = data;
	}
	
	public IMSOptionsDataControl getCost() {
		return new IMSOptionsDataControl (){

			public String[] getOptions( ) {
				return new String[]{"Yes","No"};
			}

			public void setOption( int option ) {
				if (option!=getSelectedOption()){
					data.setCost(option) ;
				}
				
			}

			public int getSelectedOption( ) {
				for (int i=0; i<AVAILABLE_OPTIONS.length; i++){
					if (AVAILABLE_OPTIONS[i].equals( data.getCost() ))
						return i;
				}
				return -1;
			}
			
		};
	}
	
	public IMSOptionsDataControl getCopyrightandotherrestrictions() {
		return new IMSOptionsDataControl (){

			public String[] getOptions( ) {
				return new String[]{"Yes","No"};
			}

			public void setOption( int option ) {
				if (option!=getSelectedOption()){
					data.setCopyrightandotherrestrictions(option);
				}
				
			}

			public int getSelectedOption( ) {
				for (int i=0; i<AVAILABLE_OPTIONS.length; i++){
					if (AVAILABLE_OPTIONS[i].equals( data.getCopyrightandotherrestrictions() ))
						return i;
				}
				return -1;
			}
			
		};
	}

	public IMSRights getData() {
		return data;
	}

	public void setData(IMSRights data) {
		this.data = data;
	}
	
	
}
