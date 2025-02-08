package brainacad.org;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class TestMaven {
    public static void main(String[] args) {

        Properties prop = new Properties();
        try(InputStream resourceAsStream = TestMaven.class.getClassLoader().getResourceAsStream("config.properties")){
            prop.load(Objects.requireNonNull(resourceAsStream));
            prop.getProperty("props.mvn.hello");
            System.out.println(prop.getProperty("props.mvn.hello"));
        } catch(IOException e){
            System.err.println("Error");
        }

        List<String> fruits = Lists.newArrayList("orange", "banana", "kiwi");
        System.out.println("Original list:");
        fruits.forEach(System.out::println);

        List<String> reverseFruits = Lists.reverse(fruits);
        System.out.println("\nReversed list:");
        reverseFruits.forEach(System.out::println);

        Multimap<String, String> map = ArrayListMultimap.create();
        map.put("key", "firstValue");
        map.put("key", "secondValue");

        System.out.println("\nMultimap content:");
        System.out.println(map);
    }
}
