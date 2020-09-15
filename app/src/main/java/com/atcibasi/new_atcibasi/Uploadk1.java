package com.atcibasi.new_atcibasi;

public class Uploadk1 {
    private String mk1Name;
    private String mk1ImageUrl;

    public Uploadk1(){

    }

    public Uploadk1(String name, String imageUrl){

        if (name.trim().equals("")){

            name="No name";
        }

        mk1Name=name;
        mk1ImageUrl=imageUrl;
    }
    public String getmName(){
        return mk1Name;
    }
    public void setmName(String name){
        mk1Name=name;
    }
    public String getmImageUrl(){
        return mk1ImageUrl;
    }
    public void setmImageUrl(String imageUrl){
        mk1ImageUrl=imageUrl;
    }

}
