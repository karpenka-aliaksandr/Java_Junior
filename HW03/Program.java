package HW03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;


// Разработайте класс Student с полями String name, int age, transient double GPA (средний балл). 
// Обеспечьте поддержку сериализации для этого класса. 
// Создайте объект класса Student и инициализируйте его данными. 
// Сериализуйте этот объект в файл. 
// Десериализуйте объект обратно в программу из файла. 
// Выведите все поля объекта, включая GPA, и обсудите, почему значение GPA не было сохранено/восстановлено.

// 2. * Выполнить задачу 1 используя другие типы сериализаторов (в xml и json документы).

public class Program {
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        final String fileNameBin = "HW03/student.bin";

        Student student = new Student("Alex", 41, 4.98);
        System.out.println("Перед сериализацией:");
        showInfo(student);     

        try(FileOutputStream fileOutputStream = new FileOutputStream(fileNameBin);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
            objectOutputStream.writeObject(student);
            System.out.println("Объект student сериализован.");
        }
        Student student1;
        try(FileInputStream fileInputStream = new FileInputStream(fileNameBin);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            student1 = (Student)objectInputStream.readObject();
            System.out.println("Объект student десериализован.");
        }

        System.out.println("После десериализации:");
        showInfo(student1);  
        System.out.println("Потеряли значение GPA, так как поле помечено transient");


        //сериализация через Externalizable
        System.out.println();
        System.out.println("Сериализация через Externalizable:");
        StudentEx studentEx = new StudentEx("Alex", 41, 4.98);
        System.out.println("Перед сериализацией:");
        showInfo(studentEx);
        studentEx.writeExternal(new ObjectOutputStream(new FileOutputStream("HW03/studentex.bin")));

        StudentEx studentEx1 = new StudentEx();
        studentEx1.readExternal(new ObjectInputStream(new FileInputStream("HW03/studentex.bin")));
        System.out.println("После десериализации:");
        showInfo(studentEx1); 
        System.out.println("Потеряли значение GPA, так как поле не сериализовали/десериализовали");



        

    }

    public static void showInfo(Object obj){
        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();

        StringBuilder sb = new StringBuilder();
        sb.append("Class: ").append(objClass.getSimpleName()).append(".   Fields: ");

        Arrays.stream(fields).forEach(field -> {
            try {
                field.setAccessible(true);
                sb.append(String.format("%s = %s, ", field.getName(), field.get(obj).toString()));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        if (fields.length>0) sb.delete(sb.length()-2,sb.length());
        System.out.println(sb);                
    }


}
