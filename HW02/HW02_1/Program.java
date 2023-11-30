package HW02.HW02_1;

public class Program {
    public static void main(String[] args) throws NoSuchMethodException, SecurityException {
        Animals animals = new Animals();
        animals.add(new Hamster("Homa", 2));
        animals.add(new Hamster("Hom"));
        animals.add(new Dog("Honey", 2));
        animals.add(new Dog());
        animals.add(new Cat());
        animals.add(new Cat("Billy"));
        animals.get(0).setName("Hom");
        animals.makeSound();
        animals.showInfoAll();
    }
}
