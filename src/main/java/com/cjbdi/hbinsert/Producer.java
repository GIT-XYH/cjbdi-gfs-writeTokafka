package com.cjbdi.hbinsert;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.gfs.data.DataBlockFile;
import com.cjbdi.gfs.data.DataStore;
import com.cjbdi.gfs.data.DataStoreManager;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.io.File;
import java.util.Map;
import java.util.Properties;

public class Producer{
	private static UnzipUtil unzipper = new UnzipUtil();
	private static Config config = new Config();

	public static void main(String [] args) {
		readFile(args);
	}

	public static void readFile(String [] args) {
		//kafka配置
		//创建 kafka 生产者
		Properties properties = new Properties();
		properties.put("acks", "all");
		properties.put("retries", 0);
		properties.put("compression.type", "snappy");
		properties.put("batch.size", 16384);
		properties.put("key.serializer", StringSerializer.class.getName());
		properties.put("value.serializer",StringSerializer.class.getName());
//		properties.put("bootstrap.servers", "rookiex01:9092");
		properties.put("bootstrap.servers", "192.1.48.233:9092");
		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
		//rootPath:Users/xuyuanhang/Desktop/hyws2
		String rootPath = args[0];
		File dataPath = new File(rootPath);
		long total = 0;
		long a=1;
		long b= 0;
		String text = "";

		File[] subPath = unzipper.fileWalker(dataPath, config.start_dir, config.end_dir);
		for(File dsmPath:subPath) {
			if(dsmPath !=null) {
				System.out.println("dsmPath: " + dsmPath);
				DataStoreManager dsm = new DataStoreManager(dsmPath, true);
				dsm.open();
				Map<String, DataStore> dsList = dsm.dataStores();
				for (String key : dsList.keySet()) {
					//me
					System.out.println("key: " + key);
//				if (!key.contains("db_ms")) {
//					continue;
//				}
					DataStore ds = dsm.getDataStore(key);
					ds.open();
					int fileCount = ds.fileCount();
					for (int i = 0; i < fileCount; i++) {
						try {
							DataBlockFile dbf = ds.getDataFile(i);
							DataBlockFile.Iterator fileitor = dbf.iterator();
							while (fileitor.hasNext()) {
								total += 1;
								if (total % 100000 == 0) {
									System.out.println("已扫描的文件数量: " + total);
								}
								try {
									fileitor.next();
									Ws singleWs = unzipper.unzip(fileitor, dsmPath.toString(), key);
									if (singleWs != null) {
										JSONObject jsonObject = new JSONObject();
										jsonObject.put("c_stm", singleWs.c_stm);
										jsonObject.put("c_ajbs", singleWs.c_ajbs);
										jsonObject.put("c_mc", singleWs.c_mc);
										jsonObject.put("c_nr", singleWs.c_nr);
										jsonObject.put("c_FILE", singleWs.c_file);
										text = JSONObject.toJSONString(jsonObject) + "\n";
										a += 1;
											b++;
//											CreateFileUtil.createJsonFile(text, jsonTextName);
//											System.out.println(text);
											try {
												producer.send(new ProducerRecord<String, String>("t01", singleWs.c_nr));
											} catch (Exception e) {
												e.printStackTrace();
											}

									}
								} catch (Exception e) {
								}
							}
							dbf.close();
						} catch (Exception e) {
						}
					}
//				}
					ds.close();
				}
				dsm.close();

			}
		}
		producer.close();

	}

}

