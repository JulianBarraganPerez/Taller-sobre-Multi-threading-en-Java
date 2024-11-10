
package com.mycompany.multi.threading;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelSumExample {
    public static void main(String[] args) {
        // Lista de números para procesar
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Crear un ExecutorService con 2 hilos
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Dividir la lista en dos partes para procesarlas en paralelo
        Callable<Integer> sumFirstHalf = () -> {
            int sum = 0;
            for (int i = 0; i < numbers.size() / 2; i++) {
                sum += numbers.get(i);
            }
            return sum;
        };

        Callable<Integer> sumSecondHalf = () -> {
            int sum = 0;
            for (int i = numbers.size() / 2; i < numbers.size(); i++) {
                sum += numbers.get(i);
            }
            return sum;
        };

        // Ejecutar las dos tareas de suma en paralelo
        Future<Integer> futureFirstHalf = executor.submit(sumFirstHalf);
        Future<Integer> futureSecondHalf = executor.submit(sumSecondHalf);

        try {
            // Obtener los resultados de cada tarea
            int totalSum = futureFirstHalf.get() + futureSecondHalf.get();
            System.out.println("Total sum: " + totalSum);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // Apagar el ExecutorService
            executor.shutdown();
        }
    }
}

