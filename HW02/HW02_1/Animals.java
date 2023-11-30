package HW02.HW02_1;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class Animals {
    private ArrayList<Animal> animals;

    public Animals() {
        this.animals = new ArrayList<>();
    }
    
    public boolean isEmpty(){
        return animals.isEmpty();
    }

    public boolean add(Animal animal){
        return animals.add(animal);
    }
    public Animal get(int index){
        return animals.get(index);
    }

    public void makeSound() {
        for (Animal animal : animals) {
            Class<?> animalClass = animal.getClass();
            try {
                Method makeSoundMethod = animalClass.getDeclaredMethod("makeSound");
                makeSoundMethod.invoke(animal);
            } catch (Exception e) {
                // TODO: handle exception
            }
            
        }
    }

    public void showInfo(Animal animal){
        Class<?> animalClass = animal.getClass();
        Field[] sFields = animalClass.getSuperclass().getDeclaredFields();
        Field[] fields = animalClass.getDeclaredFields();
        Method[] sMethods = animalClass.getSuperclass().getDeclaredMethods();
        Method[] methods = animalClass.getDeclaredMethods();

        StringBuilder sb = new StringBuilder();
        sb.append("Class: ").append(animalClass.getSimpleName()).append(".   Fields: ");

    
        Arrays.stream(sFields).forEach(field -> {
            try {
                field.setAccessible(true);
                sb.append(String.format("%s = %s, ", field.getName(), field.get(animal).toString()));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        Arrays.stream(fields).forEach(field -> {
            try {
                field.setAccessible(true);
                sb.append(String.format("%s = %s, ", field.getName(), field.get(animal).toString()));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        if (sFields.length>0 || fields.length>0) sb.delete(sb.length()-2,sb.length());

        sb.append(".   Methods: ");
        Arrays.stream(sMethods).forEach(method -> {
            try {
                sb.append(String.format("%s, ", method.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Arrays.stream(methods).forEach(method -> {
            try {
                sb.append(String.format("%s, ", method.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        if (sMethods.length>0 || methods.length>0) sb.delete(sb.length()-2,sb.length());
        System.out.println(sb);                
    }

    public void showInfoAll() {
        for (Animal animal : animals) {
            showInfo(animal);
        }
    }

    
}
