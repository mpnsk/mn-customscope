package example;

import io.micronaut.context.annotation.Prototype;

import javax.inject.Inject;
import javax.inject.Singleton;

@Prototype
public class Room {

    @Inject
    MajorDomus majorDomus1, majorDomus2;
}
