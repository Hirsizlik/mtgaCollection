
package hirsizlik.mtgacollection.jackson.scryfall.set;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "object",
    "id",
    "code",
    "mtgo_code",
    "arena_code",
    "tcgplayer_id",
    "name",
    "uri",
    "scryfall_uri",
    "search_uri",
    "released_at",
    "set_type",
    "card_count",
    "printed_size",
    "digital",
    "nonfoil_only",
    "foil_only",
    "block_code",
    "block",
    "icon_svg_uri"
})
public class ScryfallSet {

    @JsonProperty("object")
    private String object;
    @JsonProperty("id")
    private String id;
    @JsonProperty("code")
    private String code;
    @JsonProperty("mtgo_code")
    private String mtgoCode;
    @JsonProperty("arena_code")
    private String arenaCode;
    @JsonProperty("tcgplayer_id")
    private Integer tcgplayerId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("scryfall_uri")
    private String scryfallUri;
    @JsonProperty("search_uri")
    private String searchUri;
    @JsonProperty("released_at")
    private String releasedAt;
    @JsonProperty("set_type")
    private String setType;
    @JsonProperty("card_count")
    private Integer cardCount;
    @JsonProperty("printed_size")
    private Integer printedSize;
    @JsonProperty("digital")
    private Boolean digital;
    @JsonProperty("nonfoil_only")
    private Boolean nonfoilOnly;
    @JsonProperty("foil_only")
    private Boolean foilOnly;
    @JsonProperty("block_code")
    private String blockCode;
    @JsonProperty("block")
    private String block;
    @JsonProperty("icon_svg_uri")
    private String iconSvgUri;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("object")
    public String getObject() {
        return object;
    }

    @JsonProperty("object")
    public void setObject(String object) {
        this.object = object;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("mtgo_code")
    public String getMtgoCode() {
        return mtgoCode;
    }

    @JsonProperty("mtgo_code")
    public void setMtgoCode(String mtgoCode) {
        this.mtgoCode = mtgoCode;
    }

    @JsonProperty("arena_code")
    public String getArenaCode() {
        return arenaCode;
    }

    @JsonProperty("arena_code")
    public void setArenaCode(String arenaCode) {
        this.arenaCode = arenaCode;
    }

    @JsonProperty("tcgplayer_id")
    public Integer getTcgplayerId() {
        return tcgplayerId;
    }

    @JsonProperty("tcgplayer_id")
    public void setTcgplayerId(Integer tcgplayerId) {
        this.tcgplayerId = tcgplayerId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    @JsonProperty("scryfall_uri")
    public String getScryfallUri() {
        return scryfallUri;
    }

    @JsonProperty("scryfall_uri")
    public void setScryfallUri(String scryfallUri) {
        this.scryfallUri = scryfallUri;
    }

    @JsonProperty("search_uri")
    public String getSearchUri() {
        return searchUri;
    }

    @JsonProperty("search_uri")
    public void setSearchUri(String searchUri) {
        this.searchUri = searchUri;
    }

    @JsonProperty("released_at")
    public String getReleasedAt() {
        return releasedAt;
    }

    @JsonProperty("released_at")
    public void setReleasedAt(String releasedAt) {
        this.releasedAt = releasedAt;
    }

    @JsonProperty("set_type")
    public String getSetType() {
        return setType;
    }

    @JsonProperty("set_type")
    public void setSetType(String setType) {
        this.setType = setType;
    }

    @JsonProperty("card_count")
    public Integer getCardCount() {
        return cardCount;
    }

    @JsonProperty("card_count")
    public void setCardCount(Integer cardCount) {
        this.cardCount = cardCount;
    }

    @JsonProperty("printed_size")
    public Integer getPrintedSize() {
        return printedSize;
    }

    @JsonProperty("printed_size")
    public void setPrintedSize(Integer printedSize) {
        this.printedSize = printedSize;
    }

    @JsonProperty("digital")
    public Boolean getDigital() {
        return digital;
    }

    @JsonProperty("digital")
    public void setDigital(Boolean digital) {
        this.digital = digital;
    }

    @JsonProperty("nonfoil_only")
    public Boolean getNonfoilOnly() {
        return nonfoilOnly;
    }

    @JsonProperty("nonfoil_only")
    public void setNonfoilOnly(Boolean nonfoilOnly) {
        this.nonfoilOnly = nonfoilOnly;
    }

    @JsonProperty("foil_only")
    public Boolean getFoilOnly() {
        return foilOnly;
    }

    @JsonProperty("foil_only")
    public void setFoilOnly(Boolean foilOnly) {
        this.foilOnly = foilOnly;
    }

    @JsonProperty("block_code")
    public String getBlockCode() {
        return blockCode;
    }

    @JsonProperty("block_code")
    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    @JsonProperty("block")
    public String getBlock() {
        return block;
    }

    @JsonProperty("block")
    public void setBlock(String block) {
        this.block = block;
    }

    @JsonProperty("icon_svg_uri")
    public String getIconSvgUri() {
        return iconSvgUri;
    }

    @JsonProperty("icon_svg_uri")
    public void setIconSvgUri(String iconSvgUri) {
        this.iconSvgUri = iconSvgUri;
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
