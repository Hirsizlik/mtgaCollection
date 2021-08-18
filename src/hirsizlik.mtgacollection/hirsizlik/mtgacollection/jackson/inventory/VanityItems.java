
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
    "pets",
    "avatars",
    "cardBacks"
})
public class VanityItems {

    @JsonProperty("pets")
    private List<Pet> pets = null;
    @JsonProperty("avatars")
    private List<Avatar> avatars = null;
    @JsonProperty("cardBacks")
    private List<CardBack> cardBacks = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("pets")
    public List<Pet> getPets() {
        return pets;
    }

    @JsonProperty("pets")
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    @JsonProperty("avatars")
    public List<Avatar> getAvatars() {
        return avatars;
    }

    @JsonProperty("avatars")
    public void setAvatars(List<Avatar> avatars) {
        this.avatars = avatars;
    }

    @JsonProperty("cardBacks")
    public List<CardBack> getCardBacks() {
        return cardBacks;
    }

    @JsonProperty("cardBacks")
    public void setCardBacks(List<CardBack> cardBacks) {
        this.cardBacks = cardBacks;
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
