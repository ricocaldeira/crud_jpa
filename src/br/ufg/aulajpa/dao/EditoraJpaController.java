/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.aulajpa.dao;

import br.ufg.aulajpa.dao.exceptions.NonexistentEntityException;
import br.ufg.aulajpa.modelo.Editora;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author ricardocaldeira
 */
public class EditoraJpaController implements Serializable {

    public EditoraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EditoraJpaController() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Editora editora) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(editora);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Editora editora) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            editora = em.merge(editora);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = editora.getId();
                if (findEditora(id) == null) {
                    throw new NonexistentEntityException("The editora with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Editora editora;
            try {
                editora = em.getReference(Editora.class, id);
                editora.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The editora with id " + id + " no longer exists.", enfe);
            }
            em.remove(editora);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Editora> findEditoraEntities() {
        return findEditoraEntities(true, -1, -1);
    }

    public List<Editora> findEditoraEntities(int maxResults, int firstResult) {
        return findEditoraEntities(false, maxResults, firstResult);
    }

    private List<Editora> findEditoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Editora.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Editora findEditora(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Editora.class, id);
        } finally {
            em.close();
        }
    }

    public int getEditoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Editora> rt = cq.from(Editora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
