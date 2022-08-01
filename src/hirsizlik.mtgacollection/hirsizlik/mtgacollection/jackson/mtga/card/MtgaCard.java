
package hirsizlik.mtgacollection.jackson.mtga.card;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "grpId",
    "titleId",
    "altTitleId",
    "artId",
    "isSecondaryCard",
    "isToken",
    "artSize",
    "power",
    "toughness",
    "flavorId",
    "collectorNumber",
    "collectorMax",
    "altDeckLimit",
    "rarity",
    "artistCredit",
    "set",
    "usesSideboard",
    "linkedFaceType",
    "types",
    "subtypes",
    "supertypes",
    "cardTypeTextId",
    "subtypeTextId",
    "colors",
    "indicator",
    "frameColors",
    "frameDetails",
    "rawFrameDetails",
    "extraFrameDetails",
    "watermark",
    "colorIdentity",
    "abilities",
    "hiddenAbilities",
    "TextChangeData",
    "linkedFaces",
    "castingcost",
    "IsRebalanced",
    "IsDigitalOnly",
    "knownSupportedStyles",
    "RebalancedCardLink",
    "DigitalReleaseSet",
    "abilityIdToLinkedTokenGrpId",
    "linkedAbilityTemplateCardGrpIds",
    "abilityIdToLinkedConjurations"
})
public class MtgaCard {

    @JsonProperty("grpId")
    private Integer grpId;
    @JsonProperty("titleId")
    private Integer titleId;
    @JsonProperty("altTitleId")
    private Integer altTitleId;
    @JsonProperty("artId")
    private Integer artId;
    @JsonProperty("isSecondaryCard")
    private Boolean isSecondaryCard;
    @JsonProperty("isToken")
    private Boolean isToken;
    @JsonProperty("artSize")
    private Integer artSize;
    @JsonProperty("power")
    private String power;
    @JsonProperty("toughness")
    private String toughness;
    @JsonProperty("flavorId")
    private Integer flavorId;
    @JsonProperty("collectorNumber")
    private String collectorNumber;
    @JsonProperty("collectorMax")
    private String collectorMax;
    @JsonProperty("altDeckLimit")
    private Integer altDeckLimit;
    @JsonProperty("rarity")
    private Integer rarity;
    @JsonProperty("artistCredit")
    private String artistCredit;
    @JsonProperty("set")
    private String set;
    @JsonProperty("usesSideboard")
    private Boolean usesSideboard;
    @JsonProperty("linkedFaceType")
    private Integer linkedFaceType;
    @JsonProperty("types")
    private String types;
    @JsonProperty("subtypes")
    private String subtypes;
    @JsonProperty("supertypes")
    private String supertypes;
    @JsonProperty("cardTypeTextId")
    private Integer cardTypeTextId;
    @JsonProperty("subtypeTextId")
    private Integer subtypeTextId;
    @JsonProperty("colors")
    private String colors;
    @JsonProperty("indicator")
    private String indicator;
    @JsonProperty("frameColors")
    private String frameColors;
    @JsonProperty("frameDetails")
    private String frameDetails;
    @JsonProperty("rawFrameDetails")
    private String rawFrameDetails;
    @JsonProperty("extraFrameDetails")
    private String extraFrameDetails;
    @JsonProperty("watermark")
    private String watermark;
    @JsonProperty("colorIdentity")
    private String colorIdentity;
    @JsonProperty("abilities")
    private String abilities;
    @JsonProperty("hiddenAbilities")
    private String hiddenAbilities;
    @JsonProperty("TextChangeData")
    private String textChangeData;
    @JsonProperty("linkedFaces")
    private String linkedFaces;
    @JsonProperty("castingcost")
    private String castingcost;
    @JsonProperty("IsRebalanced")
    private Boolean isRebalanced;
    @JsonProperty("IsDigitalOnly")
    private Boolean isDigitalOnly;
    @JsonProperty("knownSupportedStyles")
    private String knownSupportedStyles;
    @JsonProperty("RebalancedCardLink")
    private Integer rebalancedCardLink;
    @JsonProperty("DigitalReleaseSet")
    private String digitalReleaseSet;
    @JsonProperty("abilityIdToLinkedTokenGrpId")
    private String abilityIdToLinkedTokenGrpId;
    @JsonProperty("linkedAbilityTemplateCardGrpIds")
    private String linkedAbilityTemplateCardGrpIds;
    @JsonProperty("abilityIdToLinkedConjurations")
    private String abilityIdToLinkedConjurations;

