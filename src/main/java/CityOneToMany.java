import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "city")
public class CityOneToMany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private int cityId;
    @Column(name = "city_name", length = 50, nullable = false)
    private String cityName;
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "cityOneToMany", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    List<EmployeeManyToOne> employeeList;

    public CityOneToMany() {
    }
    public CityOneToMany(Integer cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "CityOneToMany{" +
                "cityId=" + cityId +
                ", cityName='" + cityName + '\'' +
                ", employeeList=" + employeeList +
                '}';
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<EmployeeManyToOne> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeManyToOne> employeeList) {
        this.employeeList = employeeList;
    }
}
