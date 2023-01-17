package excelchaos_model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "SalaryTable")
public class SalaryTable {
    @DatabaseField()
    private String table_name;
    @DatabaseField()
    private String paygrade;
    @DatabaseField()
    private double grundendgeld;
    @DatabaseField()
    private double av_ag_anteil_lfd_entgelt;
    @DatabaseField()
    private double kv_ag_anteil_lfd_entgelt;
    @DatabaseField()
    private double zusbei_af_lfd_entgelt;
    @DatabaseField()
    private double pv_ag_anteil_lfd_entgelt;
    @DatabaseField()
    private double rv_ag_anteil_lfd_entgelt;
    @DatabaseField()
    private double sv_umlage_u2;
    @DatabaseField()
    private double steuern_ag;
    @DatabaseField()
    private double zv_Sanierungsbeitrag;
    @DatabaseField()
    private double zv_umlage_allgemein;
    @DatabaseField()
    private double vbl_wiss_4perc_ag_buchung;
    @DatabaseField()
    private double mtl_kosten_ohne_jsz;
    @DatabaseField()
    private double jsz_als_monatliche_zulage;
    @DatabaseField()
    private double mtl_kosten_mit_jsz;
    @DatabaseField()
    private double jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung;

    public SalaryTable(String table_name, double grundendgeld, double av_ag_anteil_lfd_entgelt, double
                       kv_ag_anteil_lfd_entgelt, double zusbei_af_lfd_entgelt, double pv_ag_anteil_lfd_entgelt,
                       double rv_ag_anteil_lfd_entgelt, double sv_umlage_u2, double steuern_ag, double zv_Sanierungsbeitrag,
                       double zv_umlage_allgemein, double vbl_wiss_4perc_ag_buchung, double mtl_kosten_ohne_jsz,
                       double jsz_als_monatliche_zulage, double mtl_kosten_mit_jsz,
                       double jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung, String paygrade){
        this.table_name = table_name;
        this.grundendgeld = grundendgeld;
        this.av_ag_anteil_lfd_entgelt = av_ag_anteil_lfd_entgelt;
        this.kv_ag_anteil_lfd_entgelt = kv_ag_anteil_lfd_entgelt;
        this.zusbei_af_lfd_entgelt = zusbei_af_lfd_entgelt;
        this.pv_ag_anteil_lfd_entgelt = pv_ag_anteil_lfd_entgelt;
        this.rv_ag_anteil_lfd_entgelt = rv_ag_anteil_lfd_entgelt;
        this.sv_umlage_u2 = sv_umlage_u2;
        this.steuern_ag = steuern_ag;
        this.zv_Sanierungsbeitrag = zv_Sanierungsbeitrag;
        this.zv_umlage_allgemein = zv_umlage_allgemein;
        this.vbl_wiss_4perc_ag_buchung = vbl_wiss_4perc_ag_buchung;
        this.mtl_kosten_ohne_jsz = mtl_kosten_ohne_jsz;
        this.jsz_als_monatliche_zulage = jsz_als_monatliche_zulage;
        this.mtl_kosten_mit_jsz = mtl_kosten_mit_jsz;
        this.jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung = jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung;
        this.paygrade = paygrade;
    }

    public SalaryTable(){

    }

    public  String getTable_name(){
        return this.table_name;
    }

    public void setTable_name(String table_name){
        this.table_name = table_name;
    }

    public double getGrundendgeld(){
        return this.grundendgeld;
    }

    public void setGrundendgeld(double grundendgeld){
        this.grundendgeld = grundendgeld;
    }

    public double getAv_ag_anteil_lfd_entgelt(){
        return this.av_ag_anteil_lfd_entgelt;
    }

    public void setAv_ag_anteil_lfd_entgelt(double av_ag_anteil_lfd_entgelt){
        this.av_ag_anteil_lfd_entgelt = av_ag_anteil_lfd_entgelt;
    }

