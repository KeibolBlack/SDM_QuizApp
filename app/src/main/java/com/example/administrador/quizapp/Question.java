package com.example.administrador.quizapp;

/**
 * Created by AlbertoCR on 12/03/2015.
 */
public class Question {

    private String number = null;
    private String text = null;
    private String answer1 = null;
    private String answer2 = null;
    private String answer3 = null;
    private String answer4 = null;
    private String right = null;
    private String audience = null;
    private String phone = null;
    private String fifty1 = null;
    private String fifty2= null;

    public Question(String number, String text, String answer1, String answer2, String answer3, String answer4, String right, String audience, String phone, String fifty1, String fifty2) {
        this.number = number;
        this.text = text;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.right = right;
        this.audience = audience;
        this.phone = phone;
        this.fifty1 = fifty1;
        this.fifty2= fifty2;
    }

    public Question() {
        number = null;
        text = null;
        answer1 = null;
        answer2 = null;
        answer3 = null;
        answer4 = null;
        right = null;
        audience = null;
        phone = null;
        fifty1 = null;
        fifty2= null;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFifty1() {
        return fifty1;
    }

    public void setFifty1(String fifty1) {
        this.fifty1 = fifty1;
    }

    public String getFifty2() {
        return fifty2;
    }

    public void setFifty2(String fifty2) {
        this.fifty2 = fifty2;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
