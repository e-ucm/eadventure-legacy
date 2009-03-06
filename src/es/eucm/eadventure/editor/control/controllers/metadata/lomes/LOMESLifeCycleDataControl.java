package es.eucm.eadventure.editor.control.controllers.metadata.lomes;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.ims.IMSLifeCycle;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESLifeCycle;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMESLifeCycleDataControl {

	private LOMESLifeCycle data;
	
	public LOMESLifeCycleDataControl (LOMESLifeCycle data){
		this.data = data;
	}
	
	public LOMESOptionsDataControl getRoleController() {
		return new LOMESOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getRole().getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "LOMES.LifeCycle.Role"+i );
				}
				return options;
			}

			public void setOption( int option ) {
				data.getRole().setValueIndex( option );
			}

			public int getSelectedOption( ) {
				return data.getRole().getValueIndex( );
			}
			
		};
	}
	
	
	public LOMESTextDataControl getEntityController (){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getEntity();
			}

			public void setText( String text ) {
				data.setEntity(text) ;
			}
			
		};
	}
	
	public LOMESTextDataControl getDescriptionController (){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getDescription();
			}

			public void setText( String text ) {
				data.setDescription(text) ;
			}
			
		};
	}
	
	public LOMESTextDataControl getVersionController (){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getVersion( ).getValue( 0 );
			}

			public void setText( String text ) {
				data.setVersion( new LangString(text) );
			}
			
		};
	}
	
	public LOMESOptionsDataControl getStatusController() {
		return new LOMESOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getStatus().getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "IMS.LifeCycle.Status"+i );
				}
				return options;
			}

			public void setOption( int option ) {
				data.getStatus().setValueIndex( option );
			}

			public int getSelectedOption( ) {
				return data.getStatus().getValueIndex( );
			}
			
		};
	}
	

	/**
	 * @return the data
	 */
	public LOMESLifeCycle getData( ) {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData( LOMESLifeCycle data ) {
		this.data = data;
	}

}
