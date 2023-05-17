package com.daytradingtoolfx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText accountSize, rpt, slPips, targetPips;
    private Button calculateBtn;
    private TextView outputLotName, outputLot, outputLotValuePerPip, outputTotalLoss, outputTotalProfit;
    private LinearLayout outputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{

            if (Build.VERSION.SDK_INT >= 21) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(getResources().getColor(R.color.white));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        initViews();

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    LotSizeCalculaterFx lotSizeCalculaterFx = new LotSizeCalculaterFx(
                            Double.parseDouble(accountSize.getText().toString()),
                            Double.parseDouble(rpt.getText().toString()),
                            Double.parseDouble(slPips.getText().toString()),
                            Double.parseDouble(targetPips.getText().toString())
                    );

                    Map<String, String> map = lotSizeCalculaterFx.calculate();

                    List<String> list = new ArrayList<>(map.keySet());

                    for(int i=0; i<list.size(); i++){

                        switch (list.get(i)){

                            case "Lot Name":
                                outputLotName.setText(map.get(list.get(i)));
                                break;
                            case "Lot":
                                outputLot.setText(map.get(list.get(i)));
                                break;
                            case "Lot Value Per Pip":
                                outputLotValuePerPip.setText(map.get(list.get(i))+"$");
                                break;
                            case "Total Loss":
                                outputTotalLoss.setText(map.get(list.get(i))+"$");
                                break;
                            case "Total Profit":
                                outputTotalProfit.setText(map.get(list.get(i))+"$");
                                break;
                        }
                    }
                    outputLayout.setVisibility(View.VISIBLE);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void initViews() {

        accountSize = (EditText) findViewById(R.id.account_size);
        rpt = (EditText) findViewById(R.id.rpt);
        accountSize.setText("500");
        rpt.setText("15");

        slPips = (EditText) findViewById(R.id.sl_pips);
        targetPips = (EditText) findViewById(R.id.target_pips);

        calculateBtn = (Button) findViewById(R.id.calculate_btn);

        outputLotName = (TextView) findViewById(R.id.output_lot_name);
        outputLot = (TextView) findViewById(R.id.output_lot);
        outputLotValuePerPip = (TextView) findViewById(R.id.output_lot_value_per_pip);
        outputTotalLoss = (TextView) findViewById(R.id.output_total_loss);
        outputTotalProfit = (TextView) findViewById(R.id.output_total_profit);
        outputLayout = (LinearLayout) findViewById(R.id.output_layout);
    }
}