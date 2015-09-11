package java8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Aula4 {
	
	public void teste() {
		List<Curso> cursos = new ArrayList<>();
		cursos.add(new Curso("Python", 45));
		cursos.add(new Curso("JavaScript", 150));
		cursos.add(new Curso("Java8", 113));
		cursos.add(new Curso("C", 55));
		
		cursos.stream().findAny(); // retorna qualquer elemento da stream
		
		cursos.sort(Comparator.comparing(Curso::getAlunos));
		
		cursos.stream()
			.filter(c -> c.getAlunos() > 50)
			.forEach(c -> System.out.println(c.getNome()));
		
		cursos.stream().map(Curso::getNome);
		
		cursos.stream()
		   .filter(c -> c.getAlunos() > 50)
		   .map(Curso::getAlunos)
		   .forEach(System.out::println);
	}
	
	class Curso {
		private String nome;
		private int alunos;
		
		public Curso(String nome, int alunos) {
			this.nome = nome;
			this.alunos = alunos;
		}

		public String getNome() {
			return nome;
		}
		public int getAlunos() {
			return alunos;
		}
	}

}
