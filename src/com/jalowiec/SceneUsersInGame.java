package com.jalowiec;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SceneUsersInGame {

    Stage mainStage;
    UsersFileReader usersFileReader;
    CommonDataStructure commonDataStructure = CommonDataStructure.getInstance();


    public SceneUsersInGame(Stage mainStage) {
        this.mainStage = mainStage;
        usersFileReader = new UsersFileReader();
        showScene();
    }

    public void showScene(){

        VBox vBox = new VBox(20);
        vBox.setPadding(new Insets(10, 10, 10, 10));

        List<User> userNameList = usersFileReader.readUsersListFromFile();
        userNameList.addAll(commonDataStructure.getPlayersInTheGame());

        Label label = new Label("Wybierz użytkowników: ");
        label.setMinSize(50, 50);
        label.setId("label");
        vBox.getChildren().add(label);


        List<CheckBox> checkBoxes = new ArrayList<>();

        for(User user : userNameList){
            CheckBox checkBox = new CheckBox(user.getUserName());
            checkBox.setId("checkbox");
            vBox.getChildren().add(checkBox);
            checkBoxes.add(checkBox);
        }


        Button startGameButton = new Button("Rozpocznij gre");
        startGameButton.setOnAction(e -> {
            if(isUsersSelected(checkBoxes, userNameList)) {
                new SceneUser(mainStage);
            } else {
                Alert dialog = new Alert(Alert.AlertType.ERROR);
                dialog.setTitle("Ostrzeżenie");
                dialog.setContentText("Nie wybrano zadnego uzytkownika!");
                dialog.show();
            }
        });
        startGameButton.setId("button");
        vBox.getChildren().add(startGameButton);

        Scene sceneUsersInGame = new Scene(vBox, 400, 600);
        sceneUsersInGame.getStylesheets().add("sceneusersingame.css");
        mainStage.setTitle("The Fifth Dice");
        mainStage.setScene(sceneUsersInGame);
        mainStage.show();


    }

    private boolean isUsersSelected(List<CheckBox> checkBoxes, List<User> users) {
        List<User> selectedUsers = new ArrayList<>();
        for (CheckBox checkBox : checkBoxes) {
            if(checkBox.isSelected()){
                int index = checkBoxes.indexOf(checkBox);
                selectedUsers.add(users.get(index));
            }
        }
        if(selectedUsers.size()>0){
            commonDataStructure.setPlayersInTheGame(selectedUsers);
            commonDataStructure.createPlayersWhoNotFinished();
            return true;

        }

        return false;
    }


}
