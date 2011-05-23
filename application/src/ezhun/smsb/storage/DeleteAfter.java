package ezhun.smsb.storage;

/**
 * Enum declaring values for setting drop down list.
 */
public enum DeleteAfter {
	SevenDays (7),
	FourteenDays (14),
	ThirtyDays (30);

	private final int index;

    DeleteAfter(int index) {
        this.index = index;
    }

    public int index() {
        return index;
    }
}
