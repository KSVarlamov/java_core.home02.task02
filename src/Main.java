import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    private static final Predicate<Person> isWorkAge = person -> {
        if (person.getAge() < 18) {
            return false;
        }
        if (person.getSex() == Sex.MAN && person.getAge() > 65) {
            return false;
        }
        return person.getSex() != Sex.WOMAN || person.getAge() <= 60;
    };

    public static void main(String[] args) {
        Collection<Person> persons = generateData();

        long inconsistencyCount = persons.stream()
                .filter(p -> p.getAge() < 18)
                .count();
        System.out.println("Несовершеннолетних: " + inconsistencyCount);

        List<String> inducteeList = persons.stream()
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getAge() <= 27)
                .filter(person -> person.getSex() == Sex.MAN)
                .map(Person::getFamily)
                .collect(Collectors.toList());

        System.out.println("Фамилии призывников: \n" + inducteeList);

        List<Person> workablePersons = persons.stream()
                .filter(isWorkAge)
                .filter(person -> person.getEducation() == Education.HIGHER)
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());

        System.out.println("Список потенциально работоспособных с высшим образованием: \n" + workablePersons);
    }

    private static Collection<Person> generateData() {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        return persons;
    }
}