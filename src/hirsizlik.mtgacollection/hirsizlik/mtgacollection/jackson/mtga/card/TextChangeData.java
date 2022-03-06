
package hirsizlik.mtgacollection.jackson.mtga.card;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ChangedAbilityId",
    "ChangeSourceId",
    "OriginalAbilityId"
})
public class TextChangeData {

    @JsonProperty("ChangedAbilityId")
    private Integer changedAbilityId;
    @JsonProperty("ChangeSourceId")
    private Integer changeSourceId;
    @JsonProperty("OriginalAbilityId")
    private Integer originalAbilityId;

    @JsonProperty("ChangedAbilityId")
    public Integer getChangedAbilityId() {
        return changedAbilityId;
    }

    @JsonProperty("ChangedAbilityId")
    public void setChangedAbilityId(Integer changedAbilityId) {
        this.changedAbilityId = changedAbilityId;
    }

    @JsonProperty("ChangeSourceId")
    public Integer getChangeSourceId() {
        return changeSourceId;
    }

    @JsonProperty("ChangeSourceId")
    public void setChangeSourceId(Integer changeSourceId) {
        this.changeSourceId = changeSourceId;
    }

    @JsonProperty("OriginalAbilityId")
    public Integer getOriginalAbilityId() {
        return originalAbilityId;
    }

    @JsonProperty("OriginalAbilityId")
    public void setOriginalAbilityId(Integer originalAbilityId) {
        this.originalAbilityId = originalAbilityId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(TextChangeData.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("changedAbilityId");
        sb.append('=');
        sb.append(((this.changedAbilityId == null)?"<null>":this.changedAbilityId));
        sb.append(',');
        sb.append("changeSourceId");
        sb.append('=');
        sb.append(((this.changeSourceId == null)?"<null>":this.changeSourceId));
        sb.append(',');
        sb.append("originalAbilityId");
        sb.append('=');
        sb.append(((this.originalAbilityId == null)?"<null>":this.originalAbilityId));
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
        result = ((result* 31)+((this.changedAbilityId == null)? 0 :this.changedAbilityId.hashCode()));
        result = ((result* 31)+((this.originalAbilityId == null)? 0 :this.originalAbilityId.hashCode()));
        result = ((result* 31)+((this.changeSourceId == null)? 0 :this.changeSourceId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TextChangeData) == false) {
            return false;
        }
        TextChangeData rhs = ((TextChangeData) other);
        return ((((this.changedAbilityId == rhs.changedAbilityId)||((this.changedAbilityId!= null)&&this.changedAbilityId.equals(rhs.changedAbilityId)))&&((this.originalAbilityId == rhs.originalAbilityId)||((this.originalAbilityId!= null)&&this.originalAbilityId.equals(rhs.originalAbilityId))))&&((this.changeSourceId == rhs.changeSourceId)||((this.changeSourceId!= null)&&this.changeSourceId.equals(rhs.changeSourceId))));
    }

}