    @JsonProperty("grpId")
    public Integer getGrpId() {
        return grpId;
    }

    @JsonProperty("grpId")
    public void setGrpId(final Integer grpId) {
        this.grpId = grpId;
    }

    @JsonProperty("titleId")
    public Integer getTitleId() {
        return titleId;
    }

    @JsonProperty("titleId")
    public void setTitleId(final Integer titleId) {
        this.titleId = titleId;
    }

    @JsonProperty("altTitleId")
    public Integer getAltTitleId() {
        return altTitleId;
    }

    @JsonProperty("altTitleId")
    public void setAltTitleId(final Integer altTitleId) {
        this.altTitleId = altTitleId;
    }

    @JsonProperty("artId")
    public Integer getArtId() {
        return artId;
    }

    @JsonProperty("artId")
    public void setArtId(final Integer artId) {
        this.artId = artId;
    }

    @JsonProperty("isSecondaryCard")
    public Boolean getIsSecondaryCard() {
        return isSecondaryCard;
    }

    @JsonProperty("isSecondaryCard")
    public void setIsSecondaryCard(final Boolean isSecondaryCard) {
        this.isSecondaryCard = isSecondaryCard;
    }

    @JsonProperty("isToken")
    public Boolean getIsToken() {
        return isToken;
    }

    @JsonProperty("isToken")
    public void setIsToken(final Boolean isToken) {
        this.isToken = isToken;
    }

    @JsonProperty("artSize")
    public Integer getArtSize() {
        return artSize;
    }

    @JsonProperty("artSize")
    public void setArtSize(final Integer artSize) {
        this.artSize = artSize;
    }

    @JsonProperty("power")
    public String getPower() {
        return power;
    }

    @JsonProperty("power")
    public void setPower(final String power) {
        this.power = power;
    }

    @JsonProperty("toughness")
    public String getToughness() {
        return toughness;
    }

    @JsonProperty("toughness")
    public void setToughness(final String toughness) {
        this.toughness = toughness;
    }

    @JsonProperty("flavorId")
    public Integer getFlavorId() {
        return flavorId;
    }

    @JsonProperty("flavorId")
    public void setFlavorId(final Integer flavorId) {
        this.flavorId = flavorId;
    }

    @JsonProperty("collectorNumber")
    public String getCollectorNumber() {
        return collectorNumber;
    }

    @JsonProperty("collectorNumber")
    public void setCollectorNumber(final String collectorNumber) {
        this.collectorNumber = collectorNumber;
    }

    @JsonProperty("collectorMax")
    public String getCollectorMax() {
        return collectorMax;
    }

    @JsonProperty("collectorMax")
    public void setCollectorMax(final String collectorMax) {
        this.collectorMax = collectorMax;
    }

    @JsonProperty("altDeckLimit")
    public Integer getAltDeckLimit() {
        return altDeckLimit;
    }

    @JsonProperty("altDeckLimit")
    public void setAltDeckLimit(final Integer altDeckLimit) {
        this.altDeckLimit = altDeckLimit;
    }

    @JsonProperty("rarity")
    public Integer getRarity() {
        return rarity;
    }

    @JsonProperty("rarity")
    public void setRarity(final Integer rarity) {
        this.rarity = rarity;
    }

    @JsonProperty("artistCredit")
    public String getArtistCredit() {
        return artistCredit;
    }

    @JsonProperty("artistCredit")
    public void setArtistCredit(final String artistCredit) {
        this.artistCredit = artistCredit;
    }

    @JsonProperty("set")
    public String getSet() {
        return set;
    }

