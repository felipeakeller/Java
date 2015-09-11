package java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class Aula1 {

	List<String> palavras = Arrays.asList("casa código","alura online", "caelum");
	
	public void imprime() {
		Consumer<String> consumidorString = new Consumer<String>() {
			@Override
			public void accept(String s) {
				System.out.println(s);
			}
		};
		palavras.forEach(consumidorString);
	}
	
	public void ordena() {		
		palavras.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
	}
	
	/**
	 * palavras.replaceAll(operator);
	 * palavras.spliterator();
	 */

}