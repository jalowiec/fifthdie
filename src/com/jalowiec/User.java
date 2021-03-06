package com.jalowiec;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import java.io.Serializable;

public class User implements Serializable {

    private String userName;
    private Boolean isPC;
    transient private Scene userScene;
    transient private GridPane gridPane;
    private GameTableDrawer gameTableDrawer;
    private UserDataStructures userDataStructures;
    private RoundInitCommon roundInitCommon;
    private RoundProccesorUser roundProccesorUser;
    private RoundEnd roundEnd;


    public User(String userName, Boolean isPC) {
        this.userName = userName;
        this.isPC = isPC;
        userDataStructures = new UserDataStructures();
    }

    public String getUserName() {
        return userName;
    }

    public Boolean getPC() {
        return isPC;
    }

    public void setUserScene(Scene userScene) {
        this.userScene = userScene;
    }

    public Scene getUserScene() {
        return userScene;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public GameTableDrawer getGameTableDrawer() {
        return gameTableDrawer;
    }

    public void setGameTableDrawer(GameTableDrawer gameTableDrawer) {
        this.gameTableDrawer = gameTableDrawer;
    }

    public void setRoundInitCommon(RoundInitCommon roundInitCommon) {
        this.roundInitCommon = roundInitCommon;
    }

    public RoundInitCommon getRoundInitCommon() {
        return roundInitCommon;
    }

    public RoundProccesorUser getRoundProccesorUser() {
        return roundProccesorUser;
    }

    public void setRoundProccesorUser(RoundProccesorUser roundProccesorUser) {
        this.roundProccesorUser = roundProccesorUser;
    }

    public RoundEnd getRoundEnd() {
        return roundEnd;
    }

    public void setRoundEnd(RoundEnd roundEnd) {
        this.roundEnd = roundEnd;
    }

    public UserDataStructures getUserDataStructures() {
        return userDataStructures;
    }


}
