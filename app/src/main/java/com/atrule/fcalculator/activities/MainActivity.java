package com.atrule.fcalculator.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.atrule.fcalculator.shared_preference.PreferenceManager;
import com.atrule.fcalculator.model.MyCalculationClass;
import com.atrule.fcalculator.adapter.MyRecyclerViewAdapter;
import com.atrule.fcalculator.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    // region Variables Declaration
    private static final String PLUS_CHAR = "+";
    private static final String MINUS_CHAR = "-";
    private static final String MULTIPLY_CHAR = "x";
    private static final String DIVIDE_CHAR = "÷";

    ArrayList<MyCalculationClass> myCalculationList;
    ArrayList<MyCalculationClass> mySharedPrefList;
    MyRecyclerViewAdapter myAdapter;
    RecyclerView recyclerView;

    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnPlusMinus, btnDot, btnEqual, btnClear, btnClearDigit;
    private FloatingActionButton fabPlus, fabMinus, fabDivision, fabMultiplication;
    private TextView tvInput, tvTempResult, tvFinalResult;
    HorizontalScrollView scrollView1, scrollView2;

    boolean isOperationPressed, isFirstNumber, isEqualPressed, isOperationButtonPressed;
    boolean isFirstTimeOperationPressed = true, firstPlus = true, firstMinus = true, firstMultiply = true, firstDivide = true;

    double myValueOne, myValueTwo, myVal, mValue;
    double answer, result;
    Character operation, mOperation;
    String previousOperator = "", nextOperator = "", strValue = "";
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // region Widgets Initialization
        initWidgets();
        clickListeners();
        // endregion

        // region Plus Button
        fabPlus.setOnClickListener(view -> {

            if (!tvInput.getText().toString().equals(""))
            {
                previousOperator = nextOperator;
                nextOperator = PLUS_CHAR;
                mValue = Double.parseDouble(doubleResult(Double.valueOf(tvInput.getText().toString())));

                if (isFirstTimeOperationPressed)
                {
                    if (firstPlus)
                    {
                        mFunction();
                        firstPlus = false;
                    }
                    isFirstTimeOperationPressed = false;
                }
                else
                {
                    if (firstPlus)
                    {
                        String strResult = tvFinalResult.getText().toString();

                        mFunctionPlus(strResult);
                        firstPlus = false;
                    }
                    else
                    {
                        myVal = getFirstValue();
                        myValueOne = myValueOne + getFirstValue();
                        tvInput.setText("");
                    }
                }

                showResult('+', PLUS_CHAR, myVal);
            }
            else
            {
                String string = tvFinalResult.getText().toString();
                if (!string.equals(""))
                {
                    if(string.endsWith(MINUS_CHAR) || string.endsWith(MULTIPLY_CHAR) || string.endsWith(DIVIDE_CHAR))
                    {
                        strValue = string.substring(0, string.length() - 1) + PLUS_CHAR;
                        tvFinalResult.setText(strValue);
                        operation = '+';
                    }
                }
            }

            // region Show/Hide Result
            hideShowFunction();
            // endregion

        });
        // endregion

        // region Minus Button
        fabMinus.setOnClickListener(view -> {

            if (!tvInput.getText().toString().equals(""))
            {

                previousOperator = nextOperator;
                nextOperator = MINUS_CHAR;
                mValue = Double.parseDouble(doubleResult(Double.valueOf(tvInput.getText().toString())));

                if (isFirstTimeOperationPressed)
                {
                    if (firstMinus)
                    {
                        mFunction();
                        firstMinus = false;
                    }
                    isFirstTimeOperationPressed = false;
                }
                else
                {
                    if (firstMinus)
                    {
                        String strResult = tvFinalResult.getText().toString();

                        mFunctionMinus(strResult);
                        firstMinus = false;
                    }
                    else
                    {
                        myVal = getFirstValue();
                        myValueOne = myValueOne - getFirstValue();
                        tvInput.setText("");
                    }
                }

                showResult('-', MINUS_CHAR, myVal);
            }
            else
            {
                String string = tvFinalResult.getText().toString();
                if (!string.equals(""))
                {
                    if(string.endsWith(PLUS_CHAR) || string.endsWith(MULTIPLY_CHAR) || string.endsWith(DIVIDE_CHAR))
                    {
                        strValue = string.substring(0, string.length() - 1) + MINUS_CHAR;
                        tvFinalResult.setText(strValue);
                        operation = '-';
                    }
                }
            }

            // region Show/Hide Result
            hideShowFunction();
            // endregion

        });
        // endregion

        // region Multiply Button
        fabMultiplication.setOnClickListener(view -> {

            if (!tvInput.getText().toString().equals(""))
            {
                previousOperator = nextOperator;
                nextOperator = MULTIPLY_CHAR;
                mValue = Double.parseDouble(doubleResult(Double.valueOf(tvInput.getText().toString())));
                if (isFirstTimeOperationPressed)
                {
                    if (firstMultiply)
                    {
                        mFunction();
                        firstMultiply = false;
                    }
                    isFirstTimeOperationPressed = false;
                }
                else
                {
                    if (firstMultiply)
                    {
                        String strResult = tvFinalResult.getText().toString();

                        mFunctionMultiply(strResult);
                        firstMultiply = false;
                    }
                    else
                    {
                        myVal = getFirstValue();
                        myValueOne = myValueOne * getFirstValue();
                        tvInput.setText("");
                    }
                }

                showResult('x', MULTIPLY_CHAR, myVal);
            }
            else
            {
                String string = tvFinalResult.getText().toString();

                if (!string.equals(""))
                {
                    if(string.endsWith(PLUS_CHAR) || string.endsWith(MINUS_CHAR) || string.endsWith(DIVIDE_CHAR))
                    {
                        strValue = string.substring(0, string.length() - 1) + MULTIPLY_CHAR;
                        tvFinalResult.setText(strValue);
                        operation = 'x';
                    }
                }
            }

            // region Show/Hide Result
            hideShowFunction();
            // endregion

        });
        // endregion

        // region Divide Button
        fabDivision.setOnClickListener(view -> {

            if (!tvInput.getText().toString().equals(""))
            {
                previousOperator = nextOperator;
                nextOperator = DIVIDE_CHAR;
                mValue = Double.parseDouble(doubleResult(Double.valueOf(tvInput.getText().toString())));
                if (isFirstTimeOperationPressed)
                {
                    if (firstDivide)
                    {
                        mFunction();
                        firstDivide = false;
                    }
                    isFirstTimeOperationPressed = false;
                }
                else
                {
                    if (firstDivide)
                    {
                        String strResult = tvFinalResult.getText().toString();

                        mFunctionDivide(strResult);
                        firstDivide = false;
                    }
                    else
                    {
                        myVal = getFirstValue();
                        myValueOne = myValueOne / getFirstValue();
                        tvInput.setText("");
                    }
                }

                showResult('÷', DIVIDE_CHAR, myVal);
            }
            else
            {
                String string = tvFinalResult.getText().toString();
                if (!string.equals(""))
                {
                    if(string.endsWith(PLUS_CHAR) || string.endsWith(MINUS_CHAR) || string.endsWith(MULTIPLY_CHAR))
                    {
                        strValue = string.substring(0, string.length() - 1) + DIVIDE_CHAR;
                        tvFinalResult.setText(strValue);
                        operation = '÷';
                    }
                }
            }

            // region Show/Hide Result
            hideShowFunction();
            // endregion

        });
        // endregion

        // region Checking SharedPreference Data
        if (mySharedPrefList == null)
        {
            mySharedPrefList = new ArrayList<>();
        }
        myAdapter = new MyRecyclerViewAdapter(this, mySharedPrefList);
        recyclerView.smoothScrollToPosition(myAdapter.getItemCount());
        recyclerView.setAdapter(myAdapter);
        //endregion
    }

    // region Widgets Initialization
    private void initWidgets() {

        // region Number Buttons
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        // endregion

        // region Operator Buttons
        btnPlusMinus = findViewById(R.id.btnPlusMinus);
        btnDot = findViewById(R.id.btn_dot);
        btnEqual = findViewById(R.id.btnEqual);
        btnClear = findViewById(R.id.btnClear);
        btnClearDigit = findViewById(R.id.btnClearDigit);
        // endregion

        // region Floating Action (Operation) Buttons
        fabPlus = findViewById(R.id.fab_pls);
        fabMinus = findViewById(R.id.fab_min);
        fabDivision = findViewById(R.id.fab_div);
        fabMultiplication = findViewById(R.id.fab_mul);
        // endregion

        // region TextViews
        tvInput = findViewById(R.id.inputTextView);
        tvTempResult = findViewById(R.id.tempResultTextView);
        tvFinalResult = findViewById(R.id.resultTextView);
        scrollView1 = findViewById(R.id.hsv);
        scrollView2 = findViewById(R.id.hsv2);
        // endregion

        myValueOne = 0;
        myValueTwo = 0;

        // region TextViews TextChange Listeners
        tvTempResult.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                scrollItem();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                scrollItem();
            }
        });

        tvFinalResult.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                scrollItem();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                scrollItem();
            }
        });
        // endregion

        recyclerView = findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mySharedPrefList = PreferenceManager.readListFromPreference(this);
    }
    // endregion

    // region Buttons ClickListeners Declaration
    private void clickListeners() {

        // region Number Buttons ClickListeners
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn0.setOnClickListener(this);
        // endregion

        // region Operator Buttons ClickListeners
        btnPlusMinus.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnClearDigit.setOnClickListener(this);// endregion
    }
    // endregion

    // region Setting Data to TextViews
    @SuppressLint("SetTextI18n")
    private void setTextData(String number)
    {
        if (tvInput.getText().toString().equals(getString(R.string.zero)))
        {
            tvInput.setText(number);
        }
        else
        {
            String value = tvInput.getText().toString();
            tvInput.setText(value + number);
        }
    }

    private void setTextAndOperator(char operations)
    {
        tvInput.setText("");

        operation = operations;
    }

    private void setFieldsData(String s) {

        tvInput.setText(s);
        tvFinalResult.setText("");
        isEqualPressed = false;
    }
    // endregion

    // region onClick of Buttons
    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn0:
                // region Button Zero
                if (isOperationButtonPressed)
                {
                    funcOperationPressed(getString(R.string.zero));
                }
                else
                {
                    funcOperationNotPressed(getString(R.string.zero));
                }
                break;
            // endregion

            case R.id.btn1:
                // region Button One
                if (isOperationButtonPressed)
                {
                    funcOperationPressed(getString(R.string.one));
                }
                else
                {
                    funcOperationNotPressed(getString(R.string.one));
                }
                break;
            // endregion

            case R.id.btn2:
                // region Button Two
                if (isOperationButtonPressed)
                {
                    funcOperationPressed(getString(R.string.two));
                }
                else
                {
                    funcOperationNotPressed(getString(R.string.two));
                }
                break;
            // endregion

            case R.id.btn3:
                // region Button Three
                if (isOperationButtonPressed)
                {
                    funcOperationPressed(getString(R.string.three));
                }
                else
                {
                    funcOperationNotPressed(getString(R.string.three));
                }
                break;
            // endregion

            case R.id.btn4:
                // region Button Four
                if (isOperationButtonPressed)
                {
                    funcOperationPressed(getString(R.string.four));
                }
                else
                {
                    funcOperationNotPressed(getString(R.string.four));
                }
                break;
            // endregion

            case R.id.btn5:
                // region Button Five
                if (isOperationButtonPressed)
                {
                    funcOperationPressed(getString(R.string.five));
                }
                else
                {
                    funcOperationNotPressed(getString(R.string.five));
                }
                break;
            // endregion

            case R.id.btn6:
                // region Button Six
                if (isOperationButtonPressed)
                {
                    funcOperationPressed(getString(R.string.six));
                }
                else
                {
                    funcOperationNotPressed(getString(R.string.six));
                }
                break;
            // endregion

            case R.id.btn7:
                // region Button Seven
                if (isOperationButtonPressed)
                {
                    funcOperationPressed(getString(R.string.seven));
                }
                else
                {
                    funcOperationNotPressed(getString(R.string.seven));
                }
                break;
            // endregion

            case R.id.btn8:
                // region Button Eight
                if (isOperationButtonPressed)
                {
                    funcOperationPressed(getString(R.string.eight));
                }
                else
                {
                    funcOperationNotPressed(getString(R.string.eight));
                }
                break;
            // endregion

            case R.id.btn9:
                // region Button Nine
                if (isOperationButtonPressed)
                {
                    funcOperationPressed(getString(R.string.nine));
                }
                else
                {
                    funcOperationNotPressed(getString(R.string.nine));
                }
                break;
            // endregion

            case R.id.btn_dot:
                // region Button Dot
                if (!tvInput.getText().toString().contains(getString(R.string.dot)))
                {
                    if (tvInput.getText().toString().equals(getString(R.string.zero)) || tvInput.getText().toString().equals(""))
                    {
                        tvInput.setText(getString(R.string.zero) + getString(R.string.dot));
                    }
                    else
                    {
                        tvInput.setText(tvInput.getText() + getString(R.string.dot));
                    }
                    hideOutput();
                    //isDotPressed = true;
                }
                break;
            // endregion

            case R.id.btnPlusMinus:
                // region Button PlusMinus
                if (!tvInput.getText().equals(getString(R.string.zero)) && !tvInput.getText().equals(""))
                {
                    double value = Double.parseDouble(tvInput.getText().toString());
                    value = value * (-1);

                    tvInput.setText(doubleResult(value));
                }
                break;
            // endregion

            case R.id.btnEqual:
                // region Button Equal
                scrollItem();

                if (!isFirstNumber && isOperationPressed)
                {
                    tvFinalResult.setText(strValue + getString(R.string.equal));
                    tvInput.setText(doubleResult(result));
                }

                // region Checking First Value
                if (isFirstNumber)
                {
                    if (!tvInput.getText().toString().equals(""))
                    {
                        myValueTwo = Double.parseDouble(tvInput.getText().toString());

                        // region Checking Second Value
                        if (!String.valueOf(myValueTwo).equals(""))
                        {

                            // region Perform Operation
                            result = performCalculation(myValueOne, operation, myValueTwo);
                            // endregion

                            if (strValue.endsWith("+") || strValue.endsWith("-") || strValue.endsWith("x") || strValue.endsWith("÷"))
                            {
                                strValue = strValue.substring(0, strValue.length() - 1) + "";
                            }

                            strValue = strValue + operation + doubleResult(myValueTwo);

                            tvFinalResult.setText(strValue + getString(R.string.equal));
                            tvInput.setText(doubleResult(result));

                            dataIntoList(myValueOne, myValueTwo, operation, result);

                            PreferenceManager.writeListInPreference(getApplicationContext(), mySharedPrefList);
                            myAdapter.notifyDataSetChanged();
                            recyclerView.smoothScrollToPosition(mySharedPrefList.size() - 1);

                            changeBooleans();
                        }
                        //endregion
                    }
                    else
                    {
                        switch (previousOperator)
                        {
                            case PLUS_CHAR:
                                answer = (myValueOne - mValue);
                                mOperation = '+';
                                break;

                            case MINUS_CHAR:
                                answer = (myValueOne + mValue);
                                mOperation = '-';
                                break;

                            case MULTIPLY_CHAR:
                                answer = (myValueOne / mValue);
                                mOperation = 'x';
                                break;

                            case DIVIDE_CHAR:
                                answer = (myValueOne * mValue);
                                mOperation = '÷';
                                break;

                            default:
                                break;
                        }

                        dataIntoList(answer, mValue, mOperation, myValueOne);

                        PreferenceManager.writeListInPreference(getApplicationContext(), mySharedPrefList);
                        myAdapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(mySharedPrefList.size() - 1);

                        hideOutput();
                        strValue = strValue.substring(0, strValue.length() - 1) + getString(R.string.equal);
                        tvFinalResult.setText(strValue);
                        tvInput.setText(doubleResult(myValueOne));

                        changeBooleans();
                    }
                }
                // endregion
                break;
            // endregion

            case R.id.btnClear:
                // region Button Clear
                clearData();
                break;
            // endregion

            case R.id.btnClearDigit:
                // region Button Clear Digit
                String display = tvInput.getText().toString();

                if(!TextUtils.isEmpty(display) && !display.equals("0"))
                {
                    hideOutput();
                    display  = display.substring(0, display.length() - 1);
                    tvInput.setText(display);
                }
                break;
            // endregion

            default:
                break;
        }
    }
    // endregion

    // region Functions

    // region Getting 1st Value
    private Double getFirstValue() {

        double value = Double.parseDouble(tvInput.getText().toString());

        if (isEqualPressed)
        {
            isEqualPressed = false;
        }

        isFirstNumber = true;
        isOperationPressed = true;

        return value;
    }
    // endregion

    private void changeBooleans() {

        if (isOperationPressed)
        {
            isOperationPressed = false;
        }

        isEqualPressed = true;
        isFirstNumber = false;
        isFirstTimeOperationPressed = true;
        firstPlus = true;
        firstMinus = true;
        firstMultiply = true;
        firstDivide = true;
        strValue = "";
    }

    // region Saving Data into List
    private void dataIntoList(double mFirstValue, double mSecondValue, Character mOperation, Double mResult) {

        MyCalculationClass myObject = new MyCalculationClass();
        myCalculationList = new ArrayList<>();
        myObject.setNum1(mFirstValue);
        myObject.setNum2(mSecondValue);
        myObject.setOperation(mOperation);
        myObject.setResult(mResult);
        myCalculationList.add(myObject);
        mySharedPrefList.addAll(myCalculationList);
    }
    // endregion

    // region Calculation Function
    private Double performCalculation(double valueOne, Character operation, double valueTwo) {

        double mResult = 0;

        switch (operation)
        {
            case '+':
                mResult = valueOne + valueTwo;
                break;
            case '-':
                mResult = valueOne - valueTwo;
                break;
            case 'x':
                mResult = valueOne * valueTwo;
                break;
            case '÷':
                if (valueTwo != 0)
                {
                    mResult = valueOne / valueTwo;
                }
//                                    else
//                                    {
//                                        Toast.makeText(this, "Can't divided by 0", Toast.LENGTH_SHORT).show();
//                                        tvInput.setText("");
//                                    }
                break;
            default:
                break;
        }

        return mResult;
    }
    // endregion

    // region Hide/Show Output
    private void hideShowFunction() {

        if (tvInput.getText().toString().equals(""))
        {
            showOutput();
        }
        else
        {
            hideOutput();
        }
    }

    // region Show Output Result
    private void showOutput()
    {
        isOperationButtonPressed = true;
        tvInput.setVisibility(View.INVISIBLE);
        scrollView2.setVisibility(View.VISIBLE);
        tvTempResult.setVisibility(View.VISIBLE);
        tvTempResult.setText(doubleResult(myValueOne));
    }
    // endregion

    // region Hide Output Result
    private void hideOutput()
    {
        isOperationButtonPressed = false;
        tvTempResult.setText("");
        tvInput.setVisibility(View.VISIBLE);
        scrollView2.setVisibility(View.INVISIBLE);
        tvTempResult.setVisibility(View.INVISIBLE);
    }
    // endregion
    // endregion

    // region Operation Replace Functions
    private void mFunctionPlus(String strResult) {

        if (!strResult.equals(""))
        {
            myVal = getFirstValue();

            if (strResult.endsWith("-"))
            {
                myValueOne = myValueOne - getFirstValue();
            }
            else if (strResult.endsWith("x"))
            {
                myValueOne = myValueOne * getFirstValue();
            }
            else if (strResult.endsWith("÷"))
            {
                myValueOne = myValueOne / getFirstValue();
            }
        }
    }

    private void mFunctionMinus(String strResult) {

        if (!strResult.equals(""))
        {
            myVal = getFirstValue();

            if (strResult.endsWith("+"))
            {
                myValueOne = myValueOne + getFirstValue();
            }
            else if (strResult.endsWith("x"))
            {
                myValueOne = myValueOne * getFirstValue();
            }
            else if (strResult.endsWith("÷"))
            {
                myValueOne = myValueOne / getFirstValue();
            }
        }
    }

    private void mFunctionMultiply(String strResult) {

        if (!strResult.equals(""))
        {
            myVal = getFirstValue();

            if (strResult.endsWith("+"))
            {
                myValueOne = myValueOne + getFirstValue();
            }
            else if (strResult.endsWith("-"))
            {
                myValueOne = myValueOne - getFirstValue();
            }
            else if (strResult.endsWith("÷"))
            {
                myValueOne = myValueOne / getFirstValue();
            }
        }
    }

    private void mFunctionDivide(String strResult) {

        if (!strResult.equals(""))
        {
            myVal = getFirstValue();

            if (strResult.endsWith("+"))
            {
                myValueOne = myValueOne + getFirstValue();
            }
            else if (strResult.endsWith("-"))
            {
                myValueOne = myValueOne - getFirstValue();
            }
            else if (strResult.endsWith("x"))
            {
                myValueOne = myValueOne * getFirstValue();
            }
        }
    }
    // endregion

    // region Result Function
    @SuppressLint("DefaultLocale")
    private String doubleResult(Double doubleValue)
    {
        String[] splitter = doubleValue.toString().split("\\.");
        boolean answer = ((splitter[1].startsWith(String.valueOf(0)) && (splitter[1].length() == 1)) || splitter[1].equals("00"));   // After  Decimal Count

        if (answer)
        {
            return String.valueOf(splitter[0]);
        }
        else
        {
            DecimalFormat decimalFormat = new DecimalFormat("0.##");
            return String.valueOf(decimalFormat.format(doubleValue));
        }
    }
    // endregion

    // region Number Button Pressed
    private void funcOperationPressed(String value) {

        hideOutput();
        tvInput.setText(value);
    }

    private void funcOperationNotPressed(String value) {

        if (isEqualPressed)
        {
            setFieldsData(value);
        }
        else
        {
            setTextData(value);
        }
    }
    // endregion

    // region Show Result
    private void showResult(char ch, String mChar, double myValue) {

        strValue = strValue + doubleResult(myValue) + mChar;
        setTextAndOperator(ch);
        tvFinalResult.setText(strValue);
        isOperationPressed = true;
    }
    // endregion

    private void mFunction() {

        myVal = getFirstValue();
        myValueOne = getFirstValue();
        tvInput.setText("");
    }
    // endregion

    // region Clear Data
    private void clearData() {

        myValueOne = 0;
        myValueTwo = 0;
        strValue = "";
        tvInput.setText(getString(R.string.zero));
        tvFinalResult.setText("");
        tvTempResult.setText("");
        tvInput.setVisibility(View.VISIBLE);
        scrollView2.setVisibility(View.INVISIBLE);
        tvTempResult.setVisibility(View.INVISIBLE);
        isOperationPressed = false;
        isEqualPressed = false;
        isFirstNumber = false;
        isFirstTimeOperationPressed = true;
        firstPlus = true;
        firstMinus = true;
        firstMultiply = true;
        firstDivide = true;
    }
    // endregion

    // region TextView Scrolling
    private void scrollItem()
    {
        scrollView1.post(() -> scrollView1.fullScroll(ScrollView.FOCUS_RIGHT));

        scrollView2.post(() -> scrollView2.fullScroll(ScrollView.FOCUS_RIGHT));
    }

    // endregion

}