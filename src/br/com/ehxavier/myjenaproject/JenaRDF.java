package br.com.ehxavier.myjenaproject;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

public class JenaRDF {

	public void consultarAutoresWeb(){

		String prefix = "PREFIX dbo:<http://dbpedia.org/ontology/> "+
						"PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"+
						"PREFIX dbp:<http://dbpedia.org/property/>";
		String sparql = "SELECT DISTINCT ?author ?name ?nacionality ?bplacename ?dtnasc "+
		"WHERE { ?book a dbo:Book .  ?book dbo:author ?author . ?author rdfs:label ?name . "+
		"?author dbo:birthDate ?dtnasc . ?author dbp:nationality ?nacionality . "+
		"?author dbo:birthPlace ?bplace . ?bplace rdfs:label ?bplacename . "+
		"FILTER(lang(?name) = 'pt') FILTER(lang(?bplacename) = 'pt')} "+
		"ORDER BY ?name LIMIT 10";
				
		String queryStr = prefix + sparql;
        Query query = QueryFactory.create(queryStr);

        // Remote execution.
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query) ) {
            
            // Execute
            ResultSet rs = qexec.execSelect();
            ResultSetFormatter.out(System.out, rs, query);
                       
            // RETORNAR A LISTA DE AUTORES NO MÉTODO...
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
	}
	
	public void consultarAutorWeb(String nome){

		String prefix = "PREFIX dbo:<http://dbpedia.org/ontology/> "+
						"PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"+
						"PREFIX dbp:<http://dbpedia.org/property/>";
		String sparql = "SELECT DISTINCT ?author ?name ?nacionality ?bplacename ?dtnasc "+
		"WHERE { ?book a dbo:Book .  ?book dbo:author ?author . ?author rdfs:label ?name . "+
		"?author dbo:birthDate ?dtnasc . ?author dbp:nationality ?nacionality . "+
		"?author dbo:birthPlace ?bplace . ?bplace rdfs:label ?bplacename . "+
		"FILTER(lang(?name) = 'pt') FILTER(lang(?bplacename) = 'pt') "+
		"FILTER regex(?name, \"" + nome + "\"@pt) }";
				
		String queryStr = prefix + sparql;
        Query query = QueryFactory.create(queryStr);

        // Remote execution.
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query) ) {
            
            // Execute
            ResultSet rs = qexec.execSelect();
            ResultSetFormatter.out(System.out, rs, query);
                       
            // RETORNAR AUTOR NO MÉTODO...
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
	} 
	
public void publicarAutores(){
		
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix( "livraria", "http://localhost:8080/livraria-maven/" );		
		String myNS = "http://localhost:8080/livraria-maven/data/";
		
		Property nome = model.createProperty("http://localhost:8080/livraria-maven/data/nome");
		Property nacionalidade = model.createProperty("http://localhost:8080/livraria-maven/data/nacionalidade");
		Property naturalidade = model.createProperty("http://localhost:8080/livraria-maven/data/naturalidade");
		Property dtnascimento = model.createProperty("http://localhost:8080/livraria-maven/data/dtnascimento");
		
		
		Resource autor1 = model.createResource(myNS+"author/001");
		Resource autor2 = model.createResource(myNS+"author/002");
		Resource autor3 = model.createResource(myNS+"author/003");
		
		
		
		autor1.addProperty(RDF.type, myNS+"Autor");
		autor1.addProperty(nome, myNS+"Joaquim");
		autor1.addProperty(nacionalidade, myNS+"brasileiro");
		autor1.addProperty(naturalidade, myNS+"Espírito Santo");
		autor1.addProperty(dtnascimento, myNS+"01.01.2001");
		
		autor2.addProperty(RDF.type, myNS+"Autor");
		autor2.addProperty(nome, myNS+"Maria");
		autor2.addProperty(nacionalidade, myNS+"Espanhol");
		autor2.addProperty(naturalidade, myNS+"Madrid");
		autor2.addProperty(dtnascimento, myNS+"01.01.1994");
		
		autor3.addProperty(RDF.type, myNS+"Autor");
		autor3.addProperty(nome, myNS+"Manuel");
		autor3.addProperty(nacionalidade, myNS+"Português");
		autor3.addProperty(naturalidade, myNS+"Lisboa");
		autor3.addProperty(dtnascimento, myNS+"01.01.1999");
		
		// now write the model in XML form to a file
		model.write(System.out, "RDF/XML");
		
	}
}
