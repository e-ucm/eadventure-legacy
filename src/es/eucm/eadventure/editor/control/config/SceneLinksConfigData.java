package es.eucm.eadventure.editor.control.config;

import es.eucm.eadventure.editor.control.Controller;

public class SceneLinksConfigData implements ProjectConfigDataConsumer{

	private static Controller controller = Controller.getInstance();
	
	private static String getXKey ( String sceneId ){
		int chapter = controller.getSelectedChapter( );
		return "Chapter"+chapter+"."+sceneId+".X";
	}
	
	private static String getYKey ( String sceneId ){
		int chapter = controller.getSelectedChapter( );
		return "Chapter"+chapter+"."+sceneId+".Y";
	}
	
	private static String getVisibleKey ( String sceneId ) {
		int chapter = controller.getSelectedChapter();
		return "Chapter"+chapter+"."+sceneId+".Visible";
	}
	
	public static boolean isSceneConfig ( String sceneId ){
		String keyX = getXKey ( sceneId );
		String keyY = getYKey ( sceneId );
		String keyVisible = getVisibleKey ( sceneId );
		// If both X and Y are in the config file 
		if (ProjectConfigData.existsKey( keyX ) && ProjectConfigData.existsKey( keyY ) && ProjectConfigData.existsKey(keyVisible)){
			return true;
		}
		return false;
	}

	public static int getSceneX ( String sceneId ){
		int X=Integer.MIN_VALUE;
		
		if (isSceneConfig (sceneId)){
			try {
				String keyX = getXKey ( sceneId );
				X = Integer.parseInt( ProjectConfigData.getProperty( keyX ) );
			}catch (Exception e){
				X = Integer.MIN_VALUE;
			}
		}
		return X;
	}

	
	public static int getSceneY ( String sceneId ){
		int Y=Integer.MIN_VALUE;
		
		if (isSceneConfig (sceneId)){
			try {
				String keyY = getYKey ( sceneId );
				Y = Integer.parseInt( ProjectConfigData.getProperty( keyY ) );
			}catch (Exception e){
				Y= Integer.MIN_VALUE;
			}
		}
		return Y;
	}
	
	public static boolean getSceneVisible ( String sceneId ) {
		boolean visible = true;
		
		if (isSceneConfig( sceneId)) {
			try {
				String keyVisible = getVisibleKey( sceneId );
				visible = !ProjectConfigData.getProperty(keyVisible).equals("false");
			} catch (Exception e) {
			}
		}
		return visible;
	}
	
	public static void setSceneX ( String sceneId, int X){
		if ( X!=Integer.MIN_VALUE ){
			String keyX = getXKey ( sceneId );
			String xValue = Integer.toString( X );
			ProjectConfigData.setProperty ( keyX, xValue);
		}
	}
	
	public static void setSceneY ( String sceneId , int Y){
		if ( Y!=Integer.MIN_VALUE ){ 
			String keyY = getYKey ( sceneId );
			String yValue = Integer.toString( Y );
			ProjectConfigData.setProperty ( keyY, yValue);
		}
	}
		
	public static void setSceneVisible (String sceneId, boolean visible) {
		String keyVisible = getVisibleKey (sceneId);
		String visibleValue = (visible ? "true" : "false");
		ProjectConfigData.setProperty( keyVisible, visibleValue);
	}
	
	public void updateData( ) {	}

}
