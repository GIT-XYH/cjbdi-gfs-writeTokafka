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
	public String database = "";
	public String fbId = "";
	public String host = "";
	public String schema = "";
	public String t_c_baah = "";
	public String t_c_jbfymc = "";
	public String t_c_stm = "";
	public String t_d_sarq = "";
	public String t_n_jbfy = "";
	public String ws_c_mc = "";
	public String ws_c_nr = "";

//	public Map<String, String> toHBaseInsertHashtable() {
//		Map<String, String> inLayer = new Hashtable<String, String>();
//		inLayer.put("db", this.database);
//		inLayer.put("schema", this.schema);
//		inLayer.put("c_stm", this.t_c_stm);
//		inLayer.put("c_mc", this.ws_c_mc);
//		inLayer.put("c_nr", this.ws_c_nr);
//		return inLayer;
//	}
}
