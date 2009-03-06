package es.eucm.eadventure.editor.control.controllers.metadata.ims;

import es.eucm.eadventure.editor.data.meta.ims.IMSTechnical;

public class IMSTechnicalDataControl {

	private IMSTechnical data;
	
	public IMSTechnicalDataControl (IMSTechnical data){
		this.data = data;
	}
	
	
	public IMSTextDataControl getLocation(){
		return new IMSTextDataControl (){

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
	
	public IMSTextDataControl getFormatController (){
		return new IMSTextDataControl (){

			public String getText( ) {
				return data.getFormat();
			}

			public void setText( String text ) {
				data.setFormat( text );
			}
			
		};
	}
	
	public IMSTextDataControl getMaximumVersionController (){
		return new IMSTextDataControl (){

			public String getText( ) {
				return data.getMaximumVersion( );
			}

			public void setText( String text ) {
				data.setMaximumVersion( text );
			}
			
		};
	}
	
	public IMSTextDataControl getMinimumVersionController (){
		return new IMSTextDataControl (){

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
	public IMSTechnical getData( ) {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData( IMSTechnical data ) {
		this.data = data;
	}

}
