package hw04.home;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CoursesRepositoryImpl implements CoursesRepository{
    SessionFactory sessionFactory;



    CoursesRepositoryImpl (){
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();
    };

    public void add(Course item) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(item);
        System.out.println("Object course save successfully");
        session.getTransaction().commit();
    }

    @Override
    public void update(Course item) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(item);
        session.getTransaction().commit();
    }

    @Override
    public void delete(Course item) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(item);
        session.getTransaction().commit();
    }

    @Override
    public Course getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Course course = session.get(Course.class, id);
        session.getTransaction().commit();
        return course;
    }

    @Override
    public Collection<Course> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT * FROM Courses", Course.class).getResultList();
    }
}
