package edu.polytech.ebudget.categories;

import edu.polytech.ebudget.datamodels.Category;

public interface ICategoryAdapterListner {
    void onClickCategory(Category category, int position);
}
