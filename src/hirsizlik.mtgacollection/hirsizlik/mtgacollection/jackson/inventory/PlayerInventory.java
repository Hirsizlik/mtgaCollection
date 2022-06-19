
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
    "Gems",
    "Gold",
    "TotalVaultProgress",
    "wcTrackPosition",
    "WildCardCommons",
    "WildCardUnCommons",
    "WildCardRares",
    "WildCardMythics",
    "CustomTokens",
    "Boosters"
})
public class PlayerInventory {

    @JsonProperty("Gems")
    private Integer gems;
    @JsonProperty("Gold")
    private Integer gold;
    @JsonProperty("TotalVaultProgress")
    private Integer totalVaultProgress;
    @JsonProperty("wcTrackPosition")
    private Integer wcTrackPosition;
    @JsonProperty("WildCardCommons")
    private Integer wildCardCommons;
    @JsonProperty("WildCardUnCommons")
    private Integer wildCardUnCommons;
    @JsonProperty("WildCardRares")
    private Integer wildCardRares;
    @JsonProperty("WildCardMythics")
    private Integer wildCardMythics;
    @JsonProperty("CustomTokens")
    private CustomToken customTokens;
    @JsonProperty("Boosters")
    private List<Booster> boosters = null;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Gems")
    public Integer getGems() {
        return gems;
    }

    @JsonProperty("Gems")
    public void setGems(final Integer gems) {
        this.gems = gems;
    }

    @JsonProperty("Gold")
    public Integer getGold() {
        return gold;
    }

    @JsonProperty("Gold")
    public void setGold(final Integer gold) {
        this.gold = gold;
    }

    @JsonProperty("TotalVaultProgress")
    public Integer getTotalVaultProgress() {
        return totalVaultProgress;
    }

    @JsonProperty("TotalVaultProgress")
    public void setTotalVaultProgress(final Integer totalVaultProgress) {
        this.totalVaultProgress = totalVaultProgress;
    }

    @JsonProperty("wcTrackPosition")
    public Integer getWcTrackPosition() {
        return wcTrackPosition;
    }

    @JsonProperty("wcTrackPosition")
    public void setWcTrackPosition(final Integer wcTrackPosition) {
        this.wcTrackPosition = wcTrackPosition;
    }

    @JsonProperty("WildCardCommons")
    public Integer getWildCardCommons() {
        return wildCardCommons;
    }

    @JsonProperty("WildCardCommons")
    public void setWildCardCommons(final Integer wildCardCommons) {
        this.wildCardCommons = wildCardCommons;
    }

    @JsonProperty("WildCardUnCommons")
    public Integer getWildCardUnCommons() {
        return wildCardUnCommons;
    }

    @JsonProperty("WildCardUnCommons")
    public void setWildCardUnCommons(final Integer wildCardUnCommons) {
        this.wildCardUnCommons = wildCardUnCommons;
    }

    @JsonProperty("WildCardRares")
    public Integer getWildCardRares() {
        return wildCardRares;
    }

    @JsonProperty("WildCardRares")
    public void setWildCardRares(final Integer wildCardRares) {
        this.wildCardRares = wildCardRares;
    }

    @JsonProperty("WildCardMythics")
    public Integer getWildCardMythics() {
        return wildCardMythics;
    }

    @JsonProperty("WildCardMythics")
    public void setWildCardMythics(final Integer wildCardMythics) {
        this.wildCardMythics = wildCardMythics;
    }

    @JsonProperty("CustomTokens")
    public CustomToken getCustomTokens() {
        return customTokens;
    }

    @JsonProperty("CustomTokens")
    public void setCustomTokens(final CustomToken customTokens) {
        this.customTokens = customTokens;
    }

    @JsonProperty("Boosters")
    public List<Booster> getBoosters() {
        return boosters;
    }

    @JsonProperty("Boosters")
    public void setBoosters(final List<Booster> boosters) {
        this.boosters = boosters;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(final String name, final Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PlayerInventory.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("gems");
        sb.append('=');
        sb.append(((this.gems == null)?"<null>":this.gems));
        sb.append(',');
        sb.append("gold");
        sb.append('=');
        sb.append(((this.gold == null)?"<null>":this.gold));
        sb.append(',');
        sb.append("totalVaultProgress");
        sb.append('=');
        sb.append(((this.totalVaultProgress == null)?"<null>":this.totalVaultProgress));
        sb.append(',');
        sb.append("wcTrackPosition");
        sb.append('=');
        sb.append(((this.wcTrackPosition == null)?"<null>":this.wcTrackPosition));
        sb.append(',');
        sb.append("wildCardCommons");
        sb.append('=');
        sb.append(((this.wildCardCommons == null)?"<null>":this.wildCardCommons));
        sb.append(',');
        sb.append("wildCardUnCommons");
        sb.append('=');
        sb.append(((this.wildCardUnCommons == null)?"<null>":this.wildCardUnCommons));
        sb.append(',');
        sb.append("wildCardRares");
        sb.append('=');
        sb.append(((this.wildCardRares == null)?"<null>":this.wildCardRares));
        sb.append(',');
        sb.append("wildCardMythics");
        sb.append('=');
        sb.append(((this.wildCardMythics == null)?"<null>":this.wildCardMythics));
        sb.append(',');
        sb.append("customTokens");
        sb.append('=');
        sb.append(((this.customTokens == null)?"<null>":this.customTokens));
        sb.append(',');
        sb.append("boosters");
        sb.append('=');
        sb.append(((this.boosters == null)?"<null>":this.boosters));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
