package HW02.HW02_1;
public class Dog extends Animal{

    public Dog(String name, int age){
        super(name,age);
    }

    public Dog(String name){
        this(name, 0);
    }

    public Dog(){
        this("Doggy");
    }

    @Override
    public String toString() {
        return String.format("Dog {name = %s, age = %d}", getName(), getAge());
    }

    public void makeSound() {
        System.out.println("Гав!");
    }

}