    @JsonProperty("set")
    public void setSet(final String set) {
        this.set = set;
    }

    @JsonProperty("usesSideboard")
    public Boolean getUsesSideboard() {
        return usesSideboard;
    }

    @JsonProperty("usesSideboard")
    public void setUsesSideboard(final Boolean usesSideboard) {
        this.usesSideboard = usesSideboard;
    }

    @JsonProperty("linkedFaceType")
    public Integer getLinkedFaceType() {
        return linkedFaceType;
    }

    @JsonProperty("linkedFaceType")
    public void setLinkedFaceType(final Integer linkedFaceType) {
        this.linkedFaceType = linkedFaceType;
    }

    @JsonProperty("types")
    public String getTypes() {
        return types;
    }

    @JsonProperty("types")
    public void setTypes(final String types) {
        this.types = types;
    }

    @JsonProperty("subtypes")
    public String getSubtypes() {
        return subtypes;
    }

    @JsonProperty("subtypes")
    public void setSubtypes(final String subtypes) {
        this.subtypes = subtypes;
    }

    @JsonProperty("supertypes")
    public String getSupertypes() {
        return supertypes;
    }

    @JsonProperty("supertypes")
    public void setSupertypes(final String supertypes) {
        this.supertypes = supertypes;
    }

    @JsonProperty("cardTypeTextId")
    public Integer getCardTypeTextId() {
        return cardTypeTextId;
    }

    @JsonProperty("cardTypeTextId")
    public void setCardTypeTextId(final Integer cardTypeTextId) {
        this.cardTypeTextId = cardTypeTextId;
    }

    @JsonProperty("subtypeTextId")
    public Integer getSubtypeTextId() {
        return subtypeTextId;
    }

    @JsonProperty("subtypeTextId")
    public void setSubtypeTextId(final Integer subtypeTextId) {
        this.subtypeTextId = subtypeTextId;
    }

    @JsonProperty("colors")
    public String getColors() {
        return colors;
    }

    @JsonProperty("colors")
    public void setColors(final String colors) {
        this.colors = colors;
    }

    @JsonProperty("indicator")
    public String getIndicator() {
        return indicator;
    }

    @JsonProperty("indicator")
    public void setIndicator(final String indicator) {
        this.indicator = indicator;
    }

    @JsonProperty("frameColors")
    public String getFrameColors() {
        return frameColors;
    }

    @JsonProperty("frameColors")
    public void setFrameColors(final String frameColors) {
        this.frameColors = frameColors;
    }

    @JsonProperty("frameDetails")
    public String getFrameDetails() {
        return frameDetails;
    }

    @JsonProperty("frameDetails")
    public void setFrameDetails(final String frameDetails) {
        this.frameDetails = frameDetails;
    }

    @JsonProperty("rawFrameDetails")
    public String getRawFrameDetails() {
        return rawFrameDetails;
    }

    @JsonProperty("rawFrameDetails")
    public void setRawFrameDetails(final String rawFrameDetails) {
        this.rawFrameDetails = rawFrameDetails;
    }

    @JsonProperty("extraFrameDetails")
    public String getExtraFrameDetails() {
        return extraFrameDetails;
    }

    @JsonProperty("extraFrameDetails")
    public void setExtraFrameDetails(final String extraFrameDetails) {
        this.extraFrameDetails = extraFrameDetails;
    }

    @JsonProperty("watermark")
    public String getWatermark() {
        return watermark;
    }

    @JsonProperty("watermark")
    public void setWatermark(final String watermark) {
        this.watermark = watermark;
    }

    @JsonProperty("colorIdentity")
    public String getColorIdentity() {
        return colorIdentity;
    }

    @JsonProperty("colorIdentity")
    public void setColorIdentity(final String colorIdentity) {
        this.colorIdentity = colorIdentity;
    }

    @JsonProperty("abilities")
    public String getAbilities() {
        return abilities;
    }

    @JsonProperty("abilities")
    public void setAbilities(final String abilities) {
        this.abilities = abilities;
    }

