import javax.persistence.EntityManager;
import java.util.List;
public interface CityDAO {
    void createCity(EntityManager entityManager, String cityName);
    List<City> getAllCities(EntityManager entityManager);
    City getCityById(EntityManager entityManager, int cityId);
    void deleteCityById(EntityManager entityManager, int cityId, City city);
    void updateCityById(EntityManager entityManager, int cityId, City city);
}
