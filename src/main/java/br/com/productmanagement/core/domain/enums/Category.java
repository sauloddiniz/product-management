package br.com.productmanagement.core.domain.enums;

import br.com.productmanagement.core.exception.InvalidCategoryExecption;

public enum Category {
    FOOD("food", "comida"),
    CLOTHES("clothes", "roupas"),
    ELECTRONICS("electronics", "eletronicos"),
    OTHER("other", "outro");

    private final String descriptionEn;
    private final String descriptionPt;

    Category(String descriptionEn, String descriptionPt) {
        this.descriptionEn = descriptionEn;
        this.descriptionPt = descriptionPt;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public String getDescriptionPt() {
        return descriptionPt;
    }

    public static Category fromDescription(final String description) {
        for (Category category : Category.values()) {
            if (category.getDescriptionEn().equalsIgnoreCase(description)
                || category.getDescriptionPt().equalsIgnoreCase(description)) {
                return category;
            }
        }
        throw new InvalidCategoryExecption(description);
    }
}