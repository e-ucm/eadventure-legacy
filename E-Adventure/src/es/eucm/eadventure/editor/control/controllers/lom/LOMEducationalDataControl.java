package es.eucm.eadventure.editor.control.controllers.lom;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.lom.LOMEducational;
import es.eucm.eadventure.editor.data.lom.LangString;

public class LOMEducationalDataControl {

	public static final String[] AVAILABLE_LANGS= new String[]{"en", "es"};
	
	private LOMEducational data;
	
	public LOMEducationalDataControl (LOMEducational data){
		this.data = data;
	}
	
	/******************* OPTIONS DATA CONTROLLERS *******************************************/
	public LOMOptionsDataControl getInteractivityTypeController() {
		return new LOMOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getInteractivityType( ).getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "LOM.Educational.InteractivityType"+i );
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

	public LOMOptionsDataControl getLearningResourceTypeController() {
		return new LOMOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[5];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "LOM.Educational.LearningResourceType"+i );
				}
				return options;
			}

			public void setOption( int option ) {
				
				data.getLearningResourceType( ).setValueIndex( mapIndex(option) );
			}

			public int getSelectedOption( ) {
				return mapIndexInverse(data.getLearningResourceType( ).getValueIndex( ));
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
	
	public LOMOptionsDataControl getInteractivityLevelController() {
		return new LOMOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getInteractivityLevel( ).getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "LOM.Educational.InteractivityLevel"+i );
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

	public LOMOptionsDataControl getSemanticDensityController() {
		return new LOMOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getSemanticDensity( ).getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "LOM.Educational.SemanticDensity"+i );
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
	
	public LOMOptionsDataControl getIntendedEndUserRoleController() {
		return new LOMOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getIntendedEndUserRole( ).getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "LOM.Educational.IntendedEndUserRole"+i );
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
	
	public LOMOptionsDataControl getContextController() {
		return new LOMOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getContext( ).getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "LOM.Educational.Context"+i );
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

	public LOMOptionsDataControl getDifficultyController() {
		return new LOMOptionsDataControl (){

			public String[] getOptions( ) {
				String[] options = new String[data.getDifficulty( ).getValues( ).length];
				for (int i=0; i<options.length; i++){
					options[i]=TextConstants.getText( "LOM.Educational.Difficulty"+i );
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
	
	/******************* TEXT CONTROLLERS *******************************************/
	public LOMTextDataControl getDescriptionController (){
		return new LOMTextDataControl (){

			public String getText( ) {
				return data.getDescription( ).getValue( 0 );
			}

			public void setText( String text ) {
				data.setDescription( new LangString(text) );
			}
			
		};
	}
	
	public LOMTextDataControl getTypicalAgeRangeController (){
		return new LOMTextDataControl (){

			public String getText( ) {
				return data.getTypicalAgeRange( ).getValue( 0 );
			}

			public void setText( String text ) {
				data.setTypicalAgeRange( new LangString(text) );
			}
			
		};
	}

	public LOMTypicalLearningTimeDataControl getTypicalLearningTime() {
		return new LOMTypicalLearningTimeDataControl(data);
	}
	
	/******************* GETTER & SETTER *******************************************/
	/**
	 * @return the data
	 */
	public LOMEducational getData( ) {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData( LOMEducational data ) {
		this.data = data;
	}

	
}
