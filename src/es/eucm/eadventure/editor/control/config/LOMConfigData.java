package es.eucm.eadventure.editor.control.config;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMEducationalDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMGeneralDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMLifeCycleDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMTechnicalDataControl;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMConfigData implements ProjectConfigDataConsumer{

	@Override
	public void updateData() {	}
	
	private static Controller controller = Controller.getInstance();
	
	public static void storeData(String group,String field, String value ){
		ProjectConfigData.setProperty (getKey(group,field),value);
	}
	
	private static String getKey(String groupName,String fieldName){
		int chapter = controller.getSelectedChapter();
		return "Chapter"+chapter+"."+groupName+"."+fieldName;
	}
	
	public static boolean isStored(String group,String field){
		
		return ProjectConfigData.existsKey(getKey(group,field));	
		
	}
	
	/**
	 * Make the correct changes in LOMData 
	 */
	public static void loadData(){
		
		// general
		if (LOMConfigData.isStored(LOMGeneralDataControl.GROUP, "title")){
			controller.getLOMDataControl().getGeneral().getData().setTitle(new LangString(getProperty(LOMGeneralDataControl.GROUP, "title")));
		}
		if (LOMConfigData.isStored(LOMGeneralDataControl.GROUP, "language")){
			controller.getLOMDataControl().getGeneral().getData().setLanguage(getProperty(LOMGeneralDataControl.GROUP, "language"));
			controller.updateLOMLanguage();
		}
		if (LOMConfigData.isStored(LOMGeneralDataControl.GROUP, "description")){
			controller.getLOMDataControl().getGeneral().getData().setDescription(new LangString(getProperty(LOMGeneralDataControl.GROUP, "description")));
		}
		if (LOMConfigData.isStored(LOMGeneralDataControl.GROUP, "keyword")){
			controller.getLOMDataControl().getGeneral().getData().setKeyword(new LangString(getProperty(LOMGeneralDataControl.GROUP, "keyword")));
		}
		
		// life cycle
		if (LOMConfigData.isStored(LOMLifeCycleDataControl.GROUP, "version")){
			controller.getLOMDataControl().getLifeCycle().getData().setVersion(new LangString(getProperty(LOMLifeCycleDataControl.GROUP, "version")));
		}
		
		// technical
		if (LOMConfigData.isStored(LOMTechnicalDataControl.GROUP, "minimumVersion")){
			controller.getLOMDataControl().getTechnical().getData().setMinimumVersion(getProperty(LOMTechnicalDataControl.GROUP, "minimumVersion"));
		}
		if (LOMConfigData.isStored(LOMTechnicalDataControl.GROUP, "maximumVersion")){
			controller.getLOMDataControl().getTechnical().getData().setMinimumVersion(getProperty(LOMTechnicalDataControl.GROUP, "maximumVersion"));
		}
		
		// educational
		if (isStored(LOMEducationalDataControl.GROUP, "intendedEndUserRole")){
			controller.getLOMDataControl().getEducational().getData().setIntendedEndUserRole(Integer.parseInt(getProperty(LOMEducationalDataControl.GROUP, "intendedEndUserRole")));
		}
		if (isStored(LOMEducationalDataControl.GROUP, "semanticDensity")){
			controller.getLOMDataControl().getEducational().getData().setSemanticDensity(Integer.parseInt(getProperty(LOMEducationalDataControl.GROUP, "semanticDensity")));
		}
		if (isStored(LOMEducationalDataControl.GROUP, "learningResourcesType")){
			controller.getLOMDataControl().getEducational().getData().setLearningResourceType(Integer.parseInt(getProperty(LOMEducationalDataControl.GROUP, "learningResourcesType")));
			
		}
		if (isStored(LOMEducationalDataControl.GROUP, "context")){
			controller.getLOMDataControl().getEducational().getData().setContext(Integer.parseInt(getProperty(LOMEducationalDataControl.GROUP, "context")));
		}
		if (isStored(LOMEducationalDataControl.GROUP, "difficulty")){
			controller.getLOMDataControl().getEducational().getData().setDifficulty(Integer.parseInt(getProperty(LOMEducationalDataControl.GROUP, "difficulty")));
		}
		if (isStored(LOMEducationalDataControl.GROUP, "interactivityLevel")){
			controller.getLOMDataControl().getEducational().getData().setInteractivityLevel(Integer.parseInt(getProperty(LOMEducationalDataControl.GROUP, "interactivityLevel")));
		}
		if (isStored(LOMEducationalDataControl.GROUP, "interactivityType")){
			controller.getLOMDataControl().getEducational().getData().setInteractivityType(Integer.parseInt(getProperty(LOMEducationalDataControl.GROUP, "interactivityType")));
		}
		
		if (isStored(LOMEducationalDataControl.GROUP, "description")){
			controller.getLOMDataControl().getEducational().getData().setDescription(new LangString(getProperty(LOMEducationalDataControl.GROUP, "description")));
		}
		if (isStored(LOMEducationalDataControl.GROUP, "typicalAgeRange")){
			controller.getLOMDataControl().getEducational().getData().setTypicalAgeRange(new LangString(getProperty(LOMEducationalDataControl.GROUP, "typicalAgeRange")));
		}
		if (isStored(LOMEducationalDataControl.GROUP, "typicalLearningTime")){
			controller.getLOMDataControl().getEducational().getData().setTypicalLearningTime(getProperty(LOMEducationalDataControl.GROUP, "typicalLearningTime"));
			
		}
	}
	
	public static String getProperty(String group,String field){
		return ProjectConfigData.getProperty(getKey(group,field));
	}

}
