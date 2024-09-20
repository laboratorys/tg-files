package com.github.lab.files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App
{

	public static void main(String[] args)
	{
		if (System.getenv("env").equals("dev"))
		{
			System.getProperties().put("proxySet", "true");
			System.getProperties().put("socksProxyHost", "127.0.0.1");
			System.getProperties().put("socksProxyPort", "10808");
		}
		SpringApplication.run(App.class, args);
	}

}
