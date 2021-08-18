
package hirsizlik.mtgacollection.jackson.inventory;

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
    "avatarSelection",
    "cardBackSelection",
    "petSelection"
})
public class VanitySelections {

    @JsonProperty("avatarSelection")
    private String avatarSelection;
    @JsonProperty("cardBackSelection")
    private Object cardBackSelection;
    @JsonProperty("petSelection")
    private PetSelection petSelection;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("avatarSelection")
    public String getAvatarSelection() {
        return avatarSelection;
    }

    @JsonProperty("avatarSelection")
    public void setAvatarSelection(String avatarSelection) {
        this.avatarSelection = avatarSelection;
    }

    @JsonProperty("cardBackSelection")
    public Object getCardBackSelection() {
        return cardBackSelection;
    }

    @JsonProperty("cardBackSelection")
    public void setCardBackSelection(Object cardBackSelection) {
        this.cardBackSelection = cardBackSelection;
    }

    @JsonProperty("petSelection")
    public PetSelection getPetSelection() {
        return petSelection;
    }

    @JsonProperty("petSelection")
    public void setPetSelection(PetSelection petSelection) {
        this.petSelection = petSelection;
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
