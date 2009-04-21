package es.eucm.eadventure.editor.control.config;

public class ResizeableListConfigData implements ProjectConfigDataConsumer{

	public static String getSizeKey( String name ) {
		return name + ".Size";
	}
	
	public static int getSize( String name ) {
		int size = 1;
		try {
			String key = getSizeKey( name );
			size = Integer.parseInt( ProjectConfigData.getProperty( key ));
		} catch (Exception e) {
			size = 1;
		}
		return size;
	}

	public static void setSize ( String name, int size){
		String key = getSizeKey ( name );
		String xValue = Integer.toString( size );
		ProjectConfigData.setProperty ( key, xValue);
	}

	public void updateData( ) {	}

}
