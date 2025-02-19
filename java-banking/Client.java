import java.io.Serializable;

public class Client implements Serializable {
    private int clientId;
    private String pesel;
    private String name;
    private String surname;
    private String country;
    private String city;
    private String street;

    public Client(int clientId, String pesel, String name, String surname, String country, String city, String street) {
        this.clientId = clientId;
        this.pesel = pesel;
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.city = city;
        this.street = street;
    }

    // Getters
    public int getClientId() {
        return clientId;
    }

    public String getPesel() {
        return pesel;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }
}
