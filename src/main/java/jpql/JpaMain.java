package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpqltest");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            for(int i = 0; i < 100; ++i) {
                Member member = new Member();
                member.setUsername("nimkoes " + i);
                member.setAge(i + 10);
                em.persist(member);
            }

            /*
            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
            Query query3 = em.createQuery("select m.username, m.age from Member m");
            */

            /*
            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            List<Member> resultList = query1.getResultList();

            for (Member m : resultList) {
                System.out.println("m = " + m);
            }
            */

            /*
            TypedQuery<Member> query = em.createQuery("select m from Member m where m.username = :username", Member.class);
            query.setParameter("username", "nimkoes");
            Member singleResult = query.getSingleResult();

            System.out.println("singleResult : " + singleResult.getUsername());
            */

            /*
            Member singleResult = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "nimkoes")
                    .getSingleResult();
            System.out.println("singleResult : " + singleResult.getUsername() + " : " + singleResult.getAge());
            */

            em.flush();
            em.clear();

//            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
//            List<Team> result = em.createQuery("select m.team from Member m", Team.class).getResultList();

            /*
            List<MemberDTO> result = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class).getResultList();

            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO : " + memberDTO.getUsername());
            System.out.println("memberDTO : " + memberDTO.getAge());
            */

            List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("size :: " + result.size());
            for (Member m : result) {
                System.out.println(m);
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.clear();
        }
        emf.close();
    }
}
