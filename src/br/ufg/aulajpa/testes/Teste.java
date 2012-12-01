/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.aulajpa.testes;

import br.ufg.aulajpa.dao.AutorJpaController;
import br.ufg.aulajpa.dao.EditoraJpaController;
import br.ufg.aulajpa.modelo.Autor;
import br.ufg.aulajpa.modelo.Editora;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 *
 * @author alunoinf
 */
public class Teste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Aula-JPAPU");
        
        EditoraJpaController ed = new EditoraJpaController(emf);
        AutorJpaController autorController = new AutorJpaController(emf);
        
        Editora editora = new Editora();
        Autor autor = new Autor();
        
        cadastrarEditora(editora, ed);
        listarEditoras(ed);
        
        
    }

    private static void cadastrarEditora(Editora editora, EditoraJpaController ed) {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Digite o nome da editora: ");
        editora.setNome(entrada.nextLine());
        
        System.out.println("Digite o email da editora: ");
        editora.setEmail(entrada.nextLine());
        
        ed.create(editora);
    }

    private static void listarEditoras(EditoraJpaController ed) {
        List<Editora> editoras = ed.findEditoraEntities();
        for (Editora e:editoras){
            System.out.println(e.getId());
            System.out.println(e.getNome());
            System.out.println(e.getEmail());
        }
    }

}
