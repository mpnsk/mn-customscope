package example;

import io.micronaut.context.annotation.Prototype;
import lombok.Data;

import javax.inject.Inject;

@Prototype
public class Building {

    @Inject Room room1,room2;

    public void print(){
        System.out.println("this = " + this);
        System.out.println("room1 = " + room1);
        System.out.println("room1.majorDomus1 = " + room1.majorDomus1);
        System.out.println("room1.majorDomus2 = " + room1.majorDomus2);
        System.out.println("room2 = " + room2);
        System.out.println("room2.majorDomus1 = " + room2.majorDomus1);
        System.out.println("room2.majorDomus2 = " + room2.majorDomus2);
    }
}
