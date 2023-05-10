import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
public class Application {
    public static void main(String[] args) {
        /*Условия домашки
        Продолжим работать с базой данных наших работников.
        1. Приведите оба класса (Employee и City) к требованиям Entity.
        2. Свяжите сущности между собой так, чтобы из одного города могло быть несколько сотрудников,
            а один сотрудник — только из одного города.
        3. Включите полную каскадность.
        4. Выберите Fetch Type и объясните свое решение в комментарии к ДЗ.
        5. Сформируйте слой DAO для сущности City с необходимыми CRUD-методами.
        6. Создайте объект City и несколько объектов Employee. Укажите сотрудников в объекте City.
        Сохраните город и убедитесь, что сотрудники тоже сохранились в базе данных.
        7. Замените одного из сотрудников в городе, обновите сущность в базе данных и убедитесь, что сотрудник изменился в БД.
        8. Удалите экземпляр City  из базы данных и убедитесь, что и город, и ссылающиеся на него сотрудники удалены.
        */

        task_HomeWork_3_6();

        //Тестирование методов, реализованных в EmployeeDAOIml
        //task_EmployeeDAOimpl();

        //Настройка Hibernate для Employee
        //task_dbEmployeeCheck();

        //Тестирование методов, реализованных в CityDAOIml
        //task_CityDAOimpl();

        //Настройка Hibernate для City
        //task_dbCityCheck();
        //checkEmployeeManyToOne();
    }
    public static void task_HomeWork_3_6() {
        EntityManager entityManager = JPAUtility.getEntityManager();
        //Создание нового города
        CityOneToMany city_1 = new CityOneToMany();
        city_1.setCityName("Kazan");
        entityManager.getTransaction().begin();
        entityManager.persist(city_1);
        entityManager.getTransaction().commit();

        CityOneToMany temporary = new CityOneToMany();
        entityManager.getTransaction().begin();
        temporary = entityManager.find(CityOneToMany.class, new Integer(6));
        entityManager.getTransaction().commit();

        //Создание нового сотрудника № 1
        EmployeeManyToOne newEmployee_1 = new EmployeeManyToOne();
        newEmployee_1.setFirstName("имя_3");
        newEmployee_1.setLastName("фамилия_3");
        newEmployee_1.setGender("м");
        newEmployee_1.setAge(26);
        newEmployee_1.setCity(temporary);
        entityManager.getTransaction().begin();
        entityManager.persist(newEmployee_1);
        entityManager.getTransaction().commit();

        //Создание нового сотрудника № 2
        EmployeeManyToOne newEmployee_2 = new EmployeeManyToOne();
        newEmployee_2.setFirstName("имя_4");
        newEmployee_2.setLastName("фамилия_4");
        newEmployee_2.setGender("ж");
        newEmployee_2.setAge(26);
        newEmployee_2.setCity(temporary);
        entityManager.getTransaction().begin();
        entityManager.persist(newEmployee_2);
        entityManager.getTransaction().commit();

        CityOneToMany temporary_1 = new CityOneToMany();
        entityManager.getTransaction().begin();
        temporary_1 = entityManager.find(CityOneToMany.class, new Integer(6));
        entityManager.getTransaction().commit();

        //Добавление сотрудников в City
        temporary_1.employeeList.add(newEmployee_1);
        temporary_1.employeeList.add(newEmployee_2);
        entityManager.getTransaction().begin();
        entityManager.merge(temporary_1);
        entityManager.getTransaction().commit();

        //Вывод в консоль. Проверка, что сотрудники добавились вместе с городом
        entityManager.getTransaction().begin();
        String cityQuery = "from CityOneToMany where city_id = 27";
        TypedQuery<CityOneToMany> queryCity = entityManager.createQuery(cityQuery, CityOneToMany.class);
        List<CityOneToMany> cities = queryCity.getResultList();
        entityManager.getTransaction().commit();
        for (CityOneToMany cityOneToMany_1: cities) {
            System.out.println("Город: " + cityOneToMany_1.getCityName() +
                    " ID города: " + cityOneToMany_1.getCityId() +
                    "-----" + cityOneToMany_1.getEmployeeList().toString());
        }

        EmployeeManyToOne employeeManyToOne = new EmployeeManyToOne();
        entityManager.getTransaction().begin();
        employeeManyToOne = entityManager.find(EmployeeManyToOne.class, new Integer(55));
        entityManager.getTransaction().commit();

        //Замена, обновление и проверка замены сотрудника
        temporary_1.employeeList.remove(0);
        temporary_1.employeeList.add(newEmployee_2);
        temporary_1.employeeList.add(employeeManyToOne);
        entityManager.getTransaction().begin();
        entityManager.merge(temporary_1);
        entityManager.getTransaction().commit();
        //Вывод в консоль. Проверка, что список сотрудников изменился
        entityManager.getTransaction().begin();
        cityQuery = "from CityOneToMany where city_id = 27";
        queryCity = entityManager.createQuery(cityQuery, CityOneToMany.class);
        cities = queryCity.getResultList();
        entityManager.getTransaction().commit();
        for (CityOneToMany cityOneToMany_1: cities) {
            System.out.println("Город: " + cityOneToMany_1.getCityName() +
                    " ID города: " + cityOneToMany_1.getCityId() +
                    "-----" + cityOneToMany_1.getEmployeeList().toString());
        }

        //Удаление города и ссылающихся на него сотрудников
        entityManager.getTransaction().begin();
        entityManager.remove(temporary_1);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        //String jpqlQuery = "from EmployeeManyToOne where city = :city";
        String jpqlQuery = "from EmployeeManyToOne";
        TypedQuery<EmployeeManyToOne> query = entityManager.createQuery( jpqlQuery, EmployeeManyToOne.class);
        //query.setParameter("city", city_3);
        List<EmployeeManyToOne> employees = query.getResultList();
        entityManager.getTransaction().commit();
        for (EmployeeManyToOne employee : employees) {
            System.out.println("ID сотрудника : " + employee.getId() +
                    " \tимя: " + employee.getFirstName() + "" + " фамилия: " + employee.getLastName() +
                    " пол: " + employee.getGender() + " возраст: " + employee.getAge() +
                    " город: " + employee.getCity().getCityName() +
                    " ID города: " + employee.getCity().getCityId());
        }

        entityManager.getTransaction().begin();
        String cityQuery_1 = "from CityOneToMany";
        TypedQuery<CityOneToMany> queryCity_1 = entityManager.createQuery(cityQuery_1, CityOneToMany.class);
        List<CityOneToMany> cities_1 = queryCity_1.getResultList();
        entityManager.getTransaction().commit();
        // Выводим информацию о городах в консоль
        for (CityOneToMany cityOneToMany_1: cities_1) {
            System.out.println(cityOneToMany_1);
        }
        entityManager.close();
        JPAUtility.close();
    }
    public static void checkEmployeeManyToOne() {
        System.out.println("Проверка работы БД Employee");
        EntityManager entityManager = JPAUtility.getEntityManager();

        entityManager.getTransaction().begin();
        String jpqlQuery = "from EmployeeManyToOne";
        TypedQuery<EmployeeManyToOne> query = entityManager.createQuery(jpqlQuery, EmployeeManyToOne.class);
        List<EmployeeManyToOne> employees = query.getResultList();
        entityManager.getTransaction().commit();
        System.out.println("Таблица employee и связанная с ней таблица city");
        // Выводим информацию о сотрудниках и городах в консоль
        for (EmployeeManyToOne employee : employees) {
            System.out.println("ID сотрудника : " + employee.getId() +
                    " \tимя: " + employee.getFirstName() + "" + " фамилия: " + employee.getLastName() +
                    " пол: " + employee.getGender() + " возраст: " + employee.getAge() +
                    " город: " + employee.getCity().getCityName() +
                    " ID города: " + employee.getCity().getCityId());
        }
        /*entityManager.getTransaction().begin();
        CityOneToMany city_3 = new CityOneToMany(2,"Казань");
        jpqlQuery = "from EmployeeManyToOne where city = :city";
        query = entityManager.createQuery( jpqlQuery, EmployeeManyToOne.class);
        query.setParameter("city", city_3);
        employees = query.getResultList();
        entityManager.getTransaction().commit();
        for (EmployeeManyToOne employee : employees) {
            System.out.println("ID сотрудника : " + employee.getId() +
                    " \tимя: " + employee.getFirstName() + "" + " фамилия: " + employee.getLastName() +
                    " пол: " + employee.getGender() + " возраст: " + employee.getAge() +
                    " город: " + employee.getCity().getCityName() +
                    " ID города: " + employee.getCity().getCityId());
        }*/

        entityManager.getTransaction().begin();
        String cityQuery = "from CityOneToMany";
        TypedQuery<CityOneToMany> queryCity = entityManager.createQuery(cityQuery, CityOneToMany.class);
        List<CityOneToMany> cities = queryCity.getResultList();
        entityManager.getTransaction().commit();
        // Выводим информацию о городах в консоль
        for (CityOneToMany cityOneToMany: cities) {
            System.out.println("Город: " + cityOneToMany.getCityName() +
                    " ID города: " + cityOneToMany.getCityId() +
                    "-----" + cityOneToMany.getEmployeeList().toString());
        }

        entityManager.close();
        JPAUtility.close();
    }

