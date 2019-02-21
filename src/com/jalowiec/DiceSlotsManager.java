package com.jalowiec;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class DiceSlotsManager {

    private GridPane grid;
    private Scene scene;
    private List<ImageView> imageViewList = new ArrayList<>();
    private Die[] diceLists = new Die[5];
    private List<DieSlot> diceSlotsList;
    private List<DieSlot> freeSlotsList;
    private int[] freeSlotState = new int[4];
    private DiceGenerator diceGenerator;
    private Text scoreText;
    private static DiceSlotsManager instance;


    private DiceSlotsManager(GridPane grid, Scene scene){

        this.grid = grid;
        this.scene = scene;
         diceGenerator = new DiceGenerator(grid, scene);

    }
    public static DiceSlotsManager getInstance(GridPane grid, Scene scene){
        if(instance==null){
            instance=new DiceSlotsManager(grid, scene);
        }
        return instance;
    }

    public Text getScoreText() {
        return scoreText;
    }

    public void setScoreText(Text scoreText) {
        this.scoreText = scoreText;
    }

    public void generateDice(){
        diceGenerator.createDices();
    }

    public void generateSlots(){
        DieSlotsGenerator dieSlotsGenerator = new DieSlotsGenerator();
        dieSlotsGenerator.generateSlots();
        diceSlotsList = dieSlotsGenerator.getSlotsList();
        freeSlotsList = dieSlotsGenerator.getFreeSlotsList();
        for(int i=0; i<freeSlotState.length; i++){
             freeSlotState[i] = -1;
        }
    }

    public void generateDicesInSlots() {
        removeAllDiceFromSlots();
        RandomDiceValue randomDiceValue = new RandomDiceValue();
        int[] randomValues = randomDiceValue.getRandomArray(5);
        for (int i = 0; i < randomValues.length; i++) {
            drawDieInSlot(diceGenerator.getDieFromValue(randomValues[i]), i);
        }
    }

    public void drawDieInSlot(Die die, int slotNumber) {
        EventHandler<MouseEvent> mouseHandler = e -> {
            swapDieInSlots(slotNumber);

        };

        diceLists[slotNumber] = die;
        ImageView imageView = new ImageView(die.getDieImage());
        imageView.setOnMouseClicked(mouseHandler);
        imageView.setCursor(Cursor.CLOSED_HAND);
        imageViewList.add(imageView);

        grid.add(imageView, diceSlotsList.get(slotNumber).getColumnIndex(),
                diceSlotsList.get(slotNumber).getRowIndex(),
                diceSlotsList.get(slotNumber).getColumnSpan(),
                diceSlotsList.get(slotNumber).getRowSpan());
    }

    public boolean isSlotNumberChosen(int slotNumber){
        for(int i=0; i<freeSlotState.length; i++){
            if(freeSlotState[i] == slotNumber){
                return true;
            }
        }
        return false;
    }

    public void removeAllDiceFromSlots() {
        for(ImageView element : imageViewList){
            grid.getChildren().remove(element);
        }
        imageViewList.clear();
    }

    public void removeDieInSlot(int slotNumber) {
            grid.getChildren().remove(imageViewList.get(slotNumber));
    }


    public int getFirstFreeSlotIndex(){
        for(int i=0; i<freeSlotState.length; i++){
            if(freeSlotState[i] == -1){
                return i;
            }
        }
        return -1;
    }


    public boolean isFreeSlot(){
        for(int i=0; i<freeSlotState.length; i++){
            if(freeSlotState[i]==-1){
                return true;
            }
        }

        return false;
    }

    public void closeFreeSlot(int freeSlotPosition, int slotNumber){
        freeSlotState[freeSlotPosition] = slotNumber;
    }

    public void openFreeSlot(int slotNumber){
        for(int i=0; i<freeSlotState.length; i++){
            if(freeSlotState[i]==slotNumber){
                freeSlotState[i]=-1;
            }
        }
    }


    public void swapDieInSlots(int slotNumber) {
        if (isSlotNumberChosen(slotNumber)) {
            setEndTurnButtonDisabled();
            openFreeSlot(slotNumber);
            removeDieInSlot(slotNumber);
            grid.add(imageViewList.get(slotNumber),
                    diceSlotsList.get(slotNumber).getColumnIndex(),
                    diceSlotsList.get(slotNumber).getRowIndex(),
                    diceSlotsList.get(slotNumber).getColumnSpan(),
                    diceSlotsList.get(slotNumber).getRowSpan());
        } else {
            if (isFreeSlot()) {
                int firstFreeSlot = getFirstFreeSlotIndex();
                closeFreeSlot(firstFreeSlot, slotNumber);
                setEndTurnButtonEnabled();
                removeDieInSlot(slotNumber);
                grid.add(imageViewList.get(slotNumber),
                        freeSlotsList.get(firstFreeSlot).getColumnIndex(),
                        freeSlotsList.get(firstFreeSlot).getRowIndex(),
                        freeSlotsList.get(firstFreeSlot).getColumnSpan(),
                        freeSlotsList.get(firstFreeSlot).getRowSpan());
            }
        }
    }

    public int getFifthDieValue(){

        int[] chosenSlots = freeSlotState.clone();
        Arrays.sort(chosenSlots);
        int fifthDieIndex = 4;
        for(int i=0; i<4; i++){
            if(i!=chosenSlots[i]){
                fifthDieIndex =  i;
                break;
            }
        }
        return diceLists[fifthDieIndex].getDiceValue();
    }

    public int getFirstPairSum(){
        return diceLists[freeSlotState[0]].getDiceValue() + diceLists[freeSlotState[1]].getDiceValue();
    }

    public int getSecondPairSum(){
        return diceLists[freeSlotState[2]].getDiceValue() + diceLists[freeSlotState[3]].getDiceValue();
    }

    public void setEndTurnButtonDisabled(){
        UserGameTableDrawer.getEndTurnButton().setDisable(true);
    }

    private void setEndTurnButtonEnabled(){
        if(!isFreeSlot() && isChosenFifthDieCorrect()){
            UserGameTableDrawer.getEndTurnButton().setDisable(false);
        }
    }

    private boolean isFreeRound(Set<Integer> chosenFifthDiceSet) {
        for (int i = 0; i < diceLists.length; i++) {
            if (chosenFifthDiceSet.contains(diceLists[i].getDiceValue())) {
                return false;
            }
        }
        return true;
    }

    public boolean isChosenFifthDieCorrect(){
        int fifthDieValue = getFifthDieValue();
        Set<Integer> chosenFifthDiceSet = EndRoundManager.getChosenFifthDiceSet();
        if(chosenFifthDiceSet.size()==3 && !chosenFifthDiceSet.contains(fifthDieValue) && !isFreeRound(chosenFifthDiceSet) ){
            return false;
        }

        return true;
    }



}

