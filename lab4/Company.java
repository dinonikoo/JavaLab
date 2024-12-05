public class Company {
    final int id;
    final String name;
    final String address;
    final String zip;
    final String country;
    final int employeeCount;
    final String indusrty;
    final long marketCap;
    final String domain;
    final String logo;
    final String ceoName;

    public Company(int id, String name, String address, String zip, String country, int employeeCount, String indusrty, long marketCap, String domain, String logo, String ceoName) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.country = country;
        this.employeeCount = employeeCount;
        this.indusrty = indusrty;
        this.marketCap = marketCap;
        this.domain = domain;
        this.logo = logo;
        this.ceoName = ceoName;
    }
}
