package hw04.home;


public class App 
{
    public static void main( String[] args )
    {
        CoursesRepositoryImpl coursesRepository = new CoursesRepositoryImpl();
        for (int i = 0; i < 3; i++) {
            Course course = Course.create();
            coursesRepository.add(course);
        }
        System.out.println(coursesRepository.getById(0));
        System.out.println(coursesRepository.getById(1));
        System.out.println(coursesRepository.getById(2));
        Course courseForUpdate = coursesRepository.getById(2);
        courseForUpdate.updateDuration();
        courseForUpdate.updateName();
        coursesRepository.update(courseForUpdate);
        System.out.println(coursesRepository.getById(2));
        coursesRepository.delete(courseForUpdate);
        for (Course course : coursesRepository.getAll()) {
            System.out.println(course);
        }

    }
}
