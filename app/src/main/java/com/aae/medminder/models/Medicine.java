package com.aae.medminder.models;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

@Entity (nameInDb = "Medicine")
public class Medicine {

    @Id(autoincrement = true)
    @Property(nameInDb = "medicineID")
    private Long medicineID;

    @Property(nameInDb = "barcode")
    private String barcode;

    @Property(nameInDb = "name")
    private String name;

    @Property(nameInDb = "count")
    private Long count;

    private String medicineUnitID;
    @ToOne(joinProperty = "medicineUnitID")
    private MedicineUnit medicineUnit;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 64903361)
    private transient MedicineDao myDao;
    @Generated(hash = 456478126)
    public Medicine(Long medicineID, String barcode, String name, Long count,
            String medicineUnitID) {
        this.medicineID = medicineID;
        this.barcode = barcode;
        this.name = name;
        this.count = count;
        this.medicineUnitID = medicineUnitID;
    }
    @Generated(hash = 1065091254)
    public Medicine() {
    }
    public Long getMedicineID() {
        return this.medicineID;
    }
    public void setMedicineID(Long medicineID) {
        this.medicineID = medicineID;
    }
    public String getBarcode() {
        return this.barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getCount() {
        return this.count;
    }
    public void setCount(Long count) {
        this.count = count;
    }
    public String getMedicineUnitID() {
        return this.medicineUnitID;
    }
    public void setMedicineUnitID(String medicineUnitID) {
        this.medicineUnitID = medicineUnitID;
    }
    @Generated(hash = 659188682)
    private transient String medicineUnit__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1578340196)
    public MedicineUnit getMedicineUnit() {
        String __key = this.medicineUnitID;
        if (medicineUnit__resolvedKey == null
                || medicineUnit__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MedicineUnitDao targetDao = daoSession.getMedicineUnitDao();
            MedicineUnit medicineUnitNew = targetDao.load(__key);
            synchronized (this) {
                medicineUnit = medicineUnitNew;
                medicineUnit__resolvedKey = __key;
            }
        }
        return medicineUnit;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1587573977)
    public void setMedicineUnit(MedicineUnit medicineUnit) {
        synchronized (this) {
            this.medicineUnit = medicineUnit;
            medicineUnitID = medicineUnit == null ? null
                    : medicineUnit.getMedicineUnitID();
            medicineUnit__resolvedKey = medicineUnitID;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 734240498)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMedicineDao() : null;
    }
}
