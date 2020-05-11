package universitysystem.model.structs;

import universitysystem.model.Const;

import java.util.ArrayList;
import java.util.List;

public class Pulpit extends BaseStruct {
    public Pulpit(String fullName, String shortName, String codeName) {
        super(fullName, shortName, codeName);
    }

    public Pulpit(int id, String fullName, String shortName, String codeName) {
        super(id, fullName, shortName, codeName);
    }

    public Pulpit(BaseStruct baseStruct) {
        super(baseStruct.id,baseStruct.fullName,baseStruct.shortName, baseStruct.codeName);
    }

    public static List<Pulpit> getUniversitiesFromDB(){
        ArrayList<Pulpit> ans = new ArrayList<>();
        getBaseStructs(Const.PULPIT_NAMES_TABLE).forEach(elem ->{
            ans.add(new Pulpit(elem));
        });
        return ans;
    }

    public static boolean addNewPulpit(BaseStruct university){
        return addNewStruct(university, Const.PULPIT_NAMES_TABLE);
    }

}
