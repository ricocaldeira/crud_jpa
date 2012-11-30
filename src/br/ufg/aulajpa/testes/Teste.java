/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.aulajpa.testes;

import br.ufg.aulajpa.dao.BaseDAO;
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
        
        Editora editora = new Editora();
        Scanner entrada = new Scanner(System.in);
        
        System.out.println("Digite o nome da editora: ");
        editora.setNome(entrada.nextLine());
        
        System.out.println("Digite o email da editora: ");
        editora.setEmail(entrada.nextLine());
        
        BaseDAO dao = new BaseDAO();
        
        dao.criar(editora);
        imprimeListaDeEditoras();
        
    }
    
    private static void imprimeListaDeEditoras() {
        BaseDAO dao = new BaseDAO();
        List<Editora> editoras = dao.listarEditoras();
        for (Editora e:editoras){
            System.out.println(e.getId());
            System.out.println(e.getNome());
            System.out.println(e.getEmail());
        }
    }

}
