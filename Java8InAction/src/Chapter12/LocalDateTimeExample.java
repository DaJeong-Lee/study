package Chapter12;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import static java.time.temporal.TemporalAdjusters.*;

public class LocalDateTimeExample {
	public static void main(String[] args) {
		//1. LocalDate
		LocalDate date = LocalDate.of(2016, 3, 14);
		int year = date.getYear();
		Month month = date.getMonth();
		int day = date.getDayOfMonth();
		DayOfWeek dow = date.getDayOfWeek();
		int len = date.lengthOfMonth(); //31(3월의 일수)
		boolean leap = date.isLeapYear(); //ture(윤년여부)
		LocalDate today = date.now(); //현재날짜정보
		
		//2. LocalTime
		LocalTime time = LocalTime.of(14, 45, 20); // 14시 45분 20초
		int hour = time.getHour();
		int minute = time.getMinute();
		int second = time.getSecond();
		
		//문자열로 위2개의 인스턴스 생성가능
		LocalDate date2 = LocalDate.parse("2016-04-24");
		LocalTime time2 = LocalTime.parse("14:45:10");
		
		//3. LocalDateTime
		LocalDateTime dt1 = LocalDateTime.of(2014,Month.MARCH, 18, 13, 21, 40);
		LocalDateTime dt2 = LocalDateTime.of(date2, time2);
		LocalDateTime dt3 = date.atTime(time2);
		LocalDateTime dt4 = time2.atDate(date2);
		
		//4. Duration
		Duration d1 = Duration.between(time, time2);
		System.out.println("time과 time2 간격  msecs : " + d1.toMillis());
		
		//5. Period
		Period period = Period.between(date, date2);
		System.out.println("date와 date2 간격 : "+period.getYears() + "/"+period.getMonths()+"/"+period.getDays());
		
		//6. 날짜 조정 (절대적인 방식)
		LocalDate date3 = date2.withYear(2011);
		LocalDate date4 = date3.withDayOfMonth(30);
		System.out.println("date4는 "+ date4);
		
		//7. 날짜조정(상대적인 방식)
		LocalDate date5 = date4.plusWeeks(2); //2주 추가
		System.out.println("date5는 "+ date5);
		LocalDate date6 = date5.minusYears(2); //2년 전으로
		System.out.println("date6는 "+ date6);
		
		//8. TemporalAdjusters 
		LocalDate date7 = LocalDate.of(2016, 3, 14);
		LocalDate date8 = date7.with(nextOrSame(DayOfWeek.SUNDAY)); //돌아오는 일요일
		System.out.println("date8은 "+ date8);
		LocalDate date9 = date8.with(lastDayOfMonth()); //이달의 마지막일]
		System.out.println("date9은 "+ date9);
		
		//9. DateTimeFormatter
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formattedDate = date7.format(formatter);
		System.out.println("formattedDate : " + formattedDate);
	}
}