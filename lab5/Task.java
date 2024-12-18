public class Task {
    int startFloor;
    int endFloor;
    boolean directionUp;

    public Task(int startFloor, int endFloor) {
        this.startFloor = startFloor;
        this.endFloor = endFloor;
        this.directionUp = endFloor > startFloor;
    }

    @Override
    public String toString() {
        return startFloor + " -> " + endFloor + " (" + (directionUp ? "вверх" : "вниз") + ")";
    }
}
