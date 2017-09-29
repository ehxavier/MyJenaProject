package br.com.ehxavier.myjenaproject;

public class JenaApplication {

	public static void main(String[] args) {
		
		JenaRDF myRDF = new JenaRDF();
		
		myRDF.consultarAutoresWeb();
		
		myRDF.consultarAutorWeb("Ariano Suassuna");
		myRDF.consultarAutorWeb("Graciliano Ramos");
		myRDF.consultarAutorWeb("Machado de Assis");
		
		myRDF.publicarAutores();
		

	}

}
