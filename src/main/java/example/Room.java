package example;

import io.micronaut.context.annotation.Prototype;

import javax.inject.Inject;
import javax.inject.Singleton;

@Prototype
public class Room {

    @Inject MajorDomus majorDomus1,majorDomus2;

    public void printMajorDomus(){
        System.out.println("majorDomus1 = " + majorDomus1);
        System.out.println("majorDomus2 = " + majorDomus2);
    }
}
