package com.cjbdi.hbinsert;

import java.io.File;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Logger;

import com.cjbdi.doc.detector.DocDetector;
import com.cjbdi.doc.detector.DocType;
import com.cjbdi.fp.docs.Doc2Text;
import com.cjbdi.gfs.data.DataBlockFile;
import com.cjbdi.gfs.data.DataStore;
import com.cjbdi.gfs.data.DataStoreManager;
import com.cjbdi.utils.CharsetUtil;

class DsmGroup {
	public DataStoreManager dsm = null;
	public Map<String, DataStore> dsList = null;

	public DsmGroup(DataStoreManager dsmName, Map<String, DataStore> dsListName) {
		this.dsm = dsmName;
		this.dsList = dsListName;
	}
}

public class UnzipUtil {
	private static Logger logger = Logger.getLogger(UnzipUtil.class.getName());

	/**
	 * trave the zipped file
	 * 
	 * @param dataPath: input path
	 * @param start: a subdir in input path, starting read data from this subdir
	 * @param end: a subdir in input path, ending read data from this subdir
	 * @return dsmPath
	 */
	public File[] fileWalker(File dataPath, int start, int end) {
		File[] levelOneDir = dataPath.listFiles();
		int len_dir = levelOneDir.length;
		int start_dir = start;
		int end_dir = end;
		File[] sub_path = Arrays.copyOfRange(levelOneDir, start_dir, end_dir);
		return sub_path;
	}

	/**
	 * open the DataStoreManager object
	 * 
	 * @param dsmPath: the DataStoreManager path
	 * @return a DsmGroup object
	 */
	public DsmGroup openDataStoreManager(File dsmPath) {
		DataStoreManager dsm = new DataStoreManager(dsmPath, true);
		boolean isOpen = dsm.open();
		if (isOpen) {
			Map<String, DataStore> dsList = dsm.dataStores();
			return new DsmGroup(dsm, dsList);
		}
		logger.warning(dsmPath + "DataStoreManager open failed!");
		return null;
	}

	/**
	 * open the DataStore object
	 * 
	 * @param key: name of case type
	 * @param dsm: the DataStoreManager object
	 * @return A DataStore object
	 */
	public DataStore openDataStore(String key, DataStoreManager dsm) {
		DataStore ds = dsm.getDataStore(key);
		boolean isOpen = ds.open();
		if (isOpen) {
			return ds;
		}
		logger.warning(ds + "DataStore open failed!");
		return null;
	}

	/**
	 * unzip the DataBlockFile
	 * 
	 * @param fileitor: a DataBlockFile.Iterator object
	 * @param dsmPath: the DataStoreManager dfpath
	 * @param dsName: the DataStore name / name of case type
	 * @return A Ws object
	 */
	public WsWithBase64File unzip(DataBlockFile.Iterator fileitor, String dsmPath, String dsName) {
		String base64File = "";
		String database = "";
		String fbId = "";
		String host = "";
		String schema = "";
		String t_c_baah = "";
		String t_c_jbfymc = "";
		String t_c_stm = "";
		String t_d_sarq = "";
		String t_n_jbfy = "";
		String ws_c_mc = "";
		String ws_c_nr = "";
		String name = "";
		try {
			name = new String(fileitor.name(), "UTF8");
			byte[] stream = fileitor.unzippedData();
			String[] name_split = name.split("\\:");
			base64File = Base64.getEncoder().encodeToString(stream);
			t_c_stm = name_split[0].trim();
			ws_c_mc = name_split[3].trim();
			ws_c_nr = name_split[4].trim();
			if (t_c_stm.isEmpty()) {
				t_c_stm = name_split[1].trim(); // 此文书为"09"法标的文书
			}
		} catch (Exception e) {
		}
		Ws wsObject = new Ws(database, fbId, host, schema, t_c_baah, t_c_jbfymc, t_c_stm, t_d_sarq, t_n_jbfy, ws_c_mc, ws_c_nr);
		WsWithBase64File wsWithBase64File = new WsWithBase64File(wsObject, base64File);
		return wsWithBase64File;
	}
}

