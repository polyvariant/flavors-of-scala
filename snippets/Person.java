public final class Person {
    private final String firstName;
    private final String lastName;
    private final int age;
    
    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    
    // Getters (no setters for immutability)
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public int getAge() {
        return age;
    }
    
    // Creating modified instances
    public Person withFirstName(String newFirstName) {
        return new Person(newFirstName, lastName, age);
    }
    
    public Person withLastName(String newLastName) {
        return new Person(firstName, newLastName, age);
    }
    
    public Person withAge(int newAge) {
        return new Person(firstName, lastName, newAge);
    }
    
    // Proper equals() implementation
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Person other = (Person) obj;
        
        if (age != other.age) return false;
        if (firstName != null ? !firstName.equals(other.firstName) : other.firstName != null) return false;
        return lastName != null ? !lastName.equals(other.lastName) : other.lastName != null;
    }
    
    // Proper hashCode() implementation
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + age;
        return result;
    }
    
    // toString() implementation
    public String toString() {
        return "Person{" +
               "firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", age=" + age +
               '}';
    }
}
