// Controller/HoaDonController.java
package Controller;

import Model.HoaDonModel;
import Object.HoaDon;

public class HoaDonController {
    private HoaDonModel model;

    public HoaDonController() {
        model = new HoaDonModel();
    }

    public boolean themHoaDon(HoaDon hd) {
        return model.themHoaDon(hd);
    }
}