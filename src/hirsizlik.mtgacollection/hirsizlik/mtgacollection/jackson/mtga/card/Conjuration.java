
package hirsizlik.mtgacollection.jackson.mtga.card;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ConjurationType",
    "CardGrpId"
})
public class Conjuration {

    @JsonProperty("ConjurationType")
    private Integer conjurationType;
    @JsonProperty("CardGrpId")
    private Integer cardGrpId;

    @JsonProperty("ConjurationType")
    public Integer getConjurationType() {
        return conjurationType;
    }

    @JsonProperty("ConjurationType")
    public void setConjurationType(Integer conjurationType) {
        this.conjurationType = conjurationType;
    }

    @JsonProperty("CardGrpId")
    public Integer getCardGrpId() {
        return cardGrpId;
    }

    @JsonProperty("CardGrpId")
    public void setCardGrpId(Integer cardGrpId) {
        this.cardGrpId = cardGrpId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Conjuration.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("conjurationType");
        sb.append('=');
        sb.append(((this.conjurationType == null)?"<null>":this.conjurationType));
        sb.append(',');
        sb.append("cardGrpId");
        sb.append('=');
        sb.append(((this.cardGrpId == null)?"<null>":this.cardGrpId));
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
        result = ((result* 31)+((this.conjurationType == null)? 0 :this.conjurationType.hashCode()));
        result = ((result* 31)+((this.cardGrpId == null)? 0 :this.cardGrpId.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Conjuration) == false) {
            return false;
        }
        Conjuration rhs = ((Conjuration) other);
        return (((this.conjurationType == rhs.conjurationType)||((this.conjurationType!= null)&&this.conjurationType.equals(rhs.conjurationType)))&&((this.cardGrpId == rhs.cardGrpId)||((this.cardGrpId!= null)&&this.cardGrpId.equals(rhs.cardGrpId))));
    }

}
