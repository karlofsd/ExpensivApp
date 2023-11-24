package core.models;

import java.util.UUID;

public class Model {
    private final UUID id;
    public Model() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}
