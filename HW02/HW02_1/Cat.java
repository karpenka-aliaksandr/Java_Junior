package HW02.HW02_1;
public class Cat extends Animal{

    public Cat(String name, int age){
        super(name,age);
    }

    public Cat(String name){
        this(name, 0);
    }

    public Cat(){
        this("Catty");
    }

    @Override
    public String toString() {
        return String.format("Cat {name = %s, age = %d}", getName(), getAge());
    }

    public void makeSound() {
        System.out.println("Мяу.");
    }

}
