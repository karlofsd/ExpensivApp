package category;

import java.util.UUID;

public class Category implements Comparable<Category> {
    private final UUID id;
    private final String name;
    private String description;

    public Category(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Category(String name, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(Category o) {
        return this.id.compareTo(o.getId());
    }
}
