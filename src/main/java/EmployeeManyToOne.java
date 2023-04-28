import javax.persistence.*;

@Entity
@Table(name = "employee")
public class EmployeeManyToOne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;
    @Column(name = "gender", length = 6, nullable = false)
    private String gender;
    @Column(name = "age", nullable = false)
    private int age;
    //@Column(name = "city_id", nullable = true)
    //private int cityId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private CityOneToMany cityOneToMany;

    public EmployeeManyToOne() {
    }

    /*public Employee(Integer id, String firstName, String lastName, String gender, int age, int cityId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.cityId = cityId;
    }*/

    @Override
    public String toString() {
        return "EmployeeManyToOne{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", city=" + cityOneToMany.getCityName() +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    /*public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }*/

    public CityOneToMany getCity() {
        return cityOneToMany;
    }

    public void setCity(CityOneToMany cityOneToMany) {
        this.cityOneToMany = cityOneToMany;
    }
}

