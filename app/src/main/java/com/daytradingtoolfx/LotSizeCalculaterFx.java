package com.daytradingtoolfx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LotSizeCalculaterFx {

    private double account;
    private double rpt;
    private double slPip;
    private double targetPip;

    public LotSizeCalculaterFx(double account, double rpt, double slPip, double targetPip) {
        this.account = account;
        this.rpt = rpt;
        this.slPip = slPip;
        this.targetPip = targetPip;
    }

    public Map<String, String> calculate() {

        //Lots define
        final List<String> lot_names = new ArrayList<>();
        lot_names.add("standard");
        lot_names.add("mini");
        lot_names.add("micro");
        lot_names.add("nano");

        final Map<String, Double> lot = new HashMap<>();
        final Map<String, Double> lot_per_pip = new HashMap<>();

        lot.put(lot_names.get(0), (double)1);
        lot.put(lot_names.get(1), 0.1);
        lot.put(lot_names.get(2), 0.01);
        lot.put(lot_names.get(3), 0.001);


        lot_per_pip.put(lot_names.get(0), (double)10);
        lot_per_pip.put(lot_names.get(1), (double)1);
        lot_per_pip.put(lot_names.get(2), 0.1);
        lot_per_pip.put(lot_names.get(3), 0.01);

        //////////////////////////////////////////////


        System.out.println("\n#########################################\n"+
                "Account Size: "+account+"\n"+
                "RPT: "+rpt+"\n"+
                "Stoploss pips: "+slPip+"\n"+
                "Target pips: "+targetPip);

        System.out.println("\n#########################################\n");

        String finalLotName = "";
        double diff = -1;

        for(int i=0; i<lot_names.size(); i++){
            String lotName = lot_names.get(i);

            diff = lot_per_pip.get(lotName)*slPip;

            if(diff <= rpt){
                finalLotName = lotName;
                break;
            }
        }

        System.out.println("Diff: "+diff);

        if(!finalLotName.matches("") && diff != -1){
            double perPipValue = rpt/diff;

            double finalLot = lot.get(finalLotName)*perPipValue;
            double finalLotPerPip = lot_per_pip.get(finalLotName)*perPipValue;

            double totalLoss = finalLotPerPip*slPip;
            double totalProfit = finalLotPerPip*targetPip;

            Map<String, String> map = new HashMap<>();

            map.put("Lot Name", finalLotName);
            map.put("Lot", String.valueOf(round(finalLot, 4)));
            map.put("Lot Value Per Pip", String.valueOf(round(finalLotPerPip, 4)));
            map.put("Total Loss", String.valueOf(round(totalLoss, 4)));
            map.put("Total Profit", String.valueOf(round(totalProfit, 4)));

            System.out.println("Lot Name: "+finalLotName+", Lot: "+round(finalLot, 4)+", Lot Value Per Pip: "+round(finalLotPerPip, 4)+", "+
                    "Total Loss: "+round(totalLoss, 4)+", Total Profit: "+round(totalProfit, 4));

            return map;
        }

        return new HashMap<>();

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
