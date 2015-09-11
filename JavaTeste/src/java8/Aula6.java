package java8;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Aula6 {
	
	public static void main(String[] args) {
//		LocalDate now = LocalDate.now();
//		LocalDate date2099 = LocalDate.of(2099, Month.JANUARY, 25);
//		Period period = Period.between(now, date2099);
		
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		System.out.println(formatador.format(now));
	}

}
