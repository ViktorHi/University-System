package universitysystem.model.structs;

import universitysystem.model.Const;

import java.util.ArrayList;
import java.util.List;

public class Faculty extends BaseStruct {
    public Faculty(String fullName, String shortName, String codeName) {
        super(fullName, shortName, codeName);
    }

    public Faculty(int id, String fullName, String shortName, String codeName) {
        super(id, fullName, shortName, codeName);
    }

    public Faculty(BaseStruct baseStruct) {
        super(baseStruct.id,baseStruct.fullName,baseStruct.shortName, baseStruct.codeName);
    }

    public static List<Faculty> getUniversitiesFromDB(){
        ArrayList<Faculty> ans = new ArrayList<>();
        getBaseStructs(Const.FACULTY_NAMES_TABLE).forEach(elem ->{
            ans.add(new Faculty(elem));
        });
        return ans;
    }

    public static boolean addNewFaculty(BaseStruct university){
        return addNewStruct(university, Const.FACULTY_NAMES_TABLE);
    }
}
