package universitysystem.model.structs;

import universitysystem.model.Const;

import java.util.ArrayList;
import java.util.List;


public class University extends BaseStruct {

    public University(String fullName, String shortName, String codeName) {
        super(fullName, shortName, codeName);
    }

    public University(int id, String fullName, String shortName, String codeName) {
        super(id, fullName, shortName, codeName);
    }

    public University(BaseStruct baseStruct) {
        super(baseStruct.id,baseStruct.fullName,baseStruct.shortName, baseStruct.codeName);
    }

    public static List<University> getUniversitiesFromDB(){
        ArrayList<University> ans = new ArrayList<>();
        getBaseStructs(Const.UNIVERSITY_NAMES_TABLE).forEach(elem ->{
            ans.add(new University(elem));
        });
        return ans;
    }

    public static boolean addNewUniversity(BaseStruct university){
        return addNewStruct(university, Const.UNIVERSITY_NAMES_TABLE);
    }
}
