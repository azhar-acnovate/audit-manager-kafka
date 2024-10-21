package com.acnovate.kafka.consumer.AuditManager.domain.dto;

import com.acnovate.kafka.consumer.AuditManager.util.CustomBooleanDeserializer;
import com.acnovate.kafka.consumer.AuditManager.util.CustomDateDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LCSSeasonDTO {

    @JsonDeserialize(converter = CustomBooleanDeserializer.class)
    @JsonProperty("ACTIVE")
    private Boolean ACTIVE;

    @JsonDeserialize(converter = CustomBooleanDeserializer.class)
    @JsonProperty("ADMINISTRATIVELOCKISNULL")
    private Boolean ADMINISTRATIVELOCKISNULL;

    @JsonProperty("TYPEADMINISTRATIVELOCK")
    private String TYPEADMINISTRATIVELOCK;

    @JsonProperty("BLOB$ENTRYSETADHOCL")
    private byte[] BLOB$ENTRYSETADHOCL;

    @JsonDeserialize(converter = CustomBooleanDeserializer.class)
    @JsonProperty("CHECKOUTINFOISNULL")
    private Boolean CHECKOUTINFOISNULL;

    @JsonProperty("STATECHECKOUTINFO")
    private String STATECHECKOUTINFO;

    @JsonProperty("CLASSNAMEKEYCONTAINERREFEREN")
    private String CLASSNAMEKEYCONTAINERREFEREN;

    @JsonProperty("IDA3CONTAINERREFERENCE")
    private Long IDA3CONTAINERREFERENCE;

    @JsonProperty("CLASSNAMEKEYB10")
    private String CLASSNAMEKEYB10;

    @JsonProperty("IDA3B10")
    private Long IDA3B10;

    @JsonProperty("CLASSNAMEKEYDOMAINREF")
    private String CLASSNAMEKEYDOMAINREF;

    @JsonProperty("IDA3DOMAINREF")
    private Long IDA3DOMAINREF;

    @JsonProperty("ENTRYSETADHOCL")
    private String ENTRYSETADHOCL;

    @JsonProperty("EVENTSET")
    private String EVENTSET;

    @JsonProperty("FLEXTYPEIDPATH")
    private String FLEXTYPEIDPATH;

    @JsonProperty("CLASSNAMEKEYA2FOLDERINGINFO")
    private String CLASSNAMEKEYA2FOLDERINGINFO;

    @JsonProperty("IDA3A2FOLDERINGINFO")
    private Long IDA3A2FOLDERINGINFO;

    @JsonProperty("CLASSNAMEKEYB2FOLDERINGINFO")
    private String CLASSNAMEKEYB2FOLDERINGINFO;

    @JsonProperty("IDA3B2FOLDERINGINFO")
    private Long IDA3B2FOLDERINGINFO;

    @JsonDeserialize(converter = CustomBooleanDeserializer.class)
    @JsonProperty("HASHANGINGCHANGE")
    private Boolean HASHANGINGCHANGE;

    @JsonDeserialize(converter = CustomBooleanDeserializer.class)
    @JsonProperty("HASPENDINGCHANGE")
    private Boolean HASPENDINGCHANGE;

    @JsonDeserialize(converter = CustomBooleanDeserializer.class)
    @JsonProperty("HASRESULTINGCHANGE")
    private Boolean HASRESULTINGCHANGE;

    @JsonDeserialize(converter = CustomBooleanDeserializer.class)
    @JsonProperty("HASSUSPECT")
    private Boolean HASSUSPECT;

    @JsonDeserialize(converter = CustomBooleanDeserializer.class)
    @JsonProperty("HASVARIANCE")
    private Boolean HASVARIANCE;

    @JsonProperty("INDEXERSINDEXERSET")
    private String INDEXERSINDEXERSET;

    @JsonDeserialize(converter = CustomBooleanDeserializer.class)
    @JsonProperty("INHERITEDDOMAIN")
    private Boolean INHERITEDDOMAIN;

    @JsonProperty("BRANCHIDITERATIONINFO")
    private Long BRANCHIDITERATIONINFO;

    @JsonProperty("CLASSNAMEKEYD2ITERATIONINFO")
    private String CLASSNAMEKEYD2ITERATIONINFO;

    @JsonProperty("IDA3D2ITERATIONINFO")
    private Long IDA3D2ITERATIONINFO;

    @JsonProperty("CLASSNAMEKEYE2ITERATIONINFO")
    private String CLASSNAMEKEYE2ITERATIONINFO;

    @JsonProperty("IDA3E2ITERATIONINFO")
    private Long IDA3E2ITERATIONINFO;

    @JsonProperty("ITERATIONIDA2ITERATIONINFO")
    private String ITERATIONIDA2ITERATIONINFO;

    @JsonDeserialize(converter = CustomBooleanDeserializer.class)
    @JsonProperty("LATESTITERATIONINFO")
    private Boolean LATESTITERATIONINFO;

    @JsonProperty("CLASSNAMEKEYB2ITERATIONINFO")
    private String CLASSNAMEKEYB2ITERATIONINFO;

    @JsonProperty("IDA3B2ITERATIONINFO")
    private Long IDA3B2ITERATIONINFO;

    @JsonProperty("WT_FBI_COMPUTE_U_0_3")
    private String WT_FBI_COMPUTE_U_0_3;

    @JsonProperty("WT_FBI_COMPUTE_U_0_4")
    private String WT_FBI_COMPUTE_U_0_4;

    @JsonProperty("NOTEITERATIONINFO")
    private String NOTEITERATIONINFO;

    @JsonProperty("CLASSNAMEKEYC2ITERATIONINFO")
    private String CLASSNAMEKEYC2ITERATIONINFO;

    @JsonProperty("IDA3C2ITERATIONINFO")
    private Long IDA3C2ITERATIONINFO;

    @JsonProperty("STATEITERATIONINFO")
    private String STATEITERATIONINFO;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonProperty("DATELOCK")
    private Date DATELOCK;

    @JsonProperty("CLASSNAMEKEYA2LOCK")
    private String CLASSNAMEKEYA2LOCK;

    @JsonProperty("IDA3A2LOCK")
    private Long IDA3A2LOCK;

    @JsonProperty("NOTELOCK")
    private String NOTELOCK;

    @JsonProperty("CLASSNAMEKEYMASTERREFERENCE")
    private String CLASSNAMEKEYMASTERREFERENCE;

    @JsonProperty("IDA3MASTERREFERENCE")
    private Long IDA3MASTERREFERENCE;

    @JsonProperty("CLASSNAMEKEYA2OWNERSHIP")
    private String CLASSNAMEKEYA2OWNERSHIP;

    @JsonProperty("IDA3A2OWNERSHIP")
    private Long IDA3A2OWNERSHIP;

    @JsonProperty("CLASSNAMEKEYA12")
    private String CLASSNAMEKEYA12;

    @JsonProperty("IDA3A12")
    private Long IDA3A12;

    @JsonProperty("PRIMARYIMAGEURL")
    private String PRIMARYIMAGEURL;

    @JsonProperty("PRODUCTTYPEROLE")
    private String PRODUCTTYPEROLE;

    @JsonProperty("SEASONGROUPIDS")
    private String SEASONGROUPIDS;

    @JsonProperty("SECURITYLABELS")
    private String SECURITYLABELS;

    @JsonDeserialize(converter = CustomBooleanDeserializer.class)
    @JsonProperty("ATGATESTATE")
    private Boolean ATGATESTATE;

    @JsonProperty("CLASSNAMEKEYA2STATE")
    private String CLASSNAMEKEYA2STATE;

    @JsonProperty("IDA3A2STATE")
    private Long IDA3A2STATE;

    @JsonProperty("STATESTATE")
    private String STATESTATE;

    @JsonDeserialize(converter = CustomBooleanDeserializer.class)
    @JsonProperty("TEAMIDISNULL")
    private Boolean TEAMIDISNULL;

    @JsonProperty("CLASSNAMEKEYTEAMID")
    private String CLASSNAMEKEYTEAMID;

    @JsonProperty("IDA3TEAMID")
    private Long IDA3TEAMID;

    @JsonDeserialize(converter = CustomBooleanDeserializer.class)
    @JsonProperty("TEAMTEMPLATEIDISNULL")
    private Boolean TEAMTEMPLATEIDISNULL;

    @JsonProperty("CLASSNAMEKEYTEAMTEMPLATEID")
    private String CLASSNAMEKEYTEAMTEMPLATEID;

    @JsonProperty("IDA3TEAMTEMPLATEID")
    private Long IDA3TEAMTEMPLATEID;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonProperty("CREATESTAMPA2")
    private Date CREATESTAMPA2;

    @JsonProperty("MARKFORDELETEA2")
    private Long MARKFORDELETEA2;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonProperty("MODIFYSTAMPA2")
    private Date MODIFYSTAMPA2;

    @JsonProperty("CLASSNAMEA2A2")
    private String CLASSNAMEA2A2;

    @JsonProperty("IDA2A2")
    private Long IDA2A2;

    @JsonProperty("UPDATECOUNTA2")
    private Integer UPDATECOUNTA2;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonProperty("UPDATESTAMPA2")
    private Date UPDATESTAMPA2;

    @JsonProperty("BRANCHIDA2TYPEDEFINITIONREFE")
    private Long BRANCHIDA2TYPEDEFINITIONREFE;

    @JsonProperty("IDA2TYPEDEFINITIONREFERENCE")
    private Long IDA2TYPEDEFINITIONREFERENCE;

    @JsonProperty("TYPEDISPLAY")
    private String TYPEDISPLAY;

    @JsonProperty("VERSIONIDA2VERSIONINFO")
    private String VERSIONIDA2VERSIONINFO;

    @JsonProperty("VERSIONLEVELA2VERSIONINFO")
    private Integer VERSIONLEVELA2VERSIONINFO;

    @JsonProperty("VERSIONSORTIDA2VERSIONINFO")
    private String VERSIONSORTIDA2VERSIONINFO;

    @JsonProperty("PTC_STR_1TYPEINFOLCSSEASON")
    private String PTC_STR_1TYPEINFOLCSSEASON;

    @JsonProperty("PTC_STR_2TYPEINFOLCSSEASON")
    private String PTC_STR_2TYPEINFOLCSSEASON;

    @JsonProperty("PTC_STR_3TYPEINFOLCSSEASON")
    private String PTC_STR_3TYPEINFOLCSSEASON;

    @JsonProperty("PTC_STR_4TYPEINFOLCSSEASON")
    private String PTC_STR_4TYPEINFOLCSSEASON;

    @JsonProperty("PTC_STR_5TYPEINFOLCSSEASON")
    private String PTC_STR_5TYPEINFOLCSSEASON;


}
