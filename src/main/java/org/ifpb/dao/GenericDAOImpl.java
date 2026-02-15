package org.ifpb.dao;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import org.ifpb.config.HibernateUtil;
import org.ifpb.dao.dao_exceptions.DatabaseException;
import org.ifpb.dao.interfaces.GenericDAO;

import java.util.List;
import java.util.Optional;

public abstract class GenericDAOImpl<T, ID> implements GenericDAO<T, ID> {

    private final Class<T> classe;

    public GenericDAOImpl(Class<T> classe) {
        this.classe = classe;
    }
    protected EntityManager getEntityManager(){
        return HibernateUtil.getEntityManager();
    }

    @Override
    public T update(T entity) {
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            T entityUpdated = em.merge(entity);
            em.getTransaction().commit();
            return entityUpdated;

        }catch (OptimisticLockException e) {
            rollback(em);
            throw new DatabaseException("Conflito de versão: O registro foi alterado por outro usuário.", e);


        }catch (IllegalArgumentException e) {
            rollback(em);
            throw new DatabaseException("Erro: Tentativa de atualizar um registro inválido ou removido.", e);


        }catch (Exception e) {
            rollback(em);
            throw new DatabaseException("Erro sistêmico inesperado ao atualizar " + classe.getSimpleName(), e);
        }finally {
            em.close();
        }

    }
    @Override
    public void save(T entity) {
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();

        }catch (EntityExistsException e) {
            rollback(em);
            throw new DatabaseException("Erro: Já existe um registro com este ID no sistema.", e);

        }catch (IllegalArgumentException e) {
            rollback(em);
            throw new DatabaseException("Erro de validação: Entidade inválida ou nula fornecida para salvar.", e);

        } catch (Exception e) {
            rollback(em);
            throw new DatabaseException("Erro sistêmico inesperado ao salvar " + classe.getSimpleName(), e);

        } finally {
            em.close();
        }
    }


    @Override
    public void deleteById(ID id) {
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();

            T entity = em.find(classe, id);

            if(entity != null){
                em.remove(entity);
            }
            em.getTransaction().commit();

        }catch (IllegalArgumentException e) {
            rollback(em);
            throw new DatabaseException("Erro: ID inválido para exclusão.", e);

        }catch (Exception e) {
            rollback(em);
            throw new DatabaseException("Erro sistêmico inesperado ao excluir registro.", e);

        }finally {
            em.close();
        }
    }


    @Override
    public Optional<T> findById(ID id) {

        try(EntityManager em = getEntityManager()){
            return Optional.ofNullable(em.find(classe, id));
        }catch (IllegalArgumentException e) {
            throw new DatabaseException("Erro: ID inválido para busca.", e);
        } catch (Exception e) {
            throw new DatabaseException("Erro de conexão ao buscar pelo ID.", e);
        }
    }

    @Override
    public List<T> findAll() {

        try(EntityManager em = getEntityManager()){

            String jpql = "SELECT t FROM " + classe.getSimpleName() + " t";

            return em.createQuery(jpql, classe).getResultList();

        }catch (Exception e) {
            throw new DatabaseException("Erro fatal ao listar registros de " + classe.getSimpleName(), e);
        }

    }

    @Override
    public boolean existsById(ID id) {
        try{
            return findById(id).isPresent();

        }catch (DatabaseException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Erro ao verificar existência do registro.", e);
        }
    }
    @Override
    public long count() {
        try(EntityManager em = getEntityManager()){

            String jpql = "SELECT COUNT(t) FROM " + classe.getSimpleName() + " t";
            return em.createQuery(jpql, Long.class).getSingleResult();

        }catch (Exception e) {
            throw new DatabaseException("Erro ao contar total de registros.", e);
        }
    }
    private void rollback(EntityManager em) {
        if (em != null && em.getTransaction().isActive()) {
            try {
                em.getTransaction().rollback();
            } catch (Exception e) {

                System.err.println("Erro crítico ao tentar fazer rollback: " + e.getMessage());
            }
        }
    }
}
