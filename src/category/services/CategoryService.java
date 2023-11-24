package category.services;

import category.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private final List<Category> categories = new ArrayList<>();

    public CategoryService(){
    }

    public List<Category> getAll(){
        return this.categories;
    }

    public void create(Category category){
        this.categories.add(category);
    }

    public void delete(Category category){
        this.categories.remove(category);
    }
}
