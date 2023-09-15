package inputHandling;


import com.badlogic.gdx.controllers.Controller;

public class MyControllerState {
    Controller controller;

    public boolean A_DOWN = false;
    public boolean B_DOWN = false;
    public boolean X_DOWN = false;
    public boolean Y_DOWN = false;
    public boolean LS_DOWN = false;
    public boolean RS_DOWN = false;
    public boolean LB_DOWN = false;
    public boolean RB_DOWN = false;

    public int AXIS_LEFT_X = 0;
    public int AXIS_LEFT_Y = 0;
    public int AXIS_RIGHT_X = 0;
    public int AXIS_RIGHT_Y = 0;
    public int LEFT_TRIGGER = 0;
    public int RIGHT_TRIGGER = 0;

    public boolean AXIS_LEFT_X_CHANGED=false;
    public boolean AXIS_LEFT_Y_CHANGED=false;
    public boolean AXIS_RIGHT_X_CHANGED=false;
    public boolean AXIS_RIGHT_Y_CHANGED=false;

    public MyControllerState(Controller controller) {
        this.controller = controller;
    }

    public boolean buttonDown(int buttonCode) {
        switch (buttonCode) {
            case GamepadSetting.BUTTON_A: {
                A_DOWN = true;
                break;
            }
            case GamepadSetting.BUTTON_B: {
                B_DOWN = true;
                break;
            }
            case GamepadSetting.BUTTON_X: {
                X_DOWN = true;
                break;
            }
            case GamepadSetting.BUTTON_Y: {
                Y_DOWN = true;
                break;
            }
            case GamepadSetting.BUTTON_LS: {
                LS_DOWN = true;
                break;
            }
            case GamepadSetting.BUTTON_RS: {
                RS_DOWN = true;
                break;
            }
            case GamepadSetting.BUTTON_LB: {
                LB_DOWN = true;
                break;
            }
            case GamepadSetting.BUTTON_RB: {
                RB_DOWN = true;
                break;
            }
        }
        return false;
    }

    public boolean buttonUp(int buttonCode) {
        switch (buttonCode) {
            case GamepadSetting.BUTTON_A: {
                A_DOWN = false;
                break;
            }
            case GamepadSetting.BUTTON_B: {
                B_DOWN = false;
                break;
            }
            case GamepadSetting.BUTTON_X: {
                X_DOWN = false;
                break;
            }
            case GamepadSetting.BUTTON_Y: {
                Y_DOWN = false;
                break;
            }
            case GamepadSetting.BUTTON_LS: {
                LS_DOWN = false;
                break;
            }
            case GamepadSetting.BUTTON_RS: {
                RS_DOWN = false;
                break;
            }
            case GamepadSetting.BUTTON_LB: {
                LB_DOWN = false;
                break;
            }
            case GamepadSetting.BUTTON_RB: {
                RB_DOWN = false;
                break;
            }
        }
        return false;
    }

    public boolean axisMove(int axisCode, int intValue) {
        switch(axisCode){
            case GamepadSetting.AXIS_LEFT_X:{
                AXIS_LEFT_X_CHANGED= AXIS_LEFT_X == 0 && intValue != 0;
                AXIS_LEFT_X=intValue;
                break;
            }
            case GamepadSetting.AXIS_LEFT_Y:{
                AXIS_LEFT_Y_CHANGED= AXIS_LEFT_Y == 0 && intValue != 0;
                AXIS_LEFT_Y=intValue;
                break;
            }
            case GamepadSetting.AXIS_RIGHT_X:{
                AXIS_RIGHT_X_CHANGED= AXIS_RIGHT_X == 0 && intValue != 0;
                AXIS_RIGHT_X=intValue;
                break;
            }
            case GamepadSetting.AXIS_RIGHT_Y:{
                AXIS_RIGHT_Y_CHANGED= AXIS_RIGHT_Y == 0 && intValue != 0;
                AXIS_RIGHT_Y=intValue;
                break;
            }
            case GamepadSetting.AXIS_LEFT_TRIGGER:{
                LEFT_TRIGGER=intValue;
                break;
            }
            case GamepadSetting.AXIS_RIGHT_TRIGGER:{
                RIGHT_TRIGGER=intValue;
                break;
            }
        }
        return false;
    }


}
