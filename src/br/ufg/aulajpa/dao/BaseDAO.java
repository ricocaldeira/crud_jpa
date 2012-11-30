/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.aulajpa.dao;

import br.ufg.aulajpa.modelo.Editora;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.osgi.service.jpa.EntityManagerFactoryBuilder;

/**
 *
 * @author ricardocaldeira
 */
public class BaseDAO {
    
    private static EntityManagerFactory emf;
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public BaseDAO() {
        emf = Persistence.createEntityManagerFactory("Aula-JPAPU");
    }
    
    public boolean criar(Object obj) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
        return true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }
    
    public List<Editora> listarEditoras() {
        EntityManager em = getEntityManager();
        try {
            List<Editora> editoras = em.createQuery("select e FROM Editora e").getResultList();
            return editoras;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
}