    @JsonProperty("hiddenAbilities")
    public String getHiddenAbilities() {
        return hiddenAbilities;
    }

    @JsonProperty("hiddenAbilities")
    public void setHiddenAbilities(final String hiddenAbilities) {
        this.hiddenAbilities = hiddenAbilities;
    }

    @JsonProperty("TextChangeData")
    public String getTextChangeData() {
        return textChangeData;
    }

    @JsonProperty("TextChangeData")
    public void setTextChangeData(final String textChangeData) {
        this.textChangeData = textChangeData;
    }

    @JsonProperty("linkedFaces")
    public String getLinkedFaces() {
        return linkedFaces;
    }

    @JsonProperty("linkedFaces")
    public void setLinkedFaces(final String linkedFaces) {
        this.linkedFaces = linkedFaces;
    }

    @JsonProperty("castingcost")
    public String getCastingcost() {
        return castingcost;
    }

    @JsonProperty("castingcost")
    public void setCastingcost(final String castingcost) {
        this.castingcost = castingcost;
    }

    @JsonProperty("IsRebalanced")
    public Boolean getIsRebalanced() {
        return isRebalanced;
    }

    @JsonProperty("IsRebalanced")
    public void setIsRebalanced(final Boolean isRebalanced) {
        this.isRebalanced = isRebalanced;
    }

    @JsonProperty("IsDigitalOnly")
    public Boolean getIsDigitalOnly() {
        return isDigitalOnly;
    }

    @JsonProperty("IsDigitalOnly")
    public void setIsDigitalOnly(final Boolean isDigitalOnly) {
        this.isDigitalOnly = isDigitalOnly;
    }

    @JsonProperty("knownSupportedStyles")
    public String getKnownSupportedStyles() {
        return knownSupportedStyles;
    }

    @JsonProperty("knownSupportedStyles")
    public void setKnownSupportedStyles(final String knownSupportedStyles) {
        this.knownSupportedStyles = knownSupportedStyles;
    }

    @JsonProperty("RebalancedCardLink")
    public Integer getRebalancedCardLink() {
        return rebalancedCardLink;
    }

    @JsonProperty("RebalancedCardLink")
    public void setRebalancedCardLink(final Integer rebalancedCardLink) {
        this.rebalancedCardLink = rebalancedCardLink;
    }

    @JsonProperty("DigitalReleaseSet")
    public String getDigitalReleaseSet() {
        return digitalReleaseSet;
    }

    @JsonProperty("DigitalReleaseSet")
    public void setDigitalReleaseSet(final String digitalReleaseSet) {
        this.digitalReleaseSet = digitalReleaseSet;
    }

    @JsonProperty("abilityIdToLinkedTokenGrpId")
    public String getAbilityIdToLinkedTokenGrpId() {
        return abilityIdToLinkedTokenGrpId;
    }

    @JsonProperty("abilityIdToLinkedTokenGrpId")
    public void setAbilityIdToLinkedTokenGrpId(final String abilityIdToLinkedTokenGrpId) {
        this.abilityIdToLinkedTokenGrpId = abilityIdToLinkedTokenGrpId;
    }

    @JsonProperty("linkedAbilityTemplateCardGrpIds")
    public String getLinkedAbilityTemplateCardGrpIds() {
        return linkedAbilityTemplateCardGrpIds;
    }

    @JsonProperty("linkedAbilityTemplateCardGrpIds")
    public void setLinkedAbilityTemplateCardGrpIds(final String linkedAbilityTemplateCardGrpIds) {
        this.linkedAbilityTemplateCardGrpIds = linkedAbilityTemplateCardGrpIds;
    }

    @JsonProperty("abilityIdToLinkedConjurations")
    public String getAbilityIdToLinkedConjurations() {
        return abilityIdToLinkedConjurations;
    }

    @JsonProperty("abilityIdToLinkedConjurations")
    public void setAbilityIdToLinkedConjurations(final String abilityIdToLinkedConjurations) {
        this.abilityIdToLinkedConjurations = abilityIdToLinkedConjurations;
    }

}
