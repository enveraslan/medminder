package com.aae.medminder;

public class TreatmentDetail {

    private Long treatmentID;
    private String treatmentName;
    private String amount;
    private String time;
    private boolean expandable;
    private String treatmentType;

    public TreatmentDetail(Long treatmetID, String medicineName, String amount, String time, String treatementType) {
        this.treatmentID = treatmetID;
        this.treatmentName = medicineName;
        this.treatmentType = treatementType;
        this.amount = amount;
        this.time = time;
        this.expandable = false;
    }

    public void increaseAmount(){
        if(Integer.parseInt(this.amount) < 9)
            this.amount = String.valueOf(Integer.parseInt(this.amount) + 1);
    }

    public void decreaseAmount(){
        if(Integer.parseInt(this.amount) > 0)
            this.amount = String.valueOf(Integer.parseInt(this.amount) - 1);
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public Long getTreatmentID() {
        return treatmentID;
    }

    public void setTreatmentID(Long treatmentID) {
        this.treatmentID = treatmentID;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    @Override
    public String toString() {
        return "MedicineInfo{" +
                "medicineName='" + treatmentName + '\'' +
                ", amount='" + amount + '\'' +
                ", time='" + time + '\'' +
                ", expandable=" + expandable +
                '}';
    }
}
