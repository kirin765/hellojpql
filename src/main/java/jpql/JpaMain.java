package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Member member = new Member();
            member.setUsername("회원1");
            member.setAge(10);
            member.setType(MemberType.ADMIN);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setAge(10);
            member2.setType(MemberType.ADMIN);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setAge(10);
            member3.setType(MemberType.ADMIN);

            member.setTeam(team);
            member2.setTeam(team);
            member3.setTeam(team2);

            em.persist(member);
            em.persist(member2);
            em.persist(member3);

            em.flush();
            em.clear();


            String query = "select t from Team t";
            List<Team> resultList = em.createQuery(query, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            System.out.println("resultList = " + resultList.size());

            for (Team team1 : resultList) {
                System.out.println("team1.getName() = " + team1.getName() + " " + team1.getMembers().size());
                for (Member member1 : team1.getMembers()) {
                    System.out.println("member1 = " + member1);
                }
            }


            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }

        emf.close();
    }

}
