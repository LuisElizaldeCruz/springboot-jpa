package com.luis.curso.springboot.jpa.springbootjpa;

import com.luis.curso.springboot.jpa.springbootjpa.dto.PersonDto;
import com.luis.curso.springboot.jpa.springbootjpa.entities.Person;
import com.luis.curso.springboot.jpa.springbootjpa.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

    @Autowired
    private PersonRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //findOne();
        //create();
        //update();
        //delete();
        //delete2();
        //personalizeQueries2();
        //personalizedQueriesDistinct();
        //personalizedQueriesConcatUpperAndLowerCase();
        //personalizedQueriesBetween();
        queriesFunctionAgregation();
    }

    @Transactional(readOnly = true)
    public void queriesFunctionAgregation() {

        System.out.println("============ consulta con el total de registros de la tabla persona ============");
        Long count = repository.getTotalPerson();
        System.out.println(count);

        System.out.println("============ consulta con el valor minimo del id ============");
        Long min = repository.getMinId();
        System.out.println(min);

        System.out.println("============ consulta con el valor maximo del id ============");
        Long max = repository.getMaxId();
        System.out.println(max);

        System.out.println("============  consulta con el nombre y su largo ============ ");
        List<Object[]> regs = repository.getPersonNameLength();
        regs.forEach(reg -> {
            String name = (String) reg[0];
            Integer length = (Integer) reg[1];
            System.out.println("name= " + name + " length= " + length);
        });

        System.out.println("============ consulta con el nombre mas corto ============");
        Integer minLengthName = repository.getMinLengthName();
        System.out.println(minLengthName);

        System.out.println("============ consulta con el nombre mas largo ============");
        Integer maxLengthName = repository.getMaxLengthName();
        System.out.println(maxLengthName);

    }

    @Transactional(readOnly = true)
    public void personalizedQueriesBetween() {
        System.out.println("============ consulta por rangos de ID============");
        List<Person> persons = repository.findByidBetween(2L, 5L);
        persons.forEach(System.out::println);

        System.out.println("============ consulta por rangos de caracteres entre j y p ============");
        persons = repository.findAllBetweenName("J", "Q");
        persons.forEach(System.out::println);

        persons = repository.getAllOrder();
        persons.forEach(System.out::println);

    }

    @Transactional(readOnly = true)
    public void personalizedQueriesConcatUpperAndLowerCase() {
        System.out.println("============ Consulta de nombres y apellidos de personas ============");
        List<String> names = repository.findAllFullNameConcat();
        names.forEach(name -> System.out.println(name));

        System.out.println("============ Consulta de nombres y apellidos de personas en minuscula ============");
        names = repository.findAllFullNameLower();
        names.forEach(name -> System.out.println(name));

        System.out.println("============ Consulta de nombres y apellidos de personas en mayuscula ============");
        names = repository.findAllFullNameUpper();
        names.forEach(name -> System.out.println(name));

    }

    @Transactional(readOnly = true)
    public void personalizedQueriesDistinct() {
        System.out.println("============ Consulta con  nombre de personas ============");
        List<String> names = repository.findAllNames();
        names.forEach(System.out::println);

        System.out.println("============ consultas con nombres unicos ============");
        names = repository.findAllNamesDistinct();
        names.forEach(System.out::println);

        System.out.println("============ consultas con lenguajes de programacion unicos ============");
        List<String> progLanguages = repository.findAllProgrammingLanguagesDistinct();
        progLanguages.forEach(pl -> System.out.println(pl));

    }

    @Transactional(readOnly = true)
    public void personalizeQueries2() {
        System.out.println("============ Consulta por objeto persona y lenguaje de programacion ============");
        List<Object[]> personRegs = repository.findAllMixPerson();

        personRegs.forEach(reg -> {
            System.out.println("Programing lenguage" + reg[1] + ", person=" + reg[0]);
        });
        System.out.println("Consulta que puebla y devuelve objeto entity de una instancia personalizada");
        List<Person> persons = repository.findAllObjectPersonPersonalized();
        persons.forEach(per -> System.out.println(per));

        System.out.println("Consulta que puebla y devuelve objeto dto de una clase personalizada");
        List<PersonDto> personDto = repository.findAllPersonDto();
        personDto.forEach(System.out::println);//metodo por referencia
    }


    @Transactional(readOnly = true)
    public void personalizeQueries() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("============ Consulta solo el nombre por el id ============");
        System.out.println("Ingrese el id ");
        Long id = scanner.nextLong();
        ///scanner.close();
        System.out.println("============ Mostrando solo el nombre ============");
        String name = repository.getNameById(id);
        System.out.println(name);

        System.out.println("============ Mostrando solo el id ============");
        Long idDb = repository.getIdById(id);
        System.out.println(idDb);

        System.out.println("============ Mostrando el nombre completo con concat ============");
        String fullName = repository.getFullNameById(id);
        System.out.println(fullName);

        System.out.println("============ Consulta por campos personalizados por el id ============");
        Object[] personReg = (Object[]) repository.obtenerPersonDataById(id);
        System.out.println("id=" + personReg[0] + ", nombre= " + personReg[1] + ", apellido=" + personReg[2] + ", lenguaje=" + personReg[3]);

        System.out.println("============ Consulta por campos personalizados lista ============");
        List<Object[]> regs = repository.obtenerPersonDataList();
        regs.forEach(reg -> System.out.println("id=" + reg[0] + ", nombre= " + reg[1] + ", apellido=" + reg[2] + ", lenguaje=" + reg[3]));
    }

    @Transactional
    public void delete() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el id a eliminar");
        Long id = scanner.nextLong();
        repository.deleteById(id);
        repository.findAll().forEach(System.out::println);
        scanner.close();
    }

    @Transactional
    public void delete2() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el id a eliminar");
        Long id = scanner.nextLong();

        Optional<Person> optionalPerson = repository.findById(id);

        //equivalente a el if
        optionalPerson.ifPresentOrElse(person -> repository.delete(person),
                () -> System.out.println("Lo sentimos no existe la persona con ese id"));
        repository.deleteById(id);
        repository.findAll().forEach(System.out::println);
        scanner.close();
    }

    @Transactional
    public void update() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el id de la persona: ");
        Long id = scanner.nextLong();

        Optional<Person> optionalPerson = repository.findById(id);

        //comprueva si existe
        //   optionalPerson.ifPresent(person -> {
        if (optionalPerson.isPresent()) {
            Person personDB = optionalPerson.get();

            System.out.println(personDB);
            System.out.println("Ingrese el lenguaje de programación");
            String programmingLanguage = scanner.next();
            personDB.setProgrammingLanguage(programmingLanguage);
            Person personUpdated = repository.save(personDB);
            System.out.println(personUpdated);
        } else {
            System.out.println("El usuario no existe");
        }
        //);
        scanner.close();
    }

    @Transactional
    public void create() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre:");
        String name = scanner.next();
        System.out.println("Ingrese el apellido:");
        String lastName = scanner.next();
        System.out.println("Ingrese el lenguaje de programacióñ:");
        String programmingLanguage = scanner.next();
        scanner.close();
        Person person = new Person(null, name, lastName, programmingLanguage);

        Person personNew = repository.save(person);
        System.out.println(personNew);

        repository.findById(personNew.getId()).ifPresent(p -> System.out.println(person));
    }

    @Transactional(readOnly = true)
    public void findOne() {
/*
		Person person = null;
		Optional<Person> optionalPerson = repository.findById(1L);
		if(optionalPerson.isPresent()) {
			person = optionalPerson.get();
		}
		System.out.println(person);
		*/
        repository.findByNameContaining("se").ifPresent(person -> System.out.println(person));
    }

    @Transactional(readOnly = true)
    public void list() {
        //List<Person> persons = (List<Person>) repository.findAll();
        //List<Person> persons = (List<Person>) repository.findByProgrammingLanguage("JavaScript");
        //List<Person> persons = (List<Person>) repository.buscarByProgrammingLanguage("Java","Andres");
        List<Person> persons = (List<Person>) repository.findByProgrammingLanguageAndName("Java", "Andres");

        persons.stream().forEach(person -> System.out.println(person));

        List<Object[]> personsValues = repository.obtenerPersonData();
        personsValues.stream().forEach(person -> System.out.println(person[0] + " es experto en " + person[1]));
    }
}
