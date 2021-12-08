package com.cjbdi.hbinsert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Base64;
import java.util.Hashtable;
import java.util.Map;
@Data
@ToString
@AllArgsConstructor
public class Ws {
	public String db = "";
	public String schema = "";
	public String c_rowkey = "";
	public String c_stm = "";
	public String c_wsText = "";
	public String c_ajbs = "";
	public String c_mc = "";
	public String c_nr = "";
	public String c_file = "";
	
	public Map<String, String> toHBaseInsertHashtable() {
		Map<String, String> inLayer = new Hashtable<String, String>();
		inLayer.put("db", this.db);
		inLayer.put("schema", this.schema);
		inLayer.put("c_stm", this.c_stm);
		inLayer.put("c_ajbs", this.c_ajbs);
		inLayer.put("c_mc", this.c_mc);
		inLayer.put("c_nr", this.c_nr);
		inLayer.put("c_wsText", this.c_wsText);
		return inLayer;
	}
}
