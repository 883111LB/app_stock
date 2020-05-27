package com.cvicse.inject;

/**
 * Created by liuzilu on 2017/2/24.
 */

public interface MvpBinder<T> {
    void bind(T target);
}
