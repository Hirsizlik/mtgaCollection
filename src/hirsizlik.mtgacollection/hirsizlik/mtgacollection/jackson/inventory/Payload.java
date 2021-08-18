
package hirsizlik.mtgacollection.jackson.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "playerId",
    "wcCommon",
    "wcUncommon",
    "wcRare",
    "wcMythic",
    "gold",
    "gems",
    "draftTokens",
    "sealedTokens",
    "wcTrackPosition",
    "vaultProgress",
    "boosters",
    "vanityItems",
    "vanitySelections",
    "vouchers",
    "basicLandSet",
    "latestBasicLandSet",
    "starterDecks",
    "firstSeenDate",
    "eventTokens"
})
public class Payload {

    @JsonProperty("playerId")
    private String playerId;
    @JsonProperty("wcCommon")
    private Integer wcCommon;
    @JsonProperty("wcUncommon")
    private Integer wcUncommon;
    @JsonProperty("wcRare")
    private Integer wcRare;
    @JsonProperty("wcMythic")
    private Integer wcMythic;
    @JsonProperty("gold")
    private Integer gold;
    @JsonProperty("gems")
    private Integer gems;
    @JsonProperty("draftTokens")
    private Integer draftTokens;
    @JsonProperty("sealedTokens")
    private Integer sealedTokens;
    @JsonProperty("wcTrackPosition")
    private Integer wcTrackPosition;
    @JsonProperty("vaultProgress")
    private Double vaultProgress;
    @JsonProperty("boosters")
    private List<Booster> boosters = null;
    @JsonProperty("vanityItems")
    private VanityItems vanityItems;
    @JsonProperty("vanitySelections")
    private VanitySelections vanitySelections;
    @JsonProperty("vouchers")
    private List<Object> vouchers = null;
    @JsonProperty("basicLandSet")
    private String basicLandSet;
    @JsonProperty("latestBasicLandSet")
    private String latestBasicLandSet;
    @JsonProperty("starterDecks")
    private List<String> starterDecks = null;
    @JsonProperty("firstSeenDate")
    private String firstSeenDate;
    @JsonProperty("eventTokens")
    private EventTokens eventTokens;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("playerId")
    public String getPlayerId() {
        return playerId;
    }

    @JsonProperty("playerId")
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @JsonProperty("wcCommon")
    public Integer getWcCommon() {
        return wcCommon;
    }

    @JsonProperty("wcCommon")
    public void setWcCommon(Integer wcCommon) {
        this.wcCommon = wcCommon;
    }

    @JsonProperty("wcUncommon")
    public Integer getWcUncommon() {
        return wcUncommon;
    }

    @JsonProperty("wcUncommon")
    public void setWcUncommon(Integer wcUncommon) {
        this.wcUncommon = wcUncommon;
    }

    @JsonProperty("wcRare")
    public Integer getWcRare() {
        return wcRare;
    }

    @JsonProperty("wcRare")
    public void setWcRare(Integer wcRare) {
        this.wcRare = wcRare;
    }

    @JsonProperty("wcMythic")
    public Integer getWcMythic() {
        return wcMythic;
    }

    @JsonProperty("wcMythic")
    public void setWcMythic(Integer wcMythic) {
        this.wcMythic = wcMythic;
    }

    @JsonProperty("gold")
    public Integer getGold() {
        return gold;
    }

    @JsonProperty("gold")
    public void setGold(Integer gold) {
        this.gold = gold;
    }

    @JsonProperty("gems")
    public Integer getGems() {
        return gems;
    }

    @JsonProperty("gems")
    public void setGems(Integer gems) {
        this.gems = gems;
    }

    @JsonProperty("draftTokens")
    public Integer getDraftTokens() {
        return draftTokens;
    }

    @JsonProperty("draftTokens")
    public void setDraftTokens(Integer draftTokens) {
        this.draftTokens = draftTokens;
    }

    @JsonProperty("sealedTokens")
    public Integer getSealedTokens() {
        return sealedTokens;
    }

    @JsonProperty("sealedTokens")
    public void setSealedTokens(Integer sealedTokens) {
        this.sealedTokens = sealedTokens;
    }

    @JsonProperty("wcTrackPosition")
    public Integer getWcTrackPosition() {
        return wcTrackPosition;
    }

    @JsonProperty("wcTrackPosition")
    public void setWcTrackPosition(Integer wcTrackPosition) {
        this.wcTrackPosition = wcTrackPosition;
    }

    @JsonProperty("vaultProgress")
    public Double getVaultProgress() {
        return vaultProgress;
    }

    @JsonProperty("vaultProgress")
    public void setVaultProgress(Double vaultProgress) {
        this.vaultProgress = vaultProgress;
    }

    @JsonProperty("boosters")
    public List<Booster> getBoosters() {
        return boosters;
    }

    @JsonProperty("boosters")
    public void setBoosters(List<Booster> boosters) {
        this.boosters = boosters;
    }

    @JsonProperty("vanityItems")
    public VanityItems getVanityItems() {
        return vanityItems;
    }

    @JsonProperty("vanityItems")
    public void setVanityItems(VanityItems vanityItems) {
        this.vanityItems = vanityItems;
    }

    @JsonProperty("vanitySelections")
    public VanitySelections getVanitySelections() {
        return vanitySelections;
    }

    @JsonProperty("vanitySelections")
    public void setVanitySelections(VanitySelections vanitySelections) {
        this.vanitySelections = vanitySelections;
    }

    @JsonProperty("vouchers")
    public List<Object> getVouchers() {
        return vouchers;
    }

    @JsonProperty("vouchers")
    public void setVouchers(List<Object> vouchers) {
        this.vouchers = vouchers;
    }

    @JsonProperty("basicLandSet")
    public String getBasicLandSet() {
        return basicLandSet;
    }

    @JsonProperty("basicLandSet")
    public void setBasicLandSet(String basicLandSet) {
        this.basicLandSet = basicLandSet;
    }

    @JsonProperty("latestBasicLandSet")
    public String getLatestBasicLandSet() {
        return latestBasicLandSet;
    }

    @JsonProperty("latestBasicLandSet")
    public void setLatestBasicLandSet(String latestBasicLandSet) {
        this.latestBasicLandSet = latestBasicLandSet;
    }

    @JsonProperty("starterDecks")
    public List<String> getStarterDecks() {
        return starterDecks;
    }

    @JsonProperty("starterDecks")
    public void setStarterDecks(List<String> starterDecks) {
        this.starterDecks = starterDecks;
    }

    @JsonProperty("firstSeenDate")
    public String getFirstSeenDate() {
        return firstSeenDate;
    }

    @JsonProperty("firstSeenDate")
    public void setFirstSeenDate(String firstSeenDate) {
        this.firstSeenDate = firstSeenDate;
    }

    @JsonProperty("eventTokens")
    public EventTokens getEventTokens() {
        return eventTokens;
    }

    @JsonProperty("eventTokens")
    public void setEventTokens(EventTokens eventTokens) {
        this.eventTokens = eventTokens;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
