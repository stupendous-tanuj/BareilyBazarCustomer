package com.app.customerapp.utils;


import com.app.customerapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umesh on 17/9/15.
 */
public class MenuListData {

    public static List<MenuData> getMenuData() {

        List<MenuData> menuDatas = new ArrayList<>();
        menuDatas.add(new MenuData(R.drawable.guests_accepted, "Home", false));
        menuDatas.add(new MenuData(R.drawable.guests_accepted, "Home", false));
        menuDatas.add(new MenuData(R.drawable.guests_accepted, "Home", false));
        menuDatas.add(new MenuData(R.drawable.guests_accepted, "Home", true));
        menuDatas.add(new MenuData(R.drawable.guests_accepted, "Home", false));
        menuDatas.add(new MenuData(R.drawable.guests_accepted, "Home", false));
        menuDatas.add(new MenuData(R.drawable.guests_accepted, "Home", true));
        menuDatas.add(new MenuData(R.drawable.guests_accepted, "Home", false));
        menuDatas.add(new MenuData(R.drawable.guests_accepted, "Home", false));
        menuDatas.add(new MenuData(R.drawable.guests_accepted, "Home", false));

        return menuDatas;
    }

    public static class MenuData {
        private int menuIcon;
        private String menuName;
        private boolean isline;

        public MenuData(int menuIcon, String menuName, boolean isline) {
            this.menuIcon = menuIcon;
            this.isline = isline;
            this.menuName = menuName;
        }

        public int getMenuIcon() {
            return menuIcon;
        }

        public void setMenuIcon(int menuIcon) {
            this.menuIcon = menuIcon;
        }

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public boolean isline() {
            return isline;
        }

        public void setIsline(boolean isline) {
            this.isline = isline;
        }
    }
}
