package universitysystem.model.structs;

import universitysystem.model.Const;

import java.util.ArrayList;
import java.util.List;

public class Degree extends BaseStruct {
    public Degree(int id, String fullName, String shortName, String codeName) {
        super(id, fullName, shortName, codeName);
    }

    public Degree(String fullName, String shortName, String codeName) {
        super(fullName, shortName, codeName);
    }

    public Degree(BaseStruct baseStruct) {
        super(baseStruct.id,baseStruct.fullName,baseStruct.shortName, baseStruct.codeName);
    }

    public static List<Degree> getUniversitiesFromDB(){
        ArrayList<Degree> ans = new ArrayList<>();
        getBaseStructs(Const.DEGREE_NAMES_TABLE).forEach(elem ->{
            ans.add(new Degree(elem));
        });
        return ans;
    }

    public static boolean addNewDegree(BaseStruct university){
        return addNewStruct(university, Const.DEGREE_NAMES_TABLE);
    }


}
