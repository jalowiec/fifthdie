package com.jalowiec;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class UserGameTableDrawer {

    private Scene scene;
    private GridPane grid;

    public UserGameTableDrawer(GridPane grid, Scene scene) {
        this.scene = scene;
        this.grid = grid;
    }

    public void drawTableHeader(int firstColumnIndex){
        String[] rowValues = {"SUMA", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        Text cellText;

        for(int i=0; i<rowValues.length; i++){
            cellText = new Text(rowValues[i]);
            cellText.setId("tableheader");
            grid.add(cellText,  i+firstColumnIndex, 0);
        }
    }

    public void drawPointsRow(int firstColumnIndex){
        String[] rowValues = {"PUNKTY", "10", "7", "6", "5", "4", "3", "4", "5", "6", "7", "10"};
        Text cellText;

        for(int i=0; i<rowValues.length; i++){
            cellText = new Text(rowValues[i]);
            cellText.setId("tablerow");
            grid.add(cellText,  i+firstColumnIndex, 1);
        }
    }
    public void drawMinusSection(int firstColumnIndex) {
        Text cellText = new Text("-");
        cellText.setId("cellminus");
        GridPane.setConstraints(cellText, firstColumnIndex, 2, 1,4);
        grid.getChildren().add(cellText);
    }


    public void drawPlusSection(int firstColumnIndex) {
        Text cellText = new Text("+");
        cellText.setId("cellplus");
        GridPane.setConstraints(cellText,firstColumnIndex, 6, 1,5);
        grid.getChildren().add(cellText);
    }

    public void drawFifthBoneHeader(int firstColumnIndex) {
        Text cellText = new Text("PIĄTA KOŚĆ");
        cellText.setId("tableheader");
        grid.add(cellText,  firstColumnIndex, 11);
    }

    public void drawFifthBoneSection(int firstColumnIndex) {
        Text cellText = new Text("*");
        for(int i=0; i<3; i++){
            cellText = new Text("*");
            cellText.setId("tableheader");
            grid.add(cellText,  firstColumnIndex, 12+i);
        }


    }


    public void drawScoreHeader(int firstColumnIndex) {
        Text cellText = new Text("WYNIK");
        cellText.setId("tableheader");
        GridPane.setConstraints(cellText,10+firstColumnIndex, 11, 1,2);
        grid.getChildren().add(cellText);
    }

    public void drawScore(int firstColumnIndex) {
        Text cellText = new Text("200");
        cellText.setId("score");
        GridPane.setConstraints(cellText,10+firstColumnIndex, 13, 1,2);
        grid.getChildren().add(cellText);
    }




}
