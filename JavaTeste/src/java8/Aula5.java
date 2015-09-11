package java8;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Aula5 {
	
	public void teste() {
		List<Curso> cursos = new ArrayList<>();
		cursos.add(new Curso("Python", 45));
		cursos.add(new Curso("JavaScript", 150));
		cursos.add(new Curso("Java8", 113));
		cursos.add(new Curso("C", 55));
		
		cursos.stream()
			.filter(c -> c.getAlunos() > 50)
			.findFirst();
		
		cursos.stream()
			.mapToInt(c -> c.getAlunos())
			.average();
		
		List<Curso> cursosFiltrados = cursos.stream()
				   .filter(c -> c.getAlunos() > 50)
				   .collect(Collectors.toList());
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
