package de.berlin.arzt;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {
	
	public static void main(String[] args) {
		System.out.println(getDropBoxID(new File(args[0]).getName(), new Date(), null));
	}
	
	public static long getHour(double hour) {
		return (long) (hour * 60 * 60 * 1000);
	}
	
	public static StringBuilder getDropBoxID(String folder, Date date, StringBuilder builder) {
		if (builder == null)
			builder = new StringBuilder();
		builder.append(new SimpleDateFormat("yy-MM-dd").format(date)).append('-');
		for (String s : folder.split("[^a-zA-Z-]")) {
			builder.append(Character.toUpperCase(s.charAt(0)));
		}
		builder.append(".rar");
		return builder;
	}
}
