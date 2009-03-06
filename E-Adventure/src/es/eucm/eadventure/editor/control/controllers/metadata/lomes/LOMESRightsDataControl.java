package es.eucm.eadventure.editor.control.controllers.metadata.lomes;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.ims.IMSRights;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESRights;


public class LOMESRightsDataControl {

	public static final String[] AVAILABLE_OPTIONS= new String[]{"yes", "no"};
	
	private LOMESRights data;
	
	public LOMESRightsDataControl(LOMESRights data){
		this.data = data;
	}
	
	public LOMESOptionsDataControl getCost() {
		return new LOMESOptionsDataControl (){

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
	
	public LOMESOptionsDataControl getCopyrightandotherrestrictions() {
		return new LOMESOptionsDataControl (){

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
	
	public LOMESTextDataControl getDescriptionController (){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getDescription().getValue(0);
			}

			public void setText( String text ) {
				data.setDescription( new LangString(text) );
			}
			
		};
	}
	
	public LOMESTextDataControl getAccessDescriptionController (){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getAccessDescription().getValue(0);
			}

			public void setText( String text ) {
				data.setAccessDescription(new LangString(text) );
			}
			
		};
	}
	
	public LOMESOptionsDataControl getAccesType() {
		return new LOMESOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getAccessType().getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "LOMES.Rights.AccesType"+i );
				}
				return options;
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

	public LOMESRights getData() {
		return data;
	}

	public void setData(LOMESRights data) {
		this.data = data;
	}
	
	
}
