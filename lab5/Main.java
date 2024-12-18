import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ElevatorManager controller = new ElevatorManager(2); // 2 лифта
        controller.start();

        Random random = new Random();
        for (int i = 0; i < 6; i++) { // генерация заявок
            int startFloor = random.nextInt(10) + 1;
            int endFloor = startFloor + random.nextInt(9) + 1;

            // вывод в консоль ИНФО О ЗАЯВКЕ
            System.out.println("Заявка: с этажа " + startFloor + " на этаж " + endFloor + " принята от пассажира");
            controller.addTask(new Task(startFloor, endFloor));

            try {
                Thread.sleep(random.nextInt(1000) + 500); // небольшая задержка
            } catch (InterruptedException e) {
                System.err.println("Ожидание было прервано. Поток: " + Thread.currentThread().getName());
                Thread.currentThread().interrupt();
            }
        }

        controller.stop();
    }
}
