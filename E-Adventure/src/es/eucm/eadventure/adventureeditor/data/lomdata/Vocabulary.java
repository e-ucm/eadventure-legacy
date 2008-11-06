package es.eucm.eadventure.adventureeditor.data.lomdata;

public class Vocabulary {

	public static final String[] ED_INTERACTIVITY_TYPE_5_1 = new String[]{"active", "explosive", "mixed"};
	
	public static final String[] ED_LEARNING_RESOURCE_TYPE_5_2 = new String[]{"exercise", "simulation", "questionnaire", "diagram", "figure", "graph", "index", "slide", "table", "narrative text", "exam", "experiment", "problem statement", "self assessment", "lecture"};
	
	public static final String[] ED_INTERACTIVITY_LEVEL_5_3 = new String[]{"very low", "low", "medium", "high", "very high"};
	
	public static final String[] ED_SEMANTIC_DENSITY_5_4 = new String[]{"very low", "low", "medium", "high", "very high"};
	
	public static final String[] ED_INTENDED_END_USER_ROLE_5_5 = new String[]{"teacher", "author", "learner", "manager"};
	
	public static final String[] ED_CONTEXT_5_6 = new String[]{"school", "higher education", "training", "other"};
	
	public static final String[] ED_DIFFICULTY_5_8 = new String[]{"very easy", "easy", "medium", "difficult", "very difficult"};

	
	
	private static final String DEFAULT_SOURCE = "LOMv1.0"; 
	
	
	
	/************** ATTRIBUTES ******************/
	private String[] values;
	
	private int currentValue;
	
	private String source;
	
	
	/************** CONSTRUCTORS ******************/
	public Vocabulary(String[]values){
		this.values = values;
		this.currentValue = 0;
		this.source = DEFAULT_SOURCE;
	}
	
	public Vocabulary(String[]values, int value){
		this.values = values;
		this.currentValue = value;
		this.source = DEFAULT_SOURCE;
	}
	
	public Vocabulary(String[]values, String value){
		this(values, DEFAULT_SOURCE,value);
	}

	public Vocabulary(String[]values, String source, String value){
		this.values = values;
		
		for (int i=0; i<values.length; i++){
			if (values[i].equals( value )){
				currentValue = i;
			}
		}
		this.source = source;
	}

	
	/************** GETTERS ******************/
	public int getValueIndex(){
		return currentValue;
	}
	
	public String[] getValues(){
		return values;
	}
	
	public String getValue(){
		return values[currentValue];
	}
	
	public String getSource(){
		return source;
	}
	
	/************** SETTERS ******************/
	public void setValueIndex(int index){
		currentValue = index;
	}
	
	public void setValues(String[] values){
		this.values=values;
	}
	
	public void setValue(String value){
		for (int i=0; i<values.length; i++)
			if(values[i].equals( value ))
				currentValue=i;
	}
	
	public void setSource(String source){
		this.source=source;
	}
	
	
}
