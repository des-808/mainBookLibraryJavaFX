package org.example.models;
//наделал фигни
public class Author {
    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    private int book_id;
    private int id;

    public String getName() {
        return name = firstName + " " + lastName;
    }


    public void setName(String name) {
        this.name = name;
        String[] names = name.split(" ");
        this.firstName = names[0].trim();
        this.lastName = names[1].trim();
    }

    private String name;
    private String firstName;
    private String lastName;

    public Author(String firstName , String lastName) {
        this.firstName = firstName;//имя
        this.lastName = lastName;//фамилия
        this.name = firstName + " " + lastName;
    }
    public Author(int id,  String firstName,String lastName,int book_id) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.name = lastName + " " + firstName;
        this.book_id = book_id;
    }
    public Author(int id, String firstName, String lastName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.name = lastName + " " + firstName;
    }
    public Author() {}

    public Author(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setFirstName(String firstName) { this.firstName = firstName;}

    @Override
    public String toString() {
        return "Author{" +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                "lastName='" + lastName + '\'' +
                '}';
    }
}