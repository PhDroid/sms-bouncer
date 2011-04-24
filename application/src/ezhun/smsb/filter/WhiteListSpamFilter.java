package ezhun.smsb.filter;

import ezhun.smsb.SmsPojo;

/**
 * Spam Filter taking into consideration only white list of senders.
 */
public class WhiteListSpamFilter implements ISpamFilter {
	@Override
	public boolean isSpam(SmsPojo message) {
		return false;
	}
}
