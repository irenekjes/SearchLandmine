package com.example.blaub.searchlandmine;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TableLayout mTableLayout;
    private TextView mTxtLandmineArray;

    private TableRow[] mTableRowArray;
    private int[] mLandmineDataArray;
    private int[][] mAllDataArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    protected void init() {
        // initView
        mTableLayout = findViewById(R.id.mainTableLayout);
        mTxtLandmineArray = findViewById(R.id.mainTableLandmineTxt);
        mTableRowArray = new TableRow[10];

        // initData : 지뢰를 포함하는 데이터set 생성
        mLandmineDataArray = new int[10];
        mAllDataArray = new int[10][10];

        Random r = new Random();
        String landmine = "";

        for(int i = 0; i < 10; i++) {
            mLandmineDataArray[i] = r.nextInt(100) + 1;

            for(int j = 0; j < i; j++) {
                if(mLandmineDataArray[i] == mLandmineDataArray[j]) {
                    i--;
                    break;
                }
            }

            Log.d(TAG, "i = " + i + " | rnad = " + mLandmineDataArray[i]);
        }

        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                mAllDataArray[i][j] = 0;
            }
        }

        for(int i = 0; i < 10; i++) {
            if(mLandmineDataArray[i] < 10) {
                mAllDataArray[0][mLandmineDataArray[i]-1] = 1;
            } else if(mLandmineDataArray[i]%10 == 0) {
                mAllDataArray[(mLandmineDataArray[i]/10)-1][9] = 1;
            } else if(mLandmineDataArray[i] >= 10 && mLandmineDataArray[i] < 100) {
                mAllDataArray[(mLandmineDataArray[i]/10)-1][(mLandmineDataArray[i]%10)-1] = 1;
            } else {
                mAllDataArray[9][9] = 1;
            }
        }

        // initAction
        mTxtLandmineArray.setText(landmine);

        for(int i = 0; i < 10; i++) {
            mTableRowArray[i] = new TableRow(this);
            mTableRowArray[i].setId(i+1);
            mTableRowArray[i].setLayoutParams(new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));

            TextView[] tableCellArray = new TextView[10];
            for(int j = 0; j < 10; j++) {
                int number = 0;
                if(i == 0) {
                    if(j == 0) {
                        number = mAllDataArray[i+1][j+1]
                                + mAllDataArray[i+1][j]
                                + mAllDataArray[i][j+1];
                    } else if(j == 9) {
                        number = mAllDataArray[i+1][j-1]
                                + mAllDataArray[i+1][j]
                                + mAllDataArray[i][j-1];
                    } else {
                        number = mAllDataArray[i][j-1]
                                + mAllDataArray[i][j+1]
                                + mAllDataArray[i+1][j-1]
                                + mAllDataArray[i+1][j]
                                + mAllDataArray[i+1][j+1];
                    }
                } else if(i == 9) {
                    if(j == 0) {
                        number = mAllDataArray[i-1][j+1]
                                + mAllDataArray[i-1][j]
                                + mAllDataArray[i][j+1];
                    } else if(j == 9) {
                        number = mAllDataArray[i-1][j-1]
                                + mAllDataArray[i-1][j]
                                + mAllDataArray[i][j-1];
                    } else {
                        number = mAllDataArray[i][j-1]
                                + mAllDataArray[i][j+1]
                                + mAllDataArray[i-1][j-1]
                                + mAllDataArray[i-1][j]
                                + mAllDataArray[i-1][j+1];
                    }
                } else {
                    if(j == 0) {
                        number = mAllDataArray[i-1][j]
                                + mAllDataArray[i-1][j+1]
                                + mAllDataArray[i][j+1]
                                + mAllDataArray[i+1][j]
                                + mAllDataArray[i+1][j+1];
                    } else if(j == 9) {
                        number = mAllDataArray[i-1][j]
                                + mAllDataArray[i-1][j-1]
                                + mAllDataArray[i][j-1]
                                + mAllDataArray[i+1][j-1]
                                + mAllDataArray[i+1][j];
                    } else {
                        number = mAllDataArray[i-1][j-1]
                                + mAllDataArray[i-1][j]
                                + mAllDataArray[i-1][j+1]
                                + mAllDataArray[i][j-1]
                                + mAllDataArray[i][j+1]
                                + mAllDataArray[i+1][j-1]
                                + mAllDataArray[i+1][j]
                                + mAllDataArray[i+1][j+1];
                    }
                }

                tableCellArray[j] = new TextView(this);
                tableCellArray[j].setId(i+111);
                tableCellArray[j].setPadding(5, 20, 5, 20);
                tableCellArray[j].setText(String.valueOf(number));
                tableCellArray[j].setGravity(Gravity.CENTER_HORIZONTAL);
//                if(mAllDataArray[i][j] == 1) {
//                    tableCellArray[j].setBackgroundColor(Color.GRAY);
//                    tableCellArray[j].setTextColor(Color.WHITE);
//                } else {
//                    tableCellArray[j].setBackgroundColor(Color.WHITE);
//                    tableCellArray[j].setTextColor(Color.BLACK);
//                }
                mTableRowArray[i].addView(tableCellArray[j]);
            }

            mTableLayout.addView(mTableRowArray[i], new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

        }
    }
}
