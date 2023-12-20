package hw04.home;

import java.util.Random;
import javax.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    private static final String[] names = new String[] { "Математика", "Физика", "Химия", "География", "Русский язык"};
    private static final Random random = new Random();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int duration;

    public Course() {

    }

    public Course(int id, String name, int duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public Course(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public static Course create(){
        return new Course(names[random.nextInt(names.length)], random.nextInt(30) + 20);
    }



    public void updateDuration(){
        duration = random.nextInt(30) + 20;
    }

    public void updateName(){
        name = names[random.nextInt(names.length)];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int age) {
        this.duration = age;
    }


    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                '}';
    }
}
