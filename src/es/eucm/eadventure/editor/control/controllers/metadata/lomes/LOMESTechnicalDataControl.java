package es.eucm.eadventure.editor.control.controllers.metadata.lomes;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.ims.IMSTechnical;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESTechnical;

public class LOMESTechnicalDataControl {

	private LOMESTechnical data;
	
	public LOMESTechnicalDataControl (LOMESTechnical data){
		this.data = data;
	}
	
	
	public LOMESOptionsDataControl getTypeController() {
		return new LOMESOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getType().getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "LOMES.Technical.Type"+i );
				}
				return options;
			}

			public void setOption( int option ) {
				data.getType().setValueIndex( option );
			}

			public int getSelectedOption( ) {
				return data.getType().getValueIndex( );
			}
			
		};
	}
	
	public LOMESOptionsDataControl getNameController() {
		return new LOMESOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getName().getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "LOMES.Technical.Name"+i );
				}
				return options;
			}

			public void setOption( int option ) {
				data.getName().setValueIndex( option );
			}

			public int getSelectedOption( ) {
				return data.getName().getValueIndex( );
			}
			
		};
	}
	
	/*public LOMESTextDataControl getLocation(){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getLocation();
			}

			public void setText( String text ) {
				boolean uri;
				if (text.startsWith("0"))
					uri = true;
				else 
					uri = false;
				text = text.substring(1);
				data.setLocation(text, uri );
			}
			
		};
	}
	
	public LOMESTextDataControl getFormatController (){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getFormat();
			}

			public void setText( String text ) {
				data.setFormat( text );
			}
			
		};
	}*/
	
	public LOMESTextDataControl getMaximumVersionController (){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getMaximumVersion( );
			}

			public void setText( String text ) {
				data.setMaximumVersion( text );
			}
			
		};
	}
	
	public LOMESTextDataControl getMinimumVersionController (){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getMinimumVersion( );
			}

			public void setText( String text ) {
				data.setMinimumVersion( text );
			}
			
		};
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
