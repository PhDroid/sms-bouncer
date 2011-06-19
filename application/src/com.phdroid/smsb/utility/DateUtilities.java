package com.phdroid.smsb.utility;

import android.app.Activity;
import android.text.format.DateUtils;
import com.phdroid.smsb.SmsPojo;

public class DateUtilities {
	public static String getRelativeDateString(SmsPojo sms, Activity ctx) {
		String dateTimeString = DateUtils.getRelativeDateTimeString(
				ctx,
				sms.getReceived(),
				DateUtils.MINUTE_IN_MILLIS,
				DateUtils.WEEK_IN_MILLIS,
				DateUtils.FORMAT_ABBREV_RELATIVE).toString();
		return dateTimeString.substring(0, dateTimeString.indexOf(','));
	}
}
