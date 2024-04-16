package com.luis.curso.springboot.jpa.springbootjpa;

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
         create();
        //update();
        //delete();
        //delete2();
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
