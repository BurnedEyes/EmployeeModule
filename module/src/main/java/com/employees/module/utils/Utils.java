package com.employees.module.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.stream.Stream;

public class Utils {

	public static String buildPath(String... paths) {
		StringBuilder sb = new StringBuilder();
		Stream.of(paths).forEach(p -> sb.append(p));
		return sb.toString();
	}

	public static LocalDateTime convertLongToLocalDateTime(long time) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), TimeZone.getDefault().toZoneId());
	}

}
