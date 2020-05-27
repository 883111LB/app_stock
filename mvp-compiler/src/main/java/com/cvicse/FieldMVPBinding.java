package com.cvicse;

import javax.lang.model.type.TypeMirror;

/**
 * Created by liuzilu on 2017/2/23.
 */

public class FieldMVPBinding {
    private final String name;
    private final TypeMirror type;
    private final Class _class;

    public FieldMVPBinding(String name, TypeMirror type, Class _class) {
        this.name = name;
        this.type = type;
        this._class = _class;
    }

    public Class get_Class() {
        return _class;
    }

    public String getName() {
        return name;
    }

    public TypeMirror getType() {
        return type;
    }
}
