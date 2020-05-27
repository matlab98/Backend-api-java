package com.restfulproyecto.restfulspringboot.Models.Composite;

import java.util.ArrayList;
import java.util.List;

public class Group implements Component {

    //region Members
    private List<Component> components;
    private String name;
    //endregion

    //region Constructor
    public Group(String name) {
        this.name = name;
        components = new ArrayList();
    }
    //endregion

    //region Public methods
    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    @Override
    public String getInfo() {
        StringBuilder builder = new StringBuilder();
        for (Component component: components) builder.append(component.getInfo());
        return builder.toString();
    }

    //endregion
}
