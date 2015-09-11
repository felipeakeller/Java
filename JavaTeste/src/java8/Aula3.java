package java8;

import java.util.Comparator;
import java.util.List;
import static java.util.Comparator.comparing;

public class Aula3 {
	
	public void teste(List<String> palavras) {
		palavras.sort((s1, s2) -> {
		    return Integer.compare(s1.length(), s2.length()); 
		});
		
		palavras.sort(Comparator.comparing(s -> s.length()));
		palavras.sort(comparing(String::length));
		
		palavras.sort(String.CASE_INSENSITIVE_ORDER);
		
		palavras.forEach(System.out::println);
	}
	

}
