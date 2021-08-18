
package hirsizlik.mtgacollection.jackson.mtga.card;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "abilityId",
    "textId"
})
public class HiddenAbility {

    @JsonProperty("abilityId")
    private Integer abilityId;
    @JsonProperty("textId")
    private Integer textId;

    @JsonProperty("abilityId")
    public Integer getAbilityId() {
        return abilityId;
    }

    @JsonProperty("abilityId")
    public void setAbilityId(final Integer abilityId) {
        this.abilityId = abilityId;
    }

    @JsonProperty("textId")
    public Integer getTextId() {
        return textId;
    }

    @JsonProperty("textId")
    public void setTextId(final Integer textId) {
        this.textId = textId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HiddenAbility.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("abilityId");
        sb.append('=');
        sb.append(((this.abilityId == null)?"<null>":this.abilityId));
        sb.append(',');
        sb.append("textId");
        sb.append('=');
        sb.append(((this.textId == null)?"<null>":this.textId));
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
        result = ((result* 31)+((this.abilityId == null)? 0 :this.abilityId.hashCode()));
        result = ((result* 31)+((this.textId == null)? 0 :this.textId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof HiddenAbility) == false) {
            return false;
        }
        HiddenAbility rhs = ((HiddenAbility) other);
        return (((this.abilityId == rhs.abilityId)||((this.abilityId!= null)&&this.abilityId.equals(rhs.abilityId)))&&((this.textId == rhs.textId)||((this.textId!= null)&&this.textId.equals(rhs.textId))));
    }

}
