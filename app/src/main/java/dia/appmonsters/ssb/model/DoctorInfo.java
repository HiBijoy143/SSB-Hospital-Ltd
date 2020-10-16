package dia.appmonsters.ssb.model;

import java.io.Serializable;

public class DoctorInfo implements Serializable {
    private int id;
    private String name;
    private String degress;
    private String department;
    private String designation;
    private String phone;
    private String email;
    private String offday;
    private byte[] image;

    public DoctorInfo() {
    }

    public DoctorInfo(int id, String name, String degress, String department, String designation, String phone, String email, String offday, byte[] image) {
        this.id = id;
        this.name = name;
        this.degress = degress;
        this.department = department;
        this.designation = designation;
        this.phone = phone;
        this.email = email;
        this.offday = offday;
        this.image = image;
    }

    public DoctorInfo(String name, String degress, String department, String designation, String phone, String email, String offday, byte[] image) {
        this.name = name;
        this.degress = degress;
        this.department = department;
        this.designation = designation;
        this.phone = phone;
        this.email = email;
        this.offday = offday;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegress() {
        return degress;
    }

    public void setDegress(String degress) {
        this.degress = degress;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOffday() {
        return offday;
    }

    public void setOffday(String offday) {
        this.offday = offday;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
