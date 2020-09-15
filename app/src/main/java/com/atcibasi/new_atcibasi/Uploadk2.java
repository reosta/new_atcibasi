package com.atcibasi.new_atcibasi;

public class Uploadk2 {
    private String mk2Name;
    private String mk2ImageUrl;

    public Uploadk2(){

    }

   public Uploadk2(String name, String imageUrl){

        if (name.trim().equals("")){

            name="No name";
        }

        mk2Name=name;
        mk2ImageUrl=imageUrl;
    }
    public String getmName(){
        return mk2Name;
    }
    public void setmName(String name){
        mk2Name=name;
    }
    public String getmImageUrl(){
        return mk2ImageUrl;
    }
    public void setmImageUrl(String imageUrl){
        mk2ImageUrl=imageUrl;
    }

}
