package com.testerhome.nativeandroid.models;

/**
 * Created by Bin Li on 2015/9/15.
 */
public class AbilitiesEntity {
    /**
     * update : false
     * destroy : false
     */

    private boolean update;
    private boolean destroy;

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    public boolean getUpdate() {
        return update;
    }

    public boolean getDestroy() {
        return destroy;
    }
}