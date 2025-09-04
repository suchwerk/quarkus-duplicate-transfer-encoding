package org.acme;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GreetingGenerator {

    private static final String[] FIRST_NAMES = {
            "Alice", "Bob", "Charlie", "Diana", "Ethan", "Fiona",
            "George", "Hannah", "Ivan", "Julia", "Kevin", "Laura",
            "Michael", "Nina", "Oscar", "Paula", "Quentin", "Rachel",
            "Sam", "Tina", "Umar", "Vera", "William", "Xenia", "Yuri", "Zara"
    };

    private static final String[] LAST_NAMES = {
            "Anderson", "Brown", "Clark", "Davis", "Evans", "Franklin",
            "Garcia", "Harris", "Ivanov", "Johnson", "Kumar", "Lopez",
            "Miller", "Novak", "Olsen", "Peterson", "Quinn", "Roberts",
            "Smith", "Taylor", "Usmanov", "Valdez", "Williams", "Xu",
            "Young", "Zimmerman"
    };

    private static final Random RANDOM = new Random();

    private static String randomFirstName() {
        return FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)];
    }

    private static String randomLastName() {
        return LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
    }

    public static List<Greeting> generateJsonPayload(int targetSizeInKB)  {
        ObjectMapper mapper = new ObjectMapper();
        List<Greeting> greetings = new ArrayList<>();

        int targetBytes = targetSizeInKB * 1024;
        int currentSize = 0;

        while (currentSize < targetBytes) {
            Greeting g = new Greeting();
            g.id = UUID.randomUUID().toString();
            g.name = randomFirstName();
            g.lastname = randomLastName();
            greetings.add(g);
            // very inefficient but i don't mind
            // check size
            try {
                byte[] json = mapper.writeValueAsBytes(greetings);
                currentSize = json.length;    
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            
        }

        return greetings;
    }

}
