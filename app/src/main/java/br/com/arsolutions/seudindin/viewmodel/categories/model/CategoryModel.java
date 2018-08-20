package br.com.arsolutions.seudindin.viewmodel.categories.model;

import android.os.Parcel;
import android.os.Parcelable;

import br.com.arsolutions.seudindin.App;
import com.example.regio.seudindin.R;

// Classe que representa o modelo de uma categoria na ui
public class CategoryModel implements Parcelable {

    // Declaracao de variaveis
    private Integer parentId = 0;
    private String parentName = App.getContext().getString(R.string.category_parent_novalue);
    private int id;
    private String colorName = App.getResourceName(R.color.default_color_spinner);
    private String iconName = App.getResourceName(R.drawable.ic_category_icon_empty);
    private String name;

    private boolean highlighted = false;
    private boolean enabled = true;
    private boolean show_symbol_name = false;
    private int childrenCount = 0;

    public CategoryModel() {

    }

    // Metodos GET
    public Integer getParentId() {
        if (parentId != null)
            return parentId;
        else
            return 0;
    }
    public String getParentName() {
        return getParentId() == 0 ? App.getContext().getString(R.string.category_parent_novalue) : parentName;
    }
    public int getId() {
        return id;
    }
    public String getColorName() {
        return colorName;
    }
    public Integer getColor() {
        if (colorName != null)
            return App.getResourceColor(colorName);
        else
            return App.getResourceColor(App.getResourceName(R.color.default_color_spinner));
    }
    public String getIconName() {
        return iconName;
    }
    public Integer getIcon() {
        if (iconName != null)
            return App.getResourceDrawable(iconName);
        else
            return App.getResourceDrawable(App.getResourceName(R.drawable.ic_category_icon_empty));
    }
    public String getName() {
        return isShow_symbol_name() ? ("[ " + name + " ]") : name;
    }
    public boolean isHighlighted() {
        return highlighted;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public boolean isShowIcon() {
        return getParentId() == 0;
    }
    public boolean isShow_symbol_name() {
        return show_symbol_name;
    }
    public int getChildrenCount() {
        return childrenCount;
    }


    // Metodos SET
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setColor(Integer color) {
        if (color != null)
            this.colorName = App.getResourceName(color);
        else
            this.colorName = App.getResourceName(R.color.default_color_spinner);
    }
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
    public void setIcon(Integer icon) {
        if (icon != null)
            this.iconName = App.getResourceName(icon);
        else
            this.iconName = App.getResourceName(R.drawable.ic_category_icon_empty);
    }
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public void setShow_symbol_name(boolean show_symbol_name) {
        this.show_symbol_name = show_symbol_name;
    }
    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }


    // Retorna uma instancia da categoria root
    public static CategoryModel getRoot() {
        CategoryModel model = new CategoryModel();
        model.setParentId(0);
        model.setParentName(App.getContext().getString(R.string.category_parent_novalue));
        model.setId(0);
        model.setName(App.getContext().getString(R.string.category_parent_novalue));
        model.setColor(R.color.default_color_spinner);
        model.setIcon(R.drawable.ic_category_icon_empty);
        model.setShow_symbol_name(true);
        model.setHighlighted(false);
        return  model;
    }

// *******************************************************************
// Implementação do Parcelable
// *******************************************************************


    protected CategoryModel(Parcel in) {
        if (in.readByte() == 0) {
            parentId = null;
        } else {
            parentId = in.readInt();
        }
        parentName = in.readString();
        id = in.readInt();
        colorName = in.readString();
        iconName = in.readString();
        name = in.readString();
        highlighted = in.readByte() != 0;
        enabled = in.readByte() != 0;
        show_symbol_name = in.readByte() != 0;
        childrenCount = in.readInt();
    }

    public static final Creator<CategoryModel> CREATOR = new Creator<CategoryModel>() {
        @Override
        public CategoryModel createFromParcel(Parcel in) {
            return new CategoryModel(in);
        }

        @Override
        public CategoryModel[] newArray(int size) {
            return new CategoryModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (parentId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(parentId);
        }
        parcel.writeString(parentName);
        parcel.writeInt(id);
        parcel.writeString(colorName);
        parcel.writeString(iconName);
        parcel.writeString(name);
        parcel.writeByte((byte) (highlighted ? 1 : 0));
        parcel.writeByte((byte) (enabled ? 1 : 0));
        parcel.writeByte((byte) (show_symbol_name ? 1 : 0));
        parcel.writeInt(childrenCount);
    }
}
