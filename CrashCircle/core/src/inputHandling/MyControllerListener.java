package inputHandling;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

public class MyControllerListener implements ControllerListener {
    boolean hasController = false;

    Controller controller;

    int max_power=10;

    MyControllerState controllerState;

    public MyControllerListener(MyControllerState controllerState) {
        this.controllerState = controllerState;
    }

    @Override
    public void connected(Controller controller) {
        hasController = true;
    }

    @Override
    public void disconnected(Controller controller) {
        System.out.println("disconnected");
        hasController = false;
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        controllerState.buttonDown(buttonCode);
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        controllerState.buttonUp(buttonCode);
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        controllerState.axisMove(axisCode,toInt(value));
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
    int toInt(float initial) {
        if (initial > 1 || initial < -1) {
            System.out.println("Error! toInt should be less than 1");
        }
        return Math.round(initial * max_power);
    }
}