    public double getKv_ag_anteil_lfd_entgelt(){
        return this.kv_ag_anteil_lfd_entgelt;
    }

    public void setKv_ag_anteil_lfd_entgelt(double kv_ag_anteil_lfd_entgelt){
        this.kv_ag_anteil_lfd_entgelt = kv_ag_anteil_lfd_entgelt;
    }

    public double getZusbei_af_lfd_entgelt(){
        return this.zusbei_af_lfd_entgelt;
    }

    public void setZusbei_af_lfd_entgelt(double zusbei_af_lfd_entgelt){
        this.zusbei_af_lfd_entgelt = zusbei_af_lfd_entgelt;
    }

    public double getPv_ag_anteil_lfd_entgelt(){
        return this.pv_ag_anteil_lfd_entgelt;
    }

    public void setPv_ag_anteil_lfd_entgelt(double pv_ag_anteil_lfd_entgelt){
        this.pv_ag_anteil_lfd_entgelt = pv_ag_anteil_lfd_entgelt;
    }

    public double getRv_ag_anteil_lfd_entgelt(){
        return this.rv_ag_anteil_lfd_entgelt;
    }

    public void setRv_ag_anteil_lfd_entgelt(double rv_ag_anteil_lfd_entgelt){
        this.rv_ag_anteil_lfd_entgelt = rv_ag_anteil_lfd_entgelt;
    }

    public double getSv_umlage_u2(){
        return this.sv_umlage_u2;
    }

    public void setSv_umlage_u2(double sv_umlage_u2){
        this.sv_umlage_u2 = sv_umlage_u2;
    }

    public double getSteuern_ag(){
        return this.steuern_ag;
    }

    public void setSteuern_ag(double steuern_ag){
        this.steuern_ag = steuern_ag;
    }

    public double getZv_Sanierungsbeitrag(){
        return this.zv_Sanierungsbeitrag;
    }

    public void setZv_Sanierungsbeitrag(double zv_Sanierungsbeitrag){
        this.zv_Sanierungsbeitrag = zv_Sanierungsbeitrag;
    }

    public double getZv_umlage_allgemein(){
        return this.zv_umlage_allgemein;
    }

    public void setZv_umlage_allgemein(double zv_umlage_allgemein){
        this.zv_umlage_allgemein = zv_umlage_allgemein;
    }

    public double getVbl_wiss_4perc_ag_buchung(){
        return this.vbl_wiss_4perc_ag_buchung;
    }

    public void setVbl_wiss_4perc_ag_buchung(double vbl_wiss_4perc_ag_buchung){
        this.vbl_wiss_4perc_ag_buchung = vbl_wiss_4perc_ag_buchung;
    }

    public double getMtl_kosten_ohne_jsz(){
        return this.mtl_kosten_ohne_jsz;
    }

    public void setMtl_kosten_ohne_jsz(double mtl_kosten_ohne_jsz){
        this.mtl_kosten_ohne_jsz = mtl_kosten_ohne_jsz;
    }

    public double getJsz_als_monatliche_zulage(){
        return this.jsz_als_monatliche_zulage;
    }

    public void setJsz_als_monatliche_zulage(double jsz_als_monatliche_zulage){
        this.jsz_als_monatliche_zulage = jsz_als_monatliche_zulage;
    }

    public double getMtl_kosten_mit_jsz(){
        return this.mtl_kosten_mit_jsz;
    }

    public void setMtl_kosten_mit_jsz(double mtl_kosten_mit_jsz){
        this.mtl_kosten_mit_jsz = mtl_kosten_mit_jsz;
    }

    public double getJaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung(){
        return jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung;
    }

    public void  setJaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung(double jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung){
        this.jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung = jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung;
    }

    public String getPaygrade(){
        return this.paygrade;
    }

    public void setPaygrade(String paygrade){
        this.paygrade = paygrade;
    }
}
