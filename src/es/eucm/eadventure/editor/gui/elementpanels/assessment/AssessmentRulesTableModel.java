package es.eucm.eadventure.editor.gui.elementpanels.assessment;

import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;

/**
 * Table model to display the scenes information.
 */
public class AssessmentRulesTableModel extends AbstractTableModel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Array of data to display.
	 */
	private AssessmentProfileDataControl dataControl;

	/**
	 * Constructor.
	 * 
	 * @param assRulesInfo
	 *            Container array of the information of the scenes
	 */
	public AssessmentRulesTableModel(AssessmentProfileDataControl dataControl) {
		this.dataControl = dataControl;
	}

	public int getColumnCount() {
		return 3;
	}

	public int getRowCount() {
		return dataControl.getAssessmentRules().size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		String columnName = "";
		if (columnIndex == 0)
			columnName = TextConstants.getText("AssessmentRulesList.ColumnHeader0");
		else if (columnIndex == 1)
			columnName = TextConstants.getText("AssessmentRulesList.ColumnHeader1");
		else if (columnIndex == 2)
			columnName = TextConstants.getText("AssessmentRulesList.ColumnHeader2");
		return columnName;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0)
			return dataControl.getAssessmentRules().get(rowIndex).getId();
		else if (columnIndex == 1)
			return getImportance(dataControl.getAssessmentRules().get(rowIndex).getImportance());
		else if (columnIndex == 2)
			return dataControl.getAssessmentRules().get(rowIndex).getConditions();
		return null;
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			dataControl.getAssessmentRules().get(rowIndex).setId((String) value);
		} if (columnIndex == 1) {
			dataControl.getAssessmentRules().get(rowIndex).setImportance(getImportance((String) value));
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}
	
	private String getImportance(int importance) {
		switch(importance) {
		case 0:
			return TextConstants.getText("AssessmentRule.Importance.VeryLow");
		case 1:
			return TextConstants.getText("AssessmentRule.Importance.Low");
		case 2:
			return TextConstants.getText("AssessmentRule.Importance.Normal");
		case 3:
			return TextConstants.getText("AssessmentRule.Importance.High");
		case 4:
			return TextConstants.getText("AssessmentRule.Importance.VeryHigh");
		}
		return "";
	}
	
	private int getImportance(String importance) {
		for (int i = 0; i <= 4; i++) {
			if (importance.equals(getImportance(i)))
				return i;
		}
		return 0;
	}
		
}
