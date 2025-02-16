package es.dpm;

import es.dpm.model.Manufacturer;
import es.dpm.repository.ManufacturerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App.class, args);
        var repo = context.getBean(ManufacturerRepository.class);

        repo.saveAll(List.of(
                new Manufacturer("Toyota", 370000, 1937),
                new Manufacturer("Volkswagen", 662575, 1937),
                new Manufacturer("Ford", 186000, 1903),
                new Manufacturer("Honda", 218674, 1948),
                new Manufacturer("General Motors", 155000, 1908),
                new Manufacturer("BMW", 133778, 1916),
                new Manufacturer("Mercedes-Benz", 173000, 1926),
                new Manufacturer("Hyundai", 104731, 1967),
                new Manufacturer("Nissan", 136134, 1933),
                new Manufacturer("Ferrari", 4495, 1939),
                new Manufacturer("Lamborghini", 1804, 1963),
                new Manufacturer("Porsche", 37560, 1931),
                new Manufacturer("Mazda", 48475, 1920),
                new Manufacturer("Subaru", 36974, 1953),
                new Manufacturer("Mitsubishi", 29955, 1970),
                new Manufacturer("Peugeot", 208000, 1810),
                new Manufacturer("Renault", 179565, 1899),
                new Manufacturer("Tesla", 140473, 2003),
                new Manufacturer("Volvo", 99985, 1927),
                new Manufacturer("Kia", 34655, 1944)
        ));


    }

}
