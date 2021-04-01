package com.rayferric.havook.feature.mod;

public class ModAttributeString extends ModAttribute {
	public String value;
	private final transient String nativeValue;

	public ModAttributeString(String name, String nativeValue) {
		super(name);
		this.nativeValue = nativeValue;
		value = nativeValue;
	}

	public String getNativeValue() {
		return nativeValue;
	}
}
