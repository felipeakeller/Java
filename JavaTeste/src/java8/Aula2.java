package java8;

import java.util.List;

public class Aula2 {

	public void teste(List<String> palavras) {
		
//		palavras.forEach(s -> System.out.println(s));
		
		palavras.sort( (s1, s2) -> Integer.compare(s1.length(), s2.length()) );
		
		//Object o = s -> System.out.println(s); The target type of this expression must be a functional interface
		
//		new Thread(() -> System.out.println("Executando um Runnable")).start();;
	}

}
