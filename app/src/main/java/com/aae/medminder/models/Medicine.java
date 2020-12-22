package com.aae.medminder.models;

public class Medicine {
    private int medicineID;
    private String barcode;
    private String name;
    private String medicineUnitID;
    private int inventory;

    public Medicine(int medicineID, String barcode,
                    String name, String medicineUnitID, int inventory) {
        this.medicineID = medicineID;
        this.barcode = barcode;
        this.name = name;
        this.medicineUnitID = medicineUnitID;
        this.inventory = inventory;
    }

    public int getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(int medicineID) {
        this.medicineID = medicineID;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMedicineUnitID() {
        return medicineUnitID;
    }

    public void setMedicineUnitID(String medicineUnitID) {
        this.medicineUnitID = medicineUnitID;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }
}
