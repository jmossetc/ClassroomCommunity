package paci.iut.classroomcommunity;

/**
 * Created by Vincent
 * on 03/05/2018.
 */

public class CategoryQuestion {
    private int id;
    private String cat;

    @Override
    public String toString() {
        return cat;
    }

    public CategoryQuestion(int id, String cat) {
        this.id = id;
        this.cat=cat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
}
