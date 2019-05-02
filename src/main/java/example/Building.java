package example;

import io.micronaut.context.annotation.Prototype;
import lombok.Data;

import javax.inject.Inject;

@Prototype
public class Building {

    @Inject Room room;

    public void printRoom(){
        System.out.println("room = " + room);
    }
}
