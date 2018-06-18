package com.hariofspades.dagger2advanced.presenter;

import javax.inject.Inject;

public class DummyDependency {
    @Inject
    public DummyDependency() {

    }
    public String getName() {
        return "DummyDependency";
    }
}