    public static void task_EmployeeDAOimpl() {
        /*  1.Добавьте зависимость hibernate-core версии 6.2.1.Final в файл pom.xml.
            2.Замените в классе Employee поле city с типа City на тип int.
            3.Приведите класс Employee ко всем критериям Entity. Используйте все необходимые аннотации.
            4.Создайте конфигурационный файл persistence.xml в каталоге src/main/resources/META-INF.
            5.Реализуйте класс со статическим методом чтения конфигурационного файла.
            6.Скорректируйте в интерфейсе EmployeeDAO методы удаления и изменения, они должны принимать объект типа
              Employee.
            7.Измените методы класса EmployeeDAOImpl так, чтобы они реализовывались через Hibernate.
            8.Измените класс Application так, чтобы он реализовывался через Hibernate.
        */
        EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();

        EntityManager entityManager = JPAUtility.getEntityManager();

        System.out.println("persist() Создание (добавление) сущности Employee в таблицу");
        employeeDAO.createEmployee(entityManager, "Семен", "Семенов", "муж", 55, 3);

        System.out.println("\n find() Получение конкретного объекта Employee по id");
        Employee employee = employeeDAO.getEmployeeById(entityManager, 20);
        printEmployee(employee);


        System.out.println("\njpql- запрос Получение списка всех объектов Employee из базы");
        List<Employee> employeeList = employeeDAO.getAllEmployees(entityManager);
        for (Employee employee_1 : employeeList) {
            printEmployee(employee_1);
        }

        System.out.println("\nmerge() Изменение конкретного объекта Employee в базе по id.");
        Employee employeeToUpdate = new Employee(20, "Вера", "Козлова", "жен", 42, 3);
        employeeDAO.updateEmployeeById(entityManager, employeeToUpdate.getId(), employeeToUpdate);
        Employee employeeUpdated = employeeDAO.getEmployeeById(entityManager, employeeToUpdate.getId());
        printEmployee(employeeUpdated);

        System.out.println("\nremove() Удаление конкретного объекта Employee из базы по id.");
        Employee employeeToDelete = employeeList.get(6);
        employeeDAO.deleteEmployeeById(entityManager, employeeToDelete.getId(), employeeToDelete);

        System.out.println("\nПолучение списка всех объектов Employee из базы");
        employeeList = employeeDAO.getAllEmployees(entityManager);
        for (Employee employee_2 : employeeList) {
            printEmployee(employee_2);
        }
        entityManager.close();
        JPAUtility.close();
    }
    public static void task_dbEmployeeCheck() {
        System.out.println("Проверка работы БД Employee");
        //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myUnit");
        //EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManager entityManager = JPAUtility.getEntityManager();

        // Начинаем транзакцию
        entityManager.getTransaction().begin();

        // Создаем JPQL-запрос для выборки всех служащих
        String jpqlQuery = "SELECT e FROM Employee e";

        // Создаем объект запроса с указанием типа возвращаемого результата
        // (Employee.class)
        TypedQuery<Employee> query = entityManager.createQuery(jpqlQuery, Employee.class);

        // Выполняем запрос и получаем результат в виде списка сотрудников
        List<Employee> employees = query.getResultList();

        // Завершаем транзакцию
        entityManager.getTransaction().commit();

        // Выводим информацию о сотрудниках в консоль
        for (Employee employee : employees) {
            printEmployee(employee);
        }

        entityManager.close();
        //entityManagerFactory.close();
        JPAUtility.close();
    }
    public static void task_dbCityCheck() {
        System.out.println("Проверка работы БД City");
        EntityManager entityManager = JPAUtility.getEntityManager();

        entityManager.getTransaction().begin();
        String jpqlQuery = "SELECT c FROM City c";
        TypedQuery<City> query = entityManager.createQuery(jpqlQuery, City.class);
        List<City> cities = query.getResultList();
        entityManager.getTransaction().commit();

        for (City city: cities) {
            printCity(city);
        }

        entityManager.close();
        JPAUtility.close();
    }
    public static void task_CityDAOimpl() {
        CityDAOImpl cityDAOImpl = new CityDAOImpl();

        EntityManager entityManager = JPAUtility.getEntityManager();

        //System.out.println("persist() Создание (добавление) сущности City в таблицу");
        //cityDAOImpl.createCity(entityManager, "Владивосток");

        //System.out.println("\n find() Получение конкретного объекта City по id");
        //City city = cityDAOImpl.getCityById(entityManager, 1);
        //printCity(city);

        System.out.println("\njpql- запрос Получение списка всех объектов City из БД");
        List<City> cityList = cityDAOImpl.getAllCities(entityManager);
        for (City city_1: cityList) {
            printCity(city_1);
        }

        /*System.out.println("\nmerge() Изменение конкретного объекта City в базе по id.");
        City cityToUpdate = new City(13, "Воркута");
        cityDAOImpl.updateCityById(entityManager, cityToUpdate.getCityId(), cityToUpdate);
        City cityUpdated = cityDAOImpl.getCityById(entityManager, cityToUpdate.getCityId());
        printCity(cityUpdated);*/

        //System.out.println("\nremove() Удаление конкретного объекта City из базы по id.");
        //City cityToDelete = cityList.get(7);
        //cityDAOImpl.deleteCityById(entityManager, cityToDelete.getCityId(), cityToDelete);

        System.out.println("\nПолучение списка всех объектов City из базы");
        List<City> cityList_2 = cityDAOImpl.getAllCities(entityManager);
        for (City city_2: cityList_2) {
            printCity(city_2);
        }

        entityManager.close();
        JPAUtility.close();
    }

    public static void printEmployee(Employee employee) {
        if(employee == null) {
            System.out.println("Сотрудника с таким ID нет в базе данных");
        } else {
        System.out.println("ID сотрудника : " + employee.getId() +
                " \tимя: " + employee.getFirstName() + "" + " фамилия: " + employee.getLastName() +
                " пол: " + employee.getGender() + " возраст: " + employee.getAge() +
                " ID города: " + employee.getCityId());
        }
    }
    public static void printCity(City city) {
        if(city == null) {
            System.out.println("Города с таким ID нет в базе данных");
        } else {
            System.out.println("Название города: " + city.getCityName() +
                    ", ID города: " + city.getCityId());
        }
    }

}
