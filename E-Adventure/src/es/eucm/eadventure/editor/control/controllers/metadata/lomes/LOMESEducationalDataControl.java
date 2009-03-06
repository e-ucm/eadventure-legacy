package es.eucm.eadventure.editor.control.controllers.metadata.lomes;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.ims.IMSEducational;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESEducational;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMESEducationalDataControl {

	public static final String[] AVAILABLE_LANGS= new String[]{"en", "es"};
	
	private LOMESEducational data;
	
	public LOMESEducationalDataControl (LOMESEducational data){
		this.data = data;
	}
	
	/******************* OPTIONS DATA CONTROLLERS *******************************************/
	public LOMESOptionsDataControl getInteractivityTypeController() {
		return new LOMESOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getInteractivityType( ).getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "IMS.Educational.InteractivityType"+i );
				}
				return options;
			}

			public void setOption( int option ) {
				data.getInteractivityType( ).setValueIndex( option );
			}

			public int getSelectedOption( ) {
				return data.getInteractivityType( ).getValueIndex( );
			}
			
		};
	}

	public LOMESOptionsDataControl getLearningResourceTypeController() {
		return new LOMESOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[14];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "IMS.Educational.LearningResourceType"+i );
				}
				return options;
			}

			public void setOption( int option ) {
				
				data.getLearningResourceType( ).setValueIndex( /*mapIndex(*/option/*)*/ );
			}

			public int getSelectedOption( ) {
				return /*mapIndexInverse(*/data.getLearningResourceType( ).getValueIndex( );//);
			}
			
			private int mapIndex( int index ){
				switch(index){
					case 0: return 0;
					case 1: return 1;
					case 2: return 10;
					case 3: return 13;
					case 4: return 14;
					default: return 0;
				}
			}
			
			private int mapIndexInverse( int invIndex){
				switch(invIndex){
					case 0: return 0;
					case 1: return 1;
					case 10: return 2;
					case 13: return 3;
					case 14: return 4;
					default: return 0;
				}
				
			}
			
		};
	}
	
	public LOMESOptionsDataControl getInteractivityLevelController() {
		return new LOMESOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getInteractivityLevel( ).getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "IMS.Educational.InteractivityLevel"+i );
				}
				return options;
			}

			public void setOption( int option ) {
				data.getInteractivityLevel( ).setValueIndex( option );
			}

			public int getSelectedOption( ) {
				return data.getInteractivityLevel( ).getValueIndex( );
			}
			
		};
	}

	public LOMESOptionsDataControl getSemanticDensityController() {
		return new LOMESOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getSemanticDensity( ).getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "IMS.Educational.SemanticDensity"+i );
				}
				return options;
			}

			public void setOption( int option ) {
				data.getSemanticDensity( ).setValueIndex( option );
			}

			public int getSelectedOption( ) {
				return data.getSemanticDensity( ).getValueIndex( );
			}
			
		};
	}
	
	public LOMESOptionsDataControl getIntendedEndUserRoleController() {
		return new LOMESOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getIntendedEndUserRole( ).getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "LOMES.Educational.IntendedEndUserRole"+i );
				}
				return options;
			}

			public void setOption( int option ) {
				data.getIntendedEndUserRole( ).setValueIndex( option );
			}

			public int getSelectedOption( ) {
				return data.getIntendedEndUserRole( ).getValueIndex( );
			}
			
		};
	}
	
	public LOMESOptionsDataControl getContextController() {
		return new LOMESOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getContext( ).getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "LOMES.Educational.Context"+i );
				}
				return options;
			}

			public void setOption( int option ) {
				data.getContext( ).setValueIndex( option );
			}

			public int getSelectedOption( ) {
				return data.getContext( ).getValueIndex( );
			}
			
		};
	}

	public LOMESOptionsDataControl getDifficultyController() {
		return new LOMESOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getDifficulty( ).getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "IMS.Educational.Difficulty"+i );
				}
				return options;
			}

			public void setOption( int option ) {
				data.getDifficulty( ).setValueIndex( option );
			}

			public int getSelectedOption( ) {
				return data.getDifficulty( ).getValueIndex( );
			}
			
		};
	}
	public LOMESOptionsDataControl getCognitiveProcessController() {
		return new LOMESOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getCognitiveProcess().getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "LOMES.Educational.CognitiveProcess"+i );
				}
				return options;
			}

			public void setOption( int option ) {
				data.getCognitiveProcess().setValueIndex( option );
			}

			public int getSelectedOption( ) {
				return data.getCognitiveProcess().getValueIndex( );
			}
			
		};
	}
	
	
	/******************* TEXT CONTROLLERS *******************************************/
	public LOMESTextDataControl getDescriptionController (){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getDescription( ).getValue( 0 );
			}

			public void setText( String text ) {
				data.setDescription( new LangString(text) );
			}
			
		};
	}
	
	public LOMESTextDataControl getTypicalAgeRangeController (){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getTypicalAgeRange( ).getValue( 0 );
			}

			public void setText( String text ) {
				data.setTypicalAgeRange( new LangString(text) );
			}
			
		};
	}

	public LOMESTypicalLearningTimeDataControl getTypicalLearningTime() {
		return new LOMESTypicalLearningTimeDataControl(data);
	}
	
	/******************* GETTER & SETTER *******************************************/
	/**
	 * @return the data
	 */
	public LOMESEducational getData( ) {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData( LOMESEducational data ) {
		this.data = data;
	}

	
}
