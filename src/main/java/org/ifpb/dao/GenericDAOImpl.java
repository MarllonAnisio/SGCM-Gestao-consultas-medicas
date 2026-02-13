package org.ifpb.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.ifpb.config.HibernateUtil;
import org.ifpb.dao.interfaces.GerericDAO;

import java.util.List;
import java.util.Optional;

public abstract class GenericDAOImpl<T, ID> implements GerericDAO<T, ID> {

    private final Class<T> classe;

    public GenericDAOImpl(Class<T> classe) {
        this.classe = classe;
    }
    protected EntityManager getEntityManager(){
        return HibernateUtil.getEntityManager();
    }

    /**
     * Sincroniza o estado da entidade com o banco de dados dentro de uma transação isolada.
     * * <p>Este método utiliza a semântica de <b>upsert</b> (update ou insert):</p>
     * <ul>
     * <li>Se a entidade já possui um identificador (ID) presente no contexto de persistência,
     * os dados são atualizados (SQL {@code UPDATE}).</li>
     * <li>Se a entidade é nova ou não possui ID, uma nova entrada é criada
     * no banco de dados (SQL {@code INSERT}).</li>
     * </ul>
     * * <p>Após a operação, a instância retornada é a instância gerenciada pelo
     * {@link EntityManager}, e não necessariamente a instância passada como parâmetro.</p>
     * @param entity A instância da entidade a ser salva ou atualizada. Não deve ser {@code null}.
     * @return A instância gerenciada da entidade, com seu estado atualizado e IDs gerados.
     */
    @Override
    public T update(T entity) {
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            T entityUpdated = em.merge(entity);
            em.getTransaction().commit();
            return entity;

        }catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
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

        }catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    /**
     * Busca uma entidade T pelo seu ID
     * @param id Identificador da entidade
     *  <p>Este método utiliza a semântica de <b>Select</b></p>
     * */
    @Override
    public Optional<T> findById(ID id) {

        try(EntityManager em = getEntityManager()){
            return Optional.ofNullable(em.find(classe, id));
        }
    }

    /**
     * Remove permanentemente a entidade do repositório utilizando seu identificador único.
     *
     * <p>Esta operação segue a semântica de <b>Fetch-and-Remove</b>:</p>
     * <ol>
     * <li>Realiza a busca da entidade no estado <i>Managed</i> (SQL {@code SELECT}).</li>
     * <li>Agenda a remoção da instância encontrada para o próximo flush (SQL {@code DELETE}).</li>
     * </ol>
     *
     * @param id O identificador único da entidade. Não deve ser {@code null}.
     * @throws IllegalArgumentException se o {@code id} for {@code null}.
     * @throws RuntimeException se a entidade não for encontrada ou houver erro de integridade.
     * @see #findById(Object)
     */
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
        }catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        }finally{
            em.close();
        }
    }

    @Override
    public List<T> findAll() {
        EntityManager em = getEntityManager();
        try{
            String jpql = "SELECT t FROM " + classe.getSimpleName() + " t";
            TypedQuery<T> query = em.createQuery(jpql, classe);
            return query.getResultList();

        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            em.close();
        }

    }@Override
    public boolean existsById(ID id) {
        try{
            return findById(id) != null;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }@Override
    public long count() {
        try{
            String jpql = "SELECT COUNT(t) FROM " + classe.getSimpleName() + " t";
            TypedQuery<Long> query = getEntityManager().createQuery(jpql, Long.class);
            return query.getSingleResult();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @Override
    public void delete(T entity) {
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        }finally{
            em.close();
        }
    }
}
