
package hirsizlik.mtgacollection.jackson.mtga.card;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "abilityId",
    "linkedTokenGrpId"
})
public class AbilityIdToLinkedTokenGrpId {

    @JsonProperty("abilityId")
    private Integer abilityId;
    @JsonProperty("linkedTokenGrpId")
    private List<Integer> linkedTokenGrpId = null;

    @JsonProperty("abilityId")
    public Integer getAbilityId() {
        return abilityId;
    }

    @JsonProperty("abilityId")
    public void setAbilityId(final Integer abilityId) {
        this.abilityId = abilityId;
    }

    @JsonProperty("linkedTokenGrpId")
    public List<Integer> getLinkedTokenGrpId() {
        return linkedTokenGrpId;
    }

    @JsonProperty("linkedTokenGrpId")
    public void setLinkedTokenGrpId(final List<Integer> linkedTokenGrpId) {
        this.linkedTokenGrpId = linkedTokenGrpId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(AbilityIdToLinkedTokenGrpId.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("abilityId");
        sb.append('=');
        sb.append(((this.abilityId == null)?"<null>":this.abilityId));
        sb.append(',');
        sb.append("linkedTokenGrpId");
        sb.append('=');
        sb.append(((this.linkedTokenGrpId == null)?"<null>":this.linkedTokenGrpId));
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
        result = ((result* 31)+((this.linkedTokenGrpId == null)? 0 :this.linkedTokenGrpId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AbilityIdToLinkedTokenGrpId) == false) {
            return false;
        }
        AbilityIdToLinkedTokenGrpId rhs = ((AbilityIdToLinkedTokenGrpId) other);
        return (((this.abilityId == rhs.abilityId)||((this.abilityId!= null)&&this.abilityId.equals(rhs.abilityId)))&&((this.linkedTokenGrpId == rhs.linkedTokenGrpId)||((this.linkedTokenGrpId!= null)&&this.linkedTokenGrpId.equals(rhs.linkedTokenGrpId))));
    }

}
