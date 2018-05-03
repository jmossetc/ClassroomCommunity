package paci.iut.classroomcommunity;

import android.os.Parcel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent
 * on 03/05/2018.
 */

public class Question implements Serializable {

    private String catégorie;
    private String difficulté;
    private String question;
    private String bonne_réponse;
    private List<String> mauvaises_réponses;

    public Question(String catégorie, String difficulté, String question, String bonne_réponse, List<String> mauvaises_réponses) {
        this.catégorie = catégorie;
        this.difficulté = difficulté;
        this.question = question;
        this.bonne_réponse = bonne_réponse;
        this.mauvaises_réponses = mauvaises_réponses;
    }

    public Question(Parcel i) {
       catégorie= i.readString();
        difficulté=i.readString();
        question=i.readString();
        bonne_réponse=i.readString();
        mauvaises_réponses = new ArrayList<String>();
        i.readList(mauvaises_réponses,null);
    }

    public String getCatégorie() {
        return catégorie;
    }

    public void setCatégorie(String catégorie) {
        this.catégorie = catégorie;
    }

    public String getDifficulté() {
        return difficulté;
    }

    public void setDifficulté(String difficulté) {
        this.difficulté = difficulté;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getBonne_réponse() {
        return bonne_réponse;
    }

    public void setBonne_réponse(String bonne_réponse) {
        this.bonne_réponse = bonne_réponse;
    }

    public List<String> getMauvaises_réponses() {
        return mauvaises_réponses;
    }

    public void setMauvaises_réponses(List<String> mauvaises_réponses) {
        this.mauvaises_réponses = mauvaises_réponses;
    }


}
