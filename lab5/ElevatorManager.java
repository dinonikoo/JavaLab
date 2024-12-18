import java.util.ArrayList;
import java.util.List;

public class ElevatorManager {
    private final List<Elevator> elevators = new ArrayList<>();
    private final List<Thread> elevatorThreads = new ArrayList<>();
    private volatile boolean running = true;

    public ElevatorManager(int numberOfElevators) {
        for (int i = 0; i < numberOfElevators; i++) {
            Elevator elevator = new Elevator(i + 1);
            elevators.add(elevator);
            elevatorThreads.add(new Thread(elevator));
        }
    }

    public void start() {
        for (Thread thread : elevatorThreads) {
            thread.start();
        }
    }

    public void stop() {
        running = false;
        for (Elevator elevator : elevators) {
            elevator.stop();
        }
        for (Thread thread : elevatorThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Ошибка: поток " + thread.getName() + " был прерван.");
                Thread.currentThread().interrupt();
            }
        }
    }

    public void addTask(Task task) {
        Elevator bestElevator = pickElevator(task);
        if (bestElevator != null) {
            bestElevator.addTask(task);
        }
    }

    private Elevator pickElevator(Task task) {
        Elevator bestElevator = null;
        int minCost = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            int cost = calculateCost(elevator, task);
            cost += elevator.waitingQueue.size() + elevator.stopsQueue.size();

            if (cost < minCost) {
                minCost = cost;
                bestElevator = elevator;
            }
        }
        System.out.println("Заявка '" + task + "' назначена лифту " + bestElevator.id);
        return bestElevator;
    }

    private int calculateCost(Elevator elevator, Task task) {
        if ((elevator.movingUp == task.directionUp &&
                ((elevator.movingUp && elevator.currentFloor <= task.startFloor) ||
                        (!elevator.movingUp && elevator.currentFloor >= task.startFloor)))) {
            return Math.abs(elevator.currentFloor - task.startFloor);
        }
        return Math.abs(elevator.currentFloor - task.startFloor) + 10;
    }
}
