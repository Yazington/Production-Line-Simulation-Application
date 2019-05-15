package dataForSimulation;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class Icone{
	private String type;
	private String path;
	
	public Icone(String type, String path) 
	{
		this.path = path;
		this.type = type;
	}

	public String getPath()
	{
		return path;
		
	}
}
