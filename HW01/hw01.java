package HW01;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class hw01 {
    public static void main(String[] args) {

        //Напишите программу, которая использует Stream API для обработки списка чисел. Программа должна вывести на экран среднее значение всех четных чисел в списке.

        final Long COUNT = 100000L; // количество элементов в списке
        Random random = new Random(); 
        //генерируем лист чисел из COUNT элементов.
        List<Integer> integers = random.ints(COUNT, 0, 100)
            .mapToObj(Integer::valueOf) 
            .collect(Collectors.toList()); 
        
        //System.out.println(integers); // вывод листа;

        // среднее значение всех четных чисел в списке
        System.out.println(integers.stream()
            .filter(val -> val % 2 == 0)
            .mapToInt(val -> val)
            .average()
            .getAsDouble());
                        

        
    }
}
