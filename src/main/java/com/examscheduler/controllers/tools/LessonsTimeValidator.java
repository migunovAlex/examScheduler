package com.examscheduler.controllers.tools;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.examscheduler.entity.LessonsTime;

public class LessonsTimeValidator {

	private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

	private final Pattern pattern;
	private Matcher matcher;

	public LessonsTimeValidator() {
		pattern = Pattern.compile(TIME24HOURS_PATTERN);
	}

	public boolean isValid(LessonsTime lessonTime, List<LessonsTime> listLessonTime) {
		return (isValidSymbolsInTimeStrings(lessonTime) && isValidPeriodValues(lessonTime) && isNotConflictWithOthers(lessonTime, listLessonTime));
	}

	private boolean isValidPeriodValues(LessonsTime lessonTime) {
		DateTime startTime = generateTime(lessonTime.getTimeStart());
		DateTime endTime = generateTime(lessonTime.getTimeEnd());
		return endTime.isAfter(startTime);
	}

	private boolean isNotConflictWithOthers(LessonsTime lessonTimeString, List<LessonsTime> listLessonTime) {
		DateTime startTimeToCheck = generateTime(lessonTimeString.getTimeStart());
		DateTime endTimeToCheck = generateTime(lessonTimeString.getTimeEnd());
		for (LessonsTime lessonTime : listLessonTime) {
			Interval interval = getTimePeriodFromLessonTime(lessonTime);
			if ((lessonTimeString.getLessonNumber() == lessonTime.getLessonNumber())
					|| interval.contains(startTimeToCheck) || interval.contains(endTimeToCheck))
				return false;
		}
		return true;
	}

	private Interval getTimePeriodFromLessonTime(LessonsTime lessonTime) {
		DateTime startTime = generateTime(lessonTime.getTimeStart());
		DateTime endTime = generateTime(lessonTime.getTimeEnd());
		Interval interval = new Interval(startTime, endTime);
		return interval;
	}

	private DateTime generateTime(String time) {
		String[] splittedTime = time.split(":");
		DateTime generatedTime = new DateTime(2000, 1, 1, Integer.valueOf(splittedTime[0]),
				Integer.valueOf(splittedTime[1]), 0, 0);
		return generatedTime;
	}

	private boolean isValidSymbolsInTimeStrings(LessonsTime lessonTime) {
		return validateSymbolsInTimeString(lessonTime.getTimeStart())
				&& validateSymbolsInTimeString(lessonTime.getTimeEnd());
	}

	private boolean validateSymbolsInTimeString(final String time) {
		matcher = pattern.matcher(time);
		return matcher.matches();

	}

}
