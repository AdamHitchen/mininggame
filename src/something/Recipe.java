package something;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    List<Integer> materials = new ArrayList<Integer>();
    List<Integer> numMats = new ArrayList<Integer>();
    private int result;
    private int resultAmount;
    String craftName = "";

    public Recipe() {

    }

    public List returnMaterials() {
        return materials;
    }

    public List returnNumMats() {
        return numMats;
    }

    public void addMaterial(int mat) {
        materials.add(mat);
    }

    public void addReq(int req) {
        numMats.add(req);
    }

    public int returnMaterial(int i) {
        return materials.get(i);
    }

    public void setResult(int r) {
        result = r;
    }

    public void setResultQ(int Q) {
        resultAmount = Q;
    }

    public int returnResult() {
        return result;
    }

    public int returnResultQ() {
        return resultAmount;
    }

    public String returnName() {
        return craftName;
    }

    public void setName(String name) {
        craftName = name;
    }
}
