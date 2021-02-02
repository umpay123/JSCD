package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Project : mtesense Created : java Date : 3/24/15
 */
public class PropUtil {

	private static Properties properties = null;

	private ResourceBundle mainViewResource;

	public PropUtil(String path) {
		initialize(path);
	}

	public PropUtil(ResourceBundle resource) {

		this.mainViewResource = resource;

	}

	public String getProperty(String key) {

		String keyValue = null;
		if (mainViewResource.containsKey(key)) {
			keyValue = mainViewResource.getString(key);
		}
		return keyValue;
	}

	private void initialize(String path) {
		// InputStream is =
		// getClass().getClassLoader().getResourceAsStream(path);

		FileInputStream is = null;
		try {
			is = new FileInputStream(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (is == null) {
			return;
		}
		properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * get specified key in config files
	 *
	 * @param key
	 *            the key name to get value
	 */
	public String get(String key) {
		String keyValue = null;
		if (properties.containsKey(key)) {
			keyValue = (String) properties.get(key);
		}
		return keyValue;
	}

	/**
	 * update specified key&value in config files
	 *
	 * @param keyname
	 *            the key name to get value
	 * @param keyvalue
	 *            the value
	 * @param profilepath
	 *            the properties file path
	 * @param description
	 *            the comment for key
	 */
	public void updateProperties(String keyname, String keyvalue, String profilepath, String description) {
		try {
			properties.load(new FileInputStream(profilepath));
			OutputStream fos = new FileOutputStream(profilepath);
			properties.setProperty(keyname, keyvalue);
			properties.store(fos, description);
		} catch (IOException e) {
			System.err.println("Properties file is updated incorrectly!");
		}
	}
}
