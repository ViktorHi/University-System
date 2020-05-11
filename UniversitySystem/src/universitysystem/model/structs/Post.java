package universitysystem.model.structs;

import universitysystem.model.Const;

import java.util.ArrayList;
import java.util.List;

public class Post extends BaseStruct {



    public Post(int id, String fullName, String shortName, String codeName) {
        super(id, fullName, shortName, codeName);
    }

    public Post(String fullName, String shortName, String codeName) {
        super(fullName, shortName, codeName);
    }

    public Post(BaseStruct baseStruct) {
        super(baseStruct.id,baseStruct.fullName,baseStruct.shortName, baseStruct.codeName);
    }

    public static List<Post> getPostNamesFromDB(){
        ArrayList<Post> ans = new ArrayList<>();
        getBaseStructs(Const.POST_NAMES_TABLE).forEach(elem ->{
            ans.add(new Post(elem));
        });
        return ans;
    }

    public static boolean addNewPostName(BaseStruct university){
        return addNewStruct(university, Const.POST_NAMES_TABLE);
    }


}
