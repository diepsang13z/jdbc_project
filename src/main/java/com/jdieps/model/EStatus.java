package com.jdieps.model;

public enum EStatus {
	ACTIVE(1), BLOCK(0);

	private final int value;

	EStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static EStatus findByValue(int value) {
		for (EStatus status : EStatus.values()) {
			if (status.getValue() == value) {
				return status;
			}
		}
		return null;
	}
}
