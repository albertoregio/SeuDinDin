package br.com.arsolutions.seudindin.viewmodel.categories;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import br.com.arsolutions.seudindin.viewmodel.categories.model.CategoryModel;
import br.com.arsolutions.seudindin.persistence.entity.CategoryEntity;
import br.com.arsolutions.seudindin.persistence.CategoryRepository;
import br.com.arsolutions.seudindin.viewmodel.categories.model.convert.ModelConverter;

import java.util.List;

// Classe responsavel pela comunicacao entre a ui e os dados do negocio
public class CategoryViewModel extends AndroidViewModel {

    // Declaracao de variaveis
    private CategoryRepository repository;
    private CategoryDetailLiveData detailLiveData;
    private CategoryChildrenListLiveData childrenListLiveData;


    // Construtor da classe
    public CategoryViewModel(Application application) {
        super(application);
        repository = new CategoryRepository(application);
        childrenListLiveData = new CategoryChildrenListLiveData(repository);
        detailLiveData = new CategoryDetailLiveData(repository);
        setChildrenListIdInput(0);
    }


    // Insere/Atualiza uma nova categoria
    public int save(CategoryModel categoryModel) {
        return repository.insert(ModelConverter.categoryModelToEntity(categoryModel));
    }


    // Remove uma categoria pelo id
    public void delete(int id) {
        repository.delete(id);
    }


    // Remove uma determinada categoria
    public void delete(CategoryEntity categoryEntity) {
        repository.delete(categoryEntity);
    }


    // Recupera um livedata para o detalhe de uma categoria
    public LiveData<CategoryModel> getCategoryDetailLiveData() {
        return detailLiveData;
    }


    // Atribui o id da listagem de categorias filhas
    public void setCategoryDetailIdInput(Integer id) {
        detailLiveData.setId(id);
    }


    // Recupera um livedata para a lista de categorias filhas
    public CategoryChildrenListLiveData getChildrenListLiveData() {
        return childrenListLiveData;
    }


    // Atribui o id da listagem de categorias filhas
    public void setChildrenListIdInput(Integer id) {
        childrenListLiveData.resetList();
        childrenListLiveData.setId(id);
    }

    public List<CategoryModel> getParentIdList(int id) {
        return ModelConverter.listCategoryChildrenToModel(repository.getParentIdArray(id));
    }

}