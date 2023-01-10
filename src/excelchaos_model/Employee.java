package excelchaos_model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "Employees")
public class Employee {
    @DatabaseField(id = true)
    private int id;
    @DatabaseField()
    private String name;
    @DatabaseField()
    private String surname;
    @DatabaseField()
    private String email_private;
    @DatabaseField()
    private String phone_private;
    @DatabaseField()
    private String citizenship_1;
    @DatabaseField()
    private String citizenship_2;
    @DatabaseField()
    private String employee_number;
    @DatabaseField()
    private String tu_id;
    @DatabaseField()
    private boolean visa_required;
    @DatabaseField()
    private String status;
    @DatabaseField()
    private String transponder_number;
    @DatabaseField()
    private String office_number;
    @DatabaseField()
    private String phone_tuda;
    @DatabaseField()
    private String salary_planned_until;
    @DatabaseField(dataType = DataType.DATE_STRING, format = "yyyy-MM-dd")
    private Date visa_expiration;
    @DatabaseField(dataType = DataType.DATE_STRING, format = "yyyy-MM-dd")
    private Date date_of_birth;
    @DatabaseField()
    private String house_number;
    @DatabaseField()
    private String zip_code;
    @DatabaseField()
    private String additional_address;
    @DatabaseField()
    private String city;

    public Employee(){}
    public Employee(int id, String name, String surname, String email_private, String phone_private,
                    String citizenship_1, String citizenship_2, String employee_number, String tu_id, boolean visa_required,
                    String status, String transponder_number, String office_number, String phone_tuda,
                    String salary_planned_until, Date visa_expiration, Date date_of_birth, String house_number,
                    String zip_code, String additional_address, String city){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email_private = email_private;
        this.phone_private = phone_private;
        this.citizenship_1 = citizenship_1;
        this.citizenship_2 = citizenship_2;
        this.employee_number = employee_number;
        this.tu_id = tu_id;
        this.visa_required = visa_required;
        this.status = status;
        this.transponder_number = transponder_number;
        this.office_number = office_number;
        this.phone_tuda = phone_tuda;
        this.salary_planned_until = salary_planned_until;
        this.visa_expiration = visa_expiration;
        this.date_of_birth = date_of_birth;
        this.house_number = house_number;
        this.zip_code = zip_code;
        this.additional_address = additional_address;
        this.city = city;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSurname(){
        return this.surname;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public String getEmail_private(){
        return this.email_private;
    }

    public void setEmail_private(String email_private){
        this.email_private = email_private;
    }

    public String getPhone_private(){
        return this.phone_private;
    }

    public void setPhone_private(String phone_private){
        this.phone_private = phone_private;
    }

    public String getCitizenship_1(){
        return this.citizenship_1;
    }

    public void setCitizenship_1(String citizenship_1){
        this.citizenship_1 = citizenship_1;
    }

    public String getCitizenship_2(){
        return this.citizenship_2;
    }

    public void setCitizenship_2(String citizenship_2){
        this.citizenship_2 = citizenship_2;
    }

    public String getEmployee_number(){
        return this.employee_number;
    }

    public void setEmployee_number(String employee_number){
        this.employee_number = employee_number;
    }

    public String getTu_id(){
        return this.tu_id;
    }

    public void setTu_id(String tu_id){
        this.tu_id = tu_id;
    }

    public boolean getVisa_required(){
        return this.visa_required;
    }

    public void setVisa_required(boolean visa_required){
        this.visa_required = visa_required;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getTransponder_number(){
        return this.transponder_number;
    }

    public void setTransponder_number(String transponder_number){
        this.transponder_number = transponder_number;
    }

    public String getOffice_number(){
        return this.office_number;
    }

    public void setOffice_number(String office_number){
        this.office_number = office_number;
    }

    public String getPhone_tuda(){
        return this.phone_tuda;
    }

    public void setPhone_tuda(String phone_tuda){
        this.phone_tuda = phone_tuda;
    }

    public String getSalary_planned_until(){
        return this.salary_planned_until;
    }

    public void setSalary_planned_until(String salary_planned_until){
        this.salary_planned_until = salary_planned_until;
    }

    public Date getVisa_expiration(){
        return this.visa_expiration;
    }

    public void setVisa_expiration(Date visa_expiration){
        this.visa_expiration = visa_expiration;
    }

    public void setDate_of_birth(Date date_of_birth){
        this.date_of_birth = date_of_birth;
    }

    public Date getDate_of_birth(){
        return this.date_of_birth;
    }

    public void setHouse_number(String house_number){
        this.house_number = house_number;
    }

    public String getHouse_number(){
        return this.house_number;
    }

    public String getZip_code(){
        return this.zip_code;
    }

    public void setZip_code(String zip_code){
        this.zip_code = zip_code;
    }

    public void setAdditional_address(String additional_address){
        this.additional_address = additional_address;
    }

    public String getAdditional_address(){
        return this.additional_address;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return this.city;
    }
}
