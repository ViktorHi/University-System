package universitysystem.controllers;

import universitysystem.model.structs.User;

public interface Controllable {

    void setCurrentUser(User user);
    void setControllable(Controllable controllable);
    void updateParent();

}
