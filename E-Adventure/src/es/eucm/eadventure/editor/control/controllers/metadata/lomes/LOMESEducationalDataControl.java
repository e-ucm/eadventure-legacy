package es.eucm.eadventure.editor.control.controllers.metadata.lomes;

import java.util.ArrayList;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.ims.IMSEducational;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESEducational;
import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

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

	public ArrayList<Vocabulary> getLearningResourceType(){
	    return data.getLearningResourceType();
	}
	
	public String[] getLearningResourceTypeOptions(){
	    String[] options = new String[data.getLearningResourceType( ).get(0).getValues( ).length];
		for (int i=0; i<options.length; i++){
			options[i]=TextConstants.getText( "LOMES.Educational.LearningResourceType"+i );
		}
		return options;
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
	
	
	public ArrayList<Vocabulary> getIntendedEndUserRole(){
	    return data.getIntendedEndUserRole();
	}
	
	public String[] getIntendedEndUserRoleOptions(){
	    String[] options = new String[data.getIntendedEndUserRole( ).get(0).getValues( ).length];
		for (int i=0; i<options.length; i++){
			options[i]=TextConstants.getText( "LOMES.Educational.IntendedEndUserRole"+i );
		}
		return options;
	}
	
	/*public LOMESOptionsDataControl getIntendedEndUserRoleController() {
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
	}*/
	
	public ArrayList<Vocabulary> getContext(){
	    return data.getContext();
	}
	
	public String[] getContextOptions(){
	    String[] options = new String[data.getContext( ).get(0).getValues( ).length];
		for (int i=0; i<options.length; i++){
			options[i]=TextConstants.getText( "LOMES.Educational.Context"+i );
		}
		return options;
	}
	
	/*public LOMESOptionsDataControl getContextController() {
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
	}*/

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
	
	
	public ArrayList<Vocabulary> getCognitiveProcess(){
	    return data.getCognitiveProcess();
	}
	
	public String[] getCognitiveProcessOptions(){
	    String[] options = new String[data.getCognitiveProcess( ).get(0).getValues( ).length];
		for (int i=0; i<options.length; i++){
			options[i]=TextConstants.getText( "LOMES.Educational.CognitiveProcess"+i );
		}
		return options;
	}
	
	/*public LOMESOptionsDataControl getCognitiveProcessController() {
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
	}*/
	
	
	/******************* TEXT CONTROLLERS *******************************************/
	/*public LOMESTextDataControl getDescriptionController (){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getDescription( ).getValue( 0 );
			}

			public void setText( String text ) {
				data.setDescription( new LangString(text) );
			}
			
		};
	}*/
	public ArrayList<LangString> getDescription(){
	    return data.getDescription();
	}
	
	/*public LOMESTextDataControl getTypicalAgeRangeController (){
		return new LOMESTextDataControl (){

			public String getText( ) {
				return data.getTypicalAgeRange( ).getValue( 0 );
			}

			public void setText( String text ) {
				data.setTypicalAgeRange( new LangString(text) );
			}
			
		};
	}*/
	public ArrayList<LangString> getTypicalAgeRange(){
	    return data.getTypicalAgeRange();
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
