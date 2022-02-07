package it.jpa.test;

import it.backend.entity.BusinessUser;
import it.backend.entity.Product;
import it.backend.entity.Sim;
import it.backend.model.BusinessUserDTO;
import org.hibernate.jpa.QueryHints;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class JPATest {

    @Autowired
    EntityManager em;

    @Test
    public void test() {
        // Selezionare un BusinessUser specificando un id
        /*
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<BusinessUser> criteriaQuery = criteriaBuilder.createQuery(BusinessUser.class);
        Root user = criteriaQuery.from(BusinessUser.class);
        criteriaQuery.select(user).where(criteriaBuilder.equal(user.get("id"),  criteriaBuilder.parameter(Integer.class, "id")));
        TypedQuery<BusinessUser> query = em.createQuery(criteriaQuery);
        query.setParameter("id", 1);
        query.getSingleResult(); */
    }

    @Test
    public void test2() {
        // Selezionare un singolo campo di un Entity
        /*CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
        Root user = criteriaQuery.from(BusinessUser.class);
        criteriaQuery.select(user.get("firstName")).where(criteriaBuilder.equal(user.get("id"), 1));
        TypedQuery<String> query = em.createQuery(criteriaQuery);
        query.getSingleResult();*/
    }

    @Test
    public void test3() {
        //Selezionare più campi
        /*CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
        Root user = criteriaQuery.from(BusinessUser.class);
        criteriaQuery.multiselect(user.get("firstName"), user.get("lastName"))
                .where(criteriaBuilder.equal(user.get("id"), 1));
        Query query = em.createQuery(criteriaQuery);
        List<Object[]> elements = query.getResultList();*/
    }

    @Test
    public void test4() {
        // Selezionare più campi usando le Tuple
        /*CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
        Root user = criteriaQuery.from(BusinessUser.class);
        criteriaQuery.multiselect(user.get("firstName").alias("firstName"), user.get("lastName").alias("lastName"))
                .where(criteriaBuilder.equal(user.get("id"), 1));
        Query query = em.createQuery(criteriaQuery);
        List<Tuple> elements = query.getResultList();
        System.out.println(elements.get(0).get("firstName"));*/
    }

    @Test
    public void test5() {
        // Usiamo classi Dto per il recupero di dati
        /*CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Dto> criteriaQuery = criteriaBuilder.createQuery(Dto.class);
        Root user = criteriaQuery.from(BusinessUser.class);
        criteriaQuery.select(criteriaBuilder.construct(Dto.class, user.get("firstName"), user.get("lastName")));
        Query query = em.createQuery(criteriaQuery);
        List<Dto> result = query.getResultList();*/
    }

    @Test
    public void test7() {
        // Theta Join molteplicità di entity root
        /*CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
        Root user = criteriaQuery.from(BusinessUser.class);
        Root sim = criteriaQuery.from(Sim.class);
        criteriaQuery.multiselect(user, sim);
        criteriaQuery.where(criteriaBuilder.equal(user.get("id"), sim.get("id")));
        Query query = em.createQuery(criteriaQuery);
        List<Object[]> result = query.getResultList();*/
    }

    @Test
    public void test8() {
        // Join
        /*CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<BusinessUser> criteriaQuery = criteriaBuilder.createQuery(BusinessUser.class);
        Root user = criteriaQuery.from(BusinessUser.class);
        Join sim = user.join("sim");
        //Join sim = user.join("sim", JoinType.INNER);
        criteriaQuery.where(criteriaBuilder.equal(sim.get("msisdn"), "34567890"));
        TypedQuery<BusinessUser> query = em.createQuery(criteriaQuery);
        BusinessUser u = query.getSingleResult();*/
    }


    @Test
    public void test9() {
        // Join Fetch
        /*CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<BusinessUser> criteriaQuery = criteriaBuilder.createQuery(BusinessUser.class);
        Root user = criteriaQuery.from(BusinessUser.class);

        Fetch<BusinessUser, Sim> simfetch = user.fetch("sim", JoinType.INNER);
        Join<BusinessUser, Sim> sim = (Join<BusinessUser, Sim>)simfetch;

        criteriaQuery.where(criteriaBuilder.equal(sim.get("msisdn"), "34567890"));
        TypedQuery<BusinessUser> query = em.createQuery(criteriaQuery);
        BusinessUser u = query.getSingleResult();*/
    }

    @Test
    public void test10() {
        // Left outer join con condizione di recupero di soli utenti senza Sim
        // abbiamo un filtro
        // Notare i duplicati ....
        // Rimozione con distinct()...
        /*CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<BusinessUser> criteriaQuery = criteriaBuilder.createQuery(BusinessUser.class);
        Root user = criteriaQuery.from(BusinessUser.class);

        Fetch<BusinessUser, Sim> simfetch = user.fetch("sim", JoinType.LEFT);
        //Join<BusinessUser, Sim> sim = (Join<BusinessUser, Sim>) simfetch; Non mi serve, non seleziono su Sim

        criteriaQuery.where(criteriaBuilder.isEmpty(user.get("sim")));
        TypedQuery<BusinessUser> query = em.createQuery(criteriaQuery);
        List<BusinessUser> lista = query.getResultList();
        for(BusinessUser u:lista){
            System.out.println(u.getId());
            System.out.println(u.getSim());
        }*/
    }

    @Test
    @Rollback(true)
    public void testOneToMany() {
        // OneToMany con BusinessUser - Sim
        // Relazione unidirezionale:
            // Commentare setBusinessUser() in BusinessUser
            // La OneToMany diventa:
            /* @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
               orphanRemoval = false)*/
            // @JoinColumn(name = "business_user_id")
            // Commentare in Sim:
               // businessUser
               // metodi get e set


        //Persistenza di un nuova sim non presente su DB sull'utente esistente
        // insert  + update
        /*Sim sim = new Sim();
        BusinessUser user = em.find(BusinessUser.class, 1);
        user.addSim(sim);
        em.flush();*/

        //Persistenza di un nuovo utente con sim esistente
        // insert  + update
        /*BusinessUser user = new BusinessUser();
        Sim sim = em.find(Sim.class, 1);
        user.addSim(sim);
        em.persist(user);
        em.flush();*/

        //Esercizio sperimentare la ManyToOne unidirezionale
    }

    @Test
    @Rollback(true)
    public void testReference() {
        //Assegnamo una sim sfruttando il proxy Hibernate
        // per aggiornare il foreign key della tabella sim
        // nessuna query sulla tabella sim
     /*BusinessUser user = em.getReference(BusinessUser.class,1);
     Sim sim = new Sim();
     sim.setBusinessUser(user);
     em.persist(sim);
     em.flush();*/


     // Due query per il recupero dei dati per effetto della sincronizzazione
     /*BusinessUser user = em.getReference(BusinessUser.class,1);
     Sim sim = em.getReference(Sim.class, 3);
     user.addSim(sim);
     em.flush(); //dirty checking al flush time disabilitato se rollback*/


        // Ok conosco la sincronizzazione e a cosa serve ma per aggiornare una sim
        // con l'utente posso in modo sicuro utilizzare il metodo set direttamente
        // non passando per la sincronizzazione (non ho bisogno dell'utente),
        // una singola query
     /*BusinessUser user = em.getReference(BusinessUser.class,1);
     Sim sim = em.getReference(Sim.class, 3);
     sim.setBusinessUser(user);
     em.flush(); //dirty checking al flush time disabilitato se rollback*/


        //Bulk voglio evitare ogni query
        /*int count  = em.
                createNativeQuery("update sim set business_user_id=1 where id=3")
                .executeUpdate();*/


        //Caso di studio associazione SIM non esistente ad un utente
        /*TypedQuery<BusinessUser> query = em.createQuery("select c from BusinessUser c left outer join " +
                "fetch c.sim where c.id=:userId", BusinessUser.class);
        query.setParameter("userId",1);
        BusinessUser businessUserEntity = query.getSingleResult();

        Sim simEntity = new Sim();
        simEntity.setImsi("test2");
        simEntity.setMsisdn("test2");
        simEntity.setBusinessUser(businessUserEntity);
        //em.persist(simEntity); // Senza CascadeType.PERSIST su BusinessUser è necessario
                               // Ma attenzione abbiamo un update successivo

        businessUserEntity.addSim(simEntity);
        em.flush();*/

        /* Associare la SIM nel modo più effciente se la Sim non è presente nel db
        BusinessUser businessUserEntity = em.getReference(BusinessUser.class, 1);
        Sim simEntity = new Sim();
        simEntity.setImsi("test2");
        simEntity.setMsisdn("test2");
        simEntity.setBusinessUser(businessUserEntity);
        em.persist(simEntity);
        em.flush();*/
    }

    @Test
    @Rollback(true)
    public void testRelations() {
        // ManyToOne EAGER su entity Sim per BusinessUser
        // Rimozione @LazyToOne(LazyToOneOption.NO_PROXY) si utilizza solo con il LAZY
        // Query corretta eseguita su relazione EAGER
        //Sim sim = em.find(Sim.class, 2);

        // Query aggiuntiva eseguita su relazione EAGER
        /*TypedQuery<Sim> query = em.createQuery("select c from Sim c " +
                "where id=:id", Sim.class);
        query.setParameter("id",2);
        Sim sim = query.getSingleResult();
        System.out.println(sim.getBusinessUser());*/

        // Risolviamo il problema della query in più con il join esplicito anche se EAGER
        /*TypedQuery<Sim> query = em.createQuery("select c from Sim c join fetch c.businessUser " +
                "where c.id=:id", Sim.class);
        query.setParameter("id",2);
        Sim sim = query.getSingleResult();
        System.out.println(sim.getBusinessUser());*/

        // Sebbene ci sia la possibilità di un caricamente EAGER molto spesso
        // non è una buona idea mantenerlo, entity con molte relazioni EAGER
        // possono produrre numerosi JOIN magari non desiderati

        // Attenzione alla molteplicità di relazioni uno a molti con il tipo List
        // Errore MultipleBagFetchException, utilizzare Set
    }

    @Test
    @Rollback(true)
    public void testPagination() {
        /*int pageSize = 50;
        TypedQuery<Sim> query = em.
                createQuery("select c from Sim c join fetch c.businessUser "
                        , Sim.class).setFirstResult(0).
                setMaxResults(pageSize).
                setHint(QueryHints.HINT_FETCH_SIZE, pageSize); //pageSize

        List<Sim> sim = query.getResultList();
        System.out.println(sim);*/
        // if we try to use the JOIN FETCH clause in the entity
        // query while also using JPA pagination:
        //HHH000104: firstResult/maxResults specified with collection
        // fetch; applying in memory!
        //https://vladmihalcea.com/query-pagination-jpa-hibernate/
    }

    @Test
    @Rollback(true)
    public void testPaginationDTO() {
        /*TypedQuery<BusinessUserDTO> query =
                em.createQuery("select new " +
                                        "it.backend.model.BusinessUserDTO(c.id, c.firstName," +
                                        " c.lastName,  c.fiscalCode) " +
                                        "from BusinessUser c",
                                BusinessUserDTO.class)
                .setFirstResult(0).setMaxResults(10);
        query.getResultList();*/
    }
}