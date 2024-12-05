import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Algo {
    private URL url;
    HttpURLConnection connection;

    // конструктор принимает строку URL
    public Algo(String urlString) throws IOException {
        try {
            this.url = new URL(urlString);  // Преобразуем строку в объект URL
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            System.out.println("Response code: " + responseCode);
        } catch (MalformedURLException e) {
            System.out.println("Ошибка: Неверный формат URL - " + e.getMessage());
            throw e;
        } catch (IOException e) {
            System.out.println("Ошибка при открытии соединения - " + e.getMessage());
            throw e;
        }
    }

    public List<Company> getListCompanies() throws IOException {
        List<Company> companies = new ArrayList<>();

        try {
            InputStream in = url.openStream();
            int code = connection.getResponseCode();

            if (code < 200 || code >= 300) {
                System.out.println("Ошибка: Неправильный HTTP-статус: " + code);
                return companies;
            }

            InputStream os = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(os);
            BufferedReader br = new BufferedReader(isr);

            String theLine;
            int id = 0;
            String name = "";
            String address = "";
            String zip = "";
            String country = "";
            int employeeCount = 0;
            String indusrty = "";
            long marketCap = 0;
            String domain = "";
            String logo = "";
            String ceoName = "";
            int counter = 0;

            while ((theLine = br.readLine()) != null) {
                // парсим вручную
                int index = theLine.indexOf(":") + 2;
                if (index - 2 == -1) {
                    continue;
                }

                String temp = "";
                if (index < theLine.length() && theLine.charAt(index) == '"') {
                    index = index + 1;
                }
                while (index < theLine.length() && theLine.charAt(index) != '"' && theLine.charAt(index) != ',') {
                    temp = temp + theLine.charAt(index);
                    index = index + 1;
                }

                if (!temp.isEmpty()) {
                    try {
                        switch (counter) {
                            case 0:
                                id = Integer.parseInt(temp);
                                break;
                            case 1:
                                name = temp;
                                break;
                            case 2:
                                address = temp;
                                break;
                            case 3:
                                zip = temp;
                                break;
                            case 4:
                                country = temp;
                                break;
                            case 5:
                                employeeCount = Integer.parseInt(temp);
                                break;
                            case 6:
                                indusrty = temp;
                                break;
                            case 7:
                                marketCap = Long.parseLong(temp);
                                break;
                            case 8:
                                domain = temp;
                                break;
                            case 9:
                                logo = temp;
                                break;
                            case 10:
                                ceoName = temp;
                                companies.add(new Company(id, name, address, zip, country, employeeCount, indusrty, marketCap, domain, logo, ceoName));
                                break;
                        }
                        counter++;
                        if (counter > 10) {
                            counter = 0;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка преобразования строки в число: " + temp + " - " + e.getMessage());
                    }
                }
            }

            connection.disconnect();
        } catch (MalformedURLException e) {
            System.out.println("Ошибка: Неверный формат URL - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка при чтении данных - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Неожиданная ошибка: " + e.getMessage());
        }

        return companies;
    }

    public static void printCompanies(List<Company> companies) {
        for (int i = 0; i < companies.size(); i++) {
            System.out.println("ID: " + companies.get(i).id);
            System.out.println("Name: " + companies.get(i).name);
            System.out.println("Address: " + companies.get(i).address);
            System.out.println("Zip: " + companies.get(i).zip);
            System.out.println("Country: " + companies.get(i).country);
            System.out.println("Employee Count: " + companies.get(i).employeeCount);
            System.out.println("Market Cap.: " + companies.get(i).marketCap);
            System.out.println("Domain: " + companies.get(i).domain);
            System.out.println("Logo: " + companies.get(i).logo);
            System.out.println("CEO Name: " + companies.get(i).ceoName);
            System.out.println();
        }
    }
}
