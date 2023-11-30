package HW02.HW02_1;
public class Hamster extends Animal{

    public Hamster(String name, int age){
        super(name,age);
    }

    public Hamster(String name){
        this(name, 0);
    }

    public Hamster(){
        this("Hammy");
    }

    @Override
    public String toString() {
        return String.format("Hamster {name = %s, age = %d}", getName(), getAge());
    }

    

}
