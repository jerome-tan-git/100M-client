package com.jerome.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class ParseObjects {
	private String type;
	private HashMap<String, Object> content = new HashMap<String, Object>();
	private List<FieldObject> tmpContainer = new ArrayList<FieldObject>();

	public ParseObjects(String _type) {
		this.type = _type;
	}

	public void addField(FieldObject _obj) {
		tmpContainer.add(_obj);

	}

	public String getJSON() {
		this.content.put("type", this.type);
		Collections.sort(this.tmpContainer, new SortObj());
		System.out.println(this.tmpContainer);

		for (FieldObject o : this.tmpContainer) {
			if (o.getFieldGroup() != null
					&& !o.getFieldGroup().trim().equals("")) {
				List<HashMap<String, String>> l = new ArrayList<HashMap<String, String>>();
				String group = o.getFieldGroup().trim().toLowerCase();
				if (this.content.containsKey(group)) {
					l = (List<HashMap<String, String>>) this.content.get(group);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(o.getFieldName(), o.getFieldValue());
					l.add(map);
				} else {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(o.getFieldName(), o.getFieldValue());
					l.add(map);
				}
				this.content.put(group, l);

			} else {
				this.content.put(o.getFieldName(), o.getFieldValue());
			}
		}
		return JSON.toJSONString(this.content);
	}

	public static void main(String[] args) {
		ParseObjects po = new ParseObjects("edit");

		FieldObject fo0 = new FieldObject();
		fo0.setFieldName("fo1_f");
		fo0.setFieldValue("f");

		FieldObject fo1 = new FieldObject();
		fo1.setFieldName("fo1_f");
		fo1.setFieldValue("f_1");
		fo1.setFieldGroup("g1");
		fo1.setFieldIndex(1);

		FieldObject fo3 = new FieldObject();
		fo3.setFieldName("fo1_f");
		fo3.setFieldValue("f_1");
		fo3.setFieldGroup("g2");
		fo3.setFieldIndex(1);

		FieldObject fo2 = new FieldObject();
		fo2.setFieldName("fo1_f");
		fo2.setFieldValue("f_3");
		fo2.setFieldGroup("g1");
		fo2.setFieldIndex(3);

		FieldObject fo4 = new FieldObject();
		fo4.setFieldName("fo1_f");
		fo4.setFieldValue("f_2");
		fo4.setFieldGroup("g2");
		fo4.setFieldIndex(2);

		FieldObject fo5 = new FieldObject();
		fo5.setFieldName("fo1_f");
		fo5.setFieldValue("fxxxxxxxxxxxx");

		po.addField(fo4);
		po.addField(fo2);
		po.addField(fo3);
		po.addField(fo1);
		po.addField(fo0);
		po.addField(fo5);
		System.out.println(po.getJSON());
		// HashMap<String, Object> a = new HashMap<String, Object>();
		// a.put("aaaa", "bbbbb");
		// HashMap<String, Object> x = (HashMap<String, Object>) a.clone();
		// List<Object> a1 = new ArrayList<Object>();
		// a1.add(x);
		//
		// a1.add(x.clone());
		//
		// a.put("vvc", a1);

		// FieldObject fo = new FieldObject();
		// fo.setFieldName("fieldName_1");
		// fo.setFieldValue("fieldValue_2");
		// System.out.println(JSON.toJSONString(a));
	}

	class SortObj implements Comparator {
		public int compare(Object obj1, Object obj2) {
			if (obj1 instanceof FieldObject && obj2 instanceof FieldObject) {

				int index1 = ((FieldObject) obj1).getFieldIndex();
				int index2 = ((FieldObject) obj2).getFieldIndex();

				return index1 - index2;

			}
			return -1;

		}
	}
}
