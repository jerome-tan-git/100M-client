package com.jerome;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SystemProperties {
	private static SystemProperties properties;
	private Properties p = new Properties();
	public static SystemProperties getInstance() throws IOException
	{
		if(SystemProperties.properties == null) 
			SystemProperties.properties = new SystemProperties();
		return SystemProperties.properties;
	}
	private SystemProperties() throws IOException {
		InputStream in = new BufferedInputStream(new FileInputStream("./system.properties"));
		
		p.load(in);	// TODO Auto-generated constructor stub
	}
	public String getProperty(String _key)
	{
		return this.p.getProperty(_key);
	}
	
}
