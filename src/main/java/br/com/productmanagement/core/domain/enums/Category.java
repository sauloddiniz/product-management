package br.com.productmanagement.core.domain.enums;

import br.com.productmanagement.core.exception.InvalidCategoryExecption;

public enum Category {
    FOOD("food"),
    CLOTHES("clothes"),
    ELECTRONICS("electronics"),
    OTHER("other");

    private final String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Category fromDescription(final String description) {
        for (Category category : Category.values()) {
            if (category.getDescription().equalsIgnoreCase(description)) {
                return category;
            }
        }
        throw new InvalidCategoryExecption(description);
    }
}
