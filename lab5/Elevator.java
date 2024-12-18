import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Elevator implements Runnable {
    final int id;
    int currentFloor = 1;
    boolean movingUp = true;
    final Queue<Integer> stopsQueue = new ConcurrentLinkedQueue<>();
    final Queue<Task> waitingQueue = new ConcurrentLinkedQueue<>();
    private boolean running = true;

    public Elevator(int id) {
        this.id = id;
    }

    public void addTask(Task task) {
        synchronized (waitingQueue) {
            waitingQueue.add(task);
            waitingQueue.notify();
        }
    }

    public void stop() {
        running = false;
        synchronized (waitingQueue) {
            waitingQueue.notify();
        }
    }

    @Override
    public void run() {
        while (running) {
            if (stopsQueue.isEmpty() && waitingQueue.isEmpty()) {
                synchronized (waitingQueue) {
                    try {
                        waitingQueue.wait();
                    } catch (InterruptedException e) {
                        System.err.println("Поток лифта " + id + " был прерван.");
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
            processStops();
        }
    }

    private void processStops() {
        while (!stopsQueue.isEmpty()) {
            int targetFloor = stopsQueue.poll();
            System.out.println("Лифт " + id + " движется к этажу " + targetFloor);
            moveToFloor(targetFloor);
            System.out.println("Лифт " + id + " ПРИБЫЛ НА ЭТАЖ " + currentFloor);
        }
        processWaitingRequests();
    }

    private void processWaitingRequests() {
        synchronized (waitingQueue) {
            Iterator<Task> iterator = waitingQueue.iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();

                if (task.startFloor == currentFloor) {
                    stopsQueue.add(task.endFloor);
                    iterator.remove();
                } else if (stopsQueue.isEmpty()) {
                    stopsQueue.add(task.startFloor);
                }
            }
        }
        if (!stopsQueue.isEmpty()) {
            processStops();
        }
    }

    private void moveToFloor(int targetFloor) {
        while (currentFloor != targetFloor) {
            if (currentFloor < targetFloor) {
                currentFloor++;
                movingUp = true;
            } else {
                currentFloor--;
                movingUp = false;
            }

            System.out.println("Лифт " + id + " на этаже " + currentFloor);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println("Поток лифта " + id + " был прерван.");
                Thread.currentThread().interrupt();
            }
            synchronized (waitingQueue) {
                checkIntermediate();
            }
        }
    }

    private void checkIntermediate() {
        Iterator<Task> iterator = waitingQueue.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task.startFloor == currentFloor && task.directionUp == movingUp) {
                stopsQueue.add(task.endFloor);
                iterator.remove();
            }
        }
    }
}
