import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            Algo test = new Algo("https://fake-json-api.mock.beeceptor.com/companies");
            Algo.printCompanies(test.getListCompanies());
        }
        catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}

