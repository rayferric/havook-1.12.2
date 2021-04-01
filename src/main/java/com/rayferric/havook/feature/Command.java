package com.rayferric.havook.feature;

public abstract class Command {
	public String name = "";
	public String syntax = "";
	public String description = "";

	public Command(String name, String syntax, String description) {
		this.name = name;
		this.syntax = syntax;
		this.description = description;
	}

	public abstract void execute(String[] args);
}
