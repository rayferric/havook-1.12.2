package com.rayferric.havook.feature.mod;

public class ModAttributeBoolean extends ModAttribute {
	public boolean value;
	private final transient boolean nativeValue;

	public ModAttributeBoolean(String name, boolean nativeValue) {
		super(name);
		this.nativeValue = nativeValue;
		value = nativeValue;
	}

	public boolean getNativeValue() {
		return nativeValue;
	}
}
