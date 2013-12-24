package com.jerome.test;

public class FieldObject {
	private String fieldName;
	private String fieldValue;
	private String fieldGroup;
	private int fieldIndex;
	public int getFieldIndex() {
		return fieldIndex;
	}
	public void setFieldIndex(int fieldIndex) {
		this.fieldIndex = fieldIndex;
	}
	public String getFieldGroup() {
		return fieldGroup;
	}
	public void setFieldGroup(String fieldGroup) {
		this.fieldGroup = fieldGroup;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String toString()
	{
		return "Name:" + this.fieldName + " value: " + this.fieldValue + " group:" + this.fieldGroup + " Index: " + this.fieldIndex + "\n"; 
	}
}
