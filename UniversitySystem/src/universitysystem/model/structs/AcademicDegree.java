package universitysystem.model.structs;

import universitysystem.model.Const;

import java.util.ArrayList;
import java.util.List;

public class AcademicDegree extends BaseStruct{
    public AcademicDegree(int id, String fullName, String shortName, String codeName) {
        super(id, fullName, shortName, codeName);
    }

    public AcademicDegree(String fullName, String shortName, String codeName) {
        super(fullName, shortName, codeName);
    }

    public AcademicDegree(BaseStruct baseStruct) {
        super(baseStruct.id,baseStruct.fullName,baseStruct.shortName, baseStruct.codeName);
    }

    public static List<AcademicDegree> getAcademicDegreeFromDB(){
        ArrayList<AcademicDegree> ans = new ArrayList<>();
        getBaseStructs(Const.ACADEMIC_DEGREE_NAMES_TABLE).forEach(elem ->{
            ans.add(new AcademicDegree(elem));
        });
        return ans;
    }

    public static boolean addNewAcademicDegree(BaseStruct university){
        return addNewStruct(university, Const.ACADEMIC_DEGREE_NAMES_TABLE);
    }


}
