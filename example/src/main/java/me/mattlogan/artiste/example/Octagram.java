package me.mattlogan.artiste.example;

import me.mattlogan.artiste.RegularStarPolygon;

public class Octagram extends RegularStarPolygon {

    @Override
    public int getNumberOfPoints() {
        return 8;
    }

    @Override
    public int getDensity() {
        return 3;
    }

    @Override
    public boolean isOutlined() {
        return false;
    }
}
