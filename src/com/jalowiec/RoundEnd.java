package com.jalowiec;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.*;

public class RoundEnd {

    private GridPane gridPane;
    private List<Integer> fifthDiceList = new ArrayList<>();
    private Map<Integer, Integer> chosenFiftDice = new HashMap<>();
    private DiceSlotsOperation diceSlotsOperation;
    private TableDrawer tableDrawer;
    private User user;
    private boolean isRoundEnd;
    private boolean isGameEnd;



    public RoundEnd(User user) {
        this.gridPane = user.getGridPane();
        diceSlotsOperation = DiceSlotsOperation.getInstance();
        tableDrawer = new TableDrawer(user);
        this.user = user;
    }

    public boolean isRoundEnd() {
        return isRoundEnd;
    }

    public void setRoundEnd(boolean roundEnd) {
        isRoundEnd = roundEnd;
    }

    public boolean isGameEnd() {
        return isGameEnd;
    }

    public void setGameEnd(boolean gameEnd) {
        isGameEnd = gameEnd;
    }

    public Set<Integer> getChosenFifthDiceSet(){
        return  chosenFiftDice.keySet();
            }




    private void processFifthDie(int fifthDieValue){
        if(!fifthDiceList.contains(fifthDieValue)){
            if(fifthDiceList.size()<3){
                fifthDiceList.add(fifthDieValue);
                tableDrawer.drawChosenFifthDie(fifthDieValue, chosenFiftDice.size());
                chosenFiftDice.put(fifthDieValue, 0);
            }
        } else{
            chosenFiftDice.put(fifthDieValue, chosenFiftDice.get(fifthDieValue)+1);
            tableDrawer.drawChosenFifthDieSlots(chosenFiftDice.get(fifthDieValue), fifthDiceList.indexOf(fifthDieValue));
        }
    }

    public boolean isFifthSlotFree(int fifthDieValue){
        if(chosenFiftDice.containsKey(fifthDieValue) && chosenFiftDice.get(fifthDieValue) == 8){
            return false;
        }
        return true;
    }


    public boolean isPairSlotFree(int pairSum){
        Map<Integer, Integer> scorePointerMap = user.getUserDataStructures().getScorePointerMap();

        if(scorePointerMap.get(pairSum)>9){
            return false;
        }
        return true;
    }


    public void countScoreAfterRound(TableDrawer tableDrawer) {
        //TODO - usunac
        if (!user.getPC()) {
            Map<Integer, Integer> scorePointerMap = user.getUserDataStructures().getScorePointerMap();
            int chosenFifthDieValue = getFifthDieValue();
            int firstPairSum = getFirstPairSum();
            int secondPairSum = getSecondPairSum();
            int firstCouplePointer = scorePointerMap.get(firstPairSum);
            int secondCouplePointer;

            if(!isFifthSlotFree(chosenFifthDieValue) || !isPairSlotFree(firstPairSum) ){
                endGameForUser();
            } else {
                scorePointerMap.replace(firstPairSum, ++firstCouplePointer);
                tableDrawer.drawUsedSlotsAfterRound(firstPairSum, firstCouplePointer);
                processFifthDie(chosenFifthDieValue);

                if(!isPairSlotFree(secondPairSum)){
                    endGameForUser();
                } else {
                    secondCouplePointer = scorePointerMap.get(secondPairSum);
                    scorePointerMap.replace(secondPairSum, ++secondCouplePointer);
                    tableDrawer.drawUsedSlotsAfterRound(secondPairSum, secondCouplePointer);
                    //TODO - czy ma sie wyliczyc raz jeszcze?
                    tableDrawer.drawScore(diceSlotsOperation.getScoreFromSchema(scorePointerMap));
                }
            }
        } else {
            System.out.println("ruch komputera");
            user.getTableDrawer().getEndTurnButton().setDisable(false);

        }
        user.getRoundProccesorUser().setEndTurnButtonDisabled();
        setRoundEnd(true);
        user.getTableDrawer().getNextPlayerButton().setDisable(false);


    }

    private void endGameForUser(){
        setGameEnd(true);
    }


    public int getFifthDieValue(){

        int[] chosenSlots = user.getUserDataStructures().getFreeSlotState().clone();
        Arrays.sort(chosenSlots);
        int fifthDieIndex = 4;
        for(int i=0; i<4; i++){
            if(i!=chosenSlots[i]){
                fifthDieIndex =  i;
                break;
            }
        }
        Die[] diceList = user.getUserDataStructures().getDiceList();
        return diceList[fifthDieIndex].getDiceValue();
    }

    public int getFirstPairSum(){
        Die[] diceList = user.getUserDataStructures().getDiceList();
        int[] freeSlotState = user.getUserDataStructures().getFreeSlotState();
        return diceList[freeSlotState[0]].getDiceValue() + diceList[freeSlotState[1]].getDiceValue();
    }

    public int getSecondPairSum(){
        Die[] diceList = user.getUserDataStructures().getDiceList();
        int[] freeSlotState = user.getUserDataStructures().getFreeSlotState();
        return diceList[freeSlotState[2]].getDiceValue() + diceList[freeSlotState[3]].getDiceValue();
    }





}
