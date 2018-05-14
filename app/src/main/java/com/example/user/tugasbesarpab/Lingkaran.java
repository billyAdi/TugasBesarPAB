package com.example.user.tugasbesarpab;

/**
 * Created by WIN 10 on 5/10/2018.
 */

public class Lingkaran {
    public int posX,posY,rad,speedX,speedY;

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getRad() {
        return rad;
    }

    public void setRad(int rad) {
        this.rad = rad;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
    

    public Lingkaran(int posX, int posY, int rad) {

        this.posX = posX;
        this.posY = posY;
        this.rad = rad;
        this.speedX = 0;
        this.speedY = 0;
    }
}
