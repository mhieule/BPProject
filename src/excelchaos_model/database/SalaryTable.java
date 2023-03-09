package excelchaos_model.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import excelchaos_model.utility.PayRateTableNameDateSeperator;

import java.math.BigDecimal;


@DatabaseTable(tableName = "SalaryTable")
public class SalaryTable {
    @DatabaseField()
    private String table_name;
    @DatabaseField()
    private String paygrade;
    @DatabaseField()
    private BigDecimal grundendgeld;
    @DatabaseField()
    private BigDecimal av_ag_anteil_lfd_entgelt;
    @DatabaseField()
    private BigDecimal kv_ag_anteil_lfd_entgelt;
    @DatabaseField()
    private BigDecimal zusbei_af_lfd_entgelt;
    @DatabaseField()
    private BigDecimal pv_ag_anteil_lfd_entgelt;
    @DatabaseField()
    private BigDecimal rv_ag_anteil_lfd_entgelt;
    @DatabaseField()
    private BigDecimal sv_umlage_u2;
    @DatabaseField()
    private BigDecimal steuern_ag;
    @DatabaseField()
    private BigDecimal zv_Sanierungsbeitrag;
    @DatabaseField()
    private BigDecimal zv_umlage_allgemein;
    @DatabaseField()
    private BigDecimal vbl_wiss_4perc_ag_buchung;
    @DatabaseField()
    private BigDecimal mtl_kosten_ohne_jsz;
    @DatabaseField()
    private BigDecimal jsz_als_monatliche_zulage;
    @DatabaseField()
    private BigDecimal mtl_kosten_mit_jsz;
    @DatabaseField()
    private BigDecimal jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung;

    public SalaryTable(String table_name, BigDecimal grundendgeld, BigDecimal av_ag_anteil_lfd_entgelt, BigDecimal
            kv_ag_anteil_lfd_entgelt, BigDecimal zusbei_af_lfd_entgelt, BigDecimal pv_ag_anteil_lfd_entgelt,
                       BigDecimal rv_ag_anteil_lfd_entgelt, BigDecimal sv_umlage_u2, BigDecimal steuern_ag, BigDecimal zv_Sanierungsbeitrag,
                       BigDecimal zv_umlage_allgemein, BigDecimal vbl_wiss_4perc_ag_buchung, BigDecimal mtl_kosten_ohne_jsz,
                       BigDecimal jsz_als_monatliche_zulage, BigDecimal mtl_kosten_mit_jsz,
                       BigDecimal jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung, String paygrade) {
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

    public SalaryTable() {

    }

    public String getTable_name() {
        return this.table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public BigDecimal getGrundendgeld() {
        return this.grundendgeld;
    }

    public void setGrundendgeld(BigDecimal grundendgeld) {
        this.grundendgeld = grundendgeld;
    }

    public BigDecimal getAv_ag_anteil_lfd_entgelt() {
        return this.av_ag_anteil_lfd_entgelt;
    }

    public void setAv_ag_anteil_lfd_entgelt(BigDecimal av_ag_anteil_lfd_entgelt) {
        this.av_ag_anteil_lfd_entgelt = av_ag_anteil_lfd_entgelt;
    }

    public BigDecimal getKv_ag_anteil_lfd_entgelt() {
        return this.kv_ag_anteil_lfd_entgelt;
    }

    public void setKv_ag_anteil_lfd_entgelt(BigDecimal kv_ag_anteil_lfd_entgelt) {
        this.kv_ag_anteil_lfd_entgelt = kv_ag_anteil_lfd_entgelt;
    }

    public BigDecimal getZusbei_af_lfd_entgelt() {
        return this.zusbei_af_lfd_entgelt;
    }

    public void setZusbei_af_lfd_entgelt(BigDecimal zusbei_af_lfd_entgelt) {
        this.zusbei_af_lfd_entgelt = zusbei_af_lfd_entgelt;
    }

    public BigDecimal getPv_ag_anteil_lfd_entgelt() {
        return this.pv_ag_anteil_lfd_entgelt;
    }

    public void setPv_ag_anteil_lfd_entgelt(BigDecimal pv_ag_anteil_lfd_entgelt) {
        this.pv_ag_anteil_lfd_entgelt = pv_ag_anteil_lfd_entgelt;
    }

    public BigDecimal getRv_ag_anteil_lfd_entgelt() {
        return this.rv_ag_anteil_lfd_entgelt;
    }

    public void setRv_ag_anteil_lfd_entgelt(BigDecimal rv_ag_anteil_lfd_entgelt) {
        this.rv_ag_anteil_lfd_entgelt = rv_ag_anteil_lfd_entgelt;
    }

    public BigDecimal getSv_umlage_u2() {
        return this.sv_umlage_u2;
    }

    public void setSv_umlage_u2(BigDecimal sv_umlage_u2) {
        this.sv_umlage_u2 = sv_umlage_u2;
    }

    public BigDecimal getSteuern_ag() {
        return this.steuern_ag;
    }

    public void setSteuern_ag(BigDecimal steuern_ag) {
        this.steuern_ag = steuern_ag;
    }

    public BigDecimal getZv_Sanierungsbeitrag() {
        return this.zv_Sanierungsbeitrag;
    }

    public void setZv_Sanierungsbeitrag(BigDecimal zv_Sanierungsbeitrag) {
        this.zv_Sanierungsbeitrag = zv_Sanierungsbeitrag;
    }

    public BigDecimal getZv_umlage_allgemein() {
        return this.zv_umlage_allgemein;
    }

    public void setZv_umlage_allgemein(BigDecimal zv_umlage_allgemein) {
        this.zv_umlage_allgemein = zv_umlage_allgemein;
    }

    public BigDecimal getVbl_wiss_4perc_ag_buchung() {
        return this.vbl_wiss_4perc_ag_buchung;
    }

    public void setVbl_wiss_4perc_ag_buchung(BigDecimal vbl_wiss_4perc_ag_buchung) {
        this.vbl_wiss_4perc_ag_buchung = vbl_wiss_4perc_ag_buchung;
    }

    public BigDecimal getMtl_kosten_ohne_jsz() {
        return this.mtl_kosten_ohne_jsz;
    }

    public void setMtl_kosten_ohne_jsz(BigDecimal mtl_kosten_ohne_jsz) {
        this.mtl_kosten_ohne_jsz = mtl_kosten_ohne_jsz;
    }

    public BigDecimal getJsz_als_monatliche_zulage() {
        return this.jsz_als_monatliche_zulage;
    }

    public void setJsz_als_monatliche_zulage(BigDecimal jsz_als_monatliche_zulage) {
        this.jsz_als_monatliche_zulage = jsz_als_monatliche_zulage;
    }

    public BigDecimal getMtl_kosten_mit_jsz() {
        return this.mtl_kosten_mit_jsz;
    }

    public void setMtl_kosten_mit_jsz(BigDecimal mtl_kosten_mit_jsz) {
        this.mtl_kosten_mit_jsz = mtl_kosten_mit_jsz;
    }

    public BigDecimal getJaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung() {
        return jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung;
    }

    public void setJaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung(BigDecimal jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung) {
        this.jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung = jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung;
    }

    public String getPaygrade() {
        return this.paygrade;
    }

    public void setPaygrade(String paygrade) {
        this.paygrade = paygrade;
    }

    public String getDate() {
        PayRateTableNameDateSeperator payRateTableNameDateSeperator = new PayRateTableNameDateSeperator();
        return payRateTableNameDateSeperator.seperateDateAsString(table_name);
    }

}
