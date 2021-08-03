import concurs.model.Concurs;
import concurs.repo.HibernateUtils;
import concurs.repo.IRepoConcurs;
import concurs.repo.RepoConcursHibernate;

public class TestMain {
    public static void main(String[] args){
        HibernateUtils hibernateUtils = new HibernateUtils();
        hibernateUtils.initialize();
        RepoConcursHibernate repoConcurs = new RepoConcursHibernate(hibernateUtils);
        Concurs concurs = new Concurs();
        concurs.setNumar(2);
        concurs.setCapacitate(150);
        concurs.setNume("TestHib4");
        concurs.setDistanta(200.0);
        repoConcurs.save(concurs);
        System.out.println(repoConcurs.findOne(2));
        hibernateUtils.close();
    }
}
