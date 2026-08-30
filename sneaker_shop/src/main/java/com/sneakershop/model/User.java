package com.sneakershop.model;

/**
 * User is a plain Java object (POJO) that represents one row in the 'users' table.
 *
 * <p>It holds registration data: username, hashed password, personal details,
 * and the user's role (USER or EMPLOYEE).</p>
 *
 * <p>Java 8 compatible — standard getter/setter pattern.</p>
 */
public class User {

    private int id;
    private String username;
    private String password;    // Stored as "salt:sha256hash"
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String pinCode;
    private String role;        // "USER" or "EMPLOYEE"

    // ===== Constructors =====

    public User() { }

    public User(String username, String password, String firstName, String lastName,
                String address, String city, String pinCode, String role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.pinCode = pinCode;
        this.role = role;
    }

    // ===== Getters and Setters =====

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPinCode() { return pinCode; }
    public void setPinCode(String pinCode) { this.pinCode = pinCode; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}