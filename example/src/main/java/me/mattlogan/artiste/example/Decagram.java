package me.mattlogan.artiste.example;

import me.mattlogan.artiste.RegularStarPolygon;

public class Decagram extends RegularStarPolygon {
    @Override
    public int getNumberOfPoints() {
        return 10;
    }

    @Override
    public int getDensity() {
        return 3;
    }

    @Override
    public boolean isOutlined() {
        return true;
    }
}
