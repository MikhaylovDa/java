package app.order.customer;

public class Customer {
    private final String firstName;
    private final String secondName;
    private final int age;
    private final Address address;
    public static final Customer MATURE_UNKNOWN_CUSTOMER = new Customer("", "", 18, Address.EMPTY_ADDRESS);
    public static final Customer NOT_MATURE_UNKNOWN_CUSTOMER = new Customer("", "", 17, Address.EMPTY_ADDRESS);

    public Customer (String firstName, String secondName, int age, Address address){
        if (age < 1) throw new IllegalArgumentException();
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public int getAge() {
        return age;
    }

    public Address getAddress() {
        return address;
    }
}