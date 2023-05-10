import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
public class CityDAOImpl implements CityDAO {
    public CityDAOImpl() {
    }

    @Override
    public void createCity(EntityManager entityManager, String cityName) {
        City city = new City();
        city.setCityName(cityName);

        entityManager.getTransaction().begin();
        entityManager.persist(city);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<City> getAllCities(EntityManager entityManager) {
        List<City> cities = new ArrayList<>();

        entityManager.getTransaction().begin();
        String jpqlQuery = "SELECT c FROM City c";
        TypedQuery<City> query = entityManager.createQuery(jpqlQuery, City.class);
        cities = query.getResultList();
        entityManager.getTransaction().commit();
        return cities;
    }

    @Override
    public City getCityById(EntityManager entityManager, int cityId) {
        City city = null;
        entityManager.getTransaction().begin();
        city = entityManager.find(City.class, new Integer(cityId));
        entityManager.getTransaction().commit();
        return city;
    }

    @Override
    public void deleteCityById(EntityManager entityManager, int cityId, City city) {
        entityManager.getTransaction().begin();
        City cityCheck = entityManager.find(City.class, new Integer(cityId));
        if(entityManager.contains(cityCheck)) {
            entityManager.remove(city);
            System.out.println("Сотрудник ID: " + cityId + " удален");
        } else {
            System.out.println("Сотрудник с ID: " + cityId + " не найден в базе данных");
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateCityById(EntityManager entityManager, int cityId, City city) {
        entityManager.getTransaction().begin();
        City cityCheck = entityManager.find(City.class, new Integer(cityId));
        if(entityManager.contains(cityCheck)) {
            entityManager.merge(city);
            System.out.println("Сотрудник ID: " + cityId + " обновлен");
        } else {
            System.out.println("Сотрудник с ID: " + cityId + " не найден в базе данных");
        }
        entityManager.getTransaction().commit();
    }
}
