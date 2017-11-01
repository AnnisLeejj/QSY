package com.heking.qsy.activity.Patrol;

import java.util.List;

public class PatrolTableGroup {
    private int ID;
    private String ProjectName;
    private List<PatrolTableChild> Items;

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        this.ID = iD;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public List<PatrolTableChild> getItems() {
        return Items;
    }

    public void setItems(List<PatrolTableChild> items) {
        Items = items;
    }

}
