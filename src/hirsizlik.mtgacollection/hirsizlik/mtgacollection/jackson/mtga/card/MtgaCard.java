
package hirsizlik.mtgacollection.jackson.mtga.card;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "grpid",
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
    "cmc",
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

    @JsonProperty("grpid")
    private Integer grpid;
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
    private Object altDeckLimit;
    @JsonProperty("cmc")
    private Integer cmc;
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
    private List<Integer> types = null;
    @JsonProperty("subtypes")
    private List<Integer> subtypes = null;
    @JsonProperty("supertypes")
    private List<Integer> supertypes = null;
    @JsonProperty("cardTypeTextId")
    private Integer cardTypeTextId;
    @JsonProperty("subtypeTextId")
    private Integer subtypeTextId;
    @JsonProperty("colors")
    private List<Integer> colors = null;
    @JsonProperty("indicator")
    private List<Integer> indicator = null;
    @JsonProperty("frameColors")
    private List<Integer> frameColors = null;
    @JsonProperty("frameDetails")
    private List<String> frameDetails = null;
    @JsonProperty("rawFrameDetails")
    private String rawFrameDetails;
    @JsonProperty("extraFrameDetails")
    private ExtraFrameDetail extraFrameDetails;
    @JsonProperty("colorIdentity")
    private List<Integer> colorIdentity = null;
    @JsonProperty("abilities")
    private List<Ability> abilities = null;
    @JsonProperty("hiddenAbilities")
    private List<Ability> hiddenAbilities = null;
    @JsonProperty("TextChangeData")
    private TextChangeData textChangeData;
    @JsonProperty("linkedFaces")
    private List<Integer> linkedFaces = null;
    @JsonProperty("castingcost")
    private String castingcost;
    @JsonProperty("IsRebalanced")
    private Boolean isRebalanced;
    @JsonProperty("IsDigitalOnly")
    private Boolean isDigitalOnly;
    @JsonProperty("knownSupportedStyles")
    private List<String> knownSupportedStyles = null;
    @JsonProperty("RebalancedCardLink")
    private Integer rebalancedCardLink;
    @JsonProperty("DigitalReleaseSet")
    private String digitalReleaseSet;
    @JsonProperty("abilityIdToLinkedTokenGrpId")
    private AbilityIdToLinkedTokenGrpId abilityIdToLinkedTokenGrpId;
    @JsonProperty("linkedAbilityTemplateCardGrpIds")
    private List<Integer> linkedAbilityTemplateCardGrpIds = null;
    @JsonProperty("abilityIdToLinkedConjurations")
    private AbilityIdToLinkedConjurations abilityIdToLinkedConjurations;

    @JsonProperty("grpid")
    public Integer getGrpid() {
        return grpid;
    }

    @JsonProperty("grpid")
    public void setGrpid(Integer grpid) {
        this.grpid = grpid;
    }

    @JsonProperty("titleId")
    public Integer getTitleId() {
        return titleId;
    }

    @JsonProperty("titleId")
    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }

    @JsonProperty("altTitleId")
    public Integer getAltTitleId() {
        return altTitleId;
    }

    @JsonProperty("altTitleId")
    public void setAltTitleId(Integer altTitleId) {
        this.altTitleId = altTitleId;
    }

    @JsonProperty("artId")
    public Integer getArtId() {
        return artId;
    }

    @JsonProperty("artId")
    public void setArtId(Integer artId) {
        this.artId = artId;
    }

    @JsonProperty("isSecondaryCard")
    public Boolean getIsSecondaryCard() {
        return isSecondaryCard;
    }

    @JsonProperty("isSecondaryCard")
    public void setIsSecondaryCard(Boolean isSecondaryCard) {
        this.isSecondaryCard = isSecondaryCard;
    }

    @JsonProperty("isToken")
    public Boolean getIsToken() {
        return isToken;
    }

    @JsonProperty("isToken")
    public void setIsToken(Boolean isToken) {
        this.isToken = isToken;
    }

    @JsonProperty("artSize")
    public Integer getArtSize() {
        return artSize;
    }

    @JsonProperty("artSize")
    public void setArtSize(Integer artSize) {
        this.artSize = artSize;
    }

    @JsonProperty("power")
    public String getPower() {
        return power;
    }

    @JsonProperty("power")
    public void setPower(String power) {
        this.power = power;
    }

    @JsonProperty("toughness")
    public String getToughness() {
        return toughness;
    }

    @JsonProperty("toughness")
    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    @JsonProperty("flavorId")
    public Integer getFlavorId() {
        return flavorId;
    }

    @JsonProperty("flavorId")
    public void setFlavorId(Integer flavorId) {
        this.flavorId = flavorId;
    }

    @JsonProperty("collectorNumber")
    public String getCollectorNumber() {
        return collectorNumber;
    }

    @JsonProperty("collectorNumber")
    public void setCollectorNumber(String collectorNumber) {
        this.collectorNumber = collectorNumber;
    }

    @JsonProperty("collectorMax")
    public String getCollectorMax() {
        return collectorMax;
    }

    @JsonProperty("collectorMax")
    public void setCollectorMax(String collectorMax) {
        this.collectorMax = collectorMax;
    }

    @JsonProperty("altDeckLimit")
    public Object getAltDeckLimit() {
        return altDeckLimit;
    }

    @JsonProperty("altDeckLimit")
    public void setAltDeckLimit(Object altDeckLimit) {
        this.altDeckLimit = altDeckLimit;
    }

    @JsonProperty("cmc")
    public Integer getCmc() {
        return cmc;
    }

    @JsonProperty("cmc")
    public void setCmc(Integer cmc) {
        this.cmc = cmc;
    }

    @JsonProperty("rarity")
    public Integer getRarity() {
        return rarity;
    }

    @JsonProperty("rarity")
    public void setRarity(Integer rarity) {
        this.rarity = rarity;
    }

    @JsonProperty("artistCredit")
    public String getArtistCredit() {
        return artistCredit;
    }

    @JsonProperty("artistCredit")
    public void setArtistCredit(String artistCredit) {
        this.artistCredit = artistCredit;
    }

    @JsonProperty("set")
    public String getSet() {
        return set;
    }

    @JsonProperty("set")
    public void setSet(String set) {
        this.set = set;
    }

    @JsonProperty("usesSideboard")
    public Boolean getUsesSideboard() {
        return usesSideboard;
    }

    @JsonProperty("usesSideboard")
    public void setUsesSideboard(Boolean usesSideboard) {
        this.usesSideboard = usesSideboard;
    }

    @JsonProperty("linkedFaceType")
    public Integer getLinkedFaceType() {
        return linkedFaceType;
    }

    @JsonProperty("linkedFaceType")
    public void setLinkedFaceType(Integer linkedFaceType) {
        this.linkedFaceType = linkedFaceType;
    }

    @JsonProperty("types")
    public List<Integer> getTypes() {
        return types;
    }

    @JsonProperty("types")
    public void setTypes(List<Integer> types) {
        this.types = types;
    }

    @JsonProperty("subtypes")
    public List<Integer> getSubtypes() {
        return subtypes;
    }

    @JsonProperty("subtypes")
    public void setSubtypes(List<Integer> subtypes) {
        this.subtypes = subtypes;
    }

    @JsonProperty("supertypes")
    public List<Integer> getSupertypes() {
        return supertypes;
    }

    @JsonProperty("supertypes")
    public void setSupertypes(List<Integer> supertypes) {
        this.supertypes = supertypes;
    }

    @JsonProperty("cardTypeTextId")
    public Integer getCardTypeTextId() {
        return cardTypeTextId;
    }

    @JsonProperty("cardTypeTextId")
    public void setCardTypeTextId(Integer cardTypeTextId) {
        this.cardTypeTextId = cardTypeTextId;
    }

    @JsonProperty("subtypeTextId")
    public Integer getSubtypeTextId() {
        return subtypeTextId;
    }

    @JsonProperty("subtypeTextId")
    public void setSubtypeTextId(Integer subtypeTextId) {
        this.subtypeTextId = subtypeTextId;
    }

    @JsonProperty("colors")
    public List<Integer> getColors() {
        return colors;
    }

    @JsonProperty("colors")
    public void setColors(List<Integer> colors) {
        this.colors = colors;
    }

    @JsonProperty("indicator")
    public List<Integer> getIndicator() {
        return indicator;
    }

    @JsonProperty("indicator")
    public void setIndicator(List<Integer> indicator) {
        this.indicator = indicator;
    }

    @JsonProperty("frameColors")
    public List<Integer> getFrameColors() {
        return frameColors;
    }

    @JsonProperty("frameColors")
    public void setFrameColors(List<Integer> frameColors) {
        this.frameColors = frameColors;
    }

    @JsonProperty("frameDetails")
    public List<String> getFrameDetails() {
        return frameDetails;
    }

    @JsonProperty("frameDetails")
    public void setFrameDetails(List<String> frameDetails) {
        this.frameDetails = frameDetails;
    }

    @JsonProperty("rawFrameDetails")
    public String getRawFrameDetails() {
        return rawFrameDetails;
    }

    @JsonProperty("rawFrameDetails")
    public void setRawFrameDetails(String rawFrameDetails) {
        this.rawFrameDetails = rawFrameDetails;
    }

    @JsonProperty("extraFrameDetails")
    public ExtraFrameDetail getExtraFrameDetails() {
        return extraFrameDetails;
    }

    @JsonProperty("extraFrameDetails")
    public void setExtraFrameDetails(ExtraFrameDetail extraFrameDetails) {
        this.extraFrameDetails = extraFrameDetails;
    }

    @JsonProperty("colorIdentity")
    public List<Integer> getColorIdentity() {
        return colorIdentity;
    }

    @JsonProperty("colorIdentity")
    public void setColorIdentity(List<Integer> colorIdentity) {
        this.colorIdentity = colorIdentity;
    }

    @JsonProperty("abilities")
    public List<Ability> getAbilities() {
        return abilities;
    }

    @JsonProperty("abilities")
    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    @JsonProperty("hiddenAbilities")
    public List<Ability> getHiddenAbilities() {
        return hiddenAbilities;
    }

    @JsonProperty("hiddenAbilities")
    public void setHiddenAbilities(List<Ability> hiddenAbilities) {
        this.hiddenAbilities = hiddenAbilities;
    }

    @JsonProperty("TextChangeData")
    public TextChangeData getTextChangeData() {
        return textChangeData;
    }

    @JsonProperty("TextChangeData")
    public void setTextChangeData(TextChangeData textChangeData) {
        this.textChangeData = textChangeData;
    }

    @JsonProperty("linkedFaces")
    public List<Integer> getLinkedFaces() {
        return linkedFaces;
    }

    @JsonProperty("linkedFaces")
    public void setLinkedFaces(List<Integer> linkedFaces) {
        this.linkedFaces = linkedFaces;
    }

    @JsonProperty("castingcost")
    public String getCastingcost() {
        return castingcost;
    }

    @JsonProperty("castingcost")
    public void setCastingcost(String castingcost) {
        this.castingcost = castingcost;
    }

    @JsonProperty("IsRebalanced")
    public Boolean getIsRebalanced() {
        return isRebalanced;
    }

    @JsonProperty("IsRebalanced")
    public void setIsRebalanced(Boolean isRebalanced) {
        this.isRebalanced = isRebalanced;
    }

    @JsonProperty("IsDigitalOnly")
    public Boolean getIsDigitalOnly() {
        return isDigitalOnly;
    }

    @JsonProperty("IsDigitalOnly")
    public void setIsDigitalOnly(Boolean isDigitalOnly) {
        this.isDigitalOnly = isDigitalOnly;
    }

    @JsonProperty("knownSupportedStyles")
    public List<String> getKnownSupportedStyles() {
        return knownSupportedStyles;
    }

    @JsonProperty("knownSupportedStyles")
    public void setKnownSupportedStyles(List<String> knownSupportedStyles) {
        this.knownSupportedStyles = knownSupportedStyles;
    }

    @JsonProperty("RebalancedCardLink")
    public Integer getRebalancedCardLink() {
        return rebalancedCardLink;
    }

    @JsonProperty("RebalancedCardLink")
    public void setRebalancedCardLink(Integer rebalancedCardLink) {
        this.rebalancedCardLink = rebalancedCardLink;
    }

    @JsonProperty("DigitalReleaseSet")
    public String getDigitalReleaseSet() {
        return digitalReleaseSet;
    }

    @JsonProperty("DigitalReleaseSet")
    public void setDigitalReleaseSet(String digitalReleaseSet) {
        this.digitalReleaseSet = digitalReleaseSet;
    }

    @JsonProperty("abilityIdToLinkedTokenGrpId")
    public AbilityIdToLinkedTokenGrpId getAbilityIdToLinkedTokenGrpId() {
        return abilityIdToLinkedTokenGrpId;
    }

    @JsonProperty("abilityIdToLinkedTokenGrpId")
    public void setAbilityIdToLinkedTokenGrpId(AbilityIdToLinkedTokenGrpId abilityIdToLinkedTokenGrpId) {
        this.abilityIdToLinkedTokenGrpId = abilityIdToLinkedTokenGrpId;
    }

    @JsonProperty("linkedAbilityTemplateCardGrpIds")
    public List<Integer> getLinkedAbilityTemplateCardGrpIds() {
        return linkedAbilityTemplateCardGrpIds;
    }

    @JsonProperty("linkedAbilityTemplateCardGrpIds")
    public void setLinkedAbilityTemplateCardGrpIds(List<Integer> linkedAbilityTemplateCardGrpIds) {
        this.linkedAbilityTemplateCardGrpIds = linkedAbilityTemplateCardGrpIds;
    }

    @JsonProperty("abilityIdToLinkedConjurations")
    public AbilityIdToLinkedConjurations getAbilityIdToLinkedConjurations() {
        return abilityIdToLinkedConjurations;
    }

    @JsonProperty("abilityIdToLinkedConjurations")
    public void setAbilityIdToLinkedConjurations(AbilityIdToLinkedConjurations abilityIdToLinkedConjurations) {
        this.abilityIdToLinkedConjurations = abilityIdToLinkedConjurations;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MtgaCard.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("grpid");
        sb.append('=');
        sb.append(((this.grpid == null)?"<null>":this.grpid));
        sb.append(',');
        sb.append("titleId");
        sb.append('=');
        sb.append(((this.titleId == null)?"<null>":this.titleId));
        sb.append(',');
        sb.append("altTitleId");
        sb.append('=');
        sb.append(((this.altTitleId == null)?"<null>":this.altTitleId));
        sb.append(',');
        sb.append("artId");
        sb.append('=');
        sb.append(((this.artId == null)?"<null>":this.artId));
        sb.append(',');
        sb.append("isSecondaryCard");
        sb.append('=');
        sb.append(((this.isSecondaryCard == null)?"<null>":this.isSecondaryCard));
        sb.append(',');
        sb.append("isToken");
        sb.append('=');
        sb.append(((this.isToken == null)?"<null>":this.isToken));
        sb.append(',');
        sb.append("artSize");
        sb.append('=');
        sb.append(((this.artSize == null)?"<null>":this.artSize));
        sb.append(',');
        sb.append("power");
        sb.append('=');
        sb.append(((this.power == null)?"<null>":this.power));
        sb.append(',');
        sb.append("toughness");
        sb.append('=');
        sb.append(((this.toughness == null)?"<null>":this.toughness));
        sb.append(',');
        sb.append("flavorId");
        sb.append('=');
        sb.append(((this.flavorId == null)?"<null>":this.flavorId));
        sb.append(',');
        sb.append("collectorNumber");
        sb.append('=');
        sb.append(((this.collectorNumber == null)?"<null>":this.collectorNumber));
        sb.append(',');
        sb.append("collectorMax");
        sb.append('=');
        sb.append(((this.collectorMax == null)?"<null>":this.collectorMax));
        sb.append(',');
        sb.append("altDeckLimit");
        sb.append('=');
        sb.append(((this.altDeckLimit == null)?"<null>":this.altDeckLimit));
        sb.append(',');
        sb.append("cmc");
        sb.append('=');
        sb.append(((this.cmc == null)?"<null>":this.cmc));
        sb.append(',');
        sb.append("rarity");
        sb.append('=');
        sb.append(((this.rarity == null)?"<null>":this.rarity));
        sb.append(',');
        sb.append("artistCredit");
        sb.append('=');
        sb.append(((this.artistCredit == null)?"<null>":this.artistCredit));
        sb.append(',');
        sb.append("set");
        sb.append('=');
        sb.append(((this.set == null)?"<null>":this.set));
        sb.append(',');
        sb.append("usesSideboard");
        sb.append('=');
        sb.append(((this.usesSideboard == null)?"<null>":this.usesSideboard));
        sb.append(',');
        sb.append("linkedFaceType");
        sb.append('=');
        sb.append(((this.linkedFaceType == null)?"<null>":this.linkedFaceType));
        sb.append(',');
        sb.append("types");
        sb.append('=');
        sb.append(((this.types == null)?"<null>":this.types));
        sb.append(',');
        sb.append("subtypes");
        sb.append('=');
        sb.append(((this.subtypes == null)?"<null>":this.subtypes));
        sb.append(',');
        sb.append("supertypes");
        sb.append('=');
        sb.append(((this.supertypes == null)?"<null>":this.supertypes));
        sb.append(',');
        sb.append("cardTypeTextId");
        sb.append('=');
        sb.append(((this.cardTypeTextId == null)?"<null>":this.cardTypeTextId));
        sb.append(',');
        sb.append("subtypeTextId");
        sb.append('=');
        sb.append(((this.subtypeTextId == null)?"<null>":this.subtypeTextId));
        sb.append(',');
        sb.append("colors");
        sb.append('=');
        sb.append(((this.colors == null)?"<null>":this.colors));
        sb.append(',');
        sb.append("indicator");
        sb.append('=');
        sb.append(((this.indicator == null)?"<null>":this.indicator));
        sb.append(',');
        sb.append("frameColors");
        sb.append('=');
        sb.append(((this.frameColors == null)?"<null>":this.frameColors));
        sb.append(',');
        sb.append("frameDetails");
        sb.append('=');
        sb.append(((this.frameDetails == null)?"<null>":this.frameDetails));
        sb.append(',');
        sb.append("rawFrameDetails");
        sb.append('=');
        sb.append(((this.rawFrameDetails == null)?"<null>":this.rawFrameDetails));
        sb.append(',');
        sb.append("extraFrameDetails");
        sb.append('=');
        sb.append(((this.extraFrameDetails == null)?"<null>":this.extraFrameDetails));
        sb.append(',');
        sb.append("colorIdentity");
        sb.append('=');
        sb.append(((this.colorIdentity == null)?"<null>":this.colorIdentity));
        sb.append(',');
        sb.append("abilities");
        sb.append('=');
        sb.append(((this.abilities == null)?"<null>":this.abilities));
        sb.append(',');
        sb.append("hiddenAbilities");
        sb.append('=');
        sb.append(((this.hiddenAbilities == null)?"<null>":this.hiddenAbilities));
        sb.append(',');
        sb.append("textChangeData");
        sb.append('=');
        sb.append(((this.textChangeData == null)?"<null>":this.textChangeData));
        sb.append(',');
        sb.append("linkedFaces");
        sb.append('=');
        sb.append(((this.linkedFaces == null)?"<null>":this.linkedFaces));
        sb.append(',');
        sb.append("castingcost");
        sb.append('=');
        sb.append(((this.castingcost == null)?"<null>":this.castingcost));
        sb.append(',');
        sb.append("isRebalanced");
        sb.append('=');
        sb.append(((this.isRebalanced == null)?"<null>":this.isRebalanced));
        sb.append(',');
        sb.append("isDigitalOnly");
        sb.append('=');
        sb.append(((this.isDigitalOnly == null)?"<null>":this.isDigitalOnly));
        sb.append(',');
        sb.append("knownSupportedStyles");
        sb.append('=');
        sb.append(((this.knownSupportedStyles == null)?"<null>":this.knownSupportedStyles));
        sb.append(',');
        sb.append("rebalancedCardLink");
        sb.append('=');
        sb.append(((this.rebalancedCardLink == null)?"<null>":this.rebalancedCardLink));
        sb.append(',');
        sb.append("digitalReleaseSet");
        sb.append('=');
        sb.append(((this.digitalReleaseSet == null)?"<null>":this.digitalReleaseSet));
        sb.append(',');
        sb.append("abilityIdToLinkedTokenGrpId");
        sb.append('=');
        sb.append(((this.abilityIdToLinkedTokenGrpId == null)?"<null>":this.abilityIdToLinkedTokenGrpId));
        sb.append(',');
        sb.append("linkedAbilityTemplateCardGrpIds");
        sb.append('=');
        sb.append(((this.linkedAbilityTemplateCardGrpIds == null)?"<null>":this.linkedAbilityTemplateCardGrpIds));
        sb.append(',');
        sb.append("abilityIdToLinkedConjurations");
        sb.append('=');
        sb.append(((this.abilityIdToLinkedConjurations == null)?"<null>":this.abilityIdToLinkedConjurations));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.indicator == null)? 0 :this.indicator.hashCode()));
        result = ((result* 31)+((this.colorIdentity == null)? 0 :this.colorIdentity.hashCode()));
        result = ((result* 31)+((this.extraFrameDetails == null)? 0 :this.extraFrameDetails.hashCode()));
        result = ((result* 31)+((this.knownSupportedStyles == null)? 0 :this.knownSupportedStyles.hashCode()));
        result = ((result* 31)+((this.rebalancedCardLink == null)? 0 :this.rebalancedCardLink.hashCode()));
        result = ((result* 31)+((this.titleId == null)? 0 :this.titleId.hashCode()));
        result = ((result* 31)+((this.flavorId == null)? 0 :this.flavorId.hashCode()));
        result = ((result* 31)+((this.textChangeData == null)? 0 :this.textChangeData.hashCode()));
        result = ((result* 31)+((this.artSize == null)? 0 :this.artSize.hashCode()));
        result = ((result* 31)+((this.collectorMax == null)? 0 :this.collectorMax.hashCode()));
        result = ((result* 31)+((this.colors == null)? 0 :this.colors.hashCode()));
        result = ((result* 31)+((this.subtypes == null)? 0 :this.subtypes.hashCode()));
        result = ((result* 31)+((this.isToken == null)? 0 :this.isToken.hashCode()));
        result = ((result* 31)+((this.abilities == null)? 0 :this.abilities.hashCode()));
        result = ((result* 31)+((this.artId == null)? 0 :this.artId.hashCode()));
        result = ((result* 31)+((this.abilityIdToLinkedConjurations == null)? 0 :this.abilityIdToLinkedConjurations.hashCode()));
        result = ((result* 31)+((this.linkedFaceType == null)? 0 :this.linkedFaceType.hashCode()));
        result = ((result* 31)+((this.usesSideboard == null)? 0 :this.usesSideboard.hashCode()));
        result = ((result* 31)+((this.castingcost == null)? 0 :this.castingcost.hashCode()));
        result = ((result* 31)+((this.power == null)? 0 :this.power.hashCode()));
        result = ((result* 31)+((this.toughness == null)? 0 :this.toughness.hashCode()));
        result = ((result* 31)+((this.supertypes == null)? 0 :this.supertypes.hashCode()));
        result = ((result* 31)+((this.grpid == null)? 0 :this.grpid.hashCode()));
        result = ((result* 31)+((this.isRebalanced == null)? 0 :this.isRebalanced.hashCode()));
        result = ((result* 31)+((this.altTitleId == null)? 0 :this.altTitleId.hashCode()));
        result = ((result* 31)+((this.types == null)? 0 :this.types.hashCode()));
        result = ((result* 31)+((this.set == null)? 0 :this.set.hashCode()));
        result = ((result* 31)+((this.artistCredit == null)? 0 :this.artistCredit.hashCode()));
        result = ((result* 31)+((this.isDigitalOnly == null)? 0 :this.isDigitalOnly.hashCode()));
        result = ((result* 31)+((this.frameColors == null)? 0 :this.frameColors.hashCode()));
        result = ((result* 31)+((this.frameDetails == null)? 0 :this.frameDetails.hashCode()));
        result = ((result* 31)+((this.abilityIdToLinkedTokenGrpId == null)? 0 :this.abilityIdToLinkedTokenGrpId.hashCode()));
        result = ((result* 31)+((this.rawFrameDetails == null)? 0 :this.rawFrameDetails.hashCode()));
        result = ((result* 31)+((this.digitalReleaseSet == null)? 0 :this.digitalReleaseSet.hashCode()));
        result = ((result* 31)+((this.collectorNumber == null)? 0 :this.collectorNumber.hashCode()));
        result = ((result* 31)+((this.subtypeTextId == null)? 0 :this.subtypeTextId.hashCode()));
        result = ((result* 31)+((this.linkedAbilityTemplateCardGrpIds == null)? 0 :this.linkedAbilityTemplateCardGrpIds.hashCode()));
        result = ((result* 31)+((this.cardTypeTextId == null)? 0 :this.cardTypeTextId.hashCode()));
        result = ((result* 31)+((this.cmc == null)? 0 :this.cmc.hashCode()));
        result = ((result* 31)+((this.hiddenAbilities == null)? 0 :this.hiddenAbilities.hashCode()));
        result = ((result* 31)+((this.isSecondaryCard == null)? 0 :this.isSecondaryCard.hashCode()));
        result = ((result* 31)+((this.altDeckLimit == null)? 0 :this.altDeckLimit.hashCode()));
        result = ((result* 31)+((this.rarity == null)? 0 :this.rarity.hashCode()));
        result = ((result* 31)+((this.linkedFaces == null)? 0 :this.linkedFaces.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MtgaCard) == false) {
            return false;
        }
        MtgaCard rhs = ((MtgaCard) other);
        return (((((((((((((((((((((((((((((((((((((((((((((this.indicator == rhs.indicator)||((this.indicator!= null)&&this.indicator.equals(rhs.indicator)))&&((this.colorIdentity == rhs.colorIdentity)||((this.colorIdentity!= null)&&this.colorIdentity.equals(rhs.colorIdentity))))&&((this.extraFrameDetails == rhs.extraFrameDetails)||((this.extraFrameDetails!= null)&&this.extraFrameDetails.equals(rhs.extraFrameDetails))))&&((this.knownSupportedStyles == rhs.knownSupportedStyles)||((this.knownSupportedStyles!= null)&&this.knownSupportedStyles.equals(rhs.knownSupportedStyles))))&&((this.rebalancedCardLink == rhs.rebalancedCardLink)||((this.rebalancedCardLink!= null)&&this.rebalancedCardLink.equals(rhs.rebalancedCardLink))))&&((this.titleId == rhs.titleId)||((this.titleId!= null)&&this.titleId.equals(rhs.titleId))))&&((this.flavorId == rhs.flavorId)||((this.flavorId!= null)&&this.flavorId.equals(rhs.flavorId))))&&((this.textChangeData == rhs.textChangeData)||((this.textChangeData!= null)&&this.textChangeData.equals(rhs.textChangeData))))&&((this.artSize == rhs.artSize)||((this.artSize!= null)&&this.artSize.equals(rhs.artSize))))&&((this.collectorMax == rhs.collectorMax)||((this.collectorMax!= null)&&this.collectorMax.equals(rhs.collectorMax))))&&((this.colors == rhs.colors)||((this.colors!= null)&&this.colors.equals(rhs.colors))))&&((this.subtypes == rhs.subtypes)||((this.subtypes!= null)&&this.subtypes.equals(rhs.subtypes))))&&((this.isToken == rhs.isToken)||((this.isToken!= null)&&this.isToken.equals(rhs.isToken))))&&((this.abilities == rhs.abilities)||((this.abilities!= null)&&this.abilities.equals(rhs.abilities))))&&((this.artId == rhs.artId)||((this.artId!= null)&&this.artId.equals(rhs.artId))))&&((this.abilityIdToLinkedConjurations == rhs.abilityIdToLinkedConjurations)||((this.abilityIdToLinkedConjurations!= null)&&this.abilityIdToLinkedConjurations.equals(rhs.abilityIdToLinkedConjurations))))&&((this.linkedFaceType == rhs.linkedFaceType)||((this.linkedFaceType!= null)&&this.linkedFaceType.equals(rhs.linkedFaceType))))&&((this.usesSideboard == rhs.usesSideboard)||((this.usesSideboard!= null)&&this.usesSideboard.equals(rhs.usesSideboard))))&&((this.castingcost == rhs.castingcost)||((this.castingcost!= null)&&this.castingcost.equals(rhs.castingcost))))&&((this.power == rhs.power)||((this.power!= null)&&this.power.equals(rhs.power))))&&((this.toughness == rhs.toughness)||((this.toughness!= null)&&this.toughness.equals(rhs.toughness))))&&((this.supertypes == rhs.supertypes)||((this.supertypes!= null)&&this.supertypes.equals(rhs.supertypes))))&&((this.grpid == rhs.grpid)||((this.grpid!= null)&&this.grpid.equals(rhs.grpid))))&&((this.isRebalanced == rhs.isRebalanced)||((this.isRebalanced!= null)&&this.isRebalanced.equals(rhs.isRebalanced))))&&((this.altTitleId == rhs.altTitleId)||((this.altTitleId!= null)&&this.altTitleId.equals(rhs.altTitleId))))&&((this.types == rhs.types)||((this.types!= null)&&this.types.equals(rhs.types))))&&((this.set == rhs.set)||((this.set!= null)&&this.set.equals(rhs.set))))&&((this.artistCredit == rhs.artistCredit)||((this.artistCredit!= null)&&this.artistCredit.equals(rhs.artistCredit))))&&((this.isDigitalOnly == rhs.isDigitalOnly)||((this.isDigitalOnly!= null)&&this.isDigitalOnly.equals(rhs.isDigitalOnly))))&&((this.frameColors == rhs.frameColors)||((this.frameColors!= null)&&this.frameColors.equals(rhs.frameColors))))&&((this.frameDetails == rhs.frameDetails)||((this.frameDetails!= null)&&this.frameDetails.equals(rhs.frameDetails))))&&((this.abilityIdToLinkedTokenGrpId == rhs.abilityIdToLinkedTokenGrpId)||((this.abilityIdToLinkedTokenGrpId!= null)&&this.abilityIdToLinkedTokenGrpId.equals(rhs.abilityIdToLinkedTokenGrpId))))&&((this.rawFrameDetails == rhs.rawFrameDetails)||((this.rawFrameDetails!= null)&&this.rawFrameDetails.equals(rhs.rawFrameDetails))))&&((this.digitalReleaseSet == rhs.digitalReleaseSet)||((this.digitalReleaseSet!= null)&&this.digitalReleaseSet.equals(rhs.digitalReleaseSet))))&&((this.collectorNumber == rhs.collectorNumber)||((this.collectorNumber!= null)&&this.collectorNumber.equals(rhs.collectorNumber))))&&((this.subtypeTextId == rhs.subtypeTextId)||((this.subtypeTextId!= null)&&this.subtypeTextId.equals(rhs.subtypeTextId))))&&((this.linkedAbilityTemplateCardGrpIds == rhs.linkedAbilityTemplateCardGrpIds)||((this.linkedAbilityTemplateCardGrpIds!= null)&&this.linkedAbilityTemplateCardGrpIds.equals(rhs.linkedAbilityTemplateCardGrpIds))))&&((this.cardTypeTextId == rhs.cardTypeTextId)||((this.cardTypeTextId!= null)&&this.cardTypeTextId.equals(rhs.cardTypeTextId))))&&((this.cmc == rhs.cmc)||((this.cmc!= null)&&this.cmc.equals(rhs.cmc))))&&((this.hiddenAbilities == rhs.hiddenAbilities)||((this.hiddenAbilities!= null)&&this.hiddenAbilities.equals(rhs.hiddenAbilities))))&&((this.isSecondaryCard == rhs.isSecondaryCard)||((this.isSecondaryCard!= null)&&this.isSecondaryCard.equals(rhs.isSecondaryCard))))&&((this.altDeckLimit == rhs.altDeckLimit)||((this.altDeckLimit!= null)&&this.altDeckLimit.equals(rhs.altDeckLimit))))&&((this.rarity == rhs.rarity)||((this.rarity!= null)&&this.rarity.equals(rhs.rarity))))&&((this.linkedFaces == rhs.linkedFaces)||((this.linkedFaces!= null)&&this.linkedFaces.equals(rhs.linkedFaces))));
    }

}
