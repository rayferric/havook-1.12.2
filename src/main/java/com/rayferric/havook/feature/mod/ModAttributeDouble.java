package com.rayferric.havook.feature.mod;

public class ModAttributeDouble extends ModAttribute {
	public double value;
	private final transient double nativeValue;

	public ModAttributeDouble(String name, double nativeValue) {
		super(name);
		this.nativeValue = nativeValue;
		value = nativeValue;
	}

	public double getNativeValue() {
		return nativeValue;
	}
}
